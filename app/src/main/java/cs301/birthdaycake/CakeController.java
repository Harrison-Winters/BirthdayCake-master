package cs301.birthdaycake;

import android.util.Log;
import android.view.View;

public class CakeController implements View.OnClickListener{

    private CakeView currCakeView;
    private CakeModel currCakeModel;

    public CakeController(CakeView refCakeView) {
        this.currCakeView = refCakeView;
        this.currCakeModel = this.currCakeView.getCakeModel();
    }

    @Override
    public void onClick(View view) {
        Log.d("controller", "received click" );
    }
}
