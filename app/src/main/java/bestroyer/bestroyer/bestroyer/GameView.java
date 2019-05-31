package bestroyer.bestroyer.bestroyer;

//NOTICED GAMEOVER ON BRICK FALLING FROM TOP
//1st brick spawned after middle


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Handler;

import game.mindlap.bestroyer.R;

//public so I can access it. We extend Surfaceview so we can use it in SetContentView.
//Imlementing Runnable allows us to use the thread functionality.
public class GameView extends SurfaceView implements Runnable {

    public int numEnemies;
    //this is our thread
    Thread gameThread = null;
    //SurfaceHolder is used to draw graphics in thread
    SurfaceHolder ourHolder;
    //volatile is what can be used in and out of threads
    volatile boolean playing;
    Canvas canvas;
    Paint paint;
    long fps;
    //Assets
    Bitmap bitmapBob;
    Bitmap bitBritter;

    Bitmap bitLogo;
    Bitmap bitbg;
    Bitmap bitey1;
    Bitmap bitey2;
    Bitmap bitey3;
    Bitmap bitey4;


    //Game Stages
    boolean isStart;

    Britter britter;
    List<Britter> box_Britters;
    LinkedBlockingQueue<Britter> adv_Britters;
    boolean isMoving = false;
    int britterRadius = 20;
    boolean gameOver = false;
    MainActivity act;
    int score;
    Paint bullet_paint;
    boolean isShinobuHit;
    boolean[] introBritters = new boolean[5];
    boolean tutorialCompleted;


    //Touch Connection with graphics
    int touchX;
    int touchY;
    boolean touchedScreen;
    boolean kazan = false;
    RectF rectk;
    RectF rects;
    RectF recttop;
    RectF rectright;
    RectF rectbottom;
    Paint direction_paint;
    float bulletSpeed;
    Paint enemyPaint;
    Bullet bullet;
    int prevRandwarper;
    RectF introbgRect;

    //Recycling system
    //Britter cycle[];
    List<Bullet> box_Bullets;
    float walkSpeedPerSecond = 50;
    float bobXPosition;
    float bobYPos;
    float navBarHeight;
    float navBarWidth;
    Handler handler;
    CountDownTimer spawner;
    CountDownTimer startSpawner;
    boolean isSpawning;
    private long timeThisFrame;

    //this runs when gameView is initialized
    public GameView(Context context) {

        //this asks the surfaceview to set our object
        super(context);
        introbgRect = new RectF();
        ourHolder = getHolder();
        paint = new Paint();
        //act = new MainActivity();
        bullet_paint = new Paint();
        direction_paint = new Paint();
        rectk = new RectF();
        enemyPaint = new Paint();
        isSpawning = false;
        box_Britters = new ArrayList<Britter>();
        // cycle = new Britter[20];
        box_Bullets = new ArrayList<Bullet>();
        //boolean introBritters[] = new boolean[4];
        tutorialCompleted = false;
        introBritters[0] = false;
        introBritters[1] = false;
        introBritters[2] = false;
        introBritters[3] = false;
        introBritters[4] = false;
        adv_Britters = new LinkedBlockingQueue<Britter>();
        bitmapBob = BitmapFactory.decodeResource(this.getResources(), R.drawable.bob);
        bitBritter = BitmapFactory.decodeResource(this.getResources(), R.drawable.britter);
        bitLogo = BitmapFactory.decodeResource(this.getResources(), R.drawable.logo);
        bitbg = BitmapFactory.decodeResource(this.getResources(), R.drawable.bg);
        bitey1 = BitmapFactory.decodeResource(this.getResources(), R.drawable.enemy1);
        bitey2 = BitmapFactory.decodeResource(this.getResources(), R.drawable.enemy2);
        bitey3 = BitmapFactory.decodeResource(this.getResources(), R.drawable.enemy3);
        bitey4 = BitmapFactory.decodeResource(this.getResources(), R.drawable.enemy4);

        bulletSpeed = 1000;

        //Intro RectF





        getNavBarDimensions(context, true);
        getNavBarDimensions(context, false);


    }

    public static int getScreenDimensions(boolean isWidth) {

        if (isWidth) {

            return Resources.getSystem().getDisplayMetrics().widthPixels;
        } else {

            return Resources.getSystem().getDisplayMetrics().heightPixels;
        }

    }

    @Override
    public void run() {
        while (playing) {

            //Gets time in milliseconds in current device
            long startFrameTime = System.currentTimeMillis();

            //update frame
            update();

            draw();

            //Calculate fps in this frame
            timeThisFrame = System.currentTimeMillis() - startFrameTime;
            if (timeThisFrame > 0) {
                //1000
                fps = 1000 / timeThisFrame;

            }
        }


    }

    public void draw() {


        //surface is valid? then draw
        if (ourHolder.getSurface().isValid()) {

            //


//lock canvas before drawing
            canvas = ourHolder.lockCanvas();


            //bg color
            canvas.drawColor(Color.argb(255, 0, 0, 0));

            // paint color
            paint.setColor(Color.argb(255, 0, 255, 0));
            bullet_paint.setColor(Color.argb(100, 100, 0, 0));
            paint.setTextSize(45);
            direction_paint.setColor(Color.argb(0, 0, 200, 0));


            // canvas.drawBitmap(bitmapBob, bobXPosition, bobYPos, paint);


            //STARTGAME
            //df
            if (isStart == false) {
              //  introbgRect.left = 0;
              //  introbgRect.right = act.msize.x;
              //  introbgRect.top = 0;
               // introbgRect.right = act.msize.y;
              //  canvas.drawRect(introbgRect.left,introbgRect.top,introbgRect.right,introbgRect.bottom, paint);
                canvas.drawBitmap(bitLogo, (act.msize.x + navBarHeight)/2 - 450, act.msize.y/2 -150, paint);
                canvas.drawText("TAP TO START", ((act.msize.x + navBarHeight) / 2 - 150), act.msize.y / 2+180, paint);
                score = 0;
            }


            //Game related Graphics

           else if (gameOver == false) {

                canvas.drawBitmap(bitbg, -5, -3, paint);
                canvas.drawBitmap(bitmapBob, bobXPosition+20, bobYPos-40, paint);
                //canvas.drawText("fps:" + fps, 20, 40, paint);
                canvas.drawText("Score:" + score, ((act.msize.x + navBarHeight) / 2 + 300), act.msize.y / 2 - 200, paint);


                for (Britter b : box_Britters
                ) {
                    if (b.isEquipped == true) {

                        if (b.myType == 1) {
                            enemyPaint.setColor(Color.argb(0, 255, 0, 0));
                            canvas.drawBitmap(bitey1, b.rect.centerX() - 20, b.rect.centerY() - 30, paint);
                            canvas.drawRect(b.getRekt(), enemyPaint);
                        } else if (b.myType == 2) {
                            enemyPaint.setColor(Color.argb(0, 0, 0, 255));
                            canvas.drawBitmap(bitey2, b.rect.centerX() - 20, b.rect.centerY() - 30, paint);
                            canvas.drawRect(b.getRekt(), enemyPaint);
                        } else if (b.myType == 3) {
                            enemyPaint.setColor(Color.argb(0, 0, 100, 200));

                            canvas.drawRect(b.getMirrorRekt(), enemyPaint);
                            canvas.drawBitmap(bitey3, b.rectMirror.centerX()-20, b.rectMirror.centerY()-40, paint);
                            canvas.drawRect(b.getRekt(), enemyPaint);
                        } else if (b.myType == 4) {
                            enemyPaint.setColor(Color.argb(0, 255, 100, 20));


                            canvas.drawBitmap(bitey4, b.rect.centerX()-20, b.rect.centerY()-30, paint);
                            canvas.drawRect(b.getRekt(), enemyPaint);
                        }
                    }
                }


                for (Bullet d : box_Bullets
                ) {
                    if (d.isFired) {

                        canvas.drawRect(d.getRekt(), bullet_paint);

                    }

                }


                //Draw Direction Rectangles

                //Bullet Directions Rectangles
                rects = new RectF();

                rects.left = 0;
                rects.top = (act.msize.y / 2) - 130;
                rects.right = 170;
                rects.bottom = rects.top + 270;

                rectbottom = new RectF();

                rectbottom.left = ((act.msize.x + navBarHeight) / 2) - 140;
                rectbottom.right = rectbottom.left + 280;
                rectbottom.top = (act.msize.y) - 160;
                rectbottom.bottom = rectbottom.top + 200;

                rectright = new RectF();

                rectright.left = (act.msize.x + navBarHeight) - 180;
                rectright.right = rectright.left + 220;
                rectright.top = (act.msize.y / 2) - 140;
                rectright.bottom = rectright.top + 280;

                recttop = new RectF();

                recttop.left = (act.msize.x + navBarHeight) / 2 - 140;
                recttop.right = recttop.left + 280;
                recttop.top = 0;
                recttop.bottom = recttop.top + 160;

                canvas.drawRect(rects, direction_paint);
                canvas.drawRect(rectright, direction_paint);
                canvas.drawRect(rectbottom, direction_paint);
                canvas.drawRect(recttop, direction_paint);

                //Spawn Detectable Rectangle
                if (touchedScreen) {
                    rectk.left = touchX - (10 / 2);
                    rectk.top = touchY - (10 / 2);
                    rectk.right = rectk.left + 10;
                    rectk.bottom = rectk.top + 10;
                    canvas.drawRect(rectk, paint);


                    //THIS BREAKS THE GAME CURRENTLY
                    if (rectk.intersect(rects)) {

                        Shoot(1);

                    }

                    if (rectk.intersect(recttop)) {

                        Shoot(2);

                    }

                    if (rectk.intersect(rectright)) {

                        Shoot(3);

                    }

                    if (rectk.intersect(rectbottom)) {


                        Shoot(4);

                    }


                }
            } else {
                //GAMEOVER
                //pause();
                box_Britters.clear();
                box_Bullets.clear();
                canvas.drawText("GAME OVER", (act.msize.x + navBarHeight) / 2-100, act.msize.y / 2-90, paint);
                if(tutorialCompleted){

                }else{
                    introBritters[0]=false;
                    introBritters[1]=false;
                    introBritters[2]=false;
                    introBritters[3]=false;
                    introBritters[4]=false;
                }
                numEnemies=0;
                canvas.drawText("Your Score was:" + score, (act.msize.x + navBarHeight) / 2 -150, act.msize.y / 2 , paint);
                canvas.drawText("TAP TO START AGAIN", ((act.msize.x + navBarHeight) / 2 -150), act.msize.y / 2 + 100, paint);

            }


            //draw everything to screen / unlock drawing surface
            ourHolder.unlockCanvasAndPost(canvas);
        }
    }

    public void SpawnEnemy(int EnemyType) {


        //britter = new Britter(canvas);

        float britterXpos;
        float britterYpos;
        float britterDirection;
//int randPos =4;
        //int randPos = (int) Math.floor(Math.random() * 5);


        int range = (4 - 1) + 1;
        int randPos = (int) (Math.random() * range) + 1;
        int prevRand = 0;
//Stops Shinobu from spawning in the same lane
        if (EnemyType == 2) {

            if (prevRand == randPos) {
                while (prevRand == randPos) {
                    randPos = (int) (Math.random() * range) + 1;
                }

            }
        }
        //int randPos =4;

        if (randPos == 1/*left*/) {
            britterXpos = 200;
            britterYpos = act.msize.y / 2;

            //Log.e("","left" +britterYpos);
        } else if (randPos == 2/*top*/) {
            // Log.e("","top");
            britterXpos = (act.msize.x + (int) navBarHeight) / 2;
            britterYpos = -200;
            prevRand = randPos;
        } else if (randPos == 3 /*right*/) {
            //  Log.e("","right");
            britterXpos = act.msize.x + (int) navBarHeight + 200;
            britterYpos = act.msize.y / 2;
        } else {

            britterXpos = (act.msize.x + (int) navBarHeight) / 2;
            britterYpos = act.msize.y + 100;

        }


        for (int i = 0; i < box_Britters.size(); i++) {
            //Enemy 4 is only changing it's direction
            if (EnemyType == 4) {

            /*if (box_Britters.get(i).isEquipped == true && box_Britters.get(i).isWarp) {
                box_Britters.get(i).decideDirection(randPos, false);

*/

                if (box_Britters.get(i).isEquipped == false) {
                    numEnemies++;
                    box_Britters.get(i).isEquipped = true;
                    box_Britters.get(i).myType = EnemyType;
                    box_Britters.get(i).isWarp = true;
                    box_Britters.get(i).hitPoints = 2;
                    box_Britters.get(i).decideDirection(randPos, false);
                    return;
                }


            }

            if (box_Britters.get(i).isEquipped == false) {
                numEnemies++;
                box_Britters.get(i).isEquipped = true;
                box_Britters.get(i).hitPoints = 2;
                box_Britters.get(i).myType = EnemyType;
                box_Britters.get(i).decideDirection(randPos, false);
                if (EnemyType == 3) {
                    box_Britters.get(i).decideDirection(randPos, true);
                }


                return;
            }

        }


    }

    public void RedirectEnemy(Britter warper) {

        int range = (4 - 1) + 1;
        int randPos = (int) (Math.random() * range) + 1;


        if (prevRandwarper == randPos) {
            while (prevRandwarper == randPos) {
                randPos = (int) (Math.random() * range) + 1;
            }
        }
        prevRandwarper = randPos;
        for (int i = 0; i < box_Britters.size(); i++) {
            if (box_Britters.get(i) == warper) {
                box_Britters.get(i).decideDirection(randPos, false);
            }

        }
    }


    private int getNavBarDimensions(Context gvcontext, boolean isWidth) {
        Resources resources = gvcontext.getResources();

        if (isWidth) {
            int resourceId = resources.getIdentifier("navigation_bar_width", "dimen", "android");
            if (resourceId > 0) {
                // navBarWidth = resources.getDimensionPixelSize(resourceId);
                //Log.e("",""+navBarHeight);
            }

        } else {
            int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
            if (resourceId > 0) {
                //   navBarHeight = resources.getDimensionPixelSize(resourceId);
                navBarHeight = resources.getDimension(resourceId);
                //  Log.e("",""+navBarHeight);
            }

        }
        return 0;
    }

    // This snippet hides the system bars.
    public void hideSystemUI() {
        // Set the IMMERSIVE flag.
        // Set the content to appear under the system bars so that the content
        // doesn't resize when the system bars hide and show.
        this.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);
    }

    // This snippet shows the system bars. It does this by removing all the flags
// except for the ones that make the content appear under the system bars.
    public void showSystemUI() {
        this.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }

    private void update() {

        if (gameOver == false) {
            for (Britter m : box_Britters
            ) {

                if (m.isEquipped) {

                    if (m.myDirection == 1) {
                        m.rect.right = m.rect.left + m.width + (walkSpeedPerSecond / fps);
                        m.rect.left = m.rect.right - m.width;
                        //Log.e("xupd",""+m.rect.left);
                        GameOverF(m.myDirection, m);

                        if (m.myType == 3) {
                            //m.decideDirection(1,true);

                            m.rectMirror.left = m.rectMirror.left - (walkSpeedPerSecond / fps);
                            m.rectMirror.right = m.rectMirror.left + m.width;
                        }

                    } else if (m.myDirection == 2) {
                        m.rect.bottom = m.rect.bottom + (walkSpeedPerSecond / fps);
                        m.rect.top = m.rect.bottom - m.width;

                        if (m.myType == 3) {
                            m.rectMirror.top = m.rectMirror.top - (walkSpeedPerSecond / fps);
                            m.rectMirror.bottom = m.rectMirror.top + m.width;

                        }

                        //GAMEOVER CONDITION
                        GameOverF(m.myDirection, m);

                    } else if (m.myDirection == 3) {
                        m.rect.left = m.rect.left - (walkSpeedPerSecond / fps);
                        m.rect.right = m.rect.left + m.width;

                        if (m.myType == 3) {
                            m.rectMirror.right = m.rectMirror.left + m.width + (walkSpeedPerSecond / fps);
                            m.rectMirror.left = m.rectMirror.right - m.width;
                        }

                        //GAMEOVER CONDITION
                        GameOverF(m.myDirection, m);

                    } else if (m.myDirection == 4) {
                        m.rect.top = m.rect.top - (walkSpeedPerSecond / fps);
                        m.rect.bottom = m.rect.top + m.width;

                        if (m.myType == 3) {

                            m.rectMirror.bottom = m.rectMirror.bottom + (walkSpeedPerSecond / fps);
                            m.rectMirror.top = m.rectMirror.bottom - m.width;
                        }

                        //GAMEOVER CONDITION
                        GameOverF(m.myDirection, m);
                    }

                }
            }

            for (Bullet m : box_Bullets
            ) {
                // m.update();
                if (m.rect.left > act.msize.x + 30 || m.rect.right < -30 || m.rect.top > act.msize.y + 30 || m.rect.bottom < -30) {

                    m.isFired = false;
                    m.rect.left = 0;
                    m.rect.right = 0;
                    m.rect.top = 0;
                    m.rect.bottom = 0;

                }
                if (m.isFired) {
                    if (m.directionshot == 1) {
                        // m.Xpos = m.Xpos - (walkSpeedPerSecond/fps);
                        m.rect.left = m.rect.left - (m.bulletWidth / 2) - (bulletSpeed / fps);
                        m.rect.right = m.rect.left + m.bulletWidth;
                        m.update();


                    } else if (m.directionshot == 2) {
                        m.rect.top = m.rect.top - (m.bulletWidth / 2) - (bulletSpeed / fps);
                        m.rect.bottom = m.rect.top + m.bulletWidth;
                        m.update();
                    } else if (m.directionshot == 3) {
                        m.rect.right = m.rect.right + (m.bulletWidth / 2) + (bulletSpeed / fps);
                        m.rect.left = m.rect.right - m.bulletWidth;
                        m.update();
                    } else {
                        m.rect.bottom = m.rect.bottom + (m.bulletWidth / 2) + (bulletSpeed / fps);
                        m.rect.top = m.rect.bottom - m.bulletWidth;
                        m.update();
                    }
                }
            }


        }


    }

    public void GameOverF(float direction, Britter cbritter) {
        if (direction == 1) {
            if (cbritter.rect.centerX() > ((act.msize.x + navBarHeight) / 2) + 5) {

                //  Log.e("",""+((act.msize.x + navBarHeight) / 2)+5);

                //  Log.e("",""+act.msize.x);

                gameOver = true;

            }
        } else if (direction == 2) {


            if (cbritter.rect.centerY() > ((act.msize.y / 2) + 20)) {
                gameOver = true;

            }
        } else if (direction == 3) {
            if (cbritter.rect.centerX() < (((act.msize.x + (int) navBarHeight) / 2) - 5)) {
                gameOver = true;

            }
        } else if (direction == 4) {
            if (cbritter.rect.centerY() < (((act.msize.y / 2) + 5))) {
                gameOver = true;

            }
        }
    }

    public void Shoot(int direction) {
        //Shoot Bullets


        touchedScreen = false;
        for (Bullet bl : box_Bullets
        ) {

            if (bl.isFired == false) {


                bl.isFired = true;
                bl.directionshot = direction;
                bl.resetRectLocation();
                return;
            }
        }
    }

    public void pause() {
        playing = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            Log.e("Error:", "joining thread");
        }
    }


    public void resume() {
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();

    }


    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {

            //Player touched the game
            case MotionEvent.ACTION_DOWN:
                touchX = (int) motionEvent.getX();
                touchY = (int) motionEvent.getY();

                //Screen is touched
                touchedScreen = true;
                if (gameOver == true && isStart == true) {
                    isStart = false;
                    score = 0;
                }
                if (isStart == false) {
                    isStart = true;
                    //score = 0;
                    gameOver = false;

                    for (int i = 0; i < 20; i++) {
                        Britter bitOr = new Britter(this, false);

                        box_Britters.add(bitOr);

                        if (i < 6) {
                            Bullet bullet = new Bullet(false, this);
                            box_Bullets.add(bullet);

                        }

                    }
                    spawner = new CountDownTimer(10000, 500) {


                        public void onTick(long millisUntilFinished) {
                            //Log.e("","d");
                            if (isSpawning == false) {
                                if (gameOver == false) {
                                    //if tutorial hasnt been completed
                                    if (tutorialCompleted == false) {

                                        if (introBritters[0] == false) {
                                            SpawnEnemy(1);


                                            spawner.cancel();
                                        }


                                        if (introBritters[2] && introBritters[3] == false) {
                                            int range = (2 - 1) + 1;
                                            int randPos = (int) (Math.random() * range) + 1;


                                            SpawnEnemy(randPos);


                                            //This prepares for enemy 3 1st display
                                            if (score > 300 && introBritters[3] == false) {

                                                introBritters[3] = true;
                                                spawner.cancel();
                                            }

                                        } else if (introBritters[3] && introBritters[4] == false) {
                                            int range = (3 - 1) + 1;
                                            int randPos = (int) (Math.random() * range) + 1;


                                            SpawnEnemy(randPos);


                                            //This prepares for enemy 3 1st display
                                            if (score > 600 && introBritters[4] == false) {

                                                introBritters[4] = true;
                                                spawner.cancel();
                                            }
                                        } else {

                                            if (introBritters[0] && introBritters[1] == false) {
                                                SpawnEnemy(1);
                                            }

                                            if (score > 100 && introBritters[1] == false) {


                                                //This stops this condition of part 1 from triggering

                                                //Bullet collision will check from now on when all lvl 1
                                                //enemies are destroyed
                                                if (introBritters[1] == false) {
                                                    // introBritters[0]= false;
                                                    introBritters[1] = true;

                                                    spawner.cancel();
                                                }


                                            }


                                        }
//If tutorial has been completed
                                    } else if (tutorialCompleted) {
                                        int range = (4 - 1) + 1;
                                        int randPos = (int) (Math.random() * range) + 1;


                                        SpawnEnemy(randPos);
                                    }
                                } else {
                                    spawner.cancel();
                                }
                            }
                            isSpawning = false;

                        }

                        public void onFinish() {
                            spawner.start();

                        }
                    }.start();
                }
                isMoving = true;

                break;

            //Player takes finger off
            case MotionEvent.ACTION_UP:

                touchedScreen = false;

                break;


        }

        return true;

    }


}