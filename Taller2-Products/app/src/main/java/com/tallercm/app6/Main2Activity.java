package com.tallercm.app6;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class Main2Activity extends AppCompatActivity {
    ImageView imageView;
    TextView textView;

    final Target target = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            imageView.setImageBitmap(bitmap);
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {

        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        imageView = findViewById(R.id.imageView);
        textView = findViewById(R.id.textView);

        System.out.println("Second activiyi loaded-------------");

        imageView.setTag(target);

        Bundle extras = getIntent().getExtras();
        String imageUrl = extras.getString("image");
        String value = extras.getString("desc");

        Picasso.with(this).load(imageUrl).into(target);
        textView.setText(value + " ");
    }
}
