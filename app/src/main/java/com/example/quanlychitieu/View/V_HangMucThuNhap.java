package com.example.quanlychitieu.View;

import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.widget.ListView;

import com.example.quanlychitieu.Controller.Ctrl_HangMucThuNhap;
import com.example.quanlychitieu.Model.M_HangMucThuNhap;
import com.example.quanlychitieu.R;

import java.util.ArrayList;

public class V_HangMucThuNhap extends AppCompatActivity {
    private ListView lvContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_hangmucthunhap);

        // Khởi tạo ListView
        lvContact = findViewById(R.id.listView_thunhap);

        // Tạo danh sách chi phí
        ArrayList<M_HangMucThuNhap> arrContact = new ArrayList<>();
        arrContact.add(new M_HangMucThuNhap(R.drawable.baseline_account_circle_24, "Thu nhập tài chính"));
        arrContact.add(new M_HangMucThuNhap(R.drawable.baseline_account_circle_24, "Lương"));
        arrContact.add(new M_HangMucThuNhap(R.drawable.baseline_account_circle_24, "Tiền từ việc vặt"));
        arrContact.add(new M_HangMucThuNhap(R.drawable.baseline_account_circle_24, "Tiền trợ cấp"));
        arrContact.add(new M_HangMucThuNhap(R.drawable.baseline_account_circle_24, "Tiền tiết kiệm"));

        // Khởi tạo adapter và gán cho ListView
        Ctrl_HangMucThuNhap customAdapter = new Ctrl_HangMucThuNhap(this, R.layout.list_item_hangmuc, arrContact);
        lvContact.setAdapter(customAdapter);




        // Thiết lập padding cho view chính
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.hangmucthunhap), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}