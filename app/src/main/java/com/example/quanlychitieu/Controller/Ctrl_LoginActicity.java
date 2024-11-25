package com.example.quanlychitieu.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlychitieu.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Ctrl_LoginActicity extends AppCompatActivity {

    private Button btnDangNhap;
    private EditText inputEmail, inputPassword;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_acticity);

        // Khởi tạo FirebaseAuth
        auth = FirebaseAuth.getInstance();

        // Ánh xạ các view
        btnDangNhap = findViewById(R.id.btnDangNhap);
        TextView tv_btnDangky = findViewById(R.id.tv_btnDangky);
        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);

        // Sự kiện khi nhấn vào nút Đăng nhập
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();

                // Kiểm tra điều kiện nhập liệu
                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(Ctrl_LoginActicity.this, "Vui lòng nhập đầy đủ email và mật khẩu", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Tiến hành đăng nhập qua Firebase Auth
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                FirebaseUser user = auth.getCurrentUser();
                                if (user != null) {
                                    // Đăng nhập thành công, chuyển sang trang chính
                                    Intent intent = new Intent(Ctrl_LoginActicity.this, Ctrl_TongQuan.class);
                                    startActivity(intent);
                                    finish();
                                }
                            } else {
                                // Đăng nhập thất bại
                                Toast.makeText(Ctrl_LoginActicity.this, "Tài khoản hoặc mật khẩu không chính xác", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        // Chuyển đến màn hình Đăng ký
        tv_btnDangky.setOnClickListener(view -> {
            Intent intent = new Intent(Ctrl_LoginActicity.this, Ctrl_RegisterActivity.class);
            startActivity(intent);
        });
    }
}
