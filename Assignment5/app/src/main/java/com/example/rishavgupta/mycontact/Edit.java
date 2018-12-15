package com.example.rishavgupta.mycontact;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class Edit extends AppCompatActivity {

    TextView name,number;
    Button update;
    AlertDialog.Builder adb;
    Context context;
    String id;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        context=this;
        id=MainActivity.contactVOList.get(MainActivity.position).getContactID();
        //Toast.makeText(getApplicationContext(),""+id,Toast.LENGTH_SHORT).show();
        db=openOrCreateDatabase("mydb",MODE_PRIVATE,null);
        name=(TextView)findViewById(R.id.name);
        number=(TextView)findViewById(R.id.number);
        update=(Button)findViewById(R.id.update);
        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater li = LayoutInflater.from(this);
        View customView = li.inflate(R.layout.custom_menu, null);
        mActionBar.setCustomView(customView);
        mActionBar.setDisplayShowCustomEnabled(true);
        Button addContent = (Button)customView.findViewById(R.id.editbutton);
        String n=MainActivity.contactVOList.get(MainActivity.position).getContactName();
        final String num=MainActivity.contactVOList.get(MainActivity.position).getContactNumber();
        name.setText(n);
        name.setEnabled(false);
        number.setText(num);
        number.setEnabled(false);
        addContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name.setEnabled(true);
                number.setEnabled(true);
            }
        });

        Button search = (Button) customView.findViewById(R.id.deletebutton);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adb=new AlertDialog.Builder(context);
                adb.setTitle("Confirm Delete");
                adb.setMessage("“Are you really want to delete the records?");
                adb.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                });
                adb.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        MainActivity.contactVOList.remove(MainActivity.position);
                        db.delete("contacts1", "id" + "=" + id, null);
                        Toast.makeText(getApplicationContext(),"Contact Deleted",Toast.LENGTH_SHORT).show();
                    }
                });
                adb.show();
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name.isEnabled()) {
                    String n = name.getText()+"";
                    String num=number.getText()+"";
                    MainActivity.contactVOList.get(MainActivity.position).setContactName(n);
                    MainActivity.contactVOList.get(MainActivity.position).setContactNumber(num);
                    ContentValues cv = new ContentValues();
                    cv.put("name",n); //These Fields should be your String values of actual column names
                    cv.put("number",num);
                    db.update("contacts1", cv, "id="+id, null);
                    Toast.makeText(getApplicationContext(),"Contact Updated",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
