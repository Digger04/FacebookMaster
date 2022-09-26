package com.example.facebookmaster.model;

public class model_taikhoan {
    String taikhoan;
    String matkhau;
    String sdt;
    String avata;
    String anhbia;
    long sodu;
    String diachi;
    String facebook;
    String quyentk;

    public String getQuyentk() {
        return quyentk;
    }

    public void setQuyentk(String quyentk) {
        this.quyentk = quyentk;
    }

    public model_taikhoan(String taikhoan, String matkhau, String sdt, String avata, String anhbia, long sodu, String diachi, String facebook, String quyentk, String date) {
        this.taikhoan = taikhoan;
        this.matkhau = matkhau;
        this.sdt = sdt;
        this.avata = avata;
        this.anhbia = anhbia;
        this.sodu = sodu;
        this.diachi = diachi;
        this.facebook = facebook;
        this.quyentk = quyentk;
        this.date = date;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public model_taikhoan(String facebook) {
        this.facebook = facebook;
    }

    public String getAnhbia() {
        return anhbia;
    }

    public void setAnhbia(String anhbia) {
        this.anhbia = anhbia;
    }

    public long getSodu() {
        return sodu;
    }

    public void setSodu(long sodu) {
        this.sodu = sodu;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    String date;

    public model_taikhoan() {

    }

    public String getTaikhoan() {
        return taikhoan;
    }

    public void setTaikhoan(String taikhoan) {
        this.taikhoan = taikhoan;
    }

    public String getMatkhau() {
        return matkhau;
    }

    public void setMatkhau(String matkhau) {
        this.matkhau = matkhau;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getAvata() {
        return avata;
    }

    public void setAvata(String avata) {
        this.avata = avata;
    }

    public model_taikhoan(String taikhoan, String matkhau, String sdt, String avata) {
        this.taikhoan = taikhoan;
        this.matkhau = matkhau;
        this.sdt = sdt;
        this.avata = avata;
    }
}
