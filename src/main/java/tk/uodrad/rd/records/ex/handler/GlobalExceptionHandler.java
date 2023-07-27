package tk.uodrad.rd.records.ex.handler;

import cn.leancloud.LCException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import tk.uodrad.rd.records.ex.ServiceException;
import tk.uodrad.rd.records.web.JsonResult;
import tk.uodrad.rd.records.web.ServiceCode;

import java.util.Objects;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ServiceException.class)
    public JsonResult<Void> handleServiceException(ServiceException e) {
        return JsonResult.fail(e);
    }

    @ExceptionHandler
    public JsonResult<Void> handleBindException(BindException e) {
        String message = Objects.requireNonNull(e.getFieldError()).getDefaultMessage();
        return JsonResult.fail(ServiceCode.ERROR_BAD_REQUEST, message);
    }

    @ExceptionHandler
    public JsonResult<Void> handleThrowable(Throwable e) {
        String message = "服务器忙，请稍后再次尝试（开发过程中，如果看到此提示，请检查控制台的信息，并补充处理异常的方法）！";
        e.printStackTrace(); // 注意：在生产环境中，禁止使用此语句！（有性能瓶颈）
        return JsonResult.fail(ServiceCode.ERROR, message);
    }

}
