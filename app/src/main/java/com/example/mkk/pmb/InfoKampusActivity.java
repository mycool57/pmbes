package com.example.mkk.pmb;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class InfoKampusActivity extends AppCompatActivity {
    DataHelper dcenter;
    private SQLiteDatabase db=null;
    protected Cursor cursor;
    String SqlString;
    String [] id_info, judul, deskripsi;
    public static InfoKampusActivity ika;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kampus);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar menu = (InfoKampusActivity.this).getSupportActionBar();
        if (menu != null) {
            menu.setDisplayHomeAsUpEnabled(true);
        }

        ika=this;
        dcenter = new DataHelper(this);
        RefreshList();

    }

    public void RefreshList() {
        db = dcenter.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM info_kampus",null);
        id_info = new String[cursor.getCount()];
        judul = new String[cursor.getCount()];
        cursor.moveToFirst();
        for (int cc = 0; cc < cursor.getCount(); cc++) {
            cursor.moveToPosition(cc);
            id_info[cc] = cursor.getString(0);
            judul[cc] = cursor.getString(1);
        }

        ListView list1=(ListView) findViewById(R.id.lv_kampus);
        list1.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, judul));
        list1.setSelected(true);
        list1.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            public void onItemClick(AdapterView arg0, View arg1, int arg2, long arg3) {
                final String selection = id_info[arg2]; //.getItemAtPosition(arg2).toString();
                final CharSequence[] dialogitem = {"Lihat Data","Update Data", "Hapus Data"};
                AlertDialog.Builder builder = new AlertDialog.Builder(InfoKampusActivity.this);
                builder.setTitle("Pilihan");
                builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        switch (item) {
                            case 0:
                                Intent on = new Intent(getApplicationContext(), LihatInfoKampus.class);
                                on.putExtra("id_info", selection);
                                startActivity(on);
                                break;
                            case 1:
                                Intent in = new Intent(getApplicationContext(), UpdateInfoKampus.class);
                                in.putExtra("id_info", selection);
                                startActivity(in);
                                break;
                            case 2:
                                SQLiteDatabase db = dcenter.getWritableDatabase();
                                db.execSQL("DELETE FROM info_kampus WHERE id_info = '" + selection + "'");
                                RefreshList();
                                break;
                        }
                    }
                });
                builder.create().show();
            }
        });
        ((ArrayAdapter) list1.getAdapter()).notifyDataSetInvalidated();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent i=new Intent(getApplicationContext(),TambahInfoKampus.class);
            startActivity(i);
            return true;
        }else if(id == android.R.id.home){
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
