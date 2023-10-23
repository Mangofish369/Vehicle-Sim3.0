import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
public class Biker extends Pedestrian
{
    private double speed;
    private double maxSpeed;
    public Biker(int direction){
        super(direction);
        if(direction == -1){
            setImage("images/Back-biker.png");
        }
        GreenfootImage biker = getImage();
        biker.scale(40,60);
        maxSpeed = Math.random() * 2 + 3;
        speed = maxSpeed;
    }
    public void act()
    {
        super.act();
    }
}
