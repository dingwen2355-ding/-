package com.ruoyi.system.customer.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.system.customer.CmBaseEntity;
import lombok.Data;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

/**
 *
 * @author ruoyi
 */

/**
 * 住户信息
 */
public class ResidentInfo
{


    /**
     * id
     */
    private String id;
    /**
     * 名字
     */
    private String name;

    /**
     * 证件种类
     */
    private String zjzl;
    /**
     * 身份证号
     */
    private String idNumber;
    /**
     * 户口省县的代码
     */
    private String HKSXDM;

    /**
     * 户口省县
     */
    private String HKSX;


    /**
     * 户口详址
     */
    private String HKXZ;

    /**
     * 历史人员 0 否 1是
     */
    private String HISTORY;

    /**
     *1常口2暂口3境外人员4无证人员
     */
    private String types;

    /**
     * 1人户一致2人在户不在3户在人不在
     */
    private String CZRKTYPE;

    /**
     * 地址编码
     */
    private String buildingCode;

    /**
     * 房间编码
     */
    private String roomCode;

    /**
     * 数据操作时间
     */
    private String  SJCZSJ;
    /**
     *
     */
    private String SYNC_DATE;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getZjzl() {
        return zjzl;
    }

    public void setZjzl(String zjzl) {
        this.zjzl = zjzl;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getHKSXDM() {
        return HKSXDM;
    }

    public void setHKSXDM(String HKSXDM) {
        this.HKSXDM = HKSXDM;
    }

    public String getHKSX() {
        return HKSX;
    }

    public void setHKSX(String HKSX) {
        this.HKSX = HKSX;
    }

    public String getHKXZ() {
        return HKXZ;
    }

    public void setHKXZ(String HKXZ) {
        this.HKXZ = HKXZ;
    }

    public String getHISTORY() {
        return HISTORY;
    }

    public void setHISTORY(String HISTORY) {
        this.HISTORY = HISTORY;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }

    public String getCZRKTYPE() {
        return CZRKTYPE;
    }

    public void setCZRKTYPE(String CZRKTYPE) {
        this.CZRKTYPE = CZRKTYPE;
    }

    public String getBuildingCode() {
        return buildingCode;
    }

    public void setBuildingCode(String buildingCode) {
        this.buildingCode = buildingCode;
    }

    public String getRoomCode() {
        return roomCode;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }

    public String getSJCZSJ() {
        return SJCZSJ;
    }

    public void setSJCZSJ(String SJCZSJ) {
        this.SJCZSJ = SJCZSJ;
    }

    public String getSYNC_DATE() {
        return SYNC_DATE;
    }

    public void setSYNC_DATE(String SYNC_DATE) {
        this.SYNC_DATE = SYNC_DATE;
    }

    @Override
    public String toString() {
        return "ResidentInfo{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", zjzl='" + zjzl + '\'' +
                ", idNumber='" + idNumber + '\'' +
                ", HKSXDM='" + HKSXDM + '\'' +
                ", HKSX='" + HKSX + '\'' +
                ", HKXZ='" + HKXZ + '\'' +
                ", HISTORY='" + HISTORY + '\'' +
                ", types='" + types + '\'' +
                ", CZRKTYPE='" + CZRKTYPE + '\'' +
                ", buildingCode='" + buildingCode + '\'' +
                ", roomCode='" + roomCode + '\'' +
                ", SJCZSJ='" + SJCZSJ + '\'' +
                ", SYNC_DATE='" + SYNC_DATE + '\'' +
                '}';
    }
}
