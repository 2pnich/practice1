import javax.swing.*;
import java.awt.*;

public class Cheff  {
    Image cheff;
    private final int size = 30;
    private int x;
    private int y;
    private final int speed = 5;
    private boolean kitchen = false;
    Cheff() {
        this.x = 450;
        this.y = 42;
        cheff = new ImageIcon("src/cheff1.png").getImage();
    }

    public void goToKictchen() {
        if (x < 200)
            x += speed;
        if (x > 200)
            x -= speed;
        kitchen = x == 200;
    }

    public void goToDish(Dish dish) {
        //dish.addDrink();
        if (x < dish.getX())
            x += speed;
        if (x > dish.getX())
            x -= speed;
        if (dish.getX() == x && dish.getY() - y == 48){
            if (dish.getDrink() < 10 && (int)(Math.random() * 1000) % 157 == 0)
                dish.addDrink();
            if (dish.getPizza() < 10 && (int)(Math.random() * 1000) % 157 == 0)
                dish.addPizza();
            if (dish.getDessert() < 10 && (int)(Math.random() * 1000) % 157 == 0)
                dish.addDessert();
        }
    }

    Image getImg() {
        return cheff;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
