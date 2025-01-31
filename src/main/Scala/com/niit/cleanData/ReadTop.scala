package com.niit.cleanData

import org.apache.spark.sql.{SaveMode, SparkSession}

class ReadTop {
  def run(): Unit ={
    // 创建SparkSession
    val spark = SparkSession.builder()
      .appName("MyScalaSparkApp")
      .master("local[*]") // 替换为你的Spark集群地址
      .getOrCreate()

    // 读取MySQL数据
    val jdbcDF = spark.read.format("jdbc")
      .option("url", "jdbc:mysql://192.168.56.104:3306/novel?useSSL=false")
      .option("dbtable", "CleanData")
      .option("user", "root")
      .option("password", "root")
      .load()

    // 假设你进行了某些数据处理...
    //  val processedDF = jdbcDF.limit(10) // 示例代码，你需要替换为实际的处理逻辑
    jdbcDF.createOrReplaceTempView("jobinfo")

    val processedDF= spark.sql("select id,novel_name,novel_read,novel_recommend from jobinfo order by novel_read desc limit 10")
    // 将处理后的数据写回到MySQL
    processedDF.write.format("jdbc")
      .option("url", "jdbc:mysql://192.168.56.104:3306/novel?useSSL=false")
      .option("dbtable", "readtop")
      .option("user", "root")
      .option("password", "root")
      .mode(SaveMode.Overwrite) // 根据需要选择保存模式
      .save()

    // 关闭SparkSession
    spark.stop()
  }
}
