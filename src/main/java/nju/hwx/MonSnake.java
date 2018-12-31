package nju.hwx;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

class MonSnake extends Creature{
    MonSnake(){
        super();
        this.type = CreatureType.MonSnake;
        this.name = this.type.name();
        this.isEvil = true;

        /**初始化图片*/
        this.creatureView = new ImageView();
        Image image = new Image("MonSnake.jpg");
        this.creatureView.setImage(image);
        this.bloodView = new ImageView();
        this.bloodView.setImage(imgBlood100);
    }
}