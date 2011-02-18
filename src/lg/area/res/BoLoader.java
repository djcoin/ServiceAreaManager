package lg.area.res;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.Map.Entry;

import android.content.Context;


// make a singleton out of this ?
public class BoLoader {
	
	private Context ctx;

	static final HashMap<String, Factory>  hm = new LinkedHashMap<String, Factory>();

	private final ResourceHandler ressourceHandler;
	
	{
		hm.put("area/poi.data",new CreatePOI()); 
		hm.put("area/content.data", new CreateContent());
		hm.put("area/ambient.data", new CreateAmbient());
	}
	
	public BoLoader(Context context, ResourceHandler rh) {
		ctx = context;
		this.ressourceHandler = rh;
	}	
	
	public void load(){		
		InputStream is = null;
		BufferedReader reader;
		Factory factory;
		Entry<String, Factory> next;
		
		Set<Entry<String, Factory>> entries = hm.entrySet();
		Iterator<Entry<String, Factory>> it = entries.iterator();
		
			while (it.hasNext()){
				next = it.next();
				factory = next.getValue();
				try {
					is = ctx.getAssets().open(next.getKey());		
					reader = new BufferedReader(new InputStreamReader(is));
		
					String line = null;
					String[] splitted = null;
					
					while ((line = reader.readLine()) != null) {
						splitted = line.split(":");
						factory.createFromLine(ressourceHandler, splitted[0], splitted[1]);
					}
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						is.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}		
	}
	
	interface Factory {
		void createFromLine(ResourceHandler rh, String name, String wkt);
	}
	
	class CreatePOI implements Factory{
		public void createFromLine(ResourceHandler rh, String name, String wkt) {
			rh.addPoi(new Poi(name, wkt));
		}
	}
	class CreateContent implements Factory{
		public void createFromLine(ResourceHandler rh,String name, String wkt) {
			rh.addContent(new Area("Content:" + name, wkt));
		}
	}
	class CreateAmbient implements Factory{
		
		public void createFromLine(ResourceHandler rh, String name, String wkt) {
			rh.addAmbient(new Area("Ambient:" + name, wkt));
		}
	}	
	
}
