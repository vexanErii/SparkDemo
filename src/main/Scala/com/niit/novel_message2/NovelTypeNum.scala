package com.niit.novel_message2

import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}

import java.util.Properties

//统计不同种类型小说的数量
object NovelTypeNum {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("NovelTypeNum")
      .master("local[*]")
      .getOrCreate()
    val sc = spark.sparkContext
//    val message = sc.textFile("database/novel_message2.txt")
    val message = spark.read.format("jdbc")
      //指定数据库
      .option("url", "jdbc:mysql://192.168.56.104:3306/novel?useUnicode=true&" +
         "characterEncoding=utf-8&useSSL=false&serverTimezone = GMT")
      .option("driver", "com.mysql.jdbc.Driver")
      //指定数据库中的表
      .option("dbtable", "novel_message2")
      .option("user", "root")
      .option("password", "root")
      .load()
    val dataRdd4 = message.rdd
    //获取小说类型的数据
    val typeData = dataRdd4.map(x=>{
      val novel_type = x.getString(3)
      (novel_type,1)
    })
//    result.collect() foreach println

    val novelType = typeData.flatMap(x=>x._1.stripPrefix("[").stripSuffix("]").split(","))
      .map(_.trim)
//    转换成k-v键值对的形式
      .map((_, 1))
    //对相同的key进行聚合，并返回相同key的数量
      .reduceByKey(_ + _)
    val result = novelType.map(x=>Row(x._1,x._2))
//    result.collect() foreach println

    //创建字段
    val typeHeader= Array(StructField("novel_type", StringType, true),
      StructField("type_num",IntegerType,true))
    //把字段转换成Schema（结构）
    val schema = StructType(typeHeader)
    //把结构和数据绑定起来
    val novelTypeNum = spark.createDataFrame(result, schema)
    //把DF的数据存放到内存中
    novelTypeNum.createOrReplaceTempView("NovelTypeNum")
    //查询不同小说类型的数量，并将其排序
    val typeNum = spark.sql("select * from NovelTypeNum order by type_num desc limit 10")
//    typeNum.show()
    val prop = new Properties()
    prop.put("user", "root")
    prop.put("password", "root")
    prop.put("driver", "com.mysql.jdbc.Driver")

    typeNum.write.mode("append")
      .jdbc("jdbc:mysql://192.168.56.104:3306/novel?useUnicode=true&" +
        "characterEncoding=utf-8&useSSL=false&serverTimezone = GMT", "novel.NovelTypeNum", prop)
    typeNum.show()
    sc.stop()
  }

}
