package com.cloudera.spark.practice

import org.apache.spark.sql.{SQLContext, SparkSession}
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.SparkContext._

object Wordcount {

  def main(args: Array[String]): Unit = {
    println("Spark WordCount Sample Started ...")
    val spark = SparkSession.builder()
      .master(master = "local[*]")
      .appName("Word Count Demo")
      .getOrCreate()

    val sc = spark.sparkContext
    //val sc = new SparkContext("spark://127.0.0.1:7077", "Word Count Demo")

    // creating an linesRDD by loading text file (hadoop.txt) from HDFS through Spark context

    val linesRDD = sc.textFile("hdfs://quickstart.cloudera:8020/user/root/sparkPractice/wordcount")



    val wordCounts = linesRDD.flatMap{ line =>
      val splStr = line.split(" ")
    splStr.map(word => (word, 1))
    }


    val wordCount2 = wordCounts.reduceByKey(_+_)

    println("Total words: " + wordCounts.count())

    println("All the words and occurrence count for each word: ")
    val wordCountsScala = wordCounts.collect()
    wordCountsScala.foreach(item => println("Word: " + item._1 + " --> " + "Occurrence count: " + item._2))

    println("Spark WordCount Sample Completed.")
  }

}
