package qiangyt.springboot_example.common.error;

import org.springframework.http.HttpStatus;

/**
 *
 * @author
 *
 */
public abstract class BaseException extends RuntimeException {

  /**
   *
   */
  private static final long serialVersionUID = 1174402847948155859L;

  private final HttpStatus status;

  public BaseException(HttpStatus status, String message) {
    super(message);
    this.status = status;
  }

  public BaseException(HttpStatus status, String messageFormat, Object... messageArgs) {
    super(String.format(messageFormat, messageArgs));
    this.status = status;
  }

  public BaseException(HttpStatus status, Throwable cause) {
    super(cause);
    this.status = status;
  }

  public BaseException(HttpStatus status, Throwable cause, String message) {
    super(message, cause);
    this.status = status;
  }

  public BaseException(HttpStatus status, Throwable cause, String messageFormat, Object... messageArgs) {
    super(String.format(messageFormat, messageArgs), cause);
    this.status = status;
  }

  /**
   * @return the status
   */
  public HttpStatus getStatus() {
    return this.status;
  }

}
