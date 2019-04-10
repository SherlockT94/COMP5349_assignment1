#Running the Jar file
spark-submit  \
--class ml.TrendingVideoAnalysis \
--master yarn \
--deploy-mode cluster \
sparkML.jar \
InputPath \
OutputPath
