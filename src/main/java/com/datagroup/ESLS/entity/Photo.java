package com.datagroup.ESLS.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
@Entity
@Table(name = "tb_photos", schema = "tags", catalog = "")
public class Photo implements Serializable {
    private int id;
    private String name;
    private int width;
    private int height;
    private byte[] photo;
    private byte[] redphoto;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)//自增主键
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 25)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "width", nullable = false)
    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    @Basic
    @Column(name = "height", nullable = false)
    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Basic
    @Column(name = "photo", nullable = false)
    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    @Basic
    @Column(name = "redphoto", nullable = false)
    public byte[] getRedphoto() {
        return redphoto;
    }

    public void setRedphoto(byte[] redphoto) {
        this.redphoto = redphoto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Photo photo = (Photo) o;
        return id == photo.id &&
                width == photo.width &&
                height == photo.height &&
                Objects.equals(name, photo.name) &&
                Arrays.equals(this.photo, photo.photo) &&
                Arrays.equals(redphoto, photo.redphoto);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, name, width, height);
        result = 31 * result + Arrays.hashCode(photo);
        result = 31 * result + Arrays.hashCode(redphoto);
        return result;
    }
}
