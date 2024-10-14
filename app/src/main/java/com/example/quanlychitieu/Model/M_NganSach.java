package com.example.quanlychitieu.Model;

import java.text.NumberFormat;
import java.util.Locale;

public class M_NganSach {

    private String tenHangMuc, txtConlai;
    private double soTien, soTienConLai;
    private int imgHangMuc, pgrBar;


    public M_NganSach(String tenHangMuc, String txtConlai, double soTien, double soTienConLai, int imgHangMuc, int pgrBar) {
        this.tenHangMuc = tenHangMuc;
        this.txtConlai = txtConlai;
        this.soTien = soTien;
        this.soTienConLai = soTienConLai;
        this.imgHangMuc = imgHangMuc;
        this.pgrBar = pgrBar;
    }

    public String getTenHangMuc() {
        return tenHangMuc;
    }

    public void setTenHangMuc(String tenHangMuc) {
        this.tenHangMuc = tenHangMuc;
    }

    public String getTxtConlai() {
        return txtConlai;
    }

    public void setTxtConlai(String txtConlai) {
        this.txtConlai = txtConlai;
    }

    public double getSoTien() {
        return soTien;
    }

    public void setSoTien(double soTien) {
        this.soTien = soTien;
    }

    public double getSoTienConLai() {
        return soTienConLai;
    }

    public void setSoTienConLai(double soTienConLai) {
        this.soTienConLai = soTienConLai;
    }

    public int getImgHangMuc() {
        return imgHangMuc;
    }

    public void setImgHangMuc(int imgHangMuc) {
        this.imgHangMuc = imgHangMuc;
    }

    public int getPgrBar() {
        return pgrBar;
    }

    public void setPgrBar(int pgrBar) {
        this.pgrBar = pgrBar;
    }

    public String getFormattedSoTien() {
        NumberFormat formatter = NumberFormat.getNumberInstance(Locale.US);
        return formatter.format(soTien) + " đ"; // Định dạng và thêm đơn vị tiền tệ
    }

    public String getFormattedSoTienConLai() {
        NumberFormat formatter = NumberFormat.getNumberInstance(Locale.US);
        return formatter.format(soTienConLai) + " đ";
    }

}
