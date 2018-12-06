package com.datagroup.ESLS.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "tb_rootrouters", schema = "tags", catalog = "")
public class TbRootrouters implements Serializable {
    private long id;
    private Integer mac;
    private Integer sendBaudrate;
    private Integer receiveBaudrate;
    private Integer wakeup;
    private Integer enterSleep;
    private Integer frequency;
    private Integer power;

    @Id
    @Column(name = "id", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "mac", nullable = true)
    public Integer getMac() {
        return mac;
    }

    public void setMac(Integer mac) {
        this.mac = mac;
    }

    @Basic
    @Column(name = "sendBaudrate", nullable = true)
    public Integer getSendBaudrate() {
        return sendBaudrate;
    }

    public void setSendBaudrate(Integer sendBaudrate) {
        this.sendBaudrate = sendBaudrate;
    }

    @Basic
    @Column(name = "receiveBaudrate", nullable = true)
    public Integer getReceiveBaudrate() {
        return receiveBaudrate;
    }

    public void setReceiveBaudrate(Integer receiveBaudrate) {
        this.receiveBaudrate = receiveBaudrate;
    }

    @Basic
    @Column(name = "wakeup", nullable = true)
    public Integer getWakeup() {
        return wakeup;
    }

    public void setWakeup(Integer wakeup) {
        this.wakeup = wakeup;
    }

    @Basic
    @Column(name = "enterSleep", nullable = true)
    public Integer getEnterSleep() {
        return enterSleep;
    }

    public void setEnterSleep(Integer enterSleep) {
        this.enterSleep = enterSleep;
    }

    @Basic
    @Column(name = "frequency", nullable = true)
    public Integer getFrequency() {
        return frequency;
    }

    public void setFrequency(Integer frequency) {
        this.frequency = frequency;
    }

    @Basic
    @Column(name = "power", nullable = true)
    public Integer getPower() {
        return power;
    }

    public void setPower(Integer power) {
        this.power = power;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TbRootrouters that = (TbRootrouters) o;
        return id == that.id &&
                Objects.equals(mac, that.mac) &&
                Objects.equals(sendBaudrate, that.sendBaudrate) &&
                Objects.equals(receiveBaudrate, that.receiveBaudrate) &&
                Objects.equals(wakeup, that.wakeup) &&
                Objects.equals(enterSleep, that.enterSleep) &&
                Objects.equals(frequency, that.frequency) &&
                Objects.equals(power, that.power);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, mac, sendBaudrate, receiveBaudrate, wakeup, enterSleep, frequency, power);
    }
}
