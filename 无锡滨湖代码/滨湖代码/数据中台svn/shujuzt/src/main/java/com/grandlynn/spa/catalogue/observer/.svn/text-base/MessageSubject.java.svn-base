package com.grandlynn.spa.catalogue.observer;

import com.grandlynn.spa.catalogue.domain.request.SmsRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class MessageSubject implements ApplicationContextAware {
   private ApplicationContext applicationContext;
   private List<MessageObserver> listObserver = new ArrayList();
   private ExecutorService executorService = Executors.newFixedThreadPool(10);

   public void addObServer(MessageObserver obServer) {
      this.listObserver.add(obServer);
   }

   public Map<String, String> notifyObServer(SmsRequest smsRequest) {
      Map<String, String> map = new HashMap();
      Iterator var3 = this.listObserver.iterator();

      while(var3.hasNext()) {
         MessageObserver obServer = (MessageObserver)var3.next();
         Class<? extends MessageObserver> aClass = obServer.getClass();
         String name = aClass.getSimpleName();
         String sendGet = obServer.sendMsg(smsRequest);
         map.put(obServer.getClass().getSimpleName(), sendGet);
      }

      return map;
   }

   public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
      this.applicationContext = applicationContext;
   }
}
