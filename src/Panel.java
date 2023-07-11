import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;

import static java.lang.Math.abs;

public class Panel extends JPanel implements ActionListener, MouseListener, MouseMotionListener {
    final int FORM_WIDTH = 1100;
    final int FORM_HEIGHT = 900;
    final int UNIT_SIZE = 30;
    int MouseX;
    int MouseY;
    int brushState = 0;
    int wenthome;
    boolean found = false;
    ArrayList<Table> tableList = new ArrayList<Table>();
    ArrayList<Visitor> visitorList = new ArrayList<Visitor>();
    Cheff cheff;
    Dish dish;
    Timer timer;
    JButton eraser;
    JButton brush;
    JButton addVisitor;
    JButton clear;
    JLabel drinkText;
    JLabel dessertText;
    JLabel pizzaText;
    JLabel brushText;
    JLabel tableCount;
    JLabel visitorsCount;

    Panel() {
        Border border = BorderFactory.createLineBorder(Color.black,2);
        this.setPreferredSize(new Dimension(FORM_WIDTH, FORM_HEIGHT));
        dish = new Dish(500, 90);
        cheff = new Cheff();
        timer = new Timer(10, this);
        pizzaText = new JLabel("Пицца:   " + dish.getPizza() + "");
        drinkText = new JLabel("Напитки:" + dish.getDrink());
        dessertText = new JLabel("Десерт: " + dish.getPizza());
        timer.start();

        addVisitor = new JButton();                 //кнопка добавить посетителя
        addVisitor.setBounds(935, 100, 125, 25);
        addVisitor.setBackground(Color.white);
        addVisitor.setFont(new Font("Verdena", Font.BOLD, 15));
        addVisitor.setText("Добавить");
        addVisitor.addActionListener(this);
        addVisitor.setBorder(border);
        this.add(addVisitor);

        eraser = new JButton();
        eraser.setBounds(935, 250, 50, 50);
        ImageIcon eraserPic = new ImageIcon("src/eraser.png");
        eraser.setIcon(eraserPic);
        eraser.addActionListener(this);
        eraser.setBorder(border);
        this.add(eraser);

        brush = new JButton();
        brush.setBounds(1000, 250, 50, 50);
        ImageIcon brushPic = new ImageIcon("src/brush.png");
        brush.setIcon(brushPic);
        brush.addActionListener(this);
        brush.setBorder(border);
        this.add(brush);

        clear = new JButton();                  //кнопка очистить поле
        clear.setBounds(935, 200, 125, 25);
        clear.setBackground(Color.white);
        clear.setText("Очистить");
        clear.setFont(new Font("Verdena", Font.BOLD, 15));
        clear.addActionListener(this);
        clear.setBorder(border);
        this.add(clear);

        tableCount = new JLabel();                  //количество столов
        tableCount.setFont(new Font("Verdena", Font.PLAIN, 20));
        tableCount.setBounds(950, 350, 200, 25);
        this.add(tableCount);

        visitorsCount = new JLabel();                  //количество людей
        visitorsCount.setFont(new Font("Verdena", Font.PLAIN, 20));
        visitorsCount.setBounds(950, 375, 200, 25);
        this.add(visitorsCount);

        pizzaText.setFont(new Font("Verdena", Font.PLAIN, 20));
        pizzaText.setBounds(950, 400, 200, 25);
        this.add(pizzaText);

        drinkText.setFont(new Font("Verdena", Font.PLAIN, 20));
        drinkText.setBounds(950, 425, 200, 25);
        this.add(drinkText);

        dessertText.setFont(new Font("Verdena", Font.PLAIN, 20));
        dessertText.setBounds(950, 450, 200, 25);
        this.add(dessertText);

        brushText = new JLabel();
        brushText.setFont(new Font("Verdena", Font.PLAIN, 20));
        brushText.setBounds(920, 310, 300, 25);
        this.add(brushText);

        JLabel exit = new JLabel("Вход/Выход");     //текст выход
        exit.setHorizontalAlignment(JLabel.CENTER);
        exit.setVerticalAlignment(JLabel.TOP);
        exit.setFont(new Font("Verdena", Font.PLAIN, 30));
        exit.setOpaque(true);
        exit.setBackground(new Color(114, 108, 255));
        exit.setBounds(400, 800, 200, 100);
        this.add(exit);

        JLabel menu = new JLabel("Настройки");     //текст настрйки
        menu.setHorizontalAlignment(JLabel.CENTER);
        menu.setVerticalAlignment(JLabel.TOP);
        menu.setFont(new Font("Verdena", Font.PLAIN, 30));
        menu.setOpaque(true);
        menu.setBackground(new Color(255, 226, 153));
        menu.setBounds(900, 0, 200, 900);
        menu.setBorder(border);
        this.add(menu);

        this.setLayout(null);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);

    }

    public void addGroup() {

    }

    public void visitorLogic() throws InterruptedException {
        int index = 0, full = 0;
        Iterator<Visitor> visitorIterator = visitorList.iterator();
        while (visitorIterator.hasNext()){
            Visitor visitor = visitorIterator.next();
            if (visitor.getHome())
                wenthome += 1;
            if (!found)
                index = visitor.checkClosestTable(tableList);   //найти ближайший стол
            if (index != 0)
                found = true;
            found = !tableList.get(index).checkFull();
            visitor.moveToDishes(dish);                     //идти к блюдам
            if (visitor.getGotToWait() ) {      //ждет
                Thread.sleep(500);
                visitor.setGotToWait();;
            }
            else  {
                //visitor.checkTableCollisions(tableList);        //проверка столкновений со столами
                //visitor.escapeTrap();                           //если путь прегражден
                if (visitor.getGotDish()) {
                    if (!tableList.get(index).checkEmpty())
                        visitor.moveToTop(tableList.get(index));
                    else {
                        if (!tableList.get(index).getSatTop()) {
                            visitor.moveToTop(tableList.get(index));
                        }
                        if (!tableList.get(index).getSatBot() && tableList.get(index).getSatTop()) {
                            visitor.moveToBot(tableList.get(index));
                        }
                        if (!tableList.get(index).getsatLeft() && tableList.get(index).getSatBot()) {
                            visitor.moveToLeft(tableList.get(index));
                        }
                        if (!tableList.get(index).getsatRight() && tableList.get(index).getsatLeft() && tableList.get(index).getSatTop()) {
                            visitor.moveToRight(tableList.get(index));
                        }
                    }
                }
                full = visitor.checkClosestFullTable(tableList);
                if (visitor.getSat())
                    visitor.goHome(tableList.get(full));
                if (tableList.get(index).checkFull())
                    System.out.println("ХУЕТА");
            }
            if (visitor.getHome())
                visitorIterator.remove();
        }
    }

    public void paint(Graphics g) {
        super.paint(g);
        for (int i = 0; i < FORM_HEIGHT / UNIT_SIZE; i++) {
            g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, FORM_HEIGHT);
            g.drawLine(0, i * UNIT_SIZE, FORM_WIDTH - 200, i * UNIT_SIZE);
        }

        g.setColor(Color.blue);
        for (Visitor visitor: visitorList)
            g.fillOval(visitor.getX(), visitor.getY(), UNIT_SIZE,UNIT_SIZE);

        g.setColor(Color.black);
        for (Table table: tableList)
            g.fillRect(table.getX(), table.getY(), UNIT_SIZE, UNIT_SIZE);

        g.setColor(Color.black);
        Image table =  new ImageIcon("src/table.png").getImage();
        g.drawImage(table, 0, dish.getY(), null);
        g.drawImage(dish.getDish(), dish.getX(), dish.getY(), null);
        g.drawImage(cheff.getImg(), cheff.getX(), cheff.getY(), null);

    }

    public void clearMap() {
        tableList.clear();
        visitorList.clear();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addVisitor)
            visitorList.add(new Visitor(480, 840));

        if (e.getSource() == clear)
            clearMap();

        if (e.getSource() == eraser && brushState == 0)
            brushState = 1;
        if (e.getSource() == brush)
            brushState = 0;

        //if ((int)(Math.random() * 1000) % 499 == 0)
            //visitorList.add(new Visitor(480, 840));
        if ((int)(Math.random() * 1000) % 499 == 0)
            addGroup();
            //visitorList.add(new Visitor(450, 450));


        if (!tableList.isEmpty()) {
            if (dish.getDessert() < 10 || dish.getPizza() < 10 || dish.getDrink() < 10)
                cheff.goToDish(dish);
            else cheff.goToKictchen();
            if (!visitorList.isEmpty()) {
                for (Table table : tableList) {
                    //table.checkForSeats(visitorList);
                    table.tableNearby(tableList);
                }
                try {
                    visitorLogic();
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }

        }
        visitorsCount.setText("Людей:" + (visitorList.size() - wenthome));
        tableCount.setText("Столов:" + tableList.size());
        drinkText.setText("Напитки:" + dish.getDrink());
        pizzaText.setText("Пицца:   " + dish.getPizza());
        dessertText.setText("Десерт: " + dish.getDessert());
        if (brushState == 0)
            brushText.setText("Выбрано: Кисть");
        if (brushState == 1)
            brushText.setText("Выбрано: Ластик");
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        MouseX = e.getX();
        MouseY = e.getY();
        while (MouseX % (UNIT_SIZE/2) != 0)
            MouseX -= 1;
        while (MouseY % (UNIT_SIZE/2) != 0 )
            MouseY -= 1;
        if (MouseX < 880 && brushState == 0)
            tableList.add(new Table(MouseX, MouseY));
        if (brushState == 1)
            tableList.removeIf(table -> abs(table.getX() - MouseX) < 30 && abs(table.getY() - MouseY) < 30 &&  abs(table.getX() - MouseX) > 0);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        MouseX = e.getX();
        MouseY = e.getY();
        while (MouseX % 15 != 0)
            MouseX -= 1;
        while (MouseY % 15 != 0)
            MouseY -= 1;
        if (brushState == 1) {
            tableList.removeIf(table -> abs(table.getX() - MouseX) < 30 && abs(table.getY() - MouseY) < 30 &&  abs(table.getX() - MouseX) > 0);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
