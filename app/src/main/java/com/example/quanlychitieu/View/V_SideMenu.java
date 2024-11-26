package com.example.quanlychitieu.View;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.quanlychitieu.Controller.Ctrl_NguoiDung;
import com.example.quanlychitieu.R;
import com.google.common.base.FinalizableReference;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.bumptech.glide.Glide;

public class V_SideMenu extends AppCompatActivity {

    private ListView lvContact;
    private FirebaseFirestore firestore;
    private String userId;
    private TextView txtTenUser_SM, txtEmailUser_SM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.tongquan_menu);

        txtTenUser_SM = findViewById(R.id.txtTenUser_SideMenu);
        txtEmailUser_SM = findViewById(R.id.txtEmail_SideMenu);

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

                        txtTenUser_SM.setText(tenUser);
                        txtEmailUser_SM.setText(email);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(V_SideMenu.this, "Không thể tải dữ liệu", Toast.LENGTH_SHORT).show();
                }
            });
        }

        // Áp dụng insets cho layout
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.tongquan_menu), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment); // 'fragment_container' là ID của FrameLayout chứa Fragment
        transaction.addToBackStack(null); // Thêm vào BackStack nếu muốn quay lại
        transaction.commit();
    }
}