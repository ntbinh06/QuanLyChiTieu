package com.example.quanlychitieu.Model;
import com.google.firebase.firestore.DocumentId;

public class M_NganSach {
    private String tenHangMuc;
    private String trangThai;
    private long tongSoTien;
    private long soTienConLai;
    private int hinhAnh;
    private int phanTramChi;

    public M_NganSach(String tenHangMuc, String trangThai, long tongSoTien, long soTienConLai, int hinhAnh, int phanTramChi) {
        this.tenHangMuc = tenHangMuc;
        this.trangThai = trangThai;
        this.tongSoTien = tongSoTien;
        this.soTienConLai = soTienConLai;
        this.hinhAnh = hinhAnh;
        this.phanTramChi = phanTramChi;
    }

    public String getTenHangMuc() {
        return tenHangMuc;
    }

    public long getTongSoTien() {
        return tongSoTien;
    }

    public long getSoTienConLai() {
        return soTienConLai;
    }

    public int getHinhAnh() {
        return hinhAnh;
    }

    public int getPhanTramChi() {
        return phanTramChi;
    }

    public String getFormattedSoTien() {
        return String.format("%,d đ", tongSoTien);
    }

    public String getFormattedSoTienConLai() {
        return String.format("%,d đ", soTienConLai);
    }

    public int getPgrBar() {
        return phanTramChi;
    }
}
