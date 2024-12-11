package com.example.calculadora_android;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

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

        findViewById(R.id.menu_button).setOnClickListener(view -> showPopupMenu(view));
    }

    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.getMenuInflater().inflate(R.menu.menu_main, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(item -> onMenuItemClick(item));
        popupMenu.show();
    }

    @SuppressLint("NonConstantResourceId")
    private boolean onMenuItemClick(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_save) {
            saveLastCalculation();
            return true;
        } else if (id == R.id.action_load) {
            loadLastCalculation();
            return true;
        } else {
            return false;
        }
    }

    private void saveLastCalculation() {
        try {
            // Obtenemos el texto de result y solution
            String resultText = result.getText().toString();
            String solutionText = solution.getText().toString();
            if (resultText.isEmpty() || solutionText.isEmpty()) {
                Toast.makeText(this, "No hay datos para guardar", Toast.LENGTH_SHORT).show();
                return;
            }

            // Crear el directorio "operaciones" si no existe
            File directory = new File(getFilesDir(), "operaciones");
            if (!directory.exists()) {
                directory.mkdir();
            }

            // Generar un nombre de archivo único basado en la fecha y hora actual
            String fileName = "operacion_" + System.currentTimeMillis() + ".txt";

            // Escribir los datos en el archivo
            String content = "Solution: " + solutionText + "\nResult: " + resultText;
            FileOutputStream fos = new FileOutputStream(new File(directory, fileName));  // Guardar en la carpeta operaciones
            fos.write(content.getBytes());
            fos.close();

            Toast.makeText(this, "Operación guardada como " + fileName, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Error al guardar la operación", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }


    private void loadLastCalculation() {
        try {
            File directory = new File(getFilesDir(), "operaciones");

            if (!directory.exists() || directory.listFiles().length == 0) {
                Toast.makeText(this, "No hay operaciones guardadas", Toast.LENGTH_SHORT).show();
                return;
            }

            File[] files = directory.listFiles();

            List<String> fileNames = new ArrayList<>();
            for (File file : files) {
                fileNames.add(file.getName());  // Añadimos el nombre del archivo con la fecha
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Seleccionar operación a cargar")
                    .setItems(fileNames.toArray(new String[0]), (dialog, which) -> {
                        // Al seleccionar un archivo, cargamos su contenido
                        String selectedFileName = fileNames.get(which);
                        File selectedFile = new File(directory, selectedFileName);
                        loadFileContent(selectedFile);
                    })
                    .show();

        } catch (Exception e) {
            Toast.makeText(this, "Error al cargar las operaciones", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void loadFileContent(File file) {
        try {
            FileInputStream fis = new FileInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));

            StringBuilder fileContent = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                fileContent.append(line).append("\n");
            }
            reader.close();

            String content = fileContent.toString();

            String[] parts = content.split("\n");
            String solutionText = parts[0].replace("Solution: ", "").trim();
            String resultText = parts[1].replace("Result: ", "").trim();

            solution.setText(solutionText);
            result.setText(resultText);

            Toast.makeText(this, "Operación cargada desde " + file.getName(), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Error al cargar el archivo", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
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

        if (btnText.equals("X")) {
            btnText = "*";
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

        String finalResult = getResult(dataToCalculate);

        if (finalResult.equals("Infinity") || finalResult.equals("NaN")) {
            // Mensaje que se muestra cuando se divide por cero en una toast
            Toast toast = Toast.makeText(getApplicationContext(), "Cannot divide by zero", Toast.LENGTH_SHORT);
            toast.show();
            result.setText("0");
            solution.setText("");
            return;
        }

        if (!finalResult.equals("Err")) {
            result.setText(finalResult);
        }

    }

    String getResult(String data) {
        try {
            Context context = Context.enter();
            context.setOptimizationLevel(-2);
            Scriptable scriptable = context.initStandardObjects();
            String finalResult = context.evaluateString(scriptable, data, "JavaScript", 0, null).toString();
            if (finalResult.endsWith(".0")) {
                finalResult = finalResult.replace(".0", "");
            }
            return finalResult;
        } catch (Exception e) {
            return "Err";
        }
    }


}