import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
public class Walker extends Pedestrian
{
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
