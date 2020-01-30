package qiangyt.springboot_example.common.error;

import org.springframework.http.HttpStatus;

/**
 *
 * @author
 *
 */
public class UnauthorizedException extends BaseException {

  /**
   *
   */
  private static final long serialVersionUID = -3594848915521372174L;

  public UnauthorizedException(String messageFormat, Object... messageArgs) {
    super(HttpStatus.UNAUTHORIZED, messageFormat, messageArgs);
  }

  public UnauthorizedException(String message) {
    super(HttpStatus.UNAUTHORIZED, message);
  }

  public UnauthorizedException(Throwable cause, String messageFormat, Object... messageArgs) {
    super(HttpStatus.UNAUTHORIZED, cause, messageFormat, messageArgs);
  }

  public UnauthorizedException(Throwable cause, String message) {
    super(HttpStatus.UNAUTHORIZED, cause, message);
  }

  public UnauthorizedException(Throwable cause) {
    super(HttpStatus.UNAUTHORIZED, cause);
  }

}
