//package com.grandlynn.util.impl.id;
//
//import java.text.SimpleDateFormat;
//
//public class NumberId {
//   public static int Guid = 100;
//
////   public static void main(String[] args) {
////      System.out.println(getNumberId());
////      System.out.println(getNumberId());
////      System.out.println(getNumberId());
////   }
//
//   public static String getNumberId() {
//      ++Guid;
//      long now = System.currentTimeMillis();
//      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
//      String time = dateFormat.format(now);
//      String info = now + "";
//      if (Guid > 999) {
//         Guid = 100;
//      }
//
//      int ran = Guid;
//      return time + info.substring(2, info.length()) + ran;
//   }
//}
