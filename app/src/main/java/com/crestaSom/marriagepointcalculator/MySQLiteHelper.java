package com.crestaSom.marriagepointcalculator;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {

  public static final String TABLE_STUDENTS = "comments";
  public static final String COLUMN_ID = "_id";
  public static final String COLUMN_NAME = "comment";
  public static final String COLUMN_PHOTO = "photo";
  
  public static final String TABLE_PRESENCE = "presence";
  public static final String STUDENT_ID = "_id";
  public static final String STUDENT_DATE = "att_date";

  private static final String DATABASE_NAME = "students.db";
  private static final int DATABASE_VERSION = 9;

  // Database creation sql statement
  private static final String DATABASE_CREATE1 = "create table "
	      + TABLE_STUDENTS + "(" + COLUMN_ID
	      + " integer primary key autoincrement, "
	      + COLUMN_NAME + " text not null, "
	      + COLUMN_PHOTO + " text not null);";
  private static final String DATABASE_CREATE2 = "create table "
	      + TABLE_PRESENCE + "(" + STUDENT_ID
	      + " text not null, " + STUDENT_DATE
	      + " text not null);";

  public MySQLiteHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase database) {
	  database.execSQL(DATABASE_CREATE1);
	  database.execSQL(DATABASE_CREATE2);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    Log.w(MySQLiteHelper.class.getName(),
        "Upgrading database from version " + oldVersion + " to "
            + newVersion + ", which will destroy all old data");

    db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENTS);
    db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRESENCE);
    onCreate(db);
  }

} 