package com.example.facebookmaster.model;

public class model_donhang {

    private String sdt;
    private String taikhoan;
    private int soluong;
    private int hientai;
    private int hoanthanh;
    private String curren_time;
    private int isfinisnh;

    public int getIsfinisnh() {
        return isfinisnh;
    }

    public void setIsfinisnh(int isfinisnh) {
        this.isfinisnh = isfinisnh;
    }

    public String getCurren_time() {
        return curren_time;
    }

    public void setCurren_time(String curren_time) {
        this.curren_time = curren_time;
    }

    public int getHientai() {
        return hientai;
    }

    public void setHientai(int hientai) {
        this.hientai = hientai;
    }

    public int getHoanthanh() {
        return hoanthanh;
    }

    public void setHoanthanh(int hoanthanh) {
        this.hoanthanh = hoanthanh;
    }

    public model_donhang(int hientai, int hoanthanh) {
        this.hientai = hientai;
        this.hoanthanh = hoanthanh;
    }

    public model_donhang(int soluong) {
        this.soluong = soluong;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getTaikhoan() {
        return taikhoan;
    }

    public void setTaikhoan(String taikhoan) {
        this.taikhoan = taikhoan;
    }

    public model_donhang(String sdt, String taikhoan) {
        this.sdt = sdt;
        this.taikhoan = taikhoan;
    }

    private String time;
    private String stype;
    private String link;

    public model_donhang(String stype) {
        this.stype = stype;
    }

    private long sotien;
    private String status;
    private String baohanh;

    public model_donhang() {

    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStype() {
        return stype;
    }

    public void setStype(String stype) {
        this.stype = stype;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public long getSotien() {
        return sotien;
    }

    public void setSotien(long sotien) {
        this.sotien = sotien;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBaohanh() {
        return baohanh;
    }

    public void setBaohanh(String baohanh) {
        this.baohanh = baohanh;
    }

    public String getGhichu() {
        return ghichu;
    }

    public void setGhichu(String ghichu) {
        this.ghichu = ghichu;
    }

    public model_donhang(String time, String stype, String link, long sotien, String status, String baohanh, String ghichu) {
        this.time = time;
        this.stype = stype;
        this.link = link;
        this.sotien = sotien;
        this.status = status;
        this.baohanh = baohanh;
        this.ghichu = ghichu;
    }

    private String ghichu;

}
