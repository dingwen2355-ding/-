package com.grandlynn.spa.catalogue.dto;

import com.grandlynn.util.pojo.AbstractObject;
import lombok.Data;

@Data
public class WordModelFieldData extends AbstractObject {
   private Integer index;
   private String fieldNameCh;
   private String fieldName;
   private String fieldType;
   private String fieldLength;
   private String fieldDesc;
}
