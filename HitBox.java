import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class HitBox here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class HitBox extends Actor
{
    private int width;
    private int height;
    GreenfootImage hitbox;
    
    /**
     * Act - do whatever the HitBox wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public HitBox(int x_cord, int y_cord,int width, int height){
        this.width = width;
        this.height = height;
        hitbox = new GreenfootImage (width,height);
        hitbox.setColor(Color.RED);
        hitbox.fill();   
        hitbox.drawRect(x_cord,y_cord,width, height);
    }
    public void act()
    {
        // Add your action code here.
    }
    public boolean checkHitVehicle(){
        if(isTouching(Vehicle.class)){
            return true;
        }
        return false;
    }
    public boolean checkHitPedestrian(){
        if(isTouching(Pedestrian.class)){
            return true;
        }
        return false;
    }
}
