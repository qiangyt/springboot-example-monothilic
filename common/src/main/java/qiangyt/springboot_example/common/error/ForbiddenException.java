package qiangyt.springboot_example.common.error;

import org.springframework.http.HttpStatus;

/**
 *
 * @author
 *
 */
public class ForbiddenException extends BaseException {

  /**
   *
   */
  private static final long serialVersionUID = 8231927320039836736L;

  public ForbiddenException(String messageFormat, Object... messageArgs) {
    super(HttpStatus.FORBIDDEN, messageFormat, messageArgs);
  }

  public ForbiddenException(String message) {
    super(HttpStatus.FORBIDDEN, message);
  }

  public ForbiddenException(Throwable cause, String messageFormat, Object... messageArgs) {
    super(HttpStatus.FORBIDDEN, cause, messageFormat, messageArgs);
  }

  public ForbiddenException(Throwable cause, String message) {
    super(HttpStatus.FORBIDDEN, cause, message);
  }

  public ForbiddenException(Throwable cause) {
    super(HttpStatus.FORBIDDEN, cause);
  }

}
