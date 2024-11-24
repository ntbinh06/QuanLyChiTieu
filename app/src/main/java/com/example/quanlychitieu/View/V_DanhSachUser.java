package com.example.quanlychitieu.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.quanlychitieu.Controller.Ctrl_DanhSachUser;
import com.example.quanlychitieu.Controller.Ctrl_TrangChuAdmin;
import com.example.quanlychitieu.Model.M_DanhSachUser;
import com.example.quanlychitieu.R;

import java.util.ArrayList;

public class V_DanhSachUser extends AppCompatActivity {
    private ListView lvContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danhsachuser_admin); // Đặt layout cho Activity

        lvContact = findViewById(R.id.lvuser);
        ArrayList<M_DanhSachUser> arrContact = new ArrayList<>();

        // Tạo các đối tượng Model_DanhSachUser
        arrContact.add(new M_DanhSachUser(R.drawable.user_woman_female_person_icon, "Nguyễn Thanh Bình", "binhqb@gmail.com", R.drawable.ic_eye, R.drawable.ic_lock_user));
        arrContact.add(new M_DanhSachUser(R.drawable.user_woman_female_person_icon, "Lê Thị Kim Ngân", "ngan@gmail.com", R.drawable.ic_eye, R.drawable.ic_lock_user));
        arrContact.add(new M_DanhSachUser(R.drawable.user_woman_female_person_icon, "Cái Thị Nhân Đức", "duc@gmail.com", R.drawable.ic_eye, R.drawable.ic_lock_user));
        arrContact.add(new M_DanhSachUser(R.drawable.user_woman_female_person_icon, "Lê Thị A", "thia@gmail.com", R.drawable.ic_eye, R.drawable.ic_lock_user));
        arrContact.add(new M_DanhSachUser(R.drawable.user_woman_female_person_icon, "Cái Thị B", "thib@gmail.com", R.drawable.ic_eye, R.drawable.ic_lock_user));
        arrContact.add(new M_DanhSachUser(R.drawable.user_woman_female_person_icon, "Nguyễn Thị C", "thic@gmail.com", R.drawable.ic_eye, R.drawable.ic_lock_user));

        // Tạo adapter và thiết lập cho ListView
        Ctrl_DanhSachUser customAdapter = new Ctrl_DanhSachUser(this, R.layout.list_item_user, arrContact);
        lvContact.setAdapter(customAdapter);

        // Thiết lập padding cho View chính
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.lvuser), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Thiết lập sự kiện cho nút quay lại
        ImageButton ic_back = findViewById(R.id.ic_back);
        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(V_DanhSachUser.this, Ctrl_TrangChuAdmin.class);
                startActivity(intent);
                finish(); // Kết thúc activity hiện tại nếu không cần quay lại
            }
        });
    }
}