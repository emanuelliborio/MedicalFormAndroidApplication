package com.example.michael.copaydirect;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

////////////////////////////////////////////////////////////////////////
//////////////Query & Create statements //////////////////////////////
///////////////////////////////////////////////////////////////////////
public class DatabaseOperations extends SQLiteOpenHelper {

    //soon to be 2
    public static final int database_version=1;

//TEXT specifies the datatype in mysqlite
// support for SQL foreign key constraints [was] introduced in SQLite version 3.6.19
//my android phone specs: api: 10 version: 2.3.3 Build: Gingerbread SQLite version: 3.6.22




    //soon mimic h2 sql create table structure and insertion process



    public String CREATE_ADMIN_TABLE_STATEMENT = "CREATE TABLE "+ TableData_Strings.TableInfo.ADMIN_TABLE_NAME+"("+
            TableData_Strings.TableInfo.ADMIN_ID+" TEXT NOT NULL, "+ TableData_Strings.TableInfo.ADMIN_EMAIL+" TEXT NOT NULL, "+
            TableData_Strings.TableInfo.ADMIN_PASSWORD+" TEXT NOT NULL, PRIMARY KEY("+ TableData_Strings.TableInfo.ADMIN_ID+"));";


    public String CREATE_USER_TABLE_STATEMENT = "CREATE TABLE "+ TableData_Strings.TableInfo.USERS_TABLE_NAME+"("+ TableData_Strings.TableInfo.USER_ID+" TEXT NOT NULL, "+
            TableData_Strings.TableInfo.USER_EMAIL+" TEXT NOT NULL,"+ TableData_Strings.TableInfo.USER_PASSWORD+" TEXT NOT NULL, PRIMARY KEY("+ TableData_Strings.TableInfo.USER_ID+"));";

        //composite primary key may be causing the errors
    //composite primary key to implement: service number and member id
    // PRIMARY KEY (column1, column2)


    //try three things:

    //1) Delete database files. Make one primary key, service number in ins ver pat table and two
    // foreign keys in daysheet pat table one to service number in ins ver pat table, and one to
    //user_id to user table

    //2) make service_number, dos month, dos_day, dos_year all the composite primary key for
    //daysheet table...still only have two separate single foreign keys one to serv num, and
    //one to user table user_id..and still have only one primary key serv num in ins ver pat table


    //2)  worst case scenario: make a composite foreign key from daysheet table to ins ver table's
    //service number and member id since ins ver table has a composite primary key
    //i would also need to update everything related to daysheet table with member id info, and pass
    //it from ins ver pat java class to editText slot in daysheet xml/java class and enter it in
    //daysheet table....try possible solution #1 first


    public String CREATE_INS_VER_PATIENT_TABLE_STATEMENT = "CREATE TABLE "+ TableData_Strings.TableInfo.INSURANCE_VERIFICATION_PATIENT_TABLE+
            "(" + TableData_Strings.TableInfo.INS_VER_SERVICE_NUMBER + " INTEGER PRIMARY KEY AUTOINCREMENT, "+ TableData_Strings.TableInfo.INS_VER_MEMBER_ID +" TEXT NOT NULL, "+
            TableData_Strings.TableInfo.INS_VER_PATIENT_F_NAME + " TEXT NOT NULL, "+ TableData_Strings.TableInfo.INS_VER_PATIENT_L_NAME + " TEXT NOT NULL, "+
            TableData_Strings.TableInfo.INS_VER_D_O_B_MONTH + " INTEGER, " + TableData_Strings.TableInfo.INS_VER_D_O_B_DAY + " INTEGER, "+
            TableData_Strings.TableInfo.INS_VER_D_O_B_YEAR + " INTEGER, " + TableData_Strings.TableInfo.INS_VER_NEXT_APPT_MONTH + " INTEGER, "+
            TableData_Strings.TableInfo.INS_VER_NEXT_APPT_DAY + " INTEGER, " + TableData_Strings.TableInfo.INS_VER_NEXT_APPT_YEAR + " INTEGER, "+
            TableData_Strings.TableInfo.INSURANCE_NAME + " TEXT, "+
            TableData_Strings.TableInfo.INS_VER_USER_ID + " TEXT NOT NULL, FOREIGN KEY(" + TableData_Strings.TableInfo.INS_VER_USER_ID + ") REFERENCES "+
            TableData_Strings.TableInfo.USERS_TABLE_NAME + "(" + TableData_Strings.TableInfo.USER_ID + "));";

    // In MySQlite, INTEGER PRIMARY KEY with no value entered = AUTO_INCREMENT
    //Try running this program with INTEGER PRIMARY KEY without NOT NULL so that you can try to insert a null
    // value which will be replaced by the sqlite_sequence table containing the next row_id or auto_increment value

    //1 to 1 relationship: Service numbers match for same person / same visit
    //possible future sample query... select * from table 1, table2 where service_number =1;
            public String CREATE_DAYSHEET_PATIENT_TABLE_STATEMENT="CREATE TABLE "+ TableData_Strings.TableInfo.DAYSHEET_PATIENT_TABLE_NAME+
            "("+ TableData_Strings.TableInfo.DAYSHEET_SERVICE_NUMBER+" INTEGER PRIMARY KEY AUTOINCREMENT, "+TableData_Strings.TableInfo.DAYSHEET_F_NAME + " TEXT NOT NULL, "+
            TableData_Strings.TableInfo.DAYSHEET_L_NAME + " TEXT NOT NULL, "+ TableData_Strings.TableInfo.DAYSHEET_D_O_S_MONTH+" INTEGER NOT NULL, "+
            TableData_Strings.TableInfo.DAYSHEET_D_O_S_DAY +" INTEGER NOT NULL, "+ TableData_Strings.TableInfo.DAYSHEET_D_O_S_YEAR+" INTEGER NOT NULL, "+
            TableData_Strings.TableInfo.DAYSHEET_MEDICARE_PATIENT+" TEXT, "+ TableData_Strings.TableInfo.DAYSHEET_CPT_CODE1+" INTEGER, "+
            TableData_Strings.TableInfo.DAYSHEET_CPT_CODE2+" INTEGER, "+ TableData_Strings.TableInfo.DAYSHEET_CPT_CODE3+" INTEGER, "+
            TableData_Strings.TableInfo.DAYSHEET_CPT_CODE4+" INTEGER, "+ TableData_Strings.TableInfo.DAYSHEET_DIAGNOSIS_CODE1+
            " TEXT, "+ TableData_Strings.TableInfo.DAYSHEET_DIAGNOSIS_CODE2+" TEXT, " +
            TableData_Strings.TableInfo.DAYSHEET_DIAGNOSIS_CODE3 + " TEXT, " + TableData_Strings.TableInfo.DAYSHEET_DIAGNOSIS_CODE4 + " TEXT, " +
            TableData_Strings.TableInfo.DAYSHEET_COPAY_AMOUNT + " TEXT, " + TableData_Strings.TableInfo.DAYSHEET_METHOD_OF_PAYMENT + " TEXT, " +
            TableData_Strings.TableInfo.DAYSHEET_NEXT_APPT_MONTH + " INTEGER, " + TableData_Strings.TableInfo.DAYSHEET_NEXT_APPT_DAY + " INTEGER, " +
            TableData_Strings.TableInfo.DAYSHEET_NEXT_APPT_YEAR + " INTEGER, "+
            TableData_Strings.TableInfo.DAYSHEET_USER_ID + " TEXT NOT NULL, FOREIGN KEY("+ TableData_Strings.TableInfo.DAYSHEET_SERVICE_NUMBER + ") REFERENCES " + TableData_Strings.TableInfo.INSURANCE_VERIFICATION_PATIENT_TABLE+"("+
            TableData_Strings.TableInfo.INS_VER_SERVICE_NUMBER +"), FOREIGN KEY("+ TableData_Strings.TableInfo.DAYSHEET_USER_ID +
            ") REFERENCES " + TableData_Strings.TableInfo.USERS_TABLE_NAME + "(" + TableData_Strings.TableInfo.USER_ID+"));";


    //runs 1st
    public DatabaseOperations(Context context) {
        //not using console factory objects = null
        super(context, TableData_Strings.TableInfo.DATABASE_NAME, null, database_version);
        //where is this printed?
        Log.d("Database operations", "Copay Database created!");

    }

    //runs 2nd
    @Override
    public void onOpen(SQLiteDatabase sdb) {
        super.onOpen(sdb);
        if (!sdb.isReadOnly()) {
            // Enable foreign key constraints
            sdb.execSQL("PRAGMA foreign_keys=ON;");
            Log.d("Database operations", "foreign keys activated!");

        }
    }


   // onCreate(SQLiteDatabase database) – is the method which is called first time when the database is
   // created and we need to use this method to create the tables and populate it as per the need.
    @Override
    public void onCreate(SQLiteDatabase sdb) {
        //enable foreign keys here too
        onOpen(sdb);

        //execute create table statements

        sdb.execSQL(CREATE_ADMIN_TABLE_STATEMENT);
        //check status of database operation
        Log.d("Database operations", "Admin Table Created Once");


        sdb.execSQL("INSERT INTO "+ TableData_Strings.TableInfo.ADMIN_TABLE_NAME+" VALUES('mscholzjr','mscholzjr@outlook.com','3lsr');");
        Log.d("Database operations", "Admin Account Added Once");


        //Parent Table
        sdb.execSQL(CREATE_USER_TABLE_STATEMENT);
        //check status of database operation
        Log.d("Database operations", "User Table Created Once");
        //Parent Table


        sdb.execSQL(CREATE_INS_VER_PATIENT_TABLE_STATEMENT);
        //check status of database operation
        Log.d("Database operations", "Patient Ins Ver Table Created Once");

        //Child Table
        sdb.execSQL(CREATE_DAYSHEET_PATIENT_TABLE_STATEMENT);
        //check status of database operation
        Log.d("Database operations", "Patient Daysheet Table Created Once");
        //Child Table
    }   //ends onCreate method

    @Override
    public void onUpgrade(SQLiteDatabase db_instance, int oldVersion, int newVersion) {
        onOpen(db_instance);
    }


    public void call_put_admin(){


    }
    public void put_Administrator_Table_Info(DatabaseOperations dopsAd, String admin_id, String admin_email, String admin_pass) {
        SQLiteDatabase SQAdmin = dopsAd.getWritableDatabase();
        ContentValues content_admin_values = new ContentValues();

        //put (key, value) under each specified column
        content_admin_values.put(TableData_Strings.TableInfo.ADMIN_ID, admin_id);
        content_admin_values.put(TableData_Strings.TableInfo.ADMIN_EMAIL, admin_email);
        content_admin_values.put(TableData_Strings.TableInfo.ADMIN_PASSWORD, admin_pass);


        //3 parameters: tablename, not using null column for null value so we only put "null",
        // finally content values object
        //if successful return null put in long variable
        long k = SQAdmin.insert(TableData_Strings.TableInfo.ADMIN_TABLE_NAME, null, content_admin_values);
        Log.d("Database operations", "One row inserted in Admin Table");
    }




    public void put_User_Table_Info(DatabaseOperations dopsU, String id, String email, String pass) {
        SQLiteDatabase SQU = dopsU.getWritableDatabase();
        ContentValues content_user_values = new ContentValues();

        //put (key, value) under each specified column
        content_user_values.put(TableData_Strings.TableInfo.USER_ID, id);
        content_user_values.put(TableData_Strings.TableInfo.USER_EMAIL, email);
        content_user_values.put(TableData_Strings.TableInfo.USER_PASSWORD, pass);
        //3 parameters: tablename, not using null column for null value so we only put "null", finally content values object
        //if successful return null put in long variable
        long k = SQU.insert(TableData_Strings.TableInfo.USERS_TABLE_NAME, null, content_user_values);
        Log.d("Database operations", "One row inserted in Users Table");
    }  //ends putUserInformation method




    //two ways to to this....

    // 1) without the int service number parameter all together

    //and then view the print out of the auto generated service number

    //print out was zero each time... 35 event log errors. foreign key mismatch


    //now restore all service number params

    // 2) with an "empty null service_number parameter passed

        //int empty param needs to be restored in: put_Patient_Ins_Ver_Form_Table_Info()method param (two places),
        // two more places in patient ins_ver_ form java class in the method call parameters
         //restored
    //and then view the print out of the auto generated service number

    //3) other thing to check for: parallel inserts
    //and then view the print out of the auto generated service number


    //4)how exactly i am retrieving the int primary key (service_number) null to auto increment value
    //and then view the print out of the auto generated service number

//,
    public void put_Patient_Ins_Ver_Form_Table_Info(DatabaseOperations dopsIVP, String member_id, String fir_name,
                                            String las_name, int Dob_Month, int Dob_Day,
                                            int Dob_Year, int Next_Ver_Dos_Month, int Next_Ver_Dos_Day,
                                            int Next_Ver_Dos_Year, String ins_name,
                                            String user_id)
    {
        SQLiteDatabase SQP = dopsIVP.getWritableDatabase();
        ContentValues content_patient_ver_values = new ContentValues();
       // content_patient_ver_values.put(TableData_Strings.TableInfo.INS_VER_SERVICE_NUMBER, service_number);
        content_patient_ver_values.put(TableData_Strings.TableInfo.INS_VER_MEMBER_ID, member_id);
        content_patient_ver_values.put(TableData_Strings.TableInfo.INS_VER_PATIENT_F_NAME, fir_name);
        content_patient_ver_values.put(TableData_Strings.TableInfo.INS_VER_PATIENT_L_NAME, las_name);
        content_patient_ver_values.put(TableData_Strings.TableInfo.INS_VER_D_O_B_MONTH, Dob_Month);
        content_patient_ver_values.put(TableData_Strings.TableInfo.INS_VER_D_O_B_DAY, Dob_Day);
        content_patient_ver_values.put(TableData_Strings.TableInfo.INS_VER_D_O_B_YEAR, Dob_Year);
        content_patient_ver_values.put(TableData_Strings.TableInfo.INS_VER_NEXT_APPT_MONTH, Next_Ver_Dos_Month);
        content_patient_ver_values.put(TableData_Strings.TableInfo.INS_VER_NEXT_APPT_DAY, Next_Ver_Dos_Day);
        content_patient_ver_values.put(TableData_Strings.TableInfo.INS_VER_NEXT_APPT_YEAR, Next_Ver_Dos_Year);
        content_patient_ver_values.put(TableData_Strings.TableInfo.INSURANCE_NAME, ins_name);
        content_patient_ver_values.put(TableData_Strings.TableInfo.INS_VER_USER_ID, user_id);

        long k = SQP.insert(TableData_Strings.TableInfo.INSURANCE_VERIFICATION_PATIENT_TABLE, null, content_patient_ver_values);
        Log.d("Database operations", "One row inserted in Insurance Verification Table");
    }  //ends putInsVerPatientInformation method




    public void put_Patient_Daysheet_Form_Table_Info(DatabaseOperations dopsP, String Patient_First_Name, String Patient_Last_Name, int Dos_Month, int Dos_Day,
                                                     int Dos_Year, String Medicare_Patient,
                                                     int CPT_Code_1, int CPT_Code_2, int CPT_Code_3, int CPT_Code_4,
                                                     String D_Code_1, String D_Code_2, String D_Code_3, String D_Code_4, String Copay_Amount,
                                                     String Method_of_Payment, int Next_Dos_Month, int Next_Dos_Day, int Next_Dos_Year,
                                                     String user_id)
    {
        SQLiteDatabase SQP = dopsP.getWritableDatabase();
        ContentValues content_patient_values = new ContentValues();
        content_patient_values.put(TableData_Strings.TableInfo.DAYSHEET_F_NAME, Patient_First_Name);
        content_patient_values.put(TableData_Strings.TableInfo.DAYSHEET_L_NAME, Patient_Last_Name);
        content_patient_values.put(TableData_Strings.TableInfo.DAYSHEET_D_O_S_MONTH, Dos_Month);
        content_patient_values.put(TableData_Strings.TableInfo.DAYSHEET_D_O_S_DAY, Dos_Day);
        content_patient_values.put(TableData_Strings.TableInfo.DAYSHEET_D_O_S_YEAR, Dos_Year);
        content_patient_values.put(TableData_Strings.TableInfo.DAYSHEET_MEDICARE_PATIENT, Medicare_Patient);
        content_patient_values.put(TableData_Strings.TableInfo.DAYSHEET_CPT_CODE1, CPT_Code_1);
        content_patient_values.put(TableData_Strings.TableInfo.DAYSHEET_CPT_CODE2, CPT_Code_2);
        content_patient_values.put(TableData_Strings.TableInfo.DAYSHEET_CPT_CODE3, CPT_Code_3);
        content_patient_values.put(TableData_Strings.TableInfo.DAYSHEET_CPT_CODE4, CPT_Code_4);
        content_patient_values.put(TableData_Strings.TableInfo.DAYSHEET_DIAGNOSIS_CODE1, D_Code_1);
        content_patient_values.put(TableData_Strings.TableInfo.DAYSHEET_DIAGNOSIS_CODE2, D_Code_2);
        content_patient_values.put(TableData_Strings.TableInfo.DAYSHEET_DIAGNOSIS_CODE3, D_Code_3);
        content_patient_values.put(TableData_Strings.TableInfo.DAYSHEET_DIAGNOSIS_CODE4, D_Code_4);
        content_patient_values.put(TableData_Strings.TableInfo.DAYSHEET_COPAY_AMOUNT, Copay_Amount);
        content_patient_values.put(TableData_Strings.TableInfo.DAYSHEET_METHOD_OF_PAYMENT, Method_of_Payment);
        content_patient_values.put(TableData_Strings.TableInfo.DAYSHEET_NEXT_APPT_MONTH, Next_Dos_Month);
        content_patient_values.put(TableData_Strings.TableInfo.DAYSHEET_NEXT_APPT_DAY, Next_Dos_Day);
        content_patient_values.put(TableData_Strings.TableInfo.DAYSHEET_NEXT_APPT_YEAR, Next_Dos_Year);
        content_patient_values.put(TableData_Strings.TableInfo.DAYSHEET_USER_ID, user_id);

        //3 parameters: tablename, not using null column for null value so we only put "null", finally content values object
        //if successful return null put in long variable
        long k = SQP.insert(TableData_Strings.TableInfo.DAYSHEET_PATIENT_TABLE_NAME, null, content_patient_values);
        Log.d("Database operations", "One row inserted in Daysheet Patients Table");
    }//ends putDaysheetPatientInformation method


    public Cursor getAdminInformation(DatabaseOperations dop) {
        SQLiteDatabase SQ_ALL_Admin = dop.getReadableDatabase();
        String[] columns_array = {TableData_Strings.TableInfo.ADMIN_ID, TableData_Strings.TableInfo.ADMIN_EMAIL, TableData_Strings.TableInfo.ADMIN_PASSWORD};
        //default parameters:(table, columns, selection("where" clause), selectionArgs, "groupBy" clause, "having" clause, "orderBy" clause
        Cursor CR = SQ_ALL_Admin.query(TableData_Strings.TableInfo.ADMIN_TABLE_NAME, columns_array, null, null, null, null, null);
        return CR;                                             }

    public Cursor getUserInformation(DatabaseOperations dop) {
        SQLiteDatabase SQ_ALL_USERS = dop.getReadableDatabase();
        String[] columns_array = {TableData_Strings.TableInfo.USER_ID, TableData_Strings.TableInfo.USER_EMAIL, TableData_Strings.TableInfo.USER_PASSWORD};
        //default parameters:(table, columns, selection("where" clause), selectionArgs, "groupBy" clause, "having" clause, "orderBy" clause
        Cursor CR = SQ_ALL_USERS.query(TableData_Strings.TableInfo.USERS_TABLE_NAME, columns_array, null, null, null, null, null);
        return CR;                                           }


    public Cursor getAllInsVerPatientInformation(DatabaseOperations dop) {
        SQLiteDatabase SQ = dop.getReadableDatabase();
        String[] columns_array = {TableData_Strings.TableInfo.INS_VER_SERVICE_NUMBER, TableData_Strings.TableInfo.INS_VER_MEMBER_ID,
                TableData_Strings.TableInfo.INS_VER_PATIENT_F_NAME, TableData_Strings.TableInfo.INS_VER_PATIENT_L_NAME, TableData_Strings.TableInfo.INS_VER_D_O_B_MONTH,
                TableData_Strings.TableInfo.INS_VER_D_O_B_DAY, TableData_Strings.TableInfo.INS_VER_D_O_B_YEAR, TableData_Strings.TableInfo.INS_VER_NEXT_APPT_MONTH,
                TableData_Strings.TableInfo.INS_VER_NEXT_APPT_DAY, TableData_Strings.TableInfo.INS_VER_NEXT_APPT_YEAR, TableData_Strings.TableInfo.INSURANCE_NAME, TableData_Strings.TableInfo.INS_VER_USER_ID};
        //default parameters:(table, columns, selection("where" clause), selectionArgs, "groupBy" clause, "having" clause, "orderBy" clause
        Cursor CR = SQ.query(TableData_Strings.TableInfo.INSURANCE_VERIFICATION_PATIENT_TABLE, columns_array, null, null, null, null, null);
        return CR;
    }



    public Cursor getAllDaysheetPatientInformation(DatabaseOperations dop) {
        SQLiteDatabase SQ_ALL_DAY = dop.getReadableDatabase();
        String[] columns_array = {TableData_Strings.TableInfo.DAYSHEET_SERVICE_NUMBER, TableData_Strings.TableInfo.DAYSHEET_F_NAME, TableData_Strings.TableInfo.DAYSHEET_L_NAME,
                TableData_Strings.TableInfo.DAYSHEET_D_O_S_MONTH,TableData_Strings.TableInfo.DAYSHEET_D_O_S_DAY, TableData_Strings.TableInfo.DAYSHEET_D_O_S_YEAR,
                TableData_Strings.TableInfo.DAYSHEET_MEDICARE_PATIENT,TableData_Strings.TableInfo.DAYSHEET_CPT_CODE1,
                TableData_Strings.TableInfo.DAYSHEET_CPT_CODE2, TableData_Strings.TableInfo.DAYSHEET_CPT_CODE3, TableData_Strings.TableInfo.DAYSHEET_CPT_CODE4,
                TableData_Strings.TableInfo.DAYSHEET_DIAGNOSIS_CODE1, TableData_Strings.TableInfo.DAYSHEET_DIAGNOSIS_CODE2,
                TableData_Strings.TableInfo.DAYSHEET_DIAGNOSIS_CODE3, TableData_Strings.TableInfo.DAYSHEET_DIAGNOSIS_CODE4, TableData_Strings.TableInfo.DAYSHEET_COPAY_AMOUNT,
                TableData_Strings.TableInfo.DAYSHEET_METHOD_OF_PAYMENT, TableData_Strings.TableInfo.DAYSHEET_NEXT_APPT_MONTH, TableData_Strings.TableInfo.DAYSHEET_NEXT_APPT_DAY,
                TableData_Strings.TableInfo.DAYSHEET_NEXT_APPT_YEAR, TableData_Strings.TableInfo.DAYSHEET_USER_ID};
        //default parameters:(table, columns, selection("where" clause), selectionArgs, "groupBy" clause, "having" clause, "orderBy" clause
        Cursor CR = SQ_ALL_DAY.query(TableData_Strings.TableInfo.DAYSHEET_PATIENT_TABLE_NAME, columns_array, null, null, null, null, null);
        return CR;
    }


    public Cursor getMyInsVerPatientInformation(DatabaseOperations dop, String id) {
        SQLiteDatabase SQ_MY_INS = dop.getReadableDatabase();
        String[] columns_array = {TableData_Strings.TableInfo.INS_VER_SERVICE_NUMBER, TableData_Strings.TableInfo.INS_VER_MEMBER_ID,
                TableData_Strings.TableInfo.INS_VER_PATIENT_F_NAME, TableData_Strings.TableInfo.INS_VER_PATIENT_L_NAME, TableData_Strings.TableInfo.INS_VER_D_O_B_MONTH,
                TableData_Strings.TableInfo.INS_VER_D_O_B_DAY, TableData_Strings.TableInfo.INS_VER_D_O_B_YEAR, TableData_Strings.TableInfo.INS_VER_NEXT_APPT_MONTH,
                TableData_Strings.TableInfo.INS_VER_NEXT_APPT_DAY, TableData_Strings.TableInfo.INS_VER_NEXT_APPT_YEAR, TableData_Strings.TableInfo.INSURANCE_NAME,
                TableData_Strings.TableInfo.INS_VER_USER_ID};

        String whereClause = TableData_Strings.TableInfo.INS_VER_USER_ID+" = ?";
        String[] whereArgs = new String[] {id};

        //default parameters:(table, columns, selection("where" clause), selectionArgs, "groupBy" clause, "having" clause, "orderBy" clause
        Cursor CR = SQ_MY_INS.query(TableData_Strings.TableInfo.INSURANCE_VERIFICATION_PATIENT_TABLE, columns_array, whereClause, whereArgs, null, null, null);
        return CR;                                                                  }  //ends getMyInsVerPatientInformation() method

    public Cursor getMyDaysheetPatientInformation(DatabaseOperations dop, String id) {
        SQLiteDatabase SQ_MY_DAY = dop.getReadableDatabase();
        String[] columns_array = {TableData_Strings.TableInfo.DAYSHEET_SERVICE_NUMBER,  TableData_Strings.TableInfo.DAYSHEET_D_O_S_MONTH,
                TableData_Strings.TableInfo.DAYSHEET_D_O_S_DAY, TableData_Strings.TableInfo.DAYSHEET_D_O_S_YEAR, TableData_Strings.TableInfo.DAYSHEET_MEDICARE_PATIENT,
                TableData_Strings.TableInfo.DAYSHEET_F_NAME, TableData_Strings.TableInfo.DAYSHEET_L_NAME, TableData_Strings.TableInfo.DAYSHEET_CPT_CODE1,
                TableData_Strings.TableInfo.DAYSHEET_CPT_CODE2, TableData_Strings.TableInfo.DAYSHEET_CPT_CODE3, TableData_Strings.TableInfo.DAYSHEET_CPT_CODE4,
                TableData_Strings.TableInfo.DAYSHEET_DIAGNOSIS_CODE1, TableData_Strings.TableInfo.DAYSHEET_DIAGNOSIS_CODE2,
                TableData_Strings.TableInfo.DAYSHEET_DIAGNOSIS_CODE3, TableData_Strings.TableInfo.DAYSHEET_DIAGNOSIS_CODE4, TableData_Strings.TableInfo.DAYSHEET_COPAY_AMOUNT,
                TableData_Strings.TableInfo.DAYSHEET_METHOD_OF_PAYMENT, TableData_Strings.TableInfo.DAYSHEET_NEXT_APPT_MONTH, TableData_Strings.TableInfo.DAYSHEET_NEXT_APPT_DAY,
                TableData_Strings.TableInfo.DAYSHEET_NEXT_APPT_YEAR, TableData_Strings.TableInfo.DAYSHEET_USER_ID};

        String whereClause = TableData_Strings.TableInfo.DAYSHEET_USER_ID+" = ?";
        String[] whereArgs = new String[] {id};

        //default parameters:(table, columns, selection("where" clause), selectionArgs, "groupBy" clause, "having" clause, "orderBy" clause
        Cursor CR = SQ_MY_DAY.query(TableData_Strings.TableInfo.DAYSHEET_PATIENT_TABLE_NAME, columns_array, whereClause, whereArgs, null, null, null);
        return CR;                                                                  }  //ends getMyDaysheetPatientInformation() method

        //test to see if id input throws it off
    public void deleteUser(DatabaseOperations DOP, String id, String email, String pass) {

        //first delete rows in ins_ver table since it references users_table with a foreign key
        String whereClause = TableData_Strings.TableInfo.INS_VER_USER_ID+" = ?";
        String[] whereArgs = new String[] {id};
        SQLiteDatabase SQ_DEL_INS = DOP.getWritableDatabase();
        //default parameters:(table, columns, selection("where" clause), selectionArgs, "groupBy" clause, "having" clause, "orderBy" clause
        SQ_DEL_INS.delete(TableData_Strings.TableInfo.INSURANCE_VERIFICATION_PATIENT_TABLE, whereClause, whereArgs);


        //Second, delete rows in daysheet table since it also references users_table with a foreign key
        whereClause = TableData_Strings.TableInfo.DAYSHEET_USER_ID+" = ?";
        whereArgs = new String[] {id};
        SQLiteDatabase SQ_DEL_DAY = DOP.getWritableDatabase();
        //default parameters:(table, columns, selection("where" clause), selectionArgs, "groupBy" clause, "having" clause, "orderBy" clause
        SQ_DEL_DAY.delete(TableData_Strings.TableInfo.DAYSHEET_PATIENT_TABLE_NAME, whereClause, whereArgs);


        //Third, delete row from Users_Table containing account
        String selection = TableData_Strings.TableInfo.USER_ID+" LIKE ? AND "+TableData_Strings.TableInfo.USER_EMAIL+" LIKE ? AND "+ TableData_Strings.TableInfo.USER_PASSWORD+" LIKE ?";
        String args[] = {id, email, pass};
        SQLiteDatabase SQ_DEL_USER = DOP.getWritableDatabase();
        SQ_DEL_USER.delete(TableData_Strings.TableInfo.USERS_TABLE_NAME, selection, args);
                                                                                           }  //ends deleteUser() method







}   //ends DatabaseOperations class




















//////////////////////////////////////////////////////////////////////////////
//Sample MySQLite statements/queries/knowledge
//////////////////////////////////////////////////////////////////////////////

//NOT ACCEPTABLE:
// INSERT INTO foo;   //all null columns not okay


//ACCEPTABLE:

//MySQLite note: "INSERT OR IGNORE" is a proper phrase. Ignore allows duplicate constraints...shouldnt
//haveduplicate primary keys insert or ignore statements though. Integer primary key automatically
//increments

//i may not need ignore since I will remove most of the foreign keys in daysheet form table


//   INSERT INTO foo (somecol) VALUES (NULL);


//for the insurance verification form it would be useful to search for name
//successful sql query. will need mysqlite modification
//select * from PATIENT_INS_VER_FORM_TABLE where ins_ver_f_name ='Tom' and ins_ver_l_name='Hanks';

//successful sql query. will need mysqlite modification..plus all columns from both tables list horizontally
//select * from patient_ins_ver_form_table, patient_daysheet_form_table where service_number =2 and day_service_number=2;


//beginning of person's problem
//uvalue = EditText( some user value );
  //      p_query = "select * from mytable where name_field = '" +  uvalue + "'" ;
   //     mDb.rawQuery( p_query, null );

//solution
//This not only solves your quotes problem but also mitigates SQL Injection.

//p_query = "select * from mytable where name_field = ?";
//mDb.rawQuery(p_query, new String[] { uvalue });










//fix
//String my_admin_id = "mscholzjr";
//String my_email = "mscholzjr@outlook.com";
//ContentValues content_on_create_values = new ContentValues();
//content_on_create_values.put(my_admin_id,my_email,"3lsr");
//        long k = sdb.insert(TableData.TableInfo.ADMIN_TABLE_NAME, null, content_on_create_values);
//    Log.d("Database operations", "Admin entered");