package util;


import lombok.Data;

//错误响应类
@Data
public class ErrorResponse {
  private int errorCode;
  private String errorMessage;

  public ErrorResponse(int errorCode, String errorMessage) {
    this.errorCode = errorCode;
    this.errorMessage = errorMessage;
  }

}