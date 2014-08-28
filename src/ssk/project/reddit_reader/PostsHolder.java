package ssk.project.reddit_reader;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;

public class PostsHolder {

	private final String URL_TEMPLATE = "http://www.reddit.com/r/SUBREDDIT_NAME"
			+ ".json"
			+ "?after=AFTER";
	
	String subreddit, url, after;
	
	PostsHolder(String str) {
		subreddit = str;
		after="";
		generateURL();
	}
	
	private void generateURL() {
		url = URL_TEMPLATE.replace("SUBREDDIT_NAME", subreddit);
		url = URL_TEMPLATE.replace("AFTER", after);
	}
	
	List<Post> fetchPosts() {
		String raw = RemoteData.readContents(url);
		List<Post> list = new ArrayList<Post>();
		try {
			JSONObject data = new JSONObject(raw).getJSONObject("data");
			JSONArray children = data.getJSONArray("children");
			after = data.getString("after");
			
			for (int i = 0; i < children.length(); i++) {
				JSONObject cur = children.getJSONObject(i).getJSONObject("data");
				Post p = new Post();
				p.title = cur.optString("title");
				p.url = cur.optString("url");
				p.numComments = cur.optInt("num_comments");
				p.points = cur.optInt("score");
				p.author = cur.optString("author");
				p.subreddit = cur.optString("subreddit");
				p.permalink = cur.optString("permalink");
				p.domain = cur.optString("domain");
				p.id = cur.optString("id");
				if (p.title != null) {
					list.add(p);
				}
			}
		} catch (Exception e) {
			Log.d("fetch", e.toString());
		}
		return list;
	}
	
	List<Post> fetchMorePosts() {
		generateURL();
		return fetchPosts();
	}
}
