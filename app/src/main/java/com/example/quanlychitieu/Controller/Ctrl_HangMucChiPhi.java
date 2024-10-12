package com.example.quanlychitieu.Controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.quanlychitieu.Model.Model_HangMucChiPhi;
import com.example.quanlychitieu.R;

import java.util.ArrayList;
import java.util.List;

public class Ctrl_HangMucChiPhi extends ArrayAdapter<Model_HangMucChiPhi> {
    private Context context;
    private int resource;
    private List<Model_HangMucChiPhi> arrContact;

    public Ctrl_HangMucChiPhi(Context context, int resource, ArrayList<Model_HangMucChiPhi> arrContact) {
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
            viewHolder.tvAvatar = convertView.findViewById(R.id.icon);
            viewHolder.tvName = convertView.findViewById(R.id.name);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Model_HangMucChiPhi contact = arrContact.get(position);
        viewHolder.tvAvatar.setImageResource(contact.getAvatarResource());
        viewHolder.tvName.setText(contact.getName());

        return convertView;
    }

    public class ViewHolder {
        TextView tvName;
        ImageView tvAvatar;
    }
}