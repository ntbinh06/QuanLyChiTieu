package com.example.quanlychitieu.Model;

public class M_DanhMucHangMuc {
    private String idHangmuc;
    private String tenHangmuc;
    private int anhHangmuc;
    private double nganSachDuTru;
    private String idNhom;
    private String idUser;

    // Constructor mặc định (Firebase cần)
    public M_DanhMucHangMuc() {
    }

    public M_DanhMucHangMuc(String idHangmuc,String tenHangmuc  ,int anhHangmuc) {
        this.idHangmuc= idHangmuc;
        this.tenHangmuc = tenHangmuc;
        this.anhHangmuc = anhHangmuc;
    }

    // Constructor đầy đủ
    public M_DanhMucHangMuc(String idHangmuc, String tenHangmuc, int anhHangmuc, double nganSachDuTru, String idNhom, String idUser) {
        this.idHangmuc = idHangmuc;
        this.tenHangmuc = tenHangmuc;
        this.anhHangmuc = anhHangmuc;
        this.nganSachDuTru = nganSachDuTru;
        this.idNhom = idNhom;
        this.idUser = idUser;
    }

    // Getter và Setter
    public String getIdHangmuc() {
        return idHangmuc;
    }

    public void setIdHangmuc(String idHangmuc) {
        this.idHangmuc = idHangmuc;
    }

    public String getTenHangmuc() {
        return tenHangmuc;
    }

    public void setTenHangmuc(String tenHangmuc) {
        this.tenHangmuc = tenHangmuc;
    }

    public int getAnhHangmuc() {
        return anhHangmuc;
    }

    public void setAnhHangmuc(int anhHangmuc) {
        this.anhHangmuc = anhHangmuc;
    }

    public double getNganSachDuTru() {
        return nganSachDuTru;
    }

    public void setNganSachDuTru(double nganSachDuTru) {
        this.nganSachDuTru = nganSachDuTru;
    }

    public String getIdNhom() {
        return idNhom;
    }

    public void setIdNhom(String idNhom) {
        this.idNhom = idNhom;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }
}
