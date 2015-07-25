package com.example.background;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import com.example.weatherapp.R;

public class BirdUp extends Actor {
    private static final int[] imgs = new int[]{R.drawable.finedayup_1, R.drawable.finedayup_2, R.drawable.finedayup_3, R.drawable.finedayup_4, R.drawable.finedayup_5, R.drawable.finedayup_6, R.drawable.finedayup_7, R.drawable.finedayup_8};

    float initPositionX;
    float initPositionY;
    boolean isInit;
    List<Bitmap> frames;
    RectF box;
    RectF targetBox;
    int curFrameIndex;
    long lastTime;
    Paint paint = new Paint();

    protected BirdUp(Context context) {
        super(context);
        frames = new ArrayList<Bitmap>();
        box = new RectF();
        targetBox = new RectF();
        paint.setAntiAlias(true);
    }

    @Override
    public void draw(Canvas canvas, int width, int height) {
        if (!isInit) {
            initPositionX = width * 0.117F;
            initPositionY = height * 0.35F;
            matrix.reset();
            matrix.postTranslate(initPositionX, initPositionY);
            for (int res : imgs) {
                frames.add(BitmapFactory.decodeResource(context.getResources(), res));
            }
            box.set(0, 0, frames.get(0).getWidth(), frames.get(0).getHeight());
            isInit = true;
            lastTime = System.currentTimeMillis();
            return;
        }
        matrix.postTranslate(2, 0);
        matrix.mapRect(targetBox, box);
        if (targetBox.left > width) {
            matrix.postTranslate(-targetBox.right, 0);
        }
        long curTime = System.currentTimeMillis();
        curFrameIndex = (int) ((curTime - lastTime) / 200 % 8);

        Bitmap curBitmap = frames.get(curFrameIndex);
        canvas.save();
        canvas.drawBitmap(curBitmap, matrix, paint);
        canvas.restore();
    }
}
