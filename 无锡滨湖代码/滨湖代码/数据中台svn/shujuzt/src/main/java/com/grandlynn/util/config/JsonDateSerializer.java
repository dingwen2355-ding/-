package com.grandlynn.util.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class JsonDateSerializer extends JsonSerializer<Date> {
   private static final SimpleDateFormat dateFormat;
   private static String DATE_DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";

   public void serialize(Date date, JsonGenerator gen, SerializerProvider provider) throws IOException {
      String formattedDate = dateFormat.format(date);
      gen.writeString(formattedDate);
   }

   static {
      dateFormat = new SimpleDateFormat(DATE_DEFAULT_PATTERN);
   }
}
