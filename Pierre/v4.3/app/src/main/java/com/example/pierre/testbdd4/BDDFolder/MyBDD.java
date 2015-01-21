package com.example.pierre.testbdd4.BDDFolder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.pierre.testbdd4.BDDFolder.*;
import com.example.pierre.testbdd4.Objects.*;

import java.util.Calendar;
import java.util.TimeZone;


public class MyBDD {
    private static final int VERSION_BDD = 1;
    private static final String NOM_BDD = "pst4_Bdd5.db";

    private static final String TABLE_PEOPLE = "table_people";
    private static final String COL_PEOPLE_ID = "ID";
    private static final int NUM_COL_PEOPLE_ID = 0;
    private static final String COL_PEOPLE_USERNAME = "USERNAME";
    private static final int NUM_COL_PEOPLE_USERNAME = 1;
    private static final String COL_PEOPLE_PASSWORD = "PASSWORD";
    private static final int NUM_COL_PEOPLE_PASSWORD = 2;
    private static final String COL_PEOPLE_NAME = "NAME";
    private static final int NUM_COL_PEOPLE_NAME = 3;
    private static final String COL_PEOPLE_FIRST_NAME = "FIRST_NAME";
    private static final int NUM_COL_PEOPLE_FIRST_NAME = 4;
    private static final String COL_PEOPLE_SEXE = "SEXE";
    private static final int NUM_COL_PEOPLE_SEXE = 5;
    private static final String COL_PEOPLE_DATE_OF_BIRTH = "DATE_OF_BIRTH";
    private static final int NUM_COL_PEOPLE_DATE_OF_BIRTH = 6;
    private static final String COL_PEOPLE_ROLE = "ROLE";
    private static final int NUM_COL_PEOPLE_ROLE = 7;

    private static final String TABLE_COMPANY = "table_company";
    private static final String COL_COMPANY_ID = "ID";
    private static final int NUM_COL_COMPANY_ID = 0;
    private static final String COL_COMPANY_NAME = "NAME";
    private static final int NUM_COL_COMPANY_NAME = 1;

    private static final String TABLE_REDUCTION = "table_reduction";
    private static final String COL_REDUCTION_ID = "ID";
    private static final int NUM_COL_REDUCTION_ID = 0;
    private static final String COL_REDUCTION_NAME = "NAME";
    private static final int NUM_COL_REDUCTION_NAME = 1;
    private static final String COL_REDUCTION_TEXT = "TEXT";
    private static final int NUM_COL_REDUCTION_TEXT = 2;
    private static final String COL_REDUCTION_SEXE = "SEXE";
    private static final int NUM_COL_REDUCTION_SEXE = 3;
    private static final String COL_REDUCTION_AGE_RELATION = "AGE_RELATION";
    private static final int NUM_COL_REDUCTION_AGE_RELATION = 4;
    private static final String COL_REDUCTION_AGE_VALUE = "AGE_VALUE";
    private static final int NUM_COL_REDUCTION_AGE_VALUE = 5;
    private static final String COL_REDUCTION_NB_POINTS_RELATION = "NB_POINTS_RELATION";
    private static final int NUM_COL_REDUCTION_NB_POINTS_RELATION = 6;
    private static final String COL_REDUCTION_NB_POINTS_VALUE = "NB_POINTS_VALUE";
    private static final int NUM_COL_REDUCTION_NB_POINTS_VALUE = 7;

    private static final String TABLE_CLIENT = "table_client";
    private static final String COL_CLIENT_ID = "ID";
    private static final int NUM_COL_CLIENT_ID = 0;
    private static final String COL_CLIENT_IDPEOP = "ID_PEOPLE";
    private static final int NUM_COL_CLIENT_IDPEOP = 1;
    private static final String COL_CLIENT_IDCOMP = "ID_COMPANY";
    private static final int NUM_COL_CLIENT_IDCOMP = 2;
    private static final String COL_CLIENT_POINTS = "POINTS";
    private static final int NUM_COL_CLIENT_POINTS = 3;

    private static final String TABLE_OFFER = "table_offer";
    private static final String COL_OFFER_ID = "ID";
    private static final int NUM_COL_OFFER_ID = 0;
    private static final String COL_OFFER_IDCOMP = "ID_COMPANY";
    private static final int NUM_COL_OFFER_IDCOMP = 1;
    private static final String COL_OFFER_IDREDU = "ID_REDUCTION";
    private static final int NUM_COL_OFFER_IDREDU = 2;

    private static final String TABLE_OPPORTUNITY = "table_opportunity";
    private static final String COL_OPPORTUNITY_ID = "ID";
    private static final int NUM_COL_OPPORTUNITY_ID = 0;
    private static final String COL_OPPORTUNITY_IDCLIENT = "ID_CLIENT";
    private static final int NUM_COL_OPPORTUNITY_IDCLIENT = 1;
    private static final String COL_OPPORTUNITY_IDREDU = "ID_REDUCTION";
    private static final int NUM_COL_OPPORTUNITY_IDREDU = 2;


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
        values.put(COL_PEOPLE_FIRST_NAME, people.getFirstName());
        values.put(COL_PEOPLE_SEXE, people.getSexe());
        values.put(COL_PEOPLE_DATE_OF_BIRTH, people.getDateOfBirth());
        values.put(COL_PEOPLE_ROLE, people.getRole());
        return bdd.insert(TABLE_PEOPLE, null, values);
    }

    public long insertCompany(Company company){
        ContentValues values = new ContentValues();
        values.put(COL_COMPANY_NAME, company.getName());
        return bdd.insert(TABLE_COMPANY, null, values);
    }

    public long insertReduction(Reduction reduction){
        ContentValues values = new ContentValues();
        values.put(COL_REDUCTION_NAME, reduction.getName());
        values.put(COL_REDUCTION_TEXT, reduction.getText());
        values.put(COL_REDUCTION_SEXE, reduction.getSexe());
        values.put(COL_REDUCTION_AGE_RELATION, reduction.getAge_relation());
        values.put(COL_REDUCTION_AGE_VALUE, reduction.getAge_value());
        values.put(COL_REDUCTION_NB_POINTS_RELATION, reduction.getNb_points_relation());
        values.put(COL_REDUCTION_NB_POINTS_VALUE, reduction.getNb_points_value());
        return bdd.insert(TABLE_REDUCTION, null, values);
    }

    public long insertClient(Client client){
        ContentValues values = new ContentValues();
        values.put(COL_CLIENT_IDPEOP, client.getIdPeop());
        values.put(COL_CLIENT_IDCOMP, client.getIdComp());
        values.put(COL_CLIENT_POINTS, client.getPoints());
        return bdd.insert(TABLE_CLIENT, null, values);
    }

    public long insertOffer(Offer offer){
        ContentValues values = new ContentValues();
        values.put(COL_OFFER_IDCOMP, offer.getIdComp());
        values.put(COL_OFFER_IDREDU, offer.getIdRedu());
        return bdd.insert(TABLE_OFFER, null, values);
    }

    public long insertOpportunity(Opportunity opportunity){
        ContentValues values = new ContentValues();
        values.put(COL_OPPORTUNITY_IDCLIENT, opportunity.getIdClient());
        values.put(COL_OPPORTUNITY_IDREDU, opportunity.getIdRedu());
        return bdd.insert(TABLE_OPPORTUNITY, null, values);
    }


/** FONCTIONS MISE A JOUR *************************************************************************/
    public int updatePeople(int id, People people){
        ContentValues values = new ContentValues();
        values.put(COL_PEOPLE_USERNAME, people.getUsername());
        values.put(COL_PEOPLE_PASSWORD, people.getPassword());
        values.put(COL_PEOPLE_NAME, people.getName());
        values.put(COL_PEOPLE_FIRST_NAME, people.getFirstName());
        values.put(COL_PEOPLE_SEXE, people.getSexe());
        values.put(COL_PEOPLE_DATE_OF_BIRTH, people.getDateOfBirth());
        values.put(COL_PEOPLE_ROLE, people.getRole());
        return bdd.update(TABLE_PEOPLE, values, COL_PEOPLE_ID + " = " + id, null);
    }

    public int updateCompany(int id, Company company){
        ContentValues values = new ContentValues();
        values.put(COL_COMPANY_NAME, company.getName());
        return bdd.update(TABLE_COMPANY, values, COL_COMPANY_ID + " = " + id, null);
    }

    public int updateReduction(int id, Reduction reduction){
        ContentValues values = new ContentValues();
        values.put(COL_REDUCTION_NAME, reduction.getName());
        values.put(COL_REDUCTION_TEXT, reduction.getText());
        values.put(COL_REDUCTION_SEXE, reduction.getSexe());
        values.put(COL_REDUCTION_AGE_RELATION, reduction.getAge_relation());
        values.put(COL_REDUCTION_AGE_VALUE, reduction.getAge_value());
        values.put(COL_REDUCTION_NB_POINTS_RELATION, reduction.getNb_points_relation());
        values.put(COL_REDUCTION_NB_POINTS_VALUE, reduction.getNb_points_value());
        return bdd.update(TABLE_REDUCTION, values, COL_REDUCTION_ID + " = " + id, null);
    }

    public int updateClient(int id, Client client){
        ContentValues values = new ContentValues();
        values.put(COL_CLIENT_IDPEOP, client.getIdPeop());
        values.put(COL_CLIENT_IDCOMP, client.getIdComp());
        values.put(COL_CLIENT_POINTS, client.getPoints());
        return bdd.update(TABLE_CLIENT, values, COL_CLIENT_ID + " = " + id, null);
    }

    public int updateOffer(int id, Offer offer){
        ContentValues values = new ContentValues();
        values.put(COL_OFFER_IDCOMP, offer.getIdComp());
        values.put(COL_OFFER_IDREDU, offer.getIdRedu());
        return bdd.update(TABLE_OFFER, values, COL_OFFER_ID + " = " + id, null);
    }

    public long insertOpportunity(int id, Opportunity opportunity){
        ContentValues values = new ContentValues();
        values.put(COL_OPPORTUNITY_IDCLIENT, opportunity.getIdClient());
        values.put(COL_OPPORTUNITY_IDREDU, opportunity.getIdRedu());
        return bdd.update(TABLE_OPPORTUNITY, values, COL_OPPORTUNITY_ID + " = " + id, null);
    }


/** FONCTIONS SUPPRESSION *************************************************************************/
    public int removePeopleWithID(int id){
        return bdd.delete(TABLE_PEOPLE, COL_PEOPLE_ID + " = " + id, null);
    }

    public int removeCompanyWithID(int id){
        return bdd.delete(TABLE_COMPANY, COL_COMPANY_ID + " = " + id, null);
    }

    public int removeReductionWithID(int id){
        return bdd.delete(TABLE_REDUCTION, COL_REDUCTION_ID + " = " + id, null);
    }

    public int removeClientWithID(int id){
        return bdd.delete(TABLE_CLIENT, COL_CLIENT_ID + " = " + id, null);
    }

    public int removeOfferWithID(int id){
        return bdd.delete(TABLE_OFFER, COL_OFFER_ID + " = " + id, null);
    }

    public int removeOpportunityWithID(int id){
        return bdd.delete(TABLE_OPPORTUNITY, COL_OPPORTUNITY_ID + " = " + id, null);
    }


/** FONCTIONS RECUPERATION ************************************************************************/
    public People getPeopleWithId(int id) {
    Cursor c = bdd.query(TABLE_PEOPLE, new String[]{COL_PEOPLE_ID, COL_PEOPLE_USERNAME, COL_PEOPLE_PASSWORD, COL_PEOPLE_NAME, COL_PEOPLE_FIRST_NAME, COL_PEOPLE_SEXE, COL_PEOPLE_DATE_OF_BIRTH, COL_PEOPLE_ROLE}, COL_PEOPLE_ID + " LIKE \"" + id + "\"", null, null, null, null);
    return cursorToPeople(c);
}

    public People getPeopleWithUsername(String username){
        Cursor c = bdd.query(TABLE_PEOPLE, new String[] {COL_PEOPLE_ID, COL_PEOPLE_USERNAME, COL_PEOPLE_PASSWORD, COL_PEOPLE_NAME, COL_PEOPLE_FIRST_NAME, COL_PEOPLE_SEXE, COL_PEOPLE_DATE_OF_BIRTH, COL_PEOPLE_ROLE}, COL_PEOPLE_USERNAME + " = \"" + username + "\"", null, null, null, null);

        People tmp = cursorToPeople(c);
        return tmp;
    }

    public int getPeopleIdWithUsername(String username){
        Cursor c = bdd.query(TABLE_PEOPLE, new String[] {COL_PEOPLE_ID, COL_PEOPLE_USERNAME, COL_PEOPLE_PASSWORD, COL_PEOPLE_NAME, COL_PEOPLE_FIRST_NAME, COL_PEOPLE_SEXE, COL_PEOPLE_DATE_OF_BIRTH, COL_PEOPLE_ROLE}, COL_PEOPLE_USERNAME + " = \"" + username + "\"", null, null, null, null);

        People tmp = cursorToPeople(c);
        if(tmp == null)
            return -1;
        return tmp.getId();
    }

    public Boolean doesPeopleAlreadyExists(String username){
        Cursor c = bdd.query(TABLE_PEOPLE, new String[] {COL_PEOPLE_ID, COL_PEOPLE_USERNAME, COL_PEOPLE_PASSWORD, COL_PEOPLE_NAME, COL_PEOPLE_FIRST_NAME, COL_PEOPLE_SEXE, COL_PEOPLE_DATE_OF_BIRTH, COL_PEOPLE_ROLE}, COL_PEOPLE_USERNAME + " = \"" + username + "\"", null, null, null, null);

        People tmp = cursorToPeople(c);
        if(tmp == null)
            return false;
        return true;
    }

    public Company getCompanyWithId(int id){
        Cursor c = bdd.query(TABLE_COMPANY, new String[] {COL_COMPANY_ID, COL_COMPANY_NAME}, COL_COMPANY_ID + " LIKE \"" + id +"\"", null, null, null, null);
        return cursorToCompany(c);
    }

    public Company getCompanyWithName(String name){
        Cursor c = bdd.query(TABLE_COMPANY, new String[] {COL_COMPANY_ID, COL_COMPANY_NAME}, COL_COMPANY_NAME + " LIKE \"" + name +"\"", null, null, null, null);
        return cursorToCompany(c);
    }

    public int getCompanyIdWithName(String name){
        Cursor c = bdd.query(TABLE_COMPANY, new String[] {COL_COMPANY_ID, COL_COMPANY_NAME}, COL_COMPANY_NAME + " = \"" + name + "\"", null, null, null, null);

        Company tmp = cursorToCompany(c);
        if(tmp == null)
            return -1;
        return tmp.getId();
    }

    public Boolean doesCompanyAlreadyExists(String name){
        Cursor c = bdd.query(TABLE_COMPANY, new String[] {COL_COMPANY_ID, COL_COMPANY_NAME}, COL_COMPANY_NAME + " = \"" + name + "\"", null, null, null, null);

        Company tmp = cursorToCompany(c);
        if(tmp == null)
            return false;
        return true;
    }

    public Reduction getReductionWithId(int id){
        Cursor c = bdd.query(TABLE_REDUCTION, new String[] {COL_REDUCTION_ID, COL_REDUCTION_NAME, COL_REDUCTION_TEXT, COL_REDUCTION_SEXE, COL_REDUCTION_AGE_RELATION, COL_REDUCTION_AGE_VALUE, COL_REDUCTION_NB_POINTS_RELATION, COL_REDUCTION_NB_POINTS_VALUE}, COL_REDUCTION_ID + " LIKE \"" + id +"\"", null, null, null, null);
        return cursorToReduction(c);
    }

    public Boolean doesReductionAlreadyExists(String name){
        Cursor c = bdd.query(TABLE_REDUCTION, new String[] {COL_REDUCTION_ID, COL_REDUCTION_NAME, COL_REDUCTION_TEXT, COL_REDUCTION_SEXE, COL_REDUCTION_AGE_RELATION, COL_REDUCTION_AGE_VALUE, COL_REDUCTION_NB_POINTS_RELATION, COL_REDUCTION_NB_POINTS_VALUE}, COL_REDUCTION_NAME + " = \"" + name + "\"", null, null, null, null);

        Reduction tmp = cursorToReduction(c);
        if(tmp == null)
            return false;
        return true;
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
        people.setFirstName(c.getString(NUM_COL_PEOPLE_FIRST_NAME));
        people.setSexe(c.getString(NUM_COL_PEOPLE_SEXE));
        people.setDateOfBirth(c.getString(NUM_COL_PEOPLE_DATE_OF_BIRTH));
        people.setRole(c.getString(NUM_COL_PEOPLE_ROLE));
        c.close();

        return people;
    }

    private Company cursorToCompany(Cursor c){
        if (c.getCount() == 0)
            return null;

        c.moveToFirst();
        Company company = new Company();
        company.setId(c.getInt(NUM_COL_COMPANY_ID));
        company.setName(c.getString(NUM_COL_COMPANY_NAME));
        c.close();

        return company;
    }

    private Reduction cursorToReduction(Cursor c){
        if (c.getCount() == 0)
            return null;

        c.moveToFirst();
        Reduction reduction = new Reduction();
        reduction.setId(c.getInt(NUM_COL_REDUCTION_ID));
        reduction.setName(c.getString(NUM_COL_REDUCTION_NAME));
        reduction.setText(c.getString(NUM_COL_REDUCTION_TEXT));
        reduction.setSexe(c.getString(NUM_COL_REDUCTION_SEXE));
        reduction.setAge_relation(c.getString(NUM_COL_REDUCTION_AGE_RELATION));
        reduction.setAge_value(c.getInt(NUM_COL_REDUCTION_AGE_VALUE));
        reduction.setNb_points_relation(c.getString(NUM_COL_REDUCTION_NB_POINTS_RELATION));
        reduction.setNb_points_value(c.getInt(NUM_COL_REDUCTION_NB_POINTS_VALUE));
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
        people.setFirstName(c.getString(NUM_COL_PEOPLE_FIRST_NAME));
        people.setSexe(c.getString(NUM_COL_PEOPLE_SEXE));
        people.setDateOfBirth(c.getString(NUM_COL_PEOPLE_DATE_OF_BIRTH));
        people.setRole(c.getString(NUM_COL_PEOPLE_ROLE));

        return people;
    }

    private Company cursorToCompany2(Cursor c){
        if (c.getCount() == 0)
            return null;

        Company company = new Company();
        company.setId(c.getInt(NUM_COL_COMPANY_ID));
        company.setName(c.getString(NUM_COL_COMPANY_NAME));

        return company;
    }

    private Reduction cursorToReduction2(Cursor c){
        if (c.getCount() == 0)
            return null;

        Reduction reduction = new Reduction();
        reduction.setId(c.getInt(NUM_COL_REDUCTION_ID));
        reduction.setName(c.getString(NUM_COL_REDUCTION_NAME));
        reduction.setText(c.getString(NUM_COL_REDUCTION_TEXT));
        reduction.setSexe(c.getString(NUM_COL_REDUCTION_SEXE));
        reduction.setAge_relation(c.getString(NUM_COL_REDUCTION_AGE_RELATION));
        reduction.setAge_value(c.getInt(NUM_COL_REDUCTION_AGE_VALUE));
        reduction.setNb_points_relation(c.getString(NUM_COL_REDUCTION_NB_POINTS_RELATION));
        reduction.setNb_points_value(c.getInt(NUM_COL_REDUCTION_NB_POINTS_VALUE));

        return reduction;
    }

    private Client cursorToClient2(Cursor c){
        if (c.getCount() == 0)
            return null;

        Client client = new Client();
        client.setId(c.getInt(NUM_COL_CLIENT_ID));
        client.setIdPeop(c.getInt(NUM_COL_CLIENT_IDPEOP));
        client.setIdComp(c.getInt(NUM_COL_CLIENT_IDCOMP));
        client.setPoints(c.getInt(NUM_COL_CLIENT_POINTS));

        return client;
    }

    private Offer cursorToOffer2(Cursor c){
        if (c.getCount() == 0)
            return null;

        Offer offer = new Offer();
        offer.setId(c.getInt(NUM_COL_OFFER_ID));
        offer.setIdComp(c.getInt(NUM_COL_OFFER_IDCOMP));
        offer.setIdRedu(c.getInt(NUM_COL_OFFER_IDREDU));

        return offer;
    }

    private Opportunity cursorToOpportunity2(Cursor c){
        if (c.getCount() == 0)
            return null;

        Opportunity opportunity = new Opportunity();
        opportunity.setId(c.getInt(NUM_COL_OPPORTUNITY_ID));
        opportunity.setIdClient(c.getInt(NUM_COL_OPPORTUNITY_IDCLIENT));
        opportunity.setIdRedu(c.getInt(NUM_COL_OPPORTUNITY_IDREDU));

        return opportunity;
    }


/** FONCTIONS AFFICHAGE ***************************************************************************/
    public String showPeople() {
    Cursor c = bdd.rawQuery("SELECT * FROM " + TABLE_PEOPLE, null);
    String s = COL_PEOPLE_ID + " | " + COL_PEOPLE_USERNAME + " | " + COL_PEOPLE_PASSWORD + " | " + COL_PEOPLE_NAME + " | " + COL_PEOPLE_FIRST_NAME + " | " + COL_PEOPLE_SEXE + " | " + COL_PEOPLE_DATE_OF_BIRTH + " | " + COL_PEOPLE_ROLE + "\n";
    People people = new People();

    if(c.getCount() == 0)
        return "Empty table";

    c.moveToFirst();

    do
    {
        people = cursorToPeople2(c);
        s += people.getId() + " | " + people.getUsername() + " | " + people.getPassword() + " | " + people.getName() + " | " + people.getFirstName() + " | " + people.getSexe() + " | " + people.getDateOfBirth() + " | " + people.getRole() + "\n";
    } while(c.moveToNext());

    c.close();

    return s;
}

    public String showCompany() {
        Cursor c = bdd.rawQuery("SELECT * FROM " + TABLE_COMPANY, null);
        String s = COL_COMPANY_ID + " | " + COL_COMPANY_NAME + "\n";
        Company company = new Company();

        if(c.getCount() == 0)
            return "Empty table";

        c.moveToFirst();

        do
        {
            company = cursorToCompany2(c);
            s += company.getId() + " | " + company.getName() + "\n";
        } while(c.moveToNext());

        c.close();

        return s;
    }

    public String showReduction() {
        Cursor c = bdd.rawQuery("SELECT * FROM " + TABLE_REDUCTION, null);
        String s = COL_REDUCTION_ID + " | " + COL_REDUCTION_NAME + " | " + COL_REDUCTION_TEXT + " | " + COL_REDUCTION_SEXE + " | " + COL_REDUCTION_AGE_RELATION + " | " + COL_REDUCTION_AGE_VALUE + " | " + COL_REDUCTION_NB_POINTS_RELATION + " | " + COL_REDUCTION_NB_POINTS_VALUE + "\n";
        Reduction reduction = new Reduction();

        if(c.getCount() == 0)
            return "Empty table";

        c.moveToFirst();

        do
        {
            reduction = cursorToReduction2(c);
            s += reduction.getId() + " | " + reduction.getName() + " | " + reduction.getText() + " | " + reduction.getSexe() + " | " + reduction.getAge_relation() + " | " + reduction.getAge_value() + " | " + reduction.getNb_points_relation() + " | " + reduction.getNb_points_value() + "\n";
        } while(c.moveToNext());

        c.close();

        return s;
    }

    public String showClient() {
        Cursor c = bdd.rawQuery("SELECT * FROM " + TABLE_CLIENT, null);
        String s = COL_CLIENT_ID + " | " + COL_CLIENT_IDPEOP + " | " + COL_CLIENT_IDCOMP + " | " + COL_CLIENT_POINTS + "\n";
        Client client = new Client();

        if(c.getCount() == 0)
            return "Empty table";

        c.moveToFirst();

        do
        {
            client = cursorToClient2(c);
            s += client.getId() + " | " + client.getIdPeop() + " | " + client.getIdComp() + " | " + client.getPoints() + "\n";
        } while(c.moveToNext());

        c.close();

        return s;
    }

    public String showOffer() {
        Cursor c = bdd.rawQuery("SELECT * FROM " + TABLE_OFFER, null);
        String s = COL_OFFER_ID + " | " + COL_OFFER_IDCOMP + " | " + COL_OFFER_IDREDU + "\n";
        Offer offer = new Offer();

        if(c.getCount() == 0)
            return "Empty table";

        c.moveToFirst();

        do
        {
            offer = cursorToOffer2(c);
            s += offer.getId() + " | " + offer.getIdComp() + " | " + offer.getIdRedu() + "\n";
        } while(c.moveToNext());

        c.close();

        return s;
    }

    public String showOpportunity() {
        Cursor c = bdd.rawQuery("SELECT * FROM " + TABLE_OPPORTUNITY, null);
        String s = COL_OPPORTUNITY_ID + " | " + COL_OPPORTUNITY_IDCLIENT + " | " + COL_OPPORTUNITY_IDREDU + "\n";
        Opportunity opportunity = new Opportunity();

        if(c.getCount() == 0)
            return "Empty table";

        c.moveToFirst();

        do
        {
            opportunity = cursorToOpportunity2(c);
            s += opportunity.getId() + " | " + opportunity.getIdClient() + " | " + opportunity.getIdRedu() + "\n";
        } while(c.moveToNext());

        c.close();

        return s;
    }


/** FONCTIONS RECHERCHE ***************************************************************************/
    public String[] getCompaniesJoinedByPeople(int peopleId) {
        Cursor c = bdd.rawQuery("SELECT " + COL_COMPANY_NAME + " FROM " + TABLE_COMPANY
                + " WHERE " + COL_COMPANY_ID + " IN (SELECT " + COL_CLIENT_IDCOMP + " FROM " + TABLE_CLIENT + " WHERE " + COL_CLIENT_IDPEOP + " = " + peopleId + ")", null);

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
        Cursor c = bdd.rawQuery("SELECT " + COL_REDUCTION_NAME + " FROM " + TABLE_REDUCTION
                + " WHERE " + COL_REDUCTION_ID + " IN (SELECT " + COL_OPPORTUNITY_IDREDU + " FROM " + TABLE_OPPORTUNITY
                + " WHERE " + COL_OPPORTUNITY_IDCLIENT + " IN (SELECT " + COL_CLIENT_IDPEOP + " FROM " + TABLE_CLIENT + " WHERE " + COL_CLIENT_IDPEOP + " = " + peopleId + "))", null);

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
        Cursor c = bdd.rawQuery("SELECT " + COL_REDUCTION_TEXT + " FROM " + TABLE_REDUCTION
                + " WHERE " + COL_REDUCTION_ID + " IN (SELECT " + COL_OFFER_IDREDU + " FROM " + TABLE_OFFER + " WHERE " + COL_OFFER_IDCOMP + " = " + companyId + ")", null);

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
                + " WHERE " + COL_PEOPLE_ID + " IN (SELECT " + COL_CLIENT_IDPEOP + " FROM " + TABLE_CLIENT + " WHERE " + COL_CLIENT_IDCOMP + " = " + companyId + ")", null);

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
        Cursor c = bdd.rawQuery("SELECT c." + COL_CLIENT_IDPEOP + ", c." + COL_CLIENT_IDCOMP + ", c." + COL_CLIENT_POINTS + ", c." + COL_CLIENT_ID + ", o." + COL_OFFER_IDREDU
                + " FROM " + TABLE_CLIENT + " c, " + TABLE_OFFER + " o"
                + " WHERE c." + COL_CLIENT_IDCOMP + " = o." + COL_OFFER_IDCOMP, null);

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