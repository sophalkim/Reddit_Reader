package ssk.project.reddit_reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.util.Log;

public class RemoteData {

	public static HttpURLConnection getConnection(String url) {
		HttpURLConnection hcon = null;
		try {
			hcon = (HttpURLConnection) new URL(url).openConnection();
			hcon.setReadTimeout(30000);
			hcon.setRequestProperty("User-Agent", "Alien V1.0");
		} catch (MalformedURLException e) {
			Log.e("getConnection()", "Could not connect: " + e.toString());
		} catch (IOException e) {
			Log.e("getConnection()", "Could not connect: " + e.toString());
		}
		return hcon;
	}
	
	public static String readContents(String url) {
		HttpURLConnection hcon = getConnection(url);
		if (hcon == null) {
			return null;
		}
		try {
			StringBuffer sb = new StringBuffer(8192);
			String temp = "";
			BufferedReader br = new BufferedReader(new InputStreamReader(hcon.getInputStream()));
			while ((temp = br.readLine()) != null) {
				sb.append(temp).append("\n");
			}
			br.close();
			return sb.toString();
		} catch (IOException e) {
			Log.d("READ FAILED", e.toString());
			return null;
		}
	}
	
}
