package com.yuanchik.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    String operator;
    String oldNumber;
    boolean isNew = true;
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

        if (isNew)
            editText.setText("");
        isNew = false;

        String number = editText.getText().toString();

        if (view.getId() == R.id.bu0) {
            number = number + "0";
        } else if (view.getId() == R.id.bu1) {
            number = number + "1";
        } else if (view.getId() == R.id.bu2) {
            number = number + "2";
        } else if (view.getId() == R.id.bu3) {
            number = number + "3";
        } else if (view.getId() == R.id.bu4) {
            number = number + "4";
        } else if (view.getId() == R.id.bu5) {
            number = number + "5";
        } else if (view.getId() == R.id.bu6) {
            number = number + "6";
        } else if (view.getId() == R.id.bu7) {
            number = number + "7";
        } else if (view.getId() == R.id.bu8) {
            number = number + "8";
        } else if (view.getId() == R.id.bu9) {
            number = number + "9";
        } else if (view.getId() == R.id.buDot) {
            if (dotIsPresent(number)) {
            } else {
                number = number + ".";
            }
        } else if (view.getId() == R.id.buPlusMinus) {
            if (number.isEmpty() || number.equals("0")) {
                isNew = true;
                number = "0";
            } else if (number.startsWith("-")) {
                number = number.substring(1);
            } else {
                number = "-" + number;
            }
        } else if (view.getId() == R.id.buAC) {
            isNew = true;
            number = "0";
        }
        editText.setText(number);
    }

    public void operations(View view) {
        isNew = true;
        oldNumber = editText.getText().toString();

        if (view.getId() == R.id.buPlus) {
            operator = "+";
        } else if (view.getId() == R.id.buMinus) {
            operator = "-";
        } else if (view.getId() == R.id.buMultiply) {
            operator = "*";
        } else if (view.getId() == R.id.buDivision) {
            operator = "/";
        }
    }

    public void clickEqual(View view) {
        String newNumber = editText.getText().toString();
        double result = 0.0;

        switch (operator) {
            case "+":
                result = Double.parseDouble(oldNumber) + Double.parseDouble(newNumber);
                break;
            case "-":
                result = Double.parseDouble(oldNumber) - Double.parseDouble(newNumber);
                break;
            case "*":
                result = Double.parseDouble(oldNumber) * Double.parseDouble(newNumber);
                break;
            case "/":
                if (Double.parseDouble(newNumber) == 0) {
                    editText.setText("ERROR");
                    return;
                }
                result = Double.parseDouble(oldNumber) / Double.parseDouble(newNumber);
                break;
        }

        editText.setText(result + "");
    }

    public boolean dotIsPresent(String number) {
        if (!number.contains(".") && !number.contains("-")) {
            return false;
        } else {
            return true;
        }
    }
}