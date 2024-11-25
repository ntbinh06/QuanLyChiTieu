package com.example.quanlychitieu.Model;

public class M_NhomHangMuc {
    private String idNhom;
    private String tenNhom;

    public M_NhomHangMuc() {
    }

    public M_NhomHangMuc(String idNhom, String tenNhom) {
        this.idNhom = idNhom;
        this.tenNhom = tenNhom;
    }

    public String getIdNhom() {
        return idNhom;
    }

    public void setIdNhom(String idNhom) {
        this.idNhom = idNhom;
    }

    public String getTenNhom() {
        return tenNhom;
    }

    public void setTenNhom(String tenNhom) {
        this.tenNhom = tenNhom;
    }
}
