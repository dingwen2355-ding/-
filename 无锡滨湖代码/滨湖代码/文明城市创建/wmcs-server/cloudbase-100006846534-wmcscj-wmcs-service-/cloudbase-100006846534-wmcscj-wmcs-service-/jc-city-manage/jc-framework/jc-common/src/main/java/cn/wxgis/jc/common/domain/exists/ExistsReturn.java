package cn.wxgis.jc.common.domain.exists;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ExistsReturn {

    boolean isExists;

    String message;
}
