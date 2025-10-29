
package com.znv.manage.bean;

/**
  * @ClassName: Result
  * @Description: 封装返回对象格式类型
  * @date 2018/5/16 16:29
  *
  */
public class Result<T> {
    /**
     * 默认返回状态码
     */
    private int code = 200;
    /**
     * 默认返回状态说明
     */
    private String message = "ok";
    /**
     * 返回数据对象
     */
    private T data;
    
    public Result() {
        super();
    }

    public Result(T data) {
        this.data = data;
    }
    public Result(int code, String message) {
        super();
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
    
}