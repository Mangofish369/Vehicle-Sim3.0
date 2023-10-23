import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Pointer extends Actor
{
    
    public Pointer(){
        GreenfootImage image = new GreenfootImage(20, 20);
        image.fillOval(0, 0, 20,20);
        setImage(image);
    }

    public void act(){
        MouseInfo info = Greenfoot.getMouseInfo();
        if(info != null){
            int x = info.getX();
            int y = info.getY();
            setLocation(x, y);
        }
        
    }
    public void checkKey(){
        String key = Greenfoot.getKey();
        MouseInfo info = Greenfoot.getMouseInfo();
        if("n".equals(key)){
            System.out.println("x:"+info.getX());
            System.out.println("y:"+info.getY());
        }
    }
}