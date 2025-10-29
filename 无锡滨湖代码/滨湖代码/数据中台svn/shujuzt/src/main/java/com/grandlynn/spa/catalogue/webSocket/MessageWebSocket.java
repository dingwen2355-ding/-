package com.grandlynn.spa.catalogue.webSocket;

import com.grandlynn.spa.catalogue.webSocket.encoder.ServerEncoder;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import javax.websocket.CloseReason;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@ServerEndpoint(
   value = "/websocket/{clientId}",
   encoders = {ServerEncoder.class},
   subprotocols = {"protocol"}
)
public class MessageWebSocket {
   private static final Logger log = LoggerFactory.getLogger(MessageWebSocket.class);
   private static AtomicInteger onlineCount = new AtomicInteger(0);
   private static Map<String, Session> webSocketMap = new ConcurrentHashMap();

   @OnOpen
   public void onOpen(@PathParam("clientId") String clientId, Session session) {
      if (!webSocketMap.containsKey(clientId)) {
         addOnlineCount();
      }

      webSocketMap.put(clientId, session);
      System.out.println(new Date() + "WebSocket有新连接加入！当前在线人数为" + getOnlineCount());
   }

   @OnClose
   public void onClose(@PathParam("clientId") String clientId, Session session, CloseReason closeReason) {
      if (webSocketMap.containsKey(clientId) && ((Session)webSocketMap.get(clientId)).getId().equals(session.getId())) {
         subOnlineCount();
      }

      webSocketMap.remove(clientId, session);
      System.out.println(new Date() + "WebSocket有一连接关闭！当前在线人数为" + getOnlineCount());
   }

   @OnMessage
   public void onMessage(@PathParam("clientId") String clientId, String message, Session session) {
      System.out.println("WebSocket收到来自客户端的消息:" + message);
      sendMessageByClientId(clientId, message);
   }

   private String getWebSocketMapKey(String clientId, Session session) {
      return StringUtils.isEmpty(clientId) ? session.getId() : clientId + "_" + session.getId();
   }

   @OnError
   public void onError(Session session, Throwable error) {
      System.out.println("WebSocket发生错误");
   }

   public static void doSend(String message) {
      if (webSocketMap.size() > 0) {
         Iterator var1 = webSocketMap.entrySet().iterator();

         while(var1.hasNext()) {
            Map.Entry<String, Session> entry = (Map.Entry)var1.next();

            try {
               sendMessage((Session)entry.getValue(), message);
            } catch (IOException var4) {
               System.out.println("WebSocket doSend is error:");
            }
         }
      }

   }

   public static void sendMessage(Session session, String message) throws IOException {
      session.getBasicRemote().sendText(message);
   }

   public static void sendMessageBYObject(Session session, Object obj) throws IOException, EncodeException {
      session.getBasicRemote().sendObject(obj);
   }

   public static int sendMessageByClientIdList(List<String> clientIdList, String message) {
      int status = 0;

      String clientId;
      for(Iterator var3 = clientIdList.iterator(); var3.hasNext(); status = sendMessageByClientId(clientId, message)) {
         clientId = (String)var3.next();
      }

      return status;
   }

   public static int sendMessageByClientId(String clientId, String message) {
      int status = 0;
      if (webSocketMap.size() > 0) {
         Iterator var3 = webSocketMap.entrySet().iterator();

         while(var3.hasNext()) {
            Map.Entry<String, Session> entry = (Map.Entry)var3.next();

            try {
               String key = (String)entry.getKey();
               if (key.equals(clientId)) {
                  sendMessage((Session)entry.getValue(), message);
                  status = 200;
               }
            } catch (IOException var6) {
               System.out.println("WebSocket doSend is error:");
            }
         }
      }

      return status;
   }

   public static void sendSpeechMessageByClientId(String clientId, String message) {
      if (webSocketMap.size() > 0) {
         Iterator var2 = webSocketMap.entrySet().iterator();

         while(var2.hasNext()) {
            Map.Entry<String, Session> entry = (Map.Entry)var2.next();

            try {
               String key = (String)entry.getKey();
               if (key.equals(clientId)) {
                  sendMessage((Session)entry.getValue(), message);
               }
            } catch (IOException var5) {
               System.out.println("WebSocket doSend is error:");
            }
         }
      }

   }

   public static void sendObjectMessageByClientId(String clientId, Object obj) {
      if (webSocketMap.size() > 0) {
         Iterator var2 = webSocketMap.entrySet().iterator();

         while(var2.hasNext()) {
            Map.Entry<String, Session> entry = (Map.Entry)var2.next();

            try {
               String key = (String)entry.getKey();
               if (key.equals(clientId)) {
                  sendMessageBYObject((Session)entry.getValue(), obj);
               }
            } catch (IOException | EncodeException var5) {
               var5.printStackTrace();
            }
         }
      }

   }

   public static synchronized AtomicInteger getOnlineCount() {
      return onlineCount;
   }

   public static synchronized void addOnlineCount() {
      onlineCount.getAndIncrement();
   }

   public static synchronized void subOnlineCount() {
      onlineCount.getAndDecrement();
   }
}
