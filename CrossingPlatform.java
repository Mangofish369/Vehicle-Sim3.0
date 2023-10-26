import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class CrossingPlatform extends Actor
{
    /*
     * Crossing Platform Subclass
     * - Used to modify visual elements of the crossing platform
     */
    public CrossingPlatform(int length, int width){
        GreenfootImage crossing = getImage();
        crossing.scale(length,width);
        setImage(crossing);
    }
    public void act()
    {
        // Add your action code here.
    }
}
