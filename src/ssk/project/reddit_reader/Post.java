package ssk.project.reddit_reader;

public class Post {

	String subreddit, title, author, permalink, url, domain, id;
	int points, numComments;
	
	String getTitle() {
		return title;
	}
	
	String getScore() {
		return "" + points;
	}
	
	String getDetails() {
		String details = author + " posted this and got " + numComments + " replies";
		return details;
	}
}
