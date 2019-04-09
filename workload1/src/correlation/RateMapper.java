package correlation;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;//keyin, valuein, keyout, valueout
import org.apache.hadoop.mapreduce.Mapper.Context;
/**
 * @see RateReducer
 * @see RateDriver
 * @author Haotong Tang 
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
