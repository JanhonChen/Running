package com.example.janhon.running.Running;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import java.util.Timer;
import java.util.TimerTask;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Handler.Callback;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import com.example.janhon.running.R;

// CP102 陳建宏
public class RunningDataActivity extends Activity {
    TextView timerTextView;
    long startTime ;


    //runs without a timer by reposting this handler at the end of the runnable
    Handler timerHandler = new Handler();  //將訊息與執行緒合併
    Runnable timerRunnable = new Runnable() {  //新執行緒

        @Override
        public void run() {   //內部匿名類別
            long millis = System.currentTimeMillis() - startTime;
            int seconds = (int) (millis / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;
            int hours = minutes /60;
            minutes = minutes % 60 ;
            timerTextView.setText(String.format("%d:%02d:%02d",hours, minutes, seconds));
            timerHandler.postDelayed(this, 500);
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_running_data);

        startTime = System.currentTimeMillis();   //設定起始時間為0
        timerHandler.postDelayed(timerRunnable, 0);
        timerTextView = (TextView) findViewById(R.id.text);
        Button button = (Button)findViewById(R.id.btPause);
        Button button1 = (Button)findViewById(R.id.btPlay);
        button1.setVisibility(button1.GONE);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onPauseClick(v);
            }
        });
    }

    public void onPauseClick(View view) {
        super.onPause();
        Button button = (Button)findViewById(R.id.btPause);  //將暫停鈕移除
        button.setVisibility(View.GONE);
        timerHandler.removeCallbacks(timerRunnable);  //移除執行緒
        Button button1 = (Button)findViewById(R.id.btPlay);  //將繼續鈕置入
        button1.setVisibility(View.VISIBLE);


    }

    public void onPlayClick(View view) {

        timerHandler.postDelayed(timerRunnable, 0);
        timerTextView = (TextView) findViewById(R.id.text);
        Button button1 = (Button)findViewById(R.id.btPlay);
        button1.setVisibility(View.GONE);
        Button button = (Button)findViewById(R.id.btPause);
        button.setVisibility(View.VISIBLE);
    }

}
