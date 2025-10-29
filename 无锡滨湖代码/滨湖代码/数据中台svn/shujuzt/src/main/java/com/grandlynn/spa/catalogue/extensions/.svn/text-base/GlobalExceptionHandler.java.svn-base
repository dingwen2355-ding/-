package com.grandlynn.spa.catalogue.extensions;

import com.grandlynn.spa.catalogue.common.enums.ResultCodeEnum;
import com.grandlynn.spa.catalogue.common.exception.SysSecurityException;

import com.grandlynn.util.config.Payload;
import com.grandlynn.util.extension.ApplicationException;
import java.util.Iterator;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.UnexpectedTypeException;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
   @ExceptionHandler({Exception.class})
   @ResponseStatus(
      code = HttpStatus.INTERNAL_SERVER_ERROR
   )
   public Payload<String> errorHandler(Exception ex) {
      ex.printStackTrace();
      return new Payload(null, ResultCodeEnum.FAIL.getCode(), ResultCodeEnum.FAIL.getMsg());
   }

   @ExceptionHandler({SysSecurityException.class})
   @ResponseStatus(
      code = HttpStatus.UNAUTHORIZED
   )
   public Payload<String> myErrorHandler(SysSecurityException ex) {
      return new Payload(ex.getAuthUrl(), ex.getCode(), ex.getMessage());
   }

   @ExceptionHandler({ApplicationException.class})
   @ResponseStatus(
      code = HttpStatus.INTERNAL_SERVER_ERROR
   )
   public Payload<String> myErrorHandler(ApplicationException ex) {
      return new Payload(null, ex.getCode(), ex.getMessage());
   }

   @ExceptionHandler({MethodArgumentNotValidException.class})
   @ResponseStatus(
      code = HttpStatus.BAD_REQUEST
   )
   public Payload<String> validExceptionHandler(MethodArgumentNotValidException ex) {
      String message = ((ObjectError)ex.getBindingResult().getAllErrors().get(0)).getDefaultMessage();
      return new Payload(null, ResultCodeEnum.INVALID_PARAMETER.getCode(), message);
   }

   @ExceptionHandler({BindException.class})
   @ResponseStatus(
      code = HttpStatus.BAD_REQUEST
   )
   public Payload<String> validExceptionHandler(BindException ex) {
      FieldError fieldError = ex.getBindingResult().getFieldError();
      return null != fieldError ? new Payload(null, ResultCodeEnum.INVALID_PARAMETER.getCode(), fieldError.getDefaultMessage()) : new Payload(null, ResultCodeEnum.INVALID_PARAMETER.getCode(), (String)null);
   }

   @ExceptionHandler({ConstraintViolationException.class})
   @ResponseStatus(
      code = HttpStatus.BAD_REQUEST
   )
   public Payload<String> validExceptionHandler(ConstraintViolationException ex) {
      Iterator<ConstraintViolation<?>> it = ex.getConstraintViolations().iterator();
      String message = "";
      if (it.hasNext()) {
         message = ((ConstraintViolation)it.next()).getMessageTemplate();
      }

      return new Payload(null, ResultCodeEnum.INVALID_PARAMETER.getCode(), message);
   }

   @ExceptionHandler({HttpMessageNotReadableException.class})
   @ResponseStatus(
      code = HttpStatus.BAD_REQUEST
   )
   public Payload<String> validExceptionHandler(HttpMessageNotReadableException ex) {
      return new Payload(null, ResultCodeEnum.INVALID_PARAMETER.getCode(), ResultCodeEnum.INVALID_PARAMETER.getMsg());
   }

   @ExceptionHandler({UnexpectedTypeException.class})
   @ResponseStatus(
      code = HttpStatus.BAD_REQUEST
   )
   public Payload<String> validExceptionHandler(UnexpectedTypeException ex) {
      return new Payload(null, ResultCodeEnum.INVALID_PARAMETER.getCode(), ResultCodeEnum.INVALID_PARAMETER.getMsg());
   }
}
