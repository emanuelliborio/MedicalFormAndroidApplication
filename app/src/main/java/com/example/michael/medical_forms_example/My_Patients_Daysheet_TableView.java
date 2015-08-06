package com.example.michael.copaydirect;

import android.app.Activity;
import android.database.Cursor;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import android.view.MotionEvent;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;


import android.view.Gravity;

import android.widget.TableRow.LayoutParams;

/**
 * Created by Michael on 5/5/2015.
 */
public class My_Patients_Daysheet_TableView extends Activity  {
    String user_id_string;
    TableLayout daysheet_table_layout;

    private float mx, my;
    private float curX, curY;

    private ScrollView vScroll;
    private HorizontalScrollView hScroll;


    DatabaseOperations DOP;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_patients_daysheets_tableview);

        View title = getWindow().findViewById(android.R.id.title);
        View titleBar = (View) title.getParent();
        titleBar.setBackgroundColor(getResources().getColor(R.color.blue));

        Intent id_receiver = getIntent();
        user_id_string = id_receiver.getStringExtra("user_id");
        setTitle("Hi, "+ user_id_string+"!");



        vScroll = (ScrollView) findViewById(R.id.vScroll);
        hScroll = (HorizontalScrollView) findViewById(R.id.hScroll);

        DOP = new DatabaseOperations(this);






        daysheet_table_layout = (TableLayout) findViewById(R.id.daysheet_table_Layout);

        BuildTable();

                                                        }   //ends onCreate method


    private void BuildTable() {


        Cursor CR_Daysheet = DOP.getMyDaysheetPatientInformation(DOP, user_id_string);


        int rows = CR_Daysheet.getCount();
        int col_count = CR_Daysheet.getColumnCount();

        TableRow row = new TableRow(this);
        TableLayout.LayoutParams tableRowParams=new TableLayout.LayoutParams
                (TableLayout.LayoutParams.MATCH_PARENT,TableLayout.LayoutParams.MATCH_PARENT);
                 row.setLayoutParams(tableRowParams);



        for (int column_index = 0; column_index < col_count; column_index++) {
            TextView db_col_name = new TextView(this);
            db_col_name.setSingleLine(true);

            // input-method framework that allows applications to provide users alternative input methods,
            // such as on-screen keyboards or even speech input
            db_col_name.setImeOptions(EditorInfo.IME_FLAG_NO_ENTER_ACTION);
            db_col_name.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            db_col_name.setBackgroundResource(R.drawable.cell_shape);
            db_col_name.setGravity(Gravity.CENTER);

            db_col_name.setTextSize(18);
            db_col_name.setPadding(10, 10, 10, 10);
            db_col_name.setText(CR_Daysheet.getColumnName(column_index));
            row.addView(db_col_name);

        }  //ends for loop

        daysheet_table_layout.addView(row);


        CR_Daysheet.moveToFirst();



        // navigates row by row
        for (int i = 0; i < rows; i++) {

             row = new TableRow(this);
             tableRowParams=new TableLayout.LayoutParams
                    (TableLayout.LayoutParams.MATCH_PARENT,TableLayout.LayoutParams.WRAP_CONTENT);
                     row.setLayoutParams(tableRowParams);
            // fills each column value in one row
            for (int col_pos = 0; col_pos < col_count; col_pos++) {

                TextView database_value = new TextView(this);
                database_value.setSingleLine(false);
                database_value.setImeOptions(EditorInfo.IME_FLAG_NO_ENTER_ACTION);

                database_value.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));




                database_value.setBackgroundResource(R.drawable.cell_shape);

                database_value.setGravity(Gravity.CENTER);

                database_value.setTextSize(18);
                database_value.setPadding(10, 10, 10, 10);
                if(col_pos==0||col_pos==1||col_pos==2||col_pos==3||col_pos==7||col_pos==8||col_pos==9||col_pos==10||col_pos==17||col_pos==18||col_pos==19) {
                    database_value.setText(String.valueOf(CR_Daysheet.getInt(col_pos)));
                    row.addView(database_value);
                                                                    }
                else{
                    database_value.setText(CR_Daysheet.getString(col_pos));
                    row.addView(database_value);
                    }
                                            }  //ends for loop

            CR_Daysheet.moveToNext();
            daysheet_table_layout.addView(row);
                                               }   //ends for loop
        CR_Daysheet.close();
                                }  //ends buildtable() method


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float curX, curY;

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                mx = event.getX();
                my = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                curX = event.getX();
                curY = event.getY();
                vScroll.scrollBy((int) (mx - curX), (int) (my - curY));
                hScroll.scrollBy((int) (mx - curX), (int) (my - curY));
                mx = curX;
                my = curY;
                break;
            case MotionEvent.ACTION_UP:
                curX = event.getX();
                curY = event.getY();
                vScroll.scrollBy((int) (mx - curX), (int) (my - curY));
                hScroll.scrollBy((int) (mx - curX), (int) (my - curY));
                break;
        }

        return true;
    }


}  //ends class