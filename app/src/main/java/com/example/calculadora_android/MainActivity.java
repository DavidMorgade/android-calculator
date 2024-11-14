package com.example.calculadora_android;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView result, solution;

    MaterialButton btnC, btnSwap, btnPercent, btnDivide, btn7, btn8, btn9, btnMultiply, btn4, btn5, btn6, btnSubtract, btn1, btn2, btn3, btnAdd, btn0, btnDot, btnEqual;

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
        result = findViewById(R.id.result); // Resultado
        solution = findViewById(R.id.solution); // Solución

        assignId(btnC, R.id.button_ac);
        assignId(btnSwap, R.id.button_swap);
        assignId(btnPercent, R.id.button_percent);
        assignId(btnDivide, R.id.button_divide);
        assignId(btn7, R.id.button_7);
        assignId(btn8, R.id.button_8);
        assignId(btn9, R.id.button_9);
        assignId(btnMultiply, R.id.button_multiply);
        assignId(btn4, R.id.button_4);
        assignId(btn5, R.id.button_5);
        assignId(btn6, R.id.button_6);
        assignId(btnSubtract, R.id.button_minus);
        assignId(btn1, R.id.button_1);
        assignId(btn2, R.id.button_2);
        assignId(btn3, R.id.button_3);
        assignId(btnAdd, R.id.button_plus);
        assignId(btn0, R.id.button_0);
        assignId(btnDot, R.id.button_coma);
        assignId(btnEqual, R.id.button_equal);
    }

    private void assignId(MaterialButton btn, int id) {
        btn = findViewById(id);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        MaterialButton btn = (MaterialButton) v;
        String btnText = btn.getText().toString();
        String dataToCalculate = solution.getText().toString();

        if (btnText.equals("AC")) {
            solution.setText("");
            result.setText("0");
            return;
        }

        if (btnText.equals("=")) {
            // Calcular la solución
            solution.setText(result.getText());
            return;
        }

        if (btnText.equals("±")) {
            // Cambiar el signo
            if (dataToCalculate.charAt(0) == '-') {
                dataToCalculate = dataToCalculate.substring(1);
            } else {
                dataToCalculate = "-" + dataToCalculate;
            }
            solution.setText(dataToCalculate);
            return;
        } else {
            dataToCalculate = dataToCalculate + btnText;
        }


        solution.setText(dataToCalculate);

    }

    String getResult(String data) {
        return "Calculated";
    }
}