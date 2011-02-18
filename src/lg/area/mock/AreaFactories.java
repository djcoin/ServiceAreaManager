package lg.area.mock;

import java.util.ArrayList;
import java.util.List;

import lg.area.res.Area;

public class AreaFactories {

	public interface IMockAreaFactory {
		List<Area> createFakeArea();
		double[] getSpecialPoint();
	}
	
	public class SimpleAreaFactory implements IMockAreaFactory{
		Area tmp;
		@Override
		public List<Area> createFakeArea() {
			List<Area> la = new ArrayList<Area>();
			// String sfrombo_poi = "POINT(-1.6217225789444 47.210863164074)";
			String sfrombo_content = "POLYGON((-1.6218191384689 47.210972485787,-1.6218566893951 47.210837655642,-1.6216421126738 47.210793926873,-1.6215509175674 47.21097612984,-1.6218191384689 47.210972485787))";			
			String sfrombo_ambient = "POLYGON((-1.6219961642638 47.210997994154,-1.6221302747145 47.210684604792,-1.6214436292068 47.210775706541,-1.6215026378051 47.211092739411,-1.6219961642638 47.210997994154))";

			tmp = new Area("My area 1", sfrombo_content);
			la.add(tmp);
			tmp =new Area("My area 2", sfrombo_ambient);
			la.add(tmp);
			return la;
		}
		// this points contains all
		@Override
		public double[] getSpecialPoint() {	
			double[] d = new double[]{-1.6217225789444, 47.210863164074};
			return d;
		}
		
		
	}
	
}
