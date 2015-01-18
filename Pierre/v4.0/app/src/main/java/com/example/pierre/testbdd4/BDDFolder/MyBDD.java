package com.example.pierre.testbdd4.BDDFolder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.pierre.testbdd4.BDDFolder.*;
import com.example.pierre.testbdd4.Objects.*;


public class MyBDD {
    private static final int VERSION_BDD = 1;
    private static final String NOM_BDD = "pst4_Bdd4.db";

    private static final String TABLE_COMPANY = "table_company";
    private static final String COL_COMPANY_ID = "ID";
    private static final int NUM_COL_COMPANY_ID = 0;
    private static final String COL_COMPANY_NAME = "NAME";
    private static final int NUM_COL_COMPANY_NAME = 1;

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

    private static final String TABLE_REDUCTION = "table_reduction";
    private static final String COL_REDUCTION_ID = "ID";
    private static final int NUM_COL_REDUCTION_ID = 0;
    private static final String COL_REDUCTION_TYPE = "NAME";
    private static final int NUM_COL_REDUCTION_TYPE = 1;

    private static final String TABLE_LINK = "table_link";
    private static final String COL_LINK_ID = "ID";
    private static final int NUM_COL_LINK_ID = 0;
    private static final String COL_LINK_IDCOMP = "ID_COMPANY";
    private static final int NUM_COL_LINK_IDCOMP = 1;
    private static final String COL_LINK_IDPEOP = "ID_PEOPLE";
    private static final int NUM_COL_LINK_IDPEOP = 2;
    private static final String COL_LINK_COMPT = "ID_COMPTEUR";
    private static final int NUM_COL_LINK_COMPT = 3;
    private static final String COL_LINK_IDREDU = "ID_REDUCTION";
    private static final int NUM_COL_LINK_IDREDU = 4;


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
    public long insertCompany(Company company){
        ContentValues values = new ContentValues();
        values.put(COL_COMPANY_NAME, company.getName());
        return bdd.insert(TABLE_COMPANY, null, values);
    }

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

    public long insertReduction(Reduction reduction){
        ContentValues values = new ContentValues();
        values.put(COL_REDUCTION_TYPE, reduction.getType());
        return bdd.insert(TABLE_REDUCTION, null, values);
    }

    public long insertLink(Link link){
        ContentValues values = new ContentValues();
        values.put(COL_LINK_IDCOMP, link.getIdComp());
        values.put(COL_LINK_IDPEOP, link.getIdPeop());
        values.put(COL_LINK_COMPT, link.getCompt());
        values.put(COL_LINK_IDREDU, link.getIdRedu());
        return bdd.insert(TABLE_LINK, null, values);
    }


/** FONCTIONS MISE A JOUR *************************************************************************/
    public int updateCompany(int id, Company company){
        ContentValues values = new ContentValues();
        values.put(COL_COMPANY_NAME, company.getName());
        return bdd.update(TABLE_COMPANY, values, COL_COMPANY_ID + " = " + id, null);
    }

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

    public int updateReduction(int id, Reduction reduction){
        ContentValues values = new ContentValues();
        values.put(COL_REDUCTION_TYPE, reduction.getType());
        return bdd.update(TABLE_REDUCTION, values, COL_REDUCTION_ID + " = " + id, null);
    }

    public int updateLink(int id, Link link){
        ContentValues values = new ContentValues();
        values.put(COL_LINK_IDCOMP, link.getIdComp());
        values.put(COL_LINK_IDPEOP, link.getIdPeop());
        values.put(COL_LINK_COMPT, link.getCompt());
        values.put(COL_LINK_IDREDU, link.getIdRedu());
        return bdd.update(TABLE_LINK, values, COL_LINK_ID + " = " + id, null);
    }


/** FONCTIONS SUPPRESSION *************************************************************************/
    public int removeCompanyWithID(int id){
        return bdd.delete(TABLE_COMPANY, COL_COMPANY_ID + " = " + id, null);
    }

    public int removePeopleWithID(int id){
        return bdd.delete(TABLE_PEOPLE, COL_PEOPLE_ID + " = " + id, null);
    }

    public int removeReductionWithID(int id){
        return bdd.delete(TABLE_REDUCTION, COL_REDUCTION_ID + " = " + id, null);
    }

    public int removeLinkWithID(int id){
        return bdd.delete(TABLE_LINK, COL_LINK_ID + " = " + id, null);
    }


/** FONCTIONS RECUPERATION ************************************************************************/
    public Company getCompanyWithId(int id){
        Cursor c = bdd.query(TABLE_COMPANY, new String[] {COL_COMPANY_ID, COL_COMPANY_NAME}, COL_COMPANY_ID + " LIKE \"" + id +"\"", null, null, null, null);
        return cursorToCompany(c);
    }

    public Company getCompanyWithName(String name){
        Cursor c = bdd.query(TABLE_COMPANY, new String[] {COL_COMPANY_ID, COL_COMPANY_NAME}, COL_COMPANY_NAME + " LIKE \"" + name +"\"", null, null, null, null);
        return cursorToCompany(c);
    }

    public People getPeopleWithId(int id) {
        Cursor c = bdd.query(TABLE_PEOPLE, new String[]{COL_PEOPLE_ID, COL_PEOPLE_USERNAME, COL_PEOPLE_PASSWORD, COL_PEOPLE_NAME, COL_PEOPLE_FIRST_NAME, COL_PEOPLE_SEXE, COL_PEOPLE_DATE_OF_BIRTH, COL_PEOPLE_ROLE}, COL_PEOPLE_ID + " LIKE \"" + id + "\"", null, null, null, null);
        return cursorToPeople(c);
    }

    public Reduction getReductionWithId(int id){
        Cursor c = bdd.query(TABLE_REDUCTION, new String[] {COL_REDUCTION_ID, COL_REDUCTION_TYPE}, COL_REDUCTION_ID + " LIKE \"" + id +"\"", null, null, null, null);
        return cursorToReduction(c);
    }

    public Link getLinkWithIdComp(int idComp){
        Cursor c = bdd.query(TABLE_LINK, new String[] {COL_LINK_ID, COL_LINK_IDCOMP, COL_LINK_IDPEOP, COL_LINK_COMPT, COL_LINK_IDREDU}, COL_LINK_IDCOMP + " LIKE \"" + idComp +"\"", null, null, null, null);
        return cursorToLink(c);
    }

    public Link getLinkWithIdPeop(int idPeop){
        Cursor c = bdd.query(TABLE_LINK, new String[] {COL_LINK_ID, COL_LINK_IDCOMP, COL_LINK_IDPEOP, COL_LINK_COMPT, COL_LINK_IDREDU}, COL_LINK_IDPEOP + " LIKE \"" + idPeop +"\"", null, null, null, null);
        return cursorToLink(c);
    }

    public Link getLinkWithIdRedu(int idRedu){
        Cursor c = bdd.query(TABLE_LINK, new String[] {COL_LINK_ID, COL_LINK_IDCOMP, COL_LINK_IDPEOP, COL_LINK_COMPT, COL_LINK_IDREDU}, COL_LINK_IDREDU + " LIKE \"" + idRedu +"\"", null, null, null, null);
        return cursorToLink(c);
    }


/** ALREADY EXISTS ********************************************************************************/
    public int getIdIfCompanyAlreadyExists(Company company){
        Cursor c = bdd.query(TABLE_COMPANY, new String[] {COL_COMPANY_ID, COL_COMPANY_NAME}, COL_COMPANY_NAME + " = \"" + company.getName() +"\"", null, null, null, null);

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

    public int getIdIfPeopleAlreadyExists(People people){
        Cursor c = bdd.query(TABLE_PEOPLE, new String[] {COL_PEOPLE_ID, COL_PEOPLE_USERNAME, COL_PEOPLE_PASSWORD, COL_PEOPLE_NAME, COL_PEOPLE_FIRST_NAME, COL_PEOPLE_SEXE, COL_PEOPLE_DATE_OF_BIRTH, COL_PEOPLE_ROLE}, COL_PEOPLE_USERNAME + " = \"" + people.getUsername() + "\""
                + " AND " + COL_PEOPLE_PASSWORD + " = \"" + people.getPassword() + "\""
                + " AND " + COL_PEOPLE_NAME + " = \"" + people.getName() + "\""
                + " AND " + COL_PEOPLE_FIRST_NAME + " = \"" + people.getFirstName() + "\""
                + " AND " + COL_PEOPLE_SEXE + " = \"" + people.getSexe() + "\""
                + " AND " + COL_PEOPLE_DATE_OF_BIRTH + " = \"" + people.getDateOfBirth() + "\""
                + " AND " + COL_PEOPLE_ROLE + " = \"" + people.getRole() + "\"", null, null, null, null);

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

    public int getPeopleIdWithUsername(String username){
        Cursor c = bdd.query(TABLE_PEOPLE, new String[] {COL_PEOPLE_ID, COL_PEOPLE_USERNAME, COL_PEOPLE_PASSWORD, COL_PEOPLE_NAME, COL_PEOPLE_FIRST_NAME, COL_PEOPLE_SEXE, COL_PEOPLE_DATE_OF_BIRTH, COL_PEOPLE_ROLE}, COL_PEOPLE_USERNAME + " = \"" + username + "\"", null, null, null, null);

        People tmp = cursorToPeople(c);
        if(tmp == null)
            return -1;
        return tmp.getId();
    }

    public People getPeopleWithUsername(String username){
        Cursor c = bdd.query(TABLE_PEOPLE, new String[] {COL_PEOPLE_ID, COL_PEOPLE_USERNAME, COL_PEOPLE_PASSWORD, COL_PEOPLE_NAME, COL_PEOPLE_FIRST_NAME, COL_PEOPLE_SEXE, COL_PEOPLE_DATE_OF_BIRTH, COL_PEOPLE_ROLE}, COL_PEOPLE_USERNAME + " = \"" + username + "\"", null, null, null, null);

        People tmp = cursorToPeople(c);
        return tmp;
    }

    public int getIdIfReductionAlreadyExists(Reduction reduction){
        Cursor c = bdd.query(TABLE_PEOPLE, new String[] {COL_REDUCTION_ID, COL_REDUCTION_TYPE}, COL_REDUCTION_TYPE + " = \"" + reduction.getType() +"\"", null, null, null, null);

        Reduction tmp = cursorToReduction(c);
        if(tmp == null)
            return -1;
        return tmp.getId();
    }

    public Boolean doesReductionAlreadyExists(String reduction){
        Cursor c = bdd.query(TABLE_REDUCTION, new String[] {COL_REDUCTION_ID, COL_REDUCTION_TYPE}, COL_REDUCTION_TYPE + " = \"" + reduction + "\"", null, null, null, null);

        Reduction tmp = cursorToReduction(c);
        if(tmp == null)
            return false;
        return true;
    }

    public int getIdIfLinkAlreadyExists(Link link){
        Cursor c = bdd.query(TABLE_LINK, new String[] {COL_LINK_ID, COL_LINK_IDCOMP, COL_LINK_IDPEOP, COL_LINK_COMPT, COL_LINK_IDREDU}, COL_LINK_IDCOMP + " = \"" + link.getIdComp() +"\""
                + " AND " + COL_LINK_IDPEOP + " = \"" + link.getIdPeop() + "\""
                + " AND " + COL_LINK_COMPT + " = \"" + link.getCompt() + "\""
                + " AND " + COL_LINK_IDREDU + " = \"" + link.getIdRedu() + "\"", null, null, null, null);

        Link tmp = cursorToLink(c);
        if(tmp == null)
            return -1;
        return tmp.getId();
    }


/** FONCTIONS CURSOR - Transforme un cursor en objet (Company, People, ...) ***********************/
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

    private Reduction cursorToReduction(Cursor c){
        if (c.getCount() == 0)
            return null;

        c.moveToFirst();
        Reduction reduction = new Reduction();
        reduction.setId(c.getInt(NUM_COL_REDUCTION_ID));
        reduction.setType(c.getString(NUM_COL_REDUCTION_TYPE));
        c.close();

        return reduction;
    }

    private Link cursorToLink(Cursor c){
        if (c.getCount() == 0)
            return null;

        c.moveToFirst();
        Link link = new Link();
        link.setId(c.getInt(NUM_COL_LINK_ID));
        link.setIdComp(c.getInt(NUM_COL_LINK_IDCOMP));
        link.setIdPeop(c.getInt(NUM_COL_LINK_IDPEOP));
        link.setCompt(c.getInt(NUM_COL_LINK_COMPT));
        link.setIdRedu(c.getInt(NUM_COL_LINK_IDREDU));
        c.close();

        return link;
    }

    private Company cursorToCompany2(Cursor c){
        if (c.getCount() == 0)
            return null;

        Company company = new Company();
        company.setId(c.getInt(NUM_COL_COMPANY_ID));
        company.setName(c.getString(NUM_COL_COMPANY_NAME));

        return company;
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

    private Reduction cursorToReduction2(Cursor c){
        if (c.getCount() == 0)
            return null;

        Reduction reduction = new Reduction();
        reduction.setId(c.getInt(NUM_COL_REDUCTION_ID));
        reduction.setType(c.getString(NUM_COL_REDUCTION_TYPE));

        return reduction;
    }

    private Link cursorToLink2(Cursor c){
        if (c.getCount() == 0)
            return null;

        Link link = new Link();
        link.setId(c.getInt(NUM_COL_LINK_ID));
        link.setIdComp(c.getInt(NUM_COL_LINK_IDCOMP));
        link.setIdPeop(c.getInt(NUM_COL_LINK_IDPEOP));
        link.setCompt(c.getInt(NUM_COL_LINK_COMPT));
        link.setIdRedu(c.getInt(NUM_COL_LINK_IDREDU));

        return link;
    }


/** FONCTIONS AFFICHAGE ***************************************************************************/
    public String showCompany() {
        Cursor c = bdd.rawQuery("SELECT * FROM " + TABLE_COMPANY, null);
        String s = new String(COL_COMPANY_ID + " | " + COL_COMPANY_NAME + "\n");
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

    public String showPeople() {
        Cursor c = bdd.rawQuery("SELECT * FROM " + TABLE_PEOPLE, null);
        String s = new String(COL_PEOPLE_ID + " | " + COL_PEOPLE_USERNAME + " | " + COL_PEOPLE_PASSWORD + " | " + COL_PEOPLE_NAME + " | " + COL_PEOPLE_FIRST_NAME + " | " + COL_PEOPLE_SEXE + " | " + COL_PEOPLE_DATE_OF_BIRTH + " | " + COL_PEOPLE_ROLE + "\n");
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

    public String showReduction() {
        Cursor c = bdd.rawQuery("SELECT * FROM " + TABLE_REDUCTION, null);
        String s = new String(COL_REDUCTION_ID + " | " + COL_REDUCTION_TYPE + "\n");
        Reduction reduction = new Reduction();

        if(c.getCount() == 0)
            return "Empty table";

        c.moveToFirst();

        do
        {
            reduction = cursorToReduction2(c);
            s += reduction.getId() + " | " + reduction.getType() + "\n";
        } while(c.moveToNext());

        c.close();

        return s;
    }

    public String showLink() {
        Cursor c = bdd.rawQuery("SELECT * FROM " + TABLE_LINK, null);
        String s = new String(COL_LINK_ID + " | " + COL_LINK_IDCOMP + " | " + COL_LINK_IDPEOP + " | " + COL_LINK_COMPT + " | " + COL_LINK_IDREDU + "\n");
        Link link = new Link();

        if(c.getCount() == 0)
            return "Empty table";

        c.moveToFirst();

        do
        {
            link = cursorToLink2(c);
            s += link.getId() + " | " + link.getIdComp() + " | " + link.getIdPeop() + " | " + link.getCompt() + " | " + link.getIdRedu() + "\n";
        } while(c.moveToNext());

        c.close();

        return s;
    }
}