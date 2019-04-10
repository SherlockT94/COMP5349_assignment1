if [ $# -ne 2 ]; then
    echo "Invalid number of parameters!"
    echo "Usage: ./java_naive_usertag.sh [input_location] [output_location]"
    exit 1
fi
ant -buildfile build.xml
spark-submit --class ml.TrendingVideoAnalysis --master yarn --deploy-mode cluster --num-executors 3 sparkML.jar $1 $2
