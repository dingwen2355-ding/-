package com.znv.manage.common.utils;

import org.springframework.web.multipart.MultipartFile;

public class MultipartUtil {

    public static boolean fileType(MultipartFile file) {
        if (file == null) {
            return true;
        }
        boolean flag = false;
        String type[] = {"jpg", "png", "xls", "xlsx"};
        String originalFileName = file.getOriginalFilename();
        String suffix = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
        for (int i = 0; i < type.length; i++) {
            if (type[i].equals(suffix)) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    public static boolean fileTypes(MultipartFile[] files) {
        if (files.length == 0) {
            return true;
        }
        boolean flag = false;
        String type[] = {"jpg", "png", "xls", "xlsx"};
        for (int j = 0; j < files.length; j++) {
            MultipartFile file = files[j];
            String originalFileName = file.getOriginalFilename();
            String suffix = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
            for (int i = 0; i < type.length; i++) {
                if (type[i].equals(suffix)) {
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                break;
            }
        }

        return flag;
    }

}
