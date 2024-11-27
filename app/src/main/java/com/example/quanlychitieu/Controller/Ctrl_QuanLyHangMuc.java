package com.example.quanlychitieu.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.quanlychitieu.Model.M_DanhMucHangMuc;
import com.example.quanlychitieu.Model.M_NhomHangMuc;
import com.example.quanlychitieu.R;
import com.example.quanlychitieu.View.Fragment_ChiPhi;
import com.example.quanlychitieu.View.Fragment_Them_Hang_Muc;
import com.example.quanlychitieu.View.Fragment_ThuNhap;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Ctrl_QuanLyHangMuc extends AppCompatActivity {
    FrameLayout framelayout;
    TabLayout tablayout;
    ImageView addHangMuc, iconback;
    private DatabaseReference nhomHangMucRef, hangMucRef;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quanlyhangmuc);

        // Khởi tạo tham chiếu Firebase
        nhomHangMucRef = FirebaseDatabase.getInstance().getReference("NhomHangMuc");
        hangMucRef = FirebaseDatabase.getInstance().getReference("HangMuc");

        // Thêm danh sách nhóm hạng mục vào Firebase
        //addNhomHangMucToFirebase(createSampleNhomHangMuc());

        // Thêm danh sách hạng mục vào Firebase
        //addHangMucToFirebase(createSampleHangMuc());

        // Ánh xạ các view
        framelayout = findViewById(R.id.framelayout);
        tablayout = findViewById(R.id.tablayout);
        addHangMuc = findViewById(R.id.add_HangMuc);
        iconback = findViewById(R.id.ic_back);

        // Sự kiện click vào nút quay lại
        iconback.setOnClickListener(v -> {
            Intent intent = new Intent(Ctrl_QuanLyHangMuc.this, Ctrl_TongQuan.class);
            startActivity(intent);
        });

        // Mặc định hiển thị Fragment Thu Nhập
        if (savedInstanceState == null) {
            displayFragment(new Fragment_ThuNhap());
        }

        // Thay đổi Fragment khi chọn tab
        tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Fragment fragment = null;
                switch (tab.getPosition()) {
                    case 0:
                        fragment = new Fragment_ThuNhap();
                        break;
                    case 1:
                        fragment = new Fragment_ChiPhi();
                        break;
                }
                if (fragment != null) {
                    displayFragment(fragment);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

        // Sự kiện click để mở DialogFragment thêm hạng mục
        addHangMuc.setOnClickListener(v -> openDialogFragment());
    }

    // Phương thức hiển thị Fragment
    private void displayFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.framelayout, fragment);
        fragmentTransaction.commit();
    }

    // Mở DialogFragment để thêm hạng mục
    private void openDialogFragment() {
        Fragment_Them_Hang_Muc dialogFragment = new Fragment_Them_Hang_Muc();
        dialogFragment.show(getSupportFragmentManager(), "ThemHangMuc");
    }

    // Thêm danh sách nhóm hạng mục mẫu vào Firebase
    private List<M_NhomHangMuc> createSampleNhomHangMuc() {
        List<M_NhomHangMuc> danhSachNhomHM = new ArrayList<>();
        danhSachNhomHM.add(new M_NhomHangMuc("1", "Nhóm Thu Nhập"));
        danhSachNhomHM.add(new M_NhomHangMuc("2", "Nhóm Chi Phí"));
        return danhSachNhomHM;
    }

    private void addNhomHangMucToFirebase(List<M_NhomHangMuc> danhSachNhomHM) {
        for (M_NhomHangMuc nhomHangMuc : danhSachNhomHM) {
            nhomHangMucRef.child(nhomHangMuc.getIdNhom()).setValue(nhomHangMuc)
                    .addOnSuccessListener(aVoid -> Log.d("Firebase", "Thêm nhóm hạng mục thành công: " + nhomHangMuc.getTenNhom()))
                    .addOnFailureListener(e -> Log.e("Firebase", "Lỗi thêm nhóm hạng mục: " + e.getMessage()));
        }
    }

    // Thêm danh sách hạng mục mẫu vào Firebase
    private List<M_DanhMucHangMuc> createSampleHangMuc() {
        List<M_DanhMucHangMuc> danhSachHM = new ArrayList<>();
        danhSachHM.add(new M_DanhMucHangMuc("1", "Tien Dien", "hthp/...jpg", null, "2", "DSEZCk7wpuPyegmLbv66Tn6HjCz1"));
        danhSachHM.add(new M_DanhMucHangMuc("2", "Giai tri", "/.jpg2", null, "2", "DSEZCk7wpuPyegmLbv66Tn6HjCz1"));
        danhSachHM.add(new M_DanhMucHangMuc("3", "Luong", "/.jpg3", null, "1", "DSEZCk7wpuPyegmLbv66Tn6HjCz1"));
        danhSachHM.add(new M_DanhMucHangMuc("4", "An Uong", "/an_uong.jpg", null, "1", "DSEZCk7wpuPyegmLbv66Tn6HjCz1"));
        danhSachHM.add(new M_DanhMucHangMuc("5", "Du Lich", "/du_lich.jpg", null, "2", "DSEZCk7wpuPyegmLbv66Tn6HjCz1"));
        danhSachHM.add(new M_DanhMucHangMuc("6", "Y Te", "/y_te.jpg", null, "2", "DSEZCk7wpuPyegmLbv66Tn6HjCz1"));
        return danhSachHM;
    }

    private void addHangMucToFirebase(List<M_DanhMucHangMuc> danhSachHM) {
        for (M_DanhMucHangMuc hangMuc : danhSachHM) {
            hangMucRef.child(hangMuc.getIdHangmuc()).setValue(hangMuc)
                    .addOnSuccessListener(aVoid -> Log.d("Firebase", "Thêm hạng mục thành công: " + hangMuc.getTenHangmuc()))
                    .addOnFailureListener(e -> Log.e("Firebase", "Lỗi thêm hạng mục: " + e.getMessage()));
        }
    }
}
