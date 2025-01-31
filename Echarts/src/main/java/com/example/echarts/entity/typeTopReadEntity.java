package com.example.echarts.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Lob;
import java.io.Serializable;

@Data
@TableName("typeTopRead")
public class typeTopReadEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId

    private long id;

    @Lob
    @Column(name = "novel_name", columnDefinition = "TEXT") // 指定列名和列类型
    private String novelName;

    private Integer novelRead;
    @Lob
    @Column(name = "novel_type", columnDefinition = "TEXT")
    private String novelType;

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

    public Integer getNovelRead() {
        return novelRead;
    }

    public void setNovelRead(Integer novelRead) {
        this.novelRead = novelRead;
    }

    public String getNovelType() {
        return novelType;
    }

    public void setNovelType(String novelType) {
        this.novelType = novelType;
    }
}

