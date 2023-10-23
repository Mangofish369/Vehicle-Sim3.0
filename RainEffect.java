import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
/**
 * Will rain and cause downpour
 * 
 * Rain causes cars to slow down (so they can avoid driving accidents)
 */
public class RainEffect extends Effect
{
    private int actsLeft;
    private double speed;
    private boolean turnedAround;
    
    public RainEffect(){
        actsLeft = 300;
        speed = 1;
        turnedAround = false;
    }
    
    public void addedToWorld (World w){
        if(image == null){
            image = drawSand(getWorld().getWidth() * 2, getWorld().getHeight(), 3000);
        }
        setImage(image);
    }
    
    public void act()
    {
        actsLeft--;
        if (actsLeft <=60){
            fade (actsLeft, 60);
        }
        // vary speed
        int randomChange = Greenfoot.getRandomNumber(7) - 3; // -3 to 3
        double change = randomChange / 10.0; // -0.3 to 3

        // if (getX() > 700 && !turnedAround && Greenfoot.getRandomNumber(120) == 0){
        //   speed *= -1;
        //    turnedAround = true;
        // }

        if (getX() > 600 && !turnedAround){
            speed *= -1;
            turnedAround = true;
        }

        if (getX() > 0 && change < 0 || getX() < image.getWidth() && change > 0){
            //  speed += change;
        }

        
        setLocation (getX() + speed, getY());

        ArrayList<Vehicle> vehicles = (ArrayList<Vehicle>)getWorld().getObjects(Vehicle.class);
        for (Vehicle v : vehicles){
            v.setIsRaining(true);
        }

        if (actsLeft == 0){
            for (Vehicle v: vehicles){
                v.setIsRaining(false);
            }
            getWorld().removeObject(this);
        }
        // If acts left hits zero, I'm finished
    }

    /**
     * density should be 1-100. 100 will be almost completely white
     */
    public static GreenfootImage drawSand (int width, int height, int density){

        Color[] swatch = new Color [32];

        int red = 80;
        int green = 100;

        // Build a color pallete out of shades of near-white yellow and near-white blue      
        for (int i = 0; i < swatch.length; i++){ // first half blue tones
            swatch[i] = new Color (100, 110, 200);
            red -= 2;
            green --;
        }

        // The temporary image, my canvas for drawing
        GreenfootImage temp = new GreenfootImage (width, height);

        // Run this loop one time per "density"
        for (int i = 0; i < density; i++){
            for (int j = 0; j < 100; j++){ // draw 100 circles
                int randSize;
                // Choose a random colour from my swatch, and set its tranparency randomly
                int randColor = Greenfoot.getRandomNumber(swatch.length);
                //int randTrans = Greenfoot.getRandomNumber(220) + 35; // around half transparent
                temp.setColor (swatch[randColor]);

                //setTransparency(randTrans);
                // random locations for our dot
                int randX = Greenfoot.getRandomNumber (width);
                int randY = Greenfoot.getRandomNumber (height);

                int tempVal = Greenfoot.getRandomNumber(250);
                if (tempVal >= 1){
                    //randSize = 2;
                    temp.drawRect (randX, randY, 0, 0);
                }else{
                    randSize = Greenfoot.getRandomNumber (5) + 2;
                    temp.fillOval (randX, randY, randSize, randSize);
                }
                // silly way to draw a dot..
            }
        }

        return temp;
    }
}
