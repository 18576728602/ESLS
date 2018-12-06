package com.datagroup.ESLS.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
@Entity
@Table(name = "goods", schema = "tags", catalog = "")
@Data
@Proxy(lazy = false)
public class Good implements Serializable {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)//自增主键
    private long id;
    @Column(name = "name")
    private String name;
    @Column(name = "origin")
    private String origin;
    @Column(name = "provider")
    private String provider;
    @Column(name = "unit")
    private String unit;
    @Column(name = "barCode")
    private String barCode;
    @Column(name = "qrCode")
    private String qrCode;
    @Column(name = "operator")
    private String operator;
    @Column(name = "importTime")
    private Timestamp importTime;
    @Column(name = "promotionReason")
    private String promotionReason;
    @Column(name = "status")
    private Integer status;
    @Column(name = "price")
    private double price;
    @Column(name = "promotePrice")
    private double promotePrice;
    @Column(name = "photo")
    private byte[] photo;
    @Column(name = "waitUpdate")
    private Integer waitUpdate;
    @Column(name = "shelfNumber")
    private String shelfNumber;
    @Column(name = "spec")
    private String spec;
    @Column(name = "category")
    private String category;
    @Column(name = "rfu01")
    private String rfu01;
    @Column(name = "rfu02")
    private String rfu02;
    @Column(name = "rfus01")
    private String rfus01;
    @Column(name = "rfus02")
    private String rfus02;
    @OneToMany(mappedBy = "good",fetch = FetchType.EAGER)
    @JsonIgnore
    private Collection<Tag> tags;
    @OneToMany(mappedBy = "good")
    @JsonIgnore
    private Collection<TagandGood> tagsAndGoods;
    public Good() {}

    public Good(String barCode, String name, double price, double promotePrice,String promotionReason, String unit ,String origin ,String spec,String category,String shelfNumber,String rfus01,String rfus02,String qrCode,String provider) {
        this.barCode = barCode;
        this.name = name;
        this.price = price;
        this.promotePrice = promotePrice;
        this.promotionReason = promotionReason;
        this.unit = unit;
        this.origin = origin;
        this.spec = spec;
        this.category = category;
        this.shelfNumber = shelfNumber;
        this.rfus01 = rfus01;
        this.rfus02 = rfus02;
        this.qrCode = qrCode;
        this.provider = provider;
    }
}
