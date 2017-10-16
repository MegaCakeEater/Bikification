package mmmi.sdu.dk.gamification;

import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import mmmi.sdu.dk.gamification.routing.GetDirectionsAsync;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

      private GoogleMap mMap;
      private GeoDataClient mGeoDataClient;
      private PlaceDetectionClient mPlaceDetectionClient;
      private FusedLocationProviderClient mFusedLocationProviderClient;

      @Override
      protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_maps);

            mGeoDataClient = Places.getGeoDataClient(this, null);

            mPlaceDetectionClient = Places.getPlaceDetectionClient(this, null);

            mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

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
      @Override
      public void onMapReady(GoogleMap googleMap) {
            mMap = googleMap;
            LatLng[] params = new LatLng[2];
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            builder.include(ApplicationHelper.getPlace1().getLatLng());
            builder.include(ApplicationHelper.getPlace2().getLatLng());
            LatLngBounds bounds = builder.build();
            mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 400, 400, 0));
            params[0] = ApplicationHelper.getPlace1().getLatLng();
            params[1] = ApplicationHelper.getPlace2().getLatLng();
            AsyncTask at = new GetDirectionsAsync(this.mMap);
            at.execute(params);
      }

}
