package qiangyt.springboot_example.common.json;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.TypeFactory;

import lombok.Getter;
import qiangyt.springboot_example.common.error.InternalException;
import qiangyt.springboot_example.common.error.WrappedException;
import qiangyt.springboot_example.common.misc.StringHelper;

/**
 *
 * @author
 *
 */
@Getter
public class JacksonHelper {

  public final ObjectMapper mapper;

  public JacksonHelper(ObjectMapper mapper) {
    this.mapper = mapper;
  }

  public static void initMapper(ObjectMapper mapper) {
    // var dateModule = new SimpleModule();

    mapper.setSerializationInclusion(Include.NON_NULL);
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
    mapper.configure(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS, true);
  }

  public String pretty(Object object) {
    return to(object, true);
  }

  public String to(Object object) {
    return to(object, false);
  }

  /**
   * serialize to json/yaml text
   *
   * @req object the object to serialize
   * @return json/yaml text
   */
  public String to(Object object, boolean pretty) {
    if (object == null) {
      return null;
    }

    try {
      if (pretty) {
        return getMapper().writerWithDefaultPrettyPrinter().writeValueAsString(object);
      }
      return getMapper().writeValueAsString(object);
    } catch (IOException e) {
      throw new WrappedException(e);
    }
  }

  public <T> void fromArray(String[] textArray, T[] result, Class<T> resultClazz) {
    if (textArray.length != result.length) {
      throw new InternalException("result object length not matches text array length");
    }

    for (int i = 0; i < textArray.length; i++) {
      String text = textArray[i];
      result[i] = from(text, resultClazz);
    }
  }

  /**
   * deserialze json/yaml text
   *
   * @req text  json/yaml text to deserialize
   * @req clazz class of resulted object
   * @req       <T> class of resulted object
   *
   * @return resulted object
   */
  public <T> T from(String text, Class<T> clazz) {
    if (StringHelper.isBlank(text)) {
      return null;
    }

    try {
      return getMapper().readValue(text, clazz);
    } catch (IOException e) {
      throw new WrappedException(e);
    }
  }


  public <T> T from(String text, TypeReference<T> typeReference) {
    if (StringHelper.isBlank(text)) {
      return null;
    }

    try {
      return getMapper().readValue(text, typeReference);
    } catch (IOException e) {
      throw new WrappedException(e);
    }
  }

  /**
   * deserialize json/yaml text to map
   *
   * @req text  json/yaml text to deserialize
   * @req keyClazz   class of map key
   * @req valueClazz class of map value
   * @req            <K> class of map key
   * @req            <V> class of map value
   *
   * @return map
   */
  public <K, V> Map<K, V> asMap(String text, Class<K> keyClazz, Class<V> valueClazz) {
    if (StringHelper.isBlank(text)) {
      return null;
    }

    try {
      return getMapper().readValue(text,
          TypeFactory.defaultInstance().constructParametricType(HashMap.class, keyClazz, valueClazz));
    } catch (IOException e) {
      throw new WrappedException(e);
    }
  }

}
