package com.example.quanlychitieu.Controller;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.quanlychitieu.Model.M_DanhMucHangMuc;
import com.example.quanlychitieu.Model.M_HangMucChiPhi;
import com.example.quanlychitieu.Model.M_TaiKhoan;
import com.example.quanlychitieu.R;
import com.example.quanlychitieu.View.V_CustomSpinner;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Ctrl_NganSachMoi extends AppCompatActivity {
    private EditText editTextGiaTri;
    private Spinner spnTaiKhoan;
    private FirebaseFirestore db;
    private DatabaseReference userRef;
    private List<M_TaiKhoan> taiKhoanList; // Danh sách tài khoản
    private List<M_DanhMucHangMuc> HangMucList;
    private TextView chonHangMucTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ngan_sach_moi);
        db = FirebaseFirestore.getInstance();
        editTextGiaTri = findViewById(R.id.edit_dutru);
        spnTaiKhoan = findViewById(R.id.spnTaiKhoan);
        chonHangMucTextView = findViewById(R.id.chonhangmuc);

        // Lấy danh sách từ mô hình
        taiKhoanList = new ArrayList<>();
        userRef = FirebaseDatabase.getInstance().getReference("TaiKhoan");
        // Lấy danh mục từ Firestore
        gettaiKhoanList();


        // Window insets handling
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageButton ic_back = findViewById(R.id.ic_back);
        Button btn_save = findViewById(R.id.btn_save);
        ImageButton hangmuc = findViewById(R.id.hangmuc);
        hangmuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFeedbackDialog(Gravity.BOTTOM);
            }
        });
        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Ctrl_NganSachMoi.this, Ctrl_NganSach.class));
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy giá trị từ EditText
                String giaTri = editTextGiaTri.getText().toString().trim();

                // Kiểm tra giá trị giaTri không rỗng
                if (giaTri.isEmpty()) {
                    Toast.makeText(Ctrl_NganSachMoi.this, "Vui lòng nhập giá trị", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Lấy ID tài khoản đã chọn từ danh sách tài khoản
                M_TaiKhoan selectedTaiKhoan = taiKhoanList.stream()
                        .filter(taiKhoan -> taiKhoan.getIdTaiKhoan().equals(selectedTaiKhoanId))
                        .findFirst()
                        .orElse(null);

                // Kiểm tra xem tài khoản đã chọn có tồn tại hay không
                if (selectedTaiKhoan == null) {
                    Toast.makeText(Ctrl_NganSachMoi.this, "Tài khoản không hợp lệ", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Tạo một đối tượng chứa các giá trị cần cập nhật
                Map<String, Object> updates = new HashMap<>();
                updates.put("nganSachDuTru", Double.parseDouble(giaTri));
                updates.put("idTaiKhoan", selectedTaiKhoan.getIdTaiKhoan());

                // Lưu thông tin lên Firebase
                DatabaseReference hangMucRef = FirebaseDatabase.getInstance().getReference("HangMuc").child(selectedHangMucId);

                // Cập nhật các trường nganSachDuTru và idTaiKhoan mà không làm mất dữ liệu khác
                hangMucRef.updateChildren(updates)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(Ctrl_NganSachMoi.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(Ctrl_NganSachMoi.this, Ctrl_NganSach.class));
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Ctrl_NganSachMoi.this, "Lỗi cập nhật dữ liệu: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }

    private void gettaiKhoanList () {
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                taiKhoanList.clear(); // Xóa danh sách cũ nếu cần

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String idTaiKhoan = snapshot.child("idTaiKhoan").getValue(String.class);
                    String tenTaiKhoan = snapshot.child("tenTaiKhoan").getValue(String.class);

                    // Tạo và thêm đối tượng M_TaiKhoan vào danh sách
                    M_TaiKhoan taiKhoan = new M_TaiKhoan(idTaiKhoan, tenTaiKhoan); // Giả sử bạn có constructor như vậy
                    taiKhoanList.add(taiKhoan);
                }
                updateSpinner();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(Ctrl_NganSachMoi.this, "Lỗi lấy dữ liệu", Toast.LENGTH_SHORT).show();
                Log.e("DatabaseError", databaseError.getMessage());
            }
        });
    }
    private String selectedTaiKhoanId = null; // Biến để lưu ID tài khoản đã chọn

    private void updateSpinner () {
        List<String> tentaikhoanList = new ArrayList<>();
        for (M_TaiKhoan taiKhoan : taiKhoanList) {
            if (taiKhoan.getTenTaiKhoan() != null) { // Kiểm tra null trước khi thêm
                tentaikhoanList.add(taiKhoan.getTenTaiKhoan());
            }
        }

        // Kiểm tra nếu danh sách trống
        if (tentaikhoanList.isEmpty()) {
            Toast.makeText(this, "Không có tài khoản nào để hiển thị", Toast.LENGTH_SHORT).show();
            return; // Ngừng thực hiện nếu không có dữ liệu
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tentaikhoanList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnTaiKhoan.setAdapter(adapter);

        spnTaiKhoan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // Lưu ID tài khoản dựa trên chỉ số đã chọn
                selectedTaiKhoanId = taiKhoanList.get(i).getIdTaiKhoan(); // Lấy ID tương ứng với tên tài khoản
                String selectedItem = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(Ctrl_NganSachMoi.this, selectedItem, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(Ctrl_NganSachMoi.this, "Chưa chọn mục nào", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private String selectedHangMucId = null; // Biến để lưu ID hạng mục đã chọn

    private void openFeedbackDialog ( int gravity){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_hangmucthunhap);
        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);

        // Thiết lập ListView trong dialog
        ListView listView = dialog.findViewById(R.id.listView_thunhap);

        // Tạo danh sách hạng mục
        ArrayList<M_DanhMucHangMuc> arrContact = new ArrayList<>();

        // Lấy dữ liệu từ Firebase
        DatabaseReference danhMucRef = FirebaseDatabase.getInstance().getReference("HangMuc");

        // Sử dụng idNhom là kiểu string "1"
        String idNhom = "2"; // Giá trị string bạn muốn so sánh

        danhMucRef.orderByChild("idNhom").equalTo(idNhom).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                arrContact.clear(); // Xóa danh sách cũ nếu cần

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String idHangmuc = snapshot.child("idHangmuc").getValue(String.class);
                    String tenHangmuc = snapshot.child("tenHangmuc").getValue(String.class);

                    // Thêm vào danh sách
                    arrContact.add(new M_DanhMucHangMuc(idHangmuc, tenHangmuc));
                }

                // Khởi tạo adapter và gán cho ListView
                Ctrl_HangMucThuNhap customAdapter = new Ctrl_HangMucThuNhap(Ctrl_NganSachMoi.this, R.layout.list_item_hangmuc, arrContact);
                listView.setAdapter(customAdapter);

                // Thiết lập sự kiện cho ListView
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        M_DanhMucHangMuc selectedItem = arrContact.get(position);
                        selectedHangMucId = selectedItem.getIdHangmuc(); // Lưu ID hạng mục đã chọn
                        String tenHangMuc = selectedItem.getTenHangmuc(); // Lấy tên hạng mục
                        chonHangMucTextView.setText(tenHangMuc); // Cập nhật TextView
                        Toast.makeText(Ctrl_NganSachMoi.this, "Đã chọn: " + tenHangMuc, Toast.LENGTH_SHORT).show();
                        dialog.dismiss(); // Đóng dialog sau khi chọn
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(Ctrl_NganSachMoi.this, "Lỗi lấy dữ liệu", Toast.LENGTH_SHORT).show();
                Log.e("DatabaseError", databaseError.getMessage());
            }
        });

        dialog.setCancelable(Gravity.BOTTOM == gravity);
        dialog.show(); // Hiển thị dialog
    }
}
