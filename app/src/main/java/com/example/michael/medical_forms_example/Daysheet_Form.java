package com.example.michael.copaydirect;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.content.Intent;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import org.joda.time.DateTime;

import java.text.NumberFormat;
import java.util.Locale;

public class Daysheet_Form extends Activity implements OnClickListener, OnCheckedChangeListener{
    private EditText day_fname, day_lname;
    private TextView NextApptDate;
    private Spinner med_pat_dropdown, payment_type_dropdown;
    private boolean diagnosiscode_flag_1, diagnosiscode_flag_2, diagnosiscode_flag_3, diagnosiscode_flag_4,
            med_pat_flag, cpt_flag_1, cpt_flag_2,cpt_flag_3,cpt_flag_4, pmt_type_flag, number_format_flag,
            dos_date_flag =false;
    private int diagnosis_counter, cpt_counter=0;
    private DatePicker dos_date_picker;
    private Button save_button, go_to_user_options,logout;
    private String med_care_answer, passed_d_fname, passed_d_lname, submitted_d_fname, submitted_d_lname, android_copay_amount,
            copay_database_entry, copay_method, next_appt_string,
            user_id_string="";
    private int service_number_int,today_service_month, today_service_day, today_service_year,
            next_appt_month,next_appt_day, next_appt_year=0;
    private Context d_form_context = this;
    private String diagnosiscode_1,diagnosiscode_2, diagnosiscode_3, diagnosiscode_4="N/A";
    private int cptcode1,cptcode2,cptcode3,cptcode4=0;
    //private Integer serv_num = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daysheet_form_view);
        View title = getWindow().findViewById(android.R.id.title);
        View titleBar = (View) title.getParent();
        titleBar.setBackgroundColor(getResources().getColor(R.color.blue));

        Intent ins_ver_form_receiver = getIntent();
       // service_number_string = ins_ver_form_receiver.getStringExtra("service_number");
       // service_number_int  = Integer.parseInt(service_number_string);

////////////////////////////////////////////////////////////////////////////
//     Date of Service DatePicker info
////////////////////////////////////////////////////////////////////////////

        dos_date_picker = (DatePicker) findViewById(R.id.dos_datepicker);


        user_id_string = ins_ver_form_receiver.getStringExtra("user_id");

        setTitle("Hi, "+ user_id_string+"!");

        passed_d_fname = ins_ver_form_receiver.getStringExtra("f_name");
        passed_d_lname = ins_ver_form_receiver.getStringExtra("l_name");


         day_fname = (EditText)findViewById(R.id.fname_input_id);
        day_fname.setText(passed_d_fname, TextView.BufferType.EDITABLE);

        day_lname = (EditText)findViewById(R.id.lname_input_id);
        day_lname.setText(passed_d_lname, TextView.BufferType.EDITABLE);

        next_appt_month = ins_ver_form_receiver.getIntExtra("next_appt_month", next_appt_month);
        next_appt_day = ins_ver_form_receiver.getIntExtra("next_appt_day", next_appt_day);
        next_appt_year = ins_ver_form_receiver.getIntExtra("next_appt_year", next_appt_year);
        next_appt_string = next_appt_month+" / "+next_appt_day+" / "+next_appt_year;

////////////////////////////////////////////////////////////////////////////
//     Medicare Spinner info
////////////////////////////////////////////////////////////////////////////

        med_pat_dropdown = (Spinner) findViewById(R.id.medpatspinner);
        String[] medicare_items = new String[]{"CHOOSE", "YES", "NO"};
        ArrayAdapter<String> medicare_adapter = new ArrayAdapter<String>(Daysheet_Form.this, android.R.layout.simple_spinner_item, medicare_items);
        medicare_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        med_pat_dropdown.setAdapter(medicare_adapter);

        med_pat_dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==0){
                    med_pat_flag=false;
                }
                else if(i==1){
                    med_pat_flag=true;
                    med_care_answer = "yes";
                }
                //i==2 "No"
                else{
                    med_pat_flag=true;
                    med_care_answer = "no";
                    }
                                                                                              }
            @Override
            public void onNothingSelected(AdapterView<?> parentView)
            {
                med_pat_flag=false;
            }
        });



////////////////////////////////////////////////////////////////////////////
//      cpt_code info
////////////////////////////////////////////////////////////////////////////

        DeselectableRadioButton cpt_90836 = (DeselectableRadioButton) findViewById(R.id.radioButton90836);
        DeselectableRadioButton cpt_90835 = (DeselectableRadioButton) findViewById(R.id.radioButton90835);
        DeselectableRadioButton cpt_90826 = (DeselectableRadioButton) findViewById(R.id.radioButton90826);
        DeselectableRadioButton cpt_90781 = (DeselectableRadioButton) findViewById(R.id.radioButton90781);

        cpt_90836.setOnCheckedChangeListener(this);
        cpt_90835.setOnCheckedChangeListener(this);
        cpt_90826.setOnCheckedChangeListener(this);
        cpt_90781.setOnCheckedChangeListener(this);


////////////////////////////////////////////////////////////////////////////
//      diagnosis_code info
////////////////////////////////////////////////////////////////////////////

        DeselectableRadioButton rb209_2 = (DeselectableRadioButton) findViewById(R.id.radioButton209_2);
        DeselectableRadioButton rbv62_12 = (DeselectableRadioButton) findViewById(R.id.radioButtonv62_12);
        DeselectableRadioButton rb309_2 = (DeselectableRadioButton) findViewById(R.id.radioButton309_2);
        DeselectableRadioButton rb203_5 = (DeselectableRadioButton) findViewById(R.id.radioButton203_5);

        rb209_2.setOnCheckedChangeListener(this);
        rbv62_12.setOnCheckedChangeListener(this);
        rb309_2.setOnCheckedChangeListener(this);
        rb203_5.setOnCheckedChangeListener(this);


        payment_type_dropdown = (Spinner) findViewById(R.id.payment_spinner);
        String[] payment_types = new String[]{"CHOOSE", "CASH", "CREDIT", "CHECK"};
        ArrayAdapter<String> pmt_type_adapter = new ArrayAdapter<String>(Daysheet_Form.this, android.R.layout.simple_spinner_item, payment_types);
        pmt_type_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        payment_type_dropdown.setAdapter(pmt_type_adapter);
        payment_type_dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int p, long l) {
                if(p==0){
                    pmt_type_flag=false;}
                else if(p==1){
                    pmt_type_flag=true;
                    copay_method = "cash";}
                else if(p==2){
                    pmt_type_flag=true;
                    copay_method = "credit";}
                else {
                    pmt_type_flag = true;
                    copay_method = "check";}
                                                                                             }//ends onItemSelected method

            @Override
            public void onNothingSelected(AdapterView<?> parentView)
            {
                pmt_type_flag=false;
            }
        });




         NextApptDate =(TextView)findViewById(R.id.patient_next_appt_displayed_id);
        NextApptDate.setText(next_appt_string);

        go_to_user_options = (Button) findViewById(R.id.go_to_usr_opts_id);
        logout = (Button) findViewById(R.id.logout_day_id);
        save_button = (Button) findViewById(R.id.submit_daysheet_id);

        go_to_user_options.setOnClickListener(this);
        logout.setOnClickListener(this);
        save_button.setOnClickListener(this);

    }  //ends onCreate method

    @Override
public void onClick(View v) {

        ///////////////////////////////////////////////////////////////
        ///////check if all fields are acceptable for submission////////
        ///////////////////////////////////////////////////////////////




     if (v == save_button) {

         //maybe you have to make final ints and strings to capture the last int and string value of
         // the diagnosis and cpt codes....test

         submitted_d_fname = ((EditText) findViewById(R.id.fname_input_id)).getText().toString();
         submitted_d_lname = ((EditText) findViewById(R.id.lname_input_id)).getText().toString();

            today_service_month = dos_date_picker.getMonth()+1;
             today_service_day =  dos_date_picker.getDayOfMonth();
             today_service_year = dos_date_picker.getYear();


         DateTime current_dmy = new DateTime();
         int current_year = current_dmy.getYear();
         int current_month = current_dmy.getMonthOfYear();
         int current_day = current_dmy.getDayOfMonth();
         int current_hour = current_dmy.getHourOfDay();



         //adjusts for UTC GMT global default time 4 hours ahead of America/New_York Time Zone
         if(current_hour<4){
             current_day = current_day-1;
                           }


         if((current_month==today_service_month)&&(current_day==today_service_day)&&(current_year==today_service_year)) {
             dos_date_flag = true;}
         else{
             Toast.makeText(getApplicationContext(), "Please choose today as the date of service!", Toast.LENGTH_SHORT).show();
             dos_date_flag = false;}

         try {
             android_copay_amount = ((EditText) findViewById(R.id.copay_dollar_amount_id)).getText().toString();

             if (android_copay_amount.trim().isEmpty()) {
                 number_format_flag = false;
             }
             else {
                 double double_android_value = Double.parseDouble(android_copay_amount);

                 NumberFormat usCurrencyFormat = NumberFormat.getCurrencyInstance(Locale.US);
                 copay_database_entry =  usCurrencyFormat.format(double_android_value);
                 number_format_flag = true;
             }

         }  //ends try
         catch (NumberFormatException e) {
             number_format_flag = false;
                                         }  //ends catch


          if (med_pat_flag == false) {
             Toast.makeText(getApplicationContext(), "Please choose yes/no for medicare patient", Toast.LENGTH_SHORT).show();
                                     }  //ends if

         else if (!passed_d_fname.equals(submitted_d_fname)) {
             Toast.makeText(getApplicationContext(), "Please enter the same first name that was on the insurance verification form.", Toast.LENGTH_SHORT).show();
         }
         else if (!passed_d_lname.equals(submitted_d_lname)) {
             Toast.makeText(getApplicationContext(), "Please enter the same last name that was on the insurance verification form.", Toast.LENGTH_SHORT).show();
         }
          else if(dos_date_flag == false){
             Toast.makeText(getApplicationContext(), "Please select today as the date of service.", Toast.LENGTH_SHORT).show();
                                         }

            else if (cpt_counter==0) {
                Toast.makeText(getApplicationContext(), "Please choose at least one CPT code.", Toast.LENGTH_SHORT).show();
                                                                         }
            else if ( diagnosis_counter==0) {
                Toast.makeText(getApplicationContext(), "Please choose at least one diagnosis code.", Toast.LENGTH_SHORT).show();
                                            }

            else if (number_format_flag == false) {
                Toast.makeText(getApplicationContext(), "Please enter a copay amount like this: 34 or 30205.92", Toast.LENGTH_SHORT).show();
                                                  }
            else if (pmt_type_flag == false) {
                Toast.makeText(getApplicationContext(), "Please choose a payment type.", Toast.LENGTH_SHORT).show();
                                             }

            else {


                      if(diagnosiscode_flag_1){
                        diagnosiscode_1 = "209_2";
                                              }
                      else{
                          diagnosiscode_1 = "N/A";
                          }
                      if(diagnosiscode_flag_2){
                          diagnosiscode_2 = "v62_12";
                                              }
                      else{
                          diagnosiscode_2 = "N/A";
                          }
                      if(diagnosiscode_flag_3){
                          diagnosiscode_3 = "309_2";
                                              }
                      else{
                          diagnosiscode_3 = "N/A";
                          }
                      if(diagnosiscode_flag_4){
                          diagnosiscode_4 = "203_5";
                                              }
                      else{
                          diagnosiscode_4 = "N/A";
                          }
                      if(cpt_flag_1){
                          cptcode1 = 90836;
                                    }
                      else{
                          cptcode1 = 0;
                      }
                      if(cpt_flag_2){
                          cptcode2 = 90835;
                                    }
                      else{
                          cptcode2 = 0;
                      }
                      if(cpt_flag_3){
                          cptcode3 = 90826;
                                    }
                      else{
                          cptcode3 = 0;
                      }
                      if(cpt_flag_4){
                          cptcode4 = 90781;
                                    }
                      else{
                          cptcode4 = 0;
                      }
                DatabaseOperations DOP_Patients = new DatabaseOperations(d_form_context);
                Cursor CR_P = DOP_Patients.getAllDaysheetPatientInformation(DOP_Patients);

                DOP_Patients.put_Patient_Daysheet_Form_Table_Info(DOP_Patients, submitted_d_fname, submitted_d_lname, today_service_month,today_service_day,today_service_year,
                            med_care_answer, cptcode1, cptcode2,cptcode3,cptcode4,diagnosiscode_1,diagnosiscode_2,
                            diagnosiscode_3, diagnosiscode_4, copay_database_entry, copay_method,next_appt_month, next_appt_day, next_appt_year,
                             user_id_string);
                CR_P.close();
                Toast.makeText(getApplicationContext(), "Form Saved!", Toast.LENGTH_SHORT).show();

                Intent user_options_sender = new Intent(this, User_Options.class);
                user_options_sender.putExtra("user_id", user_id_string);
                startActivity(user_options_sender);

                  } //ends else
                             } //ends if

     else if(v==go_to_user_options){
         Toast.makeText(getApplicationContext(), "Form Not Saved!", Toast.LENGTH_SHORT).show();

         Intent user_options_sender = new Intent(this, User_Options.class);
         user_options_sender.putExtra("user_id", user_id_string);
         startActivity(user_options_sender);
         day_fname.setText("");
         day_lname.setText("");
                                   }

     //v==logout
     else{
         Toast.makeText(getApplicationContext(), "Logging Out, Form Not Saved!", Toast.LENGTH_SHORT).show();
         Intent go_home = new Intent(this, App_Home.class);
         startActivity(go_home);
         day_fname.setText("");
         day_lname.setText("");
         }
    }//ends onClick method
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked){
            switch(buttonView.getId()) {

//////////////////////////////////////////////////////////////////////////////
//CPT radio buttons checked
//////////////////////////////////////////////////////////////////////////////

                case R.id.radioButton90836:
                    cpt_flag_1 =true;
                    cpt_counter++;

                    break;
                case R.id.radioButton90835:
                    cpt_flag_2 =true;
                    cpt_counter++;

                    break;
                case R.id.radioButton90826:
                    cpt_flag_3 =true;
                    cpt_counter++;

                    break;
                case R.id.radioButton90781:
                    cpt_flag_4 =true;
                    cpt_counter++;

                    break;

//////////////////////////////////////////////////////////////////////////////
//Diagnosis radio buttons checked
//////////////////////////////////////////////////////////////////////////////

                case R.id.radioButton209_2:
                    diagnosiscode_flag_1 =true;
                    diagnosis_counter++;

                    break;
                case R.id.radioButtonv62_12:
                    diagnosiscode_flag_2 =true;
                    diagnosis_counter++;

                    break;
                case R.id.radioButton309_2:
                    diagnosiscode_flag_3 =true;
                    diagnosis_counter++;

                    break;
                case R.id.radioButton203_5:
                    diagnosiscode_flag_4 =true;
                    diagnosis_counter++;

                    break;
                                        }  //ends switch
                       }  //ends if
        else{
            if(!isChecked){
                switch(buttonView.getId()) {

//////////////////////////////////////////////////////////////////////////////
//CPT radio buttons unchecked
//////////////////////////////////////////////////////////////////////////////

                    case R.id.radioButton90836:
                        cpt_flag_1 =false;
                        cptcode1 = 0;
                        cpt_counter--;

                        break;
                    case R.id.radioButton90835:
                        cpt_flag_2 =false;
                        cptcode2 = 0;
                        cpt_counter--;

                        break;
                    case R.id.radioButton90826:
                        cpt_flag_3 =false;
                        cptcode3 = 0;
                        cpt_counter--;

                        break;
                    case R.id.radioButton90781:
                        cpt_flag_4 =false;
                        cptcode4 = 0;
                        cpt_counter--;

                        break;

//////////////////////////////////////////////////////////////////////////////
//Diagnosis radio buttons unchecked
//////////////////////////////////////////////////////////////////////////////

                    case R.id.radioButton209_2:
                        diagnosiscode_flag_1 = false;
                        diagnosiscode_1 = "N/A";
                        diagnosis_counter--;

                        break;
                    case R.id.radioButtonv62_12:
                        diagnosiscode_flag_2 =false;
                        diagnosiscode_2 = "N/A";
                        diagnosis_counter--;

                        break;
                    case R.id.radioButton309_2:
                        diagnosiscode_flag_3 =false;
                        diagnosiscode_3 = "N/A";
                        diagnosis_counter--;

                        break;
                    case R.id.radioButton203_5:
                        diagnosiscode_flag_4 =false;
                        diagnosiscode_4 = "N/A";
                        diagnosis_counter--;

                        break;
                                            }  //ends switch
                            } //ends if
                }  //ends else
                                                                              } //ends onCheckedChanged method
                                                                        }  //ends Daysheet class
