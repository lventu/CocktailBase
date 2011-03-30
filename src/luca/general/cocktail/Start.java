/*
 * The class which creates the mine custom list view of the cocktail 
 * 
 * Author: Luca Venturini
 */

package luca.general.cocktail;

import luca.general.cocktail.MyDatabase;
import luca.general.cocktail.MyDatabase.CocktailMetaData;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
//import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
//import android.widget.Toast;
import android.widget.ListView;


public class Start extends Activity {
	// Global listview definition permit all the function to access to this resource
	ListView listView;
	// This is no the direct database, but it's the reference to a class that manages it
	// So, we don't confuse it!
	MyDatabase db;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// Father class method call
		super.onCreate(savedInstanceState);
		// Set the main layout from resource
		setContentView(R.layout.main);		
		//setContentView(LayoutInflater.from(getParent()).inflate(R.layout.main, null));
		
		// Create the List instance
		listView = (ListView) findViewById(R.id.cocktailList);
		
		db = new MyDatabase(getApplicationContext());
		db.open();
		 
		// Definition of custom item, creations of test item		
		Cursor dati = db.fetchCocktail("all");
		CustomItem[] cocktailData = createItems(dati);
		dati.close();
		db.close();
		// Adapter definition based on our custom items definition
		ArrayAdapter<CustomItem> arrayAdapter = new ArrayAdapter<CustomItem>(this, R.layout.riga, R.id.nam_ck, cocktailData){
			// All the items visualized has the same layout, definition of the main function getView
			public View getView(int position, View convertView, ViewGroup parent) {
				// I have to create this final clone of the variable position because the method onClick
				// attached to the listener can't have access to outer NON-final variables 
				// It's a brutal Code waiting a better solution
				final int posFin = position;
				// our layout reference
				ViewHolder viewHolder;
				if((convertView==null)){
					// in this section we are going to set-up the default view that all other items in
					// this list will use to display their data
					LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
					convertView = inflater.inflate(R.layout.riga, null);
					viewHolder = new ViewHolder();
					viewHolder.cocktailNameView = (TextView)convertView.findViewById(R.id.nam_ck);
					viewHolder.cocktailImgView = (ImageView)convertView.findViewById(R.id.img_ck);
					// Associate the tags from the View holder created to the reuse convertView
					convertView.setTag(viewHolder);
					// Listener to check what of the relative View is selected and retrieve informations
					convertView.setOnClickListener(new myListener(posFin));
					//Log.i("LUCA", "OK1 "+convertView.toString());
				}else{
					viewHolder = (ViewHolder)convertView.getTag();
					//Log.i("LUCA", i+" OK2 "+posFin+position+convertView.toString());
				}
				CustomItem item = getItem(position); 
				viewHolder.cocktailNameView.setText(item.nam_ck); 
				viewHolder.cocktailImgView.setImageResource(item.img_ck);
				//Log.w("view", i+" nome "+item.nam_ck);
				return convertView;
		}
	};
		listView.setAdapter(arrayAdapter);
		listView.setTextFilterEnabled(true);
		//Log.i("LUCA", "OK1");
	}
	
// Custom item definition
	private static class CustomItem {
		public String nam_ck;
		public int img_ck;
	}
	
// The basic structure of my list row, this will simplify and speed-up the running code 
	private static class ViewHolder {
		public TextView cocktailNameView;
		public ImageView cocktailImgView;
	}
	
// LOAD FUNCTION - communication part: Database to CustomItem array
	private CustomItem[] createItems(Cursor dati) {
		// Creates the structure of CustomItem elements
		CustomItem[] items = new CustomItem[dati.getCount()];
		if(dati.moveToFirst()){
        	do{
        		// Create the single position object
        		items[dati.getPosition()] = new CustomItem();
        		// Get the cocktail name from the database to the customitem array
        		items[dati.getPosition()].nam_ck = dati.getString(dati.getColumnIndex(CocktailMetaData.COCKTAIL_NAME_KEY));
        		// Get the resources identifier to the image of the cocktail, that is the last field of database
        		items[dati.getPosition()].img_ck= getResources().getIdentifier(dati.getString(dati.getColumnIndex(CocktailMetaData.COCKTAIL_IMAGE_KEY)), "drawable", "luca.general.cocktail");
        		//Log.i("LUCA", "OK! "+items[dati.getPosition()].img_ck);
        	}while(dati.moveToNext());
        }
		dati.close();
        //Log.i("luca", "OK!");
		return items;
	}
	
	// Listener class implementation
	private class myListener implements View.OnClickListener {
		private int posFin;
		// custom class constructor
		public myListener(int posFin) {
			this.posFin = posFin;
		}

		@Override
		public void onClick(View v) {
				CustomItem IT = (CustomItem) listView.getItemAtPosition(posFin);
				Intent intent = new Intent().setClass(getApplicationContext(), ShowCocktailChar.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				intent.putExtra("cocktailName", IT.nam_ck);
				//Log.e("LUCA", "ls "+v.toString());
				// Trying Toast: all OK
				//Toast.makeText(getBaseContext(), IT.nam_ck, 15).show();
				// Set the intent to the ShowCocktailChar activity to view the cocktail data
				startActivity(intent);		
		}		
	}
	
//	@Override
//	public void onDestroy(){
//		super.onDestroy();
//		Log.i("LUCA","onDestroy() Start");
//		finish();
//	}
}