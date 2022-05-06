package com.yangguanglu.sptest.model.buff;
public class Asset_info
{
    private boolean has_tradable_cooldown;

    private String tradable_cooldown_text;

    private String action_link;

    private String classid;

    private String instanceid;

    private String assetid;

    private int appid;

    private int goods_id;

    private int contextid;

    private String paintwear;

    private Info info;

    public void setHas_tradable_cooldown(boolean has_tradable_cooldown){
        this.has_tradable_cooldown = has_tradable_cooldown;
    }
    public boolean getHas_tradable_cooldown(){
        return this.has_tradable_cooldown;
    }
    public void setTradable_cooldown_text(String tradable_cooldown_text){
        this.tradable_cooldown_text = tradable_cooldown_text;
    }
    public String getTradable_cooldown_text(){
        return this.tradable_cooldown_text;
    }
    public void setAction_link(String action_link){
        this.action_link = action_link;
    }
    public String getAction_link(){
        return this.action_link;
    }
    public void setClassid(String classid){
        this.classid = classid;
    }
    public String getClassid(){
        return this.classid;
    }
    public void setInstanceid(String instanceid){
        this.instanceid = instanceid;
    }
    public String getInstanceid(){
        return this.instanceid;
    }
    public void setAssetid(String assetid){
        this.assetid = assetid;
    }
    public String getAssetid(){
        return this.assetid;
    }
    public void setAppid(int appid){
        this.appid = appid;
    }
    public int getAppid(){
        return this.appid;
    }
    public void setGoods_id(int goods_id){
        this.goods_id = goods_id;
    }
    public int getGoods_id(){
        return this.goods_id;
    }
    public void setContextid(int contextid){
        this.contextid = contextid;
    }
    public int getContextid(){
        return this.contextid;
    }
    public void setPaintwear(String paintwear){
        this.paintwear = paintwear;
    }
    public String getPaintwear(){
        return this.paintwear;
    }
    public void setInfo(Info info){
        this.info = info;
    }
    public Info getInfo(){
        return this.info;
    }
}

