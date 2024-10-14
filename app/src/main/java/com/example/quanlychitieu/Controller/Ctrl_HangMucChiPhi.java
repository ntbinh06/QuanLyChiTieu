package com.example.quanlychitieu.Controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quanlychitieu.Model.Model_HangMucChiPhi;
import com.example.quanlychitieu.R;

import java.util.ArrayList;

public class Ctrl_HangMucChiPhi extends ArrayAdapter<Model_HangMucChiPhi> {
    private final Context context;
    private final int resource;
    private final ArrayList<Model_HangMucChiPhi> arrContact;

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
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(resource, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tvAvatar = convertView.findViewById(R.id.image_hangMuc); // Đảm bảo ID đúng
            viewHolder.tvName = convertView.findViewById(R.id.name); // Đảm bảo ID đúng
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Model_HangMucChiPhi contact = arrContact.get(position);
        viewHolder.tvAvatar.setImageResource(contact.getAvatarResource());
        viewHolder.tvName.setText(contact.getName());

        return convertView;
    }

    private static class ViewHolder {
        ImageView tvAvatar;
        TextView tvName;
    }
}