package qiangyt.springboot_example.common.error;

/**
 *
 * @author
 *
 */
public class WrappedException extends RuntimeException {


  /**
   *
   */
  private static final long serialVersionUID = 2429258112841460243L;

  public WrappedException(Throwable cause) {
    super(cause);
  }

}
