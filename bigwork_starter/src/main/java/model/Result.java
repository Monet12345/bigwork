//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.HashMap;

@ApiModel("操作结果")
public class Result<T> implements Serializable {
  private static final long serialVersionUID = 1L;
  private static final String DEFAULT_SUCCESS_MESSAGE = "OK";
  @ApiModelProperty("是否成功")
  private boolean success;
  @ApiModelProperty("提示信息")
  private String message;
  @ApiModelProperty("业务数据")
  private T data;
  @ApiModelProperty("响应时间戳")
  private Long timestamp = System.currentTimeMillis();
  @ApiModelProperty("扩展属性，备用")
  private HashMap<String, Object> ext = new HashMap();

  public Result() {
  }

  public Result(boolean success, String message, T data) {
    this.success = success;
    this.message = message;
    this.data = data;
  }

  public static <T> Result<T> buildResult(boolean success, String message, T data) {
    return new Result(success, message, data);
  }

  public static <T> Result<T> buildSuccess() {
    return new Result(true, "OK", (Object)null);
  }

  public static <T> Result<T> buildSuccess(T data) {
    return new Result(true, "OK", data);
  }

  public static <T> Result<T> buildFailure(String message) {
    return new Result(false, message, (Object)null);
  }

  public static <T> Result<T> buildSuccess(String message, T data) {
    return new Result(false, message, data);
  }

  public boolean isSuccess() {
    return this.success;
  }

  public String getMessage() {
    return this.message;
  }

  public T getData() {
    return this.data;
  }

  public Long getTimestamp() {
    return this.timestamp;
  }

  public HashMap<String, Object> getExt() {
    return this.ext;
  }

  public void setSuccess(final boolean success) {
    this.success = success;
  }

  public void setMessage(final String message) {
    this.message = message;
  }

  public void setData(final T data) {
    this.data = data;
  }

  public void setTimestamp(final Long timestamp) {
    this.timestamp = timestamp;
  }

  public void setExt(final HashMap<String, Object> ext) {
    this.ext = ext;
  }

  public boolean equals(final Object o) {
    if (o == this) {
      return true;
    } else if (!(o instanceof Result)) {
      return false;
    } else {
      Result<?> other = (Result)o;
      if (!other.canEqual(this)) {
        return false;
      } else if (this.isSuccess() != other.isSuccess()) {
        return false;
      } else {
        label61: {
          Object this$timestamp = this.getTimestamp();
          Object other$timestamp = other.getTimestamp();
          if (this$timestamp == null) {
            if (other$timestamp == null) {
              break label61;
            }
          } else if (this$timestamp.equals(other$timestamp)) {
            break label61;
          }

          return false;
        }

        label54: {
          Object this$message = this.getMessage();
          Object other$message = other.getMessage();
          if (this$message == null) {
            if (other$message == null) {
              break label54;
            }
          } else if (this$message.equals(other$message)) {
            break label54;
          }

          return false;
        }

        Object this$data = this.getData();
        Object other$data = other.getData();
        if (this$data == null) {
          if (other$data != null) {
            return false;
          }
        } else if (!this$data.equals(other$data)) {
          return false;
        }

        Object this$ext = this.getExt();
        Object other$ext = other.getExt();
        if (this$ext == null) {
          if (other$ext != null) {
            return false;
          }
        } else if (!this$ext.equals(other$ext)) {
          return false;
        }

        return true;
      }
    }
  }

  protected boolean canEqual(final Object other) {
    return other instanceof Result;
  }

  public int hashCode() {
    int PRIME = 1;
    int result = 1;
    result = result * 59 + (this.isSuccess() ? 79 : 97);
    Object $timestamp = this.getTimestamp();
    result = result * 59 + ($timestamp == null ? 43 : $timestamp.hashCode());
    Object $message = this.getMessage();
    result = result * 59 + ($message == null ? 43 : $message.hashCode());
    Object $data = this.getData();
    result = result * 59 + ($data == null ? 43 : $data.hashCode());
    Object $ext = this.getExt();
    result = result * 59 + ($ext == null ? 43 : $ext.hashCode());
    return result;
  }

  public String toString() {
    return "Result(success=" + this.isSuccess() + ", message=" + this.getMessage() + ", data=" + this.getData() + ", timestamp=" + this.getTimestamp() + ", ext=" + this.getExt() + ")";
  }
}
