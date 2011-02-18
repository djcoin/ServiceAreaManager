package lg.area;

import java.util.ArrayList;
import java.util.List;

import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import lg.area.mock.area.AreaFactories;
import lg.area.mock.area.MockScenarioLauncher;
import lg.area.mock.area.MockScenarioPlayer;
import lg.area.mock.area.AreaFactories.IMockAreaFactory;
import lg.area.mock.area.MockScenarioPlayer.Action;
import lg.area.mock.location.FakeLocationFactories;
import lg.area.mock.location.MockLocationLauncher;
import lg.area.mock.location.MockLocationService;
import lg.area.mock.location.FakeLocationFactories.FakeCustomLocationFactory;
import lg.area.mock.location.FakeLocationFactories.ILocationFactory;
import lg.area.res.Area;
import lg.area.res.AreaManager;
import lg.area.res.BoLoader;
import lg.area.res.ResourceHandler;

/**
 * 
 * This class will kill itself immediatly if you wish to.
 * Otherwise, it will register an onZone listener that will broadcast. // make clearer
 * Additionnaly it can play mocked locations.
 * It can also directly play onZone events
 * 
 * BIG TODO: 
 * - launch it as a service - does not need to be an activity
 * - propose an onNewIntent for this activity
 * 
 * @author sim
 *
 */
public class Main extends Activity {

	private Area area1, area2;
	private AreaManager am;
	
	public boolean killapp = true;
	
	private boolean mockLocationAsService = true;
	private boolean mockLocationSimple = false;
	
	private boolean dummyBroadcastReceiver = true;
	
	// directly mock AreaVisited etc. stuff
	private boolean mockArea = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		// must be done first!
		ResourceHandler rh = ResourceHandler.getInstance();
		new BoLoader(this, rh).load();	
		
		IMockAreaFactory areaFactory =  new AreaFactories.SimpleAreaFactory();
		// am = AreaManager.makeInstance(this, areaFactory);
		// double[] d = areaFactory.getSpecialPoint();
		// locationFactory.setData(new double[]{d[0], d[1]});	
		// WARNING WITH AreaService !! It also need areaManager. Used to create it!		
		am = AreaManager.makeInstance(this, null);
		
		// new Intent AreaService.class => launch this service that will get AreaManager
		// IN ANY CASE LAUNCH THIS :
		// it will see determine from a GPS point which zone is involved
		this.registerLocationReceiver();
		
		if (dummyBroadcastReceiver)
			this.registerBroadcastReceiver();
		

		if(mockLocationAsService){
			System.out.println("Name of intent " + MockLocationService.class.getName());
			Intent intentService = new Intent(MockLocationService.class.getName());
			startService(intentService);
		}
		
		if(mockLocationSimple){
			FakeCustomLocationFactory locationFactory = new FakeLocationFactories.FakeCustomLocationFactory();		
			locationFactory.setData(rh.poiExtractLocationFrom());
			new MockLocationLauncher(this, locationFactory); // ILocationFactory
		}
				
		// launch the mocking of a complete scenario
		if (mockArea){
			new MockScenarioLauncher(this, createActions(am, areaFactory));
		}
		
		if (killapp)
			this.finish();
	}

// TODO
//	@Override
//	protected void onNewIntent(Intent intent) {
//		super.onNewIntent(intent);
//		System.out.println("Called from an intent !");
//		System.out.println("Intent:" + intent.toString() + "Action:" + intent.getAction());
//	}
	
	
	private void registerLocationReceiver() {
		Intent intentService = new Intent(AreaService.class.getName());
		startService(intentService);
	}

	private void registerBroadcastReceiver(){
		BroadcastReceiver receiver = new BroadcastReceiver() {
			//if (intent.getAction() == AreaManager.INTENT_ACTION){
			public void onReceive(Context context, Intent intent) {					
					Bundle b = intent.getExtras();
					String area_name = b.getString("area");
					String type = b.getString("type");			
					System.out.println("Receiving AreaManager Intent: " + area_name );
					System.out.println("------------------------------------");
				}
		};
		registerReceiver(receiver, new IntentFilter(AreaManager.INTENT_ACTION));
	}
	
	
	
	private List<Action> createActions(AreaManager am, IMockAreaFactory areaFactory){
		List<Area> fakeA = areaFactory.createFakeArea();
		
		List<Action> l = new ArrayList<Action>();
		for (Area area : fakeA) {
			l.add(new MockScenarioPlayer.AreaVisitedAction(am, area));
			l.add(new MockScenarioPlayer.AreaUnvisitedAction(am, area));
		}		
		return l;
	}
	
}
