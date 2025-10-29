package com.grandlynn.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CollectionUtil {
   public static boolean isEmpty(Map<?, ?> map) {
      return map == null || map.size() == 0;
   }

   public static boolean isNotEmpty(Map<?, ?> map) {
      return map != null && map.size() > 0;
   }

   public static boolean isEmpty(Collection<?> collection) {
      return collection == null || collection.size() == 0 || collection.iterator() == null || collection.iterator().next() == null;
   }

   public static boolean isNotEmpty(Collection<?> collection) {
      return collection != null && collection.size() > 0 && collection.iterator() != null && collection.iterator().next() != null;
   }

   public static <E> ArrayList<E> createArrayList() {
      return new ArrayList();
   }

   public static <E> ArrayList<E> createArrayList(int initialCapacity) {
      return new ArrayList(initialCapacity);
   }

   public static <K, V> HashMap<K, V> createHashMap() {
      return new HashMap();
   }

   public static <K, V> HashMap<K, V> createHashMap(int initialCapacity) {
      return new HashMap(initialCapacity);
   }

   public static <K, V> HashMap<K, V> createHashMap(int initialCapacity, float loadFactor) {
      return new HashMap(initialCapacity, loadFactor);
   }

   public static <V> List<V> convertList(List<List<V>> list) {
      List<V> result = new ArrayList();
      Iterator var2 = list.iterator();

      while(var2.hasNext()) {
         List<V> data = (List)var2.next();
         result.addAll(data);
      }

      return result;
   }
}
