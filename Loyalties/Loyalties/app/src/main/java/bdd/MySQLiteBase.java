package bdd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class MySQLiteBase extends SQLiteOpenHelper {

    private int version;

    private static final String TABLE_PEOPLE = "table_people";
    private static final String COL_PEOPLE_ID = "ID";
    private static final String COL_PEOPLE_USERNAME = "USERNAME";
    private static final String COL_PEOPLE_PASSWORD = "PASSWORD";
    private static final String COL_PEOPLE_NAME = "NAME";
    private static final String COL_PEOPLE_FIRST_NAME = "FIRST_NAME";
    private static final String COL_PEOPLE_SEXE = "SEXE";
    private static final String COL_PEOPLE_DATE_OF_BIRTH = "DATE_OF_BIRTH";
    private static final String COL_PEOPLE_ROLE = "ROLE";

    private static final String TABLE_COMPANY = "table_company";
    private static final String COL_COMPANY_ID = "ID";
    private static final String COL_COMPANY_NAME = "NAME";

    private static final String TABLE_REDUCTION = "table_reduction";
    private static final String COL_REDUCTION_ID = "ID";
    private static final String COL_REDUCTION_NAME = "NAME";
    private static final String COL_REDUCTION_TEXT = "TEXT";
    private static final String COL_REDUCTION_SEXE = "SEXE";
    private static final String COL_REDUCTION_AGE_RELATION = "AGE_RELATION";
    private static final String COL_REDUCTION_AGE_VALUE = "AGE_VALUE";
    private static final String COL_REDUCTION_NB_POINTS_RELATION = "NB_POINTS_RELATION";
    private static final String COL_REDUCTION_NB_POINTS_VALUE = "NB_POINTS_VALUE";

    private static final String TABLE_CLIENT = "table_client";
    private static final String COL_CLIENT_ID = "ID";
    private static final String COL_CLIENT_IDCOMP = "ID_COMPANY";
    private static final String COL_CLIENT_IDPEOP = "ID_PEOPLE";
    private static final String COL_CLIENT_POINTS = "POINTS";

    private static final String TABLE_OFFER = "table_offer";
    private static final String COL_OFFER_ID = "ID";
    private static final String COL_OFFER_IDCOMP = "ID_COMPANY";
    private static final String COL_OFFER_IDREDU = "ID_REDUCTION";

    private static final String TABLE_OPPORTUNITY = "table_opportunity";
    private static final String COL_OPPORTUNITY_ID = "ID";
    private static final String COL_OPPORTUNITY_IDCLIENT = "ID_CLIENT";
    private static final String COL_OPPORTUNITY_IDREDU = "ID_REDUCTION";


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

    private static final String CREATE_TABLE_COMPANY =
            "CREATE TABLE " + TABLE_COMPANY + " ("
                    + COL_COMPANY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COL_COMPANY_NAME + " TEXT NOT NULL);";

    private static final String CREATE_TABLE_REDUCTION =
            "CREATE TABLE " + TABLE_REDUCTION + " ("
                    + COL_REDUCTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COL_REDUCTION_NAME + " TEXT NOT NULL, "
                    + COL_REDUCTION_TEXT + " TEXT NOT NULL, "
                    + COL_REDUCTION_SEXE + " TEXT NOT NULL, "
                    + COL_REDUCTION_AGE_RELATION + " TEXT NOT NULL, "
                    + COL_REDUCTION_AGE_VALUE + " TEXT NOT NULL, "
                    + COL_REDUCTION_NB_POINTS_RELATION + " TEXT NOT NULL, "
                    + COL_REDUCTION_NB_POINTS_VALUE + " TEXT NOT NULL);";

    private static final String CREATE_TABLE_CLIENT =
            "CREATE TABLE " + TABLE_CLIENT + " ("
                    + COL_CLIENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COL_CLIENT_IDPEOP + " INTEGER NOT NULL, "
                    + COL_CLIENT_IDCOMP + " INTEGER NOT NULL, "
                    + COL_CLIENT_POINTS + " INTEGER NOT NULL);";

    private static final String CREATE_TABLE_OFFER =
            "CREATE TABLE " + TABLE_OFFER + " ("
                    + COL_OFFER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COL_OFFER_IDCOMP + " INTEGER NOT NULL, "
                    + COL_OFFER_IDREDU + " INTEGER NOT NULL);";

    private static final String CREATE_TABLE_OPPORTUNITY =
            "CREATE TABLE " + TABLE_OPPORTUNITY + " ("
                    + COL_OPPORTUNITY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COL_OPPORTUNITY_IDCLIENT + " INTEGER NOT NULL, "
                    + COL_OPPORTUNITY_IDREDU + " INTEGER NOT NULL);";


    public MySQLiteBase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.version = version;
    }

    public void reset(SQLiteDatabase db) {
        onUpgrade(db, version, version+1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_PEOPLE);
        db.execSQL(CREATE_TABLE_COMPANY);
        db.execSQL(CREATE_TABLE_REDUCTION);
        db.execSQL(CREATE_TABLE_CLIENT);
        db.execSQL(CREATE_TABLE_OFFER);
        db.execSQL(CREATE_TABLE_OPPORTUNITY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + TABLE_PEOPLE + ";");
        db.execSQL("DROP TABLE " + TABLE_COMPANY + ";");
        db.execSQL("DROP TABLE " + TABLE_REDUCTION + ";");
        db.execSQL("DROP TABLE " + TABLE_CLIENT + ";");
        db.execSQL("DROP TABLE " + TABLE_OFFER + ";");
        db.execSQL("DROP TABLE " + TABLE_OPPORTUNITY + ";");
        onCreate(db);
    }
}
