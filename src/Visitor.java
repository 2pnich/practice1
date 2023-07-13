import java.util.ArrayList;

import static java.lang.Math.abs;
import static java.lang.Math.sqrt;

public class Visitor {
    private final int size = 30;
    private int x;
    private int y;
    private final int speed = 5;
    private boolean home = false;
    private boolean sat = false;
    private boolean satTop = false;
    private boolean satBot = false;
    private boolean satLeft = false;
    private boolean satRight = false;
    private boolean gotDish = false;
    private boolean gotToWait = false;
    private boolean gotToGoHome = false;
    private boolean angry = false;
    private int preference;             //предпочтения в еде

    Visitor(int x, int y) {
        this.x = x;
        this.y = y;
        preference = (int) (Math.random() * 10);
    }

    public void angryGoHome() {
        gotToGoHome = true;
        if (x < 450)
            x += speed;
        if (x > 450)
            x -= speed;
        if (y < 900)
            y += speed;
        if (y > 900)
            y -= speed;
        if (y == 900 && x == 450)
            home = true;
    }

    public void goHome(Table table) {
        if (sat && (int) (Math.random() * 10000) % 9999 == 0) {
            gotToGoHome = true;
        }
        if (gotToGoHome) {
            if (satTop && y != table.getY() - size && x != table.getX()) {
                table.setSatTop(false);
                satTop = false;
            }
            if (satBot && y != table.getY() + size && x != table.getX()) {
                satBot = false;
                table.setSatBot(false);

            }
            if (satLeft && y != table.getY() && x != table.getX() - size) {
                table.setSatLeft(false);
                satLeft = false;
            }
            if (satRight && y != table.getY() && x != table.getX() + size) {
                table.setSatRight(false);
                satRight = false;
            }
        }
        if (gotToGoHome) {
            if (x < 450)
                x += speed;
            if (x > 450)
                x -= speed;
            if (y < 900)
                y += speed;
            if (y > 900)
                y -= speed;
        }
        if (y == 900 && x == 450)
            home = true;
    }


    public int checkClosestNotFullTable(ArrayList<Table> tableList) {
        int min = 10000, distance = 0, index = 0;
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
            if (x < dish.getX())
                x += speed;
            if (x > dish.getX())
                x -= speed;
            if (y < dish.getY() + size)
                y += speed;
            if (y > dish.getY() + size)
                y -= speed;
        }

        if (x == dish.getX() && y == dish.getY() + size && !gotDish) {
            if (dish.getDrink() > 0)
                dish.setDrink(false);
            else angry = true;
            if (preference < 5 && dish.getPizza() > 0)
                dish.setPizza(false);
            else if ((preference < 8 && preference > 5) && dish.getDessert() > 0) {
                dish.setDessert(false);
            } else angry = true;
            gotDish = true;
            gotToWait = true;
        }
    }

    public void moveToTop(Table table) {
        if (!sat) {       //верхнее место
            if (x < table.getX())
                x += speed;
            if (x > table.getX())
                x -= speed;
            if (y < table.getY() - size)
                y += speed;
            if (y > table.getY() - size)
                y -= speed;
            if (y == table.getY() - size && x == table.getX()) {
                table.setSatTop(true);
                satTop = true;
                sat = true;
            }
        }
    }

    public void moveToBot(Table table) {
        if (!sat) {
            if (x < table.getX())
                x += speed;
            if (x > table.getX())
                x -= speed;
            if (y < table.getY() + size)
                y += speed;
            if (y > table.getY() + size)
                y -= speed;
            if (y == table.getY() + size && x == table.getX()) {
                table.setSatBot(true);
                satBot = true;
                sat = true;
            }
        }
    }

    public void moveToLeft(Table table) {
        if (!sat) {
            if (x < table.getX() - size)
                x += speed;
            if (x > table.getX() - size)
                x -= speed;
            if (y < table.getY())
                y += speed;
            if (y > table.getY())
                y -= speed;
            if (y == table.getY() && x == table.getX() - size) {
                table.setSatLeft(true);
                satLeft = true;
                sat = true;
            }
        }
    }

    public void moveToRight(Table table) {
        if (!sat) {
            if (x < table.getX() + size)
                x += speed;
            if (x > table.getX() + size)
                x -= speed;
            if (y < table.getY())
                y += speed;
            if (y > table.getY())
                y -= speed;
            if (y == table.getY() && x == table.getX() + size) {
                table.setSatRight(true);
                satRight = true;
                sat = true;
            }
        }
    }

    public boolean getSat() {
        return sat;
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

    public boolean getAngry() {
        return angry;
    }
}
