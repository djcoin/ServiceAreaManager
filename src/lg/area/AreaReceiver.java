package lg.area;

import lg.area.res.AreaManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class AreaReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction() == AreaManager.INTENT_ACTION){
			System.out.println("I received your intent my lord!");
			Bundle b = intent.getExtras();
			String area_name = b.getString("area");
			String type = b.getString("type");			
			System.out.println("Extra stuff: "  +area_name + "/" + type);
		}
	}

}

//<receiver android:name=".IncomingReceiver" android:enabled="true">
//	<intent-filter>
//		<action android:name=""></action>
//	</intent-filter>
//</receiver>