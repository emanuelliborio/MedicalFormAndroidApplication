package com.example.michael.copaydirect;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Michael on 4/24/2015.
 */
public class Create_User extends Activity implements OnClickListener {
    private Button Register;
	private EditText new_user_id, new_Email, new_Password, new_Password_Again=null;
    private String new_user_id_string,new_email_string, new_pass_string, new_confirm_pass_string="";
    private Context create_user_context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user_view);

        View title = getWindow().findViewById(android.R.id.title);
        View titleBar = (View) title.getParent();
        titleBar.setBackgroundColor(getResources().getColor(R.color.blue));


        new_user_id = (EditText) findViewById(R.id.new_user_id);
        new_Email = (EditText) findViewById(R.id.new_email_address_id);
		new_Password = (EditText) findViewById(R.id.new_pass_id);
		 new_Password_Again = (EditText) findViewById(R.id.new_pass_again_id);

        Register = (Button) findViewById(R.id.register_id);
        Register.setOnClickListener(this);

    }  //ends onCreate method
                //store email address and password in database
                //email new user their credentials
                //Return to Daysheet_Login
    public void onClick(View v) {
        new_user_id_string = new_user_id.getText().toString();
        new_email_string = new_Email.getText().toString();
        new_pass_string = new_Password.getText().toString();
        new_confirm_pass_string = new_Password_Again.getText().toString();

        if (v == Register)   {
            DatabaseOperations DOP_Users = new DatabaseOperations(create_user_context);

            Cursor CR_Users = DOP_Users.getUserInformation(DOP_Users);
            CharSequence cs = new_email_string;

            if(new_user_id_string.equals(null)||new_email_string.equals(null)||new_pass_string.equals(null)||new_confirm_pass_string.equals(null)){
                Toast.makeText(getBaseContext(), "Please fill in all of the fields", Toast.LENGTH_SHORT).show();   }


            else if(new_user_id_string.length()<9){
                Toast.makeText(getBaseContext(), "Please enter 9 characters as your User ID!", Toast.LENGTH_SHORT).show();}


            else if(isValidEmail(cs)==false){
                Toast.makeText(getBaseContext(), "Please enter a valid email address.", Toast.LENGTH_SHORT).show();}

            else if (!new_confirm_pass_string.equals(new_pass_string)) {
                new_Password.setText("");
                new_Password_Again.setText("");
                Toast.makeText(getApplicationContext(), "Please retype the same password.", Toast.LENGTH_SHORT).show();
                                                                       }  //ends if

            else if(CR_Users.getCount()==0 || CR_Users ==null){
                CR_Users.moveToFirst();
                DOP_Users.put_User_Table_Info(DOP_Users,new_user_id_string, new_email_string, new_pass_string);
                Toast.makeText(getBaseContext(), "New Account Created Successfully! Welcome USER ID: "+new_user_id_string, Toast.LENGTH_SHORT).show();
                CR_Users.close();
                Intent go_back_home  = new Intent(this, App_Home.class);
                go_back_home.putExtra("new_user_id", new_user_id_string);
                go_back_home.putExtra("new_pass_id", new_pass_string);
                startActivity(go_back_home);

                new_user_id.setText("");
                new_Email.setText("");
                new_Password.setText("");
                new_Password_Again.setText("");
                                                  }


            else {

                CR_Users.moveToFirst();
                Toast.makeText(getBaseContext(), "Checking if email is already registered...", Toast.LENGTH_SHORT).show();

                boolean new_user_creation = true;

                do {
                    //0 is the index of the first column, user_email
                    //check if your email is already in the "users" table of the database
                    if (new_user_id_string==(CR_Users.getString(0))) {
                        new_user_creation = false;
                    }  //ends if
                }  //ends do
                while (CR_Users.moveToNext());
                {
                    if (new_user_creation == true) {
                        //this is a new email and password to put in the "Daysheet_Users" table of the database
                        DOP_Users.put_User_Table_Info(DOP_Users,new_user_id_string, new_email_string, new_pass_string);
                        Toast.makeText(getBaseContext(), "New Account Created Successfully! Welcome USER ID: " + new_user_id_string, Toast.LENGTH_SHORT).show();
                        CR_Users.close();
                        Intent go_back_home  = new Intent(this, App_Home.class);
                        go_back_home.putExtra("new_user_id", new_user_id_string);
                        go_back_home.putExtra("new_pass_id", new_pass_string);
                        startActivity(go_back_home);

                        new_user_id.setText("");
                        new_Email.setText("");
                        new_Password.setText("");
                        new_Password_Again.setText("");
                    }  //ends if
                    else {
                        Toast.makeText(getBaseContext(), "An account already has that User ID. Click forgot password if you think its yours.", Toast.LENGTH_SHORT).show();
                        new_user_id.setText("");
                        new_Email.setText("");
                        new_Password.setText("");
                        new_Password_Again.setText("");
                        //go back to home page if account already exists
                        CR_Users.close();
                        Intent go_home = new Intent(this, App_Home.class);
                        startActivity(go_home);
                         }  //ends else
                }  //ends while

            } //ends else
        }  //ends if
    }  //ends onClick method
    public final static boolean isValidEmail(CharSequence email_entry) {
        return !TextUtils.isEmpty(email_entry) && android.util.Patterns.EMAIL_ADDRESS.matcher(email_entry).matches();
    }
                                                                    }  //ends class
