package com.example.quanlychitieu.Controller;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ArrayAdapter;

import com.example.quanlychitieu.Model.Model_DanhSachUser;
import com.example.quanlychitieu.R;

import java.util.ArrayList;
import java.util.List;

public class Ctrl_DanhSachUser extends ArrayAdapter<Model_DanhSachUser> {
    private final Context context;
    private final int resource;
    private final List<Model_DanhSachUser> arrContact;

    public Ctrl_DanhSachUser(Context context, int resource, ArrayList<Model_DanhSachUser> arrContact) {
        super(context, resource, arrContact);
        this.context = context;
        this.resource = resource;
        this.arrContact = arrContact;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(resource, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tvAvatar = convertView.findViewById(R.id.tvAvatar);
            viewHolder.tvName = convertView.findViewById(R.id.tvName);
            viewHolder.tvGmail = convertView.findViewById(R.id.tvGmail);
            viewHolder.ic_eye = convertView.findViewById(R.id.ic_eye);
            viewHolder.ic_lock = convertView.findViewById(R.id.ic_lock);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Model_DanhSachUser contact = arrContact.get(position);
        viewHolder.tvAvatar.setImageResource(contact.getAvatarResource());
        viewHolder.tvName.setText(contact.getName());
        viewHolder.tvGmail.setText(contact.getGmail());
        viewHolder.ic_eye.setImageResource(contact.getIcInfo());
        viewHolder.ic_lock.setImageResource(contact.getIcLock());

        // Sự kiện cho nút mắt (ic_eye)
        viewHolder.ic_eye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CacGiaoDich.class); // Sử dụng context
                context.startActivity(intent);
            }
        });

        // Sự kiện cho nút khóa (ic_lock)
        viewHolder.ic_lock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xử lý sự kiện khi nhấn vào nút khóa
                // Ví dụ: hiển thị thông báo hoặc thực hiện một hành động nào đó
            }
        });

        return convertView;
    }

    public static class ViewHolder {
        TextView tvName, tvGmail;
        ImageView tvAvatar;
        ImageButton ic_eye, ic_lock;
    }
}