package com.example.quanlychitieu.View;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import com.example.quanlychitieu.Controller.Ctrl_IconAdapter;
import com.example.quanlychitieu.Model.M_IconManager;
import com.example.quanlychitieu.R;

public class Fragment_Them_Hang_Muc extends DialogFragment {

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
        List<Integer> iconList = M_IconManager.getIconList();

        // Tạo adapter và truyền danh sách icon vào
        Ctrl_IconAdapter adapter = new Ctrl_IconAdapter(iconList, selectedIcon -> {
            // Hiển thị icon được chọn trong ImageView
            selectedImageView.setImageResource(selectedIcon);
        });

        // Cài đặt GridLayout cho RecyclerView
        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 4));
        recyclerView.setAdapter(adapter);

        // Xử lý nút Hủy (Đóng DialogFragment)
        btnCancel.setOnClickListener(v -> dismiss());

        // Xử lý nút Lưu (Có thể truyền dữ liệu hoặc đóng Dialog)
        btnSave.setOnClickListener(v -> {
            // Bạn có thể thực hiện logic lưu ở đây
            dismiss();  // Đóng DialogFragment sau khi lưu
        });
        return view;
    }
    @Override
    public void onStart() {
        super.onStart();

        // Đặt kích thước cho DialogFragment
        if (getDialog() != null && getDialog().getWindow() != null) {
            Window window = getDialog().getWindow();
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }
}