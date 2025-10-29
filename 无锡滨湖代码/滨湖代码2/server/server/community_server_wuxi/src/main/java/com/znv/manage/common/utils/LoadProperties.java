package com.znv.manage.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.Date;
import java.util.Properties;

@Slf4j
public class LoadProperties {
	
	public static Properties getProperties(String propName) {
		Properties prop = new Properties();
		try {
			File file = ResourceUtils.getFile("classpath:" + propName);

			try(InputStream in = new FileInputStream(file);
				BufferedReader bf = new BufferedReader(new InputStreamReader(in))){
				//prop.load(in);
				prop.load(bf);
			}
		} catch (Exception e) {
			log.error(e.toString());
		}
		return prop;
	}

	public static String getProperty(String propName, String key) {
		Properties prop = getProperties(propName);
		return prop.getProperty(key);
	}

	public static void updateProperties(String propName, String keyname,
			String keyvalue) {
		try {
			File file = ResourceUtils.getFile("classpath:" + propName);
			try(OutputStream fos = new FileOutputStream(file)){
				Properties prop = getProperties(propName);
				prop.setProperty(keyname, keyvalue);
				prop.store(fos, "modify at" + new Date().toString());// 保存键值对到文件中
			}
		} catch (IOException e) {
			log.error(e.toString());
		}finally {

		}
	}

	public static void main(String[] args) {
		String propName = "kafka.properties";
		LoadProperties.updateProperties(propName, "cursor.1", "3");
		System.out.println(LoadProperties.getProperty(propName, "cursor.1"));
	}
}