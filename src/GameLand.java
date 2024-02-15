//Game Example
//Lockwood Version 2023-24
// Learning goals:
// make an object class to go with this main class
// the object class should have a printInfo()
//input picture for background
//input picture for object and paint the object on the screen at a given point
//create move method for the object, and use it
// create a wrapping move method and a bouncing move method
//create a second object class
//give objects rectangles
//start interactions/collisions

//*******************************************************************************
//Import Section
//Add Java libraries needed for the game
//import java.awt.Canvas;

//Graphics Libraries

import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;


//*******************************************************************************
// Class Definition Section

public class GameLand implements Runnable {

    //Variable Declaration Section
    //Declare the variables used in the program
    //You can set their initial values here if you want

    //Sets the width and height of the program window
    final int WIDTH = 1000;
    final int HEIGHT = 700;

    //Declare the variables needed for the graphics
    public JFrame frame;
    public Canvas canvas;
    public JPanel panel;

    public BufferStrategy bufferStrategy;

    //Declare the objects used in the program below
    /** STEP 0 declare object **/
    public Hero car;
    public Hero rocket;
    public Hero minion;
    public Hero boat;
    /** STEP 1 declare image for object**/
    public Image carPic;
    public Image rocketPic;
    public Image minionPic;
    public Image boatPic;

    //declare background image
    public Image backgroundPic;

    //declare intersection booleans
    public boolean boatIsIntersectingMinion=false;
    public boolean carIsIntersectingRocket=false;
    public boolean carIsIntersectingMinion=false;

    // Main method definition: PSVM
    // This is the code that runs first and automatically
    public static void main(String[] args) {
       GameLand ex = new GameLand();   //creates a new instance of the game and tells GameLand() method to run
        new Thread(ex).start();       //creates a thread & starts up the code in the run( ) method
    }

    // Constructor Method
    // This has no return type and has the same name as the class
    // This section is the setup portion of the program
    // Initialize your variables and construct your program objects here.
    public GameLand() {
        setUpGraphics(); //this calls the setUpGraphics() method

        //create (construct) the objects needed for the game below
        /** STEP 2 construct object **/
        car = new Hero(150,300,4,-8,60,80);
        car.width = 150;
        car.height = 50;
        rocket = new Hero(300,10,2,-5,50,90);
        rocket.width = 100;
        rocket.height = 100;
        minion = new Hero(250,500, 5,2,30,100);
        minion.width = 100;
        minion.height = 100;
        boat = new Hero(400,600,3,2,50,50);
        boat.width = 150;
        boat.height = 75;

        /** STEP 3 add image to object**/
        carPic= Toolkit.getDefaultToolkit().getImage("car.jpeg");
        car.printInfo();
        rocketPic= Toolkit.getDefaultToolkit().getImage("Rocket.jpeg");
        rocket.printInfo();
        backgroundPic = Toolkit.getDefaultToolkit().getImage("backgroundPic.jpeg");
        minionPic= Toolkit.getDefaultToolkit().getImage("Minion.jpeg");
        minion.printInfo();
        boatPic= Toolkit.getDefaultToolkit().getImage("boat.jpeg");
        boat.printInfo();
        //for each object that has a picture, load in images as well


    }// GameLand()

//*******************************************************************************
//User Method Section
//
// put your code to do things here.

    // main thread
    // this is the code that plays the game after you set things up
    public void run() {
        //for the moment we will loop things forever using a while loop
        while (true) {
            moveThings();//move all the game objects
            collisions();
            otherCollisions();
            moreCollisions();
            render();  // paint the graphics
            pause(20); // sleep for 20 ms
        }

    }

    //paints things on the screen using bufferStrategy
    private void render() {
        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
        g.clearRect(0, 0, WIDTH, HEIGHT);
        //draw our background image below:
        g.drawImage(backgroundPic,0,0,WIDTH,HEIGHT,null);

        //draw the image of your objects below:
/** STEP 4 draw object images **/
        g.drawImage(carPic, car.xpos,car.ypos, car.width, car.height, null);
        g.drawImage(rocketPic, rocket.xpos, rocket.ypos, rocket.width, rocket.height, null);
        g.drawImage(minionPic, minion.xpos, minion.ypos, minion.width, minion.height,null);
        g.drawImage(minionPic, minion.xpos, minion.ypos, minion.width, minion.height,null);
        g.drawImage(boatPic, boat.xpos, boat.ypos, boat.width, boat.height, null);
        //dispose the images each time(this allows for the illusion of movement).
        g.dispose();

        bufferStrategy.show();
    }

    public void moveThings() {
        //call the move() method code from your object class
        car.bouncingMove();
        rocket.bouncingMove();
        minion.wrappingMove();
        boat.bouncingMove();

    }

    public void collisions(){
        if(boat.rec.intersects(minion.rec) && boatIsIntersectingMinion==false){
            boatIsIntersectingMinion=true;
            System.out.println("ouch");
            boat.isAlive=false;
        }
        if(boat.rec.intersects(minion.rec)==false){
            boatIsIntersectingMinion=false;
        }
    }

    public void otherCollisions() {
        if (car.rec.intersects(rocket.rec) && carIsIntersectingRocket == false) {
            carIsIntersectingRocket = true;
            System.out.println("stop it");
            car.width=car.width+30;
            car.height=car.height+10;
            rocket.height=rocket.height+50;
            rocket.width=rocket.width+50;
        }
        if (car.rec.intersects(rocket.rec) == false) {
            carIsIntersectingRocket = false;
        }
    }

    public void moreCollisions() {
        if (car.rec.intersects(minion.rec) && carIsIntersectingMinion == false) {
            carIsIntersectingMinion = true;
            System.out.println("ahhhhh");
            car.dx=car.dx+5;
            car.dy=car.dy-3;
            minion.height=minion.height+20;
            minion.width=minion.width+20;
        }
        if (car.rec.intersects(minion.rec) == false) {
            carIsIntersectingMinion = false;
        }
    }

    //Pauses or sleeps the computer for the amount specified in milliseconds
    public void pause(int time) {
        //sleep
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {

        }
    }

    //Graphics setup method
    private void setUpGraphics() {
        frame = new JFrame("Game Land");   //Create the program window or frame.  Names it.

        panel = (JPanel) frame.getContentPane();  //sets up a JPanel which is what goes in the frame
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));  //sizes the JPanel
        panel.setLayout(null);   //set the layout

        // creates a canvas which is a blank rectangular area of the screen onto which the application can draw
        // and trap input events (Mouse and Keyboard events)
        canvas = new Canvas();
        canvas.setBounds(0, 0, WIDTH, HEIGHT);
        canvas.setIgnoreRepaint(true);

        panel.add(canvas);  // adds the canvas to the panel.

        // frame operations
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //makes the frame close and exit nicely
        frame.pack();  //adjusts the frame and its contents so the sizes are at their default or larger
        frame.setResizable(false);   //makes it so the frame cannot be resized
        frame.setVisible(true);      //IMPORTANT!!!  if the frame is not set to visible it will not appear on the screen!

        // sets up things so the screen displays images nicely.
        canvas.createBufferStrategy(2);
        bufferStrategy = canvas.getBufferStrategy();
        canvas.requestFocus();
        System.out.println("DONE graphic setup");
    }

}