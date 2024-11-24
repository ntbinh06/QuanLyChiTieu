package com.example.quanlychitieu.Controller;

import static android.app.PendingIntent.getActivity;

import android.content.Intent;
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
import com.example.quanlychitieu.View.Fragment_ChiPhi;
import com.example.quanlychitieu.View.Fragment_Them_Hang_Muc;
import com.example.quanlychitieu.View.Fragment_ThuNhap;
import com.google.android.material.tabs.TabLayout;

public class Ctrl_QuanLyHangMuc extends AppCompatActivity {
    FrameLayout framelayout;
    TabLayout tablayout;
    ImageView addHangMuc, iconback;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quanlyhangmuc);

        // Ánh xạ các view
        framelayout = findViewById(R.id.framelayout);
        tablayout = findViewById(R.id.tablayout);
        addHangMuc = findViewById(R.id.add_HangMuc);
        iconback = findViewById(R.id.ic_back);// Ánh xạ ImageView

        // Thêm sự kiện click vào ImageView để mở DialogFragment
        addHangMuc.setOnClickListener(v -> openDialogFragment());
        ImageView ic_back = findViewById(R.id.ic_back);

        ic_back.setOnClickListener(v -> {
            Intent intent = new Intent(Ctrl_QuanLyHangMuc.this, Ctrl_TongQuan.class);
            startActivity(intent);
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

        iconback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Ctrl_QuanLyHangMuc.this, Ctrl_TongQuan.class));
            }
        });


    }

    // Phương thức để thay thế và hiển thị Fragment vào FrameLayout
    private void displayFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.framelayout, fragment);
        fragmentTransaction.commit();
    }
    // Phương thức để mở DialogFragment
    private void openDialogFragment() {
        Fragment_Them_Hang_Muc dialogFragment = new Fragment_Them_Hang_Muc();
        dialogFragment.show(getSupportFragmentManager(), "ThemHangMuc");
    }

}
