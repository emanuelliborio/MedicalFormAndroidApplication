package com.example.michael.copaydirect;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;
import android.content.Intent;
import android.widget.Button;
import android.widget.Toast;
import android.view.View.OnClickListener;
import android.widget.DatePicker;

import org.joda.time.DateTime;





/**
 * Created by Michael on 5/2/2015.
 */
public class Insurance_Verification_Form extends Activity implements OnClickListener {


       private int dob_month, dob_day, dob_year, next_appt_month, next_appt_day, next_appt_year,
                service_number;
   // private Integer serv_num = null;

       private String vfname_input, vlname_input, member_id, user_id_passed_string,
                insurance_name, future_appt_answer, service_number_string="";

       private Spinner future_appt_choice_dropdown;

       private boolean dob_date_flag, next_appt_date_flag = false;

       private DatePicker dob_date_picker, v_next_appt_date_picker;

       private Button save_button, logout_dont_save, go_back_dont_save;

       private Context ins_ver_context = this;
    private AutoCompleteTextView Auto_Complete_Ins_Name;

    private ArrayAdapter<String> auto_complete_adapter;
    private String[] common_insurance_names;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insurance_verification_form_view);

        View title = getWindow().findViewById(android.R.id.title);
        View titleBar = (View) title.getParent();
        titleBar.setBackgroundColor(getResources().getColor(R.color.blue));

        Intent user_id_receiver = getIntent();
        user_id_passed_string = user_id_receiver.getStringExtra("user_id");
        setTitle("Hi," + user_id_passed_string+"!");


        dob_date_picker = (DatePicker) findViewById(R.id.DOB_datepicker);


      common_insurance_names = getResources().getStringArray(R.array.health_insurance_companies_array);

        auto_complete_adapter = new ArrayAdapter<String>(this,R.layout.insurance_company_list_items,common_insurance_names);
        Auto_Complete_Ins_Name = (AutoCompleteTextView) findViewById(R.id.auto_complete_insurance_name_id);
        Auto_Complete_Ins_Name.setAdapter(auto_complete_adapter);
        Auto_Complete_Ins_Name.setThreshold(1);



        future_appt_choice_dropdown = (Spinner) findViewById(R.id.Future_Appt_Spinner);
        String[]  future_appt_choice_items = new String[]{"CHOOSE", "YES", "NO"};
        ArrayAdapter<String> future_appt_choice_adapter = new ArrayAdapter<String>(Insurance_Verification_Form.this, android.R.layout.simple_spinner_item, future_appt_choice_items);
        future_appt_choice_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        future_appt_choice_dropdown.setAdapter(future_appt_choice_adapter);

        future_appt_choice_dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    future_appt_answer = "choose";
                } else if (i == 1) {
                    future_appt_answer = "yes";
                }
                //i==2 "No"
                else {
                    future_appt_answer = "no";

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                future_appt_answer = "choose";
            }
        });


        v_next_appt_date_picker = (DatePicker) findViewById(R.id.next_appt_datepicker);



        go_back_dont_save= (Button) findViewById(R.id.go_back_dont_save_id);
        logout_dont_save = (Button) findViewById(R.id.logout_ins_id);
        save_button = (Button) findViewById(R.id.save_verification_form_id);

        go_back_dont_save.setOnClickListener(this);
        logout_dont_save.setOnClickListener(this);
        save_button.setOnClickListener(this);





    }  //ends onCreate method


    @Override
    public void onClick(View v) {
        dob_month = dob_date_picker.getMonth() + 1;
        dob_day = dob_date_picker.getDayOfMonth();
        dob_year = dob_date_picker.getYear();


        DateTime current_dmy = new DateTime();
        int current_year = current_dmy.getYear();
        int current_month = current_dmy.getMonthOfYear();
        int current_day = current_dmy.getDayOfMonth();
        int current_hour = current_dmy.getHourOfDay();


        //adjusts for UTC GMT global default time 4 hours ahead of America/New_York Time Zone
        if(current_hour<4){
            current_day = current_day-1;
                          }



        if((current_year<dob_year)){
            dob_date_flag = false; }
        else if((current_year==dob_year)&&(current_month<dob_month)){
            dob_date_flag = false;                                  }
        else if((current_year==dob_year)&&(current_month==dob_month)&&(current_day<=dob_day)){
            dob_date_flag = false;                                                          }
        else{
            dob_date_flag = true;
            }


        //check if date is future date...if so set flag....
        next_appt_month = v_next_appt_date_picker.getMonth() + 1;
        next_appt_day = v_next_appt_date_picker.getDayOfMonth();
        next_appt_year = v_next_appt_date_picker.getYear();





        if((current_year>next_appt_year)){
            next_appt_date_flag = false;
        }
        else if((current_year==next_appt_year)&&(current_month>next_appt_month)){
            next_appt_date_flag = false;
        }
        else if((current_year==next_appt_year)&&(current_month==next_appt_month)&&(current_day>=next_appt_day)){
            next_appt_date_flag = false;
        }
        else{
            next_appt_date_flag = true;
            }

        if (v == save_button) {
            //some time soon seperate editext from string so I can setText("");
            vfname_input = ((EditText) findViewById(R.id.v_fname_input_id)).getText().toString();
            vlname_input = ((EditText) findViewById(R.id.v_lname_input_id)).getText().toString();
            member_id = ((EditText) findViewById(R.id.member_num_input_id)).getText().toString();

            insurance_name = Auto_Complete_Ins_Name.getText().toString();



            if(member_id.trim().isEmpty()){
                Toast.makeText(getApplicationContext(), "I'm sorry, the Patient's Member ID is required!", Toast.LENGTH_SHORT).show();
                                    }

            else if (!vfname_input.matches("^[a-zA-z]+([ '-][a-zA-Z]+)*")) {
                Toast.makeText(getApplicationContext(), "Please enter a valid first name", Toast.LENGTH_SHORT).show();
            }
            else if (!vlname_input.matches("^[a-zA-z]+([ '-][a-zA-Z]+)*")) {
                Toast.makeText(getApplicationContext(), "Please enter a valid last name", Toast.LENGTH_SHORT).show();
            }
             else if(dob_date_flag==false){
                 Toast.makeText(getApplicationContext(), "Please choose a past birth date.", Toast.LENGTH_SHORT).show();
             }
             else if(insurance_name.trim().isEmpty()){
                 Toast.makeText(getApplicationContext(), "Please enter an insurance name.", Toast.LENGTH_SHORT).show();
             }


             //If the user chooses no. The user doesn't have to set a future date
             else if((next_appt_date_flag==false) && (future_appt_answer.equals("yes"))){
                 Toast.makeText(getApplicationContext(), "Please choose a future appointment date.", Toast.LENGTH_SHORT).show();
                                                }

             else if(future_appt_answer.equals("choose")) {
                Toast.makeText(getApplicationContext(), "Please choose yes/no for future appointment date", Toast.LENGTH_SHORT).show();
                                                          }
            else if(future_appt_answer.equals("no")) {

                    next_appt_date_flag=true;
                     next_appt_month = 0;
                     next_appt_day = 0;
                     next_appt_year = 0;




                DatabaseOperations DOP = new DatabaseOperations(ins_ver_context);


                 Cursor CR = DOP.getAllInsVerPatientInformation(DOP);




                 DOP.put_Patient_Ins_Ver_Form_Table_Info(DOP, member_id, vfname_input,
                         vlname_input, dob_month, dob_day, dob_year, next_appt_month, next_appt_day,
                         next_appt_year, insurance_name, user_id_passed_string);

                CR.close();

                 Intent pass_to_daysheet_form = new Intent(this, Daysheet_Form.class);
                 pass_to_daysheet_form.putExtra("f_name", vfname_input);
                 pass_to_daysheet_form.putExtra("l_name", vlname_input);
                 pass_to_daysheet_form.putExtra("next_appt_month", next_appt_month);
                 pass_to_daysheet_form.putExtra("next_appt_day", next_appt_day);
                 pass_to_daysheet_form.putExtra("next_appt_year", next_appt_year);
                 pass_to_daysheet_form.putExtra("user_id", user_id_passed_string);
                 Toast.makeText(getApplicationContext(), "Insurance Verification Form Saved!", Toast.LENGTH_SHORT).show();
                 startActivity(pass_to_daysheet_form);
                 vfname_input="";
                vlname_input="";
                 member_id="";
                 future_appt_answer="choose";
                 insurance_name="";


                                                         } //ends else if

                //future_appt_answer.equals("yes")
                 else {

                     next_appt_month = v_next_appt_date_picker.getMonth() + 1;
                     next_appt_day = v_next_appt_date_picker.getDayOfMonth();
                     next_appt_year = v_next_appt_date_picker.getYear();

                DatabaseOperations DOP = new DatabaseOperations(ins_ver_context);

                Cursor CR = DOP.getAllInsVerPatientInformation(DOP);


                 DOP.put_Patient_Ins_Ver_Form_Table_Info(DOP, member_id, vfname_input,
                         vlname_input, dob_month, dob_day, dob_year, next_appt_month, next_appt_day,
                         next_appt_year, insurance_name, user_id_passed_string);

                CR.close();

                 Intent pass_to_daysheet_form = new Intent(this, Daysheet_Form.class);
                 pass_to_daysheet_form.putExtra("f_name", vfname_input);
                 pass_to_daysheet_form.putExtra("l_name", vlname_input);
                 pass_to_daysheet_form.putExtra("next_appt_month", next_appt_month);
                 pass_to_daysheet_form.putExtra("next_appt_day", next_appt_day);
                 pass_to_daysheet_form.putExtra("next_appt_year", next_appt_year);
                 pass_to_daysheet_form.putExtra("user_id", user_id_passed_string);

                 Toast.makeText(getApplicationContext(), "Form Saved!", Toast.LENGTH_SHORT).show();

                startActivity(pass_to_daysheet_form);
                 vfname_input="";
                vlname_input="";
                 member_id="";
                 future_appt_answer="choose";
                 insurance_name="";
                        }  //ends else
                                }  //ends if

       else if(v==go_back_dont_save){
            Intent go_back = new Intent(this, User_Options.class);
            go_back.putExtra("user_id", user_id_passed_string);
            startActivity(go_back);

            vfname_input="";
            vlname_input="";
            member_id="";
            future_appt_answer="choose";
            insurance_name="";
                                     }

        //v==logout
        else{
            Toast.makeText(getApplicationContext(), "Logging Out; Form Not Saved!", Toast.LENGTH_SHORT).show();

            Intent go_app_home = new Intent(this, App_Home.class);
            startActivity(go_app_home);

            vfname_input="";
            vlname_input="";
            member_id="";
            future_appt_answer="choose";
            insurance_name="";
        }
    }  //ends onClick method

}  //ends Insurance_Verification_Form class

