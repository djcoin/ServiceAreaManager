package lg.area;

import java.util.ArrayList;
import java.util.List;

import org.libertyguide.SoundServiceClient;

import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import lg.area.mock.AreaFactories;
import lg.area.mock.MockScenarioPlayer;
import lg.area.mock.AreaFactories.IMockAreaFactory;
import lg.area.mock.MockScenarioPlayer.Action;
import lg.area.res.Area;
import lg.area.res.AreaManager;

public class Main extends Activity {

	private Area area1, area2;
	private AreaManager am;
	
	private boolean mockArea = true;
	private boolean mockLocation = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);		
		am = AreaManager.makeInstance(this);		
		
		IMockAreaFactory areaFactory =  new AreaFactories.SimpleAreaFactory();
		
		if(mockLocation){
			
		}
		
		// launch the mocking of a complete scenario
		if (mockArea)				
			new MockLauncher(this, createActions(am, areaFactory));
		
//		Intent i = new Intent(this, SoundServiceClient.class);
//    	startActivity(i);
	}
	
	private List<Action> createActions(AreaManager am, IMockAreaFactory areaFactory){
		List<Action> l = new ArrayList<Action>();
		
		List<Area> fakeA = areaFactory.createFakeArea();
		for (Area area : fakeA) {
			l.add(new MockScenarioPlayer.AreaVisitedAction(am, area));
			l.add(new MockScenarioPlayer.AreaUnvisitedAction(am, area));
		}
		
		return l;
	}
	
}
