package com.example.quanlychitieu.Controller;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.quanlychitieu.Model.Model_HangMucThuNhap;
import com.example.quanlychitieu.R;

import java.util.ArrayList;
import java.util.List;

public class Ctrl_HangMucThuNhap extends ArrayAdapter<Model_HangMucThuNhap> {
    private Context context;
    private int resource;
    private List<Model_HangMucThuNhap> arrContact;

    public Ctrl_HangMucThuNhap(Context context, int resource, ArrayList<Model_HangMucThuNhap> arrContact) {
        super(context, resource, arrContact);
        this.context = context;
        this.resource = resource;
        this.arrContact = arrContact;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(resource, parent, false); // Sử dụng resource đã truyền vào
            viewHolder = new ViewHolder();
            viewHolder.tvAvatar = convertView.findViewById(R.id.image_hangMuc);
            viewHolder.tvName = convertView.findViewById(R.id.name);
            viewHolder.icLock = convertView.findViewById(R.id.ic_lock);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Model_HangMucThuNhap contact = arrContact.get(position);
        viewHolder.tvAvatar.setImageResource(contact.getAvatarResource());
        viewHolder.tvName.setText(contact.getName());

        viewHolder.icLock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Thực hiện hành động khi click vào ic_lock
                showDeleteDialog();
            }
        });

        return convertView;
    }

    // Phương thức xử lý hành động khi click vào ic_lock
    private void showDeleteDialog() {
        // Inflate layout tùy chỉnh
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.custom_dialog_xoa, null);

        // Khởi tạo AlertDialog.Builder
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(dialogView); // Thiết lập view cho dialog

        // Lấy các thành phần từ layout
        TextView tvMessage = dialogView.findViewById(R.id.tvMessage);
        Button btnDelete = dialogView.findViewById(R.id.btnDelete);
        Button btnCancel = dialogView.findViewById(R.id.btnCancel);

        // Tạo AlertDialog
        AlertDialog dialog = builder.create();

        // Xử lý sự kiện cho nút Xóa
        btnDelete.setOnClickListener(v -> {
            // Thực hiện hành động xóa ở đây
            Toast.makeText(context, "Đã xoá mục", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });

        // Xử lý sự kiện cho nút Hủy
        btnCancel.setOnClickListener(v -> dialog.dismiss());

        // Hiển thị AlertDialog
        dialog.show();
    }

    public class ViewHolder {
        TextView tvName;
        ImageView tvAvatar, icLock;
    }
}