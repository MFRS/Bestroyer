package bestroyer.bestroyer.bestroyer;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;

public class MainActivity extends Activity {


    GameView gameView;
    Display mdisp;
    Point msize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


//initialize gameView and set it as the view content


        gameView = new GameView(this);


        setContentView(gameView);
        gameView.hideSystemUI();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        gameView.act = this;
        msize = new Point();
        mdisp = getWindowManager().getDefaultDisplay();
        mdisp.getSize(msize);
        gameView.bobXPosition = (msize.x + gameView.navBarWidth) /2;
        gameView.bobYPos = msize.y /2;



    }



    //When the player starts  the gmae
    @Override
    protected void onResume(){
        super.onResume();

        gameView.resume();

        // gets the main char at half of screen


    }

//Not being used atm
@Override
    protected void onPause(){
        super.onPause();

        gameView.pause();
}

}
