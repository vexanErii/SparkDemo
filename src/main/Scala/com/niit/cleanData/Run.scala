package com.niit.cleanData

object Run {
  def main(args: Array[String]): Unit = {

    val cleanData = new CleanData
    cleanData.run()

    //阅读数最高20、推荐20
    val readTop = new ReadTop
    val recommendTop = new RecommendTop
    recommendTop.run()
    readTop.run()
    //各小说类型最高阅读量,推荐
    val typeTopRead = new TypeTopRead
    val typeTopRecommend = new TypeTopRecommend
    typeTopRead.run()
    typeTopRecommend.run()

    //各小说类型占比
    val typePro = new TypePro
    typePro.run()
    //各作者小说总阅读量
    val authorRead = new AuthorRead
    authorRead.run()
    //各作者小说平均阅读量
    val authorReadAve = new AuthorReadAve
    authorReadAve.run()
    //
    //
  }
}
