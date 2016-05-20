package com.wz.bubble.bubble.model;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;

/**
 * Created by BANBEICHAS on 2016/5/12.
 */
public class User extends BmobObject{
    String name;
    String sex;
    String headPic;
    String city;
    String province;
    String pwd;
    String phone;

    public User() {
    }

    public User(String name, String pwd, String sex, String phone, String headPic, String province, String city) {
        this.name = name;
        this.pwd=pwd;
        this.sex = sex;
        this.headPic = headPic;
        this.city = city;
        this.province=province;
        this.phone=phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
