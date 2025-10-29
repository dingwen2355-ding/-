package com.znv.manage.common.bean;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class ResourceTeamMember {
    private Integer id;

    private String memberIcon;

    private Integer belongTeamId;

    private String memberName;

    private String memberSex;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date memberBirthday;

    private String workStation;

    private String helpType;

    private String phone;

    private String isSpecial;

    private String isUseAble;

    private String specialMajor;

    private String contents;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date workingDate;

    private String specialities;

    private String rescueInfo;

    private String aptitudeInfo;

    private String age;

    private String specialitiesName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getMemberIcon() {
        return memberIcon;
    }

    public void setMemberIcon(String memberIcon) {
        this.memberIcon = memberIcon == null ? null : memberIcon.trim();
    }

    public Integer getBelongTeamId() {
        return belongTeamId;
    }

    public void setBelongTeamId(Integer belongTeamId) {
        this.belongTeamId = belongTeamId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName == null ? null : memberName.trim();
    }

    public String getMemberSex() {
        return memberSex;
    }

    public void setMemberSex(String memberSex) {
        this.memberSex = memberSex == null ? null : memberSex.trim();
    }

    public Date getMemberBirthday() {
        return memberBirthday;
    }

    public void setMemberBirthday(Date memberBirthday) {
        this.memberBirthday = memberBirthday;
    }

    public String getWorkStation() {
        return workStation;
    }

    public void setWorkStation(String workStation) {
        this.workStation = workStation == null ? null : workStation.trim();
    }

    public String getHelpType() {
        return helpType;
    }

    public void setHelpType(String helpType) {
        this.helpType = helpType == null ? null : helpType.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getSpecialitiesName() {
        return specialitiesName;
    }

    public void setSpecialitiesName(String specialitiesName) {
        this.specialitiesName = specialitiesName == null ? null : specialitiesName.trim();
    }

    public String getIsSpecial() {
        return isSpecial;
    }

    public void setIsSpecial(String isSpecial) {
        this.isSpecial = isSpecial == null ? null : isSpecial.trim();
    }

    public String getIsUseAble() {
        return isUseAble;
    }

    public void setIsUseAble(String isUseAble) {
        this.isUseAble = isUseAble == null ? null : isUseAble.trim();
    }

    public String getSpecialMajor() {
        return specialMajor;
    }

    public void setSpecialMajor(String specialMajor) {
        this.specialMajor = specialMajor == null ? null : specialMajor.trim();
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents == null ? null : contents.trim();
    }

    public Date getWorkingDate() {
        return workingDate;
    }

    public void setWorkingDate(Date workingDate) {
        this.workingDate = workingDate;
    }

    public String getSpecialities() {
        return specialities;
    }

    public void setSpecialities(String specialities) {
        this.specialities = specialities == null ? null : specialities.trim();
    }

    public String getRescueInfo() {
        return rescueInfo;
    }

    public void setRescueInfo(String rescueInfo) {
        this.rescueInfo = rescueInfo == null ? null : rescueInfo.trim();
    }

    public String getAptitudeInfo() {
        return aptitudeInfo;
    }

    public void setAptitudeInfo(String aptitudeInfo) {
        this.aptitudeInfo = aptitudeInfo == null ? null : aptitudeInfo.trim();
    }
}