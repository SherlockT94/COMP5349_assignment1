package correlation;

import java.io.IOException;
import java.util.ArrayList;
import java.text.DecimalFormat;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Reducer.Context;

/**
 * The Reducer for count the average rating.
 *
 * @see RateMapper
 * @see RateDriver
 * 
 * @author Haotong Tang
 *
 */
public class RateReducer extends Reducer<Text, Text, Text, Text> {
	Text result = new Text();//The result of counting
	
	public void reduce(Text key, Iterable<Text> values, 
			Context context
	) throws IOException, InterruptedException {

		ArrayList<String> term = new ArrayList<>();
		ArrayList<String> vid = new ArrayList<>();
		double rate;

		for (Text text: values){
			String dataString = text.toString();//every text is one item in the values
			String[] dataArray = dataString.split(",");//dataArray[0]=vid, [1]=country

			if(!term.contains(dataString)){
				term.add(dataString);
				if(!vid.contains(dataArray[0])){
					vid.add(dataArray[0]);	
				}
			}
		}

		rate = term.size()/vid.size();
		DecimalFormat df = new DecimalFormat("#.##");
		result.set(df.format(rate).toString());
		context.write(key, result);
	}
}
