package qiangyt.springboot_example.common.error;

import org.springframework.http.HttpStatus;

/**
 *
 * @author
 *
 */
public class InternalException extends BaseException {

  /**
   *
   */
  private static final long serialVersionUID = -7568498974012397904L;

  public InternalException(String messageFormat, Object... messageArgs) {
    super(HttpStatus.INTERNAL_SERVER_ERROR, messageFormat, messageArgs);
  }

  public InternalException(String message) {
    super(HttpStatus.INTERNAL_SERVER_ERROR, message);
  }

  public InternalException(Throwable cause, String messageFormat, Object... messageArgs) {
    super(HttpStatus.INTERNAL_SERVER_ERROR, cause, messageFormat, messageArgs);
  }

  public InternalException(Throwable cause, String message) {
    super(HttpStatus.INTERNAL_SERVER_ERROR, cause, message);
  }

}
