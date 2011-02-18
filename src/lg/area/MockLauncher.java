package lg.area;

import java.util.List;

import lg.area.mock.FakeLocationSource;
import lg.area.mock.MockScenarioPlayer;
import lg.area.mock.SimpleMockLocationProvider;
import lg.area.mock.MockScenarioPlayer.Action;

import android.content.Context;
import android.location.LocationManager;
import android.util.Log;

public class MockLauncher {
	
	SimpleMockLocationProvider mock;
	private final List<Action> _actions;
	
	// making a new instance, instantly launch scenario
	public MockLauncher(Context ctx, final List<Action> actions) {
		this._actions = actions;
		mock = SimpleMockLocationProvider.makeInstance((LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE));
		 // start playing fake GPS every two seconds AND LOOP
		mock.start(new FakeLocationSource(SimpleMockLocationProvider.mockLocationProvider, true));
		
		Thread t = new Thread(
				new Runnable() {
					public void run() {
						Log.d("Thread Scenario", "THREAD WAITING 5 SECS");
						try {
							Thread.sleep(5000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						} // wait 5 seconds
						Log.d("Thread Scenario", "THREAD WAITED 5 SEC");
						// does loop on this !
						MockScenarioPlayer.getInstance().play(_actions, true);					
					}
				}
		);
		t.start();
	}	
}
