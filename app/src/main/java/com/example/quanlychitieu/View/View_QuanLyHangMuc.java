package com.example.quanlychitieu.View;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.quanlychitieu.R;
import com.google.android.material.tabs.TabLayout;

public class View_QuanLyHangMuc extends AppCompatActivity {
    FrameLayout framelayout;
    TabLayout tablayout;
    ImageView add;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quanlyhangmuc);

        // Ánh xạ các view
        framelayout = findViewById(R.id.framelayout);
        tablayout = findViewById(R.id.tablayout);
        add = findViewById(R.id.add_HangMuc);

        // Sự kiện nhấn vào nút cộng để mở DialogFragment Thêm Hạng Mục
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openThemHangMucDialog();  // Mở DialogFragment
            }
        });

        // Hiển thị fragment mặc định nếu không có trạng thái được lưu lại
        if (savedInstanceState == null) {
            displayFragment(new Fragment_ThuNhap());
        }

        // Lắng nghe sự kiện chọn tab
        tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Fragment fragment = null;
                switch (tab.getPosition()) {
                    case 0:
                        fragment = new Fragment_ThuNhap();  // Fragment cho tab Thu Nhập
                        break;
                    case 1:
                        fragment = new Fragment_ChiPhi();  // Fragment cho tab Chi Phí
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
    }

    // Phương thức để hiển thị Fragment trong FrameLayout
    private void displayFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.framelayout, fragment);
        fragmentTransaction.commit();
    }

    // Mở DialogFragment "Thêm Hạng Mục"
    private void openThemHangMucDialog() {
        Fragment_Them_Hang_Muc dialog = new Fragment_Them_Hang_Muc();
        dialog.show(getSupportFragmentManager(), "ThemHangMuc");
    }
}
