package mmmi.sdu.dk.gamification.routing;

import android.graphics.Color;
import android.graphics.Point;
import android.os.AsyncTask;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import mmmi.sdu.dk.gamification.R;
import mmmi.sdu.dk.gamification.utils.JSONParser;

public class GetDirectionsAsync extends AsyncTask<LatLng, Void, List<LatLng>> {
      GoogleMap mMap;
      Polyline line;
      List<MarkerOptions> items;

      JSONParser jsonParser;
      String DIRECTIONS_URL = "https://maps.googleapis.com/maps/api/directions/json";


      public GetDirectionsAsync(GoogleMap map) {
            this.mMap = map;
      }

      @Override
      protected void onPreExecute() {
            super.onPreExecute();
      }

      @Override
      protected List<LatLng> doInBackground(LatLng... params) {
            items = new ArrayList();
            LatLng start = params[0];
            LatLng end = params[1];

            HashMap<String, String> points = new HashMap<>();
            points.put("origin", start.latitude + "," + start.longitude);
            points.put("destination", end.latitude + "," + end.longitude);

            jsonParser = new JSONParser();

            JSONObject obj = jsonParser.makeHttpRequest(DIRECTIONS_URL, "GET", points, true);

            if (obj == null) return null;

            try {
                  List<LatLng> list = null;

                  JSONArray routeArray = obj.getJSONArray("routes");
                  JSONObject routes = routeArray.getJSONObject(0);
                  JSONObject overviewPolylines = routes.getJSONObject("overview_polyline");
                  String encodedString = overviewPolylines.getString("points");
                  list = decodePoly(encodedString);

                  return list;

            } catch (JSONException e) {
                  e.printStackTrace();
            }

            return null;
      }

      @Override
      protected void onPostExecute(List<LatLng> pointsList) {

            if (pointsList == null) return;

            if (line != null){
                  line.remove();
            }

            PolylineOptions options = new PolylineOptions().width(5).color(Color.MAGENTA).geodesic(true);
            for (int i = 0; i < pointsList.size(); i++) {
                  LatLng point = pointsList.get(i);
                  options.add(point);
            }
            line = mMap.addPolyline(options);
            for(MarkerOptions m : items) {
                  m.icon(BitmapDescriptorFactory.fromResource(R.drawable.pumpkin_icon));
                  mMap.addMarker(m);
            }

      }

      private List<LatLng> decodePoly(String encoded) {

            List<LatLng> poly = new ArrayList<LatLng>();
            int index = 0, len = encoded.length();
            int lat = 0, lng = 0;

            while (index < len) {
                  int b, shift = 0, result = 0;
                  do {
                        b = encoded.charAt(index++) - 63;
                        result |= (b & 0x1f) << shift;
                        shift += 5;
                  } while (b >= 0x20);
                  int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
                  lat += dlat;

                  shift = 0;
                  result = 0;
                  do {
                        b = encoded.charAt(index++) - 63;
                        result |= (b & 0x1f) << shift;
                        shift += 5;
                  } while (b >= 0x20);
                  int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
                  lng += dlng;

                  LatLng p = new LatLng((((double) lat / 1E5)),
                        (((double) lng / 1E5)));
                  Random rand = new Random();

                  //temporary one in ten random chance for collectible
                  if(rand.nextInt(10) == 5) {
                        MarkerOptions m = new MarkerOptions();
                        m.position(p);
                        m.title("collectible!");
                        items.add(m);
                  }
                  poly.add(p);
            }
            return poly;
      }
}