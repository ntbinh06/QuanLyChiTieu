package com.example.quanlychitieu.Model;

public class M_HangMucThuNhap {
    private int avatarResource;
    private String name;


    public M_HangMucThuNhap(int avatarResource, String name) {
        this.avatarResource = avatarResource;
        this.name = name;
    }


    public int getAvatarResource() {
        return avatarResource;
    }

    public void setAvatarResource(int avatarResource) {
        this.avatarResource = avatarResource;
    }

    // Getter cho name
    public String getName() {
        return name;
    }

    // Setter cho name
    public void setName(String name) {
        this.name = name;
    }
}
