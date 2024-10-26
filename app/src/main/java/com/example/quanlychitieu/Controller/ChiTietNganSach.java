package com.example.quanlychitieu.Controller;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlychitieu.R;

public class ChiTietNganSach extends AppCompatActivity {

    private int transactionId;
    private TextView txtTenHangMuc, txtSoTien, txtConLai, txtDaChi;
    private ImageView imgHangMuc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_ngan_sach);

        txtTenHangMuc = findViewById(R.id.tvCategoryName);
        txtSoTien = findViewById(R.id.tvAmount);
        txtConLai = findViewById(R.id.tvRemainingMoney);
        txtDaChi = findViewById(R.id.tvSpentMoney);
        imgHangMuc = findViewById(R.id.imgCategory);
        ImageButton ic_back = findViewById(R.id.ic_back);
        Button btnEdit = findViewById(R.id.btnEdit);
        Button btnDelete = findViewById(R.id.btnDeleteNganSach);

        Intent intent = getIntent();
        if (intent != null) {
            int transactionId = intent.getIntExtra("transactionId", -1);
            String tenHangMuc = intent.getStringExtra("tenHangMuc");
            long soTien = intent.getLongExtra("soTien", 0);
            long soTienConLai = intent.getLongExtra("soTienConLai", 0);
            int hinhAnh = intent.getIntExtra("hinhAnh", -1);


            long daChi = soTien - soTienConLai;

            txtTenHangMuc.setText(tenHangMuc);
            txtSoTien.setText(formatCurrency(soTien));
            txtConLai.setText(formatCurrency(soTienConLai));
            txtDaChi.setText(formatCurrency(daChi));
        }

        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChiTietNganSach.this, C_NganSach.class);
                startActivity(intent);
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChiTietNganSach.this, NganSachMoi.class);
                startActivity(intent);
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Tạo LayoutInflater để tạo view từ layout tùy chỉnh
                LayoutInflater inflater = LayoutInflater.from(ChiTietNganSach.this);
                View dialogView = inflater.inflate(R.layout.custom_dialog_xoa, null);

                // Khởi tạo AlertDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(ChiTietNganSach.this);
                builder.setView(dialogView); // Set layout tùy chỉnh


                // Khởi tạo các thành phần trong layout tùy chỉnh
                TextView tvMessage = dialogView.findViewById(R.id.tvMessage);
                Button btnDelete = dialogView.findViewById(R.id.btnDelete);
                Button btnCancel = dialogView.findViewById(R.id.btnCancel);

                // Tạo AlertDialog
                AlertDialog dialog = builder.create();
                dialog.getWindow().setBackgroundDrawableResource(R.drawable.border_dialog);// Đặt nền cho dialog

                dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialogInterface) {
                        AlertDialog alertDialog = (AlertDialog) dialog;
                        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
                        params.copyFrom(alertDialog.getWindow().getAttributes()); // Lấy thuộc tính hiện tại của dialog
                        params.width = 850; // Hoặc một giá trị cụ thể như 800
                        params.height = WindowManager.LayoutParams.WRAP_CONTENT; // Hoặc một giá trị cụ thể
                        alertDialog.getWindow().setAttributes(params); // Áp dụng các thuộc tính mới
                    }
                });

                // Xử lý sự kiện cho nút Xóa
                btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Gọi phương thức xóa giao dịch từ cơ sở dữ liệu
                        deleteTransaction(transactionId);

                        // Đóng dialog sau khi thực hiện xóa
                        dialog.dismiss();

                        // Quay lại giao diện danh sách giao dịch
                        Intent intent = new Intent(ChiTietNganSach.this, C_NganSach.class);
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

    private void deleteTransaction(int transactionId) {
        // Thêm logic xóa giao dịch từ cơ sở dữ liệu tại đây
        // Ví dụ:
        // SQLiteDatabase db = getWritableDatabase();
        // db.delete("transactions", "id=?", new String[]{String.valueOf(transactionId)});
    }

    private void navigateToLoginScreen() {
        startActivity(new Intent(ChiTietNganSach.this, C_NganSach.class));
    }

    private String formatCurrency(long amount) {
        return String.format("%,d đ", amount);
    }
}
