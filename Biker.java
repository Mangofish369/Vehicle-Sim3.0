import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
public class Biker extends Pedestrian
{
    /*
     * This is the Biker Class
     * - The key difference is that bikers are much faster than walkers! Buses also do not pick them up.
     */
    public Biker(int direction){
        super(direction);
        maxSpeed = Math.random() * 2 + 1.5;
        speed = maxSpeed;
        if(direction == -1){
            setImage("images/Back-biker.png");
        }
        GreenfootImage biker = getImage();
        biker.scale(40,60);
    }
    public void act()
    {
        super.act();
    }
}
