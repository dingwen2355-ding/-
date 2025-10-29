package com.grandlynn.spa.catalogue.observer.start;

import com.grandlynn.spa.catalogue.observer.MessageObserver;
import com.grandlynn.spa.catalogue.observer.MessageSubject;
import java.util.Iterator;
import java.util.Map;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class StartService implements ApplicationRunner, ApplicationContextAware {
   @Autowired
   private MessageSubject subject;
   private ApplicationContext applicationContext;

   public void run(ApplicationArguments args) throws Exception {
      Map<String, MessageObserver> beansOfType = this.applicationContext.getBeansOfType(MessageObserver.class);
      Iterator var3 = beansOfType.keySet().iterator();

      while(var3.hasNext()) {
         String key = (String)var3.next();
         MessageObserver obServer = (MessageObserver)beansOfType.get(key);
         this.subject.addObServer(obServer);
      }

   }

   public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
      this.applicationContext = applicationContext;
   }
}
