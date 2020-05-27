package qiangyt.springboot_example.common.json;

import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 *
 * @author
 *
 */
public class JsonHelper {

  public static final ThreadLocal<JacksonHelper> MAPPER = ThreadLocal.withInitial(() -> {
    ObjectMapper mapper = new ObjectMapper();
      return new JacksonHelper(mapper);
  });

  public static ObjectMapper buildMapper() {
    ObjectMapper r = new ObjectMapper();
    JacksonHelper.initMapper(r);
    return r;
  }

  public static String pretty(Object object) {
    return to(object, true);
  }

  public static String to(Object object) {
    return to(object, false);
  }

  public static String to(Object object, boolean pretty) {
    return MAPPER.get().to(object, pretty);
  }

  public static <T> void from(String[] jsonArray, T[] result, Class<T> resultClazz) {
    MAPPER.get().fromArray(jsonArray, result, resultClazz);
  }


  public static <T> T from(String json, Class<T> clazz) {
    return MAPPER.get().from(json, clazz);
  }


  public static <T> T from(String json, TypeReference<T> typeReference) {
    return MAPPER.get().from(json, typeReference);
  }

  public static <K, V> Map<K, V> asMap(String json, Class<K> keyClazz, Class<V> valueClazz) {
    return MAPPER.get().asMap(json, keyClazz, valueClazz);
  }

}
