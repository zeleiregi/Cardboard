package com.example.student.cardboard;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import com.google.vr.sdk.widgets.pano.VrPanoramaView;

import static com.google.vr.sdk.widgets.pano.VrPanoramaView.Options.TYPE_STEREO_OVER_UNDER;


public class MainActivity extends AppCompatActivity {


    private VrPanoramaView VrP;
    private String URL = "http://users.itk.ppke.hu/~matad/andes.jpg";
    private Button btnVid;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnVid = (Button)findViewById(R.id.btnVid);


        VrP = findViewById(R.id.vr_Panorama);
        final VrPanoramaView.Options VrPO = new VrPanoramaView.Options();
        VrPO.inputType = (TYPE_STEREO_OVER_UNDER);

        Glide.with(this).asBitmap().load(URL).into(new SimpleTarget<Bitmap>() {
            @Override
        public void onResourceReady(Bitmap resource, Transition<? super Bitmap>
                transition) {
            VrP.loadImageFromBitmap(resource ,VrPO);
        }
        });

        btnVid.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                VideoActivity();
            }
        });


    }


    public void  VideoActivity(){
    Intent intent = new Intent(getApplicationContext(), VideoActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        VrP.resumeRendering();
    }

    @Override
    protected void onPause() {
        super.onPause();
        VrP.pauseRendering();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
                                               }
}
