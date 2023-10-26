import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Hit Box Subclass
 * - Instances of hitboxes are spawned by cars to determine if they can switch lanes
 */
public class HitBox extends Actor
{
    private int width;
    private int height;
    GreenfootImage hitbox;
    
    // Spawn an invisible rectanglular hitbox with the dimensions given by the car
    public HitBox(int x_cord, int y_cord,int width, int height){
        this.width = width;
        this.height = height;
        hitbox = new GreenfootImage (width,height);  
        hitbox.drawRect(x_cord,y_cord,width, height);
    }
    public void act()
    {
        // Add your action code here.
    }
    
    // Check if anything is touching the hitbox to determine if it is safe to change lanes
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
