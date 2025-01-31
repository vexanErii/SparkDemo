package com.example.echarts.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Lob;
import java.io.Serializable;

@Data
@TableName("authorReadAve")
public class authorReadAveEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId

    private long id;

    @Lob
    @Column(name = "novel_author", columnDefinition = "TEXT") // 指定列名和列类型
    private String novelAuthor;

    private Double avgReads;
    @Lob
    @Column(name = "all_novels_str", columnDefinition = "TEXT") // 指定列名和列类型
    private String allNovelsStr;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNovelAuthor() {
        return novelAuthor;
    }

    public void setNovelAuthor(String novelAuthor) {
        this.novelAuthor = novelAuthor;
    }

    public Double getAvgReads() {
        return avgReads;
    }

    public void setAvgReads(Double avgReads) {
        this.avgReads = avgReads;
    }

    public String getAllNovelsStr() {
        return allNovelsStr;
    }

    public void setAllNovelsStr(String allNovelsStr) {
        this.allNovelsStr = allNovelsStr;
    }
}

