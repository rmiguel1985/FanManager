package com.adictosalainformatica.fanmanager.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ruben on 17/09/16.
 */

public class FanModel {
    @SerializedName("pin")
    @Expose
    private Integer pin;
    @SerializedName("status")
    @Expose
    private Integer status;

    /**
     *
     * @return
     * The pin
     */
    public Integer getPin() {
        return pin;
    }

    /**
     *
     * @param pin
     * The pin
     */
    public void setPin(Integer pin) {
        this.pin = pin;
    }

    /**
     *
     * @return
     * The status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     *
     * @param status
     * The status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }
}