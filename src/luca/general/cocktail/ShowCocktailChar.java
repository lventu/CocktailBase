/* This Class is used to display the description of the selected cocktail
 * The cocktail name is passed with the extra parameter inside the intent start
 * This activity is called by the Start activity and is stopped by a Click on the textview
 * 
 * Author: Luca Venturini
 */


package luca.general.cocktail;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TableRow.LayoutParams;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class ShowCocktailChar extends Activity{
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.showcocktaildescr);
		
		MyDatabase my = new MyDatabase(getApplicationContext());
		my.open();
		String campoDaVis;
		// Table
		TableLayout tab1 = (TableLayout) findViewById(R.id.tableLayoutCaratt);
		//Set listener: if click on a text view we'll exit
		tab1.setOnClickListener(new exits());
		
		// ricava l'intent che ha generato l'activity, ricava il parametro passato e usa per richiedere info al DB
		Intent str = getIntent();
		String a = str.getStringExtra("cocktailName");		
		Cursor descriptor = my.fetchCocktail(a);
		
		String[] col = descriptor.getColumnNames();
		descriptor.moveToFirst(); // Necessary to let the program works
		// Define the name of the cocktail view
		TextView nomeFin = (TextView) findViewById(R.id.nome_del_cocktail);	
		nomeFin.setText(descriptor.getString(1));
		
		for(int i=2;i<col.length-1;i++){
			// Row structure: Database Field name text view
			TextView campo = new TextView(this);
			campo.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT, 9));
			campo.setTextColor(Color.WHITE);
			campo.setTextSize(15);
			campo.setGravity(Gravity.CENTER);
			campo.setText("");
			campo.setBackgroundColor(R.color.blue);
			// Row structure: Database Value text view
			TextView valore = new TextView(this);
			valore.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, 10));
			valore.setTextColor(Color.WHITE);
			valore.setTextSize(15);
			valore.setGravity(Gravity.LEFT);
			valore.setText("");
			valore.setBackgroundColor(R.color.blue);

			// Viewer 		
			TableRow riga = new TableRow(this); 
			riga.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
			riga.setBackgroundColor(R.color.blue);
			
			campoDaVis = descriptor.getString(i);			
			if((i==7)||(i==9)){				
				campoDaVis = campoDaVis.replace("; ", "\n");
			}
			if((campoDaVis!=null)&&(!campoDaVis.equals("NONE")&&(!campoDaVis.equals("")))){
				campo.append(col[i]);
				valore.append(new StringBuffer().append(campoDaVis).toString());
				// Adding widget to the row
				riga.addView(campo);
				riga.addView(valore);
				// Adding row to the table
				tab1.addView(riga);
				//Log.w("LUCA", "ok "+i);
			}
		}
		// Finally the resources can be released
		descriptor.close();
		my.close();
	}
	
	
	private class exits implements View.OnClickListener{

		@Override
		public void onClick(View v) {
			finish();			
		}
		
	}
	
	@Override
	public void onPause(){
		super.onPause();
	}

}