# Running the Jar file
spark-submit  \
--class ml.TrendingVideoAnalysis \
--master yarn \
--deploy-mode cluster \
--num-executors 3 \
sparkML.jar \
InputPath \
OutputPath
