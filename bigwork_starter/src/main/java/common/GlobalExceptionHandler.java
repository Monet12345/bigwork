package common;

import cn.dev33.satoken.exception.NotLoginException;

import model.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import util.BizException;

//全局异常捕获处理器
@ControllerAdvice
public class GlobalExceptionHandler {
  private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  /**
   * 处理业务异常 BizException
   */
  @ExceptionHandler(BizException.class)
  public ResponseEntity<Result<Void>> handleBizException(BizException ex) {
    logger.error("Business exception occurred: {}", ex.getMessage(), ex);
    Result<Void> result = Result.buildFailure(ex.getMessage()); // 使用 Result 封装错误信息
    return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
  }

  /**
   * 处理未登录异常 NotLoginException
   */
  @ExceptionHandler(NotLoginException.class)
  public ResponseEntity<Result<Void>> handleNotLoginException(NotLoginException ex) {
    logger.error("Login validation failed: {}", ex.getMessage(), ex);
    Result<Void> result = Result.buildFailure("登录校验未通过"); // 使用 Result 封装错误信息
    return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
  }

  /**
   * 处理其他未捕获的异常
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<Result<Void>> handleGenericException(Exception ex) {
    logger.error("Unexpected exception occurred: {}", ex.getMessage(), ex);
    Result<Void> result = Result.buildFailure("Internal Server Error"); // 使用 Result 封装错误信息
    return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}