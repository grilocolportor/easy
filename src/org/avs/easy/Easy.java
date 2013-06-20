package org.avs.easy;

import mensagens.Alertas;
import network.Internet;

import org.avs.gps.Gps;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

public class Easy extends FragmentActivity implements   LocationListener, Runnable {

	private Gps gps;
	private LatLng latlng;
	GoogleMap map;
	LocationManager locationManager;
	
	Alertas alerta = new Alertas();
	Internet internet;
	
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
		
		gps = new Gps();
		
		FragmentManager fmanager = getSupportFragmentManager();
        Fragment fragment = fmanager.findFragmentById(R.id.map);
        SupportMapFragment supportmapfragment = (SupportMapFragment)fragment;
        map = supportmapfragment.getMap();
        
        map.getUiSettings().setCompassEnabled(true);
        map.getUiSettings().setMyLocationButtonEnabled(true);
        map.getUiSettings().setRotateGesturesEnabled(true);
        
        map.setTrafficEnabled(true);
        map.setMyLocationEnabled(true);
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,0, this);
       		
		run();
		
	}
	
	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		
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
	public void run() {
		// TODO Auto-generated method stub
		//while (true) {
            
             try {
            	gps.findYourLocation(this, locationManager);
                 
                latlng = new LatLng(gps.getLatitude(),gps.getLongitude());
             	map.animateCamera(CameraUpdateFactory.newLatLng(latlng));
             	//map.animateCamera(CameraUpdateFactory.zoomIn());
             	map.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
             	
             	CameraPosition cameraPosition = new CameraPosition.Builder()
                 .target(latlng)      // Sets the center of the map to Mountain View
                 .zoom(15)                   // Sets the zoom
                 .bearing(90)                // Sets the orientation of the camera to east
                 .tilt(45)                   // Sets the tilt of the camera to 30 degrees
                 .build();                   // Creates a CameraPosition from the builder
             	
             	map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                 
                 
                /* if(map!=null){
                 	
                 	
                 	Marker me = map.addMarker(new MarkerOptions()
                 														.position(latlng)
                 														.title("Você está aqui")
                 														);
                    	
                 	
                 }*/
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}        
                  
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.easy, menu);
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    case R.id.lugares:
	    	Intent i = new Intent(getApplicationContext(), Lugares.class);
			// Sending user current geo location
			i.putExtra("user_latitude", Double.toString(gps.getLatitude()));
			i.putExtra("user_longitude", Double.toString(gps.getLongitude()));
			
			// passing near places to map activity
			//i.putExtra("near_places", nearPlaces);
			// staring activity
			startActivity(i);
	      break;
	    case R.id.action_settings:
	      Toast.makeText(this, "Action Settings selected", Toast.LENGTH_SHORT)
	          .show();
	      break;

	    default:
	      break;
	    }

	    return true;
	  }
}
