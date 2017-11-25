package mmmi.sdu.dk.gamification.utils;


import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Random;

public class GPSService implements LocationListener {

      private Circle playerCircle;
      private Location currentLocation = null;
      private GoogleMap mMap = null;
      private BitmapDescriptor icon = null;
      private ArrayList<Marker> collectibles;
      private int count = 0;
      private float pickupRadius = 0.0005f;
      private DatabaseReference mDatabase;
      String url;

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

                  //Load the avatar
                  FirebaseUser userId = FirebaseAuth.getInstance().getCurrentUser();
                  final String uid = userId.getUid();
                  mDatabase = FirebaseDatabase.getInstance().getReference();
                  mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                              url = dataSnapshot.child(uid).child("avatar").child("currentAvatar").getValue(String.class);

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {}
                  });

                  CircleOptions co = new CircleOptions();
                  co.center(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()));
                  co.fillColor(Color.RED);
                  co.strokeColor(Color.RED);
                  co.radius(5);
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
                  if(calculateEuclideanDistance(currentLocation.getLongitude(), currentLocation.getLatitude(), m.getPosition().longitude, m.getPosition().latitude) <= pickupRadius) {
                        ids.add(collectibles.indexOf(m));
                        m.remove();
                        count++;
                        System.out.println("GOT COLLECTIBLE # " + count );
                  }
            }

            //We put :
            //One object collected = 1 point
            //Coins : 20 coins per object collected
            for(Integer i : ids) {
                  FirebaseUser userId = FirebaseAuth.getInstance().getCurrentUser();
                  final String uid = userId.getUid();
                  final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                  final DatabaseReference counter = mDatabase.child(uid);
                  counter.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                              if(dataSnapshot.hasChild("points")) {
                                    long count = (long) dataSnapshot.child("points").getValue();
                                    counter.child("points").setValue(++count);

                                    //Update coins
                                    counter.child("avatar").child("coins").setValue(++count*20);

                              } else {
                                    counter.child("points").setValue(1);
                                    counter.child("avatar").child("coins").setValue(20);

                              }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                              // throw an error if setValue() is rejected
                              throw databaseError.toException();
                        }
                  });
                  collectibles.remove(i);
            }
      }

      private double calculateEuclideanDistance(double l1_lng, double l1_lat, double l2_lng, double l2_lat) {
            double distance = Math.sqrt(Math.pow(l1_lng - l2_lng,2) + Math.pow(l1_lat - l2_lat,2));
            return distance;
      }
}