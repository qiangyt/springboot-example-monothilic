package qiangyt.springboot_example.common.error;

import org.springframework.http.HttpStatus;

/**
 *
 * @author
 *
 */
public class NotFoundException extends BaseException {

  /**
   *
   */
  private static final long serialVersionUID = 8231927320039836736L;

  public NotFoundException(String messageFormat, Object... messageArgs) {
    super(HttpStatus.NOT_FOUND, messageFormat, messageArgs);
  }

  public NotFoundException(String message) {
    super(HttpStatus.NOT_FOUND, message);
  }

  public NotFoundException(Throwable cause, String messageFormat, Object... messageArgs) {
    super(HttpStatus.NOT_FOUND, cause, messageFormat, messageArgs);
  }

  public NotFoundException(Throwable cause, String message) {
    super(HttpStatus.NOT_FOUND, cause, message);
  }

  public NotFoundException(Throwable cause) {
    super(HttpStatus.NOT_FOUND, cause);
  }

}
