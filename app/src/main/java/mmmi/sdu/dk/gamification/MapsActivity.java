package mmmi.sdu.dk.gamification;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

import mmmi.sdu.dk.gamification.utils.GPSService;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

      private GoogleMap mMap;
      private String locationProvider  = LocationManager.GPS_PROVIDER;
      private LocationManager mLocationManager = null;
      private int updateTime = 1000 * 10;
      private int updateDistance = 10;

      @Override
      protected void onCreate(Bundle savedInstanceState) {
            mLocationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_maps);
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                  .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
      }

      @SuppressLint("MissingPermission")
      @Override
      public void onMapReady(GoogleMap googleMap) {
            mMap = googleMap;
            mMap.getUiSettings().setMapToolbarEnabled(false);
            BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.pumpkin_icon);
            mLocationManager.requestLocationUpdates(locationProvider,updateTime, updateDistance, new GPSService(mMap, icon, getApplicationContext()));
      }

}
