package mmmi.sdu.dk.gamification.utils;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Random;

import mmmi.sdu.dk.gamification.ApplicationHelper;

public class GPSService implements LocationListener {
      private Circle playerCircle;
      private Location currentLocation = null;
      private GoogleMap mMap = null;
      private BitmapDescriptor icon = null;
      private ArrayList<Marker> collectibles;
      private int count = 0;
      private float pickupRadius = 0.0001f;
      public GPSService(GoogleMap mMap, BitmapDescriptor icon) {
            this.mMap = mMap;
            collectibles = new ArrayList();
            this.icon = icon;
      }

      @Override
      public void onLocationChanged(Location location) {
            if(location != null) {

                  if(playerCircle != null) {
                        playerCircle.remove();
                  }
                  currentLocation = location;
                  if(collectibles.size() < 10) {
                        populate();
                  }
                  checkCollision();
                  CircleOptions co = new CircleOptions();
                  co.center(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()));
                  co.fillColor(Color.BLUE);
                  co.strokeColor(Color.BLUE);
                  co.radius(2.5);
                  playerCircle = mMap.addCircle(co);
                  LatLng l1 = new LatLng(currentLocation.getLatitude()+0.0005, currentLocation.getLongitude()+0.0005);
                  LatLng l2 = new LatLng(currentLocation.getLatitude()-0.0005, currentLocation.getLongitude()-0.0005);
                  LatLngBounds.Builder builder = new LatLngBounds.Builder();
                  builder.include(l1);
                  builder.include(l2);
                  LatLngBounds bounds = builder.build();
                  mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 400, 400, 0));
            }

      }

      @Override
      public void onStatusChanged(String provider, int status, Bundle extras) {

      }

      @Override
      public void onProviderEnabled(String provider) {

      }

      @Override
      public void onProviderDisabled(String provider) {

      }

      private void populate() {
            Random rand = new Random();
            for(int i = collectibles.size(); i<10; i++) {
                  float lat = rand.nextFloat() * 0.001f - 0.0005f;
                  float lng = rand.nextFloat() * 0.001f - 0.0005f;
                  MarkerOptions m = new MarkerOptions();
                  m.icon(icon);
                  LatLng pos = new LatLng(currentLocation.getLatitude() + lat, currentLocation.getLongitude() + lng);
                  m.position(pos);
                  collectibles.add(mMap.addMarker(m));
            }
      }
      private void checkCollision() {
            ArrayList<Integer> ids = new ArrayList();
            for(Marker m : collectibles) {
                  if(currentLocation.getLatitude() + pickupRadius >= m.getPosition().latitude && currentLocation.getLatitude() - pickupRadius <= m.getPosition().latitude
                        && currentLocation.getLongitude() + pickupRadius >= m.getPosition().longitude && currentLocation.getLongitude() - pickupRadius <= m.getPosition().longitude) {
                        ids.add(collectibles.indexOf(m));
                        m.remove();
                        count++;
                        System.out.println("GOT COLLECTIBLE # " + count );
                  }
            }
            for(Integer i : ids) {
                  collectibles.remove(i);
            }
      }
}