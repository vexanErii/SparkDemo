package com.example.echarts.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Lob;
import java.io.Serializable;

@Data
@TableName("CountClickRed")
public class ClickRedEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId

    private long id;

    @Lob
    @Column(name = "novel_name", columnDefinition = "TEXT") // 指定列名和列类型
    private String novelName;

    private Integer novelCount;

    private Integer novelClick;

    private Integer novelRecommend;

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

    public Integer getNovelClick() {
        return novelClick;
    }

    public void setNovelClick(Integer novelClick) {
        this.novelClick = novelClick;
    }

    public Integer getNovelRecommend() {
        return novelRecommend;
    }

    public void setNovelRecommend(Integer novelRecommend) {
        this.novelRecommend = novelRecommend;
    }


}

