package com.wxgis.topic.influx.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.apache.commons.net.ftp.*;
import java.io.*;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/ftp")
public class FtpController {

    @GetMapping("/test1")
    public String test() {
        log.info("===== test1");

        try {
            String server = "2.22.55.44"; // FTP服务器地址
            int port = 21; // FTP服务器端口号
            String user = "administrator"; // FTP登录用户名
            String pass = "YjgqpJ5F^F"; // FTP登录密码

//        String server = "124.70.203.9"; // FTP服务器地址
//        int port = 21; // FTP服务器端口号
//        String user = "Administrator"; // FTP登录用户名
//        String pass = "WODEhaoma110"; // FTP登录密码

            FTPClient ftpClient = new FTPClient();

            try {
                ftpClient.connect(server, port);
                ftpClient.login(user, pass);

                if (ftpClient.isConnected()) {
                    System.out.println("成功连接到FTP服务器");

                    // 设置被动模式
                    ftpClient.enterLocalPassiveMode();
                    //ftpClient.enterLocalActiveMode();

                    // 切换到指定目录
                    boolean success = ftpClient.changeWorkingDirectory("/");
                    if (!success) {
                        throw new RuntimeException("无法切换到指定目录");
                    } else {
                        System.out.println("已切换到指定目录");

                        // 列出当前目录下的所有文件
                        FTPFile[] files = ftpClient.listFiles();
                        for (FTPFile file : files) {
                            System.out.println(file.getName());
                        }

                        // 下载文件
                        File localFile = new File("1.txt.txt");
                        OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(localFile));
                        boolean downloadSuccess = ftpClient.retrieveFile("remote_filename.txt", outputStream);
                        outputStream.close();
                        if (downloadSuccess) {
                            System.out.println("文件下载完成");
                        } else {
                            System.err.println("文件下载失败");
                        }

                        // 上传文件
                        InputStream inputStream = new BufferedInputStream(new FileInputStream("local_filename.txt"));
                        boolean uploadSuccess = ftpClient.storeFile("remote_filename.txt", inputStream);
                        inputStream.close();
                        if (uploadSuccess) {
                            System.out.println("文件上传完成");
                        } else {
                            System.err.println("文件上传失败");
                        }
                    }
                } else {
                    System.err.println("未能连接到FTP服务器");
                }
            } finally {
                ftpClient.disconnect();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "ok";
    }

    @GetMapping("/test2")
    public String test2() {
        log.info("===== test2");

        try {
            String server = "2.22.55.44"; // FTP服务器地址
            int port = 9999; // FTP服务器端口号
            String user = "administrator"; // FTP登录用户名
            String pass = "YjgqpJ5F^F"; // FTP登录密码


//        String server = "124.70.203.9"; // FTP服务器地址
//        int port = 21; // FTP服务器端口号
//        String user = "Administrator"; // FTP登录用户名
//        String pass = "WODEhaoma110"; // FTP登录密码

            FTPClient ftpClient = new FTPClient();
            try {
                ftpClient.connect(server, port);
                ftpClient.login(user, pass);

                if (ftpClient.isConnected()) {
                    System.out.println("成功连接到FTP服务器");

                    // 设置被动模式
                    //ftpClient.enterLocalPassiveMode();
                    //ftpClient.enterLocalActiveMode();

                    // 切换到指定目录
                    boolean success = ftpClient.changeWorkingDirectory("/");
                    if (!success) {
                        throw new RuntimeException("无法切换到指定目录");
                    } else {
                        System.out.println("已切换到指定目录");

                        // 列出当前目录下的所有文件
                        FTPFile[] files = ftpClient.listFiles();
                        for (FTPFile file : files) {
                            System.out.println(file.getName());
                        }

                        // 文件下载
                        File localFile = new File("1.txt.txt");
                        OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(localFile));
                        boolean downloadSuccess = ftpClient.retrieveFile("remote_filename.txt", outputStream);
                        outputStream.close();
                        if (downloadSuccess) {
                            System.out.println("文件下载完成");
                        } else {
                            System.err.println("文件下载失败");
                        }

                        // 上传文件
                        InputStream inputStream = new BufferedInputStream(new FileInputStream("local_filename.txt"));
                        boolean uploadSuccess = ftpClient.storeFile("remote_filename.txt", inputStream);
                        inputStream.close();
                        if (uploadSuccess) {
                            System.out.println("文件上传完成");
                        } else {
                            System.err.println("文件上传失败");
                        }
                    }
                } else {
                    System.err.println("未能连接到FTP服务器");
                }
            } finally {
                ftpClient.disconnect();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "ok";
    }

    @GetMapping("/test3")
    public String test3() {
        log.info("===== test3");

        try {
            String server = "2.22.55.44"; // FTP服务器地址
            int port = 9999; // FTP服务器端口号
            String user = "administrator"; // FTP登录用户名
            String pass = "YjgqpJ5F^F"; // FTP登录密码


//        String server = "124.70.203.9"; // FTP服务器地址
//        int port = 21; // FTP服务器端口号
//        String user = "Administrator"; // FTP登录用户名
//        String pass = "WODEhaoma110"; // FTP登录密码

            FTPClient ftpClient = new FTPClient();
            try {
                ftpClient.connect(server, port);
                ftpClient.login(user, pass);

                if (ftpClient.isConnected()) {
                    System.out.println("成功连接到FTP服务器");

                    // 设置被动模式
                    //ftpClient.enterLocalPassiveMode();
                    //ftpClient.enterLocalActiveMode();

                    // 切换到指定目录
                    boolean success = ftpClient.changeWorkingDirectory("/");
                    if (!success) {
                        throw new RuntimeException("无法切换到指定目录");
                    } else {
                        System.out.println("已切换到指定目录");

                        // 列出当前目录下的所有文件
                        FTPFile[] files = ftpClient.listFiles();
                        for (FTPFile file : files) {
                            System.out.println(file.getName());
                        }

                        // 上传文件
                        InputStream inputStream = new BufferedInputStream(new FileInputStream("local_filename.txt"));
                        boolean uploadSuccess = ftpClient.storeFile("remote_filename.txt", inputStream);
                        inputStream.close();
                        if (uploadSuccess) {
                            System.out.println("文件上传完成");
                        } else {
                            System.err.println("文件上传失败");
                        }
                    }
                } else {
                    System.err.println("未能连接到FTP服务器");
                }
            } finally {
                ftpClient.disconnect();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "ok";
    }

}
