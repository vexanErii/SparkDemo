package com.example.echarts.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Lob;
import java.io.Serializable;
@Data
@TableName("ChapterNumAvg")
public class NumAvgEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId

    private long id;

    @Lob
    @Column(name = "novel_name", columnDefinition = "TEXT") // 指定列名和列类型
    private String novelName;

    private Integer novelCount;

    private Integer novelChapter;

    private Integer numAvg;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNovelName() {
        return novelName;
    }

    public void setNovelName(String novelName) {
        this.novelName = novelName;
    }

    public Integer getNovelCount() {
        return novelCount;
    }

    public void setNovelCount(Integer novelCount) {
        this.novelCount = novelCount;
    }

    public Integer getNovelChapter() {
        return novelChapter;
    }

    public void setNovelChapter(Integer novelChapter) {
        this.novelChapter = novelChapter;
    }

    public Integer getNumAvg() {
        return numAvg;
    }

    public void setNumAvg(Integer numAvg) {
        this.numAvg = numAvg;
    }



}

