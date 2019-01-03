package com.example.mkk.pmb;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Map;

public class Bottom extends AppCompatActivity {

    GridView androidGridView;

    String[] gridViewString = {
            "Home", "Pendaftaran", "Belajar", "Lulus", "Mading", "Murid",

    } ;
    int[] gridViewImageId = {
            R.drawable.home, R.drawable.buku, R.drawable.belajar, R.drawable.lulus, R.drawable.mading, R.drawable.murid,

    };
    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:

                    return true;
                case R.id.navigation_dashboard:

                    return true;
                case R.id.navigation_calls:
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:0123456789"));
                    startActivity(intent);
                    return true;
                case R.id.navigation_maps:
                    Intent mep = new Intent(Bottom.this, MapsActivity.class);
                    startActivity(mep);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        CustomGridViewActivity adapterViewAndroid = new CustomGridViewActivity(Bottom.this, gridViewString, gridViewImageId);
        androidGridView=(GridView)findViewById(R.id.grid_view_image_text);
        androidGridView.setAdapter(adapterViewAndroid);


        androidGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                Context context = Bottom.this;
                Intent intent = null;
                switch (position) {
                    case 0:
                        intent = new Intent(context, Webview.class);
                        startActivity(intent);
                        return;
                    case 1:
                        intent = new Intent(context, InfoKampusActivity.class);
                        startActivity(intent);
                        return;
                    case 2:
                       //
                        return;
                    case 3:
                        // start another activity
                        return;
                    case 4:
                        // start another activity
                        return;
                    case 5:
                        // start another activity
                        return;
                    // and so on
                }
            }
        });
    }

}
