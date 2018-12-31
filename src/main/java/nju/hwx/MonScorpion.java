package nju.hwx;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

class MonScorpion extends Creature{
    MonScorpion(){
        super();
        this.type = CreatureType.MonScorpion;
        this.name = this.type.name();
        this.isEvil = true;

        /**初始化图片*/
        this.creatureView = new ImageView();
        Image image = new Image("MonScorpion.jpg");
        this.creatureView.setImage(image);
        this.bloodView = new ImageView();
        this.bloodView.setImage(imgBlood100);
    }
}
