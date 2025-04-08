package com.example.thltm_tuan2;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView tvInput;
    private String current = "", result = "";
    private boolean isOperator = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ánh xạ màn hình tính toán
        tvInput = findViewById(R.id.tvInput);

        // Mảng các nút số và dấu chấm
        int[] numberIds = {
                R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3,
                R.id.btn4, R.id.btn5, R.id.btn6,
                R.id.btn7, R.id.btn8, R.id.btn9, R.id.btnDot
        };

        // Mảng các nút phép tính
        int[] operatorIds = {
                R.id.btnAdd, R.id.btnSub, R.id.btnMul,
                R.id.btnDiv, R.id.btnPercent
        };

        // Sự kiện khi nhấn nút số
        View.OnClickListener numberListener = view -> {
            Button b = (Button) view;
            current += b.getText().toString();  // Thêm số vào chuỗi phép tính
            tvInput.setText(current);           // Hiễn thị chuỗi phép tính
            isOperator = false;
        };

        // Sự kiện khi nhấn nút phép tính
        View.OnClickListener operatorListener = view -> {
            if (!isOperator && !current.isEmpty()) {
                Button b = (Button) view;
                current += " " + b.getText().toString() + " ";  // Thêm phép toán vào chuỗi phép tính
                tvInput.setText(current);                       // Hiễn thị chuỗi phép tính
                isOperator = true;
            }
        };

        // Vòng lặp kiểm tra các nút số và dấu chấm
        for (int id : numberIds) {
            findViewById(id).setOnClickListener(numberListener);
        }

        // Vòng lặp kiểm tra các nút phép toán
        for (int id : operatorIds) {
            findViewById(id).setOnClickListener(operatorListener);
        }

        // Sự kiện khi nhấn nút xoá
        findViewById(R.id.btnDel).setOnClickListener(v -> {
            current = "";
            result = "";
            tvInput.setText("0");
        });

        // Sự kiện khi nhấn nút bằng
        findViewById(R.id.btnEqual).setOnClickListener(v -> {
            result = calculate(current);
            tvInput.setText(result);
            current = result;
        });
    }

    // Hàm tính toán phép tính hoàn chỉnh hoặc báo lỗi
    private String calculate(String input) {
        try {
            String[] tokens = input.split(" ");       // Tách các kí tự tại dấu " "
            double num1 = Double.parseDouble(tokens[0]);    // Lấy số đầu tiên
            String op = tokens[1];                          // Lấy phép toán
            double num2 = Double.parseDouble(tokens[2]);    // Lấy số thứ hai

            // Chia trường hợp theo phép toán
            switch (op) {
                case "+": return String.valueOf(num1 + num2);
                case "-": return String.valueOf(num1 - num2);
                case "X": return String.valueOf(num1 * num2);
                case "/": return num2 != 0 ? String.valueOf(num1 / num2) : "Error";
                case "%": return String.valueOf(num1 % num2);
                default: return "Error";
            }
        } catch (Exception e) {
            return "Error";
        }
    }
}
