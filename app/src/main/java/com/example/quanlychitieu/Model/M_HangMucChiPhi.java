package com.example.quanlychitieu.Model;

public class M_HangMucChiPhi {
    private int avatarResource; // Resource ID cho hình ảnh
    private String name; // Tên của mục chi phí

    // Constructor cho class Model_HangMucChiPhi
    public M_HangMucChiPhi(int avatarResource, String name) {
        this.avatarResource = avatarResource;
        this.name = name;
    }

    // Getter cho avatarResource
    public int getAvatarResource() {
        return avatarResource;
    }

    // Setter cho avatarResource (nếu cần)
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