package com.niit.novel_message1

import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}
import org.apache.spark.sql.{Row, SparkSession}

import java.util.Properties

//查询小说最后更新月份中小说的数量
object novelUpdated {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("novelUpdated")
      .master("local[*]")
      .getOrCreate()
    val sc = spark.sparkContext
//    val message = sc.textFile("database/novel_message1.txt")
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
    val dataRdd1 = message.rdd

    //获取小说的最后更新时间
    val novelTime = dataRdd1.map(x=>{
      val time = x.getString(10).split(" ")(0)
      (time,1)
    })
//    novelTime.collect() foreach println
    //得到小说的最后更新时间的年份和月份，并根据其相同的年份和月份进行聚合
    val result = novelTime.map { case (date, count) =>
      (date.substring(0, 7), count) }
      .reduceByKey(_ + _)
//      .sortByKey()
//    result.collect().foreach(println)
      .map(x=>Row(x._1,x._2))

    //创建字段
    val header = Array(StructField("novel_time", StringType, true),
      StructField("novel_num",IntegerType,true))
    //把字段转换成Schema（结构）
    val schema = StructType(header)
    //把结构和数据绑定起来
    val novelUpdated = spark.createDataFrame(result, schema)
    //把DF的数据存放到内存中
    novelUpdated.createOrReplaceTempView("NovelUpdated")
    //查询小说最后更新月份的小说数量
    val updated = spark.sql("select * from NovelUpdated order by novel_num desc limit 10")
//    updated.show()
    val prop = new Properties()
    prop.put("user", "root")
    prop.put("password", "root")
    prop.put("driver", "com.mysql.jdbc.Driver")

    updated.write.mode("append")
      .jdbc("jdbc:mysql://192.168.56.104:3306/novel?useUnicode=true&" +
        "characterEncoding=utf-8&useSSL=false&serverTimezone = GMT", "novel.NovelUpdated", prop)
    updated.show()
    sc.stop()
  }
}
