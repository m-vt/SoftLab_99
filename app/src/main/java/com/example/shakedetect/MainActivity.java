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
    Switch shake_switch , sleep_mode_switch;
    String shakeSensitivityNum ;
    static final String sensitivity = "sensitivity";
    TextView shakeText, sleepText;
    EditText shakeSensitivityEditText ;
    SharedPreferences sharedPrefs;
    String angular_value = "15";
    EditText angular_value_editText;
    static final String degree_id = "degree_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPrefs = getSharedPreferences("MySharedPref", MODE_PRIVATE);

        shake_switch = findViewById(R.id.shakeSwitch);
        shakeText = findViewById(R.id.shake_ID);
        shakeSensitivityEditText = findViewById(R.id.shakeSensitivity);

        if (sharedPrefs.getBoolean("SaveShakeSwitch", false)) {
            shakeSensitivityEditText.setText(sharedPrefs.getString("lastshakeSensitivityNum", ""));
        }
        shake_switch.setChecked(sharedPrefs.getBoolean("SaveShakeSwitch", false));
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

        sleep_mode_switch = findViewById(R.id.sleepmodeSwitch);
        sleepText = findViewById(R.id.sleepMode_ID);

        sleep_mode_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                angular_value = angular_value_editText.getText().toString();
                if (isChecked) {
                    Intent intent = new Intent(MainActivity.this, SleepModeService.class);
                    startService(intent);
                } else {
                    stopService(new Intent(MainActivity.this, SleepModeService.class));
                }
            }
        });

    }
}

