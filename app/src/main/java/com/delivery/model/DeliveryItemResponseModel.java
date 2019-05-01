package com.delivery.model;


import android.os.Parcel;
import android.os.Parcelable;

public class DeliveryItemResponseModel implements Parcelable {
    private String description;
    private String imageUrl;
    private String id;
    private LocationCoordinatesResponseModel location;

    public DeliveryItemResponseModel(String description, String imageUrl, String id, LocationCoordinatesResponseModel location) {
        this.description = description;
        this.imageUrl = imageUrl;
        this.id = id;
        this.location = location;
    }

    public DeliveryItemResponseModel() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return imageUrl;
    }

    public void setImage(String imageUrl) {
        this.imageUrl = imageUrl;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocationCoordinatesResponseModel getLocation() {
        return location;
    }

    public void setLocation(LocationCoordinatesResponseModel location) {
        this.location = location;
    }

    private DeliveryItemResponseModel(Parcel in) {
        description = in.readString();
        imageUrl = in.readString();
        id = in.readString();
        location = (LocationCoordinatesResponseModel) in.readValue(LocationCoordinatesResponseModel.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(description);
        dest.writeString(imageUrl);
        dest.writeString(id);
        dest.writeValue(location);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<DeliveryItemResponseModel> CREATOR = new Parcelable.Creator<DeliveryItemResponseModel>() {
        @Override
        public DeliveryItemResponseModel createFromParcel(Parcel in) {
            return new DeliveryItemResponseModel(in);
        }

        @Override
        public DeliveryItemResponseModel[] newArray(int size) {
            return new DeliveryItemResponseModel[size];
        }
    };
}
