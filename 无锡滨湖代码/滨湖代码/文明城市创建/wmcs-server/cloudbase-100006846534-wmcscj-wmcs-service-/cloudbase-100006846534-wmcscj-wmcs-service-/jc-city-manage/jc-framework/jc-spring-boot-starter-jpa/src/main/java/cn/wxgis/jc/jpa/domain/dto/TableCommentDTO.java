package cn.wxgis.jc.jpa.domain.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class TableCommentDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;

    private String comment;

    private List<ColumnCommentDTO> columnCommentDTOList;
}
