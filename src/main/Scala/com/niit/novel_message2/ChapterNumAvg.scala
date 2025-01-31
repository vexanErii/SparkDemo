package com.niit.novel_message2

import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}

import java.util.Properties

//在章节数大于500章的小说中，每个章节平均字数TOP10
object ChapterNumAvg {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("ChapterNumAvg")
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
    val dataRdd2 = message.rdd
    //获取小说名称和小说的总字数
    val number = dataRdd2.map(x=>{
      val novel_name = x.getString(1)
      val novel_count = x.getString(4).split(" ")(0)
      (novel_name,novel_count)
    })
    //对小说中含有"万"字的数据进行处理，如果有"万"字则去除乘以10000，没有则不做处理
    val newNumber = number.map{ case (key,value)=>
      var newValue = value
      if(newValue.contains("万")){
        newValue = (newValue.replace("万", "").toDouble * 10000).toInt.toString
      }else{
        newValue
      }
      (key,newValue.toInt)
    }
      .keyBy(tup=>tup._1)
//    newNumber.collect() foreach println

    //获取小说名称和章节数
    val chapter = dataRdd2.map(y=>{
      val novel_name = y.getString(1)
      val novel_chapter = y.getString(8).split("章")(0).split("（")(1).toInt
      (novel_name,novel_chapter)
    })
      .keyBy(tup=>tup._1)
//    chapter.collect() foreach println

    //对newNumber和chapter数据进行聚合，并用小说字数除以章节数得到每个章节平均字数
    val result = newNumber.join(chapter)
      .map(f=>Row(f._1,f._2._1._2,f._2._2._2,f._2._1._2/f._2._2._2))

    //创建字段
    val header = Array(StructField("novel_name", StringType, true),
      StructField("novel_count", IntegerType, true),
      StructField("novel_chapter", IntegerType, true),
      StructField("NumAvg", IntegerType, true))
    //把字段转换成Schema（结构）
    val schema = StructType(header)
    //把结构和数据绑定起来
    val chapterNumAvg = spark.createDataFrame(result, schema)
    //去重，对含有相同小说名的数据进行去重
    val distinctNumAvg = chapterNumAvg.dropDuplicates("novel_name")
    //把DF的数据存放到内存中
    distinctNumAvg.createOrReplaceTempView("ChapterNumAvg")
    //查询小说章节数大于500，并按平均章节数排序
    val numAvg = spark.sql("select * from ChapterNumAvg where novel_chapter>500" +
      " order by NumAvg desc limit 10")
//    numAvg.show()
    val prop = new Properties()
    prop.put("user", "root")
    prop.put("password", "root")
    prop.put("driver", "com.mysql.jdbc.Driver")

    numAvg.write.mode("append")
      .jdbc("jdbc:mysql://192.168.56.104:3306/novel?useUnicode=true&" +
        "characterEncoding=utf-8&useSSL=false&serverTimezone = GMT", "novel.ChapterNumAvg", prop)
    numAvg.show()
    sc.stop()

  }

}
