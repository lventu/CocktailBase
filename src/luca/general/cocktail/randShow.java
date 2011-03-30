/* Thi class takes the hour slot and returns an intent toward the show description's activity
 * The intent contains an Extra name parameter which has chosen casually inside a rage of possible cocktail
 * 
 * Author: Luca Venturini
 */
package luca.general.cocktail;

import java.util.Random;

import luca.general.cocktail.MyDatabase.CocktailMetaData;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;

public class randShow {
	String cat;
	Context ctx;
	
	public randShow(String cat, Context ctx){
		this.cat = cat;
		this.ctx = ctx;
	}
	
	public Intent rand(){
		MyDatabase temp = new MyDatabase(ctx);
		temp.open();
		Cursor Cc = temp.fetchPerCateg(cat);
		Log.i("LUCA", "ric1");
		if(Cc.getCount()==0) return null;
		Cc.moveToFirst();
		Log.i("LUCA", "ric2 "+Cc.getCount());
		temp.close();
		// Random number between 0 and cursor (on category) length
		int rnd = new Random().nextInt(Cc.getCount());
		Log.i("LUCA", "ric3 "+rnd);
		Cc.moveToPosition(rnd);
		Log.i("LUCA", "ric3 "+Cc.getString(Cc.getColumnIndex(CocktailMetaData.COCKTAIL_NAME_KEY)));
		// intent View
		Intent intent = new Intent().setClass(ctx, ShowCocktailChar.class);
		intent.putExtra("cocktailName",Cc.getString(Cc.getColumnIndex(CocktailMetaData.COCKTAIL_NAME_KEY)));
		Cc.close();
		return intent;
	}
}
