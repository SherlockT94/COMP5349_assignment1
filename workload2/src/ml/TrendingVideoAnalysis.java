package ml;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import org.apache.spark.api.java.*;//Spark Java programming API
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.function.Function;//Functional Interface, call(), for mapping RDDs of othertypes

import scala.Tuple2;//Tuple including different types of data, which could not be changed

//import scala.*;
//import scala.Double;

/**
 *submit to a yarn cluster
 *
 *spark-submit  \
  --class ml.MovieLensLarge \
  --master yarn-cluster \
  sparkML.jar \
  /share/movie/small/ \
  week5_out/
 * 
 * 
 * @author HaotongTang
 *
 */
public class TrendingVideoAnalysis{

	public static void main(String[] args) {

	    String inputDataPath = args[0], outputDataPath = args[1];
	    SparkConf conf = new SparkConf();
	
	    conf.setAppName("Controversial Trending Videos");//set App name
	   
	    JavaSparkContext sc = new JavaSparkContext(conf);//SparkContext
	    
	    JavaRDD<String> rawData = sc.textFile(inputDataPath).filter(s -> !s.contains("video_id"));//read file and filter the header

	    //read ratings.csv and convert it to a key value pair RDD of the following format
		//video_id, trending_date, likes, dislikes, country
	    JavaPairRDD<String, String> dataExtraction = rawData.mapToPair(s -> 
	    	{   String[] values = s.split(",");
				String key = values[0] + "," + values[11];
				String value = values[1] + "," + values[3] + "," + values[6] + "," + values[7];
				return new Tuple2<String, String>(key, value);
	    	}
	    );

	    //map to <String, TrendingVideo>
	    JavaPairRDD<String, TrendingVideo> mapToTrends = dataExtraction.mapToPair(t -> 
	    	{	
				String vidCountry = t._1;
				String[] prop = t._2.split(",");
				return new Tuple2<String, TrendingVideo>(vidCountry, new TrendingVideo(prop[0], prop[1], Integer.parseInt(prop[2]), Integer.parseInt(prop[3])));
	    	}
	    );
		
		//group the same key together
	    JavaPairRDD<String, Iterable<TrendingVideo>> groupVideos = mapToTrends.groupByKey(1);		

	    //sort by date
	    JavaPairRDD<String,ArrayList<TrendingVideo>> sortedVideos= groupVideos.mapToPair(t->{
			ArrayList<TrendingVideo> vList = new ArrayList<TrendingVideo>();
			for(TrendingVideo tv: t._2) {
				vList.add(tv);
			}
			Collections.sort(vList);

			ArrayList<TrendingVideo> results = new ArrayList<TrendingVideo>();
			//get first and second
			if(vList.size() == 1){
				results.add(vList.get(0));
			}else {
				for(int i=0; i < 2; i++) {
					results.add(vList.get(i));
				}
			}
			
			return new Tuple2<String, ArrayList<TrendingVideo>>(t._1, results);
	    });
	    //reduce
	    JavaPairRDD<String, Tuple2<Integer, String>> deltaVideo = sortedVideos.mapToPair(t -> {	
				int num = t._2.size();
				int likeDelta;
				int dislikeDelta;
				int difference;
				if(num == 1) {
					difference = 0;
				}else {
					likeDelta = t._2.get(1).getLike() - t._2.get(0).getLike();
					dislikeDelta = t._2.get(1).getDislike() - t._2.get(0).getDislike();
					difference = dislikeDelta - likeDelta;
				}
				return new Tuple2<String, Tuple2<Integer, String>>(t._1, new Tuple2<Integer, String>(difference, t._2.get(0).getCategory()));
	    	}
	    );
	    //final output
	    JavaRDD<String> finalResults = deltaVideo.map(t -> {	
				String result = t._1 + "," + Integer.toString(t._2._1) + "," + t._2._2;
				return result;
	    	}
	    ).sortBy(s -> {
			String[] parts = s.split(",");
			return Integer.parseInt(parts[2]);
		}, false, 1).map(s -> {
			String[] parts = s.split(",");
			return parts[0] + "," + parts[2] + "," + parts[3] + "," + parts[1];
		});
	    
		List<String> top10 = finalResults.take(10);
	    JavaRDD<String> finalTop10 = sc.parallelize(top10);
			
	    finalTop10.coalesce(1).saveAsTextFile(outputDataPath);//save the output
	    sc.close();//close the spark context
	  }
}

