package com.isabbir.bloodhelpline.Models;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Donor {

    public String donorName;
    public String donorAge;
    public String donorPhone;
    public String donorDept;
    public String donorRegNo;
    public String donorSession;
    public String bloodGroup;
    public boolean donated;
    public String lastDonate;

    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public Donor() {
    }

    public Donor(String donorName, String donorAge,String donorPhone, String donorDept, String donorRegNo,
                 String donorSession, String bloodGroup,String lastDonate, boolean donated) {
        this.donorName = donorName;
        this.donorAge = donorAge;
        this.donorPhone = donorPhone;
        this.donorDept = donorDept;
        this.donorRegNo = donorRegNo;
        this.donorSession = donorSession;
        this.bloodGroup = bloodGroup;
        this.lastDonate = lastDonate;
        this.donated = donated;
    }
}