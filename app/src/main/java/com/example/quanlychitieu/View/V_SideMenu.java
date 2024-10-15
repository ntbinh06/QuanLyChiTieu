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

import com.example.quanlychitieu.Controller.C_NganSach;
import com.example.quanlychitieu.Controller.C_SideMenu;
import com.example.quanlychitieu.Controller.CacGiaoDich;
import com.example.quanlychitieu.Controller.CacTaiKhoan;
import com.example.quanlychitieu.Controller.ChuyenTien;
import com.example.quanlychitieu.Controller.Ctrl_DanhSachUser;
import com.example.quanlychitieu.Controller.Ctrl_NguoiDung;
import com.example.quanlychitieu.Controller.Ctrl_QuanLyHangMuc;
import com.example.quanlychitieu.Controller.Ctrl_ThemChiPhi;
import com.example.quanlychitieu.Controller.Ctrl_ThemThuNhap;
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

        lvContact = findViewById(R.id.lvMenu);
        ArrayList<M_SideMenu> arrContact = new ArrayList<>();

        // Tạo các đối tượng M_SideMenu
        M_SideMenu contact1 = new M_SideMenu( R.drawable.tongquan, "Tổng quan");
        M_SideMenu contact2 = new M_SideMenu(R.drawable.wallet, "Giao dịch");
        M_SideMenu contact3 = new M_SideMenu( R.drawable.banking_finance_bank_icon,"Các tài khoản");
        M_SideMenu contact4 = new M_SideMenu(R.drawable.moneybag_icon,"Ngân sách");
        M_SideMenu contact5 = new M_SideMenu(R.drawable.user_icon, "Người dùng");
        M_SideMenu contact6 = new M_SideMenu( R.drawable.baseline_category_24,"Quản lý hạng mục");
        M_SideMenu contact7 = new M_SideMenu( R.drawable.baseline_change_circle_24,"Chuyển tiền");
        M_SideMenu contact8 = new M_SideMenu(R.drawable.baseline_add_circle_24,"Thêm thu nhập");
        M_SideMenu contact9 = new M_SideMenu( R.drawable.baseline_remove_circle_24,"Thêm chi phí");

        arrContact.add(contact1);
        arrContact.add(contact2);
        arrContact.add(contact3);
        arrContact.add(contact4);
        arrContact.add(contact5);
        arrContact.add(contact6);
        arrContact.add(contact7);
        arrContact.add(contact8);
        arrContact.add(contact9);

        // Tạo adapter và thiết lập cho ListView
        C_SideMenu customAdapter = new C_SideMenu(this, R.layout.listview_menu, arrContact);
        lvContact.setAdapter(customAdapter);

        // Thiết lập sự kiện khi nhấn vào một mục trong ListView
        lvContact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0: // "Tổng quan"
                        startActivity(new Intent(V_SideMenu.this, Fragment_TongQuan.class));
                        break;
                    case 1: // "Giao dịch"
                        startActivity(new Intent(V_SideMenu.this, CacGiaoDich.class));
                        break;
                    case 2: // "Các tài khoản"
                        startActivity(new Intent(V_SideMenu.this, CacTaiKhoan.class));
                        break;
                    case 3: // "Ngân sách"
                        startActivity(new Intent(V_SideMenu.this, C_NganSach.class));
                        break;
                    case 4: // "Người dùng"
                        startActivity(new Intent(V_SideMenu.this, Ctrl_NguoiDung.class));
                        break;
                    case 5: // "Quản lý hạng mục"
                        startActivity(new Intent(V_SideMenu.this, View_QuanLyHangMuc.class));
                        break;
                    case 6: // "Chuyển tiền"
                        startActivity(new Intent(V_SideMenu.this, ChuyenTien.class));
                        break;
                    case 7: // "Thêm thu nhập"
                        startActivity(new Intent(V_SideMenu.this, Ctrl_ThemThuNhap.class));
                        break;
                    case 8: // "Thêm chi phí"
                        startActivity(new Intent(V_SideMenu.this, Ctrl_ThemChiPhi.class));
                        break;
                }
            }
        });

        // Áp dụng insets cho layout
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.tongquan_menu), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}