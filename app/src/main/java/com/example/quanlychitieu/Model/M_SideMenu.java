package com.example.quanlychitieu.Model;
import com.google.firebase.firestore.DocumentId;

public class M_SideMenu {
    private String tvMenu;
    private int iconMenu;

    public M_SideMenu(int iconMenu, String tvMenu) {
        this.iconMenu = iconMenu;
        this.tvMenu = tvMenu;
    }

    public int getIconMenu() {
        return iconMenu;
    }

    public void setIconMenu(int iconMenu) {
        this.iconMenu = iconMenu;
    }

    public String getTvMenu() {
        return tvMenu;
    }

    public void setTvMenu(String tvMenu) {
        this.tvMenu = tvMenu;
    }

}
