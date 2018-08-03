package com.isabbir.bloodhelpline;

public class Note {
    private String _id;
    private String name;
    private String age;
    private String address;
    private String phone;
    private String blood_group;
    private String last_donated;

    public String getName() {
        return name;
    }

    public Note setName(String name) {
        this.name = name;
        return this;
    }

    public String getAge() {
        return age;
    }

    public Note setAge(String age) {
        this.age = age;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public Note setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public Note setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getBgroup() {
        return blood_group;
    }

    public Note setBgroup(String blood_group) {
        this.blood_group = blood_group;
        return this;
    }

    public String getLastDonated() {
        return last_donated;
    }

    public Note setLastDonated(String last_donated) {
        this.last_donated = last_donated;
        return this;
    }

    public String getId() {
        return _id;
    }

    public Note setId(String _id) {
        this._id = _id;
        return this;
    }
}