package mmmi.sdu.dk.gamification;

import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;

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
import com.google.maps.DirectionsApi;

import mmmi.sdu.dk.gamification.routing.GetDirectionsAsync;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

      private GoogleMap mMap;

      @Override
      protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_maps);
            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
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
            LatLngBounds bounds = new LatLngBounds(new LatLng(55.37067460000001, 10.428067300000066),  new LatLng(55.37545296581934,10.428192615509033));
            mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 400, 400, 0));
            params[0] = new LatLng(55.37067460000001, 10.428067300000066);
            params[1] = new LatLng(55.37545296581934,10.428192615509033);
            AsyncTask at = new GetDirectionsAsync(this.mMap);
            at.execute(params);
      }

}
