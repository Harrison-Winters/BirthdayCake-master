package cs301.birthdaycake;

import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.SeekBar;


public class CakeController implements View.OnClickListener, CompoundButton.OnCheckedChangeListener
, SeekBar.OnSeekBarChangeListener{

    private CakeView currCakeView;
    private CakeModel currCakeModel;

    public CakeController(CakeView refCakeView) {
        this.currCakeView = refCakeView;
        this.currCakeModel = this.currCakeView.getCakeModel();
    }

    @Override
    public void onClick(View view) {

        Log.d("controller", "received click" );

        currCakeModel.candlesLit = false;
        currCakeView.invalidate();



    }


    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

        currCakeModel.hasCandles = !currCakeModel.hasCandles;
        currCakeView.invalidate();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

        currCakeModel.numCandles = i;
        currCakeView.invalidate();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
