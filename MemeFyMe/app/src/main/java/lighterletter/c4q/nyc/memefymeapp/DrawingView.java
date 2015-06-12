package lighterletter.c4q.nyc.memefymeapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

/**
 * Created by mona on 5/12/15.
 */
public class DrawingView extends View {

    private static Path drawPath;
    public static Paint drawPaint, canvasPaint;
    private ColorPicker colorPicker;
    private Button useColor;
    private static int paintColor = Color.CYAN;
    public static Canvas drawCanvas;
    private static Bitmap canvasBitmap;
    private float xPos, yPos;

    public DrawingView(Context context, AttributeSet attrs){
        super(context, attrs);

        useColor = (Button) findViewById(R.id.pickColor);
        colorPicker = (ColorPicker) findViewById(R.id.colorWheel);
        setupDrawing();
    }

    public static void setupDrawing() {
        drawPath = new Path();
        drawPaint = new Paint();
        drawPaint.setColor(paintColor);
        drawPaint.setAlpha(125);
        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(10);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.MITER);
        drawPaint.setStrokeCap(Paint.Cap.BUTT);
        canvasPaint = new Paint(Paint.DITHER_FLAG);
    }

    @Override
    protected void onSizeChanged(int w, int h, int xw, int xh) {
        super.onSizeChanged(w, h, xw, xh);
        canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        drawCanvas = new Canvas(canvasBitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(canvasBitmap, 0, 0, canvasPaint);
        canvas.drawPath(drawPath, drawPaint);
    }

    public static void onClear(Canvas canvas){
        canvas.drawBitmap(canvasBitmap,0,0,canvasPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();
        Log.d("touch", "X: " + touchX + "Y: " + touchY);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                drawPath.moveTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_MOVE:
                drawPath.lineTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_UP:
                drawCanvas.drawPath(drawPath, drawPaint);
                drawPath.reset();
                break;
            default:
                return false;
        }
        invalidate();
        return true;
    }
}
