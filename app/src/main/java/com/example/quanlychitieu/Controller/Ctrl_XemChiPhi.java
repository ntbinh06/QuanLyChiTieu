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
import com.example.quanlychitieu.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

        Intent intent = getIntent();
        if (intent != null) {
            String idHangmuc = intent.getStringExtra("idGiaoDich"); // Nhận ID dưới dạng String
            if (idHangmuc == null || idHangmuc.isEmpty()) {
                Toast.makeText(this, "ID không hợp lệ", Toast.LENGTH_SHORT).show();
                finish();
                return;
            }

            int hinhGD = intent.getIntExtra("hinhGD", 0);
            String tenGD = intent.getStringExtra("tenGD");
            String giaGD = intent.getStringExtra("tien");
            String tenTK = intent.getStringExtra("tenTK");
            String ngayGD = intent.getStringExtra("ngay");
            String tu = intent.getStringExtra("tu");
            String ghiChu = intent.getStringExtra("ghiChu");

            // Gán dữ liệu cho các View
            imgHinhGD.setImageResource(hinhGD);
            txtTenGD.setText(tenGD);
            txtGiaGD.setText(giaGD);
            txtTKGD.setText(tenTK);
            txtNgayGD.setText(ngayGD);
            txtGhiChu.setText(ghiChu);
            txtTu.setText(tu);

            // Lưu transactionId
            transactionId = idHangmuc; // Lưu ID dưới dạng String
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
