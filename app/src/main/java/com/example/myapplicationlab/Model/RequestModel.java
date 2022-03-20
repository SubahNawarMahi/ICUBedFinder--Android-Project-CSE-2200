package com.example.myapplicationlab.Model;

public class RequestModel {

    String id;
    String uid;
    String hosName;
    String patient;
    String address;
    String email;
    String phone;
    String bedType;
    String noBed;
    String status;
    String price;
    String hosId;

    public RequestModel() {
    }

    public RequestModel(String id, String uid, String hosName, String patient, String address, String email, String phone, String bedType, String noBed, String status, String price, String hosId) {
        this.id = id;
        this.uid = uid;
        this.hosName = hosName;
        this.patient = patient;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.bedType = bedType;
        this.noBed = noBed;
        this.status = status;
        this.price = price;
        this.hosId = hosId;
    }

    public String getHosName() {
        return hosName;
    }

    public void setHosName(String hosName) {
        this.hosName = hosName;
    }

    public String getHosId() {
        return hosId;
    }

    public void setHosId(String hosId) {
        this.hosId = hosId;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPatient() {
        return patient;
    }

    public void setPatient(String patient) {
        this.patient = patient;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBedType() {
        return bedType;
    }

    public void setBedType(String bedType) {
        this.bedType = bedType;
    }

    public String getNoBed() {
        return noBed;
    }

    public void setNoBed(String noBed) {
        this.noBed = noBed;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
