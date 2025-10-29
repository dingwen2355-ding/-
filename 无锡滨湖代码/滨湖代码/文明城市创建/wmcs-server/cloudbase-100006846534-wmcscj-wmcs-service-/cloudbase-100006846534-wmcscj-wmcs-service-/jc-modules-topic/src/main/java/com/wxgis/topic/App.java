package com.wxgis.topic;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.wxgis.topic.constants.WarnTypeConstant;
import com.wxgis.topic.influx.config.TiConfig;
import com.wxgis.topic.influx.util.JsonUtils;
import com.wxgis.topic.vo.*;
import com.wxgis.topic.vo.Object;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class App {

	public static String md5(String str) {
		byte[] digest = null;
		try {
			MessageDigest md5 = MessageDigest.getInstance("md5");
			digest = md5.digest(str.getBytes("utf-8"));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String md5Str = new BigInteger(1, digest).toString(16);
		return md5Str;
	}

	public static void main(String[] args) throws Exception {
		ManagedChannel channel = ManagedChannelBuilder.forAddress(TiConfig.ip, TiConfig.port).usePlaintext().build();
		ReadMsgGrpc.ReadMsgBlockingStub blockingStub = ReadMsgGrpc.newBlockingStub(channel);

		String username = TiConfig.username;
		String secretId = TiConfig.secretId;
		String secretKey = TiConfig.secretKey;
		String timestamp = TiConfig.timestamp;
//		String topic = TiConfig.topic2;
		String topic = TiConfig.topic1;

		String Signature = md5(md5(secretKey) + timestamp + secretId);
		TiMatrixOfficialAppSubscribeSvr.Msg msg = TiMatrixOfficialAppSubscribeSvr.Msg.newBuilder().setUserName(username).setSecretId(secretId).setSignature(Signature)
				.setTimestamp(timestamp).addTopics(topic).setGroupId("group_id_" + timestamp).setReadHistory(true)
				.build();
		Iterator<TiMatrixOfficialAppSubscribeSvr.Msg> iterator = blockingStub.readTopic(msg);
		while (iterator.hasNext()) {
			TiMatrixOfficialAppSubscribeSvr.Msg msgData = iterator.next();
			// 解析buff的数据
			// 解析buff的数据
			ByteBuffer buffer = msgData.getBuffer().asReadOnlyByteBuffer();
			Charset charset = Charset.forName("UTF-8");
			CharsetDecoder decoder = charset.newDecoder();
			CharBuffer charBuffer = decoder.decode(ByteBuffer.wrap(msgData.getBuffer().toByteArray()));
			buffer.flip();
			// 转buff实体类
			BufferData data = JSONObject.parseObject(charBuffer.toString(), BufferData.class);
			ResultData resultData = data.getData();
			if (resultData.getAlarmEvent().equals("4006")) {
				System.out.println(JSON.toJSONString(resultData));
			}
//			String alarmInfo[] = {WarnTypeConstant.jdcwt, WarnTypeConstant.fjdcwt,
//					WarnTypeConstant.ljtmy, WarnTypeConstant.dw, WarnTypeConstant.wgls, WarnTypeConstant.ztxgg, WarnTypeConstant.ljbl, WarnTypeConstant.sldj, WarnTypeConstant.ltsb};
//			if (Arrays.binarySearch(alarmInfo, resultData.getAlarmEvent()) < 0) continue;
			System.out.println("======事发时间：" + resultData.getCaptureTime());
			System.out.println(JSON.toJSONString(resultData));
			if (!StringUtils.isEmpty(resultData.getAlarmInfo())) {
				Map<String, String> alarmInfoKey = resultData.getAlarmInfo();
				String temp = alarmInfoKey.get(resultData.getAlarmEvent());
				Map<String, String> tempMap = JsonUtils.jsonToStringMap(temp);
				if (null == tempMap) {
					continue;
				}
				Alarm alarm = new Alarm();
				alarm.setText(tempMap.get("Text"));
				StringBuffer stringBuffer = new StringBuffer();
				stringBuffer.append(tempMap.get("Text"));
				resultData.setAlarm(alarm);
			}
			resultData.setOverlying(JSONObject.parseObject(resultData.getOverlyingInfo(), Overlying.class));
			String dataStr = JSON.toJSONString(data);
			System.out.println(dataStr);
		}
	}

}
