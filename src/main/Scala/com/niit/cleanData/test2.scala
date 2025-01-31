package com.niit.cleanData

import org.apache.spark.sql.{SaveMode, SparkSession}



object test2 {
  def main(args: Array[String]): Unit = {
    // 创建SparkSession
    val spark = SparkSession.builder()
      .appName("MyScalaSparkApp")
      .master("local[*]") // 替换为你的Spark集群地址
      .getOrCreate()

    // 读取MySQL数据
    val jdbcDF = spark.read.format("jdbc")
      .option("url", "jdbc:mysql://192.168.56.104:3306/novel?useSSL=false")
      .option("dbtable", "jobinfo")
      .option("user", "root")
      .option("password", "root")
      .load()

    // 假设你进行了某些数据处理...


//    val processedDF= spark.sql("SELECT   \n    a.novel_author,  \n    (  \n        SELECT GROUP_CONCAT(DISTINCT n.novel_name ORDER BY n.novel_name)  \n FROM jobinfo n  \n        WHERE n.novel_author = a.novel_author  \n    ) AS all_novels,  \n    a.total_reads  \nFROM (  \n    SELECT   \n        novel_author,  \n        SUM(novel_read) AS total_reads  \n    FROM   \n       jobinfo  \n    GROUP BY   \n        novel_author  \n    ORDER BY   \n        total_reads DESC  \n    LIMIT 10  \n) a  \nORDER BY   \n    a.total_reads DESC")
    jdbcDF.createOrReplaceTempView("jobinfo")

    val processedDF = spark.sql("WITH RankedAuthors AS (    SELECT     novel_author,     AVG(novel_read) AS avg_reads,     collect_list(DISTINCT novel_name) AS all_novels,      ROW_NUMBER() OVER (ORDER BY AVG(novel_read) DESC) AS rn    FROM jobinfo    GROUP BY novel_author  )  SELECT    novel_author,    avg_reads,    array_join(all_novels, ';;') AS all_novels_str  FROM RankedAuthors  WHERE rn BETWEEN 2 AND 11")

    // 将处理后的数据写回到MySQL
    processedDF.write.format("jdbc")
      .option("url", "jdbc:mysql://192.168.56.104:3306/novel?useSSL=false")
      .option("dbtable", "TEST")
      .option("user", "root")
      .option("password", "root")
      .mode(SaveMode.Append) // 根据需要选择保存模式
      .save()

    // 关闭SparkSession
    spark.stop()
  }
}
