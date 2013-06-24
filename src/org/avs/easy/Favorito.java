package org.avs.easy;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class Favorito extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_favorito);
		
		ListView listView = (ListView) findViewById(R.id.listViewFavoritoLugares);
		final EditText buscarFavoritos = (EditText) findViewById(R.id.editTextFindFavorito);
		
		final ArrayList<String> toDoList = new ArrayList<String>();
		final ArrayAdapter<String> aa;
		aa = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, toDoList );
		listView.setAdapter(aa);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.favorito, menu);
		return true;
	}

}
