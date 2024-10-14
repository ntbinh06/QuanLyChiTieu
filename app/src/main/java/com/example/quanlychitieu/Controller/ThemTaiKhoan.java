package com.example.quanlychitieu.Controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.quanlychitieu.R;

public class ThemTaiKhoan extends Fragment {

    private EditText edtTenTaiKhoan, edtLuongBatDau, edtGhiChu;
    private Spinner spinDonViTien;
    private Button btnHuy, btnLuu;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate layout of the fragment
        View view = inflater.inflate(R.layout.fragment_them_tai_khoan, container, false);

        // Reference to views in the layout
        edtTenTaiKhoan = view.findViewById(R.id.edtTenTaiKhoan);
        edtLuongBatDau = view.findViewById(R.id.edtLuongBatDau);
        edtGhiChu = view.findViewById(R.id.edtGhiChu);
        spinDonViTien = view.findViewById(R.id.spin_bank_from);
        btnHuy = view.findViewById(R.id.btnCancel);
        btnLuu = view.findViewById(R.id.btnSave);

        // Handle click event for Cancel button
        btnHuy.setOnClickListener(v -> {
            // Go back to the previous screen
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        // Handle click event for Save button
        btnLuu.setOnClickListener(v -> {
            String tenTaiKhoan = edtTenTaiKhoan.getText().toString();
            String luongBatDau = edtLuongBatDau.getText().toString();
            String ghiChu = edtGhiChu.getText().toString();
            String donViTien = spinDonViTien.getSelectedItem().toString();

            // Validate input
            if (tenTaiKhoan.isEmpty() || luongBatDau.isEmpty()) {
                Toast.makeText(getContext(), "Vui lòng điền đủ thông tin", Toast.LENGTH_SHORT).show();
            } else {
                // Handle logic to save the new account (e.g., add to database or account list)
                replaceFragment(new ThongBaoThemTK());
            }
        });

        return view;
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}