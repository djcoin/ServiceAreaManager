package lg.area.res;

import java.util.ArrayList;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


public class AreaManager
{
	protected static final String TAG = AreaManager.class.getName();
	protected static final String ACTION = "areamanager.event";
	
	protected static AreaManager instance;
	private Context ctx;

	protected AreaManager(Context context)
	{
		ctx = context;
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
		Intent i = new Intent();		
		i.setAction(ACTION);
		i.putExtra( "string", name);
		i.putExtra("type", "ENTERED");		
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

}
