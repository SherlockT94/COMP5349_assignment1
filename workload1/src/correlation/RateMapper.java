package correlation;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;//keyin, valuein, keyout, valueout
import org.apache.hadoop.mapreduce.Mapper.Context;
/**
 * 
 * This does similar thing to TagMapper, except that
 * we make the map output value format exactly the same as
 * reduce output value format. So that we can use the reducer as combiner
 *   
 * input record format
 * 2048252769	48889082718@N01	dog francis lab	2007-11-19 17:49:49	RRBihiubApl0OjTtWA	16
 * 
 * output key value pairs for the above input
 * dog -> 48889082718@N01=1,
 * francis -> 48889082718@N01=1,
 * 
 * 
 * @see RateReducer
 * @see RateDriver
 * @author Ying Zhou
 *
 */
public class RateMapper extends Mapper<Object, Text, Text, Text> {
	private Text categoryKey = new Text();
	private Text value = new Text();

	public void map(Object key, Text value, Context context
	) throws IOException, InterruptedException {
		String[] dataArray = value.toString().split(","); //split the data into array
		String category = dataArray[3];
		String vid = dataArray[0];
		String country = dataArray[11];
		String vc = vid + "," + country;

		categoryKey.set(category);
		value.set(vc);
		context.write(categoryKey, value);
	}
}
