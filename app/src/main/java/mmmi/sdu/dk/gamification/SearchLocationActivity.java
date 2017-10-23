package mmmi.sdu.dk.gamification;

import android.*;
import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

import java.io.Serializable;

import static android.app.Activity.RESULT_OK;

public class SearchLocationActivity extends Activity {

    private boolean mLocationPermissionGranted;
    private int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION;
    private int PLACES1_REQUEST = 1;
    private int PLACES2_REQUEST = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchlocation);
        PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION =  ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION);
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            }
        }
        findViewById(R.id.btn_maps).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMap();
            }
        });
        AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
              .setCountry("DK")
              .build();

        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
              getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        autocompleteFragment.setFilter(typeFilter);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
               ApplicationHelper.setPlace1(place);
            }

            @Override
            public void onError(Status status) {
                System.out.println(status);
            }
        });

        PlaceAutocompleteFragment autocompleteFragment2 = (PlaceAutocompleteFragment)
              getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment2);
        autocompleteFragment2.setFilter(typeFilter);

        autocompleteFragment2.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                ApplicationHelper.setPlace2(place);
            }

            @Override
            public void onError(Status status) {
                System.out.println(status);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        if(requestCode == PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION){
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            } else {
            }
            return;
        }
    }

    private void startMap() {
        Intent i = new Intent(this, MapsActivity.class);
        startActivity(i);
    }
}
