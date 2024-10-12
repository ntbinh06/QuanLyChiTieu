package com.example.quanlychitieu.Model;

public class Model_DanhSachUser {
    private String name;
    private String gmail;
    private int avatarResource;
    private int ic_info;
    private int ic_lock;

    // Constructor cho class Model_DanhSachUser
    public Model_DanhSachUser(int avatarResource,String name, String gmail, int ic_info, int ic_lock) {
        this.avatarResource = avatarResource;
        this.name = name;
        this.gmail = gmail;
        this.ic_info = ic_info;
        this.ic_lock = ic_lock;
    }

    // Getter và setter cho các thuộc tính
    public int getAvatarResource() {
        return avatarResource;
    }

    public void setAvatarResource(int avatarResource) {
        this.avatarResource = avatarResource;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }


    public int getIcInfo() {
        return ic_info;
    }

    public void setIcInfo(int ic_info) {
        this.ic_info = ic_info;
    }

    public int getIcLock() {
        return ic_lock;
    }

    public void setIcLock(int ic_lock) {
        this.ic_lock = ic_lock;
    }
}