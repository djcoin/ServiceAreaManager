package lg.area.geom;

import java.util.ArrayList;
import java.util.List;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class LocationTest extends Activity {
    private Point jtsPoint;
	private List<Pair<Polygon, String>> polyst;
	private TextView textView;
	private GeometryFactory geometryFactory;
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        String locationProvider = LocationManager.GPS_PROVIDER;
        
        textView = (TextView) findViewById(R.id.mytext); // used at the bottom
        
        geometryFactory = new GeometryFactory();
        this.createZoneEvent();
        
        checkLocation(locationManager.getLastKnownLocation(locationProvider));
        
        
        // Define a listener that responds to location updates
        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
              // Called when a new location is found by the network location provider.
              LocationTest.this.checkLocation(location);
            }
			public void onStatusChanged(String provider, int status, Bundle extras) {}

            public void onProviderEnabled(String provider) {}

            public void onProviderDisabled(String provider) {}
          };

        // Register the listener with the Location Manager to receive location updates
        locationManager.requestLocationUpdates(locationProvider, 0, 0, locationListener);
        
    }

	private void createZoneEvent() {	
		polyst = new ArrayList<Pair<Polygon, String>>();
		polyst.add(new Pair<Polygon, String>(WktSamples.polygon1, "Vous venez de rentrer dans la zone 1!!"));
		polyst.add(new Pair<Polygon, String>(WktSamples.polygon2, "Vous venez de rentrer dans la zone 2!!"));
		polyst.add(new Pair<Polygon, String>(WktSamples.poly_contains12, "Vous venez de rentrer dans la zone mere de 1 et 2!!"));
		
		// telnet localhost 5554
		// geo fix -1.6217225789444 47.210863164074
		// From BO stuff: // -1.6217225789444 47.210863164074 for the point
		polyst.add(new Pair<Polygon, String>(WktSamples.frombo_ambient, "Ambient !!!"));
		polyst.add(new Pair<Polygon, String>(WktSamples.frombo_content, "Content !!!"));
	}

	protected void checkLocation(Location location) {
		if (location == null){
			Log.d("location", "Location received is null");
			return;
		}
		Log.d("location", "New location..." + location);
		Coordinate coord = new Coordinate(location.getLongitude(), location.getLatitude()); // x,y
		jtsPoint = geometryFactory.createPoint( coord );
				
		for (Pair<Polygon, String> p : this.polyst) {
			if (p.first.contains(jtsPoint)) {
				Log.d("location", "Found a match!");
				textView.setText(textView.getText() + "\n" + p.second);
			}
		}

	}
}