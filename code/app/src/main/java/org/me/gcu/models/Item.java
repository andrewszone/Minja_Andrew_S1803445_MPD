//S1803445
//Andrew Minja
package org.me.gcu.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Item implements Parcelable {
    private String link;
    private String description;
    private String title;
    private String category;
    private String pubDate;
    private String lat;
    private String lng;

    private Double magnitude;
    private String location;
    private String depth;
    private Double depthValue;
    private String status = "";

    public Item(){}

    public String getLink () { return link; }

    public void setLink (String link) { this.link = link; }

    public String getDescription () { return description; }

    public void setDescription (String description) { this.description = description; }

    public String getTitle () { return title; }

    public void setTitle (String title) { this.title = title; }

    public String getCategory () { return category; }

    public void setCategory (String category) { this.category = category; }

    public String getPubDate () { return pubDate; }

    public void setPubDate (String pubDate) { this.pubDate = pubDate; }

    public String getLat() { return lat; }

    public void setLat(String lat) { this.lat = lat; }

    public String getLng() { return lng; }

    public void setLng(String lng) { this.lng = lng; }

    public Double getMagnitude() { return magnitude; }

    public String getStatus() {return status; }

    public void setStatus(String status) { this.status = status; }

    public void setMagnitude(String description) {
        String magnitude = description.split(";")[4];
        String value = magnitude.split(":")[1];
        this.magnitude = Double.valueOf(value);
    }

    public String getLocation() { return location; }

    public void setLocation(String description) {
        String location = description.split(";")[1];
        this.location = location.split(":")[1];
    }

    public String getDepth() { return depth; }

    public void setDepth(String description) {
        String depth = description.split(";")[3];
        this.depth = depth.split(":")[1];
    }

    public Double getDepthValue(){ return depthValue; }

    public void setDepthValue(String depth){
        this.depthValue = Double.valueOf(depth.split(" ")[1]);
    }

    @Override
    public String toString() {
        return "ClassPojo [link = "+link+", description = "+description+", title = "+title+", category = "+category+", pubDate = "+pubDate+"]";
    }

    protected Item(Parcel in) {
        link = in.readString();
        description = in.readString();
        title = in.readString();
        category = in.readString();
        pubDate = in.readString();
        lat = in.readString();
        lng = in.readString();
        magnitude = in.readByte() == 0x00 ? null : in.readDouble();
        depthValue = in.readByte() == 0x00 ? null : in.readDouble();
        location = in.readString();
        depth = in.readString();
        status = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(link);
        dest.writeString(description);
        dest.writeString(title);
        dest.writeString(category);
        dest.writeString(pubDate);
        dest.writeString(lat);
        dest.writeString(lng);
        if (magnitude == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeDouble(magnitude);
        }
        if (depthValue == null){
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeDouble(depthValue);
        }
        dest.writeString(location);
        dest.writeString(depth);
        dest.writeString(status);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Item> CREATOR = new Parcelable.Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };
}
