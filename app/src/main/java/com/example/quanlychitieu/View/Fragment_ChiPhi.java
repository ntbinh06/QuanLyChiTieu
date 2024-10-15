package com.example.quanlychitieu.View;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.quanlychitieu.Controller.CacGiaoDich;
import com.example.quanlychitieu.Model.DanhMucHangMuc;
import com.example.quanlychitieu.R;

import java.util.ArrayList;


public class Fragment_ChiPhi extends Fragment  {
    int image[]= {R.drawable.food, R.drawable.shopping, R.drawable.family,R.drawable.delivery_van,R.drawable.drum_set,R.drawable.house, R.drawable.healthcare,R.drawable.pets,R.drawable.travel_luggage};
    String tenGD[]={"Đồ ăn","Mua sắm","Gia đình","Vận chuyển","Giải trí","Nhà cửa","Sức khỏe","Thú cưng","Du lịch"};


    private ListView lv;
    private ArrayList<DanhMucHangMuc> danhMuc;
    private View_ItemHangMuc myAdapter;

    public Fragment_ChiPhi() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout cho Fragment
        View view = inflater.inflate(R.layout.fragment__chi_phi, container, false);
        // Khởi tạo ListView
        lv = view.findViewById(R.id.listView_chiphi);
        danhMuc = new ArrayList<>();

        // Tạo dữ liệu mẫu cho ListView
        for (int i = 0; i < tenGD.length; i++) {
            int index = i % tenGD.length;
            danhMuc.add(new DanhMucHangMuc(image[i], tenGD[i]));
        }

        // Khởi tạo Adapter
        myAdapter = new View_ItemHangMuc(getActivity(), R.layout.list_item, danhMuc);
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