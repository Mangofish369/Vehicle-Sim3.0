import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class StopLine extends Actor
/*
 * StopLine Class 
 * - Used to modifiy the apperance of the stopline
 */
{   
    private TrafficLights trafficLight;
    
    public StopLine (){
        GreenfootImage image = getImage();
        image.scale(210,10);
        setRotation(90);
    }
    public void act()
    {
        // Add your action code here.
    }
}
