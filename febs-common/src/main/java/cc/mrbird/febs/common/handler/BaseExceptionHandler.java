package cc.mrbird.febs.common.handler;

import cc.mrbird.febs.common.entity.FebsResponse;
import cc.mrbird.febs.common.exception.FebsAuthException;
import cc.mrbird.febs.common.exception.FebsException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Set;
import java.util.StringJoiner;

@Slf4j
public class BaseExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public FebsResponse handleException(Exception e) {
        log.error("系统内部异常，异常信息", e);
        return new FebsResponse().message("系统内部异常");
    }

    @ExceptionHandler(value = FebsAuthException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public FebsResponse handleFebsAuthException( FebsAuthException e) {
        log.error("系统认证异常：", e);
        return new FebsResponse().message(e.getMessage());
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public FebsResponse handleAccessDeniedException( AccessDeniedException e) {
        return new FebsResponse().message("没有权限访问该资源");
    }

    @ExceptionHandler(value = FebsException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public FebsResponse handleFebsException(FebsException e) {
        log.error("系統錯誤", e);
        return new FebsResponse().message(e.getMessage());
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public FebsResponse handleFebsException(ConstraintViolationException  e) {
        StringJoiner message = new StringJoiner(",");
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        for (ConstraintViolation<?> violation : constraintViolations) {
            Path path = violation.getPropertyPath();
            String[] pathArr = StringUtils.split(path.toString(), ".");
            message.add(pathArr[1] + violation.getMessage());
        }
        return new FebsResponse().message(message.toString());
    }

    /**
     * 统一处理请求参数校验(实体对象传参)
     *
     * @param e BindException
     * @return FebsResponse
     */
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public FebsResponse handleBindException(BindException e) {
        StringJoiner message = new StringJoiner(",");
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        for (FieldError error : fieldErrors) {
            message.add(error.getField() + "" + error.getDefaultMessage());
        }
        return new FebsResponse().message(message.toString());

    }
}
