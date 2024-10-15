package com.example.quanlychitieu.Controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quanlychitieu.Model.M_SideMenu;
import com.example.quanlychitieu.R;

import java.util.ArrayList;
import java.util.List;

public class C_SideMenu extends ArrayAdapter<M_SideMenu> {

    private Context context;
    private int resource;
    private List<M_SideMenu> arrContact;

    public C_SideMenu(Context context, int resource, ArrayList<M_SideMenu> arrContact) {
        super(context, resource, arrContact);
        this.context = context;
        this.resource = resource;
        this.arrContact = arrContact;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if (convertView == null) {
            // Inflate the custom layout for each item
            convertView = LayoutInflater.from(context).inflate(resource, parent, false);

            // Initialize ViewHolder to hold references to views
            viewHolder = new ViewHolder();
            viewHolder.tvMenu = (TextView) convertView.findViewById(R.id.tvMenu);
            viewHolder.iconMenu = (ImageView) convertView.findViewById(R.id.iconMenu);

            // Set the tag for future reuse
            convertView.setTag(viewHolder);
        } else {
            // Reuse the ViewHolder if already present
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Get the current M_SideMenu item
        M_SideMenu contact = arrContact.get(position);

        // Set the data to the views
        viewHolder.iconMenu.setImageResource(contact.getIconMenu());
        viewHolder.tvMenu.setText(contact.getTvMenu());

        // Return the completed view to render on screen
        return convertView;
    }

    // ViewHolder class to hold references to views for better performance
    public static class ViewHolder {
        TextView tvMenu;
        ImageView iconMenu;
    }
}
