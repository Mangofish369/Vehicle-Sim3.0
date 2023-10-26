import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The VFX for Ambulance's revive ability
 */
public class HealEffect extends Effect
{
    private GreenfootImage heal;
    public HealEffect (){
        heal = new GreenfootImage("healEffect.png");
        heal.scale(250,250);
        setImage(heal);
    }
    public void act()
    {
        // Add your action code here.
    }
}
