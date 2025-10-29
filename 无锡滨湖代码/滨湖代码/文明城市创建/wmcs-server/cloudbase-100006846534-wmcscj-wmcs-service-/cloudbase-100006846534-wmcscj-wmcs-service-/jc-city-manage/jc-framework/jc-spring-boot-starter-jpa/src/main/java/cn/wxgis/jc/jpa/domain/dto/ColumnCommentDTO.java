package cn.wxgis.jc.jpa.domain.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ColumnCommentDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;

    private String comment;

}
