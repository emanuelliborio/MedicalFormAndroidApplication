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
public class My_Patients_Ins_Ver_TableView extends Activity  {
    String user_id_string;
    TableLayout ins_ver_table_layout;

    private float mx, my;
    private float curX, curY;

    private ScrollView vScroll;
    private HorizontalScrollView hScroll;


    DatabaseOperations DOP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_patients_ins_ver_tableview);

        View title = getWindow().findViewById(android.R.id.title);
        View titleBar = (View) title.getParent();
        titleBar.setBackgroundColor(getResources().getColor(R.color.blue));

        Intent id_receiver = getIntent();
        user_id_string = id_receiver.getStringExtra("user_id");
        setTitle("Hi, "+ user_id_string+"!");



        vScroll = (ScrollView) findViewById(R.id.vScroll);
        hScroll = (HorizontalScrollView) findViewById(R.id.hScroll);

        DOP = new DatabaseOperations(this);






        ins_ver_table_layout = (TableLayout) findViewById(R.id.ins_ver_table_Layout);

        BuildTable();

    }   //ends onCreate method


    private void BuildTable() {


        Cursor CR_Ins = DOP.getMyInsVerPatientInformation(DOP, user_id_string);

        int row_total = CR_Ins.getCount();
        int col_count = CR_Ins.getColumnCount();

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
            db_col_name.setText(CR_Ins.getColumnName(column_index));
            row.addView(db_col_name);

        }  //ends for loop

        ins_ver_table_layout.addView(row);


        CR_Ins.moveToFirst();


        // navigates row by row
        for (int row_index = 0; row_index < row_total; row_index++) {

             row = new TableRow(this);
           tableRowParams=new TableLayout.LayoutParams
                    (TableLayout.LayoutParams.MATCH_PARENT,TableLayout.LayoutParams.MATCH_PARENT);
                     row.setLayoutParams(tableRowParams);

                // fills each column value in one row
                for (int column_pos = 0; column_pos < col_count; column_pos++) {

                    TextView db_value = new TextView(this);

                    db_value.setSingleLine(true);
                    db_value.setImeOptions(EditorInfo.IME_FLAG_NO_ENTER_ACTION);
                    db_value.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
                    db_value.setBackgroundResource(R.drawable.cell_shape);
                    db_value.setGravity(Gravity.CENTER);
                    db_value.setTextSize(18);
                    db_value.setPadding(10, 10, 10, 10);

                    if (column_pos == 0 || column_pos == 4 || column_pos == 5 || column_pos == 6 || column_pos == 7 || column_pos == 8 || column_pos == 9) {
                        db_value.setText(String.valueOf(CR_Ins.getInt(column_pos)));
                        row.addView(db_value);}  //ends if
                    else {
                        db_value.setText(CR_Ins.getString(column_pos));
                        row.addView(db_value);
                         }
                                                                               }  //ends for loop
                CR_Ins.moveToNext();
                ins_ver_table_layout.addView(row);

                                                                     }   //ends for loop
        CR_Ins.close();
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