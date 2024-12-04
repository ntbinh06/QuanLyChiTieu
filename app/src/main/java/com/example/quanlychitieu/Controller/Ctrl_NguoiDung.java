package com.example.quanlychitieu.Controller;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.quanlychitieu.Model.M_NguoiDung;
import com.example.quanlychitieu.R;
import com.google.common.base.FinalizableReference;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.bumptech.glide.Glide;


public class Ctrl_NguoiDung extends AppCompatActivity {

    private ImageView ivUserAvatar, ic_back;
    private Button btnDangXuat, btnXoaTaiKhoan;
    private FirebaseFirestore firestore;
    private String userId;
    private TextView txtHeaderTenUser, txtHeaderEmailUser, txtTenUser, txtEamil, txtDateBirth, txtSDT;
    private LinearLayout thongtinCN;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nguoi_dung);

        // Ánh xạ View
        txtHeaderTenUser = findViewById(R.id.heade_user_name);
        txtHeaderEmailUser = findViewById(R.id.hd_user_email);
        txtTenUser = findViewById(R.id.user_name);
        txtEamil = findViewById(R.id.user_email);
        txtDateBirth = findViewById(R.id.user_date);
        txtSDT = findViewById(R.id.user_phone);
        btnDangXuat = findViewById(R.id.btnDangXuat);
        btnXoaTaiKhoan = findViewById(R.id.btn_xoataikhoan);
        ic_back = findViewById(R.id.ic_back);
        thongtinCN = findViewById(R.id.thongtinCN);

        // Firebase User
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("NguoiDung").child(userId);

            userRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String tenUser = dataSnapshot.child("tenUser").getValue(String.class);
                        String email = dataSnapshot.child("email").getValue(String.class);
                        String datebirth = dataSnapshot.child("ngaySinh").getValue(String.class);
                        String sdt = dataSnapshot.child("SDT").getValue(String.class);

                        txtHeaderTenUser.setText(tenUser);
                        txtHeaderEmailUser.setText(email);
                        txtTenUser.setText(tenUser);
                        txtEamil.setText(email);
                        txtDateBirth.setText(datebirth);
                        txtSDT.setText(sdt);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(Ctrl_NguoiDung.this, "Không thể tải dữ liệu", Toast.LENGTH_SHORT).show();
                }
            });

        }

        thongtinCN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Ctrl_NguoiDung.this, Ctrl_ThongTinCaNhan.class);
                startActivity(intent);
                finish();
            }
        });

        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Ctrl_NguoiDung.this, Ctrl_TongQuan.class);
                startActivity(intent);
                finish();
            }
        });

        // Sự kiện đăng xuất
        btnDangXuat.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
            navigateToLoginScreen();
        });

        // Sự kiện xóa tài khoản
        btnXoaTaiKhoan.setOnClickListener(view -> showDeleteAccountDialog());

    }



    // Hiển thị hộp thoại đăng xuất
    private void showLogoutDialog() {
        LayoutInflater inflater = LayoutInflater.from(Ctrl_NguoiDung.this);
        View dialogView = inflater.inflate(R.layout.custom_dialog_dangxuat, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(Ctrl_NguoiDung.this);
        builder.setView(dialogView);

        TextView tvMessage = dialogView.findViewById(R.id.tvMessage);
        Button btnLogout = dialogView.findViewById(R.id.btnLogout);
        Button btnCancel = dialogView.findViewById(R.id.btnCancel);

        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.border_dialog);

        btnLogout.setOnClickListener(v -> {
            navigateToLoginScreen();
            dialog.dismiss();
        });

        btnCancel.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    // Hiển thị hộp thoại xóa tài khoản
    private void showDeleteAccountDialog() {
        LayoutInflater inflater = LayoutInflater.from(Ctrl_NguoiDung.this);
        View dialogView = inflater.inflate(R.layout.custom_dialog_xoataikhoan, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(Ctrl_NguoiDung.this);
        builder.setView(dialogView);

        TextView tvMessage = dialogView.findViewById(R.id.tvMessage);
        Button btnDelete = dialogView.findViewById(R.id.btnDelete);
        Button btnCancel = dialogView.findViewById(R.id.btnCancel);

        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.border_dialog);

        btnDelete.setOnClickListener(v -> {
            // Gọi hàm xóa tài khoản (cần bổ sung xử lý tại đây)
            deleteUserAccount();
            dialog.dismiss();
        });

        btnCancel.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    // Điều hướng đến màn hình đăng nhập
    private void navigateToLoginScreen() {
        Intent intent = new Intent(Ctrl_NguoiDung.this, Ctrl_TrangChuDNDK.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish(); // Kết thúc Ctrl_NguoiDung
    }

    private void deleteUserAccount() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Lấy ID người dùng
            String userId = user.getUid();

            // Xóa tài khoản Firebase Authentication
            user.delete()
                    .addOnCompleteListener(deleteTask -> {
                        if (deleteTask.isSuccessful()) {
                            // Nếu xóa tài khoản thành công, tiến hành xóa dữ liệu người dùng từ Realtime Database
                            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("NguoiDung").child(userId);
                            userRef.removeValue()
                                    .addOnCompleteListener(task -> {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(Ctrl_NguoiDung.this, "Xóa tài khoản và dữ liệu thành công", Toast.LENGTH_SHORT).show();
                                            navigateToLoginScreen();
                                        } else {
                                            // Thông báo lỗi khi xóa dữ liệu người dùng không thành công
                                            String errorMessage = task.getException() != null ? task.getException().getMessage() : "Lỗi không xác định";
                                            Toast.makeText(Ctrl_NguoiDung.this, "Xóa dữ liệu người dùng không thành công: " + errorMessage, Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        } else {
                            // Thông báo lỗi khi xóa tài khoản không thành công
                            String errorMessage = deleteTask.getException() != null ? deleteTask.getException().getMessage() : "Lỗi không xác định";
                            Toast.makeText(Ctrl_NguoiDung.this, "Xóa tài khoản không thành công: " + errorMessage, Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            // Thông báo nếu không có người dùng nào đang đăng nhập
            Toast.makeText(Ctrl_NguoiDung.this, "Không có người dùng đăng nhập", Toast.LENGTH_SHORT).show();
        }
    }
}
