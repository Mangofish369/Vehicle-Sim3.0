import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
public class Biker extends Pedestrian
{
    public Biker(int direction){
        super(direction);
        maxSpeed = Math.random() * 2 + 1.5;
        speed = maxSpeed;
        if(direction == -1){
            setImage("images/Back-biker.png");
        }
        GreenfootImage biker = getImage();
        biker.scale(40,60);
    }
    public void act()
    {
        super.act();
    }
}
