package com.example.quanlychitieu.Controller;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlychitieu.Model.M_DanhMucHangMuc;
import com.example.quanlychitieu.Model.M_TaiKhoan;
import com.example.quanlychitieu.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class Ctrl_ChiTietNganSach extends AppCompatActivity {

    private String transactionId; // Biến để lưu transactionId
    private TextView txtTenHangMuc, txtSoTien, txtConLai, txtDaChi,tvDay;
    private ProgressBar pgbTienTrinh;
    private ImageView imgHangMuc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_ngan_sach);

        pgbTienTrinh=findViewById(R.id.pgbTienTrinh);
        txtTenHangMuc = findViewById(R.id.tvCategoryName);
        txtSoTien = findViewById(R.id.tvAmount);
        txtConLai = findViewById(R.id.tvRemainingMoney);
        txtDaChi = findViewById(R.id.tvSpentMoney);
        imgHangMuc = findViewById(R.id.imgCategory);
        tvDay = findViewById(R.id.tvDay);
        ImageButton ic_back = findViewById(R.id.ic_back);
        Button btnEdit = findViewById(R.id.btnEdit);
        Button btnDelete = findViewById(R.id.btnDeleteNganSach);

        Intent intent = getIntent();
        if (intent != null) {
            String idHangmuc = intent.getStringExtra("transactionId"); // Nhận ID dưới dạng String
            if (idHangmuc == null || idHangmuc.isEmpty()) {
                Toast.makeText(this, "ID không hợp lệ", Toast.LENGTH_SHORT).show();
                finish();
                return;
            }

            String tenHangMuc = intent.getStringExtra("tenHangMuc");
            double soTien = intent.getDoubleExtra("soTien", 0.0);
            double soTienConLai = intent.getDoubleExtra("soTienConLai", 0.0);
            double daChi = soTien - soTienConLai;


            txtTenHangMuc.setText(tenHangMuc);
            txtSoTien.setText(formatCurrency(soTien));
            txtConLai.setText(formatCurrency(soTienConLai));
            txtDaChi.setText(formatCurrency(daChi));
                // Cập nhật ProgressBar
            pgbTienTrinh.setMax((int) soTien); // Thiết lập giá trị tối đa cho ProgressBar
            pgbTienTrinh.setProgress((int) daChi); // Thiết lập giá trị hiện tại cho ProgressBar

            // Đổi màu ProgressBar nếu cần
            if (daChi >= soTien) {
                pgbTienTrinh.setProgressDrawable(getResources().getDrawable(R.drawable.progress_red)); // Đặt drawable màu đỏ nếu đã chi tiêu vượt ngân sách
            } else {
                pgbTienTrinh.setProgressDrawable(getResources().getDrawable(R.drawable.progress_bar)); // Đặt drawable màu mặc định
            }

            // Lưu transactionId
            transactionId = idHangmuc; // Lưu ID dưới dạng String
            // Truy vấn Firebase để lấy ngayTaoNganSach
            DatabaseReference hangMucRef = FirebaseDatabase.getInstance().getReference("HangMuc").child(transactionId);
            hangMucRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        Map<String, Object> ngayTaoNganSach = (Map<String, Object>) dataSnapshot.child("ngayTaoNganSach").getValue();
                        if (ngayTaoNganSach != null) {
                            int ngay = ((Long) ngayTaoNganSach.get("ngay")).intValue();
                            int thang = ((Long) ngayTaoNganSach.get("thang")).intValue();
                            int nam = ((Long) ngayTaoNganSach.get("nam")).intValue();

                            // Hiển thị ngày tạo ngân sách
                            String formattedDate = String.format("%02d/%02d/%d", ngay, thang, nam);
                            tvDay.setText(formattedDate); // txtNgayTao là TextView để hiển thị ngày
                        } else {
                            Toast.makeText(Ctrl_ChiTietNganSach.this, "Ngày tạo không tồn tại", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(Ctrl_ChiTietNganSach.this, "ID không tồn tại trong Firebase", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(Ctrl_ChiTietNganSach.this, "Lỗi: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }



        ic_back.setOnClickListener(view -> {
            Intent backIntent = new Intent(Ctrl_ChiTietNganSach.this, Ctrl_NganSach.class);
            startActivity(backIntent);
        });

        btnEdit.setOnClickListener(view -> {
            String idHangMuc = transactionId; // ID hạng mục
            DatabaseReference hangMucRef = FirebaseDatabase.getInstance().getReference("HangMuc").child(idHangMuc);

            hangMucRef.child("nganSachDuTru").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        Double currentNganSachDuTru = snapshot.getValue(Double.class);
                        if (currentNganSachDuTru != null) {
                            showEditDialog(idHangMuc, currentNganSachDuTru); // Gọi dialog sửa ngân sách
                        } else {
                            Toast.makeText(Ctrl_ChiTietNganSach.this, "Không tìm thấy ngân sách dự trù", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(Ctrl_ChiTietNganSach.this, "Không tìm thấy hạng mục", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(Ctrl_ChiTietNganSach.this, "Lỗi: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });


        btnDelete.setOnClickListener(view -> showDeleteDialog());
    }
    private void showEditDialog(String idHangMuc, double currentNganSachDuTru) {
        LayoutInflater inflater = LayoutInflater.from(Ctrl_ChiTietNganSach.this);
        View dialogView = inflater.inflate(R.layout.custom_dialog_edit_ngansach, null); // Tạo layout cho dialog

        AlertDialog.Builder builder = new AlertDialog.Builder(Ctrl_ChiTietNganSach.this);
        builder.setView(dialogView);

        TextView tvCurrentNganSach = dialogView.findViewById(R.id.tvCurrentNganSach); // TextView hiển thị ngân sách hiện tại
        TextView editNewNganSach = dialogView.findViewById(R.id.editNewNganSach);     // EditText để nhập ngân sách mới
        Button btnSave = dialogView.findViewById(R.id.btnSave);                      // Nút lưu
        Button btnCancel = dialogView.findViewById(R.id.btnCancel);                  // Nút hủy

        tvCurrentNganSach.setText(formatCurrency(currentNganSachDuTru)); // Hiển thị ngân sách hiện tại

        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.border_dialog);

        dialog.setOnShowListener(dialogInterface -> {
            AlertDialog alertDialog = (AlertDialog) dialog;
            WindowManager.LayoutParams params = new WindowManager.LayoutParams();
            params.copyFrom(alertDialog.getWindow().getAttributes());
            dialog.getWindow().setLayout(850, WindowManager.LayoutParams.WRAP_CONTENT);
        });

        btnSave.setOnClickListener(v -> {
            String newNganSachStr = editNewNganSach.getText().toString().trim();
            if (newNganSachStr.isEmpty()) {
                Toast.makeText(Ctrl_ChiTietNganSach.this, "Vui lòng nhập giá trị mới", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                double newNganSachDuTru = Double.parseDouble(newNganSachStr);

                DatabaseReference hangMucRef = FirebaseDatabase.getInstance().getReference("HangMuc").child(idHangMuc);
                Map<String, Object> updates = new HashMap<>();
                updates.put("nganSachDuTru", newNganSachDuTru);

                hangMucRef.updateChildren(updates).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(Ctrl_ChiTietNganSach.this, "Cập nhật ngân sách thành công", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Ctrl_ChiTietNganSach.this, Ctrl_NganSach.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(Ctrl_ChiTietNganSach.this, "Lỗi cập nhật: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (NumberFormatException e) {
                Toast.makeText(Ctrl_ChiTietNganSach.this, "Giá trị không hợp lệ", Toast.LENGTH_SHORT).show();
            }
        });

        btnCancel.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    private void showDeleteDialog() {
        LayoutInflater inflater = LayoutInflater.from(Ctrl_ChiTietNganSach.this);
        View dialogView = inflater.inflate(R.layout.custom_dialog_xoa, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(Ctrl_ChiTietNganSach.this);
        builder.setView(dialogView);

        TextView tvMessage = dialogView.findViewById(R.id.tvMessage);
        Button btnDelete = dialogView.findViewById(R.id.btnDelete);
        Button btnCancel = dialogView.findViewById(R.id.btnCancel);

        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.border_dialog);

        dialog.setOnShowListener(dialogInterface -> {
            AlertDialog alertDialog = (AlertDialog) dialog;
            WindowManager.LayoutParams params = new WindowManager.LayoutParams();
            params.copyFrom(alertDialog.getWindow().getAttributes());
            dialog.getWindow().setLayout(850, WindowManager.LayoutParams.WRAP_CONTENT);
        });

        btnDelete.setOnClickListener(v -> {
            String idHangmuc = transactionId; // Lấy ID đã lưu

            Log.d("DeleteHangMuc", "Đang xóa: " + idHangmuc);
            deleteNgansach(idHangmuc);  // Truyền transactionId vào deleteNgansach
            dialog.dismiss();
        });

        btnCancel.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    private void deleteNgansach(String idHangmuc) {
        DatabaseReference hangMucRef = FirebaseDatabase.getInstance().getReference("HangMuc").child(idHangmuc);

        hangMucRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Tiến hành xóa
                    hangMucRef.child("nganSachDuTru").removeValue()
                            .addOnCompleteListener(task1 -> {
                                if (task1.isSuccessful()) {
                                    // Xóa trường ngayTaoNganSach
                                    hangMucRef.child("ngayTaoNganSach").removeValue()
                                            .addOnCompleteListener(task2 -> {
                                                if (task2.isSuccessful()) {
                                                    Toast.makeText(Ctrl_ChiTietNganSach.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                                                    finish();
                                                } else {
                                                    Toast.makeText(Ctrl_ChiTietNganSach.this, "Lỗi xóa ngayTaoNganSach: " + task2.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                } else {
                                    Toast.makeText(Ctrl_ChiTietNganSach.this, "Lỗi xóa nganSachDuTru: " + task1.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                } else {
                    Toast.makeText(Ctrl_ChiTietNganSach.this, "ID không tồn tại trong Firebase", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(Ctrl_ChiTietNganSach.this, "Lỗi: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String formatCurrency(double amount) {
        return String.format("%,.2f đ", amount); // Định dạng với 2 chữ số thập phân
    }
}