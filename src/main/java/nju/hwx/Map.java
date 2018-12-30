package nju.hwx;

class Map{/**地图类，记录每个位置的生物*/
    private Creature map[][] = new Creature[16][9];
    Map(){}
    boolean isOccupied(int x,int y){
        return map[x][y]!=null;
    }

    void set(int x,int y, Creature c) {
        map[x][y] = c;
    }

    boolean isEvil(int x,int y)throws Exception{
        if(map[x][y]!=null){
            return map[x][y].getIsEvil();
        }
        else throw new MapNullException();
    }

    boolean isIndifferent(int x,int y)throws Exception{
        if(map[x][y]!=null){
            return map[x][y].getIsIndifferent();
        }
        else throw new MapNullException();
    }

    void attack(int x,int y){
        if(isOccupied(x,y))
            map[x][y].getHurt();
    }
}