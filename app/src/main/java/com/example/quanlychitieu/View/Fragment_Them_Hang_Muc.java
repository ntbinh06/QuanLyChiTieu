package com.example.quanlychitieu.View;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import com.example.quanlychitieu.Controller.IconAdapter;
import com.example.quanlychitieu.Controller.TongQuan;
import com.example.quanlychitieu.Controller.TrangChuAdmin;
import com.example.quanlychitieu.Model.IconManager;
import com.example.quanlychitieu.R;

public class Fragment_Them_Hang_Muc extends Fragment {

    private ImageView selectedImageView;
    private RecyclerView recyclerView;
    Button btnCancel, btnSave;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout cho Fragment
        View view = inflater.inflate(R.layout.fragment__them__hang__muc, container, false);

        //ánh xạ các nút
        btnCancel = view.findViewById(R.id.btn_cancel);
        btnSave = view.findViewById(R.id.btn_save);

        selectedImageView = view.findViewById(R.id.selected_image);
        recyclerView = view.findViewById(R.id.recyclerViewIcons);
        // Lấy danh sách icon từ IconManager
        List<Integer> iconList = IconManager.getIconList();

        // Tạo adapter và truyền danh sách icon vào
        IconAdapter adapter = new IconAdapter(iconList, selectedIcon -> {
            // Hiển thị icon được chọn trong ImageView
            selectedImageView.setImageResource(selectedIcon);
        });

        // Cài đặt GridLayout cho RecyclerView
        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 4));
        recyclerView.setAdapter(adapter);

        btnCancel.setOnClickListener(v -> {
            // Sử dụng requireContext() thay vì view làm Context cho Intent
            Intent intent = new Intent(requireContext(), View_QuanLyHangMuc.class);
            startActivity(intent);  // Mở màn hình khác
        });

        // Xử lý sự kiện nút Lưu
        btnSave.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), View_QuanLyHangMuc.class);
            startActivity(intent);  // Mở màn hình khác
        });
        return view;
    }
}