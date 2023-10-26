import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * The Ambulance subclass
 * Ambulances have aoe healing effects to ensure no pedestrian is left behind (Local Collision)
 * - Great for mass healing
 */
public class Ambulance extends Vehicle
{
    public static final int HEAL_IMAGE_DURATION = 150; // VFX duration for healing circle
    SimpleTimer timer = new SimpleTimer();
    private HealEffect healEffect;
    
    protected GreenfootSound[] healSounds;
    protected int healSoundsIndex;
    public Ambulance(VehicleSpawner origin){
        super (origin); // call the superclass' constructor first
        
        maxSpeed = 2.5;
        speed = maxSpeed;
        //System.out.println("Width: "+getImage().getWidth()+"\nHeight: "+getImage().getHeight());
        GreenfootImage ambulance = getImage();
        ambulance.scale(100,50);
        
        healEffect = new HealEffect();
        
        healSoundsIndex = 0;
        healSounds = new GreenfootSound[20];
        for(int i = 0; i < healSounds.length; i++){
            healSounds[i] = new GreenfootSound("healEffect.wav");
        }
    }

    /**
     * Act - do whatever the Ambulance wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        // After a brief duration remove the healing VFX
        if(timer.millisElapsed() >= HEAL_IMAGE_DURATION){
            getWorld().removeObject(healEffect);    
        }
        super.act();
    }
    
    // Check if any pedestrains nearby are knocked down (Local Collision)
    public boolean checkHitPedestrian () {
        ArrayList<Pedestrian> peds = (ArrayList<Pedestrian>)getObjectsInRange(100,Pedestrian.class);
        for(Pedestrian pe : peds){
            // If pedestrian is knocked down, heal them
            if(pe != null && !pe.isAwake()){
                pe.healMe();
                playHealSound();
                getWorld().addObject(healEffect,getX(),getY());
                timer.mark();
            }
        }
        return false;
    }
    
    // Method for SFX
    public void playHealSound(){
        healSounds[healSoundsIndex].play();
        healSoundsIndex++;
        if(healSoundsIndex > healSounds.length -1){
                healSoundsIndex = 0;
        }
    }
}
