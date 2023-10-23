import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)


public class TrafficLights extends Actor
{
    private GreenfootImage red = new GreenfootImage("redLight.png");
    private GreenfootImage yellow = new GreenfootImage("yellowLight.png");
    private GreenfootImage green = new GreenfootImage("greenLight.png");
    private GreenfootImage traffic; 
    public TrafficLights (){
        traffic = green;
        traffic.scale(50,100);
        setRotation(-90);
        setImage(traffic);
    }
    public void act(){
        if(Greenfoot.isKeyDown("w")){
            changeLight("red");
        }
        if(Greenfoot.isKeyDown("e")){
            changeLight("green");
        }   
    }
    public void changeLight(String colour){
        if(colour.equals("red")){
            traffic = red;
        }
        else if (colour.equals("yellow")){
            traffic = yellow;
        }
        else if (colour.equals("green")){
            traffic  = green;
        }
        traffic.scale(50,100);
        setRotation(-90);
        setImage(traffic);
    }
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
