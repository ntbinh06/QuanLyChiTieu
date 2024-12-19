package com.example.quanlychitieu.View;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.quanlychitieu.Controller.Ctrl_DoiMatKhau;
import com.example.quanlychitieu.Controller.Ctrl_NguoiDung;
import com.example.quanlychitieu.Controller.Ctrl_ThongTinCaNhan;
import com.example.quanlychitieu.Controller.Ctrl_TongQuan;
import com.example.quanlychitieu.Controller.Ctrl_TrangChuDNDK;
import com.example.quanlychitieu.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Fragment_NguoiDung extends Fragment {

    private ImageView ivUserAvatar, ic_back;
    private Button btnDangXuat, btnXoaTaiKhoan;
    private TextView txtHeaderTenUser, txtHeaderEmailUser, txtTenUser, txtEamil, txtDateBirth, txtSDT;
    private LinearLayout thongtinCN;

    public Fragment_NguoiDung() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_nguoi_dung, container, false);

        ic_back = view.findViewById(R.id.ic_back);
        ImageView thongtin = view.findViewById(R.id.thongtin);
        ImageView ic_doimk = view.findViewById(R.id.ic_doimk);
        LinearLayout layoutThongTinCaNhan = view.findViewById(R.id.thongtinCN);

        // Initialize TextViews
        txtHeaderTenUser = view.findViewById(R.id.heade_user_name);
        txtHeaderEmailUser = view.findViewById(R.id.hd_user_email);
        txtTenUser = view.findViewById(R.id.user_name);
        txtEamil = view.findViewById(R.id.user_email);
        txtDateBirth = view.findViewById(R.id.user_date);
        txtSDT = view.findViewById(R.id.user_phone);

        // Firebase User
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("NguoiDung").child(userId);

            userRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String tenUser = dataSnapshot.child("tenUser").getValue(String.class);
                        String email = dataSnapshot.child("email").getValue(String.class);
                        String datebirth = dataSnapshot.child("ngaySinh").getValue(String.class);
                        String sdt = dataSnapshot.child("SDT").getValue(String.class);

                        txtHeaderTenUser.setText(tenUser);
                        txtHeaderEmailUser.setText(email);
                        txtTenUser.setText(tenUser);
                        txtEamil.setText(email);
                        txtDateBirth.setText(datebirth);
                        txtSDT.setText(sdt);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(getActivity(), "Không thể tải dữ liệu", Toast.LENGTH_SHORT).show();
                }
            });
        }

        // Set up click listeners
        ic_back.setOnClickListener(v -> startActivity(new Intent(getActivity(), Ctrl_TongQuan.class)));
        thongtin.setOnClickListener(v -> startActivity(new Intent(getActivity(), Ctrl_ThongTinCaNhan.class)));
        ic_doimk.setOnClickListener(v -> startActivity(new Intent(getActivity(), Ctrl_DoiMatKhau.class)));
        layoutThongTinCaNhan.setOnClickListener(v -> startActivity(new Intent(getActivity(), Ctrl_ThongTinCaNhan.class)));

        // Logout button
        btnDangXuat = view.findViewById(R.id.btnDangXuat);
        btnDangXuat.setOnClickListener(v -> showLogoutDialog());

        // Delete account button
        btnXoaTaiKhoan = view.findViewById(R.id.btn_xoataikhoan);
        btnXoaTaiKhoan.setOnClickListener(v -> showDeleteAccountDialog());

        return view;
    }

    private void showLogoutDialog() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View dialogView = inflater.inflate(R.layout.custom_dialog_dangxuat, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(dialogView);

        TextView tvMessage = dialogView.findViewById(R.id.tvMessage);
        Button btnLogout = dialogView.findViewById(R.id.btnLogout);
        Button btnCancel = dialogView.findViewById(R.id.btnCancel);

        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.border_dialog);

        btnLogout.setOnClickListener(v -> {
            navigateToLoginScreen();
            dialog.dismiss();
        });

        btnCancel.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    private void showDeleteAccountDialog() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View dialogView = inflater.inflate(R.layout.custom_dialog_xoataikhoan, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(dialogView);

        Button btnDelete = dialogView.findViewById(R.id.btnDelete);
        Button btnCancel = dialogView.findViewById(R.id.btnCancel);

        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.border_dialog);

        btnDelete.setOnClickListener(v -> {
            deleteUserAccount();
            dialog.dismiss();
        });

        btnCancel.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    private void deleteUserAccount() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String userId = user.getUid();
            user.delete().addOnCompleteListener(deleteTask -> {
                if (deleteTask.isSuccessful()) {
                    DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("NguoiDung").child(userId);
                    userRef.removeValue().addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(getActivity(), "Xóa tài khoản và dữ liệu thành công", Toast.LENGTH_SHORT).show();
                            navigateToLoginScreen();
                        } else {
                            String errorMessage = task.getException() != null ? task.getException().getMessage() : "Lỗi không xác định";
                            Toast.makeText(getActivity(), "Xóa dữ liệu người dùng không thành công: " + errorMessage, Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    String errorMessage = deleteTask.getException() != null ? deleteTask.getException().getMessage() : "Lỗi không xác định";
                    Toast.makeText(getActivity(), "Xóa tài khoản không thành công: " + errorMessage, Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(getActivity(), "Không có người dùng đăng nhập", Toast.LENGTH_SHORT).show();
        }
    }

    private void navigateToLoginScreen() {
        startActivity(new Intent(getActivity(), Ctrl_TrangChuDNDK.class));
    }
}