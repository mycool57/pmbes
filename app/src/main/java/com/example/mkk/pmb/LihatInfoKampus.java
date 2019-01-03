package com.example.mkk.pmb;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LihatInfoKampus extends AppCompatActivity {
    DataHelper dbHelper;
    private SQLiteDatabase db = null;
    protected Cursor cursor;
    Button btn1;
    EditText edtJudul, edtDesk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lihat_info_kampus);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar menu = (LihatInfoKampus.this).getSupportActionBar();
        if (menu != null) {
            menu.setDisplayHomeAsUpEnabled(true);
        }

        edtJudul = (EditText) findViewById(R.id.edt_judul);
        edtDesk = (EditText) findViewById(R.id.edt_desk);
        edtDesk.setEnabled(false);
        edtJudul.setEnabled(false);

        dbHelper = new DataHelper(this);
        db = dbHelper.getWritableDatabase();
        db = dbHelper.getReadableDatabase();

        cursor = db.rawQuery("SELECT * FROM info_kampus WHERE id_info = '" +
                getIntent().getStringExtra("id_info") + "'",null);
        cursor.moveToFirst();
        if (cursor.getCount()>0)
        {
            cursor.moveToPosition(0);
            edtJudul.setText(cursor.getString(1));
            edtDesk.setText(cursor.getString(2));
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if(id == android.R.id.home){
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
