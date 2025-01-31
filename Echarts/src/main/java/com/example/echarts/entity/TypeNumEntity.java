package com.example.echarts.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Lob;
import java.io.Serializable;

@Data
@TableName("NovelTypeNum")
public class TypeNumEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId

    private long id;

    @Lob
    @Column(name = "novel_type", columnDefinition = "TEXT") // 指定列名和列类型
    private String novelType;

    private Integer typeNum;



}
