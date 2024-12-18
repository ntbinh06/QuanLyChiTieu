package com.example.quanlychitieu.View;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlychitieu.Model.M_DanhMucHangMuc;
import com.example.quanlychitieu.Model.M_GiaoDich;
import com.example.quanlychitieu.R;

import java.util.ArrayList;

public class V_NganSach extends RecyclerView.Adapter<V_NganSach.ViewHolder> {
    private Context context;
    private ArrayList<M_DanhMucHangMuc> danhMucList;
    private ArrayList<M_GiaoDich> giaoDichList;

    public V_NganSach(Context context, ArrayList<M_DanhMucHangMuc> danhMucList, ArrayList<M_GiaoDich> giaoDichList) {
        this.context = context;
        this.danhMucList = danhMucList;
        this.giaoDichList = giaoDichList;
    }

    // Phương thức để tính số tiền còn lại
    public double getSoTienConLai(int position) {
        M_DanhMucHangMuc item = danhMucList.get(position);
        double totalGiatri = 0;

        for (M_GiaoDich giaoDich : giaoDichList) {
            if (giaoDich.getIdHangMuc() != null && giaoDich.getIdHangMuc().equals(item.getIdHangmuc())) {
                totalGiatri += giaoDich.getGiaTri();
            }
        }

        double nganSachDuTru = (item.getNganSachDuTru() != null) ? item.getNganSachDuTru() : 0;
        return nganSachDuTru - totalGiatri;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.listview_ngansach, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        M_DanhMucHangMuc item = danhMucList.get(position);
        holder.tenHangmucTextView.setText(item.getTenHangmuc());

        double totalGiatri = 0; // Tính tổng giá trị giao dịch

        for (M_GiaoDich giaoDich : giaoDichList) {
            if (giaoDich.getIdHangMuc() != null && giaoDich.getIdHangMuc().equals(item.getIdHangmuc())) {
                totalGiatri += giaoDich.getGiaTri();
            }
        }

        double nganSachDuTru = (item.getNganSachDuTru() != null) ? item.getNganSachDuTru() : 0;
        double giatriconlai = nganSachDuTru - totalGiatri;

        holder.nganSachDuTruTextView.setText(String.valueOf(nganSachDuTru)); // Hiển thị ngân sách dự trù
        holder.tienConLaiTextView.setText(String.valueOf(giatriconlai));

        // Cập nhật ProgressBar
        holder.progressBar.setMax((int) nganSachDuTru);
        holder.progressBar.setProgress((int) totalGiatri);
        // Đổi màu ProgressBar thành đỏ nếu totalGiatri >= nganSachDuTru
        if (totalGiatri >= nganSachDuTru) {
            holder.progressBar.setProgressDrawable(context.getResources().getDrawable(R.drawable.progress_red)); // Drawable màu đỏ
        } else {
            holder.progressBar.setProgressDrawable(context.getResources().getDrawable(R.drawable.progress_bar)); // Drawable màu mặc định
        }
    }

    @Override
    public int getItemCount() {
        return danhMucList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tenHangmucTextView;
        TextView nganSachDuTruTextView;
        TextView tienConLaiTextView;
        ProgressBar progressBar; // Thêm ProgressBar

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tenHangmucTextView = itemView.findViewById(R.id.tenHangMuc);
            nganSachDuTruTextView = itemView.findViewById(R.id.soTien);
            tienConLaiTextView = itemView.findViewById(R.id.tienConLai);
            progressBar = itemView.findViewById(R.id.pgbTienTrinh); // Khởi tạo ProgressBar
        }
    }
}