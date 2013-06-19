package org.avs.easy;

import org.avs.gps.Gps;

import android.app.Dialog;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMyLocationChangeListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

public class Easy extends FragmentActivity implements OnMyLocationChangeListener {

	private Gps gps;
	private LatLng latlng;
	GoogleMap map;
	LocationManager locationManager;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_easy);
		
		// Getting Google Play availability status
		int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());

		// Showing status
		if (status != ConnectionResult.SUCCESS) { // Google Play Services are
													// not available
			int requestCode = 10;
			Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this,
					requestCode);
			dialog.show();

		} else { // Google Play Services are available
			//Ativa o gps caso esteja desativado
			gps = new Gps();
			
			double lat = -8.02044;
			double lng=  -34.9817;
	        

			// Getting reference to the SupportMapFragment of activity_main.xml
			FragmentManager fmanager = getSupportFragmentManager();
	        Fragment fragment = fmanager.findFragmentById(R.id.map);
	        SupportMapFragment supportmapfragment = (SupportMapFragment)fragment;
	        map = supportmapfragment.getMap();

			// Getting GoogleMap object from the fragment
			//googleMap = fm.getMap();

			// Enabling MyLocation Layer of Google Map
	        map.getUiSettings().setCompassEnabled(true);
	        map.getUiSettings().setMyLocationButtonEnabled(true);
	        map.setTrafficEnabled(true);
	        map.setMyLocationEnabled(true);
	        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);

			// Setting event handler for location change
	        map.setOnMyLocationChangeListener(this);

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
	    case R.id.action_refresh:
	      Toast.makeText(this, "Action refresh selected", Toast.LENGTH_SHORT)
	          .show();
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

	@Override
	public void onMyLocationChange(Location location) {
		TextView tvLocation = (TextView) findViewById(R.id.tv_location);
		
		// Getting latitude of the current location
        double latitude = location.getLatitude();
 
        // Getting longitude of the current location
        double longitude = location.getLongitude();
 
        // Creating a LatLng object for the current location
        LatLng latLng = new LatLng(latitude, longitude);
        
     // Showing the current location in Google Map
        map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
 
        // Zoom in the Google Map
        map.animateCamera(CameraUpdateFactory.zoomTo(15));
 
     // Setting latitude and longitude in the TextView tv_location
        tvLocation.setText("Latitude:" +  latitude  + ", Longitude:"+ longitude );
        /*//map.animateCamera(CameraUpdateFactory.newLatLng(latlng));
      	map.animateCamera(CameraUpdateFactory.zoomIn());
      	map.animateCamera(CameraUpdateFactory.zoomTo(13), 2000, null);
      	
      	CameraPosition cameraPosition = new CameraPosition.Builder()
          .target(latlng)      // Sets the center of the map to Mountain View
          .zoom(13)                   // Sets the zoom
          .bearing(90)                // Sets the orientation of the camera to east
          .tilt(45)                   // Sets the tilt of the camera to 30 degrees
          .build();                   // Creates a CameraPosition from the builder
      	
      	map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
      	
      	// Setting latitude and longitude in the TextView tv_location
        tvLocation.setText("Latitude:" +  latitude  + ", Longitude:"+ longitude );
          
         /* if(map!=null){
          	
          	
          	Marker me = map.addMarker(new MarkerOptions()
          														.position(latlng)
          														.title("Você está aqui")
          														);
             	
          	
          }*/
		
        
	}
}
