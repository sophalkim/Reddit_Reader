package ssk.project.reddit_reader;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class PostsFragment extends Fragment {

	ListView postsList;
	ArrayAdapter<Post> adapter;
	Handler handler;
	
	String subreddit;
	List<Post> posts;
	PostsHolder postsHolder;
	
	public PostsFragment() {
		handler = new Handler();
		posts = new ArrayList<Post>();
	}
	
	public static Fragment newInstance(String subreddit) {
		PostsFragment pf = new PostsFragment();
		pf.subreddit = subreddit;
		pf.postsHolder = new PostsHolder(subreddit);
		return pf;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.posts, container, false);
		postsList = (ListView) v.findViewById(R.id.posts_list);
		return v;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initialize();
	}
	
	private void initialize() {
		if (posts.size() == 0) {
			new Thread() {
				public void run() {
					posts.addAll(postsHolder.fetchPosts());
					handler.post(new Runnable() {
						public void run() {
							createAdapter();
						}
					});
				}
			}.start();
		} else {
			createAdapter();
		}
	}
	
	private void createAdapter() {
		if (getActivity() == null) {
			return;
		}
		adapter = new ArrayAdapter<Post>(getActivity(), R.layout.post_item, posts) {
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				if (convertView == null) {
					convertView = getActivity()
									.getLayoutInflater()
									.inflate(R.layout.post_item, null);
				}
				
				TextView postTitle = (TextView) convertView.findViewById(R.id.post_title);
				TextView postDetails = (TextView) convertView.findViewById(R.id.post_details);
				TextView postScore = (TextView) convertView.findViewById(R.id.post_score);
				
				postTitle.setText(posts.get(position).getTitle());
				postDetails.setText(posts.get(position).getDetails());
				postScore.setText(posts.get(position).getScore());
				
				return convertView;
			}
		};
		postsList.setAdapter(adapter);
	}
}
