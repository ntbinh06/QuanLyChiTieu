package com.example.quanlychitieu.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.quanlychitieu.Model.M_DanhMucTaiKhoan;
import com.example.quanlychitieu.R;
import com.example.quanlychitieu.View.V_ItemCacTK;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class Ctrl_CacTaiKhoan extends AppCompatActivity {

    String[] tenTK = {"Ví", "Tài khoản ngân hàng", "Tài khoản trả trước"};
    String[] tien = {"400.000", "750.000", "2.000.000"};

    ArrayList<M_DanhMucTaiKhoan> taikhoan;
    V_ItemCacTK item;
    ListView lv;
    private DatabaseReference taiKhoanref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cac_tai_khoan);

        // Khởi tạo ListView và adapter
        lv = findViewById(R.id.listView1);
        taikhoan = new ArrayList<>();
        taiKhoanref= FirebaseDatabase.getInstance().getReference("taiKhoan");

        // Thêm 5 tài khoản vào Firebase
        addTaiKhoanToFirebase("TK001", "Tài khoản ví", 400000.0, "2024-11-25", "2024-11-26", "VND", "Ví tiền mặt", "DSEZCk7wpuPyegmLbv66Tn6HjCz1");
        addTaiKhoanToFirebase("TK002", "Tài khoản ngân hàng", 750000.0, "2024-11-25", "2024-11-26", "VND", "Tài khoản ngân hàng chính", "DSEZCk7wpuPyegmLbv66Tn6HjCz1");
        addTaiKhoanToFirebase("TK003", "Tài khoản trả trước", 2000000.0, "2024-11-25", "2024-11-26", "VND", "Tài khoản dành cho giao dịch trả trước", "DSEZCk7wpuPyegmLbv66Tn6HjCz1");
        addTaiKhoanToFirebase("TK004", "Tài khoản tiết kiệm", 1500000.0, "2024-11-25", "2024-11-26", "VND", "Tài khoản tiết kiệm dài hạn", "DSEZCk7wpuPyegmLbv66Tn6HjCz1");
        addTaiKhoanToFirebase("TK005", "Tài khoản đầu tư", 3000000.0, "2024-11-25", "2024-11-26", "VND", "Tài khoản dành cho đầu tư", "DSEZCk7wpuPyegmLbv66Tn6HjCz1");

        for (int i = 0; i < tenTK.length; i++) {
            taikhoan.add(new M_DanhMucTaiKhoan(tenTK[i], tien[i]));
        }
        item = new V_ItemCacTK(this, R.layout.list_item_taikhoan, taikhoan);
        lv.setAdapter(item);

        // Xử lý sự kiện cho các nút
        ImageView ic_back = findViewById(R.id.ic_back);
        ImageView ic_add = findViewById(R.id.ic_add);

        ic_back.setOnClickListener(v -> {
            Intent intent = new Intent(Ctrl_CacTaiKhoan.this, Ctrl_TongQuan.class);
            startActivity(intent);
        });

        ic_add.setOnClickListener(v -> {
            // Mở Fragment để thêm tài khoản
            openFragment(new Ctrl_ThemTaiKhoan());
        });

        // Áp dụng insets cho layout
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    // Hàm mở Fragment
    private void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment); // Đảm bảo ID này khớp với ID trong layout
        transaction.addToBackStack(null); // Cho phép quay lại màn hình trước
        transaction.commit();

    }
    private void addTaiKhoanToFirebase(String idTaiKhoan,String tenTaiKhoan, Double luongBanDau, String ngayTao, String lanSuDungCuoi, String donViTienTe, String ghiChu, String idUser) {
        // Tạo key tự động
        DatabaseReference taiKhoanRef = FirebaseDatabase.getInstance().getReference("TaiKhoan");

        // Tạo một đối tượng HashMap để lưu dữ liệu
        HashMap<String, Object> data = new HashMap<>();
        data.put("idTaiKhoan", idTaiKhoan);
        data.put("tenTaiKhoan", tenTaiKhoan);
        data.put("luongBanDau", luongBanDau != null ? luongBanDau : 0.0);
        data.put("ngayTao", ngayTao);
        data.put("lanSuDungCuoi", lanSuDungCuoi);
        data.put("donViTienTe", donViTienTe);
        data.put("ghiChu", ghiChu);
        data.put("idUser", idUser);

        // Thêm dữ liệu vào Firebase
        taiKhoanRef.child(idTaiKhoan).setValue(data).addOnSuccessListener(aVoid -> {
            Log.d("Firebase", "ThemTaiKhoan thanh cong ");
            Toast.makeText(this, "ThemTaiKhoan thanh cong!", Toast.LENGTH_SHORT).show();
        }).addOnFailureListener(e -> {
            Log.e("Firebase", "ThemTaiKhoan that bai ");
            Toast.makeText(this, "Them tai khoan that bai: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        });
    }

}