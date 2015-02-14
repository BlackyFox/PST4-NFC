package bdd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import objects.*;

import java.util.Calendar;
import java.util.TimeZone;


public class MyBDD {
    private static final int VERSION_BDD = 1;
    private static final String NOM_BDD = "testbdd13.db";

    private static final String TABLE_PEOPLE = "people";
    private static final String COL_PEOPLE_ID = "id";
    private static final int NUM_COL_PEOPLE_ID = 0;
    private static final String COL_PEOPLE_USERNAME = "username";
    private static final int NUM_COL_PEOPLE_USERNAME = 1;
    private static final String COL_PEOPLE_PASSWORD = "password";
    private static final int NUM_COL_PEOPLE_PASSWORD = 2;
    private static final String COL_PEOPLE_NAME = "name";
    private static final int NUM_COL_PEOPLE_NAME = 3;
    private static final String COL_PEOPLE_FIRST_NAME = "first_name";
    private static final int NUM_COL_PEOPLE_FIRST_NAME = 4;
    private static final String COL_PEOPLE_SEXE = "sexe";
    private static final int NUM_COL_PEOPLE_SEXE = 5;
    private static final String COL_PEOPLE_DATE_OF_BIRTH = "date_of_birth";
    private static final int NUM_COL_PEOPLE_DATE_OF_BIRTH = 6;
    private static final String COL_PEOPLE_MAIL = "mail";
    private static final int NUM_COL_PEOPLE_MAIL = 7;
    private static final String COL_PEOPLE_CITY = "city";
    private static final int NUM_COL_PEOPLE_CITY = 8;

    private static final String TABLE_COMPANIES = "companies";
    private static final String COL_COMPANIES_ID = "id";
    private static final int NUM_COL_COMPANIES_ID = 0;
    private static final String COL_COMPANIES_NAME = "name";
    private static final int NUM_COL_COMPANIES_NAME = 1;
    private static final String COL_COMPANIES_LOGO = "logo";
    private static final int NUM_COL_COMPANIES_LOGO = 2;
    private static final String COL_COMPANIES_CARD = "card";
    private static final int NUM_COL_COMPANIES_CARD = 3;

    private static final String TABLE_CLIENTS = "clients";
    private static final String COL_CLIENTS_ID = "id";
    private static final int NUM_COL_CLIENTS_ID = 0;
    private static final String COL_CLIENTS_ID_PEOP = "id_peop";
    private static final int NUM_COL_CLIENTS_ID_PEOP = 1;
    private static final String COL_CLIENTS_ID_COMP = "id_comp";
    private static final int NUM_COL_CLIENTS_ID_COMP = 2;
    private static final String COL_CLIENTS_NUM_CLIENT = "num_client";
    private static final int NUM_COL_CLIENTS_NUM_CLIENT = 3;
    private static final String COL_CLIENTS_NB_LOYALTIES = "nb_loyalties";
    private static final int NUM_COL_CLIENTS_NB_LOYALTIES = 4;
    private static final String COL_CLIENTS_LAST_USED = "last_used";
    private static final int NUM_COL_CLIENTS_LAST_USED = 5;

    private static final String TABLE_REDUCTIONS = "reductions";
    private static final String COL_REDUCTIONS_ID = "id";
    private static final int NUM_COL_REDUCTIONS_ID = 0;
    private static final String COL_REDUCTIONS_NAME = "name";
    private static final int NUM_COL_REDUCTIONS_NAME = 1;
    private static final String COL_REDUCTIONS_TEXT = "text";
    private static final int NUM_COL_REDUCTIONS_TEXT = 2;
    private static final String COL_REDUCTIONS_SEXE = "sexe";
    private static final int NUM_COL_REDUCTIONS_SEXE = 3;
    private static final String COL_REDUCTIONS_AGE_RELATION = "age_relation";
    private static final int NUM_COL_REDUCTIONS_AGE_RELATION = 4;
    private static final String COL_REDUCTIONS_AGE_VALUE = "age_value";
    private static final int NUM_COL_REDUCTIONS_AGE_VALUE = 5;
    private static final String COL_REDUCTIONS_NB_POINTS_RELATION = "nb_points_relation";
    private static final int NUM_COL_REDUCTIONS_NB_POINTS_RELATION = 6;
    private static final String COL_REDUCTIONS_NB_POINTS_VALUE = "nb_points_value";
    private static final int NUM_COL_REDUCTIONS_NB_POINTS_VALUE = 7;
    private static final String COL_REDUCTIONS_CITY = "city";
    private static final int NUM_COL_REDUCTIONS_CITY = 8;

    private static final String TABLE_OFFERS = "offers";
    private static final String COL_OFFERS_ID = "id";
    private static final int NUM_COL_OFFERS_ID = 0;
    private static final String COL_OFFERS_ID_COMP = "id_comp";
    private static final int NUM_COL_OFFERS_ID_COMP = 1;
    private static final String COL_OFFERS_ID_REDU = "id_redu";
    private static final int NUM_COL_OFFERS_ID_REDU = 2;

    private static final String TABLE_OPPORTUNITIES = "opportunities";
    private static final String COL_OPPORTUNITIES_ID = "id";
    private static final int NUM_COL_OPPORTUNITIES_ID = 0;
    private static final String COL_OPPORTUNITIES_ID_CLIENT = "id_client";
    private static final int NUM_COL_OPPORTUNITIES_ID_CLIENT = 1;
    private static final String COL_OPPORTUNITIES_ID_REDU = "id_redu";
    private static final int NUM_COL_OPPORTUNITIES_ID_REDU = 2;


    private SQLiteDatabase bdd;
    private MySQLiteBase mySQLiteBase;

    public MyBDD(Context context){
        mySQLiteBase = new MySQLiteBase(context, NOM_BDD, null, VERSION_BDD);
    }

    public void open(){
        bdd = mySQLiteBase.getWritableDatabase();
    }

    public void close(){
        bdd.close();
    }

    public void reset(){
        mySQLiteBase.reset(bdd);
    }

    public SQLiteDatabase getBDD(){
        return bdd;
    }


/** FONCTIONS INSERTION ***************************************************************************/
    public long insertPeople(People people){
        ContentValues values = new ContentValues();
        values.put(COL_PEOPLE_USERNAME, people.getUsername());
        values.put(COL_PEOPLE_PASSWORD, people.getPassword());
        values.put(COL_PEOPLE_NAME, people.getName());
        values.put(COL_PEOPLE_FIRST_NAME, people.getFirst_name());
        values.put(COL_PEOPLE_SEXE, people.getSexe());
        values.put(COL_PEOPLE_DATE_OF_BIRTH, people.getDate_of_birth());
        values.put(COL_PEOPLE_MAIL, people.getMail());
        values.put(COL_PEOPLE_CITY, people.getCity());
        return bdd.insert(TABLE_PEOPLE, null, values);
    }

    public long insertCompany(Company company){
        ContentValues values = new ContentValues();
        values.put(COL_COMPANIES_NAME, company.getName());
        values.put(COL_COMPANIES_LOGO, company.getLogo());
        values.put(COL_COMPANIES_CARD, company.getCard());
        return bdd.insert(TABLE_COMPANIES, null, values);
    }

    public long insertClient(Client client){
        ContentValues values = new ContentValues();
        values.put(COL_CLIENTS_ID_PEOP, client.getId_peop());
        values.put(COL_CLIENTS_ID_COMP, client.getId_comp());
        values.put(COL_CLIENTS_NUM_CLIENT, client.getNum_client());
        values.put(COL_CLIENTS_NB_LOYALTIES, client.getNb_loyalties());
        values.put(COL_CLIENTS_LAST_USED, client.getLast_used());
        return bdd.insert(TABLE_CLIENTS, null, values);
    }

    public long insertReduction(Reduction reduction){
        ContentValues values = new ContentValues();
        values.put(COL_REDUCTIONS_NAME, reduction.getName());
        values.put(COL_REDUCTIONS_TEXT, reduction.getText());
        values.put(COL_REDUCTIONS_SEXE, reduction.getSexe());
        values.put(COL_REDUCTIONS_AGE_RELATION, reduction.getAge_relation());
        values.put(COL_REDUCTIONS_AGE_VALUE, reduction.getAge_value());
        values.put(COL_REDUCTIONS_NB_POINTS_RELATION, reduction.getNb_points_relation());
        values.put(COL_REDUCTIONS_NB_POINTS_VALUE, reduction.getNb_points_value());
        values.put(COL_REDUCTIONS_CITY, reduction.getCity());
        return bdd.insert(TABLE_REDUCTIONS, null, values);
    }

    public long insertOffer(Offer offer){
        ContentValues values = new ContentValues();
        values.put(COL_OFFERS_ID_COMP, offer.getId_comp());
        values.put(COL_OFFERS_ID_REDU, offer.getId_redu());
        return bdd.insert(TABLE_OFFERS, null, values);
    }

    public long insertOpportunity(Opportunity opportunity){
        ContentValues values = new ContentValues();
        values.put(COL_OPPORTUNITIES_ID_CLIENT, opportunity.getId_client());
        values.put(COL_OPPORTUNITIES_ID_REDU, opportunity.getId_redu());
        return bdd.insert(TABLE_OPPORTUNITIES, null, values);
    }


/** FONCTIONS MISE A JOUR *************************************************************************/
    public int updatePeople(int id, People people){
        ContentValues values = new ContentValues();
        values.put(COL_PEOPLE_USERNAME, people.getUsername());
        values.put(COL_PEOPLE_PASSWORD, people.getPassword());
        values.put(COL_PEOPLE_NAME, people.getName());
        values.put(COL_PEOPLE_FIRST_NAME, people.getFirst_name());
        values.put(COL_PEOPLE_SEXE, people.getSexe());
        values.put(COL_PEOPLE_DATE_OF_BIRTH, people.getDate_of_birth());
        values.put(COL_PEOPLE_MAIL, people.getMail());
        values.put(COL_PEOPLE_CITY, people.getCity());
        return bdd.update(TABLE_PEOPLE, values, COL_PEOPLE_ID + " = " + id, null);
    }

    public int updateCompany(int id, Company company){
        ContentValues values = new ContentValues();
        values.put(COL_COMPANIES_NAME, company.getName());
        values.put(COL_COMPANIES_LOGO, company.getLogo());
        values.put(COL_COMPANIES_CARD, company.getCard());
        return bdd.update(TABLE_COMPANIES, values, COL_COMPANIES_ID + " = " + id, null);
    }

    public int updateClient(int id, Client client){
        ContentValues values = new ContentValues();
        values.put(COL_CLIENTS_ID_PEOP, client.getId_peop());
        values.put(COL_CLIENTS_ID_COMP, client.getId_comp());
        values.put(COL_CLIENTS_NUM_CLIENT, client.getNum_client());
        values.put(COL_CLIENTS_NB_LOYALTIES, client.getNb_loyalties());
        values.put(COL_CLIENTS_LAST_USED, client.getLast_used());
        return bdd.update(TABLE_CLIENTS, values, COL_CLIENTS_ID + " = " + id, null);
    }

    public int updateReduction(int id, Reduction reduction){
        ContentValues values = new ContentValues();
        values.put(COL_REDUCTIONS_NAME, reduction.getName());
        values.put(COL_REDUCTIONS_TEXT, reduction.getText());
        values.put(COL_REDUCTIONS_SEXE, reduction.getSexe());
        values.put(COL_REDUCTIONS_AGE_RELATION, reduction.getAge_relation());
        values.put(COL_REDUCTIONS_AGE_VALUE, reduction.getAge_value());
        values.put(COL_REDUCTIONS_NB_POINTS_RELATION, reduction.getNb_points_relation());
        values.put(COL_REDUCTIONS_NB_POINTS_VALUE, reduction.getNb_points_value());
        values.put(COL_REDUCTIONS_CITY, reduction.getCity());
        return bdd.update(TABLE_REDUCTIONS, values, COL_REDUCTIONS_ID + " = " + id, null);
    }

    public int updateOffer(int id, Offer offer){
        ContentValues values = new ContentValues();
        values.put(COL_OFFERS_ID_COMP, offer.getId_comp());
        values.put(COL_OFFERS_ID_REDU, offer.getId_redu());
        return bdd.update(TABLE_OFFERS, values, COL_OFFERS_ID + " = " + id, null);
    }

    public long updateOpportunity(int id, Opportunity opportunity){
        ContentValues values = new ContentValues();
        values.put(COL_OPPORTUNITIES_ID_CLIENT, opportunity.getId_client());
        values.put(COL_OPPORTUNITIES_ID_REDU, opportunity.getId_redu());
        return bdd.update(TABLE_OPPORTUNITIES, values, COL_OPPORTUNITIES_ID + " = " + id, null);
    }


/** FONCTIONS SUPPRESSION *************************************************************************/
    public int removePeopleWithID(int id){
        return bdd.delete(TABLE_PEOPLE, COL_PEOPLE_ID + " = " + id, null);
    }

    public int removeCompanyWithID(int id){
        return bdd.delete(TABLE_COMPANIES, COL_COMPANIES_ID + " = " + id, null);
    }

    public int removeReductionWithID(int id){
        return bdd.delete(TABLE_REDUCTIONS, COL_REDUCTIONS_ID + " = " + id, null);
    }

    public int removeClientWithID(int id){
        return bdd.delete(TABLE_CLIENTS, COL_CLIENTS_ID + " = " + id, null);
    }

    public int removeOfferWithID(int id){
        return bdd.delete(TABLE_OFFERS, COL_OFFERS_ID + " = " + id, null);
    }

    public int removeOpportunityWithID(int id){
        return bdd.delete(TABLE_OPPORTUNITIES, COL_OPPORTUNITIES_ID + " = " + id, null);
    }


/** FONCTIONS RECUPERATION ************************************************************************/
    public People getPeopleWithId(int id) {
        Cursor c = bdd.query(TABLE_PEOPLE, new String[]{COL_PEOPLE_ID, COL_PEOPLE_USERNAME, COL_PEOPLE_PASSWORD, COL_PEOPLE_NAME, COL_PEOPLE_FIRST_NAME, COL_PEOPLE_SEXE, COL_PEOPLE_DATE_OF_BIRTH, COL_PEOPLE_MAIL, COL_PEOPLE_CITY}, COL_PEOPLE_ID + " LIKE \"" + id + "\"", null, null, null, null);
        return cursorToPeople(c);
    }

    public People getPeopleWithUsername(String username){
        Cursor c = bdd.query(TABLE_PEOPLE, new String[] {COL_PEOPLE_ID, COL_PEOPLE_USERNAME, COL_PEOPLE_PASSWORD, COL_PEOPLE_NAME, COL_PEOPLE_FIRST_NAME, COL_PEOPLE_SEXE, COL_PEOPLE_DATE_OF_BIRTH, COL_PEOPLE_MAIL, COL_PEOPLE_CITY}, COL_PEOPLE_USERNAME + " = \"" + username + "\"", null, null, null, null);

        return cursorToPeople(c);
    }

    public int getPeopleIdWithUsername(String username){
        Cursor c = bdd.query(TABLE_PEOPLE, new String[] {COL_PEOPLE_ID, COL_PEOPLE_USERNAME, COL_PEOPLE_PASSWORD, COL_PEOPLE_NAME, COL_PEOPLE_FIRST_NAME, COL_PEOPLE_SEXE, COL_PEOPLE_DATE_OF_BIRTH, COL_PEOPLE_MAIL, COL_PEOPLE_CITY}, COL_PEOPLE_USERNAME + " = \"" + username + "\"", null, null, null, null);

        People tmp = cursorToPeople(c);
        if(tmp == null)
            return -1;
        return tmp.getId();
    }

    public Boolean doesPeopleAlreadyExists(String username){
        Cursor c = bdd.query(TABLE_PEOPLE, new String[] {COL_PEOPLE_ID, COL_PEOPLE_USERNAME, COL_PEOPLE_PASSWORD, COL_PEOPLE_NAME, COL_PEOPLE_FIRST_NAME, COL_PEOPLE_SEXE, COL_PEOPLE_DATE_OF_BIRTH, COL_PEOPLE_MAIL, COL_PEOPLE_CITY}, COL_PEOPLE_USERNAME + " = \"" + username + "\"", null, null, null, null);

        People tmp = cursorToPeople(c);

        return !(tmp == null);
    }

    public Company getCompanyWithId(int id){
        Cursor c = bdd.query(TABLE_COMPANIES, new String[] {COL_COMPANIES_ID, COL_COMPANIES_NAME, COL_COMPANIES_LOGO, COL_COMPANIES_CARD}, COL_COMPANIES_ID + " LIKE \"" + id +"\"", null, null, null, null);
        return cursorToCompany(c);
    }

    public Company getCompanyWithName(String name){
        Cursor c = bdd.query(TABLE_COMPANIES, new String[] {COL_COMPANIES_ID, COL_COMPANIES_NAME, COL_COMPANIES_LOGO, COL_COMPANIES_CARD}, COL_COMPANIES_NAME + " LIKE \"" + name +"\"", null, null, null, null);
        return cursorToCompany(c);
    }

    public int getCompanyIdWithName(String name){
        Cursor c = bdd.query(TABLE_COMPANIES, new String[] {COL_COMPANIES_ID, COL_COMPANIES_NAME, COL_COMPANIES_LOGO, COL_COMPANIES_CARD}, COL_COMPANIES_NAME + " = \"" + name + "\"", null, null, null, null);

        Company tmp = cursorToCompany(c);
        if(tmp == null)
            return -1;
        return tmp.getId();
    }

    public Boolean doesCompanyAlreadyExists(String name){
        Cursor c = bdd.query(TABLE_COMPANIES, new String[] {COL_COMPANIES_ID, COL_COMPANIES_NAME, COL_COMPANIES_LOGO, COL_COMPANIES_CARD}, COL_COMPANIES_NAME + " = \"" + name + "\"", null, null, null, null);

        return !(cursorToCompany(c) == null);
    }

    public Reduction getReductionWithId(int id){
        Cursor c = bdd.query(TABLE_REDUCTIONS, new String[] {COL_REDUCTIONS_ID, COL_REDUCTIONS_NAME, COL_REDUCTIONS_TEXT, COL_REDUCTIONS_SEXE, COL_REDUCTIONS_AGE_RELATION, COL_REDUCTIONS_AGE_VALUE, COL_REDUCTIONS_NB_POINTS_RELATION, COL_REDUCTIONS_NB_POINTS_VALUE, COL_REDUCTIONS_CITY}, COL_REDUCTIONS_ID + " LIKE \"" + id +"\"", null, null, null, null);
        return cursorToReduction(c);
    }

    public Boolean doesReductionAlreadyExists(String name){
        Cursor c = bdd.query(TABLE_REDUCTIONS, new String[] {COL_REDUCTIONS_ID, COL_REDUCTIONS_NAME, COL_REDUCTIONS_TEXT, COL_REDUCTIONS_SEXE, COL_REDUCTIONS_AGE_RELATION, COL_REDUCTIONS_AGE_VALUE, COL_REDUCTIONS_NB_POINTS_RELATION, COL_REDUCTIONS_NB_POINTS_VALUE, COL_REDUCTIONS_CITY}, COL_REDUCTIONS_NAME + " = \"" + name + "\"", null, null, null, null);

        return !(cursorToReduction(c) == null);
    }

    public String[] getLastCompanies(int peopleId) {
        Cursor c = bdd.rawQuery("SELECT " + COL_COMPANIES_NAME + " FROM " + TABLE_COMPANIES
                + " WHERE " + COL_COMPANIES_ID + " IN (SELECT " + COL_CLIENTS_ID_COMP + " FROM " + TABLE_CLIENTS + " WHERE " + COL_CLIENTS_ID_PEOP + " = " + peopleId
                                                                                                                             + " AND " + COL_CLIENTS_LAST_USED + " != 0)", null);

        if(c.getCount() == 0)
            return null;

        int nbCompanies = c.getCount();
        String s[] = new String[nbCompanies];

        c.moveToFirst();

        for(int i = 0 ; i < nbCompanies ; i++)
        {
            s[i] = c.getString(0);
            c.moveToNext();
        }

        c.close();

        return s;
    }


/** FONCTIONS CURSOR - Transforme un cursor en objet (Company, People, ...) ***********************/
    private People cursorToPeople(Cursor c){
        if (c.getCount() == 0)
            return null;

        c.moveToFirst();
        People people = new People();
        people.setId(c.getInt(NUM_COL_PEOPLE_ID));
        people.setUsername(c.getString(NUM_COL_PEOPLE_USERNAME));
        people.setPassword(c.getString(NUM_COL_PEOPLE_PASSWORD));
        people.setName(c.getString(NUM_COL_PEOPLE_NAME));
        people.setFirst_name(c.getString(NUM_COL_PEOPLE_FIRST_NAME));
        people.setSexe(c.getString(NUM_COL_PEOPLE_SEXE));
        people.setDate_of_birth(c.getString(NUM_COL_PEOPLE_DATE_OF_BIRTH));
        people.setMail(c.getString(NUM_COL_PEOPLE_MAIL));
        people.setCity(c.getString(NUM_COL_PEOPLE_CITY));
        c.close();

        return people;
    }

    private Company cursorToCompany(Cursor c){
        if (c.getCount() == 0)
            return null;

        c.moveToFirst();
        Company company = new Company();
        company.setId(c.getInt(NUM_COL_COMPANIES_ID));
        company.setName(c.getString(NUM_COL_COMPANIES_NAME));
        company.setLogo(c.getString(NUM_COL_COMPANIES_LOGO));
        company.setCard(c.getString(NUM_COL_COMPANIES_CARD));
        c.close();

        return company;
    }

    private Reduction cursorToReduction(Cursor c){
        if (c.getCount() == 0)
            return null;

        c.moveToFirst();
        Reduction reduction = new Reduction();
        reduction.setId(c.getInt(NUM_COL_REDUCTIONS_ID));
        reduction.setName(c.getString(NUM_COL_REDUCTIONS_NAME));
        reduction.setText(c.getString(NUM_COL_REDUCTIONS_TEXT));
        reduction.setSexe(c.getString(NUM_COL_REDUCTIONS_SEXE));
        reduction.setAge_relation(c.getString(NUM_COL_REDUCTIONS_AGE_RELATION));
        reduction.setAge_value(c.getInt(NUM_COL_REDUCTIONS_AGE_VALUE));
        reduction.setNb_points_relation(c.getString(NUM_COL_REDUCTIONS_NB_POINTS_RELATION));
        reduction.setNb_points_value(c.getInt(NUM_COL_REDUCTIONS_NB_POINTS_VALUE));
        reduction.setCity(c.getString(NUM_COL_REDUCTIONS_CITY));
        c.close();

        return reduction;
    }

    private People cursorToPeople2(Cursor c){
        if (c.getCount() == 0)
            return null;

        People people = new People();
        people.setId(c.getInt(NUM_COL_PEOPLE_ID));
        people.setUsername(c.getString(NUM_COL_PEOPLE_USERNAME));
        people.setPassword(c.getString(NUM_COL_PEOPLE_PASSWORD));
        people.setName(c.getString(NUM_COL_PEOPLE_NAME));
        people.setFirst_name(c.getString(NUM_COL_PEOPLE_FIRST_NAME));
        people.setSexe(c.getString(NUM_COL_PEOPLE_SEXE));
        people.setDate_of_birth(c.getString(NUM_COL_PEOPLE_DATE_OF_BIRTH));
        people.setMail(c.getString(NUM_COL_PEOPLE_MAIL));
        people.setCity(c.getString(NUM_COL_PEOPLE_CITY));

        return people;
    }

    private Company cursorToCompany2(Cursor c){
        if (c.getCount() == 0)
            return null;

        Company company = new Company();
        company.setId(c.getInt(NUM_COL_COMPANIES_ID));
        company.setName(c.getString(NUM_COL_COMPANIES_NAME));
        company.setLogo(c.getString(NUM_COL_COMPANIES_LOGO));
        company.setCard(c.getString(NUM_COL_COMPANIES_CARD));

        return company;
    }

    private Reduction cursorToReduction2(Cursor c){
        if (c.getCount() == 0)
            return null;

        Reduction reduction = new Reduction();
        reduction.setId(c.getInt(NUM_COL_REDUCTIONS_ID));
        reduction.setName(c.getString(NUM_COL_REDUCTIONS_NAME));
        reduction.setText(c.getString(NUM_COL_REDUCTIONS_TEXT));
        reduction.setSexe(c.getString(NUM_COL_REDUCTIONS_SEXE));
        reduction.setAge_relation(c.getString(NUM_COL_REDUCTIONS_AGE_RELATION));
        reduction.setAge_value(c.getInt(NUM_COL_REDUCTIONS_AGE_VALUE));
        reduction.setNb_points_relation(c.getString(NUM_COL_REDUCTIONS_NB_POINTS_RELATION));
        reduction.setNb_points_value(c.getInt(NUM_COL_REDUCTIONS_NB_POINTS_VALUE));
        reduction.setCity(c.getString(NUM_COL_REDUCTIONS_CITY));

        return reduction;
    }

    private Client cursorToClient2(Cursor c){
        if (c.getCount() == 0)
            return null;

        Client client = new Client();
        client.setId(c.getInt(NUM_COL_CLIENTS_ID));
        client.setId_peop(c.getInt(NUM_COL_CLIENTS_ID_PEOP));
        client.setId_comp(c.getInt(NUM_COL_CLIENTS_ID_COMP));
        client.setNum_client(c.getInt(NUM_COL_CLIENTS_NUM_CLIENT));
        client.setNb_loyalties(c.getInt(NUM_COL_CLIENTS_NB_LOYALTIES));
        client.setLast_used(c.getInt(NUM_COL_CLIENTS_LAST_USED));

        return client;
    }

    private Offer cursorToOffer2(Cursor c){
        if (c.getCount() == 0)
            return null;

        Offer offer = new Offer();
        offer.setId(c.getInt(NUM_COL_OFFERS_ID));
        offer.setId_comp(c.getInt(NUM_COL_OFFERS_ID_COMP));
        offer.setId_redu(c.getInt(NUM_COL_OFFERS_ID_REDU));

        return offer;
    }

    private Opportunity cursorToOpportunity2(Cursor c){
        if (c.getCount() == 0)
            return null;

        Opportunity opportunity = new Opportunity();
        opportunity.setId(c.getInt(NUM_COL_OPPORTUNITIES_ID));
        opportunity.setId_client(c.getInt(NUM_COL_OPPORTUNITIES_ID_CLIENT));
        opportunity.setId_redu(c.getInt(NUM_COL_OPPORTUNITIES_ID_REDU));

        return opportunity;
    }


/** FONCTIONS AFFICHAGE ***************************************************************************/
    public String showPeople() {
        Cursor c = bdd.rawQuery("SELECT * FROM " + TABLE_PEOPLE, null);
        String s = COL_PEOPLE_ID + " | " + COL_PEOPLE_USERNAME + " | " + COL_PEOPLE_PASSWORD + " | " + COL_PEOPLE_NAME + " | " + COL_PEOPLE_FIRST_NAME + " | " + COL_PEOPLE_SEXE + " | " + COL_PEOPLE_DATE_OF_BIRTH + " | " + COL_PEOPLE_MAIL + " | " + COL_PEOPLE_CITY + "\n";
        People people;

        if(c.getCount() == 0)
            return "Empty table";

        c.moveToFirst();

        do
        {
            people = cursorToPeople2(c);
            s += people.getId() + " | " + people.getUsername() + " | " + people.getPassword() + " | " + people.getName() + " | " + people.getFirst_name() + " | " + people.getSexe() + " | " + people.getDate_of_birth() + " | " + people.getMail() + " | " + people.getCity() + "\n";
        } while(c.moveToNext());

        c.close();

        return s;
    }

    public String showCompany() {
        Cursor c = bdd.rawQuery("SELECT * FROM " + TABLE_COMPANIES, null);
        String s = COL_COMPANIES_ID + " | " + COL_COMPANIES_NAME + " | " + COL_COMPANIES_LOGO + " | " + COL_COMPANIES_CARD + "\n";
        Company company;

        if(c.getCount() == 0)
            return "Empty table";

        c.moveToFirst();

        do
        {
            company = cursorToCompany2(c);
            s += company.getId() + " | " + company.getName() + " | " + company.getLogo() + " | " + company.getCard() + "\n";
        } while(c.moveToNext());

        c.close();

        return s;
    }

    public String showClient() {
        Cursor c = bdd.rawQuery("SELECT * FROM " + TABLE_CLIENTS, null);
        String s = COL_CLIENTS_ID + " | " + COL_CLIENTS_ID_PEOP + " | " + COL_CLIENTS_ID_COMP + " | " + COL_CLIENTS_NUM_CLIENT + " | " + COL_CLIENTS_NB_LOYALTIES + " | " + COL_CLIENTS_LAST_USED + "\n";
        Client client;

        if(c.getCount() == 0)
            return "Empty table";

        c.moveToFirst();

        do
        {
            client = cursorToClient2(c);
            s += client.getId() + " | " + client.getId_peop() + " | " + client.getId_comp() + " | " + client.getNum_client() + " | " + client.getNb_loyalties() + " | " + client.getLast_used() + "\n";
        } while(c.moveToNext());

        c.close();

        return s;
    }

    public String showReduction() {
        Cursor c = bdd.rawQuery("SELECT * FROM " + TABLE_REDUCTIONS, null);
        String s = COL_REDUCTIONS_ID + " | " + COL_REDUCTIONS_NAME + " | " + COL_REDUCTIONS_TEXT + " | " + COL_REDUCTIONS_SEXE + " | " + COL_REDUCTIONS_AGE_RELATION + " | " + COL_REDUCTIONS_AGE_VALUE + " | " + COL_REDUCTIONS_NB_POINTS_RELATION + " | " + COL_REDUCTIONS_NB_POINTS_VALUE + " | " + COL_REDUCTIONS_CITY + "\n";
        Reduction reduction;

        if(c.getCount() == 0)
            return "Empty table";

        c.moveToFirst();

        do
        {
            reduction = cursorToReduction2(c);
            s += reduction.getId() + " | " + reduction.getName() + " | " + reduction.getText() + " | " + reduction.getSexe() + " | " + reduction.getAge_relation() + " | " + reduction.getAge_value() + " | " + reduction.getNb_points_relation() + " | " + reduction.getNb_points_value() + " | " + reduction.getCity() + "\n";
        } while(c.moveToNext());

        c.close();

        return s;
    }

    public String showOffer() {
        Cursor c = bdd.rawQuery("SELECT * FROM " + TABLE_OFFERS, null);
        String s = COL_OFFERS_ID + " | " + COL_OFFERS_ID_COMP + " | " + COL_OFFERS_ID_REDU + "\n";
        Offer offer;

        if(c.getCount() == 0)
            return "Empty table";

        c.moveToFirst();

        do
        {
            offer = cursorToOffer2(c);
            s += offer.getId() + " | " + offer.getId_comp() + " | " + offer.getId_redu() + "\n";
        } while(c.moveToNext());

        c.close();

        return s;
    }

    public String showOpportunity() {
        Cursor c = bdd.rawQuery("SELECT * FROM " + TABLE_OPPORTUNITIES, null);
        String s = COL_OPPORTUNITIES_ID + " | " + COL_OPPORTUNITIES_ID_CLIENT + " | " + COL_OPPORTUNITIES_ID_REDU + "\n";
        Opportunity opportunity;

        if(c.getCount() == 0)
            return "Empty table";

        c.moveToFirst();

        do
        {
            opportunity = cursorToOpportunity2(c);
            s += opportunity.getId() + " | " + opportunity.getId_client() + " | " + opportunity.getId_redu() + "\n";
        } while(c.moveToNext());

        c.close();

        return s;
    }


/** FONCTIONS RECHERCHE ***************************************************************************/
    public String[] getCompaniesJoinedByPeople(int peopleId) {
        Cursor c = bdd.rawQuery("SELECT " + COL_COMPANIES_NAME + " FROM " + TABLE_COMPANIES
                + " WHERE " + COL_COMPANIES_ID + " IN (SELECT " + COL_CLIENTS_ID_COMP + " FROM " + TABLE_CLIENTS + " WHERE " + COL_CLIENTS_ID_PEOP + " = " + peopleId + ")", null);

        if(c.getCount() == 0)
            return null;

        int nbCompanies = c.getCount();
        String s[] = new String[nbCompanies];

        c.moveToFirst();

        for(int i = 0 ; i < nbCompanies ; i++)
        {
            s[i] = c.getString(0);
            c.moveToNext();
        }

        c.close();

        return s;
    }

    public String[] getAllowedReductions(int peopleId) {
        Cursor c = bdd.rawQuery("SELECT " + COL_REDUCTIONS_NAME + " FROM " + TABLE_REDUCTIONS
                + " WHERE " + COL_REDUCTIONS_ID + " IN (SELECT " + COL_OPPORTUNITIES_ID_REDU + " FROM " + TABLE_OPPORTUNITIES
                + " WHERE " + COL_OPPORTUNITIES_ID_CLIENT + " IN (SELECT " + COL_CLIENTS_ID_PEOP + " FROM " + TABLE_CLIENTS + " WHERE " + COL_CLIENTS_ID_PEOP + " = " + peopleId + "))", null);

        if(c.getCount() == 0)
            return null;

        int nbReductions = c.getCount();
        String s[] = new String[nbReductions];

        c.moveToFirst();

        for(int i = 0 ; i < nbReductions ; i++)
        {
            s[i] = c.getString(0);
            c.moveToNext();
        }

        c.close();

        return s;
    }

    public String[] getAllReductionsProvidedByCompany(int companyId) {
        Cursor c = bdd.rawQuery("SELECT " + COL_REDUCTIONS_TEXT + " FROM " + TABLE_REDUCTIONS
                + " WHERE " + COL_REDUCTIONS_ID + " IN (SELECT " + COL_OFFERS_ID_REDU + " FROM " + TABLE_OFFERS + " WHERE " + COL_OFFERS_ID_COMP + " = " + companyId + ")", null);

        if(c.getCount() == 0)
            return null;

        int nbRedu = c.getCount();
        String s[] = new String[nbRedu];

        c.moveToFirst();

        for(int i = 0 ; i < nbRedu ; i++)
        {
            s[i] = c.getString(0);
            c.moveToNext();
        }

        c.close();

        return s;
    }

    public String[] getAllPeopleInCompany(int companyId) {
        Cursor c = bdd.rawQuery("SELECT " + COL_PEOPLE_NAME + ", " + COL_PEOPLE_FIRST_NAME + " FROM " + TABLE_PEOPLE
                + " WHERE " + COL_PEOPLE_ID + " IN (SELECT " + COL_CLIENTS_ID_PEOP + " FROM " + TABLE_CLIENTS + " WHERE " + COL_CLIENTS_ID_COMP + " = " + companyId + ")", null);

        if(c.getCount() == 0)
            return null;

        int nbPeop = c.getCount();
        String s[] = new String[nbPeop];

        c.moveToFirst();

        for(int i = 0 ; i < nbPeop ; i++) {
            s[i] = c.getString(0) + " " + c.getString(1);
            c.moveToNext();
        }

        c.close();

        return s;
    }


/** FONCTIONS REFRESH *****************************************************************************/
    public Boolean opportunityMatching(int idPeop, int idRedu, int points) {
        People people = getPeopleWithId(idPeop);
        Reduction reduction = getReductionWithId(idRedu);

        if(!reduction.getSexe().equals("A") && !reduction.getSexe().equals(people.getSexe()))
            return false;

        int dateOfBirth[] = people.getSeparateDateOfBirth();
        Calendar calendar;
        calendar = Calendar.getInstance(TimeZone.getDefault());
        int age;

        if((dateOfBirth[1] < calendar.get(Calendar.MONTH)+1)
                || (dateOfBirth[1] == calendar.get(Calendar.MONTH)+1 && dateOfBirth[0] <= calendar.get(Calendar.DATE)))
            age = calendar.get(Calendar.YEAR) - dateOfBirth[2];
        else
            age = calendar.get(Calendar.YEAR) - dateOfBirth[2] - 1;

        if(!reduction.getAge_relation().equals("A")
                && !(reduction.getAge_relation().equals(">") && (age > reduction.getAge_value()))
                && !(reduction.getAge_relation().equals("<") && (age < reduction.getAge_value()))
                && !(reduction.getAge_relation().equals("=") && (age == reduction.getAge_value())))
            return false;

        if(!reduction.getNb_points_relation().equals("A")
                && !(reduction.getNb_points_relation().equals(">") && (points > reduction.getNb_points_value()))
                && !(reduction.getNb_points_relation().equals("<") && (points < reduction.getNb_points_value()))
                && !(reduction.getNb_points_relation().equals("=") && (points == reduction.getNb_points_value())))
            return false;

        return true;
    }

    public void updateOpportunities() {
        Cursor c = bdd.rawQuery("SELECT c." + COL_CLIENTS_ID_PEOP + ", c." + COL_CLIENTS_ID_COMP + ", c." + COL_CLIENTS_NUM_CLIENT + ", c." + COL_CLIENTS_NB_LOYALTIES + ", c." + COL_CLIENTS_ID + ", o." + COL_OFFERS_ID_REDU
                + " FROM " + TABLE_CLIENTS + " c, " + TABLE_OFFERS + " o"
                + " WHERE c." + COL_CLIENTS_ID_COMP + " = o." + COL_OFFERS_ID_COMP, null);

        if(c.getCount() == 0)
            return;

        int nbRelations = c.getCount();

        c.moveToFirst();

        for(int i = 0 ; i < nbRelations ; i++) {

            System.out.println("ICI : " + Integer.parseInt(c.getString(0)) + " / " + Integer.parseInt(c.getString(2)) + " / " + Integer.parseInt(c.getString(4)) + "\n");
            if(opportunityMatching(Integer.parseInt(c.getString(0)), Integer.parseInt(c.getString(4)), Integer.parseInt(c.getString(2)))) {
                Opportunity opportunity = new Opportunity(Integer.parseInt(c.getString(3)), Integer.parseInt(c.getString(4)));
                insertOpportunity(opportunity);
            }
            c.moveToNext();
        }

        c.close();
    }
}