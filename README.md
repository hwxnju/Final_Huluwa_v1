# Final_Huluwa_v1

黄文轩

161190037

hwxnju@gmail.com

## 版本更新

@v1.0

完成了各生物体和其当前血量的GUI显示，实现了基本游戏功能。

@v1.1

修复了游戏结束不停止的bug，添加了一些注释

## 游戏规则

初始游戏地图上有7个葫芦娃，爷爷一名，爷爷是中立生物，只会移动，不会攻击和被攻击。右方有怪物10个，其中蛇精一个，蝎子精一个，鳄鱼统领两名，蛤蟆两只，蜈蚣两条，蝙蝠两只。

开始游戏后，各生物体能够每回合结束时自动随机移动，当葫芦娃和怪物相遇时，会互相殴打（葫芦娃只能观察到其上方、下方和右方的怪物，怪物只能观察到上方、下方和左方的葫芦娃）。葫芦娃和怪物都初始具有100滴血，其中葫芦娃每回合殴打怪物导致怪物掉25滴血，怪物殴打葫芦娃每回合葫芦娃掉25滴血，但是怪物有50%的几率收到反噬，自损25滴血。双方一直战斗下去直到一方全部被打死。

## 游戏展示

	![](https://raw.githubusercontent.com/hwxnju/Final_Huluwa_v1/master/src/main/resources/v1.0.gif)

## 项目结构

1. Main.java

   - Main.java中是项目的主函数，通过创建一个Controller对象对整个游戏进行管理。然后设置Controller对象的primaryStage为主stage，设置primaryStage的名字、大小，调用Controller的游戏初始化和开始方法进行游戏。

2. Controller.java

   - Controller类用来管理整个游戏的功能和各组成部分。
   - 其主要方法包括初始化游戏各生物体、开始游戏、创建相关视图等。

3. Creature.java

   - Creature类是一个抽象类，用于代表一个生物体，其内部有游戏中生物体的一些基本属性，包括

     ```java
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
     ```

   此外，Creature类包含一个静态成员Map，用于保存生物体在地图上位置的数据。

   - Creature类中包含了一些对外基本方法。

     ```java
     public String getName()；
     public boolean getIsAlive()；
     public boolean getIsEvil()；
     public boolean getIsIndifferent()；
     public int getPosX()；
     public int getPosY()；
     public void getHurt()；
     public ImageView getCreatureView()；
     public ImageView getBloodView()；
     public void setPos(int x,int y)；
     
     Creature(){
         type = null;
         isAlive = true;
         isEvil = false;
         isIndifferent = false;
         isFighting = false;
         posX = 0;
         posY = 0;
         blood = 100;
     }
     ```

   - 此外，Creature类中重写了run()方法，用于各生物体多线程行为的进行。

   - 为了线程安全性，部分run方法调用的线程方法加上了synchronized关键字用于线程同步。

     ```java
     public synchronized void attack(int x,int y) ；/**攻击*/
     protected static synchronized void testEnemy(Creature c)；/**观察周围是不是有敌人*/
     public static synchronized void randomMove(final Creature c)；/**随机移动*/
     ```

4. Hulu.java

   - Hulu类是Creature类的子类，添加了Color和ranking成员，用于判断颜色和排行。

5. Grandfa.java

   - Grandfa类是Creature类的子类。

6. MonSnake.java

   - MonSnake(蛇精)类是Creature类的子类。

7. MonScorpion.java

   - MonScorpion(蝎子精)类是Creature类的子类。

8. MonRetinue.java

   - MonRetinue(小喽啰)类是Creature类的子类。

9. Map.java

   - Map类用于保存地图上的生物体，实现了一些基本方法

     ```java
     boolean isOccupied(int x,int y)；/**位置是否被占用*/
     void set(int x,int y, Creature c)；/**设置地图*/
     boolean isEvil(int x,int y)throws Exception；/**是不是怪物*/
     boolean isIndifferent(int x,int y)throws Exception；/**是不是中立生物*/
     void attack(int x,int y)；/**攻击对应位置生物*/
     ```



## 设计思想

1. 抽象

   - 程序中的一些类的基类由于不能实际上存在，可以定义成抽象类。例如Creature类就是一个抽象类，因为一个单纯意义上的生物是不存在的，只有被指定成一个类型的生物，它才能被“创造”出来。

2. 封装

   - 项目中的很多成员对象被声明为private和protected，目的是为了保证其不能任意被外部访问，保证其封装特性。

3. 继承

   - Hulu类、Grandfa类、怪物类都是继承于Creature类，因为它们都是生物，具有生物的共同特性和方法。

4. 异常处理

   - 由于程序中有一些方法的调用可能会不满足方法输入的要求。例如Map类中的一个判断某位置是不是妖怪的方法，可能地图上的这个位置根本就没有生物，这时我们就可以使用我们自己定义的MapNullException异常来抛出一个异常。

     ```java
     boolean isEvil(int x,int y)throws Exception{
         if(map[x][y]!=null){
             return map[x][y].getIsEvil();
         }
         else throw new MapNullException();
     }
     ```

5. 集合类型

   - 项目中使用了ArrayList作为使用的集合类型，完成需要保存多个同类型对象的容器功能。

6. 多线程

   - 程序中每个生物都拥有一个线程，分别进行各自的运动、观察周围敌人、攻击的功能。相应的，这些方法都需要进行同步处理，以防止出现线程不同步现象。