package woahme.teamwork.com.woahme;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import woahme.teamwork.com.woahme.Http.SingletonRequestQueue;
import woahme.teamwork.com.woahme.Models.LoginRequestModel;
import woahme.teamwork.com.woahme.Models.LoginResponseModel;
import woahme.teamwork.com.woahme.Utilities.HttpUtils;
import woahme.teamwork.com.woahme.Utilities.MD5;
import woahme.teamwork.com.woahme.Utilities.Notificator;

public class LoginActivity extends Activity implements View.OnClickListener {
    Context context;

    TextView emailView;
    TextView passwordView;
    AppCompatButton loginButton;
    AppCompatButton registerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        emailView = (TextView) this.findViewById(R.id.input_email);
        passwordView = (TextView) this.findViewById(R.id.input_password);

        loginButton = (AppCompatButton) this.findViewById(R.id.btn_login);
        loginButton.setOnClickListener(this);

        registerView = (AppCompatButton) this.findViewById(R.id.link_register);
        registerView.setOnClickListener(this);

        context = this;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        String email = emailView.getText().toString();
        String password = passwordView.getText().toString();

        if (email.length() == 0) {
            Notificator.Notify(this, "WoahMe", "Email cannot be empty.");
            return;
        } else if (password.length() == 0) {
            Notificator.Notify(this, "WoahMe", "Password cannot be empty.");
            return;
        }

        switch (v.getId()) {
            case R.id.btn_login:
                LoginUser(email, password, this.GetLoginCallback());
                break;
            case R.id.link_register:
                RegisterUser(email, password, this.GetLoginCallback());
                break;
        }
    }

    public void LoginUser(String username, String password, Response.Listener callback) {
        SingletonRequestQueue.getInstance(this).addToRequestQueue(new JsonObjectRequest(
                Request.Method.POST,
                Endpoints.LoginEndpoint,
                getInfoAsJson(username, password),
                callback,
                SingletonRequestQueue.GetDefaultErrorListener()) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        });
    }

    public void RegisterUser(String username, String password, Response.Listener callback) {
        SingletonRequestQueue.getInstance(this).addToRequestQueue(new JsonObjectRequest(
                Request.Method.POST,
                Endpoints.RegisterEndPoint,
                getInfoAsJson(username, password),
                callback,
                SingletonRequestQueue.GetDefaultErrorListener()) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        });
    }

    public Response.Listener GetLoginCallback() {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                List<LoginResponseModel> items =
                    HttpUtils.ParseJsonResponse(response.toString(), LoginResponseModel.class);

                String token = items.get(0).getToken();

                SharedPreferences session = getPreferences(0);
                session
                        .edit()
                        .putString("token", token)
                        .commit();

                Log.e("token", token);
                finish();
            }
        };
    }

    public JSONObject getInfoAsJson(String username, String password) {
        JSONObject obj = new JSONObject();

        try {
            obj.put("username", username);
            obj.put("password", MD5.crypt(password));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return obj;
    }
}
