package com.example.quanlychitieu.View;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

import com.example.quanlychitieu.Model.M_DanhMucHangMuc;
import com.example.quanlychitieu.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class V_ItemHangMuc extends ArrayAdapter<M_DanhMucHangMuc> {
    private final Activity context;
    private final int idLayout;
    private final ArrayList<M_DanhMucHangMuc> myList;
    private final FragmentManager fragmentManager;

    public V_ItemHangMuc(Activity context, int idLayout, ArrayList<M_DanhMucHangMuc> myList, FragmentManager fragmentManager) {
        super(context, idLayout, myList);
        this.context = context;
        this.idLayout = idLayout;
        this.myList = myList;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Sử dụng ViewHolder để tối ưu hóa
        ViewHolder holder;

        if (convertView == null) {
            // Inflate layout từ idLayout
            LayoutInflater inflater = context.getLayoutInflater();
            convertView = inflater.inflate(idLayout, parent, false);

            // Ánh xạ các thành phần giao diện
            holder = new ViewHolder();
            holder.imgHangMuc = convertView.findViewById(R.id.image_hangMuc);
            holder.tenHangMuc = convertView.findViewById(R.id.name);
            holder.icEye = convertView.findViewById(R.id.ic_eye);
            holder.icLock = convertView.findViewById(R.id.ic_lock);

            convertView.setTag(holder); // Lưu ViewHolder vào convertView
        } else {
            holder = (ViewHolder) convertView.getTag(); // Lấy ViewHolder từ convertView
        }

        // Lấy đối tượng hạng mục tại vị trí hiện tại
        M_DanhMucHangMuc hangMuc = myList.get(position);

        // Gán dữ liệu cho các thành phần giao diện
        holder.tenHangMuc.setText(hangMuc.getTenHangmuc());

        // Lấy hình ảnh từ `drawable` dựa trên tên trong `anhHangmuc`
        int imgResId = context.getResources().getIdentifier(hangMuc.getAnhHangmuc(), "drawable", context.getPackageName());
        if (imgResId != 0) {
            holder.imgHangMuc.setImageResource(imgResId);
        } else {
            holder.imgHangMuc.setImageResource(R.drawable.analysis); // Ảnh mặc định nếu không tìm thấy
        }
        //click button xem chi tiết
        holder.icEye.setOnClickListener(v->openDialogFragment(hangMuc));

        //click button xóa
        holder.icLock.setOnClickListener(v -> {
            // Tham chiếu đến Firebase
            DatabaseReference hangMucRef = FirebaseDatabase.getInstance().getReference("HangMuc");

            // Xóa hạng mục khỏi Firebase
            hangMucRef.child(hangMuc.getIdHangmuc()).removeValue()
                    .addOnSuccessListener(aVoid -> {
                        if (position >= 0 && position < myList.size()) { // Kiểm tra vị trí hợp lệ
                            myList.remove(position);
                            notifyDataSetChanged(); // Cập nhật lại ListView
                        }
                        Toast.makeText(context, "Đã xóa hạng mục thành công!", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(context, "Lỗi khi xóa hạng mục: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        });
        return convertView;
    }

    // Phương thức mở Fragment_Them_Hang_Muc với dữ liệu
    private void openDialogFragment(M_DanhMucHangMuc hangMuc) {
        int imgResId = context.getResources().getIdentifier(hangMuc.getAnhHangmuc(), "drawable", context.getPackageName());
        String tenHangMuc = hangMuc.getTenHangmuc();
        String anhHangMuc = hangMuc.getAnhHangmuc();

        Fragment_Them_Hang_Muc dialogFragment = Fragment_Them_Hang_Muc.newInstance(
                hangMuc.getIdHangmuc(), // Truyền ID
                Integer.parseInt(hangMuc.getIdNhom()),
                hangMuc.getTenHangmuc(),
                hangMuc.getAnhHangmuc()
        );
        dialogFragment.show(fragmentManager, "ThemHangMuc");
    }


    // ViewHolder để tối ưu hóa
    static class ViewHolder {
        ImageView imgHangMuc;
        TextView tenHangMuc;
        ImageButton icEye, icLock;
    }
}
