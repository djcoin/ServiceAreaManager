package lg.area.mock.location;

import java.util.List;

import lg.area.mock.area.MockScenarioPlayer.Action;


import android.content.Context;
import android.location.LocationManager;
import android.util.Log;

public class MockLocationLauncher {
	
	SimpleMockLocationProvider mock;
	
	// making a new instance, instantly launch scenario
	public MockLocationLauncher(Context ctx, FakeLocationFactories.ILocationFactory fact) {;
		mock = SimpleMockLocationProvider.makeInstance((LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE));
		// start playing fake GPS every two seconds AND LOOP
		mock.start(fact.createLocationSource(SimpleMockLocationProvider.mockLocationProvider, true));
	}	
}
