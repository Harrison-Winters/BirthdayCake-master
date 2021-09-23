package cs301.birthdaycake;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.SeekBar;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_main);

        CakeView cakeV = (CakeView) findViewById(R.id.cakeview);
        CakeController CakeC = new CakeController(cakeV);

        View refBlowOut = findViewById(R.id.Blow_Out);
        refBlowOut.setOnClickListener(CakeC);

        CompoundButton refCandleSwitch = (CompoundButton) findViewById(R.id.Candle_Switch);
        refCandleSwitch.setOnCheckedChangeListener(CakeC);

        SeekBar refSeekBar = findViewById(R.id.seekBar);
        refSeekBar.setOnSeekBarChangeListener(CakeC);

        cakeV.setOnTouchListener(CakeC);

    }
    public void goodbye(View button) {
       Log.i("button", "Goodbye");
    }

}
