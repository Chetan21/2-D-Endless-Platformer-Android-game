package com.example.chetan.gametestappversion3;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.Toast;

import java.util.Random;

/**
 * Created by Chetan on 2/18/2016.
 */
public class StartGameActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new GameView(this));

    }

    public class GameView extends View{
        Paint paint;

        public Canvas gameCanvas;
        int width;
        int height;
        int count, speedFactor = 10;
        Bitmap jumpLine;
        Bitmap line, character, characterDuck, characterJump, characterRun;
        Bitmap duckLine;
        float x, charX, baseX, temp, collisionWidthSum;
        float y, charY, baseY;
        Random random;
        boolean jumpTriggered, duckTriggered;
        boolean reachedMaxHeight;
        boolean jumpedUp;
        RectF charRect = new RectF();
        RectF lineRect = new RectF();
        int bitmapCharWidth =100;
        int bitmapCharheight = 200;
        Rect r;
        Paint t;


        public GameView(Context context){
            super(context);
            paint = new Paint();
            t = new Paint();
            t.setColor(Color.YELLOW);
            paint.setColor(Color.WHITE);
            paint.setStrokeWidth(5f);
            gameCanvas= new Canvas();
            random = new Random();
            characterJump = BitmapFactory.decodeResource(getResources(), R.drawable.char_jump);
            characterJump = scaleBitmap(characterJump, bitmapCharWidth, bitmapCharheight);
            character = BitmapFactory.decodeResource(getResources(), R.drawable.char_run);
            character = scaleBitmap(character, bitmapCharWidth, bitmapCharheight);
            characterRun = BitmapFactory.decodeResource(getResources(), R.drawable.char_run);
            characterRun = scaleBitmap(characterRun, bitmapCharWidth, bitmapCharheight);
            duckLine = BitmapFactory.decodeResource(getResources(), R.drawable.duck_line);

            jumpLine = BitmapFactory.decodeResource(getResources(), R.drawable.jump_line);
            characterDuck = BitmapFactory.decodeResource(getResources(), R.drawable.char_duck);
            characterDuck = scaleBitmap(characterDuck, bitmapCharWidth, bitmapCharheight);
            line = jumpLine;
            width=getScreenWidth();
            height=getScreenHeight();
            x= (float)width+40;
            y=(float)0.5*height+40;
            charX = (float)0.1*width;
            charY= (float)(0.7*height-character.getHeight());
            baseX=charX;
            baseY=charY;
            count=0;
            r = new Rect();
            jumpTriggered=false;
            duckTriggered=false;
            reachedMaxHeight=false;
            jumpedUp=false;
            charRect = new RectF();
            lineRect = new RectF();

        }


        @Override
        public void onDraw(Canvas canvas) {

            gameCanvas = canvas;
            gameCanvas.drawLine(0, (float) 0.7 * height, width, (float) 0.7 * height, paint);
            gameCanvas.drawLine(0, (float) 0.3 * height, width, (float) 0.3 * height, paint);
            if(charRect.intersect(lineRect)) {

                Intent intent = new Intent(StartGameActivity.this, RestartActivity.class);
                startActivity(intent);
                finish();
            }
                if (x > 0) {

                    gameCanvas.drawBitmap(line, x, y, new Paint());
                    lineRect.set(x + 4, y + 10, x + 5, y + line.getHeight());
                    //gameCanvas.drawRect(lineRect, t);
                    count++;
                    if (jumpTriggered) {
                        jump();
                    } else if (duckTriggered) {
                        duck();
                    } else {
                        gameCanvas.drawBitmap(character, baseX, baseY, new Paint());
                        charRect.set(baseX+10, baseY, baseX+character.getWidth(), baseY+40);
                        //gameCanvas.drawRect(charRect, t);
                    }
                    x = x - speedFactor;

                } else {
                    randomize();
                }

                invalidate();

        }
        public int getScreenWidth(){
            DisplayMetrics displaymetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
            int width = displaymetrics.widthPixels;
            return width;
        }
        public int getScreenHeight(){
            DisplayMetrics displaymetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
            int height = displaymetrics.heightPixels;
            return height;
        }
        public Bitmap scaleBitmap(Bitmap b, int reqWidth, int reqHeight){
            Matrix m = new Matrix();
            m.setRectToRect(new RectF(0, 0, b.getWidth(), b.getHeight()), new RectF(0, 0, reqWidth, reqHeight), Matrix.ScaleToFit.END);
            return Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(), m, true);
        }
        public void randomize(){
            int r = random.nextInt(10);
            if(r%2==0){
                y=(float)0.3*height;
                x=width;
                line = duckLine;

            }else{
                y=(float)0.5*height;
                x=width;
                line = jumpLine;

            }
            if(speedFactor<25){
                speedFactor++;
            }

        }
        public boolean onTouchEvent(MotionEvent event){
            if(event.getAction()==MotionEvent.ACTION_DOWN) {
                super.onTouchEvent(event);
                float touchX = event.getX();

                if(touchX>width/2) {
                    jumpTriggered = true;
                    jumpedUp = true;
                }
                if(touchX<width/2){
                    duckTriggered=true;
                    temp=x;
                }
            }
            return true;
        }
        public void jump(){
            getRootView().setClickable(false);
            character = characterJump;
            if(jumpedUp) {
                charY = charY - speedFactor;
                if(charY<(baseY-320)) {
                    jumpedUp = false;
                    reachedMaxHeight=true;
                }
            }
            if(reachedMaxHeight){
                charY = charY + speedFactor;
                if(charY>=baseY){
                    character = characterRun;
                    reachedMaxHeight=false;
                    jumpTriggered=false;
                }
            }
            gameCanvas.drawBitmap(character, charX, charY, new Paint());
            charRect.set(charX + 10, charY, charX + character.getWidth(), charY + 40);
        }
        public void duck(){
            character=characterDuck;
            gameCanvas.drawBitmap(character, baseX, baseY+20, new Paint());
            charRect.set(baseX + 10, baseY+20, baseX+character.getWidth(), baseY+40);
            if(x<=temp-250){
                duckTriggered=false;
                character=characterRun;
            }
        }

    }
}