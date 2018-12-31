package nju.hwx;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.image.Image;

import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Random;

import static java.lang.Math.abs;

@Deprecated
class BloodException extends Exception{}

class MapNullException extends Exception{}

@MyAnnotation
public abstract class Creature implements Runnable{
    public enum CreatureType{

        Grandfa("爷爷"),
        Hulu1("老大"),Hulu2("老二"),Hulu3("老三"),Hulu4("老四"),
        Hulu5("老五"),Hulu6("老六"),Hulu7("老七"),

        MonSnake("蛇精"),MonScorpion("蝎子精"),
        MonRetinue1("小喽啰1"),MonRetinue2("小喽啰2"),
        MonRetinue3("小喽啰3"),MonRetinue4("小喽啰4");

        String name;

        CreatureType(String name){
            this.name = name;
        }

        @Deprecated
        String getName(){
            return this.name;
        }
    }

    protected static int aliveHuluNum;
    protected static int aliveMonsterNum;
    protected static int round;
    protected int creatureRound;

    protected CreatureType type;/**生物类型*/
    protected String name;/**名字*/
    protected boolean isAlive;/**是否还活着*/
    protected boolean isEvil;/**是不是妖怪*/
    protected boolean isIndifferent;/**是不是爷爷*/
    protected boolean isFighting;/**是否在战斗*/
    protected int posX;/**x坐标*/
    protected int posY;/**y坐标*/

    protected int blood;/**血量*/
    protected ImageView creatureView;/**生物体的图像*/
    protected ImageView bloodView;/**血条*/
    protected static Map map = new Map();

    protected static Image imgBlood100 = new Image("blood100.jpg");
    protected static Image imgBlood75 = new Image("blood75.jpg");
    protected static Image imgBlood50 = new Image("blood50.jpg");
    protected static Image imgBlood25 = new Image("blood25.jpg");
    protected static Image imgBlood0 = new Image("blood0.jpg");


    public String getName(){
        return this.name;
    }

    @Deprecated
    public boolean getIsAlive(){
        return this.isAlive;
    }

    public boolean getIsEvil(){
        return this.isEvil;
    }

    public boolean getIsIndifferent(){
        return this.isIndifferent;
    }

    public int getPosX(){
        return this.posX;
    }

    public int getPosY(){
        return this.posY;
    }

    public void getHurt(){/**当前生物受到25点伤害*/
        if(this.blood != 0) {
            this.blood -= 25;
            if(this.blood == 75)
                this.bloodView.setImage(imgBlood75);
            else if(this.blood == 50)
                this.bloodView.setImage(imgBlood50);
            else if(this.blood == 25)
                this.bloodView.setImage(imgBlood25);
            else if(this.blood == 0)
                this.bloodView.setImage(imgBlood0);
        }
        if(this.blood == 0) {
            this.bloodView.setOpacity(0);
            this.suicide(this);
        }
    }

    public static void suicide(final Creature c){/**当前生物血量为0，执行自杀方法，在地图上去除该生物*/
        if(!c.isEvil && aliveHuluNum > 0)
            aliveHuluNum--;
        if(c.isEvil && aliveMonsterNum > 0)
            aliveMonsterNum--;

        c.isAlive = false;
        c.map.set(c.getPosX(),c.getPosY(),null);
        Platform.runLater(new Runnable(){
            public void run(){
                ImageView cView = c.creatureView;

                Timeline t = new Timeline();
                t.getKeyFrames().addAll(
                        new KeyFrame(Duration.ZERO,new KeyValue(cView.rotateProperty(),0)),
                        new KeyFrame(new Duration(500),new KeyValue(cView.rotateProperty(),180)),
                        new KeyFrame(Duration.ZERO,new KeyValue(cView.opacityProperty(),1)),
                        new KeyFrame(new Duration(500),new KeyValue(cView.opacityProperty(),0))

                );
                t.play();
            }
        });
    }
    public synchronized void attack(int x,int y) {/**对（x，y）位置的生物进行阵营和是否为中立生物进行判断，若判断不是一个阵营并不是中立生物，则进行攻击*/
        try {
            if (map.isOccupied(x, y) && !map.isIndifferent(x, y) && (this.isEvil ^ map.isEvil(x,y))) {
                Random r = new Random(System.currentTimeMillis());
                if(this.isEvil && r.nextBoolean() && this.isAlive)
                    map.attack(this.getPosX(),this.getPosY());
                if(map.isOccupied(x,y))
                    map.attack(x, y);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public ImageView getCreatureView(){
        return this.creatureView;
    }

    public ImageView getBloodView(){
        return this.bloodView;
    }

    protected static synchronized void testEnemy(Creature c){/**检查周围是不是有敌人，判断是否进入战斗状态*/
        try{
        if(!c.isIndifferent){
            c.isFighting = false;
            if(c.isEvil && c.getPosX()>0){
                if(map.isOccupied(c.getPosX()-1,c.getPosY()) && !map.isEvil(c.getPosX()-1,c.getPosY()) && !map.isIndifferent(c.getPosX()-1,c.getPosY())){
                    c.isFighting = true;
                }
            }
            else if(!c.isEvil && c.getPosX()<15){
                if(map.isOccupied(c.getPosX()+1,c.getPosY()) && map.isEvil(c.getPosX()+1,c.getPosY()) && !map.isIndifferent(c.getPosX()+1,c.getPosY())){
                    c.isFighting = true;
                }
            }
            if(c.getPosY() > 0 && (map.isOccupied(c.getPosX(),c.getPosY()-1) && (c.getIsEvil() ^ map.isEvil(c.getPosX(),c.getPosY()-1)) && !map.isIndifferent(c.getPosX(),c.getPosY()-1))){
                c.isFighting = true;
            }
            if(c.getPosY() < 8 && (map.isOccupied(c.getPosX(),c.getPosY()+1) && (c.getIsEvil() ^ map.isEvil(c.getPosX(),c.getPosY()+1)) && !map.isIndifferent(c.getPosX(),c.getPosY()+1))){
                c.isFighting = true;
            }
        }
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    Creature(){
        type = null;
        creatureRound = 0;
        isAlive = true;
        isEvil = false;
        isIndifferent = false;
        isFighting = false;
        posX = 0;
        posY = 0;
        blood = 100;
    }


    public void setPos(int x,int y){/**position in map*/
        map.set(posX,posY,null);
        map.set(x,y,this);
        setCreaturePos(x,y);
        creatureView.setX(x*64);
        creatureView.setY(y*64);
        if(!this.isIndifferent) {
            bloodView.setX(x * 64);
            bloodView.setY(y * 64);
        }
    }

    public void setCreaturePos(int x,int y){
        posX = x;
        posY = y;
    }

    public static synchronized void randomMove(final Creature c){

        final int newx,newy;
        int tx,ty;
        Random r = new Random(System.currentTimeMillis());
        tx = r.nextInt(16);
        ty = r.nextInt(9);

        while(map.isOccupied(tx,ty) || abs(tx-c.posX)>5 || abs(ty-c.posY)>5){
            tx = r.nextInt(16);
            ty = r.nextInt(9);
        }

        map.set(c.posX,c.posY,null);
        newx = tx;
        newy = ty;

        map.set(newx,newy,c);

        Platform.runLater(new Runnable(){
            public void run(){
                ImageView cView = c.creatureView;
                ImageView bView = c.bloodView;
                Timeline t = new Timeline();
                t.getKeyFrames().addAll(
                        new KeyFrame(Duration.ZERO,new KeyValue(cView.xProperty(),c.posX*64)),
                        new KeyFrame(new Duration(500),new KeyValue(cView.xProperty(),newx*64)),
                        new KeyFrame(Duration.ZERO,new KeyValue(cView.yProperty(),c.posY*64)),
                        new KeyFrame(new Duration(500),new KeyValue(cView.yProperty(),newy*64)),
                        new KeyFrame(Duration.ZERO,new KeyValue(bView.xProperty(),c.posX*64)),
                        new KeyFrame(new Duration(500),new KeyValue(bView.xProperty(),newx*64)),
                        new KeyFrame(Duration.ZERO,new KeyValue(bView.yProperty(),c.posY*64)),
                        new KeyFrame(new Duration(500),new KeyValue(bView.yProperty(),newy*64))

                );
                t.play();
                if(Controller.isSavingFile)
                    saveStep(round,c.name,newx,newy);
                c.setPos(newx,newy);
            }
        });
    }


    public void run(){
        try {
            while (Controller.isUnderPlaying && this.isAlive && (aliveHuluNum > 0 && aliveMonsterNum > 0)) {
                if(aliveHuluNum == 0 || aliveMonsterNum == 0)
                    Controller.isUnderPlaying = false;
                if(creatureRound > round)
                    round++;
                testEnemy(this);
                if(!isFighting)/**如果不在战斗状态则每个回合随机移动，否则则站在原地战斗*/
                    randomMove(this);
                else {
                    if(this.isEvil && this.getPosX() > 0)
                        attack(this.posX - 1,this.posY);
                    else if(!this.isIndifferent && this.getPosX() < 15)
                        attack(this.posX+1,this.posY);
                    if(this.getPosY() > 0)
                        attack(this.getPosX(),this.getPosY()-1);
                    if(this.getPosY() < 8)
                        attack(this.getPosX(),this.getPosY()+1);
                }

                creatureRound++;
                Thread.sleep(1000);/**每个生物每个回合后睡1s*/
                Thread.yield();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private static void saveStep(int round, String name,int newX, int newY){
        if(Controller.gameFile != null){
            FileWriter fw=null;
            BufferedWriter bw=null;
            try{

                fw=new FileWriter(Controller.gameFile.getAbsoluteFile(),true);
                bw=new BufferedWriter(fw);
                bw.write(round +'-'+ name +'-'+ newX +'-'+newY + '\n');
                bw.close();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

}
