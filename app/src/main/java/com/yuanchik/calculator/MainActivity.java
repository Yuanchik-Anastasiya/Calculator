package com.yuanchik.calculator;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    String operator;
    String oldNumber;
    private boolean isNewCalculation = true;
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

        int[] buttonIds = {
                R.id.bu0, R.id.bu1, R.id.bu2, R.id.bu3, R.id.bu4,
                R.id.bu5, R.id.bu6, R.id.bu7, R.id.bu8, R.id.bu9,
                R.id.buDot, R.id.buPlusMinus, R.id.buAC,
                R.id.buPlus, R.id.buMinus, R.id.buMultiply, R.id.buDivision,
                R.id.buPercent, R.id.buEqual, R.id.buBackspace
        };

        for (int id : buttonIds) {
            setupButtonAnimation(findViewById(id));
        }

    }

    public void clickNumber(View view) {

        if (isNew)
            editText.setText("");
        isNew = false;

        if (isNewCalculation) {
            editText.setText("");
            isNewCalculation = false;
        }

        String number = editText.getText().toString();
        if (number.equals("0") &&
                view.getId() != R.id.buDot &&
                view.getId() != R.id.buPlusMinus) {
            number = "";
        }

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
            if (!dotIsPresent(number)) {  // Если точки ещё нет
                if (number.isEmpty() || number.equals("0") || number.equals("-0")) {
                    if (number.startsWith("-")) {
                        number = "-0.";
                    } else {
                        number = "0.";
                    }
                } else {
                    number = number + ".";
                }
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
            isNewCalculation = true;
            operator = "";
            number = "0";
        }

        editText.setText(number);
    }

    public void operations(View view) {

        isNew = true;
        isNewCalculation = false;

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

        if (result == (long) result) {
            editText.setText(String.valueOf((long) result));
        } else {
            editText.setText(String.valueOf(result));
        }

        isNewCalculation = true;

    }

    public void clickPercent(View view) {
        try {
            double currentValue = Double.parseDouble(editText.getText().toString());


            if (operator != null && !operator.isEmpty()) {
                double baseValue = Double.parseDouble(oldNumber);

                switch (operator) {
                    case "+":

                        currentValue = baseValue + (baseValue * currentValue / 100);
                        break;
                    case "-":

                        currentValue = baseValue - (baseValue * currentValue / 100);
                        break;
                    case "*":

                        currentValue = baseValue * (currentValue / 100);
                        break;
                    case "/":

                        if (currentValue == 0) {
                            editText.setText("ERROR");
                            return;
                        }
                        currentValue = baseValue / (currentValue / 100);
                        break;
                }
            } else {

                currentValue = currentValue / 100;
            }

            editText.setText(String.valueOf(currentValue));
            isNewCalculation = true;

        } catch (NumberFormatException e) {
            editText.setText("ERROR");
        }
    }

    public boolean dotIsPresent(String number) {
        return number.contains(".");
    }

    public void onBackspaceClick(View view) {
        String currentText = editText.getText().toString();

        // Если строка пустая или показывает "ERROR" — ничего не делаем
        if (currentText.isEmpty() || currentText.equals("ERROR")) {
            return;
        }

        // Если осталось только "0" или "-0" — не удаляем дальше
        if (currentText.equals("0") || currentText.equals("-0")) {
            return;
        }

        // Удаляем последний символ
        String newText = currentText.substring(0, currentText.length() - 1);

        // Если после удаления строка стала пустой — ставим "0"
        if (newText.isEmpty()) {
            editText.setText("0");
        } else {
            editText.setText(newText);
        }
    }

    private void setupButtonAnimation(View button) {
        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        v.animate().scaleX(0.95f).scaleY(0.95f).setDuration(150).setInterpolator(new OvershootInterpolator(1.5f)).start();
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        v.animate().scaleX(1.0f).scaleY(1.0f).setDuration(150).setInterpolator(new OvershootInterpolator(1.5f)).start();
                        break;
                }
                return false;
            }
        });
    }

}