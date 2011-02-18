package lg.area.mock.area;

import java.util.List;

import lg.area.mock.area.MockScenarioPlayer.Action;
import lg.area.mock.location.SimpleMockLocationProvider;


import android.content.Context;
import android.location.LocationManager;
import android.util.Log;

public class MockScenarioLauncher {
	
	SimpleMockLocationProvider mock;
	private final List<Action> _actions;
	
	// making a new instance, instantly launch scenario
	public MockScenarioLauncher(Context ctx, final List<Action> actions) {
		this._actions = actions;		
		
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
