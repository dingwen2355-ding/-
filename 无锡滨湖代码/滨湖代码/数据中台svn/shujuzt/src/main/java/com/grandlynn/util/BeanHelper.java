//package com.grandlynn.util;
//
//import java.lang.reflect.Field;
//import java.math.BigDecimal;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.Map;
//import org.apache.commons.lang3.time.DateFormatUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//public class BeanHelper {
//   private static final String DATE_DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";
//   private static final String[] DATE_PARSE_PATTERNS = new String[]{"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm"};
//   protected static Logger logger = LoggerFactory.getLogger(BeanHelper.class);
//
//   private BeanHelper() {
//   }
//
//   /** @deprecated */
//   @Deprecated
//   public static <T> T mapPartOverrider(Object dest, Object orig) {
//      return copyProperties(dest, orig, true);
//   }
//
//   public static <T> T mapPartOverriderV2(Object orig, Object dest) {
//      return copyProperties(dest, orig, true);
//   }
//
//   /** @deprecated */
//   @Deprecated
//   public static <T> T mapCompleteOverrider(Object dest, Object orig) {
//      return copyProperties(dest, orig, false);
//   }
//
//   public static <T> T mapCompleteOverriderV2(Object orig, Object dest) {
//      return copyProperties(dest, orig, false);
//   }
//
//   public static <T> T copyProperties(Object dest, Object orig) {
//      return copyProperties(dest, orig, false);
//   }
//
//   private static <T> T copyProperties(Object dest, Object orig, boolean no_overrider) {
//      Map<String, BeanField> origFieldMap = new HashMap();
//      Map<String, BeanField> destFieldMap = new HashMap();
//      if (orig != null && dest != null) {
//         origFieldMap.clear();
//         destFieldMap.clear();
//         Field f;
//         BeanField beanField;
//         Class clazz;
//         Field[] destFields;
//         Field[] var19;
//         int var21;
//         int var23;
//         if (orig instanceof Map) {
//            Map origMap = (Map)orig;
//            Iterator var6 = origMap.entrySet().iterator();
//
//            while(var6.hasNext()) {
//               Map.Entry entry = (Map.Entry)var6.next();
//               String name = entry.getKey().toString();
//               beanField = BeanField.getBeanField(name, origMap);
//               if (beanField != null) {
//                  origFieldMap.put(name, beanField);
//               }
//            }
//         } else {
//            for(clazz = orig.getClass(); clazz != null && clazz != Object.class; clazz = clazz.getSuperclass()) {
//               destFields = clazz.getDeclaredFields();
//               var19 = destFields;
//               var21 = destFields.length;
//
//               for(var23 = 0; var23 < var21; ++var23) {
//                  f = var19[var23];
//                  beanField = BeanField.getBeanField(f, orig, true);
//                  if (beanField != null && !origFieldMap.containsKey(f.getName())) {
//                     origFieldMap.put(f.getName(), beanField);
//                  }
//               }
//            }
//         }
//
//         Iterator<Map.Entry<String, BeanField>> var16;
//         Map.Entry<String, BeanField> entry;
//         BeanField origField;
//         if (dest instanceof Map) {
//            var16 = origFieldMap.entrySet().iterator();
//
//            while(var16.hasNext()) {
//               entry = var16.next();
//               origField = BeanField.copyBeanField((BeanField)entry.getValue(), (Map)dest);
//               if (origField != null) {
//                  destFieldMap.put(entry.getKey(), origField);
//               }
//            }
//         } else {
//            for(clazz = dest.getClass(); clazz != null && clazz != Object.class; clazz = clazz.getSuperclass()) {
//               destFields = clazz.getDeclaredFields();
//               var19 = destFields;
//               var21 = destFields.length;
//
//               for(var23 = 0; var23 < var21; ++var23) {
//                  f = var19[var23];
//                  beanField = BeanField.getBeanField(f, dest, no_overrider);
//                  if (beanField != null && !destFieldMap.containsKey(f.getName())) {
//                     destFieldMap.put(f.getName(), beanField);
//                  }
//               }
//            }
//         }
//
//         var16 = origFieldMap.entrySet().iterator();
//
//         while(true) {
//            while(true) {
//               BeanField destField;
//               do {
//                  do {
//                     do {
//                        do {
//                           if (!var16.hasNext()) {
//                              return (T) dest;
//                           }
//
//                           entry = (Map.Entry)var16.next();
//                           origField = (BeanField)entry.getValue();
//                           destField = (BeanField)destFieldMap.get(entry.getKey());
//                        } while(destField == null);
//                     } while(origField == null);
//                  } while(!(origField.getClazz() instanceof Class));
//               } while(!(destField.getClazz() instanceof Class));
//
//               if (!no_overrider && origField.getClazz() == destField.getClazz()) {
//                  destField.setValue(origField.getValue());
//               } else if (origField.getClazz() == destField.getClazz()) {
//                  Class<?> destClazz = destField.getClazz();
//                  Class<?> origClazz = origField.getClazz();
//                  if (!destClazz.isPrimitive() && !Number.class.isAssignableFrom(destClazz) || !origClazz.isPrimitive() && !Number.class.isAssignableFrom(origClazz) && !CharSequence.class.isAssignableFrom(origClazz)) {
//                     Object value = null;
//                     if (destClazz != Boolean.TYPE && destClazz != Boolean.class) {
//                        if (Date.class.isAssignableFrom(destClazz) && Date.class.isAssignableFrom(origClazz)) {
//                           value = origField.getValue();
//                           if (destField.getValue() != null && no_overrider && value == null) {
//                              logger.debug("不覆盖此属性. {}", destField.getName());
//                           } else if (java.sql.Date.class == destClazz) {
//                              destField.setValue(value == null ? null : new java.sql.Date(((Date)value).getTime()));
//                           } else {
//                              destField.setValue(value == null ? null : value);
//                           }
//                        } else if (Date.class.isAssignableFrom(destClazz) && CharSequence.class.isAssignableFrom(origClazz)) {
//                           value = (CharSequence)origField.getValue();
//                           if (destField.getValue() != null && no_overrider && value == null) {
//                              logger.debug("不覆盖此属性. {}", destField.getName());
//                           } else {
//                              Date parseDate = null;
//
//                              try {
//                                 parseDate = org.apache.commons.lang3.time.DateUtils.parseDate(value.toString(), DATE_PARSE_PATTERNS);
//                              } catch (Exception var14) {
//                                 logger.error(var14.getMessage());
//                              }
//
//                              if (value == null) {
//                                 destField.setValue(null);
//                              } else if (java.sql.Date.class == destClazz) {
//                                 destField.setValue(new java.sql.Date(parseDate.getTime()));
//                              } else {
//                                 destField.setValue(parseDate);
//                              }
//                           }
//                        } else if (String.class.isAssignableFrom(destClazz) && Date.class.isAssignableFrom(origClazz)) {
//                           value = origField.getValue();
//                           destField.setValue(destField.getValue() != null && no_overrider && value == null ? String.valueOf(destField.getValue()) : DateFormatUtils.format((Date)value, "yyyy-MM-dd HH:mm:ss"));
//                        } else if (String.class.isAssignableFrom(destClazz)) {
//                           value = origField.getValue();
//                           if (destField.getValue() != null) {
//                              destField.setValue(no_overrider && value == null ? String.valueOf(destField.getValue() + "") : (value == null ? null : String.valueOf(value)));
//                           } else {
//                              destField.setValue(no_overrider && value == null ? null : (value == null ? null : String.valueOf(value)));
//                           }
//                        } else if (destClazz.getName().indexOf("java.") <= -1 && destClazz.getName().indexOf("javax.") <= -1 || origClazz.getName().indexOf("java.") <= -1 && origClazz.getName().indexOf("javax.") <= -1) {
//                           logger.debug("Type is not match: " + destField + ", " + origField);
//                        } else {
//                           logger.debug("Not support the complex type:" + destField + ", " + origField);
//                        }
//                     } else {
//                        value = origField.getValue();
//                        if (destField.getValue() != null && no_overrider && value == null) {
//                           logger.debug("不覆盖此属性. {}", destField.getName());
//                        } else if (origClazz != Boolean.TYPE && origClazz != Boolean.class) {
//                           destField.setValue(Boolean.valueOf(String.valueOf(value)));
//                        } else {
//                           destField.setValue(value);
//                        }
//                     }
//                  } else {
//                     boolean isString = CharSequence.class.isAssignableFrom(origClazz);
//                     Object value = origField.getValue();
//                     if (destField.getValue() != null && no_overrider && value == null) {
//                        logger.debug("不覆盖此属性. {}", destField.getName());
//                     } else if (value == null) {
//                        if (destClazz.isPrimitive()) {
//                           destField.setValue((byte)0);
//                        } else {
//                           destField.setValue(null);
//                        }
//                     } else if (destClazz != Double.class && destClazz != Double.TYPE) {
//                        if (destClazz != Float.class && destClazz != Float.TYPE) {
//                           if (destClazz != Long.class && destClazz != Long.TYPE) {
//                              if (destClazz != Integer.class && destClazz != Integer.TYPE) {
//                                 if (destClazz != Short.class && destClazz != Short.TYPE) {
//                                    if (destClazz != Byte.class && destClazz != Byte.TYPE) {
//                                       if (destClazz == BigDecimal.class && (origClazz == Double.class || origClazz == Double.TYPE || origClazz == Float.class || origClazz == Float.TYPE || isString)) {
//                                          if (isString) {
//                                             destField.setValue(BigDecimal.valueOf(new Double(String.valueOf(value))));
//                                          } else {
//                                             destField.setValue(BigDecimal.valueOf(((Number)value).doubleValue()));
//                                          }
//                                       } else if (destClazz == BigDecimal.class) {
//                                          if (isString) {
//                                             destField.setValue(BigDecimal.valueOf(new Double(String.valueOf(value))));
//                                          } else {
//                                             destField.setValue(BigDecimal.valueOf(((Number)value).longValue()));
//                                          }
//                                       } else {
//                                          logger.debug(destField + " Can't set Unknowed Number value!");
//                                       }
//                                    } else if (isString) {
//                                       destField.setValue(new Byte(String.valueOf(value)));
//                                    } else {
//                                       destField.setValue(((Number)value).byteValue());
//                                    }
//                                 } else if (isString) {
//                                    destField.setValue(new Short(String.valueOf(value)));
//                                 } else {
//                                    destField.setValue(((Number)value).shortValue());
//                                 }
//                              } else if (isString) {
//                                 destField.setValue(new Integer(String.valueOf(value)));
//                              } else {
//                                 destField.setValue(((Number)value).intValue());
//                              }
//                           } else if (isString) {
//                              destField.setValue(new Long(String.valueOf(value)));
//                           } else {
//                              destField.setValue(((Number)value).longValue());
//                           }
//                        } else if (isString) {
//                           destField.setValue(new Float(String.valueOf(value)));
//                        } else {
//                           destField.setValue(((Number)value).floatValue());
//                        }
//                     } else if (isString) {
//                        destField.setValue(new Double(String.valueOf(value)));
//                     } else {
//                        destField.setValue(((Number)value).doubleValue());
//                     }
//                  }
//               }
//            }
//         }
//      } else {
//         return (T) dest;
//      }
//   }
//
//   private static class C {
//      private int a = 10;
//      private String b = "bb";
//      private String c = "cc";
//      private Long d;
//      private int e = 50;
//      private Date f = new Date();
//      private Double g = 111.1;
//      private String h = "2015-01-01";
//      private Date i = new Date();
//      private Long j = 111L;
//      private String k;
//      private Long l = 111L;
//      private java.sql.Date m = new java.sql.Date((new Date()).getTime());
//
//      public String toString() {
//         return "C.class [a= " + this.a + ", b=" + this.b + ", c=" + this.c + ", d=" + this.d + ", e=" + this.e + ", f=" + this.f + ", g=" + this.g + ", h=" + this.h + ", i=" + this.i + ", j=" + this.j + ", k=" + this.k + ", l=" + this.l + ", m=" + this.m + "]";
//      }
//   }
//
//   private static class B extends A {
//      private String b;
//      private String c = null;
//      private Long d = 10L;
//      private int e = 0;
//      private Date f = new Date();
//      private Double g = 111.2;
//      private String h = "2015-01-02";
//      private Date i = new Date();
//      private Long j = 112L;
//      private String k = "kkkk";
//      private Long l = 112L;
//      private java.sql.Date m = new java.sql.Date((new Date()).getTime());
//
//      private B() {
//         super(null);
//      }
//   }
//
//   private static class A {
//      private int a;
//
//      private A() {
//         this.a = 1;
//      }
//
//      void print() {
//         System.out.println(this.getClass());
//      }
//
//      // $FF: synthetic method
//      A(Object x0) {
//         this();
//      }
//   }
//
//   private static class BeanField {
//      private String name;
//      private Object value;
//      private Class<?> clazz;
//      private Field field = null;
//      private Object instance = null;
//
//      static BeanField getBeanField(Field field, Object instance, boolean withValue) {
//         if ((field.getModifiers() & 8) != 0) {
//            return null;
//         } else {
//            BeanField bf = new BeanField();
//            bf.name = field.getName();
//            bf.clazz = field.getType();
//            if (withValue) {
//               try {
//                  if (field.isAccessible()) {
//                     bf.value = field.get(instance);
//                  } else {
//                     field.setAccessible(true);
//                     bf.value = field.get(instance);
//                     field.setAccessible(false);
//                  }
//               } catch (Exception var5) {
//                  BeanHelper.logger.error("BeanHelper.getBeanField exception: ", var5);
//               }
//            }
//
//            bf.field = field;
//            bf.instance = instance;
//            return bf;
//         }
//      }
//
//      static BeanField getBeanField(String name, Map instance) {
//         BeanField bf = new BeanField();
//         bf.name = name;
//
//         try {
//            bf.value = instance.get(name);
//            if (bf.value == null) {
//               bf.clazz = Object.class;
//            } else {
//               bf.clazz = bf.value.getClass();
//            }
//         } catch (Exception var4) {
//            BeanHelper.logger.error("BeanHelper.getBeanField exception: ", var4);
//         }
//
//         bf.field = null;
//         bf.instance = instance;
//         return bf;
//      }
//
//      static BeanField copyBeanField(BeanField field, Map instance) {
//         BeanField bf = new BeanField();
//         bf.name = field.name;
//         bf.clazz = field.getClazz();
//         bf.field = null;
//         bf.instance = instance;
//         return bf;
//      }
//
//      public <T> T getValue() {
//         return (T) this.value;
//      }
//
//      public void setValue(Object value) {
//         if (this.field != null && this.instance != null) {
//            try {
//               if (this.field.isAccessible()) {
//                  this.field.set(this.instance, value);
//               } else {
//                  this.field.setAccessible(true);
//                  this.field.set(this.instance, value);
//                  this.field.setAccessible(false);
//               }
//            } catch (Exception var3) {
//               BeanHelper.logger.error("Can't setting the value: " + this.field + ", value=" + value);
//            }
//         } else {
//            ((Map)this.instance).put(this.name, value);
//         }
//
//         this.value = value;
//      }
//
//      public String getName() {
//         return this.name;
//      }
//
//      public Class<?> getClazz() {
//         return this.clazz;
//      }
//
//      public String toString() {
//         return "BeanField [clazz=" + this.clazz + ", field=" + this.field + ", instance=" + this.instance + ", name=" + this.name + ", value=" + this.value + "]";
//      }
//   }
//}
