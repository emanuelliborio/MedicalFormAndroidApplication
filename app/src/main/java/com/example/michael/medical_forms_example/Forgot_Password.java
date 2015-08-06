package com.example.michael.copaydirect;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.app.Activity;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;






/**
 * Created by Michael on 4/24/2015.
 */
public class Forgot_Password extends Activity implements OnClickListener {

    private Button send_reminder;
    EditText  Existing_Email;
    String existing_email_string, pass_to_email_string;
    Context con_text = this;
    boolean email_account_exists = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot__password__view);

        View title = getWindow().findViewById(android.R.id.title);
        View titleBar = (View) title.getParent();
        titleBar.setBackgroundColor(getResources().getColor(R.color.blue));

        Existing_Email = (EditText) findViewById(R.id.email_receiver_id);

        send_reminder = (Button) findViewById(R.id.send_email_id);
        send_reminder.setOnClickListener(this);

    }

    public void onClick(View v) {
        existing_email_string = Existing_Email.getText().toString();

        if (v == send_reminder) {
            DatabaseOperations DOP = new DatabaseOperations(con_text);
            Cursor CR = DOP.getUserInformation(DOP);


            if (CR.getCount()==0 || CR ==null){
                Toast.makeText(getApplicationContext(), "Welcome, Please Create a User Account First!.", Toast.LENGTH_SHORT).show();
                Existing_Email.setText("");
                CR.close();
                Intent go_back_home  = new Intent(this, App_Home.class);
                startActivity(go_back_home);
                                              }  //ends if

            else {
                Toast.makeText(getBaseContext(), "Checking if email is already registered...", Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), "Looking for password...", Toast.LENGTH_SHORT).show();

                CR.moveToFirst();


                do {
                    //0 is the index of the first column, user_email
                    //check if your email is already in the "users" table of the database
                    if (existing_email_string.equals(CR.getString(1))) {
                        email_account_exists = true;

                        pass_to_email_string = CR.getString(2);
                    }  //ends if
                }  //ends do
                while (CR.moveToNext());
                {
                    if (email_account_exists == false) {
                        Toast.makeText(getBaseContext(), "Did you enter the correct email? Have you registered yet? No password was found.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getBaseContext(), "Password found! We are emailing you now. Please sync or refresh your email service...", Toast.LENGTH_SHORT).show();
                        AsyncTaskRunner runner = new AsyncTaskRunner();
                        runner.execute(existing_email_string, pass_to_email_string);
                        Existing_Email.setText("");
                        Intent go_home = new Intent(this, App_Home.class);
                        startActivity(go_home);
                    }  //ends else
                } //ends while
                CR.close();
            }  //ends else
        } //ends if

    }  //ends onClick method


private class AsyncTaskRunner extends AsyncTask<String, String, String> {

            @Override
            protected String doInBackground(String... params) {
                publishProgress("Emailing..."); // Calls onProgressUpdate()

                String user_to_receive = params[0];
                String pass_to_email_async = params[1];
             try{
                        String host = "smtp.gmail.com";
                        String emailer = "mscholzjr@gmail.com";

                        String password = "Leoaris29";
                        String body = "Hello Copay Direct App User! Here is your requested password: \n\n"+ pass_to_email_async+"\n\n";
                        String subject = "Your Password Request from Copay Direct";
                        //Set the properties
                        Properties props = new Properties();
                        props.put("mail.smtps.auth", "true");
                        // Set the session here
                        Session session = Session.getInstance(props);
                        MimeMessage msg = new MimeMessage(session);
                        // set the message content here
                        msg.setSubject(subject);
                        msg.setText(body);
                        msg.setFrom(new InternetAddress(emailer));

                        msg.addRecipient(Message.RecipientType.TO, new InternetAddress(user_to_receive));



                        //Port 465 is for smtps - SSL encryption is started automatically before any SMTP level
                        //communication.
                        Transport t = session.getTransport("smtps");
                        t.connect(host, emailer, password);
                        t.sendMessage(msg, msg.getAllRecipients());
                        t.close();
                    } //ends try

                    catch (Exception e) {
                        Toast.makeText(getBaseContext(), "Error emailing password reminder!", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                                        } //ends catch

                return null;  //nothing to return
            } //ends doInBackground method

            @Override
            protected void onPostExecute(String result) {
                // execution of result of Long time consuming operation
               // finalResult.setText(result);
                                                        }  //ends onPostExecute method


            @Override
            protected void onPreExecute() {
                // Things to be done before execution of long running operation. For
                // example showing ProgessDialog
            }  //ends onPreExecute method


            @Override
            protected void onProgressUpdate(String... text) {
              //  finalResult.setText(text[0]);
                // Things to be done while execution of long running operation is in
                // progress. For example updating ProgessDialog
                                                            }  //ends onProgressUpdate method
        }  //ends private class
}  //ends class