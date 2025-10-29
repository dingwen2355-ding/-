package com.grandlynn.spa.catalogue.config;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import org.springframework.util.StreamUtils;

public class MyHttpServletRequestWrapper extends HttpServletRequestWrapper {
   private byte[] body;

   public MyHttpServletRequestWrapper(HttpServletRequest request) throws IOException {
      super(request);
      this.body = StreamUtils.copyToByteArray(request.getInputStream());
   }

   public ServletInputStream getInputStream() throws IOException {
      final InputStream bodyStream = new ByteArrayInputStream(this.body);
      return new ServletInputStream() {
         public int read() throws IOException {
            return bodyStream.read();
         }

         public boolean isFinished() {
            return false;
         }

         public boolean isReady() {
            return true;
         }

         public void setReadListener(ReadListener readListener) {
         }
      };
   }

   public BufferedReader getReader() throws IOException {
      return new BufferedReader(new InputStreamReader(this.getInputStream()));
   }
}
