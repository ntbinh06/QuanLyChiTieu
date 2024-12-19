package com.example.quanlychitieu.Controller;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import androidx.appcompat.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlychitieu.Model.M_DanhMucHangMuc;
import com.example.quanlychitieu.Model.M_GiaoDich;
import com.example.quanlychitieu.Model.M_NhomHangMuc;
import com.example.quanlychitieu.Model.M_TaiKhoan;
import com.example.quanlychitieu.R;
import com.example.quanlychitieu.View.V_ItemGiaoDich;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Ctrl_CacGiaoDich extends AppCompatActivity {

    private RecyclerView recyclerView;
    private V_ItemGiaoDich myAdapter;
    private List<M_GiaoDich> giaoDichList = new ArrayList<>();
    private List<M_GiaoDich>  originalList = new ArrayList<>();
    private Map<String, M_DanhMucHangMuc> hangMucMap = new HashMap<>();
    private Map<String, String> taiKhoanMap = new HashMap<>();
    private Map<String, String> nhomHangMucMap = new HashMap<>();
    private ImageButton btnBack;
    private TextView txtTong;
    private TextView currentMonthText;
    private ImageView prevMonthButton, nextMonthButton;
    private SearchView searchView;

    private int currentMonthOffset = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cac_giao_dich);

        btnBack = findViewById(R.id.ic_back);
        recyclerView = findViewById(R.id.recyclerviewGD);
        currentMonthText = findViewById(R.id.TextTime);
        prevMonthButton = findViewById(R.id.backDate);
        nextMonthButton = findViewById(R.id.nextDate);
        searchView= findViewById(R.id.search_view);
        // Cài đặt RecyclerView
        // Cập nhật khi khởi tạo adapter
        myAdapter = new V_ItemGiaoDich(this, giaoDichList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myAdapter);


        recyclerView.addOnItemTouchListener(new Ctrl_RecyclerViewItemClickListener(this, recyclerView, new Ctrl_RecyclerViewItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                // Giả sử bạn có một danh sách chứa giao dịch
                M_GiaoDich selectedGiaoDich = giaoDichList.get(position); // Lấy giao dịch từ vị trí
                String idGiaoDich = selectedGiaoDich.getIdGiaoDich();

                DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();

                // Truy vấn bảng GiaoDich
                dbRef.child("GiaoDich").child(idGiaoDich).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot giaoDichSnap) {
                        if (giaoDichSnap.exists()) {
                            String idHangMuc = giaoDichSnap.child("idHangMuc").getValue(String.class);
                            long giaTri = giaoDichSnap.child("giaTri").getValue(Long.class);

                            // Truy vấn bảng HangMuc dựa trên idHangMuc
                            dbRef.child("HangMuc").child(idHangMuc).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot hangMucSnap) {
                                    if (hangMucSnap.exists()) {
                                        String idNhom = hangMucSnap.child("idNhom").getValue(String.class);
                                        String tenHangMuc = hangMucSnap.child("tenHangMuc").getValue(String.class);

                                        // Truy vấn bảng NhomHangMuc dựa trên idNhom
                                        dbRef.child("NhomHangMuc").child(idNhom).addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot nhomSnap) {
                                                if (nhomSnap.exists()) {
                                                    String tenNhom = nhomSnap.child("tenNhom").getValue(String.class);

                                                    // Chuyển hướng theo idNhom
                                                    Intent intent = new Intent();
                                                    if ("1".equals(idNhom)) {
                                                        intent = new Intent(view.getContext(), Ctrl_XemThuNhap.class);
                                                    } else if ("2".equals(idNhom)) {
                                                        intent = new Intent(view.getContext(), Ctrl_XemChiPhi.class);
                                                    }

                                                    // Truyền dữ liệu giao dịch
                                                    intent.putExtra("idGiaoDich", idGiaoDich);
                                                    intent.putExtra("idHangMuc", idHangMuc);
                                                    intent.putExtra("giaTri", giaTri);
                                                    intent.putExtra("tenHangMuc", tenHangMuc);
                                                    intent.putExtra("tenNhom", tenNhom);

                                                    view.getContext().startActivity(intent);

                                                    // Ghi log thông tin
                                                    Log.d("Firebase", "Chi tiết giao dịch:");
                                                    Log.d("Firebase", "Tên nhóm: " + tenNhom);
                                                    Log.d("Firebase", "Tên hạng mục: " + tenHangMuc);
                                                    Log.d("Firebase", "Giá trị giao dịch: " + giaTri);
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {
                                                Log.e("Firebase", "Lỗi truy vấn NhomHangMuc: " + error.getMessage());
                                            }
                                        });
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    Log.e("Firebase", "Lỗi truy vấn HangMuc: " + error.getMessage());
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e("Firebase", "Lỗi truy vấn GiaoDich: " + error.getMessage());
                    }
                });
            }

            @Override
            public void onLongItemClick(View view, int position) {
                // Xử lý khi click dài (nếu cần thiết)
            }
        }));





        // Tải dữ liệu
        loadHangMuc();
        loadTaiKhoan();

        // Điều hướng tháng
        updateMonthText();

        prevMonthButton.setOnClickListener(v -> {
            currentMonthOffset--;
            updateMonthText();
            loadGiaoDich();
        });

        nextMonthButton.setOnClickListener(v -> {
            currentMonthOffset++;
            updateMonthText();
            loadGiaoDich();
        });

        currentMonthText.setOnClickListener(v -> showMonthYearPicker());

        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(Ctrl_CacGiaoDich.this, Ctrl_TongQuan.class);
            startActivity(intent);
        });

        searchView.setIconified(false); // Đảm bảo SearchView luôn mở rộng
        searchView.clearFocus(); // Không tự động focus khi vào màn hình

//        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
//            @Override
//            public boolean onClose() {
//                giaoDichList.clear();
//                giaoDichList.addAll(originalList); // Khôi phục danh sách từ bản gốc
//                myAdapter.updateData(giaoDichList); // Làm mới RecyclerView
//                return false; // Giữ hành vi mặc định
//            }
//        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Xử lý tìm kiếm khi người dùng nhấn nút tìm kiếm
                if (query != null && !query.isEmpty()) {
                    searchGiaoDich(query.trim());
                } else {
                    Toast.makeText(Ctrl_CacGiaoDich.this, "Vui lòng nhập từ khóa tìm kiếm!", Toast.LENGTH_SHORT).show();
                }
                return true; // Giữ bàn phím khi nhấn nút tìm kiếm
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()) {
                    giaoDichList.clear();
                    giaoDichList.addAll(originalList); // Khôi phục danh sách đầy đủ
                    myAdapter.notifyDataSetChanged(); // Làm mới RecyclerView
                }
                return false; // Trả về false để giữ lại hành vi mặc định
            }
        });

    }

    private void searchGiaoDich(String keyword) {
        List<M_GiaoDich> filteredList = new ArrayList<>();
        for (M_GiaoDich giaoDich : originalList) {
            // Lấy tên hạng mục từ giao dịch
            String tenHangMuc = giaoDich.getTenHangMuc();
            Log.d("SearchDG_ID", "ID Hang Muc: " + giaoDich.getIdHangMuc());

            // So sánh từ khóa với tên hạng mục
            if (tenHangMuc != null && tenHangMuc.toLowerCase().contains(keyword.toLowerCase())) {
                filteredList.add(giaoDich);
                Log.d("SearchMatch", "Tìm thấy giao dịch khớp: " + tenHangMuc);
            }
        }

        // Cập nhật adapter nếu tìm thấy kết quả
        if (!filteredList.isEmpty()) {
            myAdapter.updateData(filteredList);
            Toast.makeText(this, "Tìm thấy " + filteredList.size() + " kết quả.", Toast.LENGTH_SHORT).show();
        } else {
            myAdapter.updateData(new ArrayList<>()); // Làm rỗng danh sách nếu không tìm thấy
            Toast.makeText(this, "Không tìm thấy giao dịch nào phù hợp!", Toast.LENGTH_SHORT).show();
        }
    }



    //Hien thi ten hang muc
    private void loadHangMuc() {
        DatabaseReference hangMucRef = FirebaseDatabase.getInstance().getReference("HangMuc");
        hangMucRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                hangMucMap.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    M_DanhMucHangMuc hangMuc = dataSnapshot.getValue(M_DanhMucHangMuc.class);
                    if (hangMuc != null) {
                        hangMucMap.put(hangMuc.getIdHangmuc(), hangMuc);
                    }
                }
                loadGiaoDich();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Ctrl_CacGiaoDich.this, "Không thể tải dữ liệu hạng mục", Toast.LENGTH_SHORT).show();
            }
        });
    }


    //Hien thi ten tai khoan
    private void loadTaiKhoan() {
        DatabaseReference taiKhoanRef = FirebaseDatabase.getInstance().getReference("TaiKhoan");
        taiKhoanRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                taiKhoanMap.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    M_TaiKhoan taiKhoan = dataSnapshot.getValue(M_TaiKhoan.class);
                    if (taiKhoan != null) {
                        taiKhoanMap.put(taiKhoan.getIdTaiKhoan(), taiKhoan.getTenTaiKhoan());
                    }
                }
                loadGiaoDich();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Ctrl_CacGiaoDich.this, "Không thể tải dữ liệu tài khoản", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadGiaoDich() {
        DatabaseReference giaoDichRef = FirebaseDatabase.getInstance().getReference("GiaoDich");
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid(); // Lấy UID của người dùng hiện tại
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, currentMonthOffset);
        int selectedYear = calendar.get(Calendar.YEAR);
        int selectedMonth = calendar.get(Calendar.MONTH);

        // Lấy danh sách tất cả giao dịch
        giaoDichRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                giaoDichList.clear();
                originalList.clear(); // Đảm bảo không bị trùng lặp
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    M_GiaoDich giaoDich = dataSnapshot.getValue(M_GiaoDich.class);
                    if (giaoDich != null && userId.equals(giaoDich.getUserId())) { // Kiểm tra userId
                        try {
                            // Lấy ngày, tháng, năm từ Map ngayTao
                            Map<String, Object> ngayTao = giaoDich.getNgayTao();
                            int ngay = Integer.parseInt(ngayTao.get("ngay").toString());
                            int thang = Integer.parseInt(ngayTao.get("thang").toString()) - 1; // Tháng trong Calendar bắt đầu từ 0
                            int nam = Integer.parseInt(ngayTao.get("nam").toString());

                            // Tạo đối tượng Calendar từ ngày, tháng, năm
                            Calendar giaoDichCal = Calendar.getInstance();
                            giaoDichCal.set(nam, thang, ngay);

                            if (giaoDichCal.get(Calendar.YEAR) == selectedYear &&
                                    giaoDichCal.get(Calendar.MONTH) == selectedMonth) {

                                // Lấy thông tin từ HangMuc
                                M_DanhMucHangMuc hangMuc = hangMucMap.get(giaoDich.getIdHangMuc());
                                if (hangMuc != null) {
                                    giaoDich.setTenHangMuc(hangMuc.getTenHangmuc());
                                    giaoDich.setAnhHangMuc(hangMuc.getAnhHangmuc());
                                } else {
                                    giaoDich.setTenHangMuc("Không xác định");
                                    giaoDich.setAnhHangMuc("analysis"); // Ảnh mặc định
                                }

                                // Lấy tên tài khoản
                                String tenTaiKhoan = taiKhoanMap.get(giaoDich.getIdTaiKhoan());
                                if (tenTaiKhoan == null) {
                                    tenTaiKhoan = "Không xác định";
                                }
                                giaoDich.setTenTaiKhoan(tenTaiKhoan);

                                // Giao dịch vẫn giữ idHangMuc cho mục đích khác
                                giaoDichList.add(giaoDich);
                            }

                        } catch (NumberFormatException e) {
                            Toast.makeText(Ctrl_CacGiaoDich.this, "Định dạng ngày không hợp lệ", Toast.LENGTH_SHORT).show();
                        } catch (NullPointerException e) {
                            Toast.makeText(Ctrl_CacGiaoDich.this, "Thông tin ngày tháng không đầy đủ", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                originalList.addAll(giaoDichList); // Lưu bản gốc để phục hồi
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Ctrl_CacGiaoDich.this, "Không thể tải dữ liệu giao dịch", Toast.LENGTH_SHORT).show();
            }
        });
    }




    private void updateMonthText() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, currentMonthOffset);
        if (currentMonthOffset == 0) {
            currentMonthText.setText("Tháng này");
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("MMMM yyyy", new Locale("vi", "VN"));
            currentMonthText.setText(sdf.format(calendar.getTime()));
        }
    }

    private void showMonthYearPicker() {
        Locale.setDefault(new Locale("vi", "VN"));
        Calendar calendar = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                R.style.CustomDatePickerDialog,
                (view, year, month, dayOfMonth) -> {
                    Calendar selectedDate = Calendar.getInstance();
                    selectedDate.set(year, month, 1);
                    Calendar currentDate = Calendar.getInstance();
                    currentMonthOffset = (year - currentDate.get(Calendar.YEAR)) * 12 + (month - currentDate.get(Calendar.MONTH));
                    updateMonthText();
                    loadGiaoDich();
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );

        try {
            Field dayPicker = datePickerDialog.getDatePicker().getClass().getDeclaredField("mDaySpinner");
            dayPicker.setAccessible(true);
            View dayView = (View) dayPicker.get(datePickerDialog.getDatePicker());
            dayView.setVisibility(View.GONE);
        } catch (Exception e) {
            e.printStackTrace();
        }

        datePickerDialog.show();
    }

}