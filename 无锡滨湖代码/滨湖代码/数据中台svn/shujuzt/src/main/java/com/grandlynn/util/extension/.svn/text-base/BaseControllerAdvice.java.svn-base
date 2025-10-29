//package com.grandlynn.util.extension;
//
//import com.grandlynn.util.config.Payload;
//import com.grandlynn.util.constant.ResultConstant;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//
//public class BaseControllerAdvice {
//   Logger logger = LoggerFactory.getLogger(BaseControllerAdvice.class);
//
//   @ExceptionHandler({Exception.class})
//   public Payload errorHandler(Exception ex) {
//      this.logger.error("全局异常：", ex);
//      return new Payload(ResultConstant.ERROR);
//   }
//
//   @ExceptionHandler({ApplicationException.class})
//   public Payload<String> myErrorHandler(ApplicationException ex) {
//      return new Payload(ex.getCode(), ex.getMessage());
//   }
//}
