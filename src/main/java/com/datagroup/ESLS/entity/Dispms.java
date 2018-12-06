package com.datagroup.ESLS.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
@Entity
@ToString
@Table(name = "dispms", schema = "tags", catalog = "")
public class Dispms implements Serializable {
    private long id;
    private String name;
    private Boolean status;
    private Integer x;
    private Integer y;
    private Integer width;
    private Integer height;
    private String source;
    private String sourceColumn;
    private String columnType;
    private Integer backgroundColor;
    private String text;
    private String startText;
    private String endText;
    private String fontBold;
    private String fontFamily;
    private Integer fontSize;
    private Integer fontColor;
    private Integer textX;
    private Integer textY;
    private Integer lineBoarderWidth;
    private String textAlign;
    private Style style;

    public Dispms() {
    }

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
    @Column(name = "status", nullable = true)
    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @Basic
    @Column(name = "x", nullable = true)
    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    @Basic
    @Column(name = "y", nullable = true)
    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    @Basic
    @Column(name = "width", nullable = true)
    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    @Basic
    @Column(name = "height", nullable = true)
    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
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

    @Basic
    @Column(name = "backgroundColor", nullable = true)
    public Integer getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Integer backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    @Basic
    @Column(name = "text", nullable = true, length = 255)
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Basic
    @Column(name = "startText", nullable = true, length = 255)
    public String getStartText() {
        return startText;
    }

    public void setStartText(String startText) {
        this.startText = startText;
    }

    @Basic
    @Column(name = "endText", nullable = true, length = 255)
    public String getEndText() {
        return endText;
    }

    public void setEndText(String endText) {
        this.endText = endText;
    }

    @Basic
    @Column(name = "fontBold", nullable = true, length = 255)
    public String getFontBold() {
        return fontBold;
    }

    public void setFontBold(String fontBold) {
        this.fontBold = fontBold;
    }

    @Basic
    @Column(name = "fontFamily", nullable = true, length = 255)
    public String getFontFamily() {
        return fontFamily;
    }

    public void setFontFamily(String fontFamily) {
        this.fontFamily = fontFamily;
    }

    @Basic
    @Column(name = "fontSize", nullable = true)
    public Integer getFontSize() {
        return fontSize;
    }

    public void setFontSize(Integer fontSize) {
        this.fontSize = fontSize;
    }

    @Basic
    @Column(name = "fontColor", nullable = true)
    public Integer getFontColor() {
        return fontColor;
    }

    public void setFontColor(Integer fontColor) {
        this.fontColor = fontColor;
    }

    @Basic
    @Column(name = "textX", nullable = true)
    public Integer getTextX() {
        return textX;
    }

    public void setTextX(Integer textX) {
        this.textX = textX;
    }

    @Basic
    @Column(name = "textY", nullable = true)
    public Integer getTextY() {
        return textY;
    }

    public void setTextY(Integer textY) {
        this.textY = textY;
    }

    @Basic
    @Column(name = "lineBoarderWidth", nullable = true)
    public Integer getLineBoarderWidth() {
        return lineBoarderWidth;
    }

    public void setLineBoarderWidth(Integer lineBoarderWidth) {
        this.lineBoarderWidth = lineBoarderWidth;
    }

    @Basic
    @Column(name = "text_align", nullable = true, length = 255)
    public String getTextAlign() {
        return textAlign;
    }

    public void setTextAlign(String textAlign) {
        this.textAlign = textAlign;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dispms dispms = (Dispms) o;
        return id == dispms.id &&
                Objects.equals(name, dispms.name) &&
                Objects.equals(status, dispms.status) &&
                Objects.equals(x, dispms.x) &&
                Objects.equals(y, dispms.y) &&
                Objects.equals(width, dispms.width) &&
                Objects.equals(height, dispms.height) &&
                Objects.equals(source, dispms.source) &&
                Objects.equals(sourceColumn, dispms.sourceColumn) &&
                Objects.equals(columnType, dispms.columnType) &&
                Objects.equals(backgroundColor, dispms.backgroundColor) &&
                Objects.equals(text, dispms.text) &&
                Objects.equals(startText, dispms.startText) &&
                Objects.equals(endText, dispms.endText) &&
                Objects.equals(fontBold, dispms.fontBold) &&
                Objects.equals(fontFamily, dispms.fontFamily) &&
                Objects.equals(fontSize, dispms.fontSize) &&
                Objects.equals(fontColor, dispms.fontColor) &&
                Objects.equals(textX, dispms.textX) &&
                Objects.equals(textY, dispms.textY) &&
                Objects.equals(lineBoarderWidth, dispms.lineBoarderWidth) &&
                Objects.equals(textAlign, dispms.textAlign);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, status, x, y, width, height, source, sourceColumn, columnType, backgroundColor, text, startText, endText, fontBold, fontFamily, fontSize, fontColor, textX, textY, lineBoarderWidth, textAlign);
    }
    @ManyToOne
    @JoinColumn(name = "styleid", referencedColumnName = "id")
    @JsonIgnore
    public Style getStyle() {
        return style;
    }

    public void setStyle(Style style) {
        this.style = style;
    }
}
