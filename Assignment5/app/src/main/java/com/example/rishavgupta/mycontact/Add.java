package com.example.rishavgupta.mycontact;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Pattern;

public class Add extends AppCompatActivity {

    EditText name,number, email;
    Button create;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        name=(EditText) findViewById(R.id.addname);
        number=(EditText)findViewById(R.id.addnumber);
        email=(EditText)findViewById(R.id.addemail);
        create=(Button)findViewById(R.id.create);
        db=openOrCreateDatabase("mydb",MODE_PRIVATE,null);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String n=name.getText()+"";
                String num=number.getText()+"";
                String em=email.getText()+"";
                int flag1=0,flag2=0,flag3=0;
                if(n.length()>3)
                {
                    flag1=1;
                }
                else{
                    flag1=0;
                    Toast.makeText(getApplicationContext(),"Name should be min 4 letters", Toast.LENGTH_SHORT).show();
                }
                if(Integer.parseInt(num.charAt(0) + "")>=6 && Integer.parseInt(num.charAt(0) + "")<=9 && num.length()==10)
                {
                    flag2=1;
                }
                else{
                    flag2=0;
                    Toast.makeText(getApplicationContext(),"Number is of improper format", Toast.LENGTH_SHORT).show();
                }
                if(isValid(em)){
                    flag3=1;
                }
                else
                {
                    flag3=0;
                    Toast.makeText(getApplicationContext(),"Email is of improper format", Toast.LENGTH_SHORT).show();
                }
                if(flag1==1 && flag2==1 && flag3==1)
                {
                    ContactVO contactVO;
                    contactVO = new ContactVO();
                    contactVO.setContactName(n);
                    contactVO.setContactNumber(num);
                    MainActivity.contactVOList.add(contactVO);
                    int max=0;
                    Cursor c=db.rawQuery("select * from contacts1",null);
                    while(c.moveToNext()) {
                        int i=Integer.parseInt(c.getString(0));
                        if(i>max)
                            max=i;
                    }
                    max++;
                    db.execSQL("insert into contacts1 values('"+max+"','"+contactVO.getContactName()+"','"+contactVO.getContactNumber()+"')");
                    Toast.makeText(getApplicationContext(),"Contact Added!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public static boolean isValid(String email)
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }
}
