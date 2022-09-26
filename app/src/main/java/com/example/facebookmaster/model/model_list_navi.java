package com.example.facebookmaster.model;

public class model_list_navi {
    String circlerview;
    String txt_menu;

    public String getCirclerview() {
        return circlerview;
    }

    public void setCirclerview(String circlerview) {
        this.circlerview = circlerview;
    }

    public String getTxt_menu() {
        return txt_menu;
    }

    public void setTxt_menu(String txt_menu) {
        this.txt_menu = txt_menu;
    }

    public model_list_navi(String circlerview, String txt_menu) {
        this.circlerview = circlerview;
        this.txt_menu = txt_menu;
    }
}
