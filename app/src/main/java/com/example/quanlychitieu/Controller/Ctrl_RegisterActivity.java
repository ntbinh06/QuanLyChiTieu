package com.example.quanlychitieu.Controller;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlychitieu.Model.M_NguoiDung;
import com.example.quanlychitieu.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Ctrl_RegisterActivity extends AppCompatActivity {

    private Button btnDangKy;
    private FirebaseAuth auth; // FirebaseAuth instance
    private FirebaseDatabase database;
    private DatabaseReference userRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btnDangKy = findViewById(R.id.btnDangKy);
        TextView tv_btnDangNhap = findViewById(R.id.tv_btnDangNhap);
        EditText inputName = findViewById(R.id.inputName);
        EditText inputEmail = findViewById(R.id.dk_inputEmail);
        EditText inputPassword = findViewById(R.id.dk_inputPassword);

        // Firebase Auth and Realtime Database initialization
        auth = FirebaseAuth.getInstance(); // Correct initialization
        database = FirebaseDatabase.getInstance();
        userRef = database.getReference("NguoiDung");

        // Register button click event
        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = inputName.getText().toString().trim();
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();

                if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    showAlertDialog("Vui lòng điền đầy đủ thông tin");
                    return;
                }

                if (!isEmailValid(email)) {
                    showAlertDialog("Email không hợp lệ. Vui lòng kiểm tra lại tên miền email.");
                    return;
                }

                if (password.length() < 6) {
                    showAlertDialog("Mật khẩu phải có ít nhất 6 ký tự");
                    return;
                }

                // Check if email already exists
                userRef.orderByChild("email").equalTo(email)
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    showAlertDialog("Email này đã được đăng ký. Vui lòng chọn email khác.");
                                } else {
                                    // Create a new user account
                                    auth.createUserWithEmailAndPassword(email, password)
                                            .addOnCompleteListener(task1 -> {
                                                if (task1.isSuccessful()) {
                                                    String userId = auth.getCurrentUser().getUid(); // Get the user ID from Firebase Auth

                                                    // Create a new user object with the user ID
                                                    M_NguoiDung user = new M_NguoiDung(userId, name, email, password); // lock is false by default

                                                    // Save the user data to the Realtime Database
                                                    userRef.child(userId).setValue(user)
                                                            .addOnCompleteListener(task2 -> {
                                                                if (task2.isSuccessful()) {
                                                                    Toast.makeText(Ctrl_RegisterActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                                                                    Intent intent = new Intent(Ctrl_RegisterActivity.this, Ctrl_TongQuan.class);
                                                                    startActivity(intent);
                                                                    finish();
                                                                } else {
                                                                    showAlertDialog("Lỗi khi lưu thông tin người dùng. Vui lòng thử lại.");
                                                                }
                                                            });
                                                } else {
                                                    String errorMessage = task1.getException().getMessage();
                                                    String errorMessageViet = convertErrorMessageToVietnamese(errorMessage);
                                                    showAlertDialog(errorMessageViet);
                                                }
                                            });
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                showAlertDialog("Lỗi khi kiểm tra email. Vui lòng thử lại.");
                            }
                        });
            }
        });

        // Navigate to Login screen
        tv_btnDangNhap.setOnClickListener(view -> {
            Intent intent = new Intent(Ctrl_RegisterActivity.this, Ctrl_LoginActicity.class);
            startActivity(intent);
        });
    }

    // Show AlertDialog
    private void showAlertDialog(String message) {
        new AlertDialog.Builder(Ctrl_RegisterActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                .setCancelable(false)
                .show();
    }

    // Convert Firebase error messages to Vietnamese
    private String convertErrorMessageToVietnamese(String errorMessage) {
        switch (errorMessage) {
            case "The email address is already in use by another account.":
                return "Email này đã được đăng ký bởi tài khoản khác.";
            case "The password is too weak.":
                return "Mật khẩu quá yếu. Vui lòng chọn mật khẩu mạnh hơn.";
            case "There is no user record corresponding to this identifier. The user may have been deleted.":
                return "Không tìm thấy tài khoản với email này. Tài khoản có thể đã bị xóa.";
            case "The email address is badly formatted.":
                return "Định dạng email không hợp lệ. Vui lòng kiểm tra lại địa chỉ email.";
            default:
                return errorMessage;
        }
    }

    // Check email format
    private boolean isEmailValid(String email) {
        String emailPattern = "^[a-zA-Z0-9._%+-]+@gmail\\.com$";
        return email.matches(emailPattern);
    }
}