package woahme.teamwork.com.woahme.APIs.Imgur;

/**
 * Created by newmast on 1/17/2016.
 */
import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import woahme.teamwork.com.woahme.BuildConfig;

public class ImgurRequest extends JsonObjectRequest {
    public ImgurRequest(int method, String url, JSONObject jsonRequest, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(method, url, jsonRequest, listener, errorListener);
    }

    public ImgurRequest(String url, JSONObject jsonRequest, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(url, jsonRequest, listener, errorListener);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = new HashMap<String,String>();
        headers.put("Authorization", "Client-ID " + BuildConfig.IMGUR_CLIENT_ID);
        return headers;
    }

    public static JSONObject generateUploadRequestBody(String imageAsBase64) {
        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("image", imageAsBase64);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return requestBody;
    }
}