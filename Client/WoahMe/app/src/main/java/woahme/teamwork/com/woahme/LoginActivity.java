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

public class LoginActivity extends Activity implements View.OnClickListener {
    Context context;

    TextView email;
    TextView password;
    Button loginButton;
    TextView registerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        email = (TextView) this.findViewById(R.id.input_email);
        password = (TextView) this.findViewById(R.id.input_password);

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
                break;
            case R.id.link_register:
                break;
        }
    }
}
