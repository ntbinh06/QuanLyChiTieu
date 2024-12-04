package com.example.quanlychitieu.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlychitieu.Model.M_TaiKhoan;
import com.example.quanlychitieu.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Ctrl_ChuyenTien extends AppCompatActivity {

    private Spinner spnfrom;
    private Spinner spnto;
    private DatabaseReference userRef;
    private List<M_TaiKhoan> taiKhoanList;
    private EditText inPut_monney;

    private String selectedFromAccountId = null; // ID tài khoản gửi
    private String selectedToAccountId = null;   // ID tài khoản nhận

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chuyen_tien);

        spnfrom = findViewById(R.id.spin_bank_from);
        spnto = findViewById(R.id.spin_bank_to);
        inPut_monney = findViewById(R.id.inPut_monney);
        taiKhoanList = new ArrayList<>();
        userRef = FirebaseDatabase.getInstance().getReference("TaiKhoan");

        getTaiKhoanList();

        ImageButton ic_back = findViewById(R.id.ic_back);
        Button btn_save = findViewById(R.id.btn_save);

        ic_back.setOnClickListener(v -> startActivity(new Intent(Ctrl_ChuyenTien.this, Ctrl_TongQuan.class)));

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy số tiền nhập vào
                Double inputMoney;
                try {
                    inputMoney = Double.parseDouble(inPut_monney.getText().toString());
                } catch (NumberFormatException e) {
                    Toast.makeText(Ctrl_ChuyenTien.this, "Vui lòng nhập số tiền hợp lệ", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Kiểm tra tài khoản đã chọn
                if (selectedFromAccountId == null || selectedToAccountId == null) {
                    Toast.makeText(Ctrl_ChuyenTien.this, "Vui lòng chọn tài khoản", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Lấy thông tin tài khoản
                M_TaiKhoan fromAccount = taiKhoanList.get(spnfrom.getSelectedItemPosition());
                M_TaiKhoan toAccount = taiKhoanList.get(spnto.getSelectedItemPosition());

                // Cập nhật số dư
                double newFromBalance = fromAccount.getLuongBanDau() - inputMoney;
                double newToBalance = toAccount.getLuongBanDau() + inputMoney;

                // Kiểm tra số dư
                if (newFromBalance < 0) {
                    Toast.makeText(Ctrl_ChuyenTien.this, "Số dư không đủ", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Cập nhật vào cơ sở dữ liệu
                Map<String, Object> updates = new HashMap<>();
                updates.put(selectedFromAccountId + "/luongBanDau", newFromBalance);
                updates.put(selectedToAccountId + "/luongBanDau", newToBalance);

                userRef.updateChildren(updates).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Cập nhật danh sách địa phương
                        fromAccount.setLuongBanDau(newFromBalance);
                        toAccount.setLuongBanDau(newToBalance);
                        Toast.makeText(Ctrl_ChuyenTien.this, "Chuyển tiền thành công", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Ctrl_ChuyenTien.this, Ctrl_CacTaiKhoan.class));
                    } else {
                        Toast.makeText(Ctrl_ChuyenTien.this, "Lỗi khi cập nhật số dư", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
    private void getTaiKhoanList() {
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                taiKhoanList.clear();
                String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String idTaiKhoan = snapshot.child("idTaiKhoan").getValue(String.class);
                    String tenTaiKhoan = snapshot.child("tenTaiKhoan").getValue(String.class);
                    String hangMucUserId = snapshot.child("userId").getValue(String.class);
                    double luongBanDau = snapshot.child("luongBanDau").getValue(Double.class); // Lấy giá trị luongBanDau

                    if (userId.equals(hangMucUserId)) {
                        M_TaiKhoan taiKhoan = new M_TaiKhoan(idTaiKhoan, tenTaiKhoan, hangMucUserId, luongBanDau);
                        taiKhoanList.add(taiKhoan);
                    }
                }
                updateSpinner();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(Ctrl_ChuyenTien.this, "Lỗi lấy dữ liệu", Toast.LENGTH_SHORT).show();
                Log.e("DatabaseError", databaseError.getMessage());
            }
        });
    }

    private void updateSpinner() {
        List<String> tentaikhoanList = new ArrayList<>();
        for (M_TaiKhoan taiKhoan : taiKhoanList) {
            if (taiKhoan.getTenTaiKhoan() != null) {
                tentaikhoanList.add(taiKhoan.getTenTaiKhoan());
            }
        }

        if (tentaikhoanList.isEmpty()) {
            Toast.makeText(this, "Không có tài khoản nào để hiển thị", Toast.LENGTH_SHORT).show();
            return;
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tentaikhoanList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnfrom.setAdapter(adapter);
        spnto.setAdapter(adapter);

        spnfrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedFromAccountId = taiKhoanList.get(i).getIdTaiKhoan();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(Ctrl_ChuyenTien.this, "Chưa chọn tài khoản gửi", Toast.LENGTH_SHORT).show();
            }
        });

        spnto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedToAccountId = taiKhoanList.get(i).getIdTaiKhoan();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(Ctrl_ChuyenTien.this, "Chưa chọn tài khoản nhận", Toast.LENGTH_SHORT).show();
            }
        });
    }
}