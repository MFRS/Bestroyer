package bestroyer.bestroyer.bestroyer;

import android.graphics.RectF;

class Britter  {
    public int myType;
    public float myDirection;
    public RectF rect;
    public RectF rectMirror;
    public int width = 30;
    public boolean isDestroyed;
    public boolean isEquipped;
    public GameView gv;
    public boolean isShinobuHit;
    public boolean isWarp;
    public int hitPoints =2;

public Britter (GameView gview, boolean shouldMove) {

isEquipped = shouldMove;
    rect = new RectF();
    rectMirror = new RectF();
gv = gview;

}

    public void decideDirection(int direction,boolean isMirror){
        myDirection = direction;
        if (myDirection == 1) {
            if(myType==3 && isMirror){
                this.rectMirror.left = gv.act.msize.x + width;
                rectMirror.right = rectMirror.left + this.width;
                rectMirror.top = (gv.act.msize.y / 2) - (this.width / 2);
                rectMirror.bottom = rectMirror.top + this.width;
            }else {
                rect.right = -200 + this.width / 2;
                rect.left = rect.right - width;

                rect.top = gv.act.msize.y / 2 - (this.width / 2);
                rect.bottom = rect.top + this.width;
            }



        } else if (myDirection == 2) {
            if(myType==3 && isMirror){
                this.rectMirror.left = ((gv.act.msize.x + gv.navBarHeight) / 2) - this.width;
                rectMirror.right = rectMirror.left + this.width;
                rectMirror.top = gv.act.msize.y + 200 + (this.width);
                rectMirror.bottom = rectMirror.top + this.width;

            }else {
                this.rect.left = ((gv.act.msize.x + gv.navBarHeight) / 2) - width;
                rect.right = rect.left + this.width;
                rect.top = -200 - (this.width / 2);
                rect.bottom = rect.top + this.width;
            }
        } else if (myDirection == 3) {

            if(myType==3 && isMirror){
                rectMirror.right = -200 + this.width / 2;
                rectMirror.left = rectMirror.right - width;

                rectMirror.top = gv.act.msize.y / 2 - (this.width / 2);
                rectMirror.bottom = rectMirror.top + this.width;
            }
            else {
                this.rect.left = gv.act.msize.x + width;
                rect.right = rect.left + this.width;
                rect.top = (gv.act.msize.y / 2) - (this.width / 2);
                rect.bottom = rect.top + this.width;
            }
        } else if (myDirection == 4) {
            if(myType==3 && isMirror){
                this.rectMirror.left = ((gv.act.msize.x + gv.navBarHeight) / 2) - width;
                rectMirror.right = rectMirror.left + this.width;
                rectMirror.top = -200 - (this.width / 2);
                rectMirror.bottom = rectMirror.top + this.width;
            }else {
                this.rect.left = ((gv.act.msize.x + gv.navBarHeight) / 2) - this.width;
                rect.right = rect.left + this.width;
                rect.top = gv.act.msize.y + 200 + (this.width);
                rect.bottom = rect.top + this.width;
            }
        }
    }

public RectF getRekt(){
    return rect;
}

public RectF getMirrorRekt() {
    return rectMirror;
}




}
