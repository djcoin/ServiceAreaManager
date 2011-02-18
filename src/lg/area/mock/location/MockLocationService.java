package lg.area.mock.location;

import lg.area.mock.location.FakeLocationFactories.FakeCustomLocationFactory;
import lg.area.res.ResourceHandler;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.IBinder;

// http://developer.android.com/reference/android/app/Service.html#ServiceLifecycle
public class MockLocationService extends Service {
	
	SimpleMockLocationProvider mock;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}	
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// FakeLocationFactories.ILocationFactory fact
		mock = SimpleMockLocationProvider.makeInstance((LocationManager) this.getSystemService(Context.LOCATION_SERVICE));
		
		// The resourceHandler has to be populated before !!!
		FakeCustomLocationFactory locationFactory = new FakeLocationFactories.FakeCustomLocationFactory();		
		locationFactory.setData(ResourceHandler.getInstance().poiExtractLocationFrom());
		
		// start playing fake GPS every two seconds AND LOOP
		mock.start(locationFactory.createLocationSource(SimpleMockLocationProvider.mockLocationProvider, true));
		
		return START_STICKY;
	}
	
}
