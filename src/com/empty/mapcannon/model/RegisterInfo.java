
package com.empty.mapcannon.model;

import java.io.Serializable;

public class RegisterInfo implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String city;

    private String gender;

    private String nickname;

    private String password;

    private String phone;

    private String province;

    public String getCity() {
        return this.city;
    }

    public String getGender() {
        return this.gender;
    }

    public String getNickname() {
        return this.nickname;
    }

    public String getPassword() {
        return this.password;
    }

    public String getPhone() {
        return this.phone;
    }

    public String getProvince() {
        return this.province;
    }

    public void setCity(String paramString) {
        this.city = paramString;
    }

    public void setGender(String paramString) {
        this.gender = paramString;
    }

    public void setNickname(String paramString) {
        this.nickname = paramString;
    }

    public void setPassword(String paramString) {
        this.password = paramString;
    }

    public void setPhone(String paramString) {
        this.phone = paramString;
    }

    public void setProvince(String paramString) {
        this.province = paramString;
    }
}
