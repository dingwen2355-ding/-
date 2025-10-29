package com.grandlynn.util.pojo;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public abstract class AbstractObject {
   public <T> T clone(Class<T> targetClazz) {
      try {
         T target = targetClazz.newInstance();
         BeanCopierUtils.copyProperties(this, target);
         return this.getTarget(target);
      } catch (Exception var3) {
         throw new RuntimeException("error", var3);
      }
   }

   public <T> T clone(T target) {
      try {
         BeanCopierUtils.copyProperties(this, target);
         return this.getTarget(target);
      } catch (Exception var3) {
         throw new RuntimeException("error", var3);
      }
   }

   public <T> T clone(Class<T> targetClazz, Integer cloneDirection) {
        try {
             T target = targetClazz.newInstance();
             BeanCopierUtils.copyProperties(this, target);
             Class<?> thisClazz = getClass();
             List<Field> thisFields = listThisField(null, thisClazz);
             for (Field thisField : thisFields) {
                  thisField.setAccessible(true);
                  if (!Collection.class.isAssignableFrom(thisField.getType())) {
                       Class<?> sourceFeildClazz = thisField.getType();
                       if (sourceFeildClazz == String.class || sourceFeildClazz == Long.class || sourceFeildClazz
          .toString().equals("long") || thisField.getType() == Integer.class || sourceFeildClazz
          .toString().equals("int") || sourceFeildClazz == Short.class || sourceFeildClazz
          .toString().equals("short") || sourceFeildClazz == Double.class || sourceFeildClazz
          .toString().equals("double") || sourceFeildClazz == Float.class || sourceFeildClazz
          .toString().equals("float") || sourceFeildClazz == BigDecimal.class || sourceFeildClazz == Boolean.class || sourceFeildClazz
          .toString().equals("boolean") || sourceFeildClazz == java.sql.Date.class || sourceFeildClazz == Character.class || sourceFeildClazz

          .toString().equals("char") || sourceFeildClazz == Byte.class || sourceFeildClazz
          .toString().equals("byte") || sourceFeildClazz == Date.class)
                            continue;
                       try {
                            if (!(thisField.getType().newInstance() instanceof AbstractObject))
                                 continue;
                          } catch (Exception e) {
                            if (e instanceof InstantiationException)
                                 continue;
                            throw new RuntimeException("error", e);
                          }
                       AbstractObject sourceObj = (AbstractObject)thisField.get(this);
                       if (sourceObj == null)
                            continue;
                       Field field = null;
                       try {
                            field = getTargetClazzField(thisField, targetClazz);
                          } catch (NoSuchFieldException e) {
                            continue;
                          }
                       if (field != null) {
                            Class<?> cloneTargetClazz = field.getType();
                            AbstractObject clonedObj = (AbstractObject)sourceObj.clone(cloneTargetClazz, cloneDirection);
                            String name = thisField.getName();
                            String setMethodName = "set" + name.substring(0, 1).toUpperCase() + name.substring(1);
                            Method setFieldMethod = targetClazz.getMethod(setMethodName, new Class[] { field.getType() });
                            setFieldMethod.invoke(target, new Object[] { clonedObj });
                          }
                       continue;
                     }
                  Collection<?> list = (Collection)thisField.get(this);
                  if (list == null || list.size() == 0)
                       continue;
                  Field targetField = null;
                  try {
                       targetField = getTargetClazzField(thisField, targetClazz);
                     } catch (NoSuchFieldException e) {
                       continue;
                     }
                  if (targetField != null) {
                       Class<?> cloneTargetClazz = getTargetListGenericType(targetField);
                       Collection clonedList = (Collection)thisField.get(this).getClass().newInstance();
                       cloneList(list, clonedList, cloneTargetClazz, cloneDirection);
                       String name = thisField.getName();
                       String setMethodName = "set" + name.substring(0, 1).toUpperCase() + name.substring(1);
                       Method setFieldMethod = targetClazz.getMethod(setMethodName, new Class[] { targetField.getType() });
                       setFieldMethod.invoke(target, new Object[] { clonedList });
                     }
                }
             return target;
           } catch (Exception e) {
             throw new RuntimeException("error", e);
           }
      }

   public List<Field> listThisField(List<Field> thisFields, Class<?> thisClazz) {
      if (thisFields == null) {
         thisFields = new ArrayList(Arrays.asList(thisClazz.getDeclaredFields()));
      } else {
         ((List)thisFields).addAll(Arrays.asList(thisClazz.getDeclaredFields()));
      }

      if (!thisClazz.getSuperclass().getTypeName().equals(AbstractObject.class.getTypeName())) {
         this.listThisField((List)thisFields, thisClazz.getSuperclass());
      }

      return (List)thisFields;
   }

   private Field getTargetClazzField(Field thisField, Class<?> targetClazz) throws NoSuchFieldException {
      Field targetField = null;

      try {
         targetField = targetClazz.getDeclaredField(thisField.getName());
      } catch (NoSuchFieldException var6) {
         if (targetClazz.getSuperclass() != null) {
            String targetSuperClazzTypeName = targetClazz.getSuperclass().getTypeName();
            if (!targetSuperClazzTypeName.equals(Object.class.getTypeName()) && !targetSuperClazzTypeName.equals(AbstractObject.class.getTypeName())) {
               targetField = this.getTargetClazzField(thisField, targetClazz.getSuperclass());
            }
         }

         if (targetField == null) {
            throw var6;
         }
      }

      return targetField;
   }

   private void cloneList(Collection sourceList, Collection targetList, Class cloneTargetClazz, Integer cloneDirection) {
        for (Object object : sourceList) {
             if (object instanceof AbstractObject) {
                  AbstractObject targetObject = (AbstractObject)object;
                  AbstractObject clonedObject = targetObject.<AbstractObject>clone(cloneTargetClazz, cloneDirection);
                  targetList.add(clonedObject);
                  continue;
                }
             targetList.add(object);
           }
      }

   private Class<?> getTargetListGenericType(Field targetField) {
      Type genericType = targetField.getGenericType();
      if (genericType instanceof ParameterizedType) {
         ParameterizedType parameterizedType = (ParameterizedType)genericType;
         return (Class)parameterizedType.getActualTypeArguments()[0];
      } else {
         return null;
      }
   }

   private <T> T getTarget(T target) throws Exception {
        Class<?> targetClazz = target.getClass();
        Field[] fields = targetClazz.getDeclaredFields();
        for (Field field : fields) {
             field.setAccessible(true);
             if (field.getType() == List.class) {
                  List<?> list = (List)field.get(target);
                  if (list != null && list.size() != 0) {
                       Class<?> targetListGenericTypeClazz = getTargetListGenericType(field);
                       if (isAbstractObjectClass(targetListGenericTypeClazz)) {
                            String name = field.getName();
                            String setMethodName = "set" + name.substring(0, 1).toUpperCase() + name.substring(1);
                            Method setFieldMethod = targetClazz.getMethod(setMethodName, new Class[] { field.getType() });
                            setFieldMethod.invoke(target, new Object[] { new ArrayList() });
                          }
                     }
                }
           }
        return target;
      }

   private boolean isAbstractObjectClass(Class clazz) {
      if (clazz.getSuperclass() != null) {
         String superClazzTypeName = clazz.getSuperclass().getTypeName();
         if (superClazzTypeName.equals(Object.class.getTypeName())) {
            return false;
         } else {
            return superClazzTypeName.equals(AbstractObject.class.getTypeName()) ? true : this.isAbstractObjectClass(clazz.getSuperclass());
         }
      } else {
         return false;
      }
   }
}
