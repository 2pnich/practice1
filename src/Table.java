import java.util.ArrayList;

public class Table {
    private final int size = 30;
    private final int x;
    private final int y;
    private boolean satTop = false;
    private boolean satBot = false;
    private boolean satLeft = false;
    private boolean satRight = false;
    boolean full = false;

    Table(int table_x, int table_y) {
        this.x = table_x;
        this.y = table_y;

    }

    public void tableNearby(ArrayList<Table> tableList) {
        for (Table table : tableList) {
            if (table.getX() == x - size && y == table.getY())       //&& (abs(table.getY() - y) < 5)
                table.setSatRight(true);
            if (table.getX() == x + size && y == table.getY())// && abs(table.getY() - y) < 5
                table.setSatLeft(true);
            if (table.getY() == y + size && x == table.getX()) // && abs(table.getX() - x) < 5
                table.setSatTop(true);
            if (table.getY() == y - size && x == table.getX())
                table.setSatBot(true);
        }
    }

    public void setSatTop(boolean t) {
        satTop = t;
    }

    public void setSatBot(boolean t) {
        satBot = t;
    }

    public void setSatRight(boolean t) {
        satRight = t;
    }

    public void setSatLeft(boolean t) {
        satLeft = t;
    }

    public boolean checkEmpty() {
        boolean empty;
        empty = satTop || satBot || satRight || satLeft;
        return empty;
    }

    public boolean checkFull() {
        full = satTop && satBot && satRight && satLeft;
        return full;
    }

    public boolean getSatTop() {
        return satTop;
    }

    public boolean getSatBot() {
        return satBot;
    }

    public boolean getsatRight() {
        return satRight;
    }

    public boolean getsatLeft() {
        return satLeft;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
