package com.example.michael.copaydirect;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Delete_User_Account extends Activity implements OnClickListener {

    private Button Permanently_Delete_Account_Button;
    private Spinner yes_no_delete_dropdown;
    private EditText user_id_to_delete, email_to_delete, password_for_confirmation;
    private String admin_id_string, user_id_to_delete_string, email_to_delete_string, password_for_confirmation_string;
    private Context con_text = this;
    private String retrieved_user_id, retrieved_user_email="";
    private boolean yes_no_flag, user_id_existence, email_existence, pass_existence = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_user_account_view);

        View title = getWindow().findViewById(android.R.id.title);
        View titleBar = (View) title.getParent();
        titleBar.setBackgroundColor(getResources().getColor(R.color.blue));

        Intent admin_id_receiver = getIntent();


        admin_id_string = admin_id_receiver.getStringExtra("admin_id");

        setTitle("Hi, "+ admin_id_string+"!");






        user_id_to_delete = (EditText) findViewById(R.id.user_id_delete_input_id);
        email_to_delete = (EditText) findViewById(R.id.email_delete_input_id);
        password_for_confirmation = (EditText) findViewById(R.id.password_delete_input_id);

        yes_no_delete_dropdown = (Spinner) findViewById(R.id.yesnodeletespinner);
        String[] delete_choices = new String[]{"Choose", "Yes", "No"};
        ArrayAdapter<String> delete_choices_adapter = new ArrayAdapter<String>(Delete_User_Account.this, android.R.layout.simple_spinner_item, delete_choices);
        delete_choices_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yes_no_delete_dropdown.setAdapter(delete_choices_adapter);

        yes_no_delete_dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int yn, long l) {
                if (yn==0) {
                    yes_no_flag = false;}
                else if(yn==1) {
                    yes_no_flag = true;}
                else {
                    yes_no_flag = false;}
                                                                                             }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                yes_no_flag = false;
            }
                                                                                                });
        Permanently_Delete_Account_Button = (Button) findViewById(R.id.delete_account_button_id);
        Permanently_Delete_Account_Button.setOnClickListener(this);
                                                    }  //ends onCreate method

    @Override
    public void onClick(View v) {
        user_id_to_delete_string=user_id_to_delete.getText().toString();
        email_to_delete_string = email_to_delete.getText().toString();
        password_for_confirmation_string = password_for_confirmation.getText().toString();

        if (v == Permanently_Delete_Account_Button) {

            DatabaseOperations DOP = new DatabaseOperations(con_text);
            Cursor CR = DOP.getUserInformation(DOP);

            if (yes_no_flag == false) {
                Toast.makeText(getApplicationContext(), "Please select YES if you want to delete your account!", Toast.LENGTH_SHORT).show();
                                       }

            else {
                if (CR.getCount() == 0 || CR == null) {
                    Toast.makeText(getApplicationContext(), "I'm Sorry, There are no more user accounts to delete!", Toast.LENGTH_SHORT).show();
                    user_id_to_delete.setText("");
                    email_to_delete.setText("");
                    password_for_confirmation.setText("");
                    CR.close();
                    finish();
                 }  //ends if

                else {
                    CR.moveToFirst();
                    do {
                        //0 is first column index 1 is "password" column index
                        if (user_id_to_delete_string.equals(CR.getString(0))) {
                            user_id_existence = true;
                            if(email_to_delete_string.equals(CR.getString(1))){
                                email_existence = true;
                                                                              }
                            if (password_for_confirmation_string.equals(CR.getString(2))) {
                                pass_existence = true;
                                                                                          }
                                                                            }  //ends if
                       }  //ends do
                    while (CR.moveToNext());
                    {
                        if (pass_existence == true) {
                            Toast.makeText(getBaseContext(), "Deleting the account for... " + user_id_to_delete_string, Toast.LENGTH_SHORT).show();
                            DOP.deleteUser(DOP,user_id_to_delete_string, email_to_delete_string, password_for_confirmation_string);
                            user_id_to_delete.setText("");
                            email_to_delete.setText("");
                            password_for_confirmation.setText("");
                            Toast.makeText(getBaseContext(), "The account has been removed!", Toast.LENGTH_SHORT).show();
                            user_id_existence=false;
                            email_existence = false;
                            pass_existence = false;
                            CR.close();
                            Intent admin_return = new Intent(this, Admin_Home.class);
                            startActivity(admin_return);
                                                      }  //ends if

                        else if ((user_id_existence == true)&&(email_existence == true)&&(pass_existence == false)) {
                            Toast.makeText(getBaseContext(), "I'm Sorry, Incorrect Password!", Toast.LENGTH_SHORT).show();
                            password_for_confirmation.setText("");
                            email_existence=false;
                        } else {
                            Toast.makeText(getApplicationContext(), "I'm Sorry, some of your entries are incorrect!", Toast.LENGTH_SHORT).show();
                            user_id_to_delete.setText("");
                            email_to_delete.setText("");
                            password_for_confirmation.setText("");
                              }
                    }  //ends while
                    CR.close();
                }  //ends else
            }  //ends else
                             }  //ends if
                                }  //ends onClick method
}//ends class