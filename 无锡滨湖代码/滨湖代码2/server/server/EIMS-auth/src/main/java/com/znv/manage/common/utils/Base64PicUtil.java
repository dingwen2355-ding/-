package com.znv.manage.common.utils;

import lombok.extern.slf4j.Slf4j;
import sun.misc.BASE64Decoder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.UUID;
@Slf4j
public class Base64PicUtil {

    /**
     * base64照片处理
     * @param base64String base64字符串
     * @param cardNo 证件信息，用于生成图片名称
     * @param targetFilePath 保存路径
     * @param serverAddress 服务器url，用于生成返回的ftp访问路径
     * @return
     */
    public static String savePhoto(String base64String, String cardNo, String targetFilePath, String serverAddress){

        String dataPrix = ""; //base64格式前头
        String data = "";//实体部分数据
        if(base64String==null||"".equals(base64String)){
            // 错误
        }else {
            String [] d = base64String.split("base64,");//将字符串分成数组
            if(d != null && d.length == 2){
                dataPrix = d[0];
                data = d[1];
            }else {
                // 错误
            }
        }
        String suffix = "";//图片后缀，用以识别哪种格式数据
        if("data:image/jpeg;".equalsIgnoreCase(dataPrix)){
            //data:image/jpeg;base64,base64编码的jpeg图片数据
            suffix = ".jpg";
        }else if("data:image/x-icon;".equalsIgnoreCase(dataPrix)){
            //data:image/x-icon;base64,base64编码的icon图片数据
            suffix = ".ico";
        }else if("data:image/gif;".equalsIgnoreCase(dataPrix)){
            //data:image/gif;base64,base64编码的gif图片数据
            suffix = ".gif";
        }else if("data:image/png;".equalsIgnoreCase(dataPrix)){
            //data:image/png;base64,base64编码的png图片数据
            suffix = ".png";
        }else {
            // 错误
        }
        String tempFileName=cardNo + suffix;
        String imgFilePath = targetFilePath +tempFileName;//新生成的图片
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            //Base64解码
            byte[] b = decoder.decodeBuffer(data);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    //调整异常数据
                    b[i] += 256;
                }
            }
            try(OutputStream out = new FileOutputStream(imgFilePath)) {
                out.write(b);
                out.flush();
            }
            return serverAddress + tempFileName;
        } catch (Exception e){
            log.error(e.getMessage());
            return null;
        }
    }
    
	/**
	 * 删除图片
	 * 
	 * @return
	 */
	public static boolean deletePhoto(String fileName, String targetFilePath) {
		String filePath = targetFilePath + fileName;
		try {
			File fileTemp = new File(filePath);

			// 判断文件是否存在
			boolean flag = false;

			flag = fileTemp.exists();
			if (flag) {
				boolean f =fileTemp.delete();
				if (f){
				    log.info("fileTemp.delete();");
                }
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
