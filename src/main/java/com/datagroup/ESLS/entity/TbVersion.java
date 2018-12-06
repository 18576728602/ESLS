package com.datagroup.ESLS.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "tb_version", schema = "tags", catalog = "")
public class TbVersion implements Serializable {
    private long id;
    private String softVersion;
    private String productor;
    private Timestamp date;

    @Id
    @Column(name = "id", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "softVersion", nullable = true, length = 20)
    public String getSoftVersion() {
        return softVersion;
    }

    public void setSoftVersion(String softVersion) {
        this.softVersion = softVersion;
    }

    @Basic
    @Column(name = "productor", nullable = true, length = 255)
    public String getProductor() {
        return productor;
    }

    public void setProductor(String productor) {
        this.productor = productor;
    }

    @Basic
    @Column(name = "date", nullable = false)
    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TbVersion tbVersion = (TbVersion) o;
        return id == tbVersion.id &&
                Objects.equals(softVersion, tbVersion.softVersion) &&
                Objects.equals(productor, tbVersion.productor) &&
                Objects.equals(date, tbVersion.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, softVersion, productor, date);
    }
}
