package com.grandlynn.util;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class StringUtil {
   public static String addSQuote(String str) {
      String result = "'";
      if (str.indexOf(",") > 0) {
         String tmp = str.replace(",", "','");
         result = result + tmp + "'";
      } else {
         result = result + str + "'";
      }

      return result;
   }

   public static String setToString(Set<?> set, String split) {
      return set != null && !set.isEmpty() ? listToString(Collections.list(Collections.enumeration(set)), split) : "";
   }

   public static String listToString(List<?> list, String split) {
      StringBuffer sb = new StringBuffer("");
      if (list != null && list.size() > 0) {
         for(int i = 0; i < list.size(); ++i) {
            if (list.get(i) != null && list.get(i) != "") {
               if (list.get(i) instanceof List) {
                  sb.append(listToString((List)list.get(i), split));
                  sb.append(split);
               } else {
                  sb.append(list.get(i));
                  sb.append(split);
               }
            }
         }
      }

      return sb.toString();
   }

   public static String filterQueryValue(String str) {
      return str.replaceAll(".*([';]+|(--)+).*", " ");
   }

   public static String parseFromException(Exception e) {
      String result = "";
      ByteArrayOutputStream out = new ByteArrayOutputStream();
      PrintStream ps = new PrintStream(out);
      e.printStackTrace(ps);
      result = new String(out.toByteArray());
      return result;
   }

   public static String getRandomString(int length) {
      String base = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
      Random random = new Random();
      StringBuffer sb = new StringBuffer();

      for(int i = 0; i < length; ++i) {
         int number = random.nextInt(base.length());
         sb.append(base.charAt(number));
      }

      return sb.toString();
   }

   public static boolean isBlank(CharSequence cs) {
      int strLen;
      if (cs != null && (strLen = cs.length()) != 0) {
         for(int i = 0; i < strLen; ++i) {
            if (!Character.isWhitespace(cs.charAt(i))) {
               return false;
            }
         }

         return true;
      } else {
         return true;
      }
   }

   public static boolean isNotBlank(CharSequence cs) {
      return !isBlank(cs);
   }

   public static boolean isEmpty(CharSequence cs) {
      return cs == null || cs.length() == 0;
   }

   public static boolean isNotEmpty(CharSequence cs) {
      return !isEmpty(cs);
   }

   public static String toLowerCaseFirstOne(String s) {
      return Character.isLowerCase(s.charAt(0)) ? s : Character.toLowerCase(s.charAt(0)) + s.substring(1);
   }

   public static String toUpperCaseFirstOne(String s) {
      return Character.isUpperCase(s.charAt(0)) ? s : Character.toUpperCase(s.charAt(0)) + s.substring(1);
   }

   public static void main(String[] args) {
      try {
         System.out.println(toLowerCaseFirstOne("ADFSD"));
         System.out.println(toUpperCaseFirstOne("dadfad"));
         System.out.println(getRandomString(12));
      } catch (Exception var2) {
         System.out.println(parseFromException(var2));
      }

   }
}
