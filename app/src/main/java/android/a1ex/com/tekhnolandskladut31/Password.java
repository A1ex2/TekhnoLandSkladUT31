package android.a1ex.com.tekhnolandskladut31;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Password extends AppCompatActivity {
    private EditText login;
    private EditText password;
    private Button ok;

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
                Toast.makeText(getApplicationContext(), "Все ОК", Toast.LENGTH_LONG);
            }
        });

    }
}
