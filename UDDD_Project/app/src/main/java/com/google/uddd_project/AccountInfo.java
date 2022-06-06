package com.google.uddd_project;

public class AccountInfo {
    private String Firstname;
    private String Lastname;
    private String Address;
    private String City;
    private String Country;
    private String Age;
    private String Height;
    private String Weight;
    private String profileImage;

    public AccountInfo(String firstname, String lastname, String address, String city, String country, String age, String height, String weight, String profileImage) {
        Firstname = firstname;
        Lastname = lastname;
        Address = address;
        City = city;
        Country = country;
        Age = age;
        Height = height;
        Weight = weight;
        this.profileImage = profileImage;
    }

    public String getFirstname() {
        return Firstname;
    }

    public void setFirstname(String firstname) {
        Firstname = firstname;
    }

    public String getLastname() {
        return Lastname;
    }

    public void setLastname(String lastname) {
        Lastname = lastname;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String age) {
        Age = age;
    }

    public String getHeight() {
        return Height;
    }

    public void setHeight(String height) {
        Height = height;
    }

    public String getWeight() {
        return Weight;
    }

    public void setWeight(String weight) {
        Weight = weight;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public AccountInfo(){

    }

}
