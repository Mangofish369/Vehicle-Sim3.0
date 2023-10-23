import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class CrossingPlatform extends Actor
{
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
