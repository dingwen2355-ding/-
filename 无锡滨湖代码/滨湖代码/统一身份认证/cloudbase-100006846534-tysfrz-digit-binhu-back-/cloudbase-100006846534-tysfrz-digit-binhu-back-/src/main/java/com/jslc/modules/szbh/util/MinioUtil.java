package com.jslc.modules.szbh.util;

import cn.hutool.core.io.FastByteArrayOutputStream;
import io.minio.*;
import org.springblade.core.log.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import com.jslc.common.config.MinioProperties;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lc
 */
@Component
public class MinioUtil {

    @Autowired
    private MinioProperties minioProperties;
    @Autowired
    private MinioClient minioClient;

    public void upload(MultipartFile multipartFile, String path) throws Exception{
            BucketExistsArgs bucketArgs = BucketExistsArgs.builder().bucket(minioProperties.getBucket()).build();
            // 检查bucket是否存在。
            boolean found = minioClient.bucketExists(bucketArgs);
            if (!found) {
                MakeBucketArgs build = MakeBucketArgs.builder().bucket(minioProperties.getBucket()).build();
                minioClient.makeBucket(build);
            }
            PutObjectArgs objectArgs = PutObjectArgs.builder()
                    .bucket(minioProperties.getBucket())
                    .object(path + multipartFile.getOriginalFilename())
                    .contentType(multipartFile.getContentType())
                    .stream(multipartFile.getInputStream(), multipartFile.getSize(), -1).build();
            minioClient.putObject(objectArgs);
    }

    public void deleteFile(String path) {
        RemoveObjectArgs build = RemoveObjectArgs.builder().bucket(minioProperties.getBucket()).object(path).build();
        try {
            minioClient.removeObject(build);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("删除失败");
        }
    }

    public void download(String path, HttpServletResponse res, String fileName) {
        GetObjectArgs objectArgs = GetObjectArgs.builder().bucket(minioProperties.getBucket()).object(path).build();
        try (GetObjectResponse response = minioClient.getObject(objectArgs)) {
            byte[] buf = new byte[1024];
            int len;
            try (FastByteArrayOutputStream os = new FastByteArrayOutputStream()) {
                while ((len = response.read(buf)) != -1) {
                    os.write(buf, 0, len);
                }
                os.flush();
                byte[] bytes = os.toByteArray();
                res.setCharacterEncoding("utf-8");
                // 设置强制下载不打开
                res.setContentType("application/force-download");
                res.addHeader("Content-Disposition", "attachment;fileName=" +fileName);
                try (ServletOutputStream stream = res.getOutputStream()) {
                    stream.write(bytes);
                    stream.flush();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("下载失败");
        }
    }
}
