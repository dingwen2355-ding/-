package com.grandlynn.spa.catalogue.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.DispatcherServlet;

public class MyDispatcherServlet extends DispatcherServlet {
   private static final Logger log = LoggerFactory.getLogger(MyDispatcherServlet.class);

   protected void doDispatch(HttpServletRequest request, HttpServletResponse response) {
      try {
         super.doDispatch(new MyHttpServletRequestWrapper(request), response);
      } catch (Exception var4) {
         log.error("MyDispatcherServlet error", var4.getMessage());
      }

   }
}
