package com.datagroup.ESLS.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
@Entity
@Table(name = "scans", schema = "tags", catalog = "")
public class Scan implements Serializable {
    private long id;
    private String name;
    private Integer mac;
    private String serialNumber;
    private Integer hardwareVersion;
    private Integer softwareVersion;
    private String productionBatch;
    private String manufacture;
    private Integer status;
    private Integer updateState;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)//自增主键
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = true, length = 30)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
    @Column(name = "serialNumber", nullable = true, length = 20)
    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    @Basic
    @Column(name = "hardwareVersion", nullable = true)
    public Integer getHardwareVersion() {
        return hardwareVersion;
    }

    public void setHardwareVersion(Integer hardwareVersion) {
        this.hardwareVersion = hardwareVersion;
    }

    @Basic
    @Column(name = "softwareVersion", nullable = true)
    public Integer getSoftwareVersion() {
        return softwareVersion;
    }

    public void setSoftwareVersion(Integer softwareVersion) {
        this.softwareVersion = softwareVersion;
    }

    @Basic
    @Column(name = "productionBatch", nullable = true, length = 20)
    public String getProductionBatch() {
        return productionBatch;
    }

    public void setProductionBatch(String productionBatch) {
        this.productionBatch = productionBatch;
    }

    @Basic
    @Column(name = "manufacture", nullable = true, length = 20)
    public String getManufacture() {
        return manufacture;
    }

    public void setManufacture(String manufacture) {
        this.manufacture = manufacture;
    }

    @Basic
    @Column(name = "status", nullable = true)
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Basic
    @Column(name = "updateState", nullable = true)
    public Integer getUpdateState() {
        return updateState;
    }

    public void setUpdateState(Integer updateState) {
        this.updateState = updateState;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Scan scan = (Scan) o;
        return id == scan.id &&
                Objects.equals(name, scan.name) &&
                Objects.equals(mac, scan.mac) &&
                Objects.equals(serialNumber, scan.serialNumber) &&
                Objects.equals(hardwareVersion, scan.hardwareVersion) &&
                Objects.equals(softwareVersion, scan.softwareVersion) &&
                Objects.equals(productionBatch, scan.productionBatch) &&
                Objects.equals(manufacture, scan.manufacture) &&
                Objects.equals(status, scan.status) &&
                Objects.equals(updateState, scan.updateState);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, mac, serialNumber, hardwareVersion, softwareVersion, productionBatch, manufacture, status, updateState);
    }
}
