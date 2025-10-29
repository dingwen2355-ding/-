package com.grandlynn.util.pojo;

import java.util.HashMap;
import java.util.Map;
import net.sf.cglib.beans.BeanCopier;
import net.sf.cglib.core.Converter;

public class BeanCopierUtils {
   public static Map<String, BeanCopier> beanCopierCacheMap = new HashMap();

   public static void copyProperties(Object source, Object target) {
      String cacheKey = source.getClass().toString() + target.getClass().toString();
      BeanCopier beanCopier = null;
      if (!beanCopierCacheMap.containsKey(cacheKey)) {
         Class var4 = BeanCopierUtils.class;
         synchronized(BeanCopierUtils.class) {
            if (!beanCopierCacheMap.containsKey(cacheKey)) {
               beanCopier = BeanCopier.create(source.getClass(), target.getClass(), false);
               beanCopierCacheMap.put(cacheKey, beanCopier);
            } else {
               beanCopier = (BeanCopier)beanCopierCacheMap.get(cacheKey);
            }
         }
      } else {
         beanCopier = (BeanCopier)beanCopierCacheMap.get(cacheKey);
      }

      beanCopier.copy(source, target, (Converter)null);
   }
}
