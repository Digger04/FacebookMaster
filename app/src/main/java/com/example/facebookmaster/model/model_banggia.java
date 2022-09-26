package com.example.facebookmaster.model;

public class model_banggia {
    private long gialike;
    private long giaflollow;
    private long giashare;
    private String time;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public model_banggia(String time) {
        this.time = time;
    }

    public model_banggia() {

    }

    public long getGialike() {
        return gialike;
    }

    public void setGialike(long gialike) {
        this.gialike = gialike;
    }

    public long getGiaflollow() {
        return giaflollow;
    }

    public void setGiaflollow(long giaflollow) {
        this.giaflollow = giaflollow;
    }

    public long getGiashare() {
        return giashare;
    }

    public void setGiashare(long giashare) {
        this.giashare = giashare;
    }

    public model_banggia(long gialike, long giaflollow, long giashare) {
        this.gialike = gialike;
        this.giaflollow = giaflollow;
        this.giashare = giashare;
    }
}
