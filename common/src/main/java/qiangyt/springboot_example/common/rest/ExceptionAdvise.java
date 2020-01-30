package qiangyt.springboot_example.common.rest;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import qiangyt.springboot_example.common.error.BaseException;

import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author
 *
 */
@Slf4j
@ControllerAdvice(annotations = { RestController.class })
public class ExceptionAdvise {


  @ExceptionHandler(Throwable.class)
  public ResponseEntity<List<String>> handleError(Throwable ex) {
    var status = translateExceptionToStatus(ex);
    var response = translateExceptionToExceptionResponse(ex);

    if (status == HttpStatus.INTERNAL_SERVER_ERROR) {
      log.error(response.toString(), ex);
    } else {
      log.warn(response.toString(), ex);
    }

    return new ResponseEntity<>(response, status);
  }


  public static List<String> translateExceptionToExceptionResponse(Throwable ex) {
    if (ex instanceof BaseException) {
      return Collections.singletonList(ex.getMessage());
    } else if (ex instanceof MethodArgumentNotValidException) {
      List<ObjectError> objErrors = ((MethodArgumentNotValidException) ex).getBindingResult().getAllErrors();
      return objErrors.stream().map(ExceptionAdvise::getMessage).collect(Collectors.toList());
    } else if (ex instanceof ConstraintViolationException) {
      Set<ConstraintViolation<?>> violations = ((ConstraintViolationException) ex).getConstraintViolations();
      return violations.stream().map(ExceptionAdvise::getMessage).collect(Collectors.toList());
    }

    return Collections.singletonList(ex.getMessage());
  }

  private static String getMessage(ObjectError objError) {
    return String.format("%s: %s", List.of(objError.getArguments()), objError.getDefaultMessage());
  }

  private static String getMessage(ConstraintViolation<?> violation) {
    return String.format("%s: %s", violation.getPropertyPath(), violation.getMessage());
  }

  public static HttpStatus translateExceptionToStatus(Throwable ex) {
    if (ex instanceof BaseException) {
      return ((BaseException)ex).getStatus();
    }

    // TODO: handle InvocationTargetException and root cause

    if (ex instanceof BindException ||
        ex instanceof HttpMessageNotReadableException ||
        ex instanceof MissingServletRequestParameterException ||
        ex instanceof MissingServletRequestPartException ||
        ex instanceof TypeMismatchException ||
        ex instanceof MethodArgumentNotValidException ||
        ex instanceof ConstraintViolationException) {
      return HttpStatus.BAD_REQUEST;
    }

    if (ex instanceof HttpMediaTypeNotAcceptableException) {
      return HttpStatus.NOT_ACCEPTABLE;
    }
    if (ex instanceof HttpRequestMethodNotSupportedException) {
      return HttpStatus.METHOD_NOT_ALLOWED;
    }
    if (ex instanceof HttpMediaTypeNotSupportedException) {
      return HttpStatus.UNSUPPORTED_MEDIA_TYPE;
    }

    if (ex instanceof AsyncRequestTimeoutException) {
      return HttpStatus.REQUEST_TIMEOUT;
    }

    return HttpStatus.INTERNAL_SERVER_ERROR;
  }

}
