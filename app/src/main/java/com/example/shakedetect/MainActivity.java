package com.example.shakedetect;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Switch shake_switch;
    String shakeSensitivityNum ;
    static final String sensitivity = "sensitivity";
    TextView shakeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        shake_switch = findViewById(R.id.shakeSwitch);
        shakeText = findViewById(R.id.shake_ID);
        shake_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Intent intent_for_shake = new Intent(MainActivity.this, ShakeService.class);
                    intent_for_shake.putExtra(sensitivity, shakeSensitivityNum);
                    startService(intent_for_shake);
                } else {
                    stopService(new Intent(MainActivity.this, ShakeService.class));
                }
            }
        });
    }
}
