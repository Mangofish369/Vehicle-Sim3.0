import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class HealEffect here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class HealEffect extends Effect
{
    GreenfootImage heal;
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
