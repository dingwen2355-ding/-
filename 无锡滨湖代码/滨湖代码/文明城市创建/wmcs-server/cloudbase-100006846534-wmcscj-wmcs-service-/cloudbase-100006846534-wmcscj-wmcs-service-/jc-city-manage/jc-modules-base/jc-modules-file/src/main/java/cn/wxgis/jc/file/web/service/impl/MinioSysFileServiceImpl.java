package cn.wxgis.jc.file.web.service.impl;

import cn.hutool.core.util.ZipUtil;
import cn.hutool.http.HttpUtil;
import cn.wxgis.jc.common.constant.Constant;
import cn.wxgis.jc.common.constant.ConstantForError;
import cn.wxgis.jc.common.enums.global.GlobalStatusEnum;
import cn.wxgis.jc.common.exception.ServiceException;
import cn.wxgis.jc.common.util.StringUtils;
import cn.wxgis.jc.common.util.file.FileUploadUtils;
import cn.wxgis.jc.common.util.file.FileUtils;
import cn.wxgis.jc.common.web.service.impl.BaseServiceImpl;
import cn.wxgis.jc.config.MinioConfig;
import cn.wxgis.jc.file.web.mapper.SysFileMapper;
import cn.wxgis.jc.file.web.service.FilesService;
import cn.wxgis.jc.system.po.SysFile;
import cn.wxgis.jc.system.req.SysFileUploadRequest;
import io.minio.*;
import io.minio.errors.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.file.FileSystemException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class MinioSysFileServiceImpl extends BaseServiceImpl<SysFileMapper, SysFile> implements FilesService {
    private final MinioConfig minioConfig;
    private final MinioClient minioClient;

    public MinioSysFileServiceImpl(SysFileMapper baseDao, MinioConfig minioConfig, MinioClient minioClient) {
        super(baseDao);
        this.minioConfig = minioConfig;
        this.minioClient = minioClient;
    }

    /**
     * 判断bulk桶是否存在
     * @param bulkName
     * @return
     */
    private boolean isBuckExist(String bulkName){
        try {
            return minioClient.bucketExists(BucketExistsArgs.builder().bucket(bulkName).build());
        } catch (Exception e) {
            log.error("bulkname is not exist,error "+e.getMessage());
            return false;
        }
    }

    public void createBucket(String bucketName) {
        //如果不存在就创建
        if (!isBuckExist(bucketName)) {
            try {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            } catch (Exception e) {
                log.error("error " + e.getMessage());
            }
        }
    }

    @Override
    public String upload(MultipartFile file, boolean isToken) {
        try {
            String bucketName = isToken ? minioConfig.getBucketName() : minioConfig.getBucketNameText();
            if(!isBuckExist(bucketName)) throw new ServiceException(ConstantForError.FILE_BUCKETNAME_ERROR);
            //重命名
            String fileName = FileUploadUtils.extractFilename(file);
            InputStream inputStream = file.getInputStream();
            PutObjectArgs args = PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(fileName)
                    .stream(inputStream, file.getSize(), -1)
                    .contentType(StringUtils.isNotBlank(file.getContentType()) ?  file.getContentType() : null)
                    .build();
            minioClient.putObject(args);
            inputStream.close();
            return  "/" + fileName;
        } catch (Exception e) {
            throw new ServiceException(GlobalStatusEnum.FILE_UPLOAD_FAIL.getMsg());
        }
    }

    @Override
    public void download(String file, HttpServletResponse response, boolean isToken) {
        String errorMsg = GlobalStatusEnum.FILE_DOWNLOAD_FAIL.getMsg();
        String bucketName = isToken ? minioConfig.getBucketName() : minioConfig.getBucketNameText();
        if(!isBuckExist(bucketName)) throw new ServiceException(ConstantForError.FILE_BUCKETNAME_ERROR);

        InputStream inputStream   = null;
        OutputStream outputStream = null;
        try {
            String fileName = file;
            if (StringUtils.isBlank(fileName)) {
                response.setHeader("Content-type", "application/octet-stream;charset=UTF-8");
                OutputStream ps = response.getOutputStream();
                ps.write(errorMsg.getBytes("UTF-8"));
                throw new ServiceException(errorMsg);
            }
            // 获取文件对象
            outputStream = response.getOutputStream();
            inputStream = minioClient.getObject(GetObjectArgs.builder().bucket(bucketName).object(fileName).build());
            byte buf[] = new byte[1024];
            int length = 0;
            response.reset();
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName.substring(fileName.lastIndexOf("/") + 1), Constant.UTF8));
            response.setContentType("application/octet-stream;charset=UTF-8");
            response.setCharacterEncoding(Constant.UTF8);
            // 输出文件
            while ((length = inputStream.read(buf)) > 0) {
                outputStream.write(buf, 0, length);
            }
            inputStream.close();
        } catch (Throwable ex) {
            response.setHeader("Content-type", "text/html;charset=UTF-8");
            try {
                OutputStream ps = response.getOutputStream();
                ps.write(errorMsg.getBytes("UTF-8"));
            } catch (IOException e){
                throw new ServiceException(errorMsg);
            }
        } finally {
            try {
                outputStream.close();
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e){
                throw new ServiceException(errorMsg);
            }
        }
    }

    @Override
    public SysFile uploadFile(SysFileUploadRequest request, boolean isToken) {
        String bucketName = isToken ? minioConfig.getBucketName() : minioConfig.getBucketNameText();
        if(!isBuckExist(bucketName)) throw new ServiceException(ConstantForError.FILE_BUCKETNAME_ERROR);
        try{
            MultipartFile file = request.getFile();
            //重命名
            String fileName = FileUploadUtils.extractFilename(file);
            InputStream inputStream = file.getInputStream();
            PutObjectArgs args = PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(fileName)
                    .stream(inputStream, file.getSize(), -1)
                    .contentType(file.getContentType())
                    .build();
            minioClient.putObject(args);
            inputStream.close();

            SysFile sysFile = new SysFile();
            sysFile.setBusinessModule(request.getBusinessModule());
            sysFile.setOriginalName(file.getOriginalFilename().replace(",", "").trim());
            sysFile.setFileSize(file.getSize());
            sysFile.setContentType(file.getContentType());
            sysFile.setFileSuffix(FileUploadUtils.getSuffix(file));
            sysFile.setUrl("/" + fileName);
            sysFile.setFileName(FileUtils.getName(sysFile.getUrl()));
            sysFile.setFolderName(bucketName);
            baseDao.insert(sysFile);
            return sysFile;
        } catch (Exception e) {
            throw new ServiceException(GlobalStatusEnum.FILE_UPLOAD_FAIL.getMsg());
        }
    }

    @Override
    public void downloadFile(String id, HttpServletResponse response, boolean isToken) {
        String errorMsg = GlobalStatusEnum.FILE_DOWNLOAD_FAIL.getMsg();
        String bucketName = isToken ? minioConfig.getBucketName() : minioConfig.getBucketNameText();
        if(!isBuckExist(bucketName)) throw new ServiceException(ConstantForError.FILE_BUCKETNAME_ERROR);

        InputStream inputStream   = null;
        OutputStream outputStream = null;
        try {
            SysFile file = baseDao.selectById(id);
            if (StringUtils.isNull(file)) throw new ServiceException(GlobalStatusEnum.DATA_NOT_FOUND.getMsg());
            String fileName = file.getUrl();
            if (StringUtils.isBlank(fileName)) {
                response.setHeader("Content-type", "text/html;charset=UTF-8");
                OutputStream ps = response.getOutputStream();
                ps.write(errorMsg.getBytes("UTF-8"));
                throw new ServiceException(errorMsg);
            }
            // 获取文件对象
            outputStream = response.getOutputStream();
            inputStream = minioClient.getObject(GetObjectArgs.builder().bucket(bucketName).object(fileName).build());
            byte buf[] = new byte[1024];
            int length = 0;
            response.reset();
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName.substring(fileName.lastIndexOf("/") + 1), Constant.UTF8));
            response.setContentType("application/octet-stream");
            response.setCharacterEncoding(Constant.UTF8);
            // 输出文件
            while ((length = inputStream.read(buf)) > 0) {
                outputStream.write(buf, 0, length);
            }
            inputStream.close();
        } catch (Throwable ex) {
            response.setHeader("Content-type", "text/html;charset=UTF-8");
            try {
                OutputStream ps = response.getOutputStream();
                ps.write(errorMsg.getBytes("UTF-8"));
            } catch (IOException e){
                throw new ServiceException(errorMsg);
            }
        } finally {
            try {
                outputStream.close();
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e){
                throw new ServiceException(errorMsg);
            }
        }
    }

    @Override
    public void delete(List<String> fileNames) {
        String bucketName = minioConfig.getBucketName();
        if(!isBuckExist(bucketName)) throw new ServiceException(ConstantForError.FILE_BUCKETNAME_ERROR);
        try {
            for (int i = 0; i< fileNames.size(); i++) {
                minioClient.removeObject(RemoveObjectArgs.builder().bucket(bucketName).object(fileNames.get(0)).build());
            }
        } catch (Exception e) {
            throw new ServiceException("删除失败");
        }
    }

    @Override
    public void downloadZip(String ids, HttpServletResponse httpServletResponse, boolean isToken) {
        if (StringUtils.isBlank(ids)) throw new ServiceException("无可下载文件");
        List<String> idList = Arrays.asList(ids.split(","));
        List<SysFile> fileList = baseDao.selectBatchIds(idList);
        try {
            //被压缩文件流集合
            InputStream[] srcFiles = new InputStream[fileList.size()];
            //被压缩文件名称
            String[] srcFileNames = new String[fileList.size()];
            int i = 0;
            List<String> fileUrl = fileList.stream().map(SysFile::getUrl).collect(Collectors.toList());
            for (String url : fileUrl) {
                String subStr = minioConfig.getBucketName()+  "/";
                url = url.substring(url.indexOf(subStr) + subStr.length(), url.length());
                GetObjectArgs args = GetObjectArgs.builder().bucket(minioConfig.getBucketName()).object(url).build();
                InputStream inputStream = minioClient.getObject(args);
                if (inputStream == null) {
                    continue;
                }
                //塞入流数组中
                srcFiles[i] = inputStream;
                srcFileNames[i] = url;
                i++;
            }
            httpServletResponse.setCharacterEncoding("UTF-8");
            httpServletResponse.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("下载附件.zip", "UTF-8"));
            //多个文件压缩成压缩包返回
            ZipUtil.zip(httpServletResponse.getOutputStream(), srcFileNames, srcFiles);
        } catch (FileSystemException e) {
            throw new ServiceException(e.getMessage());
        } catch (ServerException e) {
            throw new ServiceException(e.getMessage());
        } catch (InsufficientDataException e) {
            throw new ServiceException(e.getMessage());
        } catch (ErrorResponseException e) {
            throw new ServiceException(e.getMessage());
        } catch (IOException e) {
            throw new ServiceException(e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            throw new ServiceException(e.getMessage());
        } catch (InvalidKeyException e) {
            throw new ServiceException(e.getMessage());
        } catch (InvalidResponseException e) {
            throw new ServiceException(e.getMessage());
        } catch (XmlParserException e) {
            throw new ServiceException(e.getMessage());
        } catch (InternalException e) {
            throw new ServiceException(e.getMessage());
        }
    }

}
