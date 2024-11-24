package com.example.quanlychitieu.View;

import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.widget.ListView;

import com.example.quanlychitieu.Controller.Ctrl_HangMucChiPhi;
import com.example.quanlychitieu.Model.M_HangMucChiPhi;
import com.example.quanlychitieu.R;

import java.util.ArrayList;

public class V_HangMucChiPhi extends AppCompatActivity {
    private ListView lvContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_hangmucchiphi);

        // Khởi tạo ListView
        lvContact = findViewById(R.id.listView_chiphi);

        // Tạo danh sách chi phí
        ArrayList<M_HangMucChiPhi> arrContact = new ArrayList<>();
        arrContact.add(new M_HangMucChiPhi(R.drawable.baseline_account_circle_24, "Đồ ăn/ Đồ uống"));
        arrContact.add(new M_HangMucChiPhi(R.drawable.baseline_account_circle_24, "Mua sắm"));
        arrContact.add(new M_HangMucChiPhi(R.drawable.baseline_account_circle_24, "Vận chuyển"));
        arrContact.add(new M_HangMucChiPhi(R.drawable.baseline_account_circle_24, "Giải trí"));
        arrContact.add(new M_HangMucChiPhi(R.drawable.baseline_account_circle_24, "Nhà cửa"));
        arrContact.add(new M_HangMucChiPhi(R.drawable.baseline_account_circle_24, "Gia đình"));
        arrContact.add(new M_HangMucChiPhi(R.drawable.baseline_account_circle_24, "Sức khỏe"));

        // Khởi tạo adapter và gán cho ListView
        Ctrl_HangMucChiPhi customAdapter = new Ctrl_HangMucChiPhi(this, R.layout.list_item_hangmuc, arrContact);
        lvContact.setAdapter(customAdapter);



        // Thiết lập padding cho view chính
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.hangmuchiphi), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}