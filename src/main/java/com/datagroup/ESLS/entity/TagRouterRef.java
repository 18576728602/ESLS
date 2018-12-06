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
@Table(name = "tb_tag_router_ref", schema = "tags", catalog = "")
public class TagRouterRef implements Serializable {
    private long id;
    private int routerMac;
    private double routerRssi;
    private Tag tag;

    @Id
    @Column(name = "id", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "routerMac", nullable = false)
    public int getRouterMac() {
        return routerMac;
    }

    public void setRouterMac(int routerMac) {
        this.routerMac = routerMac;
    }

    @Basic
    @Column(name = "routerRssi", nullable = false, precision = 0)
    public double getRouterRssi() {
        return routerRssi;
    }

    public void setRouterRssi(double routerRssi) {
        this.routerRssi = routerRssi;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TagRouterRef that = (TagRouterRef) o;
        return id == that.id &&
                routerMac == that.routerMac &&
                Double.compare(that.routerRssi, routerRssi) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, routerMac, routerRssi);
    }

    @ManyToOne
    @JoinColumn(name = "tbTagRouterRefsById", referencedColumnName = "id", nullable = false)
    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tagByTagid) {
        this.tag = tagByTagid;
    }
}
