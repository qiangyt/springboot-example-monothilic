package qiangyt.springboot_example.common.misc;


/**
 *
 * @author
 *
 */
public class Holder<T> {

  private T data;

  public Holder() {
    this(null);
  }

  public Holder(T data) {
    this.data = data;
  }

  public static <T> Holder<T> of(T data) {
    return new Holder<>(data);
  }

  public void set(T newData) {
    this.data = newData;
  }

  public T get() {
    return this.data;
  }

}
