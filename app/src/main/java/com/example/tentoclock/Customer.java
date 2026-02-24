package com.example.tentoclock;

import android.os.Parcel;
import android.os.Parcelable;

public class Customer implements Parcelable {

    private String firstname;
    private String lastname;
    private String phone;
    private String email;
    private String address;
    private String regionArea;
    private String postcode;
    private String floor;
    private String doorbell;

    public Customer() {

    }

    public Customer(String email, String firstname, String lastname, String regionArea) {
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.regionArea = regionArea;
    }

    public Customer(String firstname, String lastname, String phone, String email, String address, String regionArea, String postcode, String floor, String doorbell) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.regionArea = regionArea;
        this.postcode = postcode;
        this.floor = floor;
        this.doorbell = doorbell;
    }

    protected Customer(Parcel in) {
        firstname = in.readString();
        lastname = in.readString();
        phone = in.readString();
        email = in.readString();
        address = in.readString();
        regionArea = in.readString();
        postcode = in.readString();
        floor = in.readString();
        doorbell = in.readString();
    }

    public static final Creator<Customer> CREATOR = new Creator<Customer>() {
        @Override
        public Customer createFromParcel(Parcel in) {
            return new Customer(in);
        }

        @Override
        public Customer[] newArray(int size) {
            return new Customer[size];
        }
    };

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRegionArea() {
        return regionArea;
    }

    public void setRegionArea(String regionArea) {
        this.regionArea = regionArea;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getDoorbell() {
        return doorbell;
    }

    public void setDoorbell(String doorbell) {
        this.doorbell = doorbell;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(firstname);
        dest.writeString(lastname);
        dest.writeString(phone);
        dest.writeString(email);
        dest.writeString(address);
        dest.writeString(regionArea);
        dest.writeString(postcode);
        dest.writeString(floor);
        dest.writeString(doorbell);
    }
}