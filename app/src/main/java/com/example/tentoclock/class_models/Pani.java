package com.example.tentoclock.class_models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class Pani implements Parcelable {
    private String code;
    private String name;
    private ArrayList<String> colors;
    private String series;
    private double price;
    private String description;

    public Pani() {
        // Default constructor required for calls to DataSnapshot.getValue(Pani.class)
        colors = new ArrayList<>();
    }

    public Pani(String code, String name, ArrayList<String> colors, String series, double price, String description) {
        this.code = code;
        this.name = name;
        this.colors = colors;
        this.series = series;
        this.price = price;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(double price) {
        this.price = price;
    }



    public ArrayList<String> getColors() {
        return colors;
    }

    public String getColor(int index) {
        return colors.get(index);
    }

    public void setColors(ArrayList<String> colors) {
        this.colors = colors;
    }

    public void addColor(String color) {
        colors.add(color);
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }


    @Override
    public String toString() {
        return "Pani{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", colors=" + colors +
                ", series='" + series + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(code);
        dest.writeString(name);
        dest.writeStringList(colors);
        dest.writeString(series);
        dest.writeDouble(price);
        dest.writeString(description);
    }

    public static final Parcelable.Creator<Pani> CREATOR
            = new Parcelable.Creator<Pani>() {
        public Pani createFromParcel(Parcel in) {
            return new Pani(in);
        }

        public Pani[] newArray(int size) {
            return new Pani[size];
        }
    };

    private Pani(Parcel in) {
        this.code = in.readString();
        this.name = in.readString();
        this.colors = in.createStringArrayList();
        this.series = in.readString();
        this.price = in.readDouble();
        this.description = in.readString();
    }
}
