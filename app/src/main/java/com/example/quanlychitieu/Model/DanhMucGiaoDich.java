package com.example.quanlychitieu.Model;
import com.google.firebase.firestore.DocumentId;
public class DanhMucGiaoDich {
    private int image;
    private String tenGD;
    private String taikhoan;
    private String tien;
    private String ngay;

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTenGD() {
        return tenGD;
    }

    public void setTenGD(String tenGD) {
        this.tenGD = tenGD;
    }

    public String getTaikhoan() {
        return taikhoan;
    }

    public void setTaikhoan(String taikhoan) {
        this.taikhoan = taikhoan;
    }

    public String getTien() {
        return tien;
    }

    public void setTien(String tien) {
        this.tien = tien;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    //Tạo constrator

    public DanhMucGiaoDich(int image, String tenGD, String taikhoan, String tien, String ngay) {
        this.image = image;
        this.tenGD = tenGD;
        this.taikhoan = taikhoan;
        this.tien = tien;
        this.ngay = ngay;
    }
}
