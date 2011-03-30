/* This activity help our choice if we're not sure on what we drink 
 * The choice is done within a timeframe
 * 
 * Author: Luca Venturini
 * */

package luca.general.cocktail;

import java.text.SimpleDateFormat;
import java.util.Date;
import luca.general.cocktail.MyDatabase.CocktailMetaTime;
import luca.general.cocktail.randShow;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HelpMyChoice extends Activity{
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cons_lay);
		
		Button scg = (Button) findViewById(R.id.scegli);
		TextView hr = (TextView) findViewById(R.id.hour);
		
		scg.setOnClickListener(new aiutami(hr));
	}
	
	private class aiutami implements View.OnClickListener{
		private TextView hr;
		public aiutami(TextView hr) {
			this.hr=hr;
		}

		@Override
		public void onClick(View v) { 
			String p = new SimpleDateFormat("HH:mm").format(new Date());
			String o = new SimpleDateFormat("HH").format(new Date());
			String queryFascia = fascia(o);
			hr.setText(new StringBuffer().append("\n\nSono le ").append(p));
			hr.append(new StringBuffer().append(", sei nella fascia ").append(queryFascia));
			// calling the randShow class, it will return the intent with the extra set to a random cocktail
			Intent goGoGo = new randShow(queryFascia,getApplicationContext()).rand();
			if(goGoGo!=null) startActivity(goGoGo);
		}
		
		private String fascia(String ora){
			// da ricordare è il fatto che la fascia tutte le ore vale appunto per tutte le ore
			// non ci devono essere vincoli
			String cat=null;
			int hh = Integer.valueOf(ora).intValue();
			switch(hh){
			case 16:
			case 17:
			case 18:
			case 19:
				cat = CocktailMetaTime.PRE;
				break;			
			case 20:
			case 21:
			case 22:
				cat = CocktailMetaTime.AFTER;
				break;			
			case 23:
			case 0:
			case 1:
			case 2:
			case 3:
			case 4:
				cat = CocktailMetaTime.LONG;
				break;
				
			default: cat = "fuori dal funzionamento";
			}
			return cat;
		}
		
	}
}
