package com.lweather.app.db;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

import com.lweather.app.model.City;
import com.lweather.app.model.County;
import com.lweather.app.model.Province;

import android.R.color;
import android.R.integer;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class LweatherDB {
public static final String DB_NAME="l_weather";
public int version=1;
private LweatherDB lweatherDB;
private SQLiteDatabase db;
private LweatherDB(Context context){
	LweatherOpenHelper dbHelper=new LweatherOpenHelper(context, DB_NAME, null, version);
	db=dbHelper.getWritableDatabase();
}
public synchronized LweatherDB getInstance(Context context){
	if(lweatherDB==null){
		lweatherDB=new LweatherDB(context);
	}
	return lweatherDB;
}
public void saveProvince(Province province){
	if(province!=null){
		ContentValues values=new ContentValues();
		values.put("province_name", province.getProvinceName());
		values.put("province_code", province.getProvinceCode());
		db.insert("Province", null, values);
	}
	
}
public List<Province> loadProvince(){
	List<Province> pList=new ArrayList<Province>();
	Cursor cursor=db.query("Province",null, null,null,null,null, null);
	if(cursor.moveToFirst()){
		do{
			Province province=new Province();
			province.setId(cursor.getInt(cursor.getColumnIndex("id")));
			province.setProvinceCode(cursor.getString(cursor.getColumnIndex("province_code")));
			province.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
			pList.add(province);
		}while(cursor.moveToNext());
	}
	if(cursor!=null){
		cursor.close();
	}
	return pList;
}
public void saveCity(City city){
	if(city!=null){
		ContentValues values=new ContentValues();
		values.put("city_name", city.getCityName());
		values.put("city_code", city.getCityCode());
		values.put("province_id", city.getProvinceId());
		db.insert("City", null, values);
	}
	
}
public List<City> loadCity(int provinceId){
	List<City> cList=new ArrayList<City>();
	Cursor cursor=db.query("City",null, "provinceId = ? ",new String[]{String.valueOf(provinceId)},null,null, null);
	if(cursor.moveToFirst()){
		do{
			City city=new City();
			city.setId(cursor.getInt(cursor.getColumnIndex("id")));
			city.setCityCode(cursor.getString(cursor.getColumnIndex("city_code")));
			city.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
			city.setProvinceId(provinceId);
			cList.add(city);
		}while(cursor.moveToNext());
	}
	if(cursor!=null){
		cursor.close();
	}
	return cList;
}
public void saveCounty(County county){
	if(county!=null){
		ContentValues values=new ContentValues();
		values.put("county_name", county.getCountyName());
		values.put("county_code", county.getCountyCode());
		db.insert("City", null, values);
	}
	
}
public List<County> loadCounty(int cityId){
	List<County> cList=new ArrayList<County>();
	Cursor cursor=db.query("County",null, "cityId = ? ",new String[]{String.valueOf(cityId)},null,null, null);
	if(cursor.moveToFirst()){
		do{
			County county=new County();
			county.setId(cursor.getInt(cursor.getColumnIndex("id")));
			county.setCountyCode(cursor.getString(cursor.getColumnIndex("county_code")));
			county.setCountyName(cursor.getString(cursor.getColumnIndex("county_name")));
			county.setCityId(cityId);
			cList.add(county);
		}while(cursor.moveToNext());
	}
	if(cursor!=null){
		cursor.close();
	}
	return cList;
}
}
