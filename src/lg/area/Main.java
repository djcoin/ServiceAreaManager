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
import lg.area.mock.location.FakeLocationFactories.FakeCustomLocationFactory;
import lg.area.mock.location.FakeLocationFactories.ILocationFactory;
import lg.area.res.Area;
import lg.area.res.AreaManager;
import lg.area.res.BoLoader;
import lg.area.res.ResourceHandler;

public class Main extends Activity {

	private Area area1, area2;
	private AreaManager am;
	
	private boolean mockArea = false;
	private boolean mockLocation = true;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		ResourceHandler rh = ResourceHandler.getInstance();
		new BoLoader(this, rh).load();	
		
		IMockAreaFactory areaFactory =  new AreaFactories.SimpleAreaFactory();
		// am = AreaManager.makeInstance(this, areaFactory);
		// WARNING WITH AreaService !! It also need areaManager. Used to create it!		
		am = AreaManager.makeInstance(this, null);
		
		// new Intent AreaService.class => launch this service that will get AreaManager
		this.registerLocationReceiver();
		this.registerBroadcastReceiver();
		
		
		FakeCustomLocationFactory locationFactory = new FakeLocationFactories.FakeCustomLocationFactory();
		// double[] d = areaFactory.getSpecialPoint();
		// locationFactory.setData(new double[]{d[0], d[1]});		
		locationFactory.setData(rh.poiExtractLocationFrom());
		
		if(mockLocation){			
			new MockLocationLauncher(this, locationFactory); // ILocationFactory
		}
		
		// launch the mocking of a complete scenario
		if (mockArea){
			new MockScenarioLauncher(this, createActions(am, areaFactory));
		}

	}
	
	private void registerLocationReceiver() {
		Intent intentService = new Intent(AreaService.class.getName());
		startService(intentService);
	}

	private void registerBroadcastReceiver(){
		BroadcastReceiver receiver = new BroadcastReceiver() {
			//if (intent.getAction() == AreaManager.INTENT_ACTION){
			public void onReceive(Context context, Intent intent) {
					System.out.println("I received your intent my lord!");
					Bundle b = intent.getExtras();
					String area_name = b.getString("area");
					String type = b.getString("type");			
					System.out.println("Extra stuff: "  + area_name + "/" + type);
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
