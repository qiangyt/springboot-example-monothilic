/**
 *
 */
package qiangyt.springboot_example.common.error;

import java.lang.reflect.InvocationTargetException;

/**
 *
 * @author
 *
 */
public class ExceptionHelper {

  /**
   *
   */
  private ExceptionHelper() {
    // dummy
  }

  /**
   *
   * @req error
   * @return
   */
  public static Throwable getRootCause(Throwable error) {
    Throwable ex = error;

    while (true) {
      Throwable cause;

      if (ex instanceof InvocationTargetException) {
        cause = ((InvocationTargetException) ex).getTargetException();
      } else {
        cause = ex.getCause();
      }

      if (cause == null) {
        return ex;
      }
      ex = cause;
    }
  }

}
