package com.example.pierre.testbdd4.BDDFolder;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class MySQLiteBase extends SQLiteOpenHelper {

    private int version;

    private static final String TABLE_COMPANY = "table_company";
    private static final String COL_COMPANY_ID = "ID";
    private static final String COL_COMPANY_NAME = "NAME";

    private static final String TABLE_PEOPLE = "table_people";
    private static final String COL_PEOPLE_ID = "ID";
    private static final String COL_PEOPLE_USERNAME = "USERNAME";
    private static final String COL_PEOPLE_PASSWORD = "PASSWORD";
    private static final String COL_PEOPLE_NAME = "NAME";
    private static final String COL_PEOPLE_FIRST_NAME = "FIRST_NAME";
    private static final String COL_PEOPLE_SEXE = "SEXE";
    private static final String COL_PEOPLE_DATE_OF_BIRTH = "DATE_OF_BIRTH";
    private static final String COL_PEOPLE_ROLE = "ROLE";

    private static final String TABLE_REDUCTION = "table_reduction";
    private static final String COL_REDUCTION_ID = "ID";
    private static final String COL_REDUCTION_NAME = "NAME";

    private static final String TABLE_LINK = "table_link";
    private static final String COL_LINK_ID = "ID";
    private static final String COL_LINK_IDCOMP = "ID_COMPANY";
    private static final String COL_LINK_IDPEOP = "ID_PEOPLE";
    private static final String COL_LINK_COMPT = "ID_COMPTEUR";
    private static final String COL_LINK_IDREDU = "ID_REDUCTION";


    private static final String CREATE_TABLE_COMPANY =
            "CREATE TABLE " + TABLE_COMPANY + " ("
                    + COL_COMPANY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COL_COMPANY_NAME + " TEXT NOT NULL);";

    private static final String CREATE_TABLE_PEOPLE =
            "CREATE TABLE " + TABLE_PEOPLE + " ("
                    + COL_PEOPLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COL_PEOPLE_USERNAME + " TEXT NOT NULL, "
                    + COL_PEOPLE_PASSWORD + " TEXT NOT NULL, "
                    + COL_PEOPLE_NAME + " TEXT NOT NULL, "
                    + COL_PEOPLE_FIRST_NAME + " TEXT NOT NULL, "
                    + COL_PEOPLE_SEXE + " TEXT NOT NULL, "
                    + COL_PEOPLE_DATE_OF_BIRTH + " TEXT NOT NULL, "
                    + COL_PEOPLE_ROLE + " TEXT NOT NULL);";

    private static final String CREATE_TABLE_REDUCTION =
            "CREATE TABLE " + TABLE_REDUCTION + " ("
                    + COL_REDUCTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COL_REDUCTION_NAME + " TEXT NOT NULL);";

    private static final String CREATE_TABLE_LINK =
            "CREATE TABLE " + TABLE_LINK + " ("
                    + COL_LINK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COL_LINK_IDCOMP + " INTEGER NOT NULL, "
                    + COL_LINK_IDPEOP + " INTEGER NOT NULL, "
                    + COL_LINK_COMPT + " INTEGER, "
                    + COL_LINK_IDREDU + " INTEGER);";

    public MySQLiteBase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.version = version;
    }

    public void reset(SQLiteDatabase db) {
        onUpgrade(db, version, version+1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_COMPANY);
        db.execSQL(CREATE_TABLE_PEOPLE);
        db.execSQL(CREATE_TABLE_REDUCTION);
        db.execSQL(CREATE_TABLE_LINK);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + TABLE_COMPANY + ";");
        db.execSQL("DROP TABLE " + TABLE_PEOPLE + ";");
        db.execSQL("DROP TABLE " + TABLE_REDUCTION + ";");
        db.execSQL("DROP TABLE " + TABLE_LINK + ";");
        onCreate(db);
    }
}
