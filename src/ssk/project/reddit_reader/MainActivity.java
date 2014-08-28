package ssk.project.reddit_reader;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class MainActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		addFragment();
	}

	public void addFragment() {
		getSupportFragmentManager()
			.beginTransaction()
			.add(R.id.fragments_holder, PostsFragment.newInstance("keto"))
			.commit();
	}

}
