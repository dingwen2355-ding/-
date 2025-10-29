package com.grandlynn.spa.catalogue.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
@Data
public class PasswordDTO implements Serializable {
   private static final long serialVersionUID = -4121021016699704255L;
   @ApiModelProperty(
      value = "id",
      required = true
   )
   private @NotNull(
   message = "id不能为空"
) Integer id;
   @ApiModelProperty(
      value = "password",
      required = true
   )
   private @NotEmpty(
   message = "password不能为空"
) String password;
}
