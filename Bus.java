import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * The Bus subclass
 * It can pickup walkers and run over bikers
 * 
 * Mostly borrowed from Vehicle Simulation Starter Code
 */
public class Bus extends Vehicle
{
    public static final int STOP_DURATION = 1000; // Stop duration (in ms) for pickup
    SimpleTimer stop; // Timer for stop duration
    
    // Arraylist for collison detection hitbox
    // Not to be confused with lane change hitbox
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
    
    // Different y-offset collision detection points for the bus
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
    
    // Loop through the array of hitboxes to determine if collision occured
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
}
