package com.datagroup.ESLS.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
@Entity
@Table(name = "shops", schema = "tags", catalog = "")
public class Shop implements Serializable {
    private long id;
    private byte type;
    private String number;
    private String fatherShop;
    private String name;
    private String manager;
    private String address;
    private String account;
    private String password;
    private String phone;
    private Collection<Router> routers;
    private Collection<User> users;

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
    @Column(name = "type", nullable = false)
    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    @Basic
    @Column(name = "number", nullable = false, length = 20)
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Basic
    @Column(name = "fatherShop", nullable = true, length = 20)
    public String getFatherShop() {
        return fatherShop;
    }

    public void setFatherShop(String fatherShop) {
        this.fatherShop = fatherShop;
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
    @Column(name = "manager", nullable = false, length = 10)
    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    @Basic
    @Column(name = "address", nullable = true, length = 50)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Basic
    @Column(name = "account", nullable = true, length = 20)
    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    @Basic
    @Column(name = "password", nullable = true, length = 20)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "phone", nullable = true, length = 11)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shop shop = (Shop) o;
        return id == shop.id &&
                type == shop.type &&
                Objects.equals(number, shop.number) &&
                Objects.equals(fatherShop, shop.fatherShop) &&
                Objects.equals(name, shop.name) &&
                Objects.equals(manager, shop.manager) &&
                Objects.equals(address, shop.address) &&
                Objects.equals(account, shop.account) &&
                Objects.equals(password, shop.password) &&
                Objects.equals(phone, shop.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, number, fatherShop, name, manager, address, account, password, phone);
    }

    @OneToMany(mappedBy = "shop")
    @JsonIgnore
    public Collection<Router> getRouters() {
        return routers;
    }

    public void setRouters(Collection<Router> routers) {
        this.routers = routers;
    }

    @OneToMany(mappedBy = "shop")
    @JsonIgnore
    public Collection<User> getUsers() {
        return users;
    }

    public void setUsers(Collection<User> users) {
        this.users = users;
    }
}
