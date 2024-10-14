package com.example.quanlychitieu.View;

import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.quanlychitieu.R;
import com.google.android.material.tabs.TabLayout;

public class View_QuanLyHangMuc extends AppCompatActivity {
    FrameLayout framelayout;
    TabLayout tablayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quanlyhangmuc);

        framelayout = findViewById(R.id.framelayout);
        tablayout = findViewById(R.id.tablayout);

        // Thiết lập tab đầu tiên mặc định
        if (savedInstanceState == null) {
            tablayout.getTabAt(0).select(); // Chọn tab đầu tiên
        }

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
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.framelayout, fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // Có thể để trống hoặc xử lý nếu cần
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // Có thể để trống hoặc xử lý nếu cần
            }
        });
    }
}