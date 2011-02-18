package lg.area.res;

import java.util.ArrayList;
import java.util.List;

public class ResourceHandler {

	private static ResourceHandler instance;
	List<Poi> lpoi = new ArrayList<Poi>();
	List<Area> lcontent = new ArrayList<Area>();
	List<Area> lambient = new ArrayList<Area>();
	
	private ResourceHandler() {
	}
	
	public static ResourceHandler getInstance(){
		if (instance == null)
			instance = new ResourceHandler();
		return instance;
	}
	
	public void addPoi(Poi p){
		this.lpoi.add(p);
	}
	public void addContent(Area a){
		this.lcontent.add(a);
	}
	public void addAmbient(Area a){
		this.lambient.add(a);
	}
	
	public List<Poi> getPoi(){
		return  lpoi;
	}
	public List<Area> getContent(){
		return lcontent;
	}
	public List<Area> getAmbient(){
		return lambient;
	}
	
	public double[] poiExtractLocationFrom (){
		double[] d = new double[lpoi.size()*2];
		int cpt = 0;
		for (Poi poi : lpoi) {
			d[cpt++] = poi.geom.getX();
			d[cpt++] = poi.geom.getY();
		}
		return d;
	}
}
