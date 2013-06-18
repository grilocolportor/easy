package org.avs.easy;

import org.avs.gps.Gps;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class Easy extends FragmentActivity implements LocationListener, Runnable {

	private Gps gps;
	private LatLng latlng;
	GoogleMap map;
	LocationManager locationManager;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_easy);
		
		gps = new Gps();
		
		FragmentManager fmanager = getSupportFragmentManager();
        Fragment fragment = fmanager.findFragmentById(R.id.map);
        SupportMapFragment supportmapfragment = (SupportMapFragment)fragment;
        map = supportmapfragment.getMap();
        
        map.getUiSettings().setCompassEnabled(true);
        map.getUiSettings().setMyLocationButtonEnabled(true);
        map.setTrafficEnabled(true);
        map.setMyLocationEnabled(true);
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,0, this);
       		
		run();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.easy, menu);
		return true;
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
             	//map.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng, 15));
             	map.animateCamera(CameraUpdateFactory.zoomIn());
             	map.animateCamera(CameraUpdateFactory.zoomTo(13), 2000, null);
             	
             	CameraPosition cameraPosition = new CameraPosition.Builder()
                 .target(latlng)      // Sets the center of the map to Mountain View
                 .zoom(13)                   // Sets the zoom
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

}
