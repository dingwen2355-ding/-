package com.grandlynn.util.pageHelper;

import com.github.pagehelper.Page;
import java.io.Serializable;
import java.util.List;

public class PageBean<T> implements Serializable {
   private static final long serialVersionUID = 8656597559014685635L;
   private long totalElements;
   private List<T> content;
   private int number;
   private int size;
   private int totalPages;
   private int numberOfElements;

   public PageBean() {
   }

   public PageBean(List<T> list) {
      if (list != null) {
         if (list instanceof Page) {
            Page<T> page = (Page)list;
            this.number = page.getPageNum();
            this.size = page.getPageSize();
            this.totalElements = page.getTotal();
            this.totalPages = page.getPages();
            this.content = page;
            this.numberOfElements = page.size();
         } else {
            this.number = 1;
            this.size = list.size();
            this.totalElements = (long)list.size();
            this.totalPages = 1;
            this.content = list;
            this.numberOfElements = list.size();
         }
      }

   }

   public List<T> getContent() {
      return this.content;
   }

   public void setContent(List<T> content) {
      this.content = content;
   }

   public int getSize() {
      return this.size;
   }

   public void setSize(int size) {
      this.size = size;
   }

   public long getTotalElements() {
      return this.totalElements;
   }

   public void setTotalElements(long totalElements) {
      this.totalElements = totalElements;
   }

   public int getNumber() {
      return this.number;
   }

   public void setNumber(int number) {
      this.number = number;
   }

   public int getTotalPages() {
      return this.totalPages;
   }

   public void setTotalPages(int totalPages) {
      this.totalPages = totalPages;
   }

   public int getNumberOfElements() {
      return this.numberOfElements;
   }

   public void setNumberOfElements(int numberOfElements) {
      this.numberOfElements = numberOfElements;
   }
}
