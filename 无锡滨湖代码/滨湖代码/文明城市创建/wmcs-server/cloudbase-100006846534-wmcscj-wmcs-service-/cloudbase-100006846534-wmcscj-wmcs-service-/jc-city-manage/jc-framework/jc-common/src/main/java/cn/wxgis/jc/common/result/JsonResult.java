package cn.wxgis.jc.common.result;

import cn.wxgis.jc.common.constant.Constant;
import cn.wxgis.jc.common.enums.global.GlobalStatus;
import cn.wxgis.jc.common.enums.global.GlobalStatusEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JsonResult<T> {

    /**
     * 状态码
     */
    private String code;
    /**
     * 状态码信息
     */
    private String msg;

    /**
     * 返回结果
     */
    private Object data;


    /**
     * 返回成功
     * @return 返回结果
     */
    public static JsonResult ok() {
        GlobalStatus success = GlobalStatusEnum.STATUS_SUCCESS;
        return JsonResult.build(success.getCode(), success.getMsg(), null);
    }

    /**
     * 返回成功
     * @param <T>  泛型
     * @return 返回结果
     */
    public static <T> JsonResult ok(T data) {
        GlobalStatus success = GlobalStatusEnum.STATUS_SUCCESS;
        JsonResult result = JsonResult.build(success.getCode(), success.getMsg(), data);
        return result;
    }

    /**
     * 返回成功
     * @param <T>  泛型
     * @return 返回结果
     */
    public static <T> JsonResult ok(String key, T data) {
        GlobalStatus success = GlobalStatusEnum.STATUS_SUCCESS;
        Map<String, Object> map = new HashMap<>();
        map.put(key, data);
        JsonResult result = JsonResult.build(success.getCode(), success.getMsg(), map);
        return result;
    }

    /**
     * 返回成功
     * @param data 获取的多条数据
     * @param <T>  泛型
     * @return 返回结果
     */
    public static <T> JsonResult ok(List<T> data) {
        GlobalStatus success = GlobalStatusEnum.STATUS_SUCCESS;
        return JsonResult.build(success.getCode(), success.getMsg(), data);
    }

    /**
     * 返回成功
     * @param data 获取的多条数据
     * @param <T>  泛型
     * @return 返回结果
     */
    public static <T> JsonResult ok(String key, List<T> data) {
        GlobalStatus success = GlobalStatusEnum.STATUS_SUCCESS;
        Map<String, Object> map = new HashMap<>();
        map.put(key, data);
        return JsonResult.build(success.getCode(), success.getMsg(), map);
    }

    /**
     * 返回成功
     * @param code 状态码
     * @param msg  信息
     * @return 返回结果
     */
    public static JsonResult ok(String code, String msg) {
        return JsonResult.build(code, msg, null);
    }

    /**
     * 返回失败
     * @return 返回结果
     */
    public static  <T> JsonResult error(String msg) {
        GlobalStatus error = GlobalStatusEnum.STATUS_ERROR;
        return JsonResult.build(error.getCode(), msg, null);
    }

    /**
     * 返回失败
     * @return 返回结果
     */
    public static JsonResult error() {
        GlobalStatus error = GlobalStatusEnum.STATUS_ERROR;
        return JsonResult.build(error.getCode(), error.getMsg(), null);
    }

    /**
     * 返回失败
     * @param code 状态码
     * @param msg  信息
     * @Parma desc 详细信息
     * @return 返回结果
     */
    public static JsonResult error(String code, String msg) {
        return JsonResult.build(code, msg,  null);
    }

    /**
     * 返回失败
     * @param o 定义的全局参数
     * @return 返回结果
     */
    public static JsonResult error(GlobalStatus o) {
        return JsonResult.error(o, o.getMsg());
    }

    /**
     * 返回失败
     *
     * @param o    定义的全局参数
     * @param msg 描述
     * @return 返回结果
     */
    public static JsonResult error(GlobalStatus o, String msg) {
        return JsonResult.build(o.getCode(),  msg, null);
    }


    /**
     * 构建返回对象
     *
     * @param code     状态码
     * @param msg      信息
     * @param data     返回记录
     * @param <T>      泛型
     * @return 返回对象
     */
    private static <T> JsonResult build(String code, String msg, Object data) {
        return new JsonResult(code, msg,  data);
    }

}
