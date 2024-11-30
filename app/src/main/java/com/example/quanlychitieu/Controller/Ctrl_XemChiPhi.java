package com.example.quanlychitieu.Controller;

import android.app.AlertDialog;
import android.content.Intent;
import android.icu.text.DecimalFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlychitieu.Model.M_DanhMucHangMuc;
import com.example.quanlychitieu.Model.M_GiaoDich;
import com.example.quanlychitieu.Model.M_TaiKhoan;
import com.example.quanlychitieu.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class Ctrl_XemChiPhi extends AppCompatActivity {
    private String transactionId;
    private DatabaseReference giaoDichRef; // Firebase reference for the transaction to be deleted

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xemchiphi); // Your layout file

        ImageButton ic_back = findViewById(R.id.ic_back);
        Button buttonsua = findViewById(R.id.btnSuaChiPhi);
        Button buttonxoa = findViewById(R.id.btnXoaChiPhi);

        ImageView imgHinhGD = findViewById(R.id.xemCTHinhGD);
        TextView txtTenGD = findViewById(R.id.xemCTTenGD);
        TextView txtGiaGD = findViewById(R.id.xemCTGiaGD);
        TextView txtTKGD = findViewById(R.id.xemCTTKGD);
        TextView txtNgayGD = findViewById(R.id.xemCTNgayGD);
        TextView txtTu = findViewById(R.id.txtTu);
        TextView txtGhiChu = findViewById(R.id.txtGhiChu);

        // Nhận idGiaoDich và idNhom từ Intent
        String idGiaoDich = getIntent().getStringExtra("idGiaoDich");
        String idNhom = getIntent().getStringExtra("idNhom");
// Lấy tham chiếu đến Firebase
        giaoDichRef = FirebaseDatabase.getInstance().getReference("GiaoDich");

// Nhận idGiaoDich từ Intent
        Intent intent = getIntent();
        if (intent != null) {
            idGiaoDich = intent.getStringExtra("idGiaoDich");
            if (idGiaoDich == null || idGiaoDich.isEmpty()) {
                Toast.makeText(this, "ID không hợp lệ", Toast.LENGTH_SHORT).show();
                finish();
                return;
            }

            // Lấy thông tin giao dịch sử dụng ID
            giaoDichRef = FirebaseDatabase.getInstance().getReference("GiaoDich");
                // Lấy thông tin giao dịch sử dụng ID
                giaoDichRef.child(idGiaoDich).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            Log.d("FirebaseData", "Dữ liệu giao dịch: " + dataSnapshot.getValue());

                            // Tạo đối tượng M_GiaoDich từ DataSnapshot
                            M_GiaoDich giaoDich = dataSnapshot.getValue(M_GiaoDich.class);

                            // Lấy thông tin danh mục hàng hóa
                            DatabaseReference hangMucRef = FirebaseDatabase.getInstance().getReference("HangMuc").child(giaoDich.getIdHangMuc());
                            hangMucRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot hangMucSnap) {
                                    M_DanhMucHangMuc danhMucHangMuc = hangMucSnap.getValue(M_DanhMucHangMuc.class);
                                    txtTenGD.setText(danhMucHangMuc != null ? danhMucHangMuc.getTenHangmuc() : "Không có tên");
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    Log.w("Firebase", "loadHangMuc:onCancelled", databaseError.toException());
                                }
                            });

                            // Lấy thông tin tài khoản
                            DatabaseReference taiKhoanRef = FirebaseDatabase.getInstance().getReference("TaiKhoan").child(giaoDich.getIdTaiKhoan());
                            taiKhoanRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot taiKhoanSnap) {
                                    M_TaiKhoan taiKhoan = taiKhoanSnap.getValue(M_TaiKhoan.class);
                                    txtTKGD.setText(taiKhoan != null ? taiKhoan.getTenTaiKhoan() : "Không có tài khoản");
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    Log.w("Firebase", "loadTaiKhoan:onCancelled", databaseError.toException());
                                }
                            });

                            // Gán dữ liệu cho các View còn lại
                            txtGiaGD.setText(String.valueOf(giaoDich.getGiaTri())); // Hoặc sử dụng DecimalFormat nếu cần định dạng tiền
                            txtNgayGD.setText(giaoDich.getFormattedNgayTao()); // Sử dụng phương thức đã định nghĩa để định dạng ngày
                            txtGhiChu.setText(giaoDich.getGhiChu() != null ? giaoDich.getGhiChu() : "Không có ghi chú");
                            txtTu.setText(giaoDich.getTu() != null ? giaoDich.getTu() : "Không có thông tin");
                        } else {
                            Toast.makeText(Ctrl_XemChiPhi.this, "Giao dịch không tồn tại", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w("Firebase", "loadGiaoDich:onCancelled", databaseError.toException());
                        Toast.makeText(Ctrl_XemChiPhi.this, "Lỗi khi tải dữ liệu", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        // Xử lý sự kiện nút Sửa
        buttonsua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Ctrl_XemChiPhi.this, Ctrl_ThemChiPhi.class);
                startActivity(intent);
            }
        });

        // Xử lý sự kiện nút Quay lại
        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Ctrl_XemChiPhi.this, Ctrl_CacGiaoDich.class);
                startActivity(intent);
            }
        });

        // Xử lý sự kiện nút Xóa
        buttonxoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo LayoutInflater để tạo view từ layout tùy chỉnh
                LayoutInflater inflater = LayoutInflater.from(Ctrl_XemChiPhi.this);
                View dialogView = inflater.inflate(R.layout.custom_dialog_xoa, null);

                // Khởi tạo AlertDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(Ctrl_XemChiPhi.this);
                builder.setView(dialogView); // Set layout tùy chỉnh

                // Khởi tạo các thành phần trong layout tùy chỉnh
                TextView tvMessage = dialogView.findViewById(R.id.tvMessage);
                Button btnDelete = dialogView.findViewById(R.id.btnDelete);
                Button btnCancel = dialogView.findViewById(R.id.btnCancel);

                // Tạo AlertDialog
                AlertDialog dialog = builder.create();
                dialog.getWindow().setBackgroundDrawableResource(R.drawable.border_dialog); // Đặt nền cho dialog

                // Xử lý sự kiện cho nút Xóa
                btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Gọi phương thức xóa giao dịch từ cơ sở dữ liệu
                        deleteTransaction(transactionId); // Đảm bảo truyền đúng ID (String)

                        // Đóng dialog sau khi thực hiện xóa
                        dialog.dismiss();

                        // Quay lại giao diện danh sách giao dịch
                        Intent intent = new Intent(Ctrl_XemChiPhi.this, Ctrl_CacGiaoDich.class);
                        startActivity(intent);
                        finish(); // Hoặc sử dụng finish() để xóa activity hiện tại
                    }
                });

                // Xử lý sự kiện cho nút Hủy
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Đóng hộp thoại, không làm gì cả
                        dialog.dismiss();
                    }
                });

                // Hiển thị AlertDialog
                dialog.show();
            }
        });
    }

    private void deleteTransaction(String transactionId) {
        // Firebase database deletion logic
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        giaoDichRef = database.getReference("GiaoDich"); // Reference to the GiaoDich node

        // Delete transaction by ID
        giaoDichRef.child(transactionId).removeValue().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(Ctrl_XemChiPhi.this, "Giao dịch đã được xóa", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(Ctrl_XemChiPhi.this, "Xóa giao dịch thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void navigateToLoginScreen() {
        startActivity(new Intent(Ctrl_XemChiPhi.this, Ctrl_CacGiaoDich.class));
    }

}
