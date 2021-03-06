package cs301.birthdaycake;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceView;

public class CakeView extends SurfaceView {

    /* These are the paints we'll use to draw the birthday cake below */
    Paint cakePaint = new Paint();
    Paint frostingPaint = new Paint();
    Paint candlePaint = new Paint();
    Paint outerFlamePaint = new Paint();
    Paint innerFlamePaint = new Paint();
    Paint wickPaint = new Paint();
    Paint balloonPaint = new Paint();
    Paint stringPaint = new Paint();
    Paint brightRed = new Paint();
    Paint greenPaint = new Paint();
    Paint redPaint = new Paint();

    /* These constants define the dimensions of the cake.  While defining constants for things
        like this is good practice, we could be calculating these better by detecting
        and adapting to different tablets' screen sizes and resolutions.  I've deliberately
        stuck with hard-coded values here to ease the introduction for CS371 students.
     */
    public static final float cakeTop = 400.0f;
    public static final float cakeLeft = 100.0f;
    public static final float cakeWidth = 1200.0f;
    public static final float layerHeight = 200.0f;
    public static final float frostHeight = 50.0f;
    public static final float candleHeight = 300.0f;
    public static final float candleWidth = 40.0f;
    public static final float wickHeight = 30.0f;
    public static final float wickWidth = 6.0f;
    public static final float outerFlameRadius = 30.0f;
    public static final float innerFlameRadius = 15.0f;


    //Declare private instance variable CakeModel
    private CakeModel cakeInfo;



    /**
     * ctor must be overridden here as per standard Java inheritance practice.  We need it
     * anyway to initialize the member variables
     */
    public CakeView(Context context, AttributeSet attrs) {
        super(context, attrs);

        //Initialize instance of CakeModel
        cakeInfo = new CakeModel();

        //This is essential or your onDraw method won't get called
        setWillNotDraw(false);

        //Setup our palette
        cakePaint.setColor(0xFF9FCFDF);  //gray-ish sky-ish blue
        cakePaint.setStyle(Paint.Style.FILL);
        frostingPaint.setColor(0xFFFFFACD);  //pale yellow
        frostingPaint.setStyle(Paint.Style.FILL);
        candlePaint.setColor(0xFF32CD32);  //lime green
        candlePaint.setStyle(Paint.Style.FILL);
        outerFlamePaint.setColor(0xFFFFD700);  //gold yellow
        outerFlamePaint.setStyle(Paint.Style.FILL);
        innerFlamePaint.setColor(0xFFFFA500);  //orange
        innerFlamePaint.setStyle(Paint.Style.FILL);
        wickPaint.setColor(Color.BLACK);
        wickPaint.setStyle(Paint.Style.FILL);
        balloonPaint.setColor(Color.BLUE);
        stringPaint.setColor(Color.BLACK);
        greenPaint.setColor(Color.GREEN);
        greenPaint.setStyle(Paint.Style.FILL);
        redPaint.setColor(Color.RED);
        redPaint.setStyle(Paint.Style.FILL);

        brightRed.setColor(0xFFFFA160);
        brightRed.setTextSize(30);
        setBackgroundColor(Color.WHITE);  //better than black default



    }

    /**
     * draws a candle at a specified position.  Important:  the left, bottom coordinates specify
     * the position of the bottom left corner of the candle
     */
    public void drawCandle(Canvas canvas, float left, float bottom) {
        if (cakeInfo.hasCandles) {

            canvas.drawRect(left, bottom - candleHeight, left + (candleWidth * 3), bottom, candlePaint);


            if (cakeInfo.candlesLit) {
                //draw the outer flame
                float flameCenterX = left + (candleWidth * 3) / 2;
                float flameCenterY = bottom - wickHeight - candleHeight - outerFlameRadius / 3;
                canvas.drawCircle(flameCenterX, flameCenterY, outerFlameRadius, outerFlamePaint);

                //draw the inner flame
                flameCenterY += outerFlameRadius / 3;
                canvas.drawCircle(flameCenterX, flameCenterY, innerFlameRadius, innerFlamePaint);
            }

            //draw the wick
            float wickLeft = left + (candleWidth * 3) / 2 - wickWidth / 2;
            float wickTop = bottom - wickHeight - candleHeight;
            canvas.drawRect(wickLeft, wickTop, wickLeft + wickWidth, wickTop + wickHeight, wickPaint);
        }
    }

    /**
     * onDraw is like "paint" in a regular Java program.  While a Canvas is
     * conceptually similar to a Graphics in javax.swing, the implementation has
     * many subtle differences.  Show care and read the documentation.
     *
     * This method will draw a birthday cake
     */
    @Override
    public void onDraw(Canvas canvas)
    {
        //top and bottom are used to keep a running tally as we progress down the cake layers
        float top = cakeTop;
        float bottom = cakeTop + frostHeight;

        //Frosting on top
        canvas.drawRect(cakeLeft, top, cakeLeft + cakeWidth, bottom, frostingPaint);
        top += frostHeight;
        bottom += layerHeight;

        //Then a cake layer
        canvas.drawRect(cakeLeft, top, cakeLeft + cakeWidth, bottom, cakePaint);
        top += layerHeight;
        bottom += frostHeight;

        //Then a second frosting layer
        canvas.drawRect(cakeLeft, top, cakeLeft + cakeWidth, bottom, frostingPaint);
        top += frostHeight;
        bottom += layerHeight;

        //Then a second cake layer
        canvas.drawRect(cakeLeft, top, cakeLeft + cakeWidth, bottom, cakePaint);

        //Now a candle in the center

        for (int i = 1; i <= cakeInfo.numCandles; i++) {
            drawCandle(canvas, cakeLeft + i * (cakeWidth) / (cakeInfo.numCandles + 1) - (candleWidth) / (cakeInfo.numCandles) , cakeTop);
            //drawCandle(canvas, cakeLeft + 2 * cakeWidth / -candleWidth / 2, cakeTop);
        }

        if (cakeInfo.balloonX > 0 && cakeInfo.balloonY > 0) {
            drawBalloon(canvas, cakeInfo.balloonX, cakeInfo.balloonY);
        }


        if(cakeInfo.clickX > 0 && cakeInfo.clickY > 0) {
            writeText(canvas);
        }

        // checkerboard pattern
        if (cakeInfo.squareX >= 0 && cakeInfo.squareY >= 0) {
            canvas.drawRect(cakeInfo.squareX - 50, cakeInfo.squareY - 50,
                    cakeInfo.squareX, cakeInfo.squareY, greenPaint);
            canvas.drawRect(cakeInfo.squareX, cakeInfo.squareY - 50,
                    cakeInfo.squareX + 50, cakeInfo.squareY, redPaint);
            canvas.drawRect(cakeInfo.squareX - 50, cakeInfo.squareY,
                    cakeInfo.squareX, cakeInfo.squareY + 50, redPaint);
            canvas.drawRect(cakeInfo.squareX, cakeInfo.squareY,
                    cakeInfo.squareX + 50, cakeInfo.squareY + 50, greenPaint);
        }
    }//onDraw

    //getter method for CakeModel
    public CakeModel getCakeModel() {
        return cakeInfo;
    }

    public void writeText(Canvas canvas) {
        canvas.drawText("(" + cakeInfo.clickX + " ," + cakeInfo.clickY + ")", 1000, 1000, brightRed);
    }

    public void drawBalloon (Canvas canvas, float centerX, float centerY) {

        canvas.drawOval(centerX - 50.0f, centerY - 75.0f, centerX + 50.0f, centerY + 75.0f, balloonPaint);
        canvas.drawRect(centerX - 5.0f, centerY + 75.0f, centerX + 5.0f, centerY + 150.0f, stringPaint);

    }


}//class CakeView

