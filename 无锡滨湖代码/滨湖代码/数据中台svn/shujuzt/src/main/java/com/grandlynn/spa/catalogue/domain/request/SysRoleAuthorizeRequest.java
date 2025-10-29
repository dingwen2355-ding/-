package com.grandlynn.spa.catalogue.domain.request;

import com.grandlynn.spa.catalogue.base.BaseRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import javax.validation.constraints.NotNull;
@Data
public class SysRoleAuthorizeRequest extends BaseRequest {
   private static final long serialVersionUID = 1321578882681308987L;
   @ApiModelProperty(
      value = "角色ID",
      required = true
   )
   private @NotNull(
   message = "角色ID不能为空"
) Long roleId;
   @ApiModelProperty(
      value = "菜单ID集合",
      required = true
   )
   private List<Long> menuIdList;
   private Integer solSystem;

}
