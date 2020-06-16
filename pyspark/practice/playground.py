from pyspark.sql import SparkSession
from pyspark.sql.functions import *


if __name__ == "__main__":
    spark = SparkSession.builder.getOrCreate()
    df = spark.range(100)
    df.printSchema()
    newDF = df.withColumn("smoopies", lit(5))

    def f(colName):
        newName = colName + "AHH"
        return newName

    for item in newDF.columns:
        print(item)

    maptest = list(map(f, newDF.columns))

    for item in maptest:
        print(item)

