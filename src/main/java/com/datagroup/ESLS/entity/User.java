package com.datagroup.ESLS.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
@Entity
@Table(name = "users", schema = "tags", catalog = "")
public class User implements Serializable {
    private long id;
    private String name;
    private String passwd;
    private byte sex;
    private String telephone;
    private String address;
    private Integer position;
    private String department;
    private Shop shop;

    @Id
    @Column(name = "id", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 255)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "passwd", nullable = true, length = 255)
    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    @Basic
    @Column(name = "sex", nullable = false)
    public byte getSex() {
        return sex;
    }

    public void setSex(byte sex) {
        this.sex = sex;
    }

    @Basic
    @Column(name = "telephone", nullable = true, length = 255)
    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Basic
    @Column(name = "address", nullable = true, length = 255)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Basic
    @Column(name = "position", nullable = true)
    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    @Basic
    @Column(name = "department", nullable = true, length = 255)
    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                sex == user.sex &&
                Objects.equals(name, user.name) &&
                Objects.equals(passwd, user.passwd) &&
                Objects.equals(telephone, user.telephone) &&
                Objects.equals(address, user.address) &&
                Objects.equals(position, user.position) &&
                Objects.equals(department, user.department);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, passwd, sex, telephone, address, position, department);
    }

    @ManyToOne
    @JoinColumn(name = "shopid", referencedColumnName = "id")
    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }
}
