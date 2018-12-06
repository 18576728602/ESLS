package com.datagroup.ESLS.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
public class Logs implements Serializable {
    private Long id;
    private String username;
    private String operation;
    private String method;
    private String params;
    private String ip;
    private String runnintTime;
    private Timestamp createDate;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)//自增主键

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "username", nullable = true, length = 255)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "operation", nullable = true, length = 255)
    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    @Basic
    @Column(name = "method", nullable = true, length = 255)
    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    @Basic
    @Column(name = "params", nullable = true, length = 255)
    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    @Basic
    @Column(name = "ip", nullable = true, length = 255)
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Basic
    @Column(name = "runnint_time", nullable = true, length = 255)
    public String getRunnintTime() {
        return runnintTime;
    }

    public void setRunnintTime(String runnintTime) {
        this.runnintTime = runnintTime;
    }

    @Basic
    @Column(name = "create_date", nullable = false)
    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Logs logs = (Logs) o;
        return Objects.equals(id, logs.id) &&
                Objects.equals(username, logs.username) &&
                Objects.equals(operation, logs.operation) &&
                Objects.equals(method, logs.method) &&
                Objects.equals(params, logs.params) &&
                Objects.equals(ip, logs.ip) &&
                Objects.equals(runnintTime, logs.runnintTime) &&
                Objects.equals(createDate, logs.createDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, operation, method, params, ip, runnintTime, createDate);
    }
}
