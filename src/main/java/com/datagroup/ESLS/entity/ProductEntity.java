package com.datagroup.ESLS.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "product", schema = "tags", catalog = "")
public class ProductEntity {
    private long id;
    private String productId;
    private String name;
    private String place;
    private String seller;
    private double price;
    private String unit;
    private double promotionprice;
    private boolean ifPromotion;
    private String reason;
    private int barcode;
    private String qrcode;
    private boolean ifBind;
    private String operator;
    private Timestamp updatetime;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//自增主键
    @Column(name = "id", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "product_id", nullable = false, length = 50)
    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 50)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "place", nullable = false, length = 20)
    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    @Basic
    @Column(name = "seller", nullable = false, length = 20)
    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    @Basic
    @Column(name = "price", nullable = false, precision = 0)
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Basic
    @Column(name = "unit", nullable = false, length = 10)
    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Basic
    @Column(name = "promotionprice", nullable = false, precision = 0)
    public double getPromotionprice() {
        return promotionprice;
    }

    public void setPromotionprice(double promotionprice) {
        this.promotionprice = promotionprice;
    }

    @Basic
    @Column(name = "if_promotion", nullable = false,length =1)
    public boolean getIfPromotion() {
        return ifPromotion;
    }

    public void setIfPromotion(boolean ifPromotion) {
        this.ifPromotion = ifPromotion;
    }

    @Basic
    @Column(name = "reason", nullable = false, length = 10)
    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Basic
    @Column(name = "barcode", nullable = false)
    public int getBarcode() {
        return barcode;
    }

    public void setBarcode(int barcode) {
        this.barcode = barcode;
    }

    @Basic
    @Column(name = "qrcode", nullable = false, length = 10)
    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    @Basic
    @Column(name = "if_bind", nullable = false,length =1)
    public boolean getIfBind() {
        return ifBind;
    }

    public void setIfBind(boolean ifBind) {
        this.ifBind = ifBind;
    }

    @Basic
    @Column(name = "operator", nullable = false, length = 40)
    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    @Basic
    @Column(name = "updatetime", nullable = false)
    public Timestamp getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Timestamp updatetime) {
        this.updatetime = updatetime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductEntity that = (ProductEntity) o;
        return id == that.id &&
                Double.compare(that.price, price) == 0 &&
                Double.compare(that.promotionprice, promotionprice) == 0 &&
                ifPromotion == that.ifPromotion &&
                barcode == that.barcode &&
                ifBind == that.ifBind &&
                Objects.equals(productId, that.productId) &&
                Objects.equals(name, that.name) &&
                Objects.equals(place, that.place) &&
                Objects.equals(seller, that.seller) &&
                Objects.equals(unit, that.unit) &&
                Objects.equals(reason, that.reason) &&
                Objects.equals(qrcode, that.qrcode) &&
                Objects.equals(operator, that.operator) &&
                Objects.equals(updatetime, that.updatetime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productId, name, place, seller, price, unit, promotionprice, ifPromotion, reason, barcode, qrcode, ifBind, operator, updatetime);
    }
}
