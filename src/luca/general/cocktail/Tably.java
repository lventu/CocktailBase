/* This activity creates the main layout of application
 * The layout consist in two tabs
 * 
 * Author: Luca Venturini
 */

package luca.general.cocktail;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
//import android.util.Log;
import android.widget.TabHost;

public class Tably extends TabActivity{	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tablay);
		// Getting the tab host layout and its specification field
		Resources res = getResources();
		TabHost tabHost = getTabHost();
		TabHost.TabSpec spec;
		// Adding the first tab contains the Start activity, set the title and a related image 
		Intent intent = new Intent().setClass(this, Start.class);
		spec = tabHost.newTabSpec("tutti_ck").setIndicator("Cocktails", res.getDrawable(R.drawable.cocktails)).setContent(intent);
		tabHost.addTab(spec);
		// The second one table, its call help me Cocktail, but 
		intent = new Intent().setClass(this, HelpMyChoice.class);
		spec = tabHost.newTabSpec("help_me").setIndicator("Aiutami a Scegliere",res.getDrawable(R.drawable.help)).setContent(intent);
		tabHost.addTab(spec);
		
		tabHost.setCurrentTab(0);
	}
	
//	@Override
//	public void onDestroy(){
//		super.onDestroy();
//		Log.i("LUCA", "onDestroy() Tably");
//		finish();
//	}
}