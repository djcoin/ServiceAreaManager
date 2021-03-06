package lg.area.res;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;

@Element
public class Area implements Geom
{
	@Attribute
	protected String name;

	@Attribute
	protected String style; // we dont use this

	@Attribute
	protected String wkt;

	// Geometry features
	private static WKTReader reader = new WKTReader();
	protected Polygon geom;
	
	public Area(String name, String wkt) {
		this.name = name;
		this.wkt = wkt;
		createGeom();
	}
	
	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getStyle()
	{
		return style;
	}

	public void setStyle(String style)
	{
		this.style = style;
	}

	public String getWkt()
	{
		return wkt;
	}

	public void setWkt(String wkt)
	{
		this.wkt = wkt;
	}

	public void createGeom()
	{
		try
		{
			geom = (Polygon) reader.read(wkt);
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public Geometry getGeom() {		
		return geom;
	}

}
