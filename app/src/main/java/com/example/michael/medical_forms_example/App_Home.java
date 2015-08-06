package com.example.michael.copaydirect;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.view.View.OnClickListener;


public class App_Home extends Activity implements OnClickListener {
   private EditText USER_ID,Password;
   private Button login_as_user_button,login_as_admin_button,create_user_acct_button, forgot_password_button;
   private String user_id_string, pass, created_user_id_string, created_pass_string="";

  //next try two diffrent contexts maybe
   private Context home_context = this;
   private boolean id_status, login_status = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app__home);

        View title = getWindow().findViewById(android.R.id.title);
        View titleBar = (View) title.getParent();
        titleBar.setBackgroundColor(getResources().getColor(R.color.blue));

        USER_ID = (EditText) findViewById(R.id.app_home_user_id);
        Password = (EditText) findViewById(R.id.password_input_id);


        Intent id_receiver = getIntent();
        created_user_id_string = id_receiver.getStringExtra("new_user_id");
        created_pass_string = id_receiver.getStringExtra("new_pass_id");


            USER_ID.setText(created_user_id_string);
            Password.setText(created_pass_string);



        login_as_user_button = (Button) findViewById(R.id.user_login_id);
        login_as_admin_button= (Button) findViewById(R.id.admin_login_id);
        create_user_acct_button = (Button) findViewById(R.id.create_acct_id);
        forgot_password_button = (Button) findViewById(R.id.forgot_password_id);


        login_as_user_button.setOnClickListener(this);
        login_as_admin_button.setOnClickListener(this);
        create_user_acct_button.setOnClickListener(this);
        forgot_password_button.setOnClickListener(this);

    }  //ends onCreate method

			@Override
public void onClick(View v) {

			//check database for these values


	if(v==login_as_user_button) {
                   user_id_string = USER_ID.getText().toString();
                   pass = Password.getText().toString();

                   Toast.makeText(getApplicationContext(), "Checking Credentials...", Toast.LENGTH_SHORT).show();

                   DatabaseOperations DOP = new DatabaseOperations(home_context);
                   Cursor CR_U = DOP.getUserInformation(DOP);

                   if(user_id_string.equals("")||pass.equals("")){
                        Toast.makeText(getBaseContext(), "Please fill in all of the fields", Toast.LENGTH_SHORT).show();   }

                   else if (CR_U.getCount() == 0 || CR_U == null) {
                        CR_U.moveToFirst();
                        Toast.makeText(getApplicationContext(), "Welcome, First Time User! Please Create a User Account.", Toast.LENGTH_SHORT).show();
                        USER_ID.setText("");
                        Password.setText("");
                        CR_U.close();
                                                                }  //ends if



                    else if(user_id_string.length()<9){
                        Toast.makeText(getBaseContext(), "Your User ID needs to be 9 characters long.", Toast.LENGTH_SHORT).show();}

                    else {

                        CR_U.moveToFirst();

                        do {
                            //0 is first column index 1 is "password" column index
                            if (user_id_string.equals(CR_U.getString(0))) {
                                id_status = true;
                                if (pass.equals(CR_U.getString(2))){
                                    login_status = true;
                                                                   }
                                                                   }  //ends if
                        }  //ends do
                        while (CR_U.moveToNext());
                        {
                            if (login_status == true) {
                                    Toast.makeText(getBaseContext(), "Login Success! Welcome, "+
                                    user_id_string+"!", Toast.LENGTH_SHORT).show();
                                    id_status=false;
                                    login_status=false;


                                    Intent user_options_sender = new Intent(this, User_Options.class);
                                    user_options_sender.putExtra("user_id", USER_ID.getText().toString());
                                    startActivity(user_options_sender);
                                    CR_U.close();
                                    USER_ID.setText("");
                                    Password.setText("");

                            }  //ends if

                            else if((id_status==true) && (login_status==false)){
                                     Password.setText("");
                                     Toast.makeText(getBaseContext(), "Sorry, Incorrect Password!", Toast.LENGTH_SHORT).show();
                                     id_status=false;
                                                                                }
                            else {
                                Toast.makeText(getApplicationContext(), "I'm sorry your entries are incorrect. You may not have an account.", Toast.LENGTH_SHORT).show();
                                USER_ID.setText("");
                                Password.setText("");
                                 }
                        }  //ends while
                        CR_U.close();

                   }  //ends else
                                          }  //ends if


     else if(v==login_as_admin_button) {
                    user_id_string = USER_ID.getText().toString();
                    pass = Password.getText().toString();

                    Toast.makeText(getApplicationContext(), "Checking Credentials...", Toast.LENGTH_SHORT).show();



                    DatabaseOperations DOP = new DatabaseOperations(home_context);
                    Cursor CR_Admin = DOP.getAdminInformation(DOP);

                    CR_Admin.moveToFirst();
                    if (CR_Admin.getCount() == 0||CR_Admin == null) {

                        Toast.makeText(getApplicationContext(), "Please contact this app's developer. There are no administrator accounts set up.", Toast.LENGTH_SHORT).show();
                        USER_ID.setText("");
                        Password.setText("");
                        CR_Admin.close();
                                                                    }  //ends if
                    else if(user_id_string.equals("")&&pass.equals("")){
                        Toast.makeText(getBaseContext(), "Please fill in all of the fields.", Toast.LENGTH_SHORT).show();   }

                    else if(user_id_string.length()<9){
                        Toast.makeText(getBaseContext(), "Your User ID needs to be 9 characters long.", Toast.LENGTH_SHORT).show();}


                    else {
                        CR_Admin.moveToFirst();

                        do {
                            //0 is first column index 1 is "password" column index
                            if (user_id_string.equals(CR_Admin.getString(0))) {
                                id_status = true;
                                if (pass.equals(CR_Admin.getString(2))){
                                    login_status = true;
                                                                       }
                            }  //ends if
                        }  //ends do
                        while (CR_Admin.moveToNext());
                        {
                            if (login_status == true) {
                                Toast.makeText(getBaseContext(), "Login Success! Welcome, "+
                                        user_id_string+"!", Toast.LENGTH_SHORT).show();


                                Intent admin_home_sender = new Intent(this, Admin_Home.class);
                                admin_home_sender.putExtra("admin_id", USER_ID.getText().toString());
                                startActivity(admin_home_sender);
                                CR_Admin.close();
                                USER_ID.setText("");
                                Password.setText("");  }  //ends if

                            else if((id_status==true) && (login_status==false)){
                                Password.setText("");
                                Toast.makeText(getBaseContext(), "I'm Sorry, that password is incorrect.", Toast.LENGTH_SHORT).show();
                                id_status=false;
                            }
                            else {
                                Toast.makeText(getApplicationContext(), "I'm sorry your entries are incorrect.", Toast.LENGTH_SHORT).show();
                                USER_ID.setText("");
                                Password.setText("");
                                 }
                        }  //ends while
                        CR_Admin.close();

                    }  //ends else
                            }  //ends else if

        else if(v==create_user_acct_button) {
            Intent create_user_intention = new Intent(this, Create_User.class);
            startActivity(create_user_intention);
                                            }

               //if(v==forgot_password_button)
                else  {
                    Intent forgot_password = new Intent(this, Forgot_Password.class);
                    startActivity(forgot_password);
                      }
                                            }  //ends onClick method

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;                }

        return super.onOptionsItemSelected(item);
                                                        } //ends onOptionsItemSelected method
}  //ends Daysheet_Login class
