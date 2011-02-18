package lg.area.geom;

import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;

public class WktSamples {
	private final static String spoly1 = "POLYGON((-105.32812714577 57.32813000679, -121.50000214577 30.60938000679, -84.937502145767 16.54688000679, -81.421877145767 51.70313000679, -105.32812714577 57.32813000679))";
	private final static String spoly2 = "POLYGON((-61.734377145767 46.07813000679, -77.906252145767 26.39063000679, -32.203127145767 0.37500500679016, -15.328127145767 33.42188000679, -61.734377145767 46.07813000679))";
	
	private final static String spoly_contains12 = "POLYGON((-91.476566791534 74.22657251358, -152.64844179153 1.8046975135802, -26.085941791534 -9.4453024864198, -9.2109417915342 39.77344751358, -91.476566791534 74.22657251358))";
	private final static String spoly_contains2 = "POLYGON((-54.210941791534 77.74219751358, -156.16406679153 -17.17967748642, 41.414058208466 -23.50780248642, 36.492183208466 -1.0078024864198, -54.210941791534 77.74219751358))";
	
	private final static String spoint1 = "POINT(-96.890627145767 38.34375500679)";
	private final static String spoint2 = "POINT(-63.140627145767 35.53125500679)";
		
	public static Point point1, point2;

	public static Polygon polygon1, polygon2, poly_contains12, poly_contains2;
	
	private final static String sfrombo_poi = "POINT(-1.6217225789444 47.210863164074)";
	private static Point frombo_poi;
	
	private final static String sfrombo_content = "POLYGON((-1.6218191384689 47.210972485787,-1.6218566893951 47.210837655642,-1.6216421126738 47.210793926873,-1.6215509175674 47.21097612984,-1.6218191384689 47.210972485787))";
	public static Polygon frombo_content;
	
	private final static String sfrombo_ambient = "POLYGON((-1.6219961642638 47.210997994154,-1.6221302747145 47.210684604792,-1.6214436292068 47.210775706541,-1.6215026378051 47.211092739411,-1.6219961642638 47.210997994154))";
	public static Polygon frombo_ambient;
	
	private static WKTReader reader;
	// private GeometryFactory geometryFactory;
	static {		
		reader = new WKTReader();
		try {
			// .contains .intersects
			// "polycontains 12 should contain 1 and 2 "
			// "polycontains 2 should intersect 1 and 2 "
			// "polycontains 2 should NOT contains 1 and 2 "(only2)
			
			point1 = (Point) reader.read(spoint1);
			point2 = (Point) reader.read(spoint2);
			polygon1 = (Polygon) reader.read(spoly1);
			polygon2 = (Polygon) reader.read(spoly2);
			poly_contains12 = (Polygon) reader.read(spoly_contains12);
			poly_contains2 = (Polygon) reader.read(spoly_contains2);

			// frombo 
			frombo_poi = (Point) reader.read(sfrombo_poi);
			frombo_content = (Polygon) reader.read(sfrombo_content);
			frombo_ambient = (Polygon) reader.read(sfrombo_ambient);
			
			// -1.6217225789444 47.210863164074 for the point
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
