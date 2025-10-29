//package com.grandlynn.util.impl.reflection;
//
//
//import com.grandlynn.util.CollectionUtil;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//public class ClassUtil {
//   private static final Map<String, PrimitiveInfo<?>> PRIMITIVES = CollectionUtil.createHashMap();
//
//   private static <T> void addPrimitive(Class<T> type, String typeCode, Class<T> wrapperType, String unwrapMethod, T defaultValue) {
//      PrimitiveInfo<T> info = new PrimitiveInfo(type, typeCode, wrapperType, unwrapMethod, defaultValue);
//      PRIMITIVES.put(type.getName(), info);
//      PRIMITIVES.put(wrapperType.getName(), info);
//   }
//
//   public static <T> T getPrimitiveDefaultValue(Class<T> type) {
//      if (type == null) {
//         return null;
//      } else {
//         PrimitiveInfo<T> info = (PrimitiveInfo)PRIMITIVES.get(type.getName());
//         return info != null ? info.defaultValue : null;
//      }
//   }
//
//   public static boolean checkClassType(Class clazz1, Class clazz2) {
//      boolean result = clazz1 == clazz2;
//      if (!result) {
//         result = clazz1.equals(clazz2);
//      }
//
//      if (!result) {
//         result = clazz1.isAssignableFrom(clazz2);
//      }
//
//      if (!result) {
//         result = clazz2.isAssignableFrom(clazz1);
//      }
//
//      return result;
//   }
//
//   public static void main(String[] args) {
//      Class clazz1 = List.class;
//      Class clazz2 = ArrayList.class;
//      System.out.println(clazz1 != clazz2);
//      System.out.println(checkClassType(clazz1, clazz2));
//   }
//
//   static {
//      addPrimitive(Boolean.TYPE, "Z", Boolean.class, "booleanValue", false);
//      addPrimitive(Short.TYPE, "S", Short.class, "shortValue", Short.valueOf((short)0));
//      addPrimitive(Integer.TYPE, "I", Integer.class, "intValue", 0);
//      addPrimitive(Long.TYPE, "J", Long.class, "longValue", 0L);
//      addPrimitive(Float.TYPE, "F", Float.class, "floatValue", 0.0F);
//      addPrimitive(Double.TYPE, "D", Double.class, "doubleValue", 0.0);
//      addPrimitive(Character.TYPE, "C", Character.class, "charValue", '\u0000');
//      addPrimitive(Byte.TYPE, "B", Byte.class, "byteValue", (byte)0);
//      addPrimitive(Void.TYPE, "V", Void.class, (String)null, null);
//   }
//
//   private static class PrimitiveInfo<T> {
//      final Class<T> type;
//      final String typeCode;
//      final Class<T> wrapperType;
//      final String unwrapMethod;
//      final T defaultValue;
//
//      public PrimitiveInfo(Class<T> type, String typeCode, Class<T> wrapperType, String unwrapMethod, T defaultValue) {
//         this.type = type;
//         this.typeCode = typeCode;
//         this.wrapperType = wrapperType;
//         this.unwrapMethod = unwrapMethod;
//         this.defaultValue = defaultValue;
//      }
//   }
//}
