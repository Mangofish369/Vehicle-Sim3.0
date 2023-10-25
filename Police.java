import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class truck here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Police extends Vehicle
{
    public Police (VehicleSpawner origin){
        super(origin);
        
        maxSpeed = 2.0 + ((Math.random() * 30)/5);
        speed = maxSpeed;
        
        
        //yOffset = 10;
        
        GreenfootImage police = getImage();
        //police.scale(160,60);
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
