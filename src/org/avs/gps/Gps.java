package org.avs.gps;

import com.google.android.gms.maps.model.LatLng;

import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;

public class Gps implements LocationListener{
	private LocationManager locationManager;
	private String provider;
	private LatLng latlng;
	private Double lat;
	private Double lng;
	Context context;
	
	public Gps(){
		
	}
	
	private void ativarGps(){
		
		if (!locationManager.isProviderEnabled(locationManager.GPS_PROVIDER))
		{
		 Intent intent = new Intent();
		 intent.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
		 intent.addCategory(Intent.CATEGORY_ALTERNATIVE);
		 intent.setData(Uri.parse("3"));
		 context.sendBroadcast(intent);
		}
		
	}
	
	public void findYourLocation(Context c, LocationManager lm){
		
		this.locationManager = lm;
		this.context = c;
		
		ativarGps();
		
		Criteria criteria = new Criteria();
		provider = locationManager.getBestProvider(criteria, false);
		Location location = locationManager.getLastKnownLocation(provider);
		
		if(location!=null){
			onLocationChanged(location);
		}else{
			lat=-8.02044;
			lng= -34.9817;
			latlng = new LatLng(lat,lng);
		}
		
	}
	
	public LatLng getLocationLatLng(){
		return  latlng;
	}
	
	public Double getLatitude(){
		return lat;
	}
	
	public Double getLongitude(){
		return lng;
	}

	@Override
	public void onLocationChanged(Location location) {
		lat = location.getLatitude();
		lng =  location.getLongitude();
		latlng = new LatLng(lat,lng);
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
	
}
