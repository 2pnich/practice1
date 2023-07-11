import java.util.ArrayList;

import static java.lang.Math.abs;
import static java.lang.Math.sqrt;

public class Visitor {
    private final int size = 30;
    private int x;
    private int y;
    private final int speed = 5;
    boolean canMoveLeft = true;
    boolean canMoveRight = true;
    boolean canMoveTop = true;
    boolean canMoveBot = true;
    boolean home = false;
    boolean sat = false;
    boolean satTop = false;
    boolean satBot = false;
    boolean satLeft = false;
    boolean satRight = false;
    boolean gotDish = false;
    boolean gotToWait = false;
    boolean gotToGoHome = false;


    int preference;             //предпочтения в еде

    Visitor(int x, int y) {
        this.x = x;
        this.y = y;
        preference = (int) (Math.random() * 2);
    }

    public void checkTableCollisions(ArrayList<Table> tableList) {
        for (Table table : tableList) {
//            canMoveLeft = table.getX() - x >= size || table.getX() - x <= 0;
            //canMoveRight = abs(table.getX() - x) > size || table.getX() - x >= 0 || abs(table.getY() - ) > 15;
            //canMoveTop = abs(table.getY() - y) > size || table.getY() - y >= 0 || abs(table.getX() - x) > 15;
            //canMoveTop = abs(table.getY() - y) >= size || table.getY() - y > 0 && x == table.getX();

//            canMoveTop = table.getY() - y >= size || table.getX() - x > 0;
//            canMoveBot = table.getY() - y >= size || table.getX() - x <= 0;
        }
    }

    public void escapeTrap() {
        if (!sat) {
            if (!canMoveTop && canMoveRight)
                x += speed;
            if (!canMoveTop && !canMoveRight && canMoveLeft)
                x -= speed;
        }
    }

    public void goHome(Table table) {
        if (sat && (int)(Math.random() * 10000) % 4000 == 0) {
            gotToGoHome = true;
        }
        if (gotToGoHome) {
            if (satTop && y != table.getY() - size && x != table.getX()) {
                table.setSatTop(false);
                satTop = false;
                System.out.println("ушел с топа");
            }
            if (satBot && y != table.getY() + size && x != table.getX()) {
                satBot = false;
                table.setSatBot(false);
                System.out.println("ушел с бота");

            }
            if (satLeft && y != table.getY() && x != table.getX() - size) {
                table.setSatLeft(false);
                satLeft = false;
                System.out.println("ушел слева");
            }
            if (satRight && y != table.getY() && x != table.getX() + size) {
                table.setSatRight(false);
                satRight = false;
                System.out.println("ушел справа");
            }
        }
        if (gotToGoHome) {
            if (x < 450 && canMoveRight)
                x += speed;
            if (x > 450 && canMoveLeft)
                x -= speed;
            if (y < 900 && canMoveBot)
                y += speed;
            if (y > 900 && canMoveTop)
                y -= speed;
        }
        if (y == 900 && x == 450)
            home = true;
    }

    public int findEmptySeat(Table table) {
        if (!(satTop || satBot || satRight || satLeft)) {
            return 1;
        }
        if (!satTop)
            return 1;
        if (!satBot)
            return 2;
        if (!satLeft)
            return 3;
        if (!satRight)
            return 4;
        return 0;
    }

    public int checkClosestFullTable(ArrayList<Table> tableList) {
        int min = 10000, distance, index = 0;
        for (int i = 0; i < tableList.size(); i++) {
            distance = (int) sqrt(((tableList.get(i).getX() - x) * (tableList.get(i).getX() - x)) + ((tableList.get(i).getY() - y) * (tableList.get(i).getY() - y)));
            if (distance < min) {
                min = distance;
                index = i;
            }
        }
        return index;
    }

    public int checkClosestTable(ArrayList<Table> tableList) {
        int min = 10000, distance, index = 0;
        for (int i = 0; i < tableList.size(); i++) {
            distance = (int) sqrt(((tableList.get(i).getX() - x) * (tableList.get(i).getX() - x)) + ((tableList.get(i).getY() - y) * (tableList.get(i).getY() - y)));
            if (distance < min && !tableList.get(i).checkFull()) {
                min = distance;
                index = i;
            }
        }
        return index;
    }

    public void moveToDishes(Dish dish) {
        if (!gotDish) {
            if (x < dish.getX() && canMoveRight)
                x += speed;
            if (x > dish.getX() && canMoveLeft)
                x -= speed;
            if (y < dish.getY() + size && canMoveBot)
                y += speed;
            if (y > dish.getY() + size && canMoveTop)
                y -= speed;
        }

        if (x == dish.getX() && y == dish.getY() + size && !gotDish) {
            dish.setDrink();
            if (preference == 0)
                dish.setPizza();
            else {
                dish.setDessert();
            }
            gotDish = true;
            gotToWait = true;
        }
    }

    public void moveToTop(Table table) {
        if (!sat) {       //верхнее место
            if (x < table.getX() && canMoveRight)
                x += speed;
            if (x > table.getX() && canMoveLeft)
                x -= speed;
            if (y < table.getY() - size && canMoveBot)
                y += speed;
            if (y > table.getY() - size && canMoveTop)
                y -= speed;
            if (y == table.getY() - size && x == table.getX()){
                table.setSatTop(true);
                satTop = true;
                sat = true;
                System.out.println("сел сверху");
            }
        }
    }

    public void moveToBot( Table table) {
        if (!sat) {
            if (x < table.getX() && canMoveRight)
                x += speed;
            if (x > table.getX() && canMoveLeft)
                x -= speed;
            if (y < table.getY() + size && canMoveBot)
                y += speed;
            if (y > table.getY() + size && canMoveTop)
                y -= speed;
            if (y == table.getY() + size && x == table.getX()) {
                table.setSatBot(true);
                satBot = true;
                sat = true;
            }
        }
    }

    public void moveToLeft( Table table) {
        if (!sat) {
            if (x < table.getX() - size && canMoveRight)
                x += speed;
            if (x > table.getX() - size && canMoveLeft)
                x -= speed;
            if (y < table.getY() && canMoveBot)
                y += speed;
            if (y > table.getY() && canMoveTop)
                y -= speed;
            if (y == table.getY() && x == table.getX() - size) {
                System.out.println("СЕЛ ВЛЕВОБЛЯТЬ");
                table.setSatLeft(true);
                satLeft = true;
                sat = true;
            }
        }
    }

    public void moveToRight( Table table) {
        if (!sat) {
            if (x < table.getX() + size && canMoveRight)
                x += speed;
            if (x > table.getX() + size && canMoveLeft)
                x -= speed;
            if (y < table.getY() && canMoveBot)
                y += speed;
            if (y > table.getY() && canMoveTop)
                y -= speed;
            if (y == table.getY() && x == table.getX() + size) {
                table.setSatRight(true);
                System.out.println("СЕЛ ВПРАВОБЛЯТЬ");
                satRight = true;
                sat = true;
            }
        }
    }

    public boolean getSat() {
        return sat;
    }

    public boolean getToGoHone() {
        return gotToGoHome;
    }

    public boolean getGotDish() {
        return gotDish;
    }

    public boolean getGotToWait() {
        return gotToWait;
    }

    public void setGotToWait() {
        gotToWait = false;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean getHome() {
        return home;
    }
}
