package com.example.android.orphanmanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.app.AlertDialog.Builder;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class StudentActivity extends AppCompatActivity {
    EditText oName,oRegistrationNo,oAge,oBloodGroup,oWeight,oHeight;
    Button add,view,viewall,Show1,delete,modify;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        oName=(EditText)findViewById(R.id.name);
        oRegistrationNo=(EditText)findViewById(R.id.registration_no);
        oAge=(EditText)findViewById(R.id.age);
        oBloodGroup=(EditText)findViewById(R.id.blood_group);
        oWeight=(EditText)findViewById(R.id.weight);
        oHeight=(EditText)findViewById(R.id.height);
        add=(Button)findViewById(R.id.addbtn);
        view=(Button)findViewById(R.id.viewbtn);
        viewall=(Button)findViewById(R.id.viewallbtn);
        delete=(Button)findViewById(R.id.deletebtn);
        Show1=(Button)findViewById(R.id.showbtn);
        modify=(Button)findViewById(R.id.modifybtn);

        db=openOrCreateDatabase("Orphan_manage", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS orphan(registration VARCHAR,name VARCHAR,age VARCHAR,bloodgroup VARCHAR,weight VARCHAR,height VARCHAR);");


        add.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(oRegistrationNo.getText().toString().trim().length()==0||
                        oName.getText().toString().trim().length()==0||
                        oAge.getText().toString().trim().length()==0||
                        oBloodGroup.getText().toString().trim().length()==0||
                        oWeight.getText().toString().trim().length()==0||
                        oHeight.getText().toString().trim().length()==0)
                {
                    showMessage("Error", "Please enter all values");
                    return;
                }
                db.execSQL("INSERT INTO orphan VALUES('"+oRegistrationNo.getText()+"','"+oName.getText()+
                        "','"+oAge.getText()+"','"+oBloodGroup.getText()+"','"+oWeight.getText()+"','"+oHeight.getText()+"');");
                showMessage("Success", "Record added successfully");
                clearText();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(oRegistrationNo.getText().toString().trim().length()==0)
                {
                    showMessage("Error", "Please enter Rollno");
                    return;
                }
                Cursor c=db.rawQuery("SELECT * FROM orphan WHERE registration='"+oRegistrationNo.getText()+"'", null);
                if(c.moveToFirst())
                {
                    db.execSQL("DELETE FROM orphan WHERE registration='"+oRegistrationNo.getText()+"'");
                    showMessage("Success", "Record Deleted");
                }
                else
                {
                    showMessage("Error", "Invalid Rollno");
                }
                clearText();
            }
        });
        modify.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(oRegistrationNo.getText().toString().trim().length()==0)
                {
                    showMessage("Error", "Please enter Rollno");
                    return;
                }
                Cursor c=db.rawQuery("SELECT * FROM orphan WHERE registration='"+oRegistrationNo.getText()+"'", null);
                if(c.moveToFirst())
                {
                    db.execSQL("UPDATE student SET name='"+oName.getText()+"',age='"+oAge.getText()+
                            "',bloodgroup='"+oBloodGroup.getText()+"',weight='"+oWeight.getText()+"',height='"+oHeight.getText()+"' WHERE registration='"+oRegistrationNo.getText()+"'");
                    showMessage("Success", "Record Modified");
                }
                else
                {
                    showMessage("Error", "Invalid Rollno");
                }
                clearText();
            }
        });
        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(oRegistrationNo.getText().toString().trim().length()==0)
                {
                    showMessage("Error", "Please enter Rollno");
                    return;
                }
                Cursor c=db.rawQuery("SELECT * FROM orphan WHERE registration='"+oRegistrationNo.getText()+"'", null);
                if(c.moveToFirst())
                {
                    oName.setText(c.getString(1));
                    oAge.setText(c.getString(2));
                    oBloodGroup.setText(c.getString(3));
                    oWeight.setText(c.getString(4));
                    oHeight.setText(c.getString(5));
                }
                else
                {
                    showMessage("Error", "Invalid Rollno");
                    clearText();
                }
            }
        });
        viewall.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Cursor c=db.rawQuery("SELECT * FROM orphan", null);
                if(c.getCount()==0)
                {
                    showMessage("Error", "No records found");
                    return;
                }
                StringBuffer buffer=new StringBuffer();
                while(c.moveToNext())
                {
                    buffer.append("Registration No. : "+c.getString(0)+"\n");
                    buffer.append("Name: "+c.getString(1)+"\n");
                    buffer.append("Age: "+c.getString(2)+"\n\n");
                    buffer.append("Blood group: "+c.getString(3)+"\n\n");
                    buffer.append("Weight: "+c.getString(4)+"\n\n");
                    buffer.append("Height: "+c.getString(5)+"\n\n");
                }
                showMessage("Student Details", buffer.toString());
            }
        });
        Show1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                showMessage("Student Management Application", "Developed By Aryan Badola");
            }
        });

    }
    public void showMessage(String title,String message)
    {
        Builder builder=new Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
    public void clearText()
    {
        oRegistrationNo.setText("");
        oName.setText("");
        oAge.setText("");
        oBloodGroup.setText("");
        oWeight.setText("");
        oHeight.setText("");
        oRegistrationNo.requestFocus();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.student_main, menu);
        return true;
    }

}