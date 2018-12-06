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
@Table(name = "tb_dispmmanager", schema = "tags", catalog = "")
public class Dispmmanager implements Serializable {
    private long id;
    private String name;
    private String source;
    private String sourceColumn;
    private String columnType;

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
    @Column(name = "name", nullable = true, length = 255)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "source", nullable = true, length = 255)
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Basic
    @Column(name = "sourceColumn", nullable = true, length = 255)
    public String getSourceColumn() {
        return sourceColumn;
    }

    public void setSourceColumn(String sourceColumn) {
        this.sourceColumn = sourceColumn;
    }

    @Basic
    @Column(name = "columnType", nullable = true, length = 255)
    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dispmmanager that = (Dispmmanager) o;
        return id == that.id &&
                Objects.equals(name, that.name) &&
                Objects.equals(source, that.source) &&
                Objects.equals(sourceColumn, that.sourceColumn) &&
                Objects.equals(columnType, that.columnType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, source, sourceColumn, columnType);
    }
}
