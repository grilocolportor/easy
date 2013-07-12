package org.avs.easy;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.TextView;

public class Estabelecimento extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_estabelecimento);
		
		TextView t = (TextView) findViewById(R.id.estabelecimento);
		
		Intent i = getIntent();
		Bundle param = i.getExtras();
		
		t.setText(param.getString("title"));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.estabelecimento, menu);
		return true;
	}

}
