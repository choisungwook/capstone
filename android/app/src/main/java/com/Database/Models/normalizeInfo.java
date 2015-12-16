package com.Database.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by choisunguk on 2015-11-14.
 */
public class normalizeInfo implements Parcelable{
    private static final long serailVersionUID = 1L;
    public int _id;
    public String roastername;
    public String roasterdate;
    public int roasterweight;
    public int startTemp;
    public String firstTime;
    public int firsttemp;
    public String secondTime;
    public int secondtemp;

    public normalizeInfo() { };

    public normalizeInfo(String name, String date, int weight, int starttemp, String firsttime, int firstTemp, String secondtime, int secondTemp)
    {
        roastername = name;
        roasterdate = date;
        roasterweight = weight;
        startTemp = starttemp;
        firstTime = firsttime;
        firsttemp = firstTemp;
        secondTime = secondtime;
        secondtemp = secondTemp;
    }

    public normalizeInfo(int id, String name, String date, int weight, int starttemp, String firsttime, int firstTemp, String secondtime, int secondTemp)
    {
        _id = id;
        roastername = name;
        roasterdate = date;
        roasterweight = weight;
        startTemp = starttemp;
        firstTime = firsttime;
        firsttemp = firstTemp;
        secondTime = secondtime;
        secondtemp = secondTemp;
    }


    protected normalizeInfo(Parcel in) {
        roastername = in.readString();
        roasterdate = in.readString();
        roasterweight = in.readInt();
        startTemp = in.readInt();
        firstTime = in.readString();
        firsttemp = in.readInt();
        secondTime = in.readString();
        secondtemp = in.readInt();
    }

    public static final Creator<normalizeInfo> CREATOR = new Creator<normalizeInfo>() {
        @Override
        public normalizeInfo createFromParcel(Parcel in) {
            String roastername = in.readString();
            String roasterdate = in.readString();
            int roasterweight= in.readInt();
            int startTemp = in.readInt();
            String firstTime= in.readString();
            int firsttemp = in.readInt();
            String secondTime = in.readString();
            int secondtemp = in.readInt();

            return new normalizeInfo(roastername,roasterdate, roasterweight, startTemp, firstTime, firsttemp, secondTime, secondtemp);
        }

        @Override
        public normalizeInfo[] newArray(int size) {
            return new normalizeInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(roastername);
        parcel.writeString(roasterdate);
        parcel.writeInt(roasterweight);
        parcel.writeInt(startTemp);
        parcel.writeString(firstTime);
        parcel.writeInt(firsttemp);
        parcel.writeString(secondTime);
        parcel.writeInt(secondtemp);
    }
}
