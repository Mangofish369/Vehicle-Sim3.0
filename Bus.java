import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * The Bus subclass
 */
public class Bus extends Vehicle
{
    public static final int STOP_DURATION = 1000;
    SimpleTimer stop;
    ArrayList <Pedestrian> hitbox; 
    public Bus(VehicleSpawner origin){
        super (origin); // call the superclass' constructor first
        
        //Set up values for Bus
        maxSpeed = 1.5 + ((Math.random() * 10)/5);
        speed = maxSpeed;
        // because the Bus graphic is tall, offset it a up (this may result in some collision check issues)
        yOffset = 15;
        
        stop = new SimpleTimer();
        
        hitbox = new ArrayList <Pedestrian>();
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
    public void generateHitbox(){
        Pedestrian pFront = (Pedestrian)getOneObjectAtOffset((int)speed + getImage().getWidth()/2, 0, Pedestrian.class);
        Pedestrian pTop = (Pedestrian)getOneObjectAtOffset(-4+(int)speed + getImage().getWidth()/2, getImage().getHeight()/2, Pedestrian.class);
        Pedestrian pBottom = (Pedestrian)getOneObjectAtOffset(-4+(int)speed + getImage().getWidth()/2, getImage().getHeight()/2, Pedestrian.class);
        if(direction == 1){
            hitbox.add(pFront);
            hitbox.add(pTop);
        } else if (direction == -1){
            hitbox.add(pFront);
            hitbox.add(pBottom);
        }
    }
    public boolean checkHitPedestrian(){
        for(Pedestrian p : hitbox){
            if(p!= null && p.isAwake()){
                System.out.println("here");
                if(p instanceof Walker){
                    getWorld().removeObject(p);
                    moving = false;
                    stop.mark();
                    return true;
                } else if (p instanceof Biker){
                    p.knockDown();
                    return true;
                } 
            }
        }
        return false;
    }
    public boolean multicollision () {
        Pedestrian p = (Pedestrian)getOneObjectAtOffset((int)speed + getImage().getWidth()/2, 0, Pedestrian.class);
        Walker w = (Walker)getOneObjectAtOffset((int)speed + getImage().getWidth()/2, 0, Walker.class);
        Biker b = (Biker)getOneObjectAtOffset((int)speed + getImage().getWidth()/2, 0, Biker.class);
        if( p!= null && p.isAwake()){
            if(w != null){
                getWorld().removeObject(p);
                moving = false;
                stop.mark();
                return true;
            }
            else if(b!= null){
                p.knockDown();
            }
            return true; 
        }
        return false;
    }
}
