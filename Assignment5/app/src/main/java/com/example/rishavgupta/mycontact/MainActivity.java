package com.example.rishavgupta.mycontact;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class MainActivity extends AppCompatActivity{

    TextView noContacts;
    RecyclerView rvContacts;
    public static List<ContactVO> contactVOList;
    public static int position;
    SQLiteDatabase db;
    AlertDialog.Builder adb;
    AllContactsAdapter contactAdapter;
    Context context;
    RecyclerView.ViewHolder vh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context=this;
        if(isPermissionGranted()){
            call_action();
        }
    }
    public void call_action(){
        noContacts = (TextView) findViewById(R.id.noContacts);
        rvContacts = (RecyclerView) findViewById(R.id.rvContacts);
        db = openOrCreateDatabase("mydb", MODE_PRIVATE, null);
        db.execSQL("create table if not exists contacts1(id varchar(100), name varchar(100), number varchar(20))");
        Cursor c = db.rawQuery("select * from contacts1", null);
        if (!c.moveToNext()) {
            getAllContacts();
            //Toast.makeText(getApplicationContext(),"here",Toast.LENGTH_SHORT).show();
        } else {
            //Toast.makeText(getApplicationContext(),"there",Toast.LENGTH_SHORT).show();
            c = db.rawQuery("select * from contacts1", null);
            contactVOList = new ArrayList();
            while (c.moveToNext()) {
                ContactVO contactVO;
                contactVO = new ContactVO();
                contactVO.setContactID(c.getString(0));
                contactVO.setContactName(c.getString(1));
                contactVO.setContactNumber(c.getString(2));
                contactVOList.add(contactVO);
            }
            contactAdapter = new AllContactsAdapter(contactVOList, getApplicationContext());
            rvContacts.setLayoutManager(new LinearLayoutManager(this));
            rvContacts.setAdapter(contactAdapter);
        }
        rvContacts.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), rvContacts, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        MainActivity.position = position;
                        Intent intent = new Intent(getApplicationContext(), Edit.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                vh=viewHolder;
                if (direction == ItemTouchHelper.RIGHT || direction == ItemTouchHelper.LEFT) {
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
                            String id=contactVOList.get(vh.getAdapterPosition()).getContactID();
                            MainActivity.contactVOList.remove(MainActivity.position);
                            db.delete("contacts1", "id" + "=" + id, null);
//                    contactAdapter = new AllContactsAdapter(contactVOList, context);
//                    rvContacts.setLayoutManager(new LinearLayoutManager(context));
//                    rvContacts.setAdapter(contactAdapter);
                            //contactAdapter.notifyItemRangeChanged(viewHolder.getAdapterPosition(),contactVOList.size()-1);
//                    Intent i=new Intent(getApplicationContext(),MainActivity.class);
//                    startActivity(i);
                            Intent intent1=new Intent(context, MainActivity.class);
                            intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            context.startActivity(intent1);

                            Toast.makeText(getApplicationContext(),"Contact Deleted",Toast.LENGTH_SHORT).show();
                        }
                    });
                    adb.show();
                }
            }
        }).attachToRecyclerView(rvContacts);
    }
    public  boolean isPermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
                Log.v("TAG","Permission is granted");
                return true;
            } else {

                Log.v("TAG","Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v("TAG","Permission is granted");
            return true;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {

            case 1: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "Permission granted", Toast.LENGTH_SHORT).show();
                    call_action();
                } else {
                    Toast.makeText(getApplicationContext(), "Permission denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
    private void getAllContacts() {
        contactVOList = new ArrayList();
        ContactVO contactVO;

        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {

                int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)));
                if (hasPhoneNumber > 0) {
                    String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                    String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                    contactVO = new ContactVO();
                    contactVO.setContactName(name);
                    contactVO.setContactID(id);

                    Cursor phoneCursor = contentResolver.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id},
                            null);
                    if (phoneCursor.moveToNext()) {
                        String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        contactVO.setContactNumber(phoneNumber);
                    }

                    phoneCursor.close();

                    Cursor emailCursor = contentResolver.query(
                            ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    while (emailCursor.moveToNext()) {
                        String emailId = emailCursor.getString(emailCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                    }
                    contactVOList.add(contactVO);
                    db.execSQL("insert into contacts1 values('"+id+"','"+contactVO.getContactName()+"','"+contactVO.getContactNumber()+"')");
                }
            }

            AllContactsAdapter contactAdapter = new AllContactsAdapter(contactVOList, getApplicationContext());
            rvContacts.setLayoutManager(new LinearLayoutManager(this));
            rvContacts.setAdapter(contactAdapter);
        }
        else{
            rvContacts.setVisibility(View.GONE);
            noContacts.setVisibility(View.VISIBLE);
        }

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        AllContactsAdapter contactAdapter = new AllContactsAdapter(contactVOList, getApplicationContext());
        rvContacts.setLayoutManager(new LinearLayoutManager(this));
        rvContacts.setAdapter(contactAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.addcontact, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.add) {
            Intent intent = new Intent(getApplicationContext(), Add.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
