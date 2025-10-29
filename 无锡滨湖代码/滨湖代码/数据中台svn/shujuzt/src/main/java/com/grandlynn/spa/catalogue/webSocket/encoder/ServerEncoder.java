package com.grandlynn.spa.catalogue.webSocket.encoder;

import com.alibaba.fastjson.JSON;
import com.grandlynn.spa.catalogue.dto.MessageDTO;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class ServerEncoder implements Encoder.Text<MessageDTO> {
   public String encode(MessageDTO messageDTO) throws EncodeException {
      return JSON.toJSONString(messageDTO);
   }

   public void init(EndpointConfig endpointConfig) {
   }

   public void destroy() {
   }
}
