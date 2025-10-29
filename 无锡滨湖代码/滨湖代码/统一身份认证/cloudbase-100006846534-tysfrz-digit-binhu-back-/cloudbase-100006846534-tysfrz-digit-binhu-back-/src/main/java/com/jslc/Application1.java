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
package com.jslc;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 启动器
 *
 * @author Chill
 */
//@EnableScheduling
//@SpringBootApplication()
//@MapperScan({"org.springblade.**.mapper.**", "com.jslc.**.mapper.**"})
//@ComponentScan(value = {"org.springblade","com.jslc"})
public class Application1 {

	public static void main1111(String[] args) {
	//	BladeApplication.run(CommonConstant.APPLICATION_NAME, Application.class, args);

		Snowflake snowflake = IdUtil.createSnowflake(1, 9);
// 有两种返回值类型
		long id = snowflake.nextId();
		for (int i = 0 ;i <20 ;i++){
			System.out.println(snowflake.nextId());
		}

		//BladeRedis bladeRedis = SpringUtil.getBean(BladeRedis.class);
		/*String strIds = "1347436929515380738,1400664846529224706,1400664974866538498,1407997667383980033,1347450870635290626,1437616205597814786,1452465787976597506,1471295200981794817,1471363443201261569,1437617374101549057,1462977674669469698,1437617516741439490,1462977574253637634," +
				"1471310650612498433,1437617629337530370,1452465975814258690,1462977278819446786,1462977481882480642,1452465645030522882";*/

		/*String strIds = "1400664846529224706";
		DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
		String[] ids = strIds.split(",");
		for (String id : ids) {
			LocalDateTime localDateTime = LocalDateTime.of(2023,10,17,17,0,0);
			JSONObject jsonObject = new JSONObject();
			for(int i =1 ;i <15 ;i++){
				jsonObject.put("bizId",id);
				jsonObject.put("endTime",localDateTime.plusHours(i+1));
				jsonObject.put("execNum",0);
				jsonObject.put("execStat","1");
				jsonObject.put("id","1705783098232274946");
				jsonObject.put("st","95");
				jsonObject.put("startTime",localDateTime.plusHours(i));
				jsonObject.put("subTaskType",1);
				jsonObject.put("taskType",1);
				bladeRedis.setEx("task:hourlyReport:95:"+fmt.format(localDateTime.plusHours(i+1))+":"+id,jsonObject.toJSONString(),60*60L);
			}
		}*/

		/*JSONObject jsonObject = new JSONObject();
		jsonObject.put("bizId","1452465787976597506");
		jsonObject.put("endTime","2023-10-16T19:00:00");
		jsonObject.put("execNum",0);
		jsonObject.put("execStat","1");
		jsonObject.put("id","1705783098232274946");
		jsonObject.put("st","22");
		jsonObject.put("startTime","2023-10-16T18:00:00");
		jsonObject.put("subTaskType",1);
		jsonObject.put("taskType",1);


		JSONObject jsonObject2 = new JSONObject();
		jsonObject2.put("bizId","1452465787976597506");
		jsonObject2.put("endTime","2023-10-16T14:00:00");
		jsonObject2.put("execNum",0);
		jsonObject2.put("execStat","1");
		jsonObject2.put("id","1705783098232274946");
		jsonObject2.put("st","22");
		jsonObject2.put("startTime","2023-10-16T13:00:00");
		jsonObject2.put("subTaskType",1);
		jsonObject2.put("taskType",1);


		JSONObject jsonObject3 = new JSONObject();
		jsonObject3.put("bizId","1452465787976597506");
		jsonObject3.put("endTime","2023-10-16T22:00:00");
		jsonObject3.put("execNum",0);
		jsonObject3.put("execStat","1");
		jsonObject3.put("id","1705783098232274946");
		jsonObject3.put("st","22");
		jsonObject3.put("startTime","2023-10-16T21:00:00");
		jsonObject3.put("subTaskType",1);
		jsonObject3.put("taskType",1);

		bladeRedis.setEx("task:hourlyReport:22:2023101614:1452465787976597506",jsonObject.toJSONString(),60*60L);
		bladeRedis.setEx("task:hourlyReport:22:2023101619:1471363443201261569",jsonObject2.toJSONString(),60*60L);*/
		//bladeRedis.setEx("task:hourlyReport:95:2023101623:1471363443201261569",jsonObject3.toJSONString(),60*60L);

		//System.out.println(bladeRedis);

	}
}

