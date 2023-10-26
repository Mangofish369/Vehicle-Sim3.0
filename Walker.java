import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
public class Walker extends Pedestrian
{
    /*
     * This is the Walker class. They maybe be very slow, but they can ride buses.
     */
    public Walker (int direction){
        super(direction);
        // choose a random speed
        maxSpeed = Math.random() * 2 + 1;
        speed = maxSpeed;
    }
    public void act()
    {
        super.act();
    }
}
