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
    FrameLayout framelayout, framgent_ThemHM;
    TabLayout tablayout;
    ImageView add;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quanlyhangmuc);

        // Ánh xạ các view
        framelayout = findViewById(R.id.framelayout);
        framgent_ThemHM = findViewById(R.id.framgent_ThemHM);
        tablayout = findViewById(R.id.tablayout);
        add = findViewById(R.id.add_HangMuc);

        //sự kiện nhấn vào nút cộng thì hiện thị fragment thêm hạng mục
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                framelayout.setVisibility(View.GONE);  // Ẩn fragment chính
                framgent_ThemHM.setVisibility(View.VISIBLE);  // Hiển thị fragment thêm hạng mục
                openFragment(new Fragment_Them_Hang_Muc());
            }
        });

        if (savedInstanceState == null) {
            displayFragment(new Fragment_ThuNhap());
        }

        tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Fragment fragment = null;
                switch (tab.getPosition()) {
                    case 0:
                        fragment = new Fragment_ThuNhap(); // Fragment cho tab Thu Nhập
                        break;
                    case 1:
                        fragment = new Fragment_ChiPhi(); // Fragment cho tab Chi Phí
                        break;
                }
                if (fragment != null) {
                    displayFragment(fragment);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }

            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });
    }

    // Phương thức để thay thế và hiển thị Fragment vào FrameLayout
    private void displayFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.framelayout, fragment);
        fragmentTransaction.commit();
    }
    //hàm cho phép mở trang ThemHangMuc
    private void openFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.framgent_ThemHM, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    @Override
    public void onBackPressed() {
        if (framgent_ThemHM.getVisibility() == View.VISIBLE) {
            framgent_ThemHM.setVisibility(View.GONE);  // Ẩn framgent_ThemHM
            framelayout.setVisibility(View.VISIBLE);  // Hiển thị lại framelayout
        } else {
            super.onBackPressed();  // Quay lại màn hình trước đó
        }
    }
}
