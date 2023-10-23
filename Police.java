import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class truck here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Police extends Vehicle
{
    /**
     * Act - do whatever the truck wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public Police (VehicleSpawner origin){
        super(origin);
        
        maxSpeed = 2.5;
        speed = maxSpeed;
        
        GreenfootImage police = getImage();
        police.scale(100,50);
    }
    public void act()
    {
        super.act();
    }
    public boolean checkHitPedestrian(){
        Pedestrian p = (Pedestrian)getOneObjectAtOffset((int)speed + getImage().getWidth()/2, 0, Pedestrian.class);
        if (p != null)
        {
            p.knockDown();
            return true;
        }
        return false;
    }
}
