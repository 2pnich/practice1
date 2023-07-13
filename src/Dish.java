import javax.swing.*;
import java.awt.*;

public class Dish {
    Image dish;
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

    public void setDessert(boolean f) {
        if (f)
            dessert +=1;
        if (!f)
            dessert -=1;
    }

    public void setPizza(boolean f) {
        if (f)
            dessert +=1;
        if (!f)
            dessert -=1;
    }

    public void setDrink(boolean f) {
        if (f)
            dessert +=1;
        if (!f)
            dessert -=1;
    }

    public void resetDish() {
        dessert = 20;
        pizza = 20;
        drink = 20;
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
