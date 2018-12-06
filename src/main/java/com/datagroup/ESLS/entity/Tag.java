package com.datagroup.ESLS.entity;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Collection;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
@Entity
@Table(name = "tags", schema = "tags", catalog = "")
@Data
public class Tag implements Serializable {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)//自增主键
    private long id;
    @Column(name = "power")
    private String power;
    @Column(name = "tagRssi")
    private String tagRssi;
    @Column(name = "apRssi")
    private String apRssi;
    @Column(name = "state")
    private Integer state;
    @Column(name = "type")
    private String type;
    @Column(name = "hardwareVersion")
    private Integer hardwareVersion;
    @Column(name = "softwareVersion")
    private Integer softwareVersion;
    @Column(name = "updateStatus")
    private Integer updateStatus;
    @Column(name = "forbidState")
    private Integer forbidState;
    @Column(name = "execTime")
    private Integer execTime;
    @Column(name = "comleteTime")
    private Timestamp comleteTime;
    @Column(name = "barCode")
    private String barCode;
    @ManyToOne
    @JoinColumn(name = "goodid", referencedColumnName = "id")
    private Good good;
    @ManyToOne
    @JoinColumn(name = "styleid", referencedColumnName = "id")
    private Style style;
    @ManyToOne
    @JoinColumn(name = "routerid", referencedColumnName = "id")
    private Router router;
    @JsonIgnore
    @OneToMany(mappedBy = "tag")
    private Collection<TagRouterRef> tagRouterRefs;
    @JsonIgnore
    @OneToMany(mappedBy = "tag")
    private Collection<TagandGood> tagAndGoods;
    public Tag() {
    }
}
