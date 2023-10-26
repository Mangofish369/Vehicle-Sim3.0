import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The Police Class
 * Only spawns when a speeding car is detected
 * After a short duration, police will spawn in all lanes that had a speeder to try and chase it
 */
public class Police extends Vehicle
{
    public Police (VehicleSpawner origin){
        super(origin);
        
        maxSpeed = 2.0 + ((Math.random() * 30)/5); // Police cars have high speeds
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
