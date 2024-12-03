package com.example.quanlychitieu.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlychitieu.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

public class V_ThongKeItem extends RecyclerView.Adapter<V_ThongKeItem.ViewHolder> {
    private Context context;
    private List<HashMap<String, Object>> giaoDichList; // Dữ liệu sẽ lưu các thông tin giao dịch

    public V_ThongKeItem(Context context) {
        this.context = context;
        this.giaoDichList = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.listviewsodo, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HashMap<String, Object> giaoDichData = giaoDichList.get(position);

        // Định dạng dữ liệu
        String thangNam = (String) giaoDichData.get("thangNam");
        long tongThuNhap = (long) giaoDichData.get("tongThuNhap");
        long tongChiPhi = (long) giaoDichData.get("tongChiPhi");
        int percentThuNhap = (int) giaoDichData.get("percentThuNhap");
        int percentChiPhi = (int) giaoDichData.get("percentChiPhi");

        NumberFormat format = NumberFormat.getInstance(Locale.forLanguageTag("vi-VN"));
        String formattedThuNhap = format.format(tongThuNhap) + " đ";
        String formattedChiPhi = format.format(tongChiPhi) + " đ";

        // Hiển thị dữ liệu lên các TextView và ProgressBar
        holder.txtThangHienTai.setText(thangNam);
        holder.txtTongThuNhap.setText(formattedThuNhap);
        holder.txtTongChiPhi.setText(formattedChiPhi);
        holder.progressBarThuNhap.setProgress(percentThuNhap);
        holder.progressBarChiPhi.setProgress(percentChiPhi);
    }

    @Override
    public int getItemCount() {
        return giaoDichList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtThangHienTai, txtTongThuNhap, txtTongChiPhi;
        ProgressBar progressBarThuNhap, progressBarChiPhi;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtThangHienTai = itemView.findViewById(R.id.txt_ThangHienTai);
            txtTongThuNhap = itemView.findViewById(R.id.txtTongThuNhap);
            txtTongChiPhi = itemView.findViewById(R.id.txtTongChiPhi);
            progressBarThuNhap = itemView.findViewById(R.id.progressBarThuNhap);
            progressBarChiPhi = itemView.findViewById(R.id.progressBarChiPhi);
        }
    }

    public void loadData() {
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();

        // Bước 1: Lấy dữ liệu từ HangMuc
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid(); // Lấy UID của người dùng hiện tại
        dbRef.child("HangMuc").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot hangMucSnap) {
                HashMap<String, String> hangMucMap = new HashMap<>();
                for (DataSnapshot hangMuc : hangMucSnap.getChildren()) {
                    String idHangMuc = hangMuc.getKey();
                    String idNhom = hangMuc.child("idNhom").getValue(String.class);
                    if (idHangMuc != null && idNhom != null) {
                        hangMucMap.put(idHangMuc, idNhom);
                    }
                }

                // Bước 2: Lấy dữ liệu từ GiaoDich, lọc theo userId
                dbRef.child("GiaoDich").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot giaoDichSnap) {
                        HashSet<Integer> yearsSet = new HashSet<>();
                        // Lấy tất cả các năm có trong dữ liệu
                        for (DataSnapshot giaoDich : giaoDichSnap.getChildren()) {
                            // Lấy userId của giao dịch
                            String giaoDichUserId = giaoDich.child("userId").getValue(String.class);
                            if (userId.equals(giaoDichUserId)) { // Kiểm tra userId
                                Integer namGD = giaoDich.child("ngayTao/nam").getValue(Integer.class);
                                if (namGD != null) {
                                    yearsSet.add(namGD);  // Thêm năm vào set (đảm bảo không trùng)
                                }
                            }
                        }

                        // Lặp qua tất cả các năm có trong dữ liệu
                        for (Integer nam : yearsSet) {
                            // Lặp qua tất cả các tháng trong năm
                            for (int thang = 1; thang <= 12; thang++) {
                                int finalThang = thang;
                                int finalNam = nam;

                                long tongThuNhap = 0;
                                long tongChiPhi = 0;
                                boolean hasDataForMonth = false; // Kiểm tra xem tháng có dữ liệu không

                                // Lặp lại qua tất cả giao dịch để tính toán
                                for (DataSnapshot giaoDich : giaoDichSnap.getChildren()) {
                                    String giaoDichUserId = giaoDich.child("userId").getValue(String.class);
                                    if (userId.equals(giaoDichUserId)) { // Kiểm tra userId
                                        String idHangMuc = giaoDich.child("idHangMuc").getValue(String.class);
                                        Long giaTri = giaoDich.child("giaTri").getValue(Long.class);
                                        Integer thangGD = giaoDich.child("ngayTao/thang").getValue(Integer.class);
                                        Integer namGD = giaoDich.child("ngayTao/nam").getValue(Integer.class);

                                        if (idHangMuc != null && giaTri != null && thangGD != null && namGD != null) {
                                            // Kiểm tra tháng và năm có khớp và giaTri không phải là null
                                            if (thangGD == finalThang && namGD == finalNam) {
                                                String idNhom = hangMucMap.get(idHangMuc);
                                                if ("1".equals(idNhom)) {
                                                    tongThuNhap += giaTri;
                                                } else if ("2".equals(idNhom)) {
                                                    tongChiPhi += giaTri;
                                                }
                                                hasDataForMonth = true; // Đã có dữ liệu cho tháng này
                                            }
                                        }
                                    }
                                }

                                // Chỉ thêm vào danh sách nếu có dữ liệu cho tháng
                                if (hasDataForMonth) {
                                    long tongCong = tongThuNhap + tongChiPhi;
                                    int percentThuNhap = tongCong > 0 ? (int) ((tongThuNhap * 100) / tongCong) : 0;
                                    int percentChiPhi = tongCong > 0 ? (int) ((tongChiPhi * 100) / tongCong) : 0;

                                    // Thêm dữ liệu vào danh sách
                                    HashMap<String, Object> giaoDichData = new HashMap<>();
                                    giaoDichData.put("thangNam", "Tháng " + finalThang + " / " + finalNam);
                                    giaoDichData.put("tongThuNhap", tongThuNhap);
                                    giaoDichData.put("tongChiPhi", tongChiPhi);
                                    giaoDichData.put("percentThuNhap", percentThuNhap);
                                    giaoDichData.put("percentChiPhi", percentChiPhi);

                                    giaoDichList.add(giaoDichData);

                                    // Cập nhật RecyclerView
                                    notifyDataSetChanged();
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Xử lý khi có lỗi xảy ra
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Xử lý khi có lỗi xảy ra
            }
        });
    }

}
