package com.example.michael.copaydirect;

import android.provider.BaseColumns;

public class TableData_Strings {

public TableData_Strings(){

                  }

public static abstract class TableInfo implements BaseColumns {

//Database Name
public static final String DATABASE_NAME = "Copay_Direct_Database.db";

public static final String USERS_TABLE_NAME = "User_Table";
    //Users columns

    public static final String USER_ID = "User_ID";
    public static final String USER_EMAIL = "Email";
    public static final String USER_PASSWORD = "Password";

public static final String ADMIN_TABLE_NAME = "Administrator_Table";
    //Admin columns
    public static final String ADMIN_ID = "Admin_ID";
    public static final String ADMIN_EMAIL = "Admin_Email";
    public static final String ADMIN_PASSWORD = "Admin_Password";


public static final String INSURANCE_VERIFICATION_PATIENT_TABLE = "Patient_Ins_Ver_Form_Table";
//Ins Ver columns
    public static final String INS_VER_SERVICE_NUMBER ="Service_Number";
    public static final String INS_VER_PATIENT_F_NAME = "Patient_First_Name";
    public static final String INS_VER_PATIENT_L_NAME = "Patient_Last_Name";
    public static final String INS_VER_MEMBER_ID = "Member_ID";
    public static final String INS_VER_D_O_B_MONTH = "DOB_Month";
    public static final String INS_VER_D_O_B_DAY = "DOB_Day";
    public static final String INS_VER_D_O_B_YEAR = "DOB_Year";
    public static final String INS_VER_NEXT_APPT_MONTH = "Next_Appt_Month";
    public static final String INS_VER_NEXT_APPT_DAY = "Next_Appt_Day";
    public static final String INS_VER_NEXT_APPT_YEAR = "Next_Appt_Year";
    public static final String INSURANCE_NAME ="Insurance_Name";
    public static final String INS_VER_USER_ID = USER_ID;



public static final String DAYSHEET_PATIENT_TABLE_NAME = "Patient_Daysheet_Form_Table";
        //Daysheet columns
        public static final String DAYSHEET_SERVICE_NUMBER =INS_VER_SERVICE_NUMBER;
        public static final String DAYSHEET_MEDICARE_PATIENT= "Medicare_Patient";
        public static final String DAYSHEET_F_NAME = INS_VER_PATIENT_F_NAME;
        public static final String DAYSHEET_L_NAME = INS_VER_PATIENT_L_NAME;
        public static final String DAYSHEET_D_O_S_MONTH = "Appt_Month";
        public static final String DAYSHEET_D_O_S_DAY = "Appt_Day";
        public static final String DAYSHEET_D_O_S_YEAR = "Appt_Year";
        public static final String DAYSHEET_CPT_CODE1 = "CPT_Code_1";
        public static final String DAYSHEET_CPT_CODE2 = "CPT_Code_2";
        public static final String DAYSHEET_CPT_CODE3 = "CPT_Code_3";
        public static final String DAYSHEET_CPT_CODE4 = "CPT_Code_4";
        public static final String DAYSHEET_DIAGNOSIS_CODE1 = "Diagnosis_Code_1";
        public static final String DAYSHEET_DIAGNOSIS_CODE2 = "Diagnosis_Code_2";
        public static final String DAYSHEET_DIAGNOSIS_CODE3 = "Diagnosis_Code_3";
        public static final String DAYSHEET_DIAGNOSIS_CODE4 = "Diagnosis_Code_4";
        public static final String DAYSHEET_COPAY_AMOUNT = "Copay_Amount";
        public static final String DAYSHEET_METHOD_OF_PAYMENT = "Method_of_Payment";
        public static final String DAYSHEET_NEXT_APPT_MONTH = INS_VER_NEXT_APPT_MONTH;
        public static final String DAYSHEET_NEXT_APPT_DAY = INS_VER_NEXT_APPT_DAY;
        public static final String DAYSHEET_NEXT_APPT_YEAR = INS_VER_NEXT_APPT_YEAR;
        public static final String DAYSHEET_USER_ID = USER_ID;


}  //ends TableInfo class
                  }  //ends class TableData
