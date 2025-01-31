package com.niit.novel_message2

import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}

import java.util.Properties

//统计每个作家的作品及作品数
object WriterNumTop {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("WriterNumTop")
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

    //获取小说名称和小说作者
    val result = dataRdd4.map(x=>{
      val novel_name = x.getString(1)
      val novel_author = x.getString(2).split(" ")(0)
      (novel_author,novel_name)
    })
//    result.collect() foreach println
    val writer =result.groupByKey()
      .flatMap{ case (key, values) => values.map(value => (key, value)) }
      .reduceByKey((x,y)=>(x+"|"+y))
//    writer.collect() foreach println

    val result1 = result.map(x=>(x._1,x._2))
    val writerNum = result1.groupByKey()
      .mapValues(x=>x.size)
//    writerNum.collect() foreach println

    val result2 = writer.join(writerNum)
      .map(f=>Row(f._1,f._2._1,f._2._2))
//    result2.collect() foreach println
    //创建字段
    val header1 = Array(StructField("novel_author",StringType,true),
      StructField("novel_name",StringType,true),
      StructField("novel_num",IntegerType,true))
    //把字段转换成Schema（结构）
    val schema = StructType(header1)
    //把结构和数据绑定起来
    val writerNumTop = spark.createDataFrame(result2,schema)
    //把DF的数据存放到内存中
    writerNumTop.createOrReplaceTempView("WriterNumTop")
    //查询小说作者及其作品并按照其作品数进行排序
    val numTop = spark.sql("select * from writerNumTop order by novel_num desc limit 10")
//    numTop.show()
    val prop = new Properties()
    prop.put("user", "root")
    prop.put("password", "root")
    prop.put("driver", "com.mysql.jdbc.Driver")

    numTop.write.mode("append")
      .jdbc("jdbc:mysql://192.168.56.104:3306/novel?useUnicode=true&" +
        "characterEncoding=utf-8&useSSL=false&serverTimezone = GMT", "novel.WriterNumTop", prop)
    numTop.show()
    sc.stop()
  }

}
