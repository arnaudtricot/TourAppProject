package com.application.tourapp.databasehandler;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.application.tourapp.dal.CityDAL;
import com.application.tourapp.global.TourAppGlobal;
import com.application.tourapp.model.City;
import com.application.tourapp.xml.XMLParser;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class TourAppDatabaseHandler extends SQLiteOpenHelper {
	
	private static final int DATABASE_VERSION = 57;
	private static final String DB_PATH = "/data/data/com.application.tourapp/databases/";
    public static final String DATABASE_NAME = "tourapp";
    private Context context;
    
    public static final String TABLE_TYPES = "type";
    public static final String KEY_ID_TYPE = "id";
    public static final String KEY_NAME_TYPE = "name";
    public static final String KEY_IMAGE_TYPE = "image";
    
    public static final String TABLE_TI = "touritems";
    public static final String KEY_ID_TI = "id";
    public static final String KEY_NAME_TI = "name";
    public static final String KEY_DESCRIPTION_TI = "description";
    public static final String KEY_PRIX_TI = "prix";
    public static final String KEY_COORDS_TI = "coords";
    public static final String KEY_EMAIL_TI = "email";
    public static final String KEY_ADDRESS_TI = "address";
    public static final String KEY_LONG_TI = "longitude";
    public static final String KEY_LAT_TI = "latitude";
    public static final String KEY_TYPE_TI = "type";
    public static final String KEY_CITY_TI = "city";
    public static final String KEY_WEBSITE_TI = "website";
    public static final String KEY_IMAGE_ONE_TI = "image_one";
    public static final String KEY_IMAGE_TWO_TI = "image_two";
    public static final String KEY_IMAGE_THREE_TI = "image_three";
    
    public static final String TABLE_MESSAGE = "message";
    public static final String KEY_ID_MESSAGE = "id";
    public static final String KEY_SUBJECT_MESSAGE = "subject";
    public static final String KEY_TEXT_MESSAGE = "text";
    
    public static final String TABLE_TMT = "tmt";
    public static final String KEY_ID_TMT = "id";
    public static final String KEY_MORNING_TMT = "morning";
    public static final String KEY_AFTER_TMT = "after";
    public static final String KEY_NIGHT_TMT = "night";
    
    public static final String TABLE_FAV = "favorits";
    public static final String KEY_ID_FAV = "id_tour_item";
    
    public static final String TABLE_DESIGN = "design";
    public static final String KEY_ID_DESIGN = "id";
    public static final String KEY_NAME_DESIGN = "name";
    public static final String KEY_TITLE_ITEM_DESIGN = "title_item_color";
    public static final String KEY_DESC_ITEM_DESIGN = "desc_item_color";
    public static final String KEY_BACK_ITEM_DESIGN = "back_item_color";
    public static final String KEY_LOGO_DESIGN = "logo";
    
    public static final String FILE_CITY = "cities.xml";
    public static final String TABLE_CITY = "city";
    public static final String KEY_ID_CITY = "id";
    public static final String KEY_NAME_CITY = "name";
    public static final String KEY_SELECT_CITY = "selected";
    
    private String CREATE_TYPES_TABLE = "CREATE TABLE " + TABLE_TYPES + "("
            + KEY_ID_TYPE + " INTEGER PRIMARY KEY," 
    		+ KEY_NAME_TYPE + " TEXT,"
            + KEY_IMAGE_TYPE + " BLOB" + ")";
    
    private String CREATE_TI_TABLE = "CREATE TABLE " + TABLE_TI + "("
            + KEY_ID_TI + " INTEGER PRIMARY KEY," 
    		+ KEY_NAME_TI + " TEXT,"
    		+ KEY_PRIX_TI + " TEXT,"
    		+ KEY_COORDS_TI + " TEXT,"
    		+ KEY_EMAIL_TI + " TEXT,"
    		+ KEY_ADDRESS_TI + " TEXT,"
    		+ KEY_LONG_TI + " TEXT,"
    		+ KEY_LAT_TI + " TEXT,"
    		+ KEY_TYPE_TI + " TEXT,"
    		+ KEY_CITY_TI + " TEXT,"
    		+ KEY_WEBSITE_TI + " TEXT,"
            + KEY_DESCRIPTION_TI + " TEXT," 
            + KEY_IMAGE_ONE_TI + " BLOB,"
            + KEY_IMAGE_TWO_TI + " BLOB,"
            + KEY_IMAGE_THREE_TI + " BLOB" + ")";
    
    private String CREATE_MESSAGE_TABLE = "CREATE TABLE " + TABLE_MESSAGE + "("
            + KEY_ID_MESSAGE + " INTEGER PRIMARY KEY," 
    		+ KEY_SUBJECT_MESSAGE + " TEXT,"
    		+ KEY_TEXT_MESSAGE + " TEXT" + ")";
    
    private String CREATE_TMT_TABLE = "CREATE TABLE " + TABLE_TMT + "("
    		+ KEY_ID_TMT + " INTEGER PRIMARY KEY,"
            + KEY_MORNING_TMT + " INTEGER," 
    		+ KEY_AFTER_TMT + " INTEGER,"
    		+ KEY_NIGHT_TMT + " INTEGER" + ")";
  
    private String CREATE_FAV_TABLE = "CREATE TABLE " + TABLE_FAV + "("
            + KEY_ID_FAV + " INTEGER PRIMARY KEY" + ")";
    
    private String CREATE_DESIGN_TABLE = "CREATE TABLE " + TABLE_DESIGN + "("
            + KEY_ID_DESIGN + " INTEGER PRIMARY KEY," 
    		+ KEY_NAME_DESIGN + " TEXT,"
    		+ KEY_TITLE_ITEM_DESIGN + " TEXT,"
    		+ KEY_DESC_ITEM_DESIGN + " TEXT,"
    		+ KEY_BACK_ITEM_DESIGN + " TEXT,"
            + KEY_LOGO_DESIGN + " BLOB" + ")";
    
    private String CREATE_CITY_TABLE = "CREATE TABLE " + TABLE_CITY + "("
            + KEY_ID_CITY + " INTEGER PRIMARY KEY," 
    		+ KEY_NAME_CITY + " TEXT," 
    		+ KEY_SELECT_CITY + " BOOLEAN" +")";
    
    public TourAppDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

	@Override
	public void onCreate(SQLiteDatabase db) {
		System.out.println("ON CREATE");
		boolean dbExist = checkDataBase();
		
		db.execSQL(CREATE_TYPES_TABLE);
		db.execSQL(CREATE_TI_TABLE);
		db.execSQL(CREATE_MESSAGE_TABLE);
		db.execSQL(CREATE_TMT_TABLE);
		db.execSQL(CREATE_FAV_TABLE);
		db.execSQL(CREATE_DESIGN_TABLE);
		db.execSQL(CREATE_CITY_TABLE);
		
		if (!dbExist) {
			System.out.println("test insert");
			//db.execSQL("INSERT INTO city (id, name, selected) VALUES (1, \"Angers\", true), (2, \"Nantes\", false)");
			//this.getReadableDatabase();
			//insertCities(db);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		System.out.println("ON UPDATE");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_TYPES);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_TI);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_MESSAGE);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_TMT);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAV);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_DESIGN);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CITY);
        onCreate(db);
	}
	
	/**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase(){
 
    	SQLiteDatabase checkDB = null;
 
    	try{
    		String myPath = DB_PATH + DATABASE_NAME;
    		checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
    	}catch(SQLiteException e){
    		//database does't exist yet.
    	}
    	
    	if(checkDB != null){
    		checkDB.close();
    	}
 
    	return checkDB != null ? true : false;
    }

	public void insertCities(SQLiteDatabase db) {
		try {
			InputStream input = context.getAssets().open(FILE_CITY);
			
			XMLParser parser = new XMLParser();
			CityDAL cityDAL = new CityDAL(context);
			String xmlToSave = getStringFromInputStream(input);
			System.out.println(xmlToSave);

			Document doc = parser.getDomElement(xmlToSave);
			NodeList listCitiesNode = doc.getElementsByTagName("models.City");
			
			for (int i = 0; i < listCitiesNode.getLength(); i++) {
				Element typeElement = (Element) listCitiesNode.item(i);

				City city = new City();

				String nameCity = parser.getValue(typeElement, KEY_NAME_CITY);
				int idCity = Integer.parseInt(parser.getValue(typeElement,
						KEY_ID_CITY));
				boolean selected = false;
				if (i == 0) {
					selected = true;
				}

				city.setName(nameCity);
				city.setId(idCity);
				city.setSelected(selected);
				cityDAL.addCity(city);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static String getStringFromInputStream(InputStream is) {
		 
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();
 
		String line;
		try {
			br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
 
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
 
		return sb.toString();
	}
}
