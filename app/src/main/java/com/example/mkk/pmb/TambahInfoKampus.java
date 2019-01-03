package com.example.mkk.pmb;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.os.Environment.DIRECTORY_PICTURES;
import static android.os.Environment.getExternalStoragePublicDirectory;

public class TambahInfoKampus extends AppCompatActivity {
    DataHelper dbHelper;
    private SQLiteDatabase db = null;
    protected Cursor cursor;
    Button btn1, btnFoto;
    EditText edtJudul, edtDesk;
    TextView pathPoto;
    Intent intent;

    private static final int GALERY = 301;
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;
    private Uri fileUri;
    private static final String IMAGE_DIRECTORY_NAME = "Photo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tambah_info_kampus);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar menu = (TambahInfoKampus.this).getSupportActionBar();
        if (menu != null) {
            menu.setDisplayHomeAsUpEnabled(true);
        }

        dbHelper = new DataHelper(this);
        db = dbHelper.getWritableDatabase();

        btn1 = (Button) findViewById(R.id.bt_simpan);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                edtJudul = (EditText) findViewById(R.id.edt_judul);
                edtDesk = (EditText) findViewById(R.id.edt_desk);

                db.execSQL("INSERT INTO info_kampus (judul_info, deskripsi) VALUES ('" +
                        edtJudul.getText().toString() + "','" +
                        edtDesk.getText().toString() + "')");
                Toast.makeText(getApplicationContext(), "Berhasil", Toast.LENGTH_LONG).show();
                InfoKampusActivity.ika.RefreshList();
                finish();
            }
        });

        btnFoto = (Button) findViewById(R.id.btn_foto);

        final String [] pilih			= new String [] {"Camera", "Galery"};
        @SuppressWarnings("Convert2Diamond") ArrayAdapter<String> arr_adapter	= new ArrayAdapter<String>(this, android.R.layout.select_dialog_item,pilih);
        AlertDialog.Builder builder		= new AlertDialog.Builder(this);

        builder.setTitle("Pilih Gambar");
        builder.setAdapter( arr_adapter, new DialogInterface.OnClickListener()
        {
            public void onClick( DialogInterface dialog, int pilihan )
            {

                if (pilihan == 0)
                {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                    intent.resolveActivity(getPackageManager());
                    startActivityForResult(intent, 100);

                /*    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                    startActivityForResult(intent, 100); */

                }
                else if(pilihan == 1)
                {
                    Intent intent = new Intent();
                    intent.setType("*/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);

                    startActivityForResult(Intent.createChooser(intent, "Pilih Aplikasi"), GALERY);
                }
            }


        } );

        final AlertDialog dialog = builder.create();

        btnFoto = (Button) findViewById(R.id.btn_foto);
        if (btnFoto != null) {
            btnFoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.show();
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode != RESULT_OK) return;
        if (requestCode == GALERY) {
            if (null == data) return;

            String selectedImagePath;
            Uri selectedImageUri = data.getData();

            //MEDIA GALLERY
            selectedImagePath = ImageFilePath.getPath(getApplicationContext(), selectedImageUri);
            Log.i("Image File Path", "" + selectedImagePath);

            pathPoto = (TextView) findViewById(R.id.tv_pathphoto);
            assert pathPoto != null;
            pathPoto.setText(selectedImagePath);
            pathPoto.setVisibility(View.GONE);
            assert selectedImagePath != null;

            //meta.setVisibility(View.GONE);
            File imgFile = new File(selectedImagePath);
            if (imgFile.exists()) {
                Log.d("Response :", " Ada Gambar");
                Log.d("Response :", " Ada Gambar");
                Bitmap bm = decodeSampledBitmapFromUri(selectedImagePath, 220, 220);
                ImageView myImage = (ImageView) findViewById(R.id.imV_Foto);
                assert myImage != null;
                myImage.setImageBitmap(bm);
            } else {
                Log.d("Response :", " Tidak Ada Gambar");
            }
        }
        else if(requestCode == 100)
        {

            previewCapturedImage();

        }

    }

    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }
    private static File getOutputMediaFile(int type) {
        File mediaStorageDir = new File(
                getExternalStoragePublicDirectory(DIRECTORY_PICTURES),
                IMAGE_DIRECTORY_NAME);
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(IMAGE_DIRECTORY_NAME, "Direktori " + IMAGE_DIRECTORY_NAME + " tidak ditemukan");
                return null;
            }
        }
        // buat nama file
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".png");
        } else if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "VID_" + timeStamp + ".mp4");
        } else {
            return null;
        }
        return mediaFile;
    }

    private void previewCapturedImage() {
        try {
            Log.d("Response :", " Ada Gambar" + fileUri.getPath() );
            Bitmap bm = decodeSampledBitmapFromUri(fileUri.getPath(), 220, 220);
            ImageView myImage = (ImageView) findViewById(R.id.imV_Foto);
            assert myImage != null;
            myImage.setImageBitmap(bm);
            pathPoto = (TextView)findViewById(R.id.tv_pathphoto);
            pathPoto.setText(fileUri.getPath());
            pathPoto.setVisibility(View.GONE);
            //Exif.setVisibility(View.GONE);
        } catch (NullPointerException e) {
            e.printStackTrace();
            Log.d("Response :", " error" + fileUri.getPath() + " " + e.getMessage());
        }
    }

    public Bitmap decodeSampledBitmapFromUri(String path, int reqWidth, int reqHeight) {

        Bitmap bm;
        final ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        final BitmapFactory.Options options = new BitmapFactory.Options();
        BitmapFactory.decodeFile(path, options);
        options.inJustDecodeBounds = true;
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        Matrix matrix = new Matrix();
        matrix.postRotate(0);
        bm = BitmapFactory.decodeFile(path, options);
        bm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
        bm.compress(Bitmap.CompressFormat.JPEG, 60, bytes);
        try {
            FileOutputStream outStream = new FileOutputStream(new File(path));
            outStream.write(bytes.toByteArray());
            outStream.close();
        } catch (FileNotFoundException e) {

        } catch (IOException e) {

        }

        return bm;
    }
    public int calculateInSampleSize(

            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            if (width > height) {
                inSampleSize = Math.round((float)height / (float)reqHeight);
            } else {
                inSampleSize = Math.round((float)width / (float)reqWidth);
            }
        }

        return inSampleSize;
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
