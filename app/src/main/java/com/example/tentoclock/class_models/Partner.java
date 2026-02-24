package com.example.tentoclock.class_models;

public class Partner {
    private String firstname;
    private String lastname;
    private String landline;
    private String phone;
    private String email;
    private String profession;
    private String company;
    private String afm;
    private String address;
    private String regionArea;
    private String postcode;
    private String floor;

    public Partner() {
        // Default constructor required for calls to DataSnapshot.getValue(Partner.class)
    }

    public Partner(String firstname, String lastname, String landline, String phone, String email, String address, String regionArea, String postcode, String profession, String company, String afm) {
        this.firstname = firstname.toUpperCase();
        this.lastname = lastname.toUpperCase();
        this.landline = landline;
        this.phone = phone;
        this.email = email;
        this.profession = profession.toUpperCase();
        this.company = company;
        this.afm = afm;
        this.address = address.toUpperCase();
        this.regionArea = regionArea.toUpperCase();
        this.postcode = postcode;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getRegionArea() {
        return regionArea;
    }

    public String getPostcode() {
        return postcode;
    }

    public String getProfession() {
        return profession;
    }

    public String getLandline() {
        return landline;
    }

    public String getCompany() {
        return company;
    }

    public String getAfm() {
        return afm;
    }



    public void setFirstname(String firstname) {
        this.firstname = firstname.toUpperCase();
    }

    public void setLastname(String lastname) {
        this.lastname = lastname.toUpperCase();
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address.toUpperCase();
    }

    public void setRegionArea(String regionArea) {
        this.regionArea = regionArea.toUpperCase();
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public void setProfession(String profession) {
        this.profession = profession.toUpperCase();
    }

    public void setLandline(String landline) {
        this.landline = landline;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setAfm(String afm) {
        this.afm = afm;
    }

    @Override
    public String toString() {
        return "Partner{" +
                "firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", landline='" + landline + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", profession='" + profession + '\'' +
                ", company='" + company + '\'' +
                ", afm='" + afm + '\'' +
                ", address='" + address + '\'' +
                ", regionArea='" + regionArea + '\'' +
                ", postcode='" + postcode + '\'' +
                '}';
    }
}