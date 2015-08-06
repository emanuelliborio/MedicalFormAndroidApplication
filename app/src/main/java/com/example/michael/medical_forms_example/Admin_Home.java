package com.example.michael.copaydirect;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.TextView;

/**
 * Created by Michael on 5/5/2015.
 */
public class Admin_Home extends Activity implements OnClickListener {
   private Button view_all_patients_ins_ver_data,view_all_patients_daysheet_data, delete_user_account;
    private TextView Greeting;
    private String admin_id_string;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home_view);

        View title = getWindow().findViewById(android.R.id.title);
        View titleBar = (View) title.getParent();
        titleBar.setBackgroundColor(getResources().getColor(R.color.blue));

        Intent id_receiver = getIntent();
        admin_id_string = id_receiver.getStringExtra("admin_id");
        setTitle("Hi, "+ admin_id_string+"!");

        Greeting = (TextView) findViewById(R.id.admin_greeting_id);
        Greeting.setText("Hello, "+admin_id_string+"!");

        view_all_patients_daysheet_data = (Button) findViewById(R.id.view_all_patients_daysheet_data_id);
        view_all_patients_ins_ver_data = (Button) findViewById(R.id.view_all_patients_ins_ver_data_id);
        delete_user_account = (Button) findViewById(R.id.delete_user_account_id);

        view_all_patients_ins_ver_data.setOnClickListener(this);
        view_all_patients_daysheet_data.setOnClickListener(this);
        delete_user_account.setOnClickListener(this);
                                                        }  //ends onCreate method

    public void onClick(View v) {

        if(v==view_all_patients_ins_ver_data){
            Intent view_all  = new Intent(this, All_Patients_Ins_Ver_TableView.class);
            view_all.putExtra("admin_id", admin_id_string);
            startActivity(view_all);
                                         }


        if(v==view_all_patients_daysheet_data){
            Intent view_all  = new Intent(this, All_Patients_Daysheet_TableView.class);
            view_all.putExtra("admin_id", admin_id_string);
            startActivity(view_all);          }

        //v==delete_user_account
        else{
            Intent pass_to_delete_user = new Intent(this, Delete_User_Account.class);
            pass_to_delete_user.putExtra("admin_id", admin_id_string);
            startActivity(pass_to_delete_user);
            }


                                }  //ends onClick method
}  //ends Admin_Home class
