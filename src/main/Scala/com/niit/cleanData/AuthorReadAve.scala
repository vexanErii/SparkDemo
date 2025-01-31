package com.niit.cleanData

import org.apache.spark.sql.{SaveMode, SparkSession}

class AuthorReadAve {
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

    val processedDF = spark.sql("""
      WITH RankedAuthors AS (
        SELECT
          novel_author,
          AVG(novel_read) AS avg_reads,
          collect_list(DISTINCT novel_name) AS all_novels,
          ROW_NUMBER() OVER (ORDER BY AVG(novel_read) DESC) AS rn
        FROM jobinfo
        GROUP BY novel_author
        ORDER BY avg_reads DESC
        LIMIT 10
      )
      SELECT
      rn AS id,
        novel_author,
        avg_reads,
        array_join(all_novels, '\t') AS all_novels_str
      FROM RankedAuthors
    """)

    // 将处理后的数据写回到MySQL
    processedDF.write.format("jdbc")
      .option("url", "jdbc:mysql://192.168.56.104:3306/novel?useSSL=false")
      .option("dbtable", "authorReadAve")
      .option("user", "root")
      .option("password", "root")
      .mode(SaveMode.Overwrite) // 根据需要选择保存模式
      .save()

    // 关闭SparkSession
    spark.stop()
  }
}
