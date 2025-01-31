package com.niit.novel_message2

import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}
import org.apache.spark.sql.{Row, SparkSession}

import java.util.Properties

//在小说字数大于500万的小说中，周点击量的top10
object CountClickRed {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("CountClickRed")
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
    val dataRdd3 = message.rdd
    //获取小说的名字以及小说的字数
    val novelCount = dataRdd3.map(x=>{
      val novel_name = x.getString(1)
      val novl_count = x.getString(4).split(" ")(0)
      (novel_name,novl_count)
    })

    //对小说字数进行处理，如果含有“万”字则乘以10000，没有则不做处理
    val processedRDD = novelCount.map { case (key, value) =>
      val newValue = if (value.contains("万")) {
        (value.replace("万", "").toDouble * 10000).toInt.toString
      } else {
        value
      }
      (key, newValue)
    }
    //由("永不解封的档案",4997500)类型的键值对转化成("永不解封的档案",("永不解封的档案",4997500))
    val newProcess = processedRDD.keyBy(x=>x._1)
//    newProcess.collect() foreach println

    //获取小说的名称，小说的周点击量，以及小说的推荐票
    val clickRed = dataRdd3.map(y=>{
      val novel_name1 = y.getString(1)
      val novel_click = y.getString(5).split(" ")(0)
      val novel_recommend = y.getString(7).split(" ")(0)
      (novel_name1,novel_click,novel_recommend)
    })
    //对小说的点击量以及小说的推荐票含有“万”字的进行处理
    val newData = clickRed.map { case (a, b, c) =>
      var newB = b
      var newC = c
      if (newB.contains("万")) {
        newB = (newB.replace("万", "").toDouble * 10000).toInt.toString
      }
      if (newC.contains("万")) {
        newC = (newC.replace("万", "").toDouble * 10000).toInt.toString
      }
      (a, newB.toInt, newC.toInt)

    }
    val rdd1 = newData.keyBy(x=>x._1)
    //按照小说的名字将newProcess数据和rdd1数据进行聚合
    val result = newProcess.join(rdd1)
      .map(f=>Row(f._1,f._2._1._2.toInt,f._2._2._2,f._2._2._3))
//    result.collect() foreach println

//    val TwiceClickRed = result.map(x => (new TwiceSortKey(x._2._2._2, x._2._2._3), x))
//      .sortBy(x=>((x._2._2._2._2,x._2._2._2._3),true))
//      .map(f=>Row(f._2._1,f._2._2._1._2.toInt,f._2._2._2._2,f._2._2._2._3))
    //创建字段
    val header = Array(StructField("novel_name", StringType, true),
      StructField("novel_count",IntegerType,true),
      StructField("novel_click",IntegerType,true),
      StructField("novel_recommend",IntegerType,true))
    //把字段转换成Schema（结构）
    val schema = StructType(header)
    //把结构和数据绑定起来
    val countClickRed = spark.createDataFrame(result, schema)
    //去重，对含有相同小说名的数据进行去重
    val distinctDF = countClickRed.dropDuplicates("novel_name")
    //把DF的数据存放到内存中
    distinctDF.createOrReplaceTempView("CountClickRed")
    //查询小说字数大于500万字的小说，并按照其周点击量进行排序
    val countClick = spark.sql("select * from CountClickRed where novel_count>5000000" +
      " order by novel_click desc limit 10")
//    countClick.show()
    val prop = new Properties()
    prop.put("user", "root")
    prop.put("password", "root")
    prop.put("driver", "com.mysql.jdbc.Driver")

    countClick.write.mode("append")
      .jdbc("jdbc:mysql://192.168.56.104:3306/novel?useUnicode=true&" +
        "characterEncoding=utf-8&useSSL=false&serverTimezone = GMT", "novel.CountClickRed", prop)
    countClick.show()
    sc.stop()
  }

}
