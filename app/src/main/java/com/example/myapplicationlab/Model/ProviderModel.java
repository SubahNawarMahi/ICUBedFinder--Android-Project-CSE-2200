package com.example.myapplicationlab.Model;

public class ProviderModel {
    private String id;
    private String hospiname;
    private String hospiadd;
    private String district;
    private String totalICUBed;
    private String contact;
    private String availICUBed;
    private String CGB;
    private String CGBP;
    private String NCGB;
    private String NCGBP;
    private String CIGB;
    private String CIGBP;
    private String NCIGB;
    private String NCIGBP;
    private String email;
    private String userId;

    public ProviderModel() {
    }

    public ProviderModel(String id, String hospiname, String hospiadd, String district, String totalICUBed, String contact, String availICUBed, String CGB, String CGBP, String NCGB, String NCGBP, String CIGB, String CIGBP, String NCIGB, String NCIGBP, String email, String userId) {
        this.id = id;
        this.hospiname = hospiname;
        this.hospiadd = hospiadd;
        this.district = district;
        this.totalICUBed = totalICUBed;
        this.contact = contact;
        this.availICUBed = availICUBed;
        this.CGB = CGB;
        this.CGBP = CGBP;
        this.NCGB = NCGB;
        this.NCGBP = NCGBP;
        this.CIGB = CIGB;
        this.CIGBP = CIGBP;
        this.NCIGB = NCIGB;
        this.NCIGBP = NCIGBP;
        this.email = email;
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHospiname() {
        return hospiname;
    }

    public void setHospiname(String hospiname) {
        this.hospiname = hospiname;
    }

    public String getHospiadd() {
        return hospiadd;
    }

    public void setHospiadd(String hospiadd) {
        this.hospiadd = hospiadd;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getTotalICUBed() {
        return totalICUBed;
    }

    public void setTotalICUBed(String totalICUBed) {
        this.totalICUBed = totalICUBed;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAvailICUBed() {
        return availICUBed;
    }

    public void setAvailICUBed(String availICUBed) {
        this.availICUBed = availICUBed;
    }

    public String getCGB() {
        return CGB;
    }

    public void setCGB(String CGB) {
        this.CGB = CGB;
    }

    public String getCGBP() {
        return CGBP;
    }

    public void setCGBP(String CGBP) {
        this.CGBP = CGBP;
    }

    public String getNCGB() {
        return NCGB;
    }

    public void setNCGB(String NCGB) {
        this.NCGB = NCGB;
    }

    public String getNCGBP() {
        return NCGBP;
    }

    public void setNCGBP(String NCGBP) {
        this.NCGBP = NCGBP;
    }

    public String getCIGB() {
        return CIGB;
    }

    public void setCIGB(String CIGB) {
        this.CIGB = CIGB;
    }

    public String getCIGBP() {
        return CIGBP;
    }

    public void setCIGBP(String CIGBP) {
        this.CIGBP = CIGBP;
    }

    public String getNCIGB() {
        return NCIGB;
    }

    public void setNCIGB(String NCIGB) {
        this.NCIGB = NCIGB;
    }

    public String getNCIGBP() {
        return NCIGBP;
    }

    public void setNCIGBP(String NCIGBP) {
        this.NCIGBP = NCIGBP;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}

