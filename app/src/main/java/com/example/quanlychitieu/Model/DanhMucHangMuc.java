package com.example.quanlychitieu.Model;

public class DanhMucHangMuc {
    private int image;
    private String tenHangMuc;

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTenHangMuc() {
        return tenHangMuc;
    }

    public void setTenHangMuc(String tenHangMuc) {
        this.tenHangMuc = tenHangMuc;
    }

    public DanhMucHangMuc(int image, String tenHangMuc) {
        this.image = image;
        this.tenHangMuc = tenHangMuc;
    }
}
