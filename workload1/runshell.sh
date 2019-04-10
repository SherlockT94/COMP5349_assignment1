if [ $# -ne 2 ]; then
    echo "Invalid number of parameters!"
	echo "Usage: ./java_naive_usertag.sh [input_location] [output_location]"
	exit 1
fi
ant -buildfile build.xml.emr
hadoop jar correlation.jar correlation.RateDriver $1 $2
