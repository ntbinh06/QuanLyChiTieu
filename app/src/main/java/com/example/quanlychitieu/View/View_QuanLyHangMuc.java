package com.example.quanlychitieu.View;

import android.os.Bundle;
import android.widget.FrameLayout;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quanlyhangmuc);

        // Ánh xạ các view
        framelayout = findViewById(R.id.framelayout);
        tablayout = findViewById(R.id.tablayout);


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
}
