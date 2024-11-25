package com.example.quanlychitieu.View;
import android.os.Bundle;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.example.quanlychitieu.Model.M_DanhMucHangMuc;
import com.example.quanlychitieu.R;
import java.util.ArrayList;


public class Fragment_ThuNhap extends Fragment {
    int image[]= {R.drawable.wage,R.drawable.lending,R.drawable.financial_statement,R.drawable.low_income,R.drawable.streams};
    String tenTN[]={"Lương","Thu lãi", "Tiền trợ cấp","Tiền từ việc vặt","Tiền tiết kiệm"};


    private ListView lv;
    private ArrayList<M_DanhMucHangMuc> danhMuc;
    private V_ItemHangMuc myAdapter;

    public Fragment_ThuNhap() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout cho Fragment
        View view = inflater.inflate(R.layout.fragment__thu_nhap, container, false);
        // Khởi tạo ListView
        lv = view.findViewById(R.id.listView_thunhap);
        danhMuc = new ArrayList<>();

        // Tạo dữ liệu mẫu cho ListView
        for (int i = 0; i < tenTN.length; i++) {
            int index = i % tenTN.length;
            danhMuc.add(new M_DanhMucHangMuc());
        }

        // Khởi tạo Adapter
        // Inside Fragment_ChiPhi

// Khởi tạo Adapter
        myAdapter = new V_ItemHangMuc(getActivity(), R.layout.list_item, danhMuc, getChildFragmentManager());
        lv.setAdapter(myAdapter);


        // Xử lý Insets cho toàn màn hình
        ViewCompat.setOnApplyWindowInsetsListener(view.findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        return view;
    }
}