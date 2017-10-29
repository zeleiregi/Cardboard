package com.example.student.cardboard;

import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;

import com.google.vr.sdk.widgets.video.VrVideoEventListener;
import com.google.vr.sdk.widgets.video.VrVideoView;

import java.io.IOException;
import java.net.URI;

import static com.google.vr.sdk.widgets.pano.VrPanoramaView.Options.TYPE_STEREO_OVER_UNDER;
import static com.google.vr.sdk.widgets.video.deps.b.l;

public class VideoActivity extends AppCompatActivity {

    protected VrVideoView VrVid;
    protected String URL = "http://users.itk.ppke.hu/~baupa/4.mp4";
    private boolean muted = false;
    private boolean playing = false;
    private SeekBar seekBar;
    private ImageButton imgBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        imgBtn = (ImageButton)findViewById(R.id.imageButton);
        seekBar= (SeekBar)findViewById(R.id.seekBar);
        VrVid = (VrVideoView) findViewById(R.id.vrVid);
        openVideo();

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                VrVid.seekTo(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(muted){
                    muted=false;
                    VrVid.setVolume(1.0f);
                    imgBtn.setImageResource(R.drawable.ic_volume_up_black_24dp);
                }else{
                    muted=true;
                    VrVid.setVolume(0.0f);
                    imgBtn.setImageResource(R.drawable.ic_volume_off_black_24dp);
                }
            }
        });
    }
private void openVideo() {
    new Handler().post(new Runnable() {
        @Override
        public void run() {

            try{
                VrVid.loadVideo(Uri.parse(URL), new VrVideoView.Options());
                VrVid.setEventListener(new VrVideoEventListener(){
                    @Override
                    public void onCompletion() {
                        super.onCompletion();
                        VrVid.seekTo(0);
                    }

                    @Override
                    public void onNewFrame() {
                        super.onNewFrame();
                        seekBar.setProgress((int) VrVid.getCurrentPosition());
                    }

                    @Override
                    public void onLoadSuccess() {
                        super.onLoadSuccess();
                        playing = true;
                        seekBar.setMax((int) VrVid.getDuration());
                    }

                    @Override
                    public void onClick() {
                        super.onClick();
                        if(playing){
                            VrVid.pauseVideo();
                            playing=false;
                        }else{
                            VrVid.playVideo();
                            playing=true;
                        }
                    }
                });


            }catch(IOException e){
                e.printStackTrace();
            }


        }
    });
}

    @Override
    protected void onResume() {
        super.onResume();
        VrVid.resumeRendering();
    }

    @Override
    protected void onPause() {
        super.onPause();
        VrVid.pauseRendering();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        VrVid.shutdown();
    }
}
