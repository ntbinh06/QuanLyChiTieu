package com.example.quanlychitieu.Controller;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.quanlychitieu.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Calendar;
import java.util.Locale;

public class Ctrl_ThongTinCaNhan extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private LinearLayout calenderBirthDate;
    private DatabaseReference databaseReference;
    private EditText edtTenUser, edtSDT;
    private TextView txtBirthDay, txtEmail;
    private ImageView imgAvatar;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Thiết lập Locale tiếng Việt
        Locale locale = new Locale("vi");
        Locale.setDefault(locale);
        Configuration config = getResources().getConfiguration();
        config.setLocale(locale);
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());

        setContentView(R.layout.activity_thongtincanhan);
        // Khởi tạo các thành phần giao diện
        ImageButton ic_back = findViewById(R.id.ic_back);
        Button btn_save = findViewById(R.id.btn_save);
        ImageButton ic_camera = findViewById(R.id.camera_icon);
        calenderBirthDate = findViewById(R.id.birthDate);
        txtBirthDay = findViewById(R.id.ttcn_date);
        edtTenUser = findViewById(R.id.ttcn_name);
        txtEmail = findViewById(R.id.ttcn_email);
        edtSDT = findViewById(R.id.ttcn_phone);
        imgAvatar = findViewById(R.id.profile_image);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser != null) {
            String userId = currentUser.getUid();
            databaseReference = FirebaseDatabase.getInstance().getReference("NguoiDung").child(userId);

            // Tải dữ liệu từ Firebase
            loadUserData();
        }

        // Xử lý sự kiện quay lại
        ic_back.setOnClickListener(v -> finish());

        // Xử lý sự kiện lưu dữ liệu

        // Hiển thị DatePicker để chọn ngày
        calenderBirthDate.setOnClickListener(view -> showDatePicker());

        // Mở thư viện để chọn ảnh
        ic_camera.setOnClickListener(view -> openGallery());

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveUserInfo();
                Intent intent = new Intent(Ctrl_ThongTinCaNhan.this, Ctrl_NguoiDung.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void loadUserData() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String tenUser = dataSnapshot.child("tenUser").getValue(String.class);
                    String email = dataSnapshot.child("email").getValue(String.class);
                    String ngaySinh = dataSnapshot.child("ngaySinh").getValue(String.class);
                    String sdt = dataSnapshot.child("SDT").getValue(String.class);
                    String avatarUrl = dataSnapshot.child("avatarUrl").getValue(String.class);

                    edtTenUser.setText(tenUser);
                    txtEmail.setText(email);
                    txtBirthDay.setText(ngaySinh);
                    edtSDT.setText(sdt);

                    if (avatarUrl != null) {
                        Glide.with(Ctrl_ThongTinCaNhan.this).load(avatarUrl).into(imgAvatar);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Ctrl_ThongTinCaNhan.this, "Không thể tải dữ liệu", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                R.style.CustomDatePickerDialog,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    String selectedDate = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                    txtBirthDay.setText(selectedDate);
                },
                year, month, day
        );

        datePickerDialog.show();
    }

    // Mở thư viện chọn ảnh
    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }


    // Nhận ảnh đã chọn
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData(); // URI ảnh
            imgAvatar.setImageURI(imageUri); // Hiển thị ảnh trên ImageView
            uploadImageToFirebase(imageUri); // Gọi hàm tải ảnh
        }
    }

    private void uploadImageToFirebase(Uri uri) {
        if (uri != null) {
            // Tham chiếu tới Firebase Storage
            String userId = FirebaseAuth.getInstance().getCurrentUser().getUid(); // ID người dùng
            StorageReference storageReference = FirebaseStorage.getInstance().getReference("avatars/" + userId + ".jpg");

            // Tải file lên Firebase Storage
            storageReference.putFile(uri)
                    .addOnSuccessListener(taskSnapshot -> {
                        // Lấy URL tải xuống của ảnh
                        storageReference.getDownloadUrl().addOnSuccessListener(downloadUrl -> {
                            // Lưu URL vào Firebase Realtime Database
                            DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                                    .getReference("NguoiDung")
                                    .child(userId);
                            databaseReference.child("avatarUrl").setValue(downloadUrl.toString());

                            Toast.makeText(this, "Cập nhật ảnh đại diện thành công!", Toast.LENGTH_SHORT).show();
                        });
                    })
                    .addOnFailureListener(e -> Toast.makeText(this, "Tải ảnh thất bại: " + e.getMessage(), Toast.LENGTH_SHORT).show());
        }
    }


    private void saveUserInfo() {
        String tenUser = edtTenUser.getText().toString().trim();
        String email = txtEmail.getText().toString().trim();
        String ngaySinh = txtBirthDay.getText().toString().trim();
        String sdt = edtSDT.getText().toString().trim();

        if (tenUser.isEmpty() || email.isEmpty() || ngaySinh.isEmpty() || sdt.isEmpty()) {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            return;
        }

        databaseReference.child("tenUser").setValue(tenUser);
        databaseReference.child("email").setValue(email);
        databaseReference.child("ngaySinh").setValue(ngaySinh);
        databaseReference.child("SDT").setValue(sdt)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Lưu thông tin thành công!", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(this, "Đã xảy ra lỗi. Vui lòng thử lại!", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
