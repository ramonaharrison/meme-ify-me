package lighterletter.c4q.nyc.memefymeapp;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;

/**
 * Created by Ramona Harrison
 * on 5/31/15.
 */
public class TextDrawable extends Drawable {

    private final String text;
    private final Paint paint;
    private final float x;
    private final float y;
    private final float size;

    public TextDrawable(String text, float x, float y, float size) {

        this.text = text;
        this.x = x;
        this.y = y;
        this.size = size;
        this.paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(size);
        paint.setAntiAlias(true);
        paint.setFakeBoldText(true);
        paint.setShadowLayer(6f, 0, 0, Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextAlign(Paint.Align.LEFT);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawText(text, x, y, paint);
    }

    @Override
    public void setAlpha(int alpha) {
        paint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        paint.setColorFilter(cf);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

}