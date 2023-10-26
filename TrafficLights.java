import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class TrafficLights extends Actor
/*
 * The Traffic Light Class
 * This class is responsible for changing the light in the world
 */
{
    //Image assets
    private GreenfootImage red = new GreenfootImage("redLight.png");
    private GreenfootImage yellow = new GreenfootImage("yellowLight.png");
    private GreenfootImage green = new GreenfootImage("greenLight.png");
    private GreenfootImage traffic; // The current traffic image
    public TrafficLights (){
        traffic = green; // By default the traffic starts with green
        traffic.scale(50,100);
        setRotation(-90);
        setImage(traffic);
    }
    public void act(){
        // Your run code here   
    }
    
    // Logic for selecting the next light
    public void changeLight(){
        if(getColour().equals("red")){
            traffic = green;
        }
        else if (getColour().equals("yellow")){
            traffic = red;
        }
        else if (getColour().equals("green")){
            traffic  = yellow;
        }
        traffic.scale(50,100);
        setRotation(-90);
        setImage(traffic);
    }
    
    // Method for determining the colour of current traffic light
    public String getColour(){
        if(getImage().equals(red)){
            return "red";
        }
        else if(getImage().equals(yellow)){
            return "yellow";
        }
        else if(getImage().equals(green)){
            return "green";
        }
        return "no light";
    }
}
