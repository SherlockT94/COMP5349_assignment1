package ml;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.text.ParseException;
/**
* Supporting class for TrendingVideoAnalysis
* @author haotongTang
*/
public class TrendingVideo implements Comparable, Serializable {
	String date;
	String Category;
	int like;
	int dislike;

	public TrendingVideo(String date, String category, int like, int dislike) {
		this.date = date;
		Category = category;
		this.like = like;
		this.dislike = dislike;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getCategory() {
		return Category;
	}

	public void setCategory(String category) {
		Category = category;
	}

	public int getLike() {
		return like;
	}

	public void setLike(int like) {
		this.like = like;
	}

	public int getDislike() {
		return dislike;
	}

	public void setDislike(int dislike) {
		this.dislike = dislike;
	}

	@java.lang.Override
	public java.lang.String toString() {
		return "TrendingVideo{" +
				"date='" + date + '\'' +
				", Category='" + Category + '\'' +
				", like=" + like +
				", dislike=" + dislike +
				'}';
	}


	public int compareTo(Object o2) { // decending order
         // TODO Auto-generated method stub
		 String pattern = "yyyy.dd.MM";
		 SimpleDateFormat sd = new SimpleDateFormat(pattern);
         TrendingVideo tv = (TrendingVideo) o2;
		 try{
			if (sd.parse(date).before(sd.parse(tv.date)))
				    return -1;
			if (sd.parse(date).after(sd.parse(tv.date)))
					return 1;
		 } catch(ParseException e){
			e.printStackTrace();
		 }
         return 0;
 	}

}
