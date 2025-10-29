/*
 *      Copyright (c) 2018-2028, Chill Zhuang All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 *  Redistributions of source code must retain the above copyright notice,
 *  this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *  Neither the name of the dreamlu.net developer nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
 *  Author: Chill 庄骞 (smallchill@163.com)
 */
package com.jslc.modules.szbh.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.aliyun.oss.ServiceException;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jslc.common.config.MinioProperties;
import com.jslc.common.utils.PdfUtils;
import com.jslc.modules.szbh.dto.BladeFileDTO;
import com.jslc.modules.szbh.entity.BhFile;
import com.jslc.modules.szbh.mapper.BhFileMapper;
import com.jslc.modules.szbh.service.IFileService;
import com.jslc.modules.szbh.util.MinioUtil;
import org.springblade.core.secure.utils.AuthUtil;
import org.springblade.core.tool.utils.Func;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 部门管理同步原始表 服务实现类
 *
 * @author BladeX
 * @since 2023-05-09
 */
@Service
public class FileServiceImpl extends ServiceImpl<BhFileMapper, BhFile> implements IFileService {

    @Value(value = "${file.uploadUrl}")
    private String UPLOAD_URL;
    @Value(value = "${file.downloadUrl}")
    private String DOWNLOAD_URL;
    @Autowired
    private MinioUtil minioUtil;
    @Autowired
    private MinioProperties minioProperties;

    private static final String FILE_SEPARATOR = "/";

//    @Override
//    public BhFile saveFile(MultipartFile multipartFile) throws IOException {
//        BhFile result = new BhFile();
//
//        //文件上传前的名称
//        String fileName = multipartFile.getOriginalFilename();
//        File file = new File(fileName);
//        OutputStream out = null;
//        try{
//            //获取文件流，以文件流的方式输出到新文件
//            out = new FileOutputStream(file);
//            byte[] ss = multipartFile.getBytes();
//            for(int i = 0; i < ss.length; i++){
//                out.write(ss[i]);
//            }
//        }catch(IOException e){
//            e.printStackTrace();
//        }finally {
//            if (out != null){
//                try {
//                    out.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//
//        //参数
//        Map<String, Object> map = new HashMap<>();
//        map.put("file", file);
//        //发送请求
//        String backResult = HttpRequest.post(UPLOAD_URL)
//                .header("Content-Type", "multipart/form-data")//头信息，多个头信息多次调用此方法即可
//                .form(map)//表单内容
//                .timeout(100000)//超时，毫秒
//                .execute().body();
//
//        // 操作完上的文件 需要删除在根目录下生成的文件
//        File f = new File(file.toURI());
//        if (f.delete()){
//            System.out.println("删除成功");
//        }else {
//            System.out.println("删除失败");
//        }
//
//        //判断提取返回结果
//        if (Func.isNotEmpty(backResult)) {
//            JSONObject json = JSONObject.parseObject(backResult);
//            if (json.get("code").toString().equals("200")) {
//                JSONObject jsonObject = JSONObject.parseObject(json.get("data").toString());
//                String name = jsonObject.getString("name");
//                String url = jsonObject.getString("url");
//
//                BhFile bhFile = new BhFile();
//                bhFile.setFileUrl(DOWNLOAD_URL + url);
//                bhFile.setFileName(fileName);
//                bhFile.setFilePath(url);
//                bhFile.setTenantId("000000");
//                bhFile.setCreateTime(LocalDateTime.now());
//                this.save(bhFile);
//
//                result = bhFile;
//            } else {
//                throw new RuntimeException(json.get("msg").toString());
//            }
//        } else {
//            throw new RuntimeException("请求失败");
//        }
//
//        return result;
//    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BhFile saveFile(MultipartFile file) throws IOException {
        String tenantId = AuthUtil.getTenantId();
        tenantId = StringUtils.isBlank(tenantId) ? "000000" : tenantId;
        String originalFilename = file.getOriginalFilename();

        String suffix = originalFilename.substring(originalFilename.lastIndexOf(".") + 1).toLowerCase();
        // 判断是否是pdf文件类型
        if (StrUtil.equals(suffix, "pdf")) {
            // 判断文件xss攻击
            boolean haveJavaScript = PdfUtils.containsJavaScript(PdfUtils.multipartFileToFile(file));
            if (haveJavaScript) {
                throw new ServiceException("对不起,您上传的文件[" + originalFilename + "]包含xss脚本代码!");
            }
        }

        String path = UUID.randomUUID() + FILE_SEPARATOR;
        BhFile result = new BhFile();
        try {
            minioUtil.upload(file, path);
        } catch (Exception e) {
            throw new ServiceException("上传失败");
        }
        result.setFilePath(path + originalFilename);
        result.setFileUrl(minioProperties.getPrefix() + path + originalFilename);
        result.setFileName(originalFilename);
        result.setCreateTime(LocalDateTime.now());
        result.setTenantId(tenantId);
//        result.setSize(FileOperateUtil.getSize(file.getSize()));

//        bhFile.setFileUrl(DOWNLOAD_URL + url);
//        bhFile.setFileName(fileName);
//        bhFile.setFilePath(url);
//        bhFile.setTenantId("000000");
//        bhFile.setCreateTime(LocalDateTime.now());

        try {
            this.save(result);
        } catch (Exception e) {
            minioUtil.deleteFile(path + originalFilename);
            throw e;
        }
        return result;
    }


    @Override
    public BhFile uploadImg(MultipartFile file) throws IOException {
        String tenantId = AuthUtil.getTenantId();
        tenantId = StringUtils.isBlank(tenantId) ? "000000" : tenantId;
        String originalFilename = file.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf(".") + 1).toLowerCase();
        if (!suffix.equals("png") && !suffix.equals("jpg")){
            throw new ServiceException("上传失败 请上传.png，.jpg结尾的文件");
        }
        String path = UUID.randomUUID() + FILE_SEPARATOR;
        BhFile result = new BhFile();
        try {
            minioUtil.upload(file, path);
        } catch (Exception e) {
            throw new ServiceException("上传失败");
        }
        result.setFilePath(path + originalFilename);
        result.setFileUrl(minioProperties.getPrefix() + path + originalFilename);
        result.setFileName(originalFilename);
        result.setCreateTime(LocalDateTime.now());
        result.setTenantId(tenantId);
//        result.setSize(FileOperateUtil.getSize(file.getSize()));

//        bhFile.setFileUrl(DOWNLOAD_URL + url);
//        bhFile.setFileName(fileName);
//        bhFile.setFilePath(url);
//        bhFile.setTenantId("000000");
//        bhFile.setCreateTime(LocalDateTime.now());

        try {
            this.save(result);
        } catch (Exception e) {
            minioUtil.deleteFile(path + originalFilename);
            throw e;
        }
        return result;
    }
}
