package org.avs.easy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

import mensagens.Alertas;
import network.Internet;

import org.avs.gps.Gps;
import org.json.JSONObject;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Easy extends FragmentActivity implements   LocationListener {

	private Gps gps;
	private LatLng latlng;
	private String categoria="";
	private String raio="";
	
	Alertas alerta = new Alertas();
	Internet internet;
	
	// Progress dialog
	ProgressDialog pDialog;
		
	GoogleMap mGoogleMap;	
	
	String[] mPlaceType=null;
	String[] mPlaceTypeName=null;
	
	double mLatitude=0;
	double mLongitude=0;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_easy);
		
		internet = new Internet(this);
		
		if (!internet.conectado()) {
			// Internet Connection is not present
			alerta.showAlertDialog(Easy.this, "Internet Connection Error",
					"Please connect to working Internet connection", false);
			// stop executing code by return
			return;
		}
							
				// Getting Google Play availability status
		        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());

		        
		        if(status!=ConnectionResult.SUCCESS){ // Google Play Services are not available

		        	int requestCode = 10;
		            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this, requestCode);
		            dialog.show();

		        }else { // Google Play Services are available
		        	
		        	gps = new Gps(Easy.this);
		        		// Getting reference to the SupportMapFragment
			    	SupportMapFragment fragment = ( SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
			    			
			    	// Getting Google Map
			    	mGoogleMap = fragment.getMap();
			    			
			    	// Enabling MyLocation in Google Map
			    	mGoogleMap.setMyLocationEnabled(true);
			    	
			    	mGoogleMap.setTrafficEnabled(true);
			    	
			    	
			    	// Getting LocationManager object from System Service LOCATION_SERVICE
		            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

		            // Creating a criteria object to retrieve provider
		            Criteria criteria = new Criteria();

		            // Getting the name of the best provider
		            String provider = locationManager.getBestProvider(criteria, true);

		            // Getting Current Location From GPS
		            Location location = locationManager.getLastKnownLocation(provider);

		            if(location!=null){
		                    onLocationChanged(location);
		            }

		            locationManager.requestLocationUpdates(provider, 20000, 0, this);
		        }

	}
	
	public void getPosicaoUsuario(){
		Runnable runnable = new Runnable(){

			@Override
			public void run() {
				mLatitude = gps.getLatitude();
				mLongitude = gps.getLongitude();
				timeGetPosicao();
			}
			
		};
		
	}
	
	private void timeGetPosicao(){
		try {
		      Thread.sleep(2000);
		    } catch (InterruptedException e) {
		      e.printStackTrace();
		    }
		
	}

	
	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		// TODO Auto-generated method stub
		super.onActivityResult(arg0, arg1, arg2);
		
		if(arg2.getExtras().containsKey("categoria")){
			categoria = arg2.getStringExtra("categoria");
		}
		if(arg2.getExtras().containsKey("raio")){
			raio = arg2.getStringExtra("raio");
		}
		Toast.makeText(this, "Raio: " + raio +" :: Estados marcados : " + categoria, Toast.LENGTH_LONG).show();
		
		
		StringBuilder sb = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
		sb.append("location="+mLatitude+","+mLongitude);
		sb.append("&radius="+raio);
		sb.append("&types="+categoria);
		sb.append("&sensor=true");
		sb.append("&key=AIzaSyBdqOUnyB-TUoJnnMgqjsSmGE9iBfuq92o");
		
		
		// Creating a new non-ui thread task to download Google place json data 
        PlacesTask placesTask = new PlacesTask();		        			        
        
		// Invokes the "doInBackground()" method of the class PlaceTask
        placesTask.execute(sb.toString());

		
		
	}
	
	/** A method to download json data from url */
    private String downloadUrl(String strUrl) throws IOException{
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try{
                URL url = new URL(strUrl);                
                

                // Creating an http connection to communicate with url 
                urlConnection = (HttpURLConnection) url.openConnection();                

                // Connecting to url 
                urlConnection.connect();                

                // Reading data from url 
                iStream = urlConnection.getInputStream();

                BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

                StringBuffer sb  = new StringBuffer();

                String line = "";
                while( ( line = br.readLine())  != null){
                        sb.append(line);
                }

                data = sb.toString();

                br.close();

        }catch(Exception e){
                Log.d("Exception while downloading url", e.toString());
        }finally{
                iStream.close();
                urlConnection.disconnect();
        }

        return data;
    }         

	
	/** A class, to download Google Places */
	private class PlacesTask extends AsyncTask<String, Integer, String>{

		String data = null;
		
		// Invoked by execute() method of this object
		@Override
		protected String doInBackground(String... url) {
			try{
				data = downloadUrl(url[0]);
			}catch(Exception e){
				 Log.d("Background Task",e.toString());
			}
			return data;
		}
		
		// Executed after the complete execution of doInBackground() method
		@Override
		protected void onPostExecute(String result){			
			ParserTask parserTask = new ParserTask();
			
			// Start parsing the Google places in JSON format
			// Invokes the "doInBackground()" method of the class ParseTask
			parserTask.execute(result);
		}
		
	}
	
	/** A class to parse the Google Places in JSON format */
	private class ParserTask extends AsyncTask<String, Integer, List<HashMap<String,String>>>{

		JSONObject jObject;
		
		// Invoked by execute() method of this object
		@Override
		protected List<HashMap<String,String>> doInBackground(String... jsonData) {
		
			List<HashMap<String, String>> places = null;			
			PlaceJSONParser placeJsonParser = new PlaceJSONParser();
        
	        try{
	        	jObject = new JSONObject(jsonData[0]);
	        	
	            /** Getting the parsed data as a List construct */
	            places = placeJsonParser.parse(jObject);
	            
	        }catch(Exception e){
	                Log.d("Exception",e.toString());
	        }
	        return places;
		}
		
		// Executed after the complete execution of doInBackground() method
		@Override
		protected void onPostExecute(List<HashMap<String,String>> list){			
			
			// Clears all the existing markers 
			mGoogleMap.clear();
			
			for(int i=0;i<list.size();i++){
			
				// Creating a marker
	            MarkerOptions markerOptions = new MarkerOptions();
	            
	            // Getting a place from the places list
	            HashMap<String, String> hmPlace = list.get(i);
	
	            // Getting latitude of the place
	            double lat = Double.parseDouble(hmPlace.get("lat"));	            
	            
	            // Getting longitude of the place
	            double lng = Double.parseDouble(hmPlace.get("lng"));
	            
	            // Getting name
	            String name = hmPlace.get("place_name");
	            
	            // Getting vicinity
	            String vicinity = hmPlace.get("vicinity");
	            
	            LatLng latLng = new LatLng(lat, lng);
	            
	            // Setting the position for the marker
	            markerOptions.position(latLng);
	
	            // Setting the title for the marker. 
	            //This will be displayed on taping the marker
	            markerOptions.title(name + " : " + vicinity);	            
	
	            // Placing a marker on the touched position
	            mGoogleMap.addMarker(markerOptions);            
            
			}		
			
		}
		
	}
	
	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		mLatitude = location.getLatitude();
		mLongitude = location.getLongitude();
		LatLng latLng = new LatLng(mLatitude, mLongitude);
		
     	
     CameraPosition cameraPosition = new CameraPosition.Builder()
         .target(latLng)      // Sets the center of the map to Mountain View
         .zoom(15)                   // Sets the zoom
         .bearing(90)                // Sets the orientation of the camera to east
         .tilt(45)                   // Sets the tilt of the camera to 30 degrees
         .build();                   // Creates a CameraPosition from the builder
     	
     	mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition), 2000, null);
         
		
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.easy, menu);
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent i;
	    switch (item.getItemId()) {
	    case R.id.mnu_categoria:
	    	i = new Intent(getApplicationContext(), Categoria.class);
			// Sending user current geo location
			//i.putExtra("user_latitude", Double.toString(gps.getLatitude()));
			//i.putExtra("user_longitude", Double.toString(gps.getLongitude()));
			
			// passing near places to map activity
			//i.putExtra("near_places", nearPlaces);
			// staring activity
			//startActivity(i);
			startActivityForResult(i, 1);
			
			
	      break;
	    case R.id.mnu_favorito:
	    	mGoogleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
	    	LatLng latLng = new LatLng(mLatitude, mLongitude);
	    	 CameraPosition cameraPosition = new CameraPosition.Builder()
	    	 .target(latLng)
	         .zoom(17)                   // Sets the zoom
	         .bearing(90)                // Sets the orientation of the camera to east
	         .tilt(45)                   // Sets the tilt of the camera to 30 degrees
	         .build();                   // Creates a CameraPosition from the builder
	     	
	     	mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition), 2000, null);
	      break;

	    default:
	      break;
	    }

	    return true;
	  }
	
}
