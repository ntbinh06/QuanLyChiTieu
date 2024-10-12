package com.example.quanlychitieu.Controller;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewAnimator;

import androidx.annotation.NonNull;

import com.example.quanlychitieu.Model.Model_DanhSachUser;
import com.example.quanlychitieu.R;

import java.util.ArrayList;
import java.util.List;
public class Ctrl_DanhSachUser extends ArrayAdapter<Model_DanhSachUser> {
    private Context context;
    private int resource;
    private List<Model_DanhSachUser> arrContact;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_user, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tvAvatar = (ImageView) convertView.findViewById(R.id.tvAvatar);
            viewHolder.tvName = (TextView) convertView.findViewById(R.id.tvName);
            viewHolder.tvGmail = (TextView) convertView.findViewById(R.id.tvGmail);
            viewHolder.ic_eye = (ImageButton) convertView.findViewById(R.id.ic_eye);
            viewHolder.ic_lock = (ImageButton) convertView.findViewById(R.id.ic_lock);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Model_DanhSachUser contact = arrContact.get(position);
        viewHolder.tvAvatar.setImageResource(contact.getAvatarResource());
        viewHolder.tvName.setText(contact.getName());
        viewHolder.tvGmail.setText(contact.getGmail());
        viewHolder.ic_eye.setImageResource(contact.getIcInfo());
        viewHolder.ic_lock.setImageResource(contact.getIcLock());
        return convertView;
    }
    public class ViewHolder {
        TextView tvName, tvGmail;
        ImageView tvAvatar;
        ImageButton ic_eye,ic_lock;
    }
}
