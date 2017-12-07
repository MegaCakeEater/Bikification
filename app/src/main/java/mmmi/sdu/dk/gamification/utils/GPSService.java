package mmmi.sdu.dk.gamification.utils;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.Random;

import mmmi.sdu.dk.gamification.R;
import mmmi.sdu.dk.gamification.model.Avatar;
import mmmi.sdu.dk.gamification.model.User;

public class GPSService implements LocationListener {

      private Marker player;
      private Location currentLocation = null;
      private GoogleMap mMap = null;
      private BitmapDescriptor icon = null;
      private BitmapDescriptor playerAvatar;
      private ArrayList<Marker> collectibles;
      private Context context;
      private boolean loadingAvatar = false;
      private boolean loadingBitmap = false;
      private Target target = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                  playerAvatar = BitmapDescriptorFactory.fromBitmap(bitmap);
                        player.setIcon(playerAvatar);
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
            }
      };

      private float pickupRadius = 0.0002f;
      private Avatar avatar;
      private User user;

      public GPSService(GoogleMap mMap, BitmapDescriptor icon, Context context) {
            this.mMap = mMap;
            collectibles = new ArrayList();
            this.icon = icon;
            this.context = context;
      }

      @Override
      public void onLocationChanged(Location location) {
            if (location != null) {
                  currentLocation = location;

                  if (player == null || !loadingAvatar) {
                        if (DatabaseFacade.getInstance().getUser() != null) {
                              user = DatabaseFacade.getInstance().getUser();
                        }
                        if (avatar == null) {
                              playerAvatar = BitmapDescriptorFactory.fromResource(R.drawable.bike);
                        }
                        if (DatabaseFacade.getInstance().getAvatars().get(user.getCurrentAvatar()) != null) {
                              avatar = DatabaseFacade.getInstance().getAvatars().get(user.getCurrentAvatar());
                        }
                        if (!loadingBitmap && avatar != null) {
                              Picasso.with(context).load(avatar.getImageUrl()).resize(96, 96).into(target);
                              loadingBitmap = true;
                        }
                        if(playerAvatar != null && player == null) {
                              MarkerOptions mo = new MarkerOptions();
                              mo.icon(playerAvatar);
                              mo.position(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()));
                              player = mMap.addMarker(mo);
                        }
                  }

                  //todo when are collectibles spawned?
                  if (collectibles.size() < 10) {
                        populate();
                  }
                  checkCollision();
                  if(player != null) {
                        player.setPosition(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()));
                  }
                  LatLng l1 = new LatLng(currentLocation.getLatitude() + 0.0005, currentLocation.getLongitude() + 0.0005);
                  LatLng l2 = new LatLng(currentLocation.getLatitude() - 0.0005, currentLocation.getLongitude() - 0.0005);
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

      //todo how are collectibles spawned?
      private void populate() {
            Random rand = new Random();
            for (int i = collectibles.size(); i < 10; i++) {
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
            for (Marker m : collectibles) {
                  if (calculateEuclideanDistance(currentLocation.getLongitude(), currentLocation.getLatitude(), m.getPosition().longitude, m.getPosition().latitude) <= pickupRadius) {
                        Toast.makeText(context,"Collectible get!", Toast.LENGTH_SHORT);
                        ids.add(collectibles.indexOf(m));
                        m.remove();
                  }
            }

            //We put :
            //One object collected = 1 point
            //Coins : 20 coins per object collected
            for (Integer i : ids) {
                  if (user != null) {
                        user.setPoints(user.getPoints() + 1);
                        user.setCoins(user.getCoins() + 20);
                        DatabaseFacade.getInstance().updateUser(user);
                        collectibles.remove(i);
                  }
            }
      }

      private double calculateEuclideanDistance(double l1_lng, double l1_lat, double l2_lng, double l2_lat) {
            double distance = Math.sqrt(Math.pow(l1_lng - l2_lng, 2) + Math.pow(l1_lat - l2_lat, 2));
            return distance;
      }
}