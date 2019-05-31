package bestroyer.bestroyer.bestroyer;

import android.graphics.RectF;

class Bullet {

    public int directionshot;
    public float Xpos;
    public float Ypos;
    public int bulletWidth = 20;
    boolean isFired;
    GameView vw;
    RectF rect;


public Bullet (boolean eisFired,GameView gview){

    vw = gview;
    isFired = eisFired;

    rect = new RectF();

resetRectLocation();
}
public RectF getRekt(){
    return rect;
}

    public void update(){
    for (int i=0; i< vw.box_Britters.size();i++){
   // for (Britter d : vw.box_Britters){
          //Log.e("","" +vw.box_Britters.get(i).myDirection);

        if(RectF.intersects(this.getRekt(), vw.box_Britters.get(i).getRekt())){

            if (vw.introBritters[0] == false) {
                vw.introBritters[0] = true;
                vw.spawner.start();
            }



           // vw.adv_Britters.addFirst(vw.box_Britters.get(i));
if(vw.box_Britters.get(i).myType==1) {
    vw.box_Britters.get(i).isEquipped = false;
    vw.box_Britters.get(i).rect.left = 0;
    vw.box_Britters.get(i).rect.right = 0;
    vw.box_Britters.get(i).rect.top = 0;
    vw.box_Britters.get(i).rect.bottom = 0;


    //REDUCES ENEMY NUM
    vw.numEnemies--;









}else if(vw.box_Britters.get(i).myType==2) {
  /*  if(vw.isShinobuHit ==false){
if(  vw.box_Britters.get(i).isShinobuHit == false) {

    vw.box_Britters.get(i).isShinobuHit = true;
    vw.isShinobuHit = true;
}
}else if(vw.isShinobuHit == true){
        if(vw.box_Britters.get(i).isShinobuHit == true) {

        }else {

            //Turns current shinobu to true so that it gets destroyed with its brother
            vw.box_Britters.get(i).isShinobuHit = true;


            for (int p=0; p<vw.box_Britters.size();p++) {
                Britter tBtr = vw.box_Britters.get(p);
                if (vw.box_Britters.get(p).isEquipped && vw.box_Britters.get(p).isShinobuHit) {
                   //
                    tBtr.isShinobuHit = false;
                    tBtr.isEquipped = false;


                    //Removes 2 enemies
                    vw.numEnemies = vw.numEnemies-2;

                    tBtr.rect.left = 0;
                    tBtr.rect.right = 0;
                    tBtr.rect.top = 0;
                    tBtr.rect.bottom = 0;
                }

                //resets shinobu destroy ritual
                vw.isShinobuHit=false;
                if(vw.introBritters[1]==true) {

                    //This SPAWNS ENEMY WAVE 2
                    vw.introBritters[2]=true;
                    vw.spawner.start();



            }
            }
        }
}
*/
vw.box_Britters.get(i).hitPoints--;


    //It checks if enemy has less than -0 life as it might have a bug
if(vw.box_Britters.get(i).hitPoints==0 || vw.box_Britters.get(i).hitPoints<0) {

    vw.box_Britters.get(i).isEquipped = false;
    vw.box_Britters.get(i).rect.left = 0;
    vw.box_Britters.get(i).rect.right = 0;
    vw.box_Britters.get(i).rect.top = 0;
    vw.box_Britters.get(i).rect.bottom = 0;


    //REDUCES ENEMY NUM
    vw.numEnemies--;


    if(vw.introBritters[1]==true && vw.introBritters[2]==false) {
        if (vw.numEnemies == 0) {
            //This SPAWNS ENEMY WAVE 2
            vw.introBritters[2] = true;
            vw.spawner.start();
        }
    }
}




}else if(vw.box_Britters.get(i).myType==3){
    vw.box_Britters.get(i).isEquipped = false;
    vw.box_Britters.get(i).rect.left = 0;
    vw.box_Britters.get(i).rect.right = 0;
    vw.box_Britters.get(i).rect.top = 0;
    vw.box_Britters.get(i).rect.bottom = 0;
vw.numEnemies--;
    //THIS SPAWNS PHASE 4
    if(vw.introBritters[3]&&vw.introBritters[4]==false){
        vw.spawner.start();

    }

}else if(vw.box_Britters.get(i).myType==4){
    if(vw.box_Britters.get(i).hitPoints>0){
        vw.box_Britters.get(i).hitPoints--;


        if(vw.box_Britters.get(i).hitPoints==0||vw.box_Britters.get(i).hitPoints<0){

        }else {

            vw.RedirectEnemy(vw.box_Britters.get(i));

        }
    }
    if(vw.box_Britters.get(i).hitPoints==0||vw.box_Britters.get(i).hitPoints<0){

        vw.box_Britters.get(i).isWarp=false;
        vw.box_Britters.get(i).isEquipped = false;
        vw.numEnemies--;
        vw.box_Britters.get(i).rect.left = 0;

        vw.box_Britters.get(i).rect.right = 0;
        vw.box_Britters.get(i).rect.top = 0;
        vw.box_Britters.get(i).rect.bottom = 0;


        if(vw.introBritters[1]==true&&vw.introBritters[2]==true&&vw.introBritters[3]==true&&vw.introBritters[4]&&vw.tutorialCompleted==false){
            vw.tutorialCompleted=true;
            vw.spawner.start();


        }

    }

}

            //vw.isSpawning = true;

          //  vw.adv_Britters.removeFirst();

            for (Bullet tb: vw.box_Bullets
                 ) {
                if(this == tb){
                    vw.score +=10;

                    tb.isFired=false;
                }
            }

            //TUTORIAL CHHANGING PHASES
            if (vw.numEnemies == 0) {

                //This SPAWNS TUTORIAL ENEMY 2
                if(vw.introBritters[1]==true && vw.introBritters[2]==false) {
                    vw.SpawnEnemy(2);
                    vw.SpawnEnemy(2);
                }
                //This SPAWNS TUTORIAL ENEMY 3
                if(vw.introBritters[1]==true&&vw.introBritters[2]==true&&vw.introBritters[3]==true&&vw.introBritters[4]==false){
                    vw.SpawnEnemy(3);

                }
                if(vw.introBritters[1]==true&&vw.introBritters[2]==true&&vw.introBritters[3]==true&&vw.introBritters[4]&&vw.tutorialCompleted==false){
                    vw.SpawnEnemy(4);



                }
            }
        }
    }
    }

    public void resetRectLocation(){
        rect.left= ((vw.act.msize.x+vw.navBarHeight)/2)- (this.bulletWidth/2);
        rect.right = rect.left + this.bulletWidth;
        rect.top = vw.act.msize.y/2 - (this.bulletWidth/2);
        rect.bottom = rect.top + this.bulletWidth;
    }
}



