import javax.swing.*;
import java.awt.*;

public class Dish {
    Image dish;
    private final int size = 30;
    private final int x;
    private final int y;
    private int pizza;
    private int dessert;
    private int drink;

    Dish(int dish_x, int dish_y) {
        this.x = dish_x;
        this.y = dish_y;
        this.dessert = 20;
        this.drink = 20;
        this.pizza = 20;
        dish =  new ImageIcon("src/food.png").getImage();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void addDessert() {
        dessert +=1;
    }

    public void addPizza() {
        pizza +=1;
    }

    public void addDrink() {
        drink +=1;
    }

    public void setDessert() {
        dessert -= 1;
    }

    public void setPizza() {
        pizza -= 1;
    }

    public void setDrink() {
        drink -= 1;
    }

    public int getDessert() {
        return dessert;
    }

    public int getPizza() {
        return pizza;
    }

    public int getDrink() {
        return drink;
    }

    Image getDish() {
        return dish;
    }
}
