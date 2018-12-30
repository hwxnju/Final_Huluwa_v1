package nju.hwx;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

class MonRetinue extends Creature{
    MonRetinue(int rank){
        super();
        this.type = CreatureType.valueOf("MonRetinue"+rank);
        this.name = this.type.getName();
        this.isEvil = true;

        /**初始化图片*/
        this.creatureView = new ImageView();
        Image image = new Image("MonRetinue"+rank+".jpg");
        this.creatureView.setImage(image);
        this.bloodView = new ImageView();
        this.bloodView.setImage(imgBlood100);
    }

}