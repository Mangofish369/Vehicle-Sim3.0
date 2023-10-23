import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The Bus subclass
 */
public class Bus extends Vehicle
{
    public static final int STOP_DURATION = 1000;
    SimpleTimer stop = new SimpleTimer();
    public Bus(VehicleSpawner origin){
        super (origin); // call the superclass' constructor first
        
        //Set up values for Bus
        maxSpeed = 1.5 + ((Math.random() * 10)/5);
        speed = maxSpeed;
        // because the Bus graphic is tall, offset it a up (this may result in some collision check issues)
        yOffset = 15;
    }

    /**
     * Act - do whatever the Bus wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        if(moving){
            super.act();
        }
        else{
            if(stop.millisElapsed() >= STOP_DURATION){
                moving = true;
            }
        }
        
    }
    public void multicollision(){
        Pedestrian pFront = (Pedestrian)getOneObjectAtOffset((int)speed + getImage().getWidth()/2, 0, Pedestrian.class);
        Pedestrian pLeft = (Pedestrian)getOneObjectAtOffset((int)speed + getImage().getWidth()/2, getImage().getHeight()/-2, Pedestrian.class);
        Pedestrian pRight = (Pedestrian)getOneObjectAtOffset((int)speed + getImage().getWidth()/2, getImage().getHeight()/2, Pedestrian.class);
        
    }
    public boolean checkHitPedestrian () {
        Pedestrian p = (Pedestrian)getOneObjectAtOffset((int)speed + getImage().getWidth()/2, 0, Pedestrian.class);
        Walker w = (Walker)getOneObjectAtOffset((int)speed + getImage().getWidth()/2, 0, Walker.class);
        Biker b = (Biker)getOneObjectAtOffset((int)speed + getImage().getWidth()/2, 0, Biker.class);
        if( p!= null && p.isAwake()){
            if(w != null){
                getWorld().removeObject(p);
                moving = false;
                stop.mark();
            }
            else if(b!= null){
                p.knockDown();
            }
            return true; 
        }
        return false;
    }
}
