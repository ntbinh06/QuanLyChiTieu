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

public class Ctrl_XemChiPhi extends AppCompatActivity {
    private String transactionId;
    private M_GiaoDich giaoDich;
    private DatabaseReference giaoDichRef; // Firebase reference for the transaction

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xemchiphi); // Your layout file

        ImageButton ic_back = findViewById(R.id.ic_back);
        Button buttonsua = findViewById(R.id.btnSuaChiPhi);
        Button buttonxoa = findViewById(R.id.btnXoaChiPhi);

        // Khởi tạo các view
        ImageView imgHinhGD = findViewById(R.id.xemCTHinhGD);
        TextView txtTenGD = findViewById(R.id.xemCTTenGD);
        TextView txtGiaGD = findViewById(R.id.xemCTGiaGD);
        TextView txtTKGD = findViewById(R.id.xemCTTKGD);
        TextView txtNgayGD = findViewById(R.id.xemCTNgayGD);
        TextView txtTu = findViewById(R.id.txtTu);
        TextView txtGhiChu = findViewById(R.id.txtGhiChu);

        // Nhận idGiaoDich từ Intent
        Intent intent = getIntent();
        String transactionId = intent.getStringExtra("idGiaoDich");
        String idNhom = intent.getStringExtra("idNhom");

        // Ghi log để kiểm tra ID giao dịch
        Log.d("Ctrl_XemChiPhi", "transactionId: " + transactionId);

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
                                Toast.makeText(Ctrl_XemChiPhi.this, "Lỗi khi tải tên tài khoản", Toast.LENGTH_SHORT).show();
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

                                        // Hiển thị ảnh hạng mục
                                        String anhHangMuc = hangMuc.getAnhHangmuc(); // Lấy giá trị ảnh từ Firebase
                                        if (anhHangMuc != null && !anhHangMuc.isEmpty()) {
                                            int drawableId = getResources().getIdentifier(anhHangMuc, "drawable", getPackageName());
                                            if (drawableId != 0) {
                                                imgHinhGD.setImageResource(drawableId); // Gán ảnh vào ImageView
                                            } else {
                                                imgHinhGD.setImageResource(R.drawable.analysis); // Ảnh mặc định nếu không tìm thấy
                                            }
                                        } else {
                                            imgHinhGD.setImageResource(R.drawable.analysis); // Ảnh mặc định nếu `anhHangMuc` null
                                        }
                                    } else {
                                        txtTenGD.setText("Không có tên hạng mục");
                                    }
                                } else {
                                    txtTenGD.setText("Không có tên hạng mục");
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Toast.makeText(Ctrl_XemChiPhi.this, "Lỗi khi tải tên hạng mục", Toast.LENGTH_SHORT).show();
                            }
                        });

                        // Xử lý sự kiện nút Sửa
                        buttonsua.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(Ctrl_XemChiPhi.this, Ctrl_SuaChiPhi.class);
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
                    }
                } else {
                    Toast.makeText(Ctrl_XemChiPhi.this, "Giao dịch không tồn tại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(Ctrl_XemChiPhi.this, "Lỗi khi tải dữ liệu", Toast.LENGTH_SHORT).show();
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
                builder.setView(dialogView);

                // Khởi tạo các thành phần trong layout tùy chỉnh
                TextView tvMessage = dialogView.findViewById(R.id.tvMessage);
                Button btnDelete = dialogView.findViewById(R.id.btnDelete);
                Button btnCancel = dialogView.findViewById(R.id.btnCancel);

                // Tạo AlertDialog
                AlertDialog dialog = builder.create();
                dialog.getWindow().setBackgroundDrawableResource(R.drawable.border_dialog);

                // Xử lý sự kiện cho nút Xóa
                btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteTransaction(transactionId);
                        dialog.dismiss();
                        Intent intent = new Intent(Ctrl_XemChiPhi.this, Ctrl_CacGiaoDich.class);
                        startActivity(intent);
                        finish();
                    }
                });

                // Xử lý sự kiện cho nút Hủy
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
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
                Toast.makeText(Ctrl_XemChiPhi.this, "Giao dịch đã được xóa", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(Ctrl_XemChiPhi.this, "Lỗi khi xóa giao dịch", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void navigateToLoginScreen() {
        startActivity(new Intent(Ctrl_XemChiPhi.this, Ctrl_CacGiaoDich.class));
    }
}