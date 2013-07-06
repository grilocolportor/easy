package org.avs.banco;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHandler extends SQLiteOpenHelper {
	
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "EASY";
	
	private static final String TABLE_CATEGORIA = "CATEGORIA";
	private static final String COLUMN_CATEGORIA_ID = "ID";
	private static final String COLUMN_CATEGORIA = "CAT";
	private static final String COLUMN_CATEGORIA_FAV = "FAV";
	
	private static final String TABLE_FAVORITO = "FAVORITO";
	private static final String COLUMN_FAVORITO_ID = "ID";
	private static final String COLUMN_FAVORITO = "FAV";
	private static final String COLUMN_CATEGORIA_ID_FAVORITO = "CAT_ID";
	private static final String COLUMN_LATITUDE = "LAT";
	private static final String COLUMN_LONGITUDE ="LNG";
	private static final String COLUMN_ENDERECO = "END";
	private static final String COLUMN_TEL="TEL";
	
	public DataBaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		
		String sql = " CREATE TABLE " +  TABLE_CATEGORIA + " " +
				"(" + COLUMN_CATEGORIA_ID + " INTEGER PRIMARY KEY AUTO_INCREMENT, " +
				COLUMN_CATEGORIA + " TEXT, " +
				"" + COLUMN_CATEGORIA_FAV + " int)" ;
		db.execSQL(sql);
		
		sql = " CREATE TABLE " +  TABLE_FAVORITO + " " +
				"(" + COLUMN_FAVORITO_ID + " INTEGER PRIMARY KEY AUTO_INCREMENT, " +
					COLUMN_FAVORITO + " TEXT, " +
					COLUMN_CATEGORIA_ID_FAVORITO + " INTEGER, " +
					COLUMN_LATITUDE + " DOUBLE, " +
					COLUMN_LONGITUDE + " DOUBLE, " +
					COLUMN_ENDERECO + "	 TEXT, " +
					COLUMN_TEL + " TEXT," +
							" FOREIGN KEY " + COLUMN_CATEGORIA_ID_FAVORITO + " REFERENCES " + COLUMN_CATEGORIA_ID + "("+COLUMN_CATEGORIA_ID+")" +
									" ON DELETE CASCADE ON UPDATE CASCADE)" ;
		db.execSQL(sql);
		
		
		
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
		db.execSQL("DROP TABLE IF EXIST " + TABLE_CATEGORIA);
		onCreate(db);
		db.execSQL("DROP TABLE IF EXIST " + TABLE_FAVORITO);
		onCreate(db);
			
	}
	
	public void addCategoria(Categorias categoria){
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues cv = new ContentValues();
		cv.put(COLUMN_CATEGORIA, categoria.getCategoria());
		cv.put(COLUMN_CATEGORIA_FAV, categoria.getFavorito());
		
		db.insert(TABLE_CATEGORIA, null, cv);
		db.close();
	}
	
	public Categorias getCategoria(Integer id){
		
		SQLiteDatabase db =this.getReadableDatabase();
		
		Cursor cursor = db.query(TABLE_CATEGORIA, new String[] {COLUMN_CATEGORIA_ID, COLUMN_CATEGORIA_FAV, COLUMN_CATEGORIA_FAV}
		, COLUMN_CATEGORIA_ID + "=?", new String[] {String.valueOf(id)}, null, null, null);
		
		if(cursor!=null){
			cursor.moveToFirst();
		}
		
		int col=0;
		Categorias categoria = new Categorias();
		categoria.setId(cursor.getInt(col++));
		categoria.setCategoria(cursor.getString(col++));
		categoria.setFavorito(cursor.getInt(col++));
			
		return categoria;
		
	}
	
	public List<Categorias> getTodasCategorias(boolean favorito){
		
		List<Categorias> listaCategorias = new ArrayList<Categorias>();
		
		String sql = "SELECT * FROM CATEGORIA ";
		
		if(favorito){
			sql = sql + " WHERE FAV = TRUE";
		}
		
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(sql, null);
		
		if(cursor.moveToFirst()){
			do{
				int col = 0;
				Categorias categoria = new Categorias();
				categoria.setId(cursor.getInt(col++));
				categoria.setCategoria(cursor.getString(col++));
				categoria.setFavorito(cursor.getInt(col++));
				
			}while(cursor.moveToNext());
		}
		
		return listaCategorias;
	}
	
	public int updateCategoria(Categorias categoria){
		
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues cv = new ContentValues();
		
		cv.put(COLUMN_CATEGORIA, categoria.getCategoria());
		cv.put(COLUMN_CATEGORIA_FAV, categoria.getCategoria());
		
		 return db.update(TABLE_CATEGORIA, cv, COLUMN_CATEGORIA_ID + " = ?",
	                new String[] { String.valueOf(categoria.getId()) });
		
	}

}
 