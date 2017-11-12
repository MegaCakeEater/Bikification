package mmmi.sdu.dk.gamification;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import mmmi.sdu.dk.gamification.utils.GPSService;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

      private GoogleMap mMap;
      private String locationProvider  = LocationManager.GPS_PROVIDER;
      private LocationManager mLocationManager = null;
      private int updateTime = 1000 * 5;
      private int updateDistance = 1;

      @Override
      protected void onCreate(Bundle savedInstanceState) {
            mLocationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_maps);
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                  .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
      }


      /**
       * Manipulates the map once available.
       * This callback is triggered when the map is ready to be used.
       * This is where we can add markers or lines, add listeners or move the camera. In this case,
       * we just add a marker near Sydney, Australia.
       * If Google Play services is not installed on the device, the user will be prompted to install
       * it inside the SupportMapFragment. This method will only be triggered once the user has
       * installed Google Play services and returned to the app.
       */
      @SuppressLint("MissingPermission")
      @Override
      public void onMapReady(GoogleMap googleMap) {
            mMap = googleMap;
            mMap.getUiSettings().setMapToolbarEnabled(false);
            BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.pumpkin_icon);
            mLocationManager.requestLocationUpdates(locationProvider,updateTime, updateDistance, new GPSService(mMap, icon));

      }

}
