package software.cranes.com.dota.common;



import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.JavaType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by GiangNT - PC on 23/09/2016.
 */

public class JsonUtil {
    public static <T> T convertObjectFromJsonString(String json, Class<T> clss) {
        T ret = null;
        if (json != null) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                mapper.configure(org.codehaus.jackson.JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
                mapper.configure(org.codehaus.jackson.JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
                ret = mapper.readValue(json, clss);
            } catch (JsonParseException e) {
                e.printStackTrace();
                return null;
            } catch (JsonMappingException e) {
                e.printStackTrace();
                return null;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return ret;
    }

    /**
     * Convert a list object from json string
     *
     * @param <T>
     *            the generic type
     * @param json
     * @param clss
     * @return List<T>
     */
    public static <T> List<T> convertArrayFromJsonString(String json, Class<T> clss) {
        List<T> ret = null;
        if (json != null) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                mapper.configure(org.codehaus.jackson.JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
                mapper.configure(org.codehaus.jackson.JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
                JavaType type = mapper.getTypeFactory().constructCollectionType(ArrayList.class, clss);
                ret = mapper.readValue(json, type);
            } catch (JsonParseException e) {
                e.printStackTrace();
                return null;
            } catch (JsonMappingException e) {
                e.printStackTrace();
                return null;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        return ret;
    }
}
