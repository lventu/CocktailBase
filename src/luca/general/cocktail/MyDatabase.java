/* The database object
 * 
 * Author: Luca Venturini
 */
package luca.general.cocktail;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class MyDatabase {  

	SQLiteDatabase mDb;
	dbCocktails mDbHelper;
	Context mContext; // ha bisogno di un contesto
	
	public MyDatabase(Context ctx){
		mContext=ctx; // the context is passed as constructor's parameter
		mDbHelper=new dbCocktails(ctx);	  //create also the helper instance (see the other class file)	
	}
	
	public void open(){  //open the R/W database
		mDb=mDbHelper.getWritableDatabase();		
	}
	
	public void close(){ //Closing the Database
		mDb.close();
	}
	
	
/*	public Cursor fetchCocktail(){ //metodo per fare la query di tutti i dati
		return mDb.query(CocktailMetaData.COCKTAIL_TABLE, null,null,null,null,null,CocktailMetaData.COCKTAIL_NAME_KEY+" ASC",null);		
	}
	*/
	
	// Function that return the rows whit a string Name on input (note that the cursor could contains many entries)
	public Cursor fetchCocktail(String CKName){
		Cursor C;
		//Log.i("prova", CKName);
		if(CKName.equalsIgnoreCase("all")){
			String[] a = {CocktailMetaData.COCKTAIL_NAME_KEY,CocktailMetaData.COCKTAIL_IMAGE_KEY};
			C = mDb.query(CocktailMetaData.COCKTAIL_TABLE,a,null,null,null,null,CocktailMetaData.COCKTAIL_NAME_KEY+" ASC",null);
		}else{
			C = mDb.rawQuery("SELECT * FROM "+CocktailMetaData.COCKTAIL_TABLE+" WHERE "+CocktailMetaData.COCKTAIL_NAME_KEY+"='"+CKName+"'",null);
		}
		//Log.i("prova", "ok"+C.getCount());
		return C;
	}
	
	// in this method the only database's column fetched is Nome
	public Cursor fetchPerCateg(String Category){
		if(Category!=null){
			return mDb.rawQuery("SELECT "+CocktailMetaData.COCKTAIL_NAME_KEY+" FROM "+CocktailMetaData.COCKTAIL_TABLE+" WHERE "+CocktailMetaData.COCKTAIL_CATEGORY_KEY+"='"+Category+"' OR "+CocktailMetaData.COCKTAIL_CATEGORY_KEY+"='Tutte le ore'",null);
		}else{
			return null;
		}
	}
	
	public static class CocktailMetaData {  
		// the open meta-data of the main(and unique) table of valid data
		static final String COCKTAIL_TABLE = "mainData";
		static final String ID = "_id";
		static final String COCKTAIL_NAME_KEY = "Nome";
		static final String COCKTAIL_CATEGORY_KEY = "Categoria";
		static final String COCKTAIL_FAMILY_KEY = "Famiglia";
		static final String COCKTAIL_IBA_KEY = "IBARef";
		static final String COCKTAIL_DIVISION_KEY = "Ripartizione";
		static final String COCKTAIL_GLASS_KEY = "GlassType";
		static final String COCKTAIL_INGREDIENT_KEY = "Ingredienti";
		static final String COCKTAIL_BLEND_KEY = "Miscelazione";
		static final String COCKTAIL_DECOR_KEY = "Decorazione";
		static final String COCKTAIL_IMAGE_KEY = "Immagine";
		}
	public static class CocktailMetaTime{
		static final String PRE = "Pre Dinner";
		static final String AFTER = "After Dinner";
		static final String LONG = "Long Drink";
		static final String ALL = "Tutte le ore";

	}
	//Next methods are to create and modify the database's structure (SOME MODIFY NEEDED)
	// FOR THE MOMENT DO NOT USE THE METHOD IN THIS SECITON
}