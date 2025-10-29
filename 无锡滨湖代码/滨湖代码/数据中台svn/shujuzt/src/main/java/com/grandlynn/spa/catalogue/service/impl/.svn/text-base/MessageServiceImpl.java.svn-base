package com.grandlynn.spa.catalogue.service.impl;

import com.grandlynn.spa.catalogue.dto.MessageDTO;
import com.grandlynn.spa.catalogue.service.MessageService;
import com.grandlynn.spa.catalogue.webSocket.MessageWebSocket;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService {
   public void sendMessage(MessageDTO messageDTO) {
      MessageWebSocket.sendObjectMessageByClientId(messageDTO.getUserId().toString(), messageDTO);
   }
}
