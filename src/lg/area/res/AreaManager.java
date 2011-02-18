package lg.area.res;

import java.util.ArrayList;
import java.util.List;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;

import lg.area.mock.area.AreaFactories;
import lg.area.mock.area.AreaFactories.IMockAreaFactory;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.util.Log;


public class AreaManager
{
	protected static final String TAG = AreaManager.class.getName();
	public static final String INTENT_ACTION = "areamanager.event";
	
	protected static AreaManager instance;
	private Context ctx;
	private List<Area> larea;
	
	// Geometry
	private GeometryFactory geometryFactory = new GeometryFactory();
	private Point jtsPoint; // gps Point un JTS

	protected AreaManager(Context context)
	{
		ctx = context;
		IMockAreaFactory areaFactory =  new AreaFactories.SimpleAreaFactory();
		larea = areaFactory.createFakeArea();
	}
	public static AreaManager makeInstance(Context context)
	{		
		instance = new AreaManager(context);
		return instance;
	}
	
	// could be null
	public static AreaManager getInstance()
	{
		return instance;
	}
	
	private void broadcast(String name, String type){
		System.out.println("Sending broadcast " + name + "/" + type);
		Intent i = new Intent();		
		i.setAction(INTENT_ACTION);
		i.putExtra( "area", name);
		i.putExtra("type", type);		
		ctx.sendBroadcast(i);
	}
	
	public void fireAreaChange(Area a, int event){
		this.broadcast(a.getName(), "event:" + event);
	} 		
	
	public void fireAreaVisited(Area a){
		this.broadcast(a.getName(), "VISITED");
	}
	
	public void fireAreaUnvisited(Area a){
		this.broadcast(a.getName(), "NOTVISITED");
	}
	
	public void onLocationChanged(Location location) {
		Coordinate coord = new Coordinate(location.getLongitude(), location.getLatitude()); // x,y
		jtsPoint = geometryFactory.createPoint(coord);
		for (Area a : larea) {
			if(jtsPoint.intersects(a.geom)){
				System.out.println("AreaManager intersection !" + a.toString() + "/" + location);
				fireAreaVisited(a);
			}
		}
	}

}
