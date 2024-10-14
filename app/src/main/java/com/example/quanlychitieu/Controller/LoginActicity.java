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

public class LoginActicity extends AppCompatActivity {

    private Button btnDangNhap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_acticity);

        btnDangNhap = (Button) findViewById(R.id.btnDangNhap);
        TextView tv_btnDangky = (TextView) findViewById(R.id.tv_btnDangky);
        EditText inputEmail = (EditText) findViewById(R.id.inputEmail);
        EditText inputPasssword = (EditText) findViewById(R.id.inputPassword);

        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = inputEmail.getText().toString();
                String password = inputPasssword.getText().toString();

                // Kiểm tra điều kiện đăng nhập
                if ("ntb@gmail.com".equals(email) && "1234".equals(password)) {
                    // Đăng nhập thành công cho user
                    Intent intent = new Intent(LoginActicity.this, TongQuan.class);
                    startActivity(intent);
                } else if ("admin@gmail.com".equals(email) && "12356".equals(password)) {
                    // Đăng nhập thành công cho admin
                    Intent intent = new Intent(LoginActicity.this, TrangChuAdmin.class);
                    startActivity(intent);
                } else {
                    // Đăng nhập thất bại
                    Toast.makeText(LoginActicity.this, "Tài khoản hoặc mật khẩu không chính xác", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tv_btnDangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActicity.this, RegisterActivity.class);
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