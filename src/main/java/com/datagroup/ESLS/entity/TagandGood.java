package com.datagroup.ESLS.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Objects;
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
@Entity
@Table(name = "tb_tagsandgoods", schema = "tags", catalog = "")
public class TagandGood implements Serializable {
    private long id;
    private double price;
    private String barcode;
    private String qrCode;
    private String shopNumber;
    private byte[] photo;
    private String validity;
    private int status;
    private String name;
    private String origin;
    private String provider;
    private String operator;
    private Timestamp timestamp;
    private Byte promotion;
    private String promotionReason;
    private double promotePrice;
    private Byte waitUpdate;
    private String unit;
    private Good good;
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
    @Column(name = "price", nullable = true, precision = 2)
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Basic
    @Column(name = "barcode", nullable = true, length = 255)
    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    @Basic
    @Column(name = "qrCode", nullable = true, length = 255)
    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    @Basic
    @Column(name = "shopNumber", nullable = true, length = 20)
    public String getShopNumber() {
        return shopNumber;
    }

    public void setShopNumber(String shopNumber) {
        this.shopNumber = shopNumber;
    }

    @Basic
    @Column(name = "photo", nullable = true)
    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    @Basic
    @Column(name = "validity", nullable = true, length = 255)
    public String getValidity() {
        return validity;
    }

    public void setValidity(String validity) {
        this.validity = validity;
    }

    @Basic
    @Column(name = "status", nullable = true)
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Basic
    @Column(name = "name", nullable = true, length = 255)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "origin", nullable = true, length = 255)
    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    @Basic
    @Column(name = "provider", nullable = true, length = 255)
    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    @Basic
    @Column(name = "operator", nullable = true, length = 255)
    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    @Basic
    @Column(name = "timestamp", nullable = true)
    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    @Basic
    @Column(name = "promotion", nullable = true)
    public Byte getPromotion() {
        return promotion;
    }

    public void setPromotion(Byte promotion) {
        this.promotion = promotion;
    }

    @Basic
    @Column(name = "promotionReason", nullable = true, length = 255)
    public String getPromotionReason() {
        return promotionReason;
    }

    public void setPromotionReason(String promotionReason) {
        this.promotionReason = promotionReason;
    }

    @Basic
    @Column(name = "promotePrice", nullable = true, precision = 2)
    public double getPromotePrice() {
        return promotePrice;
    }

    public void setPromotePrice(double promotePrice) {
        this.promotePrice = promotePrice;
    }

    @Basic
    @Column(name = "waitUpdate", nullable = true)
    public Byte getWaitUpdate() {
        return waitUpdate;
    }

    public void setWaitUpdate(Byte waitUpdate) {
        this.waitUpdate = waitUpdate;
    }

    @Basic
    @Column(name = "unit", nullable = true, length = 255)
    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TagandGood that = (TagandGood) o;
        return id == that.id &&
                Objects.equals(price, that.price) &&
                Objects.equals(barcode, that.barcode) &&
                Objects.equals(qrCode, that.qrCode) &&
                Objects.equals(shopNumber, that.shopNumber) &&
                Arrays.equals(photo, that.photo) &&
                Objects.equals(validity, that.validity) &&
                Objects.equals(status, that.status) &&
                Objects.equals(name, that.name) &&
                Objects.equals(origin, that.origin) &&
                Objects.equals(provider, that.provider) &&
                Objects.equals(operator, that.operator) &&
                Objects.equals(timestamp, that.timestamp) &&
                Objects.equals(promotion, that.promotion) &&
                Objects.equals(promotionReason, that.promotionReason) &&
                Objects.equals(promotePrice, that.promotePrice) &&
                Objects.equals(waitUpdate, that.waitUpdate) &&
                Objects.equals(unit, that.unit);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, price, barcode, qrCode, shopNumber, validity, status, name, origin, provider, operator, timestamp, promotion, promotionReason, promotePrice, waitUpdate, unit);
        result = 31 * result + Arrays.hashCode(photo);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "goodid", referencedColumnName = "id")
    public Good getGood() {
        return good;
    }

    public void setGood(Good good) {
        this.good = good;
    }

    @ManyToOne
    @JoinColumn(name = "tagid", referencedColumnName = "id")
    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }
}
