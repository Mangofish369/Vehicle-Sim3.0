import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * The Ambulance subclass
 */
public class Ambulance extends Vehicle
{
    public static final int HEAL_IMAGE_DURATION = 150;
    SimpleTimer timer = new SimpleTimer();
    HealEffect healEffect;
    
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
            healSounds[i] = new GreenfootSound("carBeep.wav");
        }
    }

    /**
     * Act - do whatever the Ambulance wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        if(timer.millisElapsed() >= HEAL_IMAGE_DURATION){
            getWorld().removeObject(healEffect);    
        }
        super.act();
    }

    public boolean checkHitPedestrian () {
        ArrayList<Pedestrian> peds = (ArrayList<Pedestrian>)getObjectsInRange(100,Pedestrian.class);
        for(Pedestrian pe : peds){
            if(pe != null && !pe.isAwake()){
                pe.healMe();
                playHealSound();
                getWorld().addObject(healEffect,getX(),getY());
                timer.mark();
            }
        }
        return false;
    }
    
    public void playHealSound(){
        healSounds[healSoundsIndex].play();
        healSoundsIndex++;
        if(healSoundsIndex > healSounds.length -1){
                healSoundsIndex = 0;
        }
    }
}
