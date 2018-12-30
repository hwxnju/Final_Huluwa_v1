package nju.hwx;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

class Grandfa extends Creature{
    Grandfa(){
        super();
        this.type = CreatureType.Grandfa;
        this.name = this.type.getName();
        this.isIndifferent = true;

        /**初始化图片*/
        this.creatureView = new ImageView();
        Image image = new Image("Grandfa.jpg");
        this.creatureView.setImage(image);
        this.bloodView = new ImageView();
        this.bloodView.setImage(imgBlood100);
    }
}