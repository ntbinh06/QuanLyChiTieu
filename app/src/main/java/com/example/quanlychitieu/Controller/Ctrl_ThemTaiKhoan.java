package com.example.quanlychitieu.Controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.quanlychitieu.Model.M_TaiKhoan;
import com.example.quanlychitieu.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Date;

public class Ctrl_ThemTaiKhoan extends Fragment {

    private EditText edtTenTaiKhoan, edtLuongBatDau, edtGhiChu;
    private TextView DonViTien;
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
        DonViTien = view.findViewById(R.id.DonViTien);
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
            String luongBatDauStr = edtLuongBatDau.getText().toString();
            String ghiChu = edtGhiChu.getText().toString();
            String donViTien = DonViTien.getText().toString();

            // Validate input
            if (tenTaiKhoan.isEmpty() || luongBatDauStr.isEmpty()) {
                Toast.makeText(getContext(), "Vui lòng điền đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                // Parse lương bắt đầu từ chuỗi sang số
                double luongBatDau = Double.parseDouble(luongBatDauStr);

                // Lấy userId hiện tại từ Firebase Authentication
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                String userId = currentUser != null ? currentUser.getUid() : "default_user";

                // Tạo đối tượng M_TaiKhoan mới
                M_TaiKhoan taiKhoanMoi = new M_TaiKhoan(
                        "", // idTaiKhoan sẽ được tự động thêm trong addTaiKhoanToFirebase
                        tenTaiKhoan,
                        luongBatDau,
                        new Date(), // ngayTao là ngày hiện tại
                        new Date(), // lanSuDungCuoi là ngày hiện tại
                        donViTien,
                        ghiChu,
                        userId

                    );

                // Gọi hàm thêm tài khoản vào Firebase
                ((Ctrl_CacTaiKhoan) requireActivity()).addTaiKhoanToFirebase(taiKhoanMoi);

                // Chuyển sang màn hình thông báo hoặc danh sách
                replaceFragment(new Ctrl_ThongBaoThemTK());
            } catch (NumberFormatException e) {
                Toast.makeText(getContext(), "Lương bắt đầu không hợp lệ", Toast.LENGTH_SHORT).show();
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
