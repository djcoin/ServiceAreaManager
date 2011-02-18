package lg.area;



import lg.area.mock.location.SimpleMockLocationProvider;
import lg.area.res.AreaManager;
import android.app.Activity;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

// should register for every GPS input
public class AreaService extends Service implements LocationListener {

	private final IBinder mBinder = new LocalBinder();
	private AreaManager areaManager;
	private LocationManager locationManager;
	
	
	@Override
	public void onCreate() {
		super.onCreate();
		areaManager = AreaManager.makeInstance(this);
		locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		locationManager.requestLocationUpdates(
				SimpleMockLocationProvider.mockLocationProvider, 0, 0, this);
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}	
	/**
     * Class for clients to access.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with
     * IPC.
     */
    public class LocalBinder extends Binder {
    	AreaManager getService() {
            return areaManager;
        }
    }
    
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("LocalService", "Received start id " + startId + ": " + intent);
        return START_STICKY;
    }
    
    @Override
    public void onDestroy() {
    }

    
    
	@Override
	public void onLocationChanged(Location location) {
		areaManager.onLocationChanged(location);
	}

	@Override
	public void onProviderDisabled(String provider) {
	}

	@Override
	public void onProviderEnabled(String provider) {
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
	}
}