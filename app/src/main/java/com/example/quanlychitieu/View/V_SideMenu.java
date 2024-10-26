package com.example.quanlychitieu.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.quanlychitieu.Controller.C_NganSach;
import com.example.quanlychitieu.Controller.C_SideMenu;
import com.example.quanlychitieu.Controller.CacGiaoDich;
import com.example.quanlychitieu.Controller.CacTaiKhoan;
import com.example.quanlychitieu.Controller.ChuyenTien;
import com.example.quanlychitieu.Controller.Ctrl_DanhSachUser;
import com.example.quanlychitieu.Controller.Ctrl_NguoiDung;
import com.example.quanlychitieu.Controller.Ctrl_ThemChiPhi;
import com.example.quanlychitieu.Controller.Ctrl_ThemThuNhap;
import com.example.quanlychitieu.Controller.TongQuan;
import com.example.quanlychitieu.Controller.View_QuanLyHangMuc;
import com.example.quanlychitieu.Model.M_SideMenu;
import com.example.quanlychitieu.R;

import java.util.ArrayList;

public class V_SideMenu extends AppCompatActivity {

    private ListView lvContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.tongquan_menu);



        // Áp dụng insets cho layout
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.tongquan_menu), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment); // 'fragment_container' là ID của FrameLayout chứa Fragment
        transaction.addToBackStack(null); // Thêm vào BackStack nếu muốn quay lại
        transaction.commit();
    }
}