package lg.area.mock.location;

import android.location.Location;

public class FakeCustomizeLocationSource implements ILocationSource {

	// List of location
	private static double[] location_ll;

	Location l;
	private int cpt;
	private String mockProvider;

	private final boolean loop;
	
	public FakeCustomizeLocationSource(String mockProvider, boolean loop, double[] location_ll) {
		this.loop = loop;
		this.cpt = 0;
		this.mockProvider = mockProvider;
		this.location_ll = location_ll;
		this.l = new Location(mockProvider);
	}
	
	public Location getNextLocation() {
		if (cpt + 1 > location_ll.length){
			if (!loop)
				return null; // no more location
			cpt = 0;
		}
		
		l.setLongitude(location_ll[cpt]);
		l.setLatitude(location_ll[cpt+1]);		
		l.setAltitude(0);		
		cpt += 2;
		
		return l;
	}

}
