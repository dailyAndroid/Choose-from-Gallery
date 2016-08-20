package com.example.hwhong.choosefromgallery;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private ImageView imageView;

    private String string;

    private int CHOOSE_PHOTO = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.chooseBut);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto , CHOOSE_PHOTO);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {

            if (resultCode == RESULT_OK && requestCode == CHOOSE_PHOTO) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                // Get the cursor
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                string = cursor.getString(columnIndex);
                cursor.close();
                imageView = (ImageView) findViewById(R.id.imageView);
                // Set the Image in ImageView after decoding the String
                imageView.setImageBitmap(BitmapFactory
                        .decodeFile(string));
            } else {
                Toast.makeText(this, "No Image Picked",
                        Toast.LENGTH_SHORT).show();
            }
        } catch(Exception e) {
            Toast.makeText(this, "Something went wrong!",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
