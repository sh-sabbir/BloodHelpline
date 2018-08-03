package com.isabbir.bloodhelpline;

/**
 * Created by sabbirhasan on 1/22/17.
 */

public class Shop {

    private int id;
    private String name;
    private String age;
    private String address;
    private String phone;
    private String bloodGroup;
    private String lastDonate;

    public Shop()
    {
    }

    public Shop(int id,String name,String age,String address,String phone,String bloodGroup,String lastDonate)
    {
        this.id=id;
        this.name=name;
        this.age=age;
        this.address=address;
        this.phone=phone;
        this.bloodGroup=bloodGroup;
        this.lastDonate=lastDonate;
    }

    public Shop(String name,String age,String address,String phone,String bloodGroup,String lastDonate)
    {
        this.name=name;
        this.age=age;
        this.address=address;
        this.phone=phone;
        this.bloodGroup=bloodGroup;
        this.lastDonate=lastDonate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public void setLastDonate(String lastDonate) {
        this.lastDonate = lastDonate;
    }

    public int getId() {

        return id;
    }

    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }

    public String getAge() {
        return age;
    }

    public String getPhone() {
        return phone;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public String getLastDonate() {
        return lastDonate;
    }
}
