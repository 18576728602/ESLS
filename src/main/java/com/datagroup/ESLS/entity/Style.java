package com.datagroup.ESLS.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringExclude;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
@Entity
@Table(name = "styles", schema = "tags", catalog = "")
@Data
public class Style implements Serializable {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)//自增主键
    private long id;
    @Column(name = "styleNumber")
    private String styleNumber;
    @Column(name = "styleType")
    private String styleType;
    @Column(name = "name")
    private String name;
    @Column(name = "width")
    private Integer width;
    @Column(name = "height")
    private Integer height;
    @Column(name = "refreshState")
    private Integer refreshState;
    @Column(name = "refreshTime")
    private Integer refreshTime;
    @Column(name = "refreshBegin")
    private Timestamp refreshBegin;
    @OneToMany(mappedBy = "style")
    @JsonIgnore
    @ToStringExclude
    private Collection<Dispms> dispmses;
    @OneToMany(mappedBy = "style")
    @JsonIgnore
    @ToStringExclude
    private Collection<Tag> tags;
}