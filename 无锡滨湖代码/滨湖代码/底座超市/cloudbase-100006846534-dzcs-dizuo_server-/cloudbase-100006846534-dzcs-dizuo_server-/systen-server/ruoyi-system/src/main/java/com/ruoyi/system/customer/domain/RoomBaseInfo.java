package com.ruoyi.system.customer.domain;

/**
 *
 * @author ruoyi
 */

/**
 * 住户信息
 */
public class RoomBaseInfo
{


    /**
     * id
     */
    private String id;
    /**
     * 房间名称
     */
    private String roomName;

    /**
     * 房间状态（1自住2出租3群租4空置5单位6落户待查7自住兼出租8宿舍 空：落户待查）
     */
    private String usa;
    /**
     * 位置
     */
    private String location;

    /**
     * 所在楼层数
     */
    private String floor;

    /**
     * 所在单元
     */
    private String unite;

    /**
     * 楼层名简称
     */
    private String flpprShowName;

    /**
     * 单元名简称
     */
    private String uniteShowName;

    /**
     *楼幢编码
     */
    private String buildingCode;

    /**
     * 房间编码
     */
    private String roomCode;

    /**
     * '房间是否已访查，0未访查，1已访查
     */
    private String fangCha;

    /**
     * 二维码
     */
    private String barCode;

    /**
     * 二维码编码
     */
    private String  barCodeInfo;
    /**
     *添加时间
     */
    private String addTime;

    /**
     *添加人编码
     */
    private String addUserCode;
    /**
     *添加人姓名
     */
    private String addUserName;
    /**
     *
     */
    private String QXDM;
    /**
     *
     */
    private String PCSDM;
    /**
     *
     */
    private String XZJDDM;
    /**
     *
     */
    private String SQDM;
    /**
     *
     */
    private String FJDM;

    /**
     *访查时间
     */
    private String fangChaTime;
    /**
     *
     */
    private String WGDM;
    /**
     *
     */
    private String standardAddress;
    /**
     *档案号
     */
    private String dah;
    /**
     *户号
     */
    private String hh;
    /**
     *状态：0正常1删除
     */
    private String state;
    /**
     *访查人员编号
     */
    private String fangChaUserCode;
    /**
     *访查人员姓名
     */
    private String fangChaUserNanme;

    /**
     *数据操作时间
     */
    private String SJCZSJ;
    /**
     *更新时间
     */
    private String updateTime;
    /**
     *更新人编码
     */
    private String updateUserCode;
    /**
     *更信任姓名
     */
    private String updateUserName;
    /**
     *删除时间
     */
    private String deleteTime;
    /**
     *删除人编码
     */
    private String deleteUserCode;
    /**
     *删除人姓名
     */
    private String deleteUserName;
    /**
     *
     */
    private String MLPID;
    /**
     *
     */
    private String YBZMLPID;
    /**
     *
     */
    private String BDYY;
    /**
     *是否群租房，1是2否
     */
    private String SFQZ;
    /**
     *房间类型，非标准地址房间有 code_dic
     */
    private String roomType;
    /**
     *
     */
    private String SYNC_DATE;

    private String lz;

    public String getLz() {
        return lz;
    }

    public void setLz(String lz) {
        this.lz = lz;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getUsa() {
        return usa;
    }

    public void setUsa(String usa) {
        this.usa = usa;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getUnite() {
        return unite;
    }

    public void setUnite(String unite) {
        this.unite = unite;
    }

    public String getFlpprShowName() {
        return flpprShowName;
    }

    public void setFlpprShowName(String flpprShowName) {
        this.flpprShowName = flpprShowName;
    }

    public String getUniteShowName() {
        return uniteShowName;
    }

    public void setUniteShowName(String uniteShowName) {
        this.uniteShowName = uniteShowName;
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

    public String getFangCha() {
        return fangCha;
    }

    public void setFangCha(String fangCha) {
        this.fangCha = fangCha;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getBarCodeInfo() {
        return barCodeInfo;
    }

    public void setBarCodeInfo(String barCodeInfo) {
        this.barCodeInfo = barCodeInfo;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getAddUserCode() {
        return addUserCode;
    }

    public void setAddUserCode(String addUserCode) {
        this.addUserCode = addUserCode;
    }

    public String getAddUserName() {
        return addUserName;
    }

    public void setAddUserName(String addUserName) {
        this.addUserName = addUserName;
    }

    public String getQXDM() {
        return QXDM;
    }

    public void setQXDM(String QXDM) {
        this.QXDM = QXDM;
    }

    public String getPCSDM() {
        return PCSDM;
    }

    public void setPCSDM(String PCSDM) {
        this.PCSDM = PCSDM;
    }

    public String getXZJDDM() {
        return XZJDDM;
    }

    public void setXZJDDM(String XZJDDM) {
        this.XZJDDM = XZJDDM;
    }

    public String getSQDM() {
        return SQDM;
    }

    public void setSQDM(String SQDM) {
        this.SQDM = SQDM;
    }

    public String getFJDM() {
        return FJDM;
    }

    public void setFJDM(String FJDM) {
        this.FJDM = FJDM;
    }

    public String getFangChaTime() {
        return fangChaTime;
    }

    public void setFangChaTime(String fangChaTime) {
        this.fangChaTime = fangChaTime;
    }

    public String getWGDM() {
        return WGDM;
    }

    public void setWGDM(String WGDM) {
        this.WGDM = WGDM;
    }

    public String getStandardAddress() {
        return standardAddress;
    }

    public void setStandardAddress(String standardAddress) {
        this.standardAddress = standardAddress;
    }

    public String getDah() {
        return dah;
    }

    public void setDah(String dah) {
        this.dah = dah;
    }

    public String getHh() {
        return hh;
    }

    public void setHh(String hh) {
        this.hh = hh;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getFangChaUserCode() {
        return fangChaUserCode;
    }

    public void setFangChaUserCode(String fangChaUserCode) {
        this.fangChaUserCode = fangChaUserCode;
    }

    public String getFangChaUserNanme() {
        return fangChaUserNanme;
    }

    public void setFangChaUserNanme(String fangChaUserNanme) {
        this.fangChaUserNanme = fangChaUserNanme;
    }

    public String getSJCZSJ() {
        return SJCZSJ;
    }

    public void setSJCZSJ(String SJCZSJ) {
        this.SJCZSJ = SJCZSJ;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateUserCode() {
        return updateUserCode;
    }

    public void setUpdateUserCode(String updateUserCode) {
        this.updateUserCode = updateUserCode;
    }

    public String getUpdateUserName() {
        return updateUserName;
    }

    public void setUpdateUserName(String updateUserName) {
        this.updateUserName = updateUserName;
    }

    public String getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(String deleteTime) {
        this.deleteTime = deleteTime;
    }

    public String getDeleteUserCode() {
        return deleteUserCode;
    }

    public void setDeleteUserCode(String deleteUserCode) {
        this.deleteUserCode = deleteUserCode;
    }

    public String getDeleteUserName() {
        return deleteUserName;
    }

    public void setDeleteUserName(String deleteUserName) {
        this.deleteUserName = deleteUserName;
    }

    public String getMLPID() {
        return MLPID;
    }

    public void setMLPID(String MLPID) {
        this.MLPID = MLPID;
    }

    public String getYBZMLPID() {
        return YBZMLPID;
    }

    public void setYBZMLPID(String YBZMLPID) {
        this.YBZMLPID = YBZMLPID;
    }

    public String getBDYY() {
        return BDYY;
    }

    public void setBDYY(String BDYY) {
        this.BDYY = BDYY;
    }

    public String getSFQZ() {
        return SFQZ;
    }

    public void setSFQZ(String SFQZ) {
        this.SFQZ = SFQZ;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getSYNC_DATE() {
        return SYNC_DATE;
    }

    public void setSYNC_DATE(String SYNC_DATE) {
        this.SYNC_DATE = SYNC_DATE;
    }

    @Override
    public String toString() {
        return "RoomBaseInfo{" +
                "id='" + id + '\'' +
                ", roomName='" + roomName + '\'' +
                ", usa='" + usa + '\'' +
                ", location='" + location + '\'' +
                ", floor='" + floor + '\'' +
                ", unite='" + unite + '\'' +
                ", flpprShowName='" + flpprShowName + '\'' +
                ", uniteShowName='" + uniteShowName + '\'' +
                ", buildingCode='" + buildingCode + '\'' +
                ", roomCode='" + roomCode + '\'' +
                ", fangCha='" + fangCha + '\'' +
                ", barCode='" + barCode + '\'' +
                ", barCodeInfo='" + barCodeInfo + '\'' +
                ", addTime='" + addTime + '\'' +
                ", addUserCode='" + addUserCode + '\'' +
                ", addUserName='" + addUserName + '\'' +
                ", QXDM='" + QXDM + '\'' +
                ", PCSDM='" + PCSDM + '\'' +
                ", XZJDDM='" + XZJDDM + '\'' +
                ", SQDM='" + SQDM + '\'' +
                ", FJDM='" + FJDM + '\'' +
                ", fangChaTime='" + fangChaTime + '\'' +
                ", WGDM='" + WGDM + '\'' +
                ", standardAddress='" + standardAddress + '\'' +
                ", dah='" + dah + '\'' +
                ", hh='" + hh + '\'' +
                ", state='" + state + '\'' +
                ", fangChaUserCode='" + fangChaUserCode + '\'' +
                ", fangChaUserNanme='" + fangChaUserNanme + '\'' +
                ", SJCZSJ='" + SJCZSJ + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", updateUserCode='" + updateUserCode + '\'' +
                ", updateUserName='" + updateUserName + '\'' +
                ", deleteTime='" + deleteTime + '\'' +
                ", deleteUserCode='" + deleteUserCode + '\'' +
                ", deleteUserName='" + deleteUserName + '\'' +
                ", MLPID='" + MLPID + '\'' +
                ", YBZMLPID='" + YBZMLPID + '\'' +
                ", BDYY='" + BDYY + '\'' +
                ", SFQZ='" + SFQZ + '\'' +
                ", roomType='" + roomType + '\'' +
                ", SYNC_DATE='" + SYNC_DATE + '\'' +
                '}';
    }
}
