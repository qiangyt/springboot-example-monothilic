/**
 *
 */
package qiangyt.springboot_example.common.misc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import com.google.common.collect.Lists;

/**
 *
 * @author
 *
 */
public class StringHelper {

  private StringHelper() {
    // do nothing
  }

  public static <T> String toString(T[] array) {
    if (array == null) {
      return null;
    }
    return Lists.newArrayList(array).toString();
  }

  /**
   *
   * @req separator
   * @req texts
   * @return
   */
  public static <T> String join(String separator, Collection<T> texts) {
    return join(separator, texts.toArray(new String[texts.size()]));
  }

  /**
   *
   * @req separator
   * @req texts
   * @return
   */
  public static <T> String join(String separator, T... objects) {
    StringBuilder r = new StringBuilder(objects.length * 64);
    boolean isFirst = true;
    for (T obj : objects) {
      if (isFirst) {
        isFirst = false;
      } else {
        r.append(separator);
      }
      r.append(Objects.toString(obj));
    }
    return r.toString();
  }

  /**
   *
   * @req str
   * @return
   */
  public static boolean isBlank(String str) {
    return (str == null || str.length() == 0 || str.trim().length() == 0);
  }

  public static boolean notBlank(String str) {
    return !isBlank(str);
  }

  /**
   *
   * @req textIterable
   * @return the first blank string if found, otherwise, return null
   */
  public static String hasBlank(Iterable<String> textIterable) {
    for (String t : textIterable) {
      if (isBlank(t)) {
        return t;
      }
    }
    return null;
  }

  /**
   *
   * @req textIterable
   * @return
   */
  public static List<String> trim(Iterable<String> textIterable) {
    List<String> r = new ArrayList<>();

    for (String t : textIterable) {
      r.add(t.trim());
    }

    return r;
  }

}
