package tk.uodrad.rd.records.web;

import lombok.Data;
import tk.uodrad.rd.records.ex.ServiceException;

import java.io.Serializable;

@Data
public class JsonResult<T> implements Serializable {
    private Integer statusCode;
    private String message;
    private T data;

    public static JsonResult<Void> ok() {
        return ok(null);
    }

    public static <T> JsonResult<T> ok(T data) {
        JsonResult<T> jsonResult = new JsonResult<>();
        jsonResult.statusCode = ServiceCode.SUCCESS.getCode();
        jsonResult.message = "Success";
        jsonResult.data = data;
        return jsonResult;
    }

    public static JsonResult<Void> fail(ServiceException e) {
        return fail(e.getServiceCode(), e.getMessage());
    }

    public static <T> JsonResult<T> fail(ServiceCode serviceCode, String message) {
        JsonResult<T> jsonResult = new JsonResult<>();
        jsonResult.statusCode = serviceCode.getCode();
        jsonResult.message = message;
        return jsonResult;
    }
}
