package com.yangguanglu.sptest.model.c5;
public class SellerInfo
{
    private String thirdUserId;

    private DeliveryInfo deliveryInfo;

    private int lastActive;

    private String nickname;

    private int platformId;

    private String avatar;

    private String userId;

    public void setThirdUserId(String thirdUserId){
        this.thirdUserId = thirdUserId;
    }
    public String getThirdUserId(){
        return this.thirdUserId;
    }
    public void setDeliveryInfo(DeliveryInfo deliveryInfo){
        this.deliveryInfo = deliveryInfo;
    }
    public DeliveryInfo getDeliveryInfo(){
        return this.deliveryInfo;
    }
    public void setLastActive(int lastActive){
        this.lastActive = lastActive;
    }
    public int getLastActive(){
        return this.lastActive;
    }
    public void setNickname(String nickname){
        this.nickname = nickname;
    }
    public String getNickname(){
        return this.nickname;
    }
    public void setPlatformId(int platformId){
        this.platformId = platformId;
    }
    public int getPlatformId(){
        return this.platformId;
    }
    public void setAvatar(String avatar){
        this.avatar = avatar;
    }
    public String getAvatar(){
        return this.avatar;
    }
    public void setUserId(String userId){
        this.userId = userId;
    }
    public String getUserId(){
        return this.userId;
    }
}

