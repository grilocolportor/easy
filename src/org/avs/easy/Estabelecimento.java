package org.avs.easy;

import org.avs.json.JSONParser;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.view.Menu;
import android.widget.TextView;

public class Estabelecimento extends Activity {

	String id_google;
	
	// Progress Dialog
	private ProgressDialog pDialog;

	// JSON parser class
	JSONParser jsonParser = new JSONParser();

	// single product url
	private static final String url_product_detials = "http://api.androidhive.info/android_connect/get_product_details.php";

	// url to update product
	private static final String url_update_product = "http://api.androidhive.info/android_connect/update_product.php";

	// url to delete product
	private static final String url_delete_product = "http://api.androidhive.info/android_connect/delete_product.php";

	// JSON Node names
	private static final String TAG_SUCCESS = "sucesso";
	private static final String TAG_EMPRESA = "empresa";
	private static final String TAG_PID = "empresa_id";
	private static final String TAG_NAME = "name";
	private static final String TAG_LATITUDE = "latitude";
	private static final String TAG_LOGINTUDE = "longitude";

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_estabelecimento);
		
		TextView t = (TextView) findViewById(R.id.estabelecimento);
		
		Intent i = getIntent();
		Bundle param = i.getExtras();
		
		id_google = param.getString("title");
		
		t.setText(param.getString("title"));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.estabelecimento, menu);
		return true;
	}
	
	class getAllEmpresa extends AsyncTask<String, String, String>{
		
		@Override
		protected void onPreExecute(){
			super.onPreExecute();
			pDialog = new ProgressDialog(Estabelecimento.this);
			pDialog.setMessage("Carregando produtos. Aguarde...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}

}
