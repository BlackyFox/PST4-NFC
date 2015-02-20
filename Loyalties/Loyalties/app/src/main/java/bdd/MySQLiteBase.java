package bdd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class MySQLiteBase extends SQLiteOpenHelper {

    private int version;

    private static final String TABLE_PEOPLE = "people";
    private static final String COL_PEOPLE_ID = "id";
    private static final String COL_PEOPLE_USERNAME = "username";
    private static final String COL_PEOPLE_PASSWORD = "password";
    private static final String COL_PEOPLE_NAME = "name";
    private static final String COL_PEOPLE_FIRST_NAME = "first_name";
    private static final String COL_PEOPLE_SEXE = "sexe";
    private static final String COL_PEOPLE_DATE_OF_BIRTH = "date_of_birth";
    private static final String COL_PEOPLE_MAIL = "mail";
    private static final String COL_PEOPLE_CITY = "city";
    private static final String COL_PEOPLE_UP_DATE = "up_date";

    private static final String TABLE_COMPANIES = "companies";
    private static final String COL_COMPANIES_ID = "id";
    private static final String COL_COMPANIES_NAME = "name";
    private static final String COL_COMPANIES_LOGO = "logo";
    private static final String COL_COMPANIES_CARD = "card";
    private static final String COL_COMPANIES_UP_DATE = "up_date";

    private static final String TABLE_CLIENTS = "clients";
    private static final String COL_CLIENTS_ID = "id";
    private static final String COL_CLIENTS_ID_PEOP = "id_peop";
    private static final String COL_CLIENTS_ID_COMP = "id_comp";
    private static final String COL_CLIENTS_NUM_CLIENT = "num_client";
    private static final String COL_CLIENTS_NB_LOYALTIES = "nb_loyalties";
    private static final String COL_CLIENTS_LAST_USED = "last_used";
    private static final String COL_CLIENTS_UP_DATE = "up_date";

    private static final String TABLE_REDUCTIONS = "reductions";
    private static final String COL_REDUCTIONS_ID = "id";
    private static final String COL_REDUCTIONS_NAME = "name";
    private static final String COL_REDUCTIONS_TEXT = "text";
    private static final String COL_REDUCTIONS_SEXE = "sexe";
    private static final String COL_REDUCTIONS_AGE_RELATION = "age_relation";
    private static final String COL_REDUCTIONS_AGE_VALUE = "age_value";
    private static final String COL_REDUCTIONS_NB_POINTS_RELATION = "nb_points_relation";
    private static final String COL_REDUCTIONS_NB_POINTS_VALUE = "nb_points_value";
    private static final String COL_REDUCTIONS_CITY = "city";
    private static final String COL_REDUCTIONS_UP_DATE = "up_date";

    private static final String TABLE_OFFERS = "offers";
    private static final String COL_OFFERS_ID = "id";
    private static final String COL_OFFERS_ID_COMP = "id_comp";
    private static final String COL_OFFERS_ID_REDU = "id_redu";
    private static final String COL_OFFERS_UP_DATE = "up_date";

    private static final String TABLE_OPPORTUNITIES = "opportunities";
    private static final String COL_OPPORTUNITIES_ID = "id";
    private static final String COL_OPPORTUNITIES_ID_CLIENT = "id_client";
    private static final String COL_OPPORTUNITIES_ID_REDU = "id_redu";
    private static final String COL_OPPORTUNITIES_UP_DATE = "up_date";


    private static final String CREATE_TABLE_PEOPLE =
            "CREATE TABLE " + TABLE_PEOPLE + " ("
                    + COL_PEOPLE_ID + " INTEGER PRIMARY KEY, "
                    + COL_PEOPLE_USERNAME + " TEXT NOT NULL, "
                    + COL_PEOPLE_PASSWORD + " TEXT NOT NULL, "
                    + COL_PEOPLE_NAME + " TEXT NOT NULL, "
                    + COL_PEOPLE_FIRST_NAME + " TEXT NOT NULL, "
                    + COL_PEOPLE_SEXE + " TEXT NOT NULL, "
                    + COL_PEOPLE_DATE_OF_BIRTH + " TEXT NOT NULL, "
                    + COL_PEOPLE_MAIL + " TEXT NOT NULL, "
                    + COL_PEOPLE_CITY + " TEXT NOT NULL, "
                    + COL_PEOPLE_UP_DATE + " TEXT NOT NULL);";

    private static final String CREATE_TABLE_COMPANIES =
            "CREATE TABLE " + TABLE_COMPANIES + " ("
                    + COL_COMPANIES_ID + " INTEGER PRIMARY KEY, "
                    + COL_COMPANIES_NAME + " TEXT NOT NULL, "
                    + COL_COMPANIES_LOGO + " TEXT NOT NULL, "
                    + COL_COMPANIES_CARD + " TEXT NOT NULL, "
                    + COL_COMPANIES_UP_DATE + " TEXT NOT NULL);";

    private static final String CREATE_TABLE_CLIENTS =
            "CREATE TABLE " + TABLE_CLIENTS + " ("
                    + COL_CLIENTS_ID + " INTEGER PRIMARY KEY, "
                    + COL_CLIENTS_ID_PEOP + " INTEGER NOT NULL, "
                    + COL_CLIENTS_ID_COMP + " INTEGER NOT NULL, "
                    + COL_CLIENTS_NUM_CLIENT + " TEXT NOT NULL, "
                    + COL_CLIENTS_NB_LOYALTIES + " INTEGER NOT NULL, "
                    + COL_CLIENTS_LAST_USED + " INTEGER NOT NULL, "
                    + COL_CLIENTS_UP_DATE + " TEXT NOT NULL);";

    private static final String CREATE_TABLE_REDUCTIONS =
            "CREATE TABLE " + TABLE_REDUCTIONS + " ("
                    + COL_REDUCTIONS_ID + " INTEGER PRIMARY KEY, "
                    + COL_REDUCTIONS_NAME + " TEXT NOT NULL, "
                    + COL_REDUCTIONS_TEXT + " TEXT NOT NULL, "
                    + COL_REDUCTIONS_SEXE + " TEXT NOT NULL, "
                    + COL_REDUCTIONS_AGE_RELATION + " TEXT NOT NULL, "
                    + COL_REDUCTIONS_AGE_VALUE + " TEXT NOT NULL, "
                    + COL_REDUCTIONS_NB_POINTS_RELATION + " TEXT NOT NULL, "
                    + COL_REDUCTIONS_NB_POINTS_VALUE + " TEXT NOT NULL, "
                    + COL_REDUCTIONS_CITY + " TEXT NOT NULL, "
                    + COL_REDUCTIONS_UP_DATE + " TEXT NOT NULL);";

    private static final String CREATE_TABLE_OFFERS =
            "CREATE TABLE " + TABLE_OFFERS + " ("
                    + COL_OFFERS_ID + " INTEGER PRIMARY KEY, "
                    + COL_OFFERS_ID_COMP + " INTEGER NOT NULL, "
                    + COL_OFFERS_ID_REDU + " INTEGER NOT NULL, "
                    + COL_OFFERS_UP_DATE + " TEXT NOT NULL);";

    private static final String CREATE_TABLE_OPPORTUNITIES =
            "CREATE TABLE " + TABLE_OPPORTUNITIES + " ("
                    + COL_OPPORTUNITIES_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COL_OPPORTUNITIES_ID_CLIENT + " INTEGER NOT NULL, "
                    + COL_OPPORTUNITIES_ID_REDU + " INTEGER NOT NULL, "
                    + COL_OPPORTUNITIES_UP_DATE + " TEXT NOT NULL);";


    public MySQLiteBase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.version = version;
    }

    public void reset(SQLiteDatabase db) {
        onUpgrade(db, version, version+1);
    }

    public void delete(SQLiteDatabase db) {
        db.execSQL("DROP TABLE " + TABLE_PEOPLE + ";");
        db.execSQL("DROP TABLE " + TABLE_COMPANIES + ";");
        db.execSQL("DROP TABLE " + TABLE_CLIENTS + ";");
        db.execSQL("DROP TABLE " + TABLE_REDUCTIONS + ";");
        db.execSQL("DROP TABLE " + TABLE_OFFERS + ";");
        db.execSQL("DROP TABLE " + TABLE_OPPORTUNITIES + ";");
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_PEOPLE);
        db.execSQL(CREATE_TABLE_COMPANIES);
        db.execSQL(CREATE_TABLE_CLIENTS);
        db.execSQL(CREATE_TABLE_REDUCTIONS);
        db.execSQL(CREATE_TABLE_OFFERS);
        db.execSQL(CREATE_TABLE_OPPORTUNITIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + TABLE_PEOPLE + ";");
        db.execSQL("DROP TABLE " + TABLE_COMPANIES + ";");
        db.execSQL("DROP TABLE " + TABLE_CLIENTS + ";");
        db.execSQL("DROP TABLE " + TABLE_REDUCTIONS + ";");
        db.execSQL("DROP TABLE " + TABLE_OFFERS + ";");
        db.execSQL("DROP TABLE " + TABLE_OPPORTUNITIES + ";");
        onCreate(db);
    }
}
