package android.a1ex.com.sklad_tsd;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Password extends AppCompatActivity {
    public EditText login;
    public EditText password;
    public Button ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        login = findViewById(R.id.login);
        password = findViewById(R.id.password);
        ok = findViewById(R.id.butt_OK);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mLogin = "Админ";
                String mPassword = "1";

                if (login.getText().toString().equals(mLogin) && password.getText().toString().equals(mPassword)) {
                    Toast.makeText(getApplicationContext(), R.string.success, Toast.LENGTH_LONG).show();

                    Intent intent = new Intent();
                    intent.putExtra("result", true);
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), R.string.error, Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
