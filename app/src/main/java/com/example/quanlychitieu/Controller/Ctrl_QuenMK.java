package com.example.quanlychitieu.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlychitieu.R;
import com.google.firebase.auth.FirebaseAuth;

public class Ctrl_QuenMK extends AppCompatActivity {

    private EditText inputEmail;
    private Button btnResetPassword;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quenmk);

        // Ánh xạ view
        inputEmail = findViewById(R.id.inputEmail);
        btnResetPassword = findViewById(R.id.btnDangNhap);
        TextView tv_btnDangky = findViewById(R.id.tv_btnDangky);

        // Firebase Auth
        auth = FirebaseAuth.getInstance();

        // Chuyển đến màn hình Đăng nhập
        tv_btnDangky.setOnClickListener(view -> {
            Intent intent = new Intent(Ctrl_QuenMK.this, Ctrl_LoginActicity.class);
            startActivity(intent);
        });

        // Xử lý sự kiện nút Đặt lại mật khẩu
        btnResetPassword.setOnClickListener(v -> {
            String email = inputEmail.getText().toString().trim();

            if (TextUtils.isEmpty(email)) {
                Toast.makeText(Ctrl_QuenMK.this, "Vui lòng nhập email", Toast.LENGTH_SHORT).show();
                return;
            }

            // Gửi yêu cầu đặt lại mật khẩu
            resetPassword(email);
        });
    }

    private void resetPassword(String email) {
        // Gửi email đặt lại mật khẩu từ Firebase Auth
        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(Ctrl_QuenMK.this, "Email đặt lại mật khẩu đã được gửi. Vui lòng kiểm tra hộp thư!", Toast.LENGTH_SHORT).show();

                        // Chuyển màn hình sang Đăng nhập sau khi gửi thành công
                        Intent intent = new Intent(Ctrl_QuenMK.this, Ctrl_LoginActicity.class);
                        startActivity(intent);
                        finish();  // Để kết thúc màn hình hiện tại nếu không muốn quay lại
                    } else {
                        Toast.makeText(Ctrl_QuenMK.this, "Không thể gửi email đặt lại mật khẩu: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
