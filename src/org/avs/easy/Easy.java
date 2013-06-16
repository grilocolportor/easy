package org.avs.easy;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;

public class Easy extends FragmentActivity  {

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_easy);
		
		FragmentManager fmanager = getSupportFragmentManager();
        Fragment fragment = fmanager.findFragmentById(R.id.map);
        SupportMapFragment supportmapfragment = (SupportMapFragment)fragment;
        GoogleMap supportMap = supportmapfragment.getMap();
        
        
        supportMap.getUiSettings().setCompassEnabled(true);
        supportMap.getUiSettings().setMyLocationButtonEnabled(true);
        supportMap.setTrafficEnabled(true);
        supportMap.setMyLocationEnabled(true);
    	LatLng latLng = new LatLng(-28.388286,-51.845645);
    	LatLng MOUNTAIN_VIEW = new LatLng(37.4, -122.1);
    	supportMap.animateCamera(CameraUpdateFactory.newLatLngZoom(MOUNTAIN_VIEW, 15));
    	supportMap.animateCamera(CameraUpdateFactory.zoomIn());
    	supportMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
    	
    	CameraPosition cameraPosition = new CameraPosition.Builder()
        .target(latLng)      // Sets the center of the map to Mountain View
        .zoom(17)                   // Sets the zoom
        .bearing(90)                // Sets the orientation of the camera to east
        .tilt(20)                   // Sets the tilt of the camera to 30 degrees
        .build();                   // Creates a CameraPosition from the builder
    	supportMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        //double lat = supportMap.getMyLocation().getLatitude();
        //double lng = supportMap.getMyLocation().getLongitude();
        
        if(supportMap!=null){
        	supportMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        	
        	Marker me = supportMap.addMarker(new MarkerOptions()
        														.position(latLng)
        														.title("Você está aqui")
        														);
           	
        	
        }
	 
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.easy, menu);
		return true;
	}

}
