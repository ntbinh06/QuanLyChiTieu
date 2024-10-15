package com.example.quanlychitieu.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.quanlychitieu.R;
import com.example.quanlychitieu.View.Fragment_TongQuan;

public class RegisterActivity extends AppCompatActivity {

    private Button btnDangKy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        btnDangKy = (Button) findViewById(R.id.btnDangKy);
        TextView tv_btnDangnhap = (TextView) findViewById(R.id.tv_btnDangNhap)  ;
        EditText inputName = (EditText) findViewById(R.id.inputName);
        EditText inputEmail = (EditText) findViewById(R.id.dk_inputEmail);
        EditText inputPasssword = (EditText) findViewById(R.id.dk_inputPassword);

        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = inputName.getText().toString();
                String email = inputEmail.getText().toString();
                String password = inputPasssword.getText().toString();

                // Kiểm tra điều kiện nhập liệu
                if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    // Hiện thông báo nếu có trường không được điền
                    Toast.makeText(RegisterActivity.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    // Nếu tất cả các trường đã được điền, chuyển sang trang TongQuan
                    Intent intent = new Intent(RegisterActivity.this, TongQuan.class);
                    startActivity(intent);
                }
            }
        });

        tv_btnDangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActicity.class);
                startActivity(intent);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}