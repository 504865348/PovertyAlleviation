package com.example.jayny.povertyalleviation;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jayny on 2016/12/7.
 */

public class Constant implements Parcelable {

    public static String userid;
    public static String aid;
    public static String username;
    public static String pname;
    public static String statusTime;
    public static String usertype;
    public static String fundstatus;

    public static String getFundstatus() {
        return fundstatus;
    }

    public static void setFundstatus(String fundstatus) {
        Constant.fundstatus = fundstatus;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        Constant.username = username;
    }

    public static String getPname() {
        return pname;
    }

    public static void setPname(String pname) {
        Constant.pname = pname;
    }

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public String getUid() {
        return userid;
    }

    public void setUid(String uid) {
        this.userid = uid;
    }

    public String getStatusTime() {
        return statusTime;
    }

    public void setStatusTime(String statusTime) {
        this.statusTime = statusTime;
    }

    public String getUtype() {
        return usertype;
    }

    public void setUtype(String utype) {
        this.usertype = utype;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.userid);
        dest.writeString(this.aid);
        dest.writeString(this.username);
        dest.writeString(this.pname);
        dest.writeString(this.statusTime);
        dest.writeString(this.usertype);
        dest.writeString(this.fundstatus);
    }

    public Constant() {
    }

    protected Constant(Parcel in) {
        this.userid = in.readString();
        this.aid = in.readString();
        this.username = in.readString();
        this.pname = in.readString();
        this.statusTime = in.readString();
        this.usertype = in.readString();
        this.fundstatus = in.readString();
    }

    public static final Parcelable.Creator<Constant> CREATOR = new Parcelable.Creator<Constant>() {
        @Override
        public Constant createFromParcel(Parcel source) {
            return new Constant(source);
        }

        @Override
        public Constant[] newArray(int size) {
            return new Constant[size];
        }
    };
}
