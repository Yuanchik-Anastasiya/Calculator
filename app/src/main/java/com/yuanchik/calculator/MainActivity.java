package com.yuanchik.calculator;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editText = findViewById(R.id.editText);
    }

    public void clickNumber(View view) {

        String number = editText.getText().toString();

        if (view.getId() == R.id.bu0) {
            number = number + "0";
        } else if (view.getId() == R.id.bu1) {
            number = number + "1";
        }else if (view.getId() == R.id.bu2) {
            number = number + "2";
        }else if (view.getId() == R.id.bu3) {
            number = number + "3";
        }else if (view.getId() == R.id.bu4) {
            number = number + "4";
        }else if (view.getId() == R.id.bu5) {
            number = number + "5";
        }else if (view.getId() == R.id.bu6) {
            number = number + "6";
        }else if (view.getId() == R.id.bu7) {
            number = number + "7";
        }else if (view.getId() == R.id.bu8) {
            number = number + "8";
        }else if (view.getId() == R.id.bu9) {
            number = number + "9";
        }else if (view.getId() == R.id.buAC){
            number = "0";
        }
        editText.setText(number);
    }
}