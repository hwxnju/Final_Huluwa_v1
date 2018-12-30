package nju.hwx;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.Math.abs;
import static java.lang.Thread.sleep;

public class Controller {

    public AnchorPane pane;
    public Stage primaryStage;

    private boolean isUnderPlaying;

    private int numOfAliveHulu;
    private int numOfAliveMonster;
    private ArrayList<Creature> Hulus = new ArrayList<Creature>();
    private ArrayList<Creature> Monsters = new ArrayList<Creature>();
    private ArrayList<Creature> Grandfas = new ArrayList<Creature>();

    private ExecutorService execPool;



    Controller(){
        this.isUnderPlaying = false;
        this.numOfAliveHulu = 0;
        this.numOfAliveMonster = 0;
    }

    public AnchorPane initPane(){
        this.pane = new AnchorPane();
        pane.setPrefSize(1024,576);/**(16*9)*64 pixel*/

        /**
         * 设置背景
         */
        BackgroundImage myBI = new BackgroundImage(new Image("background.jpg",1024,576,true,true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        pane.setBackground(new Background(myBI));

        return pane;
    }

    public void setPrimaryStage(Stage primaryStage){
        this.primaryStage = primaryStage;
    }

    public void initGame() throws Exception {

        try {
            /**
             * 设置存活的葫芦娃数量和妖怪数量
             */
            this.isUnderPlaying = false;
            Creature.aliveHuluNum = 7;
            Creature.aliveMonsterNum = 10;

            /**
             * 添加7个葫芦娃、一个爷爷、一个蛇精、一个蝎子精、四种小怪各两个
             */

            this.Hulus.add(new Hulu(1));
            this.Hulus.add(new Hulu(2));
            this.Hulus.add(new Hulu(3));
            this.Hulus.add(new Hulu(4));
            this.Hulus.add(new Hulu(5));
            this.Hulus.add(new Hulu(6));
            this.Hulus.add(new Hulu(7));
            this.Grandfas.add(new Grandfa());
            this.Monsters.add(new MonSnake());
            this.Monsters.add(new MonScorpion());
            this.Monsters.add(new MonRetinue(1));
            this.Monsters.add(new MonRetinue(1));
            this.Monsters.add(new MonRetinue(2));
            this.Monsters.add(new MonRetinue(2));
            this.Monsters.add(new MonRetinue(3));
            this.Monsters.add(new MonRetinue(3));
            this.Monsters.add(new MonRetinue(4));
            this.Monsters.add(new MonRetinue(4));

            setCreatureView(Hulus.get(0), 3, 1);
            setCreatureView(Hulus.get(1), 3, 2);
            setCreatureView(Hulus.get(2), 3, 3);
            setCreatureView(Hulus.get(3), 3, 4);
            setCreatureView(Hulus.get(4), 3, 5);
            setCreatureView(Hulus.get(5), 3, 6);
            setCreatureView(Hulus.get(6), 3, 7);

            setCreatureView(Grandfas.get(0), 1, 7);

            setCreatureView(Monsters.get(0), 14, 4);
            setCreatureView(Monsters.get(1), 15, 0);
            setCreatureView(Monsters.get(2), 15, 1);
            setCreatureView(Monsters.get(3), 15, 2);
            setCreatureView(Monsters.get(4), 15, 3);
            setCreatureView(Monsters.get(5), 15, 4);
            setCreatureView(Monsters.get(6), 15, 5);
            setCreatureView(Monsters.get(7), 15, 6);
            setCreatureView(Monsters.get(8), 15, 7);
            setCreatureView(Monsters.get(9), 15, 8);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("????????????");
        }
    }

    public void startGame()throws Exception{
        this.isUnderPlaying = true;
        /**
         * 初始化线程池
         */
        execPool = Executors.newCachedThreadPool();
        ArrayList<Creature> allCreature = new ArrayList<Creature>();
        allCreature.addAll(Hulus);
        allCreature.addAll(Grandfas);
        allCreature.addAll(Monsters);

        for(int i = 0;i<18;i++) {
            execPool.submit(allCreature.get(i));
        }


        execPool.shutdown();
    }

    void setCreatureView(Creature creature, int posX, int posY) {
        creature.setPos(posX,posY);
        pane.getChildren().add(creature.getCreatureView());
        if(!creature.getIsIndifferent())
            pane.getChildren().add(creature.getBloodView());
    }


}
