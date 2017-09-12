package com.lsh.boxbox.model;

import android.os.Parcel;
import android.os.Parcelable;



/**
 * Created by LSH on 2017/9/8.
 * 序列化
 */

public class User implements Parcelable {

    public int userId;
    public String userName;
    public boolean isMale;

    public City city;

    public User(int userId, String userName, boolean isMale) {
        this.userId = userId;
        this.userName = userName;
        this.isMale = isMale;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(userId);
        out.writeString(userName);
        out.writeInt(isMale ? 1 : 0);
        out.writeParcelable(city, 0);
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {

        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    private User(Parcel in) {
        userId = in.readInt();
        userName = in.readString();
        isMale = in.readInt() == 1;
        city=in.readParcelable(Thread.currentThread().getContextClassLoader());
    }


}
