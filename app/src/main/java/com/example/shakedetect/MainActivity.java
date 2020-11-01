package com.example.shakedetect;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Switch shake_switch;
    String shakeSensitivityNum ;
    static final String sensitivity = "sensitivity";
    TextView shakeText;
    EditText shakeSensitivityEditText ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        shake_switch = findViewById(R.id.shakeSwitch);
        shakeText = findViewById(R.id.shake_ID);
        shakeSensitivityEditText = findViewById(R.id.shakeSensitivity);
        shake_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = getSharedPreferences("MySharedPref", MODE_PRIVATE).edit();
                shakeSensitivityNum = shakeSensitivityEditText.getText().toString();
                if (isChecked) {

                    if (!shakeSensitivityNum.isEmpty()) {
                        Intent intent_for_shake = new Intent(MainActivity.this, ShakeService.class);
                        intent_for_shake.putExtra(sensitivity, shakeSensitivityNum);
                        startService(intent_for_shake);
                        editor.putBoolean("SaveShakeSwitch", true);
                        editor.putString("lastshakeSensitivityNum", shakeSensitivityNum);
                    } else {
                        shake_switch.setChecked(false);
                        Toast.makeText(getApplicationContext(), "Enter Shake Sensitivity", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    stopService(new Intent(MainActivity.this, ShakeService.class));
                    editor.putBoolean("SaveShakeSwitch", false);
                }
                editor.apply();
            }
        });
    }
}

