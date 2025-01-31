package com.niit.novel_message1

import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}
import org.apache.spark.sql.{Row, SparkSession}

import java.util.Properties

object novelTypeClick {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("novelTypeClick")
      .master("local[*]")
      .getOrCreate()
    val sc = spark.sparkContext
//    val message = sc.textFile("file:user/local/database/novel_message1.txt")
    //读取数据
    val message = spark.read.format("jdbc")
      //指定数据库
      .option("url", "jdbc:mysql://192.168.56.104:3306/novel?useUnicode=true&" +
        "characterEncoding=utf-8&useSSL=false&serverTimezone = GMT")
      .option("driver", "com.mysql.jdbc.Driver")
      //指定数据库中的表
      .option("dbtable", "novel_message1")
      .option("user", "root")
      .option("password", "root")
      .load()
    val dataRdd = message.rdd

    val typeNumber = dataRdd.map(row=>{
      val novel_type = row.getString(3).split("： ")(1)
      (novel_type, 1)
    })
    val groupData = typeNumber.reduceByKey(_ + _)
//      groupData.collect() foreach println
    val novelClick = dataRdd.map(x=>{
      val novel_type1 = x.getString(3).split("： ")(1)
      val novel_click = x.getString(5).split("：")(1).toDouble
      (novel_type1,novel_click)
    })
//    novelClick.collect() foreach println
    val keyNumber = novelClick.reduceByKey(_ + _)
//    keyNumber.collect() foreach println
    val result = groupData.join(keyNumber)
//    val result = groupData.map { case (key, value) =>
//      (key, value.toInt / keyNumber(key))
//    }
//      .map(x=>Row(x._1,x._2))
      .map(f=>Row(f._1,f._2._1,f._2._2.toInt/f._2._1))
//    result.collect() foreach println
    //创建字段
    val header = Array(StructField("novel_type", StringType, true),
      StructField("type_num",IntegerType,true),
      StructField("avg_click",IntegerType,true))
    //把字段转换成Schema（结构）
    val schema = StructType(header)
    //把结构和数据绑定起来
    val novelTypeClick = spark.createDataFrame(result, schema)
    //把DF的数据存放到内存中
    novelTypeClick .createOrReplaceTempView("NovelTypeClick ")
    //
    val typeAvgClick = spark.sql("select * from NovelTypeClick order by avg_click desc")
//    typeAvgClick.show()
    val prop = new Properties()
    prop.put("user", "root")
    prop.put("password", "root")
    prop.put("driver", "com.mysql.jdbc.Driver")

    typeAvgClick.write.mode("append")
      .jdbc("jdbc:mysql://192.168.56.104:3306/novel?useUnicode=true&" +
        "characterEncoding=utf-8&useSSL=false&serverTimezone = GMT", "novel.NovelTypeClick", prop)
    typeAvgClick.show()
    sc.stop()
  }

}
