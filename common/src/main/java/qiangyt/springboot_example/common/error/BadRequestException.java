package qiangyt.springboot_example.common.error;

import org.springframework.http.HttpStatus;

/**
 *
 * @author
 *
 */
public class BadRequestException extends BaseException {

  /**
   *
   */
  private static final long serialVersionUID = -2826281784471560606L;

  public BadRequestException(String messageFormat, Object... messageArgs) {
    super(HttpStatus.BAD_REQUEST, messageFormat, messageArgs);
  }

  public BadRequestException(String message) {
    super(HttpStatus.BAD_REQUEST, message);
  }

  public BadRequestException(Throwable cause, String messageFormat, Object... messageArgs) {
    super(HttpStatus.BAD_REQUEST, messageFormat, messageArgs);
  }

  public BadRequestException(Throwable cause, String message) {
    super(HttpStatus.BAD_REQUEST, cause, message);
  }

}
