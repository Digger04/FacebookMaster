package com.example.facebookmaster.model;

public class model_tinnhan {
    private long ID;
    private String avata;
    private String message;
    private String time;
    private String picture;
    private int Type;
    private String sdt;
    private String taikhoan;

    public model_tinnhan() {

    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getAvata() {
        return avata;
    }

    public void setAvata(String avata) {
        this.avata = avata;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
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

    public model_tinnhan(long ID, String avata, String message, String time, String picture, int type, String sdt, String taikhoan) {
        this.ID = ID;
        this.avata = avata;
        this.message = message;
        this.time = time;
        this.picture = picture;
        Type = type;
        this.sdt = sdt;
        this.taikhoan = taikhoan;
    }
}
