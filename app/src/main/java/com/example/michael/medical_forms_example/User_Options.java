package com.example.michael.copaydirect;

import android.app.Activity;
import android.view.View;
import android.os.Bundle;
import android.content.Intent;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class User_Options extends Activity implements OnClickListener {

    private Button view_my_patient_daysheet_data, view_my_patient_ins_data, enter_new_patient_data, logout;
    private String user_id_string;
    private TextView Greeting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_options_view);

        Intent id_receiver = getIntent();
        user_id_string = id_receiver.getStringExtra("user_id");
        setTitle("Hi, " + user_id_string+"!");

        View title = getWindow().findViewById(android.R.id.title);
        View titleBar = (View) title.getParent();
        titleBar.setBackgroundColor(getResources().getColor(R.color.blue));

        Greeting = (TextView) findViewById(R.id.user_greeting_id);
        Greeting.setText("Hello, "+user_id_string+"!");

        view_my_patient_daysheet_data = (Button) findViewById(R.id.view_patient_day_data_id);
        view_my_patient_ins_data= (Button) findViewById(R.id.view_patient_ins_data_id);
        enter_new_patient_data = (Button) findViewById(R.id.new_patient_forms_id);
        logout = (Button) findViewById(R.id.logout_id);

        view_my_patient_ins_data.setOnClickListener(this);
        view_my_patient_daysheet_data.setOnClickListener(this);
        enter_new_patient_data.setOnClickListener(this);
        logout.setOnClickListener(this);
                                                        }  //ends onCreate method
    @Override
    public void onClick(View v) {


        if (v == view_my_patient_daysheet_data) {
            Intent view_my_pat_table = new Intent(this, My_Patients_Daysheet_TableView.class);
            view_my_pat_table.putExtra("user_id", user_id_string);
            startActivity(view_my_pat_table);
        }

       else if (v == view_my_patient_ins_data) {
            Intent view_my_pat_table = new Intent(this, My_Patients_Ins_Ver_TableView.class);
            view_my_pat_table.putExtra("user_id", user_id_string);
            startActivity(view_my_pat_table);
        }


        else if (v == enter_new_patient_data) {
            Intent enter_new_pat  = new Intent(this, Insurance_Verification_Form.class);
            enter_new_pat.putExtra("user_id", user_id_string);
            startActivity(enter_new_pat);
                                              }
        //v==logout
        else  {
            Toast.makeText(getBaseContext(), "Logging out!", Toast.LENGTH_SHORT).show();
            Intent go_back_home  = new Intent(this, App_Home.class);
            startActivity(go_back_home);
              }
                                 }  //ends onClick method
                                                                                   }  //ends class
