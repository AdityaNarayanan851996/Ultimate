package com.example.myfinal.ultimate;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class crud extends AppCompatActivity {

    private MyDBHelper dbHelper;

    private EditText et_id;
    private EditText et_fstname;
    private EditText et_lstname;
    private EditText et_salary;
    private EditText et_country;
    private Button btn_insert;
    private Button btn_delete;
    private Button btn_load;
    private Button btn_update;
    private TextView tv_data;
    private long result_insert;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud);

        dbHelper = new MyDBHelper(crud.this);

        init();

    }

    private void init() {
        et_id = (EditText)findViewById(R.id.et_id);
        et_fstname = (EditText)findViewById(R.id.et_fstname);
        et_lstname = (EditText)findViewById(R.id.et_lstname);
        et_salary = (EditText)findViewById(R.id.et_salary);
        et_country = (EditText)findViewById(R.id.et_country);

        btn_insert = (Button)findViewById(R.id.btn_insert);
        btn_delete = (Button)findViewById(R.id.btn_delete);
        btn_load = (Button)findViewById(R.id.btn_load);
        btn_update = (Button)findViewById(R.id.btn_update);

        btn_insert.setOnClickListener(dbHelperlist);
        btn_delete.setOnClickListener(dbHelperlist);
        btn_load.setOnClickListener(dbHelperlist);
        btn_update.setOnClickListener(dbHelperlist);





        tv_data = (TextView)findViewById(R.id.tv_data);


    }

    private View.OnClickListener dbHelperlist;

    private long result_update;

    private long result_delete;

    {
        dbHelperlist = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                switch (view.getId()) {
                    case R.id.btn_insert:
                        result_insert = dbHelper.insert(Integer.parseInt(getValue(et_id)),getValue(et_fstname),getValue(et_lstname), Double.parseDouble(getValue(et_salary)),getValue(et_country));

                        if(result_insert ==-1){
                            Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(crud.this,"SuccesInsert @ DataID = "+ result_insert,Toast.LENGTH_SHORT).show();
                        }

                        break;

                    case R.id.btn_delete:
                        result_delete = dbHelper.delete(Integer.parseInt(getValue(et_id)));

                        if(result_delete ==0){
                            Toast.makeText(crud.this,"Error",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(crud.this,"SuccesDelete ",Toast.LENGTH_SHORT).show();
                        }


                        break;
                    case R.id.btn_load:
                        StringBuffer finaldata = new StringBuffer();

                        Cursor cursor = dbHelper.getAllRecords();
          //              Toast.makeText(getApplicationContext(),"BUTTONCLICKED! ",Toast.LENGTH_SHORT).show();

                        for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){

                            finaldata.append(cursor.getInt(cursor.getColumnIndex(MyDBHelper.ID)));
                            finaldata.append(" - ");
                            finaldata.append(cursor.getString(cursor.getColumnIndex(MyDBHelper.First_NAME)));
                            finaldata.append("  -   ");
                            finaldata.append(cursor.getString(cursor.getColumnIndex(MyDBHelper.Last_NAME)));
                            finaldata.append("  -   ");
                            finaldata.append(cursor.getLong(cursor.getColumnIndex(MyDBHelper.Salary)));
                            finaldata.append("  -   ");
                            finaldata.append(cursor.getString(cursor.getColumnIndex(MyDBHelper.Address)));
                            finaldata.append("\n");
                        }

                        tv_data.setText(finaldata);



                        break;

                    case R.id.btn_update:
                        result_update = dbHelper.update(Integer.parseInt(getValue(et_id)),getValue(et_fstname),getValue(et_lstname), Double.parseDouble(getValue(et_salary)),getValue(et_country));

                        if(result_update==0){
                            Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();
                        }
                        if(result_update==1){
                            Toast.makeText(crud.this,"SuccesUpdate @ DataID = "+ result_update,Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(crud.this,"DeepErrorOccured "+ result_update,Toast.LENGTH_SHORT).show();


                        }

                        break;


                }

            }
        };
    }

    private String getValue(EditText editText) {
        return editText.getText().toString().trim();

    }

    @Override
    protected void onStart() {
        super.onStart();
        dbHelper.openDB();

    }

    @Override
    protected void onStop() {
        super.onStop();
        dbHelper.closeDB();
    }
}
