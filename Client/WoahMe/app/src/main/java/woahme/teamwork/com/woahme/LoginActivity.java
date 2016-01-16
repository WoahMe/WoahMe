package woahme.teamwork.com.woahme;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.List;

import woahme.teamwork.com.woahme.Http.SingletonRequestQueue;
import woahme.teamwork.com.woahme.Models.LoginRequestModel;

public class LoginActivity extends Activity implements View.OnClickListener {
    Context context;

    TextView emailView;
    TextView passwordView;
    Button loginButton;
    TextView registerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        emailView = (TextView) this.findViewById(R.id.input_email);
        passwordView = (TextView) this.findViewById(R.id.input_password);

        loginButton = (Button) this.findViewById(R.id.btn_login);
        loginButton.setOnClickListener(this);

        registerView = (TextView) this.findViewById(R.id.link_register);
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
        switch (v.getId()) {
            case R.id.btn_login:
                String email = emailView.getText().toString();
                String password = passwordView.getText().toString();

                LoginUser(email, password, this.GetLoginCallback());
                break;
            case R.id.link_register:
                break;
        }
    }

    public void LoginUser(String username, String password, Response.Listener callback) {
        SingletonRequestQueue.getInstance(this).addToRequestQueue(new JsonObjectRequest(
                Request.Method.POST,
                Endpoints.LoginEndpoint,
                callback,
                SingletonRequestQueue.GetDefaultErrorListener()));
    }

    public Response.Listener GetLoginCallback() {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                List<LoginRequestModel> items =
                        SingletonRequestQueue.ParseResponse(response.toString(), LoginRequestModel.class);

            }
        };
    }
}
