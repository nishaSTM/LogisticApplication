package com.delivery.model;


import android.os.Parcel;
import android.os.Parcelable;

public class LocationCoordinates implements Parcelable {
    private float lat;
    private float lng;
    private String address;

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public float getLng() {
        return lng;
    }

    public void setLng(float lng) {
        this.lng = lng;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    private LocationCoordinates(Parcel in) {
        lat = in.readFloat();
        lng = in.readFloat();
        address = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(lat);
        dest.writeFloat(lng);
        dest.writeString(address);
    }

    public static final Parcelable.Creator<LocationCoordinates> CREATOR = new Parcelable.Creator<LocationCoordinates>() {
        @Override
        public LocationCoordinates createFromParcel(Parcel in) {
            return new LocationCoordinates(in);
        }

        @Override
        public LocationCoordinates[] newArray(int size) {
            return new LocationCoordinates[size];
        }
    };
}
