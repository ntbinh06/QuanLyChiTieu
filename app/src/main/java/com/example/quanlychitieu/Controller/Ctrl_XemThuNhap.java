package com.example.quanlychitieu.Controller;

import android.app.AlertDialog;
import android.content.Intent;
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

public class Ctrl_XemThuNhap extends AppCompatActivity {

    private String transactionId; // ID của giao dịch cần xóa, bạn sẽ lấy giá trị này từ Intent

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xemthunhap); // Your layout file

        ImageButton ic_back = findViewById(R.id.ic_back);
        Button buttonsua = findViewById(R.id.btnSuaThuNhap);
        Button buttonxoa = findViewById(R.id.btnXoaThuNhap);

        // Liên kết các View với mã Java
        ImageView imgHinhGD = findViewById(R.id.xemCTHinhGD);
        TextView txtTenGD = findViewById(R.id.xemCTTenGD);
        TextView txtGiaGD = findViewById(R.id.xemCTGiaGD);
        TextView txtTKGD = findViewById(R.id.xemCTTKGD);
        TextView txtNgayGD = findViewById(R.id.xemCTNgayGD);
        TextView txtTu = findViewById(R.id.txtTu);
        TextView txtGhiChu = findViewById(R.id.txtGhiChu);

        // Nhận idGiaoDich từ Intent
        Intent intent = getIntent();
        transactionId = intent.getStringExtra("idGiaoDich");

        // Ghi log để kiểm tra ID giao dịch
        Log.d("Ctrl_XemThuNhap", "transactionId: " + transactionId);

        // Kiểm tra ID giao dịch
        if (transactionId == null || transactionId.isEmpty()) {
            Toast.makeText(this, "ID không hợp lệ", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Thiết lập Firebase reference
        DatabaseReference giaoDichRef = FirebaseDatabase.getInstance().getReference("GiaoDich").child(transactionId);

        // Lấy dữ liệu giao dịch từ Firebase
        giaoDichRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    M_GiaoDich giaoDich = dataSnapshot.getValue(M_GiaoDich.class);
                    if (giaoDich != null) {
                        txtGiaGD.setText(String.valueOf(giaoDich.getGiaTri()));
                        txtGhiChu.setText(giaoDich.getGhiChu() != null ? giaoDich.getGhiChu() : "Không có ghi chú");
                        txtTu.setText(giaoDich.getTu() != null ? giaoDich.getTu() : "Không có thông tin");
                        txtNgayGD.setText(giaoDich.getFormattedNgayTao());

                        // Lấy tên tài khoản
                        String idTaiKhoan = giaoDich.getIdTaiKhoan();
                        DatabaseReference taiKhoanRef = FirebaseDatabase.getInstance().getReference("TaiKhoan").child(idTaiKhoan);
                        taiKhoanRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    M_TaiKhoan taiKhoan = dataSnapshot.getValue(M_TaiKhoan.class);
                                    if (taiKhoan != null) {
                                        txtTKGD.setText(taiKhoan.getTenTaiKhoan() != null ? taiKhoan.getTenTaiKhoan() : "Không có tên tài khoản");
                                    } else {
                                        txtTKGD.setText("Không có tên tài khoản");
                                    }
                                } else {
                                    txtTKGD.setText("Không có tên tài khoản");
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Toast.makeText(Ctrl_XemThuNhap.this, "Lỗi khi tải tên tài khoản", Toast.LENGTH_SHORT).show();
                            }
                        });

                        // Lấy tên hạng mục
                        String idHangMuc = giaoDich.getIdHangMuc();
                        DatabaseReference hangMucRef = FirebaseDatabase.getInstance().getReference("HangMuc").child(idHangMuc);
                        hangMucRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    M_DanhMucHangMuc hangMuc = dataSnapshot.getValue(M_DanhMucHangMuc.class);
                                    if (hangMuc != null) {
                                        txtTenGD.setText(hangMuc.getTenHangmuc() != null ? hangMuc.getTenHangmuc() : "Không có tên hạng mục");
                                    } else {
                                        txtTenGD.setText("Không có tên hạng mục");
                                    }
                                } else {
                                    txtTenGD.setText("Không có tên hạng mục");
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Toast.makeText(Ctrl_XemThuNhap.this, "Lỗi khi tải tên hạng mục", Toast.LENGTH_SHORT).show();
                            }
                        });

                        // Xử lý sự kiện nút Sửa
                        buttonsua.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(Ctrl_XemThuNhap.this, Ctrl_SuaThuNhap.class);
                                intent.putExtra("idGiaoDich", transactionId);
                                intent.putExtra("giaTri", giaoDich.getGiaTri());
                                intent.putExtra("ngayTao", giaoDich.getFormattedNgayTao());
                                intent.putExtra("ghiChu", giaoDich.getGhiChu());
                                intent.putExtra("tu", giaoDich.getTu());
                                intent.putExtra("idHangMuc", giaoDich.getIdHangMuc());
                                intent.putExtra("idTaiKhoan", giaoDich.getIdTaiKhoan());
                                startActivity(intent);
                            }
                        });
                    } else {
                        Toast.makeText(Ctrl_XemThuNhap.this, "Dữ liệu giao dịch không hợp lệ", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Ctrl_XemThuNhap.this, "Giao dịch không tồn tại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(Ctrl_XemThuNhap.this, "Lỗi khi tải dữ liệu", Toast.LENGTH_SHORT).show();
            }
        });

        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Ctrl_XemThuNhap.this, Ctrl_CacGiaoDich.class);
                startActivity(intent);
            }
        });

        buttonxoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo LayoutInflater để tạo view từ layout tùy chỉnh
                LayoutInflater inflater = LayoutInflater.from(Ctrl_XemThuNhap.this);
                View dialogView = inflater.inflate(R.layout.custom_dialog_xoa, null);

                // Khởi tạo AlertDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(Ctrl_XemThuNhap.this);
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
                        deleteTransaction(transactionId);

                        // Đóng dialog sau khi thực hiện xóa
                        dialog.dismiss();

                        // Quay lại giao diện danh sách giao dịch
                        Intent intent = new Intent(Ctrl_XemThuNhap.this, Ctrl_CacGiaoDich.class);
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
        DatabaseReference giaoDichRef = FirebaseDatabase.getInstance().getReference("GiaoDich").child(transactionId);
        giaoDichRef.removeValue().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(Ctrl_XemThuNhap.this, "Giao dịch đã được xóa", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(Ctrl_XemThuNhap.this, "Lỗi khi xóa giao dịch", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void navigateToLoginScreen() {
        startActivity(new Intent(Ctrl_XemThuNhap.this, Ctrl_CacGiaoDich.class));
    }
}