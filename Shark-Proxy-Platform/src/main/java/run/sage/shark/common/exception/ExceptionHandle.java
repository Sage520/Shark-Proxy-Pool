package run.sage.shark.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.xnio.Result;
import run.sage.shark.common.exception.base.BaseException;
import run.sage.shark.common.web.AjaxResult;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;


/**
 * 异常处理
 *
 * @author Sage
 * @date 2021/04/28
 */
@Slf4j
@RestControllerAdvice
public class ExceptionHandle {

    /**
     * 自定义验证异常
     * 处理Get请求中 使用@Valid 验证路径中请求实体校验失败后抛出的异常，详情继续往下看代码
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public AjaxResult handleBindException(BindException e) {
        String message = e.getAllErrors().get(0).getDefaultMessage();
        return AjaxResult.error(message);
    }

    /**
     * 自定义验证异常
     * 处理请求参数格式错误 @RequestParam上validate失败后抛出的异常是javax.validation.ConstraintViolationException
     *
     * @param e e
     * @return {@link AjaxResult}
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public AjaxResult handleConstraintViolationException(ConstraintViolationException e) {
        String message = e.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.joining());
        return AjaxResult.error(message);
    }

    /**
     * 自定义验证异常
     * 处理请求参数格式错误 @RequestBody上validate失败后抛出的异常是MethodArgumentNotValidException异常。
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public AjaxResult handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getFieldError().getDefaultMessage();
        return AjaxResult.error(message);
    }

    /**
     * BaseException捕获
     *
     * @param e
     * @return {@link AjaxResult}
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BaseException.class)
    public AjaxResult baseExceptionHandler(BaseException e){
        return AjaxResult.error(e.getMessage());
    }

    /**
     * 系统级异常捕捉
     *
     * @param e
     * @return {@link AjaxResult}
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception.class)
    public AjaxResult exceptionHandle(Exception e){
        log.error("Exception", e);
        return AjaxResult.error("系统出错，请稍后再试");
    }

}