package com.example.quanlychitieu.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlychitieu.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Ctrl_DoiMatKhau extends AppCompatActivity {

    private EditText edtCurrentPassword, edtNewPassword, edtConfirmPassword;
    private Button btn_save;
    private ImageButton ic_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doi_mat_khau); // Layout của bạn

        // Lấy tham chiếu các đối tượng trong layout
        ic_back = findViewById(R.id.ic_back);
        btn_save = findViewById(R.id.btn_save);

        // EditText cho mật khẩu
        edtCurrentPassword = findViewById(R.id.txtPassHienTai); // Mật khẩu hiện tại
        edtNewPassword = findViewById(R.id.inputPassNew); // Mật khẩu mới
        edtConfirmPassword = findViewById(R.id.inputPassAgainNew); // Xác nhận mật khẩu mới

        // Lắng nghe sự kiện quay lại
        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Đóng Activity khi nhấn nút quay lại
            }
        });

        // Lắng nghe sự kiện nút lưu
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Kiểm tra các điều kiện đổi mật khẩu
                String currentPassword = edtCurrentPassword.getText().toString();
                String newPassword = edtNewPassword.getText().toString();
                String confirmPassword = edtConfirmPassword.getText().toString();

                if (newPassword.equals(confirmPassword)) {
                    changePassword(currentPassword, newPassword);
                } else {
                    Toast.makeText(getApplicationContext(), "Mật khẩu mới không khớp!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void changePassword(String currentPassword, String newPassword) {
        // Lấy thông tin người dùng hiện tại
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            // Đăng nhập lại người dùng với mật khẩu hiện tại để xác minh
            AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), currentPassword);

            user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        // Nếu đăng nhập lại thành công, thay đổi mật khẩu
                        user.updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    // Mật khẩu đã được thay đổi thành công
                                    Toast.makeText(getApplicationContext(), "Đổi mật khẩu thành công!", Toast.LENGTH_SHORT).show();

                                    // Cập nhật thông tin người dùng vào Firestore (nếu cần)
                                    updateUserDataInFirestore(user);

                                    // Chuyển hướng đến màn hình đăng nhập sau khi thay đổi mật khẩu thành công
                                    Intent intent = new Intent(Ctrl_DoiMatKhau.this, Ctrl_LoginActicity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Xóa các activity trước đó
                                    startActivity(intent);
                                    finish(); // Đóng Activity này
                                } else {
                                    // Có lỗi khi thay đổi mật khẩu
                                    Toast.makeText(getApplicationContext(), "Lỗi: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } else {
                        // Nếu đăng nhập lại không thành công
                        Toast.makeText(getApplicationContext(), "Mật khẩu hiện tại không đúng!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void updateUserDataInFirestore(FirebaseUser user) {
        // Giả sử bạn đang sử dụng Firestore để lưu thông tin người dùng
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference userRef = db.collection("NguoiDung").document(user.getUid());

        // Cập nhật thông tin người dùng vào Firestore, ví dụ chỉ cập nhật mật khẩu (có thể thêm nhiều trường khác)
        Map<String, Object> userData = new HashMap<>();
        userData.put("matKhau", user.getUid());  // Bạn có thể thêm bất kỳ thông tin nào bạn cần ở đây

        userRef.update(userData).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    // Thông báo thành công
                    Log.d("UserData", "User data updated successfully!");
                } else {
                    // Thông báo lỗi khi cập nhật
                    Log.d("UserData", "Error updating user data: " + task.getException().getMessage());
                }
            }
        });
    }

}
