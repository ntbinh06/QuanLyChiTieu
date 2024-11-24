package com.example.quanlychitieu.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.quanlychitieu.R;

public class Ctrl_ThongBaoThemTK extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate layout của SuccessFragment
        View view = inflater.inflate(R.layout.fragment_thong_bao_them_t_k, container, false);

        // Khi người dùng nhấn vào Fragment, quay về CacTaiKhoanActivity
        view.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), Ctrl_CacTaiKhoan.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            requireActivity().finish();
        });

        return view;
    }

}