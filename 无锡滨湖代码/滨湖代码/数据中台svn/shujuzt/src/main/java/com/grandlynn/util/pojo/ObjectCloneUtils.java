package com.grandlynn.util.pojo;

import com.grandlynn.util.pageHelper.PageBean;
import com.github.pagehelper.Page;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ObjectCloneUtils {
   public static <T> List<T> convertList(List<? extends AbstractObject> sourceList, Class<T> targetClazz) {
      if (sourceList == null) {
         return null;
      } else {
         Iterator var3;
         AbstractObject sourceObject;
         if (sourceList instanceof Page) {
            Page<T> targetPage = getTargetPage(sourceList);
            var3 = sourceList.iterator();

            while(var3.hasNext()) {
               sourceObject = (AbstractObject)var3.next();
               targetPage.add(sourceObject.clone(targetClazz));
            }

            return targetPage;
         } else {
            List<T> targetList = new ArrayList();
            var3 = sourceList.iterator();

            while(var3.hasNext()) {
               sourceObject = (AbstractObject)var3.next();
               targetList.add(sourceObject.clone(targetClazz));
            }

            return targetList;
         }
      }
   }

   public static <T> List<T> convertList(List<? extends AbstractObject> sourceList, Class<T> targetClazz, Integer cloneDirection) {
      if (sourceList == null) {
         return null;
      } else {
         Iterator var4;
         AbstractObject sourceObject;
         if (sourceList instanceof Page) {
            Page<T> targetPage = getTargetPage(sourceList);
            var4 = sourceList.iterator();

            while(var4.hasNext()) {
               sourceObject = (AbstractObject)var4.next();
               targetPage.add(sourceObject.clone(targetClazz, cloneDirection));
            }

            return targetPage;
         } else {
            List<T> targetList = new ArrayList();
            var4 = sourceList.iterator();

            while(var4.hasNext()) {
               sourceObject = (AbstractObject)var4.next();
               targetList.add(sourceObject.clone(targetClazz, cloneDirection));
            }

            return targetList;
         }
      }
   }

   public static <T> PageBean<T> convertPageBean(PageBean<? extends AbstractObject> sourcePageBean, Class<T> targetClazz) {
      if (sourcePageBean == null) {
         return null;
      } else {
         List<? extends AbstractObject> sourceList = sourcePageBean.getContent();
         List<T> targetList = convertList(sourceList, targetClazz);
         PageBean<T> pageBean = new PageBean(targetList);
         if (!(targetList instanceof Page)) {
            pageBean.setNumber(sourcePageBean.getNumber());
            pageBean.setSize(sourcePageBean.getSize());
            pageBean.setTotalElements(sourcePageBean.getTotalElements());
            pageBean.setTotalPages(sourcePageBean.getTotalPages());
            pageBean.setNumberOfElements(sourcePageBean.getNumberOfElements());
         }

         return pageBean;
      }
   }

   public static <T> PageBean<T> convertPageBean(PageBean<? extends AbstractObject> sourcePageBean, Class<T> targetClazz, Integer cloneDirection) {
      if (sourcePageBean == null) {
         return null;
      } else {
         List<? extends AbstractObject> sourceList = sourcePageBean.getContent();
         List<T> targetList = convertList(sourceList, targetClazz, cloneDirection);
         PageBean<T> pageBean = new PageBean(targetList);
         if (!(targetList instanceof Page)) {
            pageBean.setNumber(sourcePageBean.getNumber());
            pageBean.setSize(sourcePageBean.getSize());
            pageBean.setTotalElements(sourcePageBean.getTotalElements());
            pageBean.setTotalPages(sourcePageBean.getTotalPages());
            pageBean.setNumberOfElements(sourcePageBean.getNumberOfElements());
         }

         return pageBean;
      }
   }

   private static <T> Page<T> getTargetPage(List<? extends AbstractObject> sourceList) {
      Page<? extends AbstractObject> tempPage = (Page)sourceList;
      Page<T> targetPage = new Page(tempPage.getPageNum(), tempPage.getPageSize(), tempPage.isCount());
      targetPage.setStartRow(tempPage.getStartRow());
      targetPage.setEndRow(tempPage.getEndRow());
      targetPage.setTotal(tempPage.getTotal());
      targetPage.setPages(tempPage.getPages());
      targetPage.setReasonable(tempPage.getReasonable());
      targetPage.setPageSizeZero(tempPage.getPageSizeZero());
      targetPage.setCountColumn(tempPage.getCountColumn());
      targetPage.setOrderBy(tempPage.getOrderBy());
      targetPage.setOrderByOnly(tempPage.isOrderByOnly());
      return targetPage;
   }
}
