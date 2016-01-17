package woahme.teamwork.com.woahme.Utilities;

import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;

/**
 * Created by newmast on 1/17/2016.
 */
public class HttpUtils {
    public static final <T> List<T> ParseJsonResponse(String response, Class<T> type) {
        final T jsonToObject = new Gson().fromJson(response, type);

        return Arrays.asList(jsonToObject);
    }
}
