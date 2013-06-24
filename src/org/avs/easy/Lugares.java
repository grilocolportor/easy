package org.avs.easy;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class Lugares extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lugares);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.lugares, menu);
		return true;
	}

}
