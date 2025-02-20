package util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class JsonSerializer {

  // 私有构造函数，防止外部实例化
  private JsonSerializer() {
    throw new UnsupportedOperationException("Utility class, cannot be instantiated");
  }

  // 静态的 ObjectMapper 实例
  private static final ObjectMapper objectMapper = new ObjectMapper();
  static {
    // 配置 ObjectMapper 忽略不匹配的字段
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
  }
  /**
   * 将对象序列化为 JSON 字符串
   *
   * @param object 需要序列化的对象
   * @return 序列化后的 JSON 字符串
   * @throws RuntimeException 如果序列化失败
   */
  public static String serializeToJson(Object object) {
    try {
      return objectMapper.writeValueAsString(object);
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Failed to serialize object to JSON", e);
    }
  }

  /**
   * 将 JSON 字符串反序列化为 Java 对象
   *
   * @param json  JSON 字符串
   * @param clazz 目标对象的类型
   * @param <T>   泛型类型
   * @return 反序列化后的 Java 对象
   * @throws RuntimeException 如果反序列化失败
   */
  public static <T> T deserializeFromJson(String json, Class<T> clazz) {
    try {
      return objectMapper.readValue(json, clazz);
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Failed to deserialize JSON to object", e);
    }
  }

  /**
   * 将 JSON 字符串反序列化为泛型类型的 Java 对象
   *
   * @param json          JSON 字符串
   * @param typeReference 目标对象的类型引用
   * @param <T>           泛型类型
   * @return 反序列化后的 Java 对象
   * @throws RuntimeException 如果反序列化失败
   */
  public static <T> T deserializeFromJson(String json, TypeReference<T> typeReference) {
    try {
      return objectMapper.readValue(json, typeReference);
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Failed to deserialize JSON to object", e);
    }
  }
}