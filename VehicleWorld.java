import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.Collections; //helloooo!!
import java.util.ArrayList; 
import java.util.Queue;
import java.util.LinkedList;
/**
 * Welcome to the traffic simulation; Created by Vincent Li, October 25, 2023
 * 
 * Within the 8 lanes, cars and pedestrians generally follow traffic control for smooth driving.
 * However every so often, a speed freak (we all know so well) may spawn, ignoring all traffic rules and running over red lights.
 * This promonts a police car to chase after the speed freak, and ironiclly the police will also break all the rules in order to 
 * catch this car.
 * 
 * To add some visual elements, rain will occur every 10 - 16 sec, and slow down all vehicles briefly 
 * Traffic lights change automatically, the time period is set to allow some clutter of vehicles and pedestrains. 
 * Cars will change lanes modestly if they feel the need to, speeding cars and police will violently change lanes to ensure they
 * maintain as much speed as possible.
 * 
 * Side note: A mouse pointer is also created for you!
 *               --> To find the coordinates of your mouse press 'n'
 * 
 * 
 * Credits:
 * -----------------------------------Art work-----------------------------------
 * 
 * Image of Biker
 * - Designed by: flatvectors
 * - Found on: Adobe Stock.com
 * https://stock.adobe.com/ca/search/images?filters%5Bcontent_type%3Aphoto%5D=1&filters%5Bcontent_type%3Aillustration%5D=1&filters%5Bcontent_type%3Azip_vector%5D=1&filters%5Bcontent_type%3Avideo%5D=0&filters%5Bcontent_type%3Atemplate%5D=0&filters%5Bcontent_type%3A3d%5D=0&filters%5Bcontent_type%3Aaudio%5D=0&filters%5Binclude_stock_enterprise%5D=0&filters%5Bis_editorial%5D=0&filters%5Bfree_collection%5D=0&order=relevance&serie_id=493870822&asset_id=364064825
 * 
 * Image of Traffic Lights
 * - Designed by: bubaone
 * - Found on: iStock.com
 * https://www.istockphoto.com/vector/led-modern-street-traffic-lights-background-gm165818773-18626367
 * 
 * Image of white line
 * - Author Unknown
 * - Found on: CITYPNG.com
 * https://www.citypng.com/photo/17303/horizontal-white-line
 * 
 * Image of police car
 * - Designed by: Topgeek
 * - Found on: Dreamstime.com
 * https://www.dreamstime.com/modern-army-truck-police-car-pixel-game-design-military-technics-force-heavy-equipment-modern-army-truck-police-car-image213411909
 * 
 * 
 * -----------------------------------Code-----------------------------------
 * Simple Timer Class:
 * - Designed by: Neil Brown
 * - Downloaded from: Greenfoot ide
 * 
 * World & Actor Class:
 * - Found on Greenfoot
 * 
 * Vehicle Similation Starter Code:
 * - Designed by: Jordan Cohen
 * - Found by taking: ICS4U1-01
 * 
 * Additionally Borrowed from Jordan Cohen:
 * - Storm Demo
 * - Bus Stop Demo
 * - Sound Demo
 * - Sound Effects Slideshow
 * - Collision Detection Slideshow
 * -----------------------------------Sound-----------------------------------
 * Car honking
 * - Designed by: Jacob Bowerman <-- on Youtube
 * - Downloaded on: MediaFire.com
 * https://www.youtube.com/watch?v=pNoGHcIHzYo (Source)
 * https://www.mediafire.com/file/bw8twrv6t9p8y1i/car_beep.wav/file (Download)
 * 
 * Driving in the rain
 * - Author Unknown
 * - Found on: mixkit.co
 * https://mixkit.co/free-sound-effects/traffic/
 * 
 */
public class VehicleWorld extends World
{
    private GreenfootImage background;
    private GreenfootImage crossingPlatform;

    // Color Constants
    public static Color GREY_BORDER = new Color (108, 108, 108);
    public static Color GREY_STREET = new Color (88, 88, 88);
    public static Color YELLOW_LINE = new Color (255, 216, 0);

    public static boolean SHOW_SPAWNERS = true;
    
    // Set Y Positions for Pedestrians to spawn
    public static final int TOP_SPAWN = 190; // Pedestrians who spawn on top
    public static final int BOTTOM_SPAWN = 705; // Pedestrians who spawn on the bottom

    // Instance variables / Objects
    private boolean twoWayTraffic, splitAtCenter;
    private int laneHeight, laneCount, spaceBetweenLanes;
    private int[] lanePositionsY;
    private VehicleSpawner[] laneSpawners;
    private boolean crime;
    
    //Timer
    public static final int DELAY_SPAWN_DURATION = 60;
    private int timer = 0;
    SimpleTimer policeDelay = new SimpleTimer();
    Queue<Integer> speedingLane = new LinkedList<>();
    
    //Rain Timer
    private int actCount;
    private int nextRainEffectAct;
    
    //Mouse pointer
    Pointer pointer = new Pointer();
    
    // Traffic Lights and Stoplines
    TrafficLights bottom = new TrafficLights();
    TrafficLights top = new TrafficLights();
    
    StopLine leftToRight = new StopLine();
    StopLine rightToLeft = new StopLine();

    
    private int nextChangeLightAct;
    private int RED_LIGHT_DURATION;
    private int YELLOW_LIGHT_DURATION;
    private int GREEN_LIGHT_DURATION;
    
    SimpleTimer changeLightTime = new SimpleTimer();
    
    private GreenfootSound ambience;
    private GreenfootSound rainSound;
    
    
    /**
     * Constructor for objects of class MyWorld.
     * 
     * Note that the Constrcutor for the default world is always called
     * when you click the reset button in the Greenfoot scenario screen -
     * this is is basically the code that runs when the program start.
     * Anything that should be done FIRST should go here.
     * 
     */
    public VehicleWorld()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(1024, 800, 1, false); 
        
        actCount = 0;
        // This command (from Greenfoot World API) sets the order in which 
        // objects will be displayed. In this example, Pedestrians will
        // always be on top of everything else, then Vehicles (of all
        // sub class types) and after that, all other classes not listed
        // will be displayed in random order. 
        //setPaintOrder (Pedestrian.class, Vehicle.class); // Commented out to use Z-sort instead
        
        nextRainEffectAct = 300;
        
        // set up background -- If you change this, make 100% sure
        // that your chosen image is the same size as the World
        background = new GreenfootImage ("grass-background.png");
        background.scale(1024, 800);
        setBackground (background);
        
        crossingPlatform = new GreenfootImage("crossing-road.png");
        
        
        

        // Set critical variables - will affect lane drawing
        laneCount = 8;
        laneHeight = 48;
        spaceBetweenLanes = 6;
        splitAtCenter = true;
        twoWayTraffic = true;

        // Init lane spawner objects 
        laneSpawners = new VehicleSpawner[laneCount];

        // Prepare lanes method - draws the lanes
        lanePositionsY = prepareLanes (this, background, laneSpawners, 232, laneHeight, laneCount, spaceBetweenLanes, twoWayTraffic, splitAtCenter);

        laneSpawners[0].setSpeedModifier(0.8);
        laneSpawners[3].setSpeedModifier(1.4);

        setBackground (background);
        
        addObject(new CrossingPlatform(150,200),500,330);
        addObject(new CrossingPlatform(150,200),500,500);
        addObject(new CrossingPlatform(150,200),500,570);
        
        addObject(bottom,650,200);
        addObject(top,350,700);
        
        nextChangeLightAct = 360;
        RED_LIGHT_DURATION = 360;
        YELLOW_LIGHT_DURATION = 180;
        GREEN_LIGHT_DURATION = 480;
        
        addObject(pointer, -10,-10);
        
        addObject(leftToRight, 400,560);
        addObject(rightToLeft, 600,340);
        
        ambience = new GreenfootSound("drivingInTheRain.mp3");
        rainSound = new GreenfootSound("drivingInTheRain.mp3");
    }

    public void started(){
        ambience.playLoop();
    }
    
    public void stopped(){
        ambience.stop();
    }
    
    public void act () {
        actCount++;
        changeTraffic();
        if(crime){
            timer++;
        }
        pointer.checkKey();
        spawn();
        zSort ((ArrayList<Actor>)(getObjects(Actor.class)), this);
    }

    private void spawn () {
        // Chance to spawn a vehicle
        if (Greenfoot.getRandomNumber (60) == 0){
            int lane = Greenfoot.getRandomNumber(laneCount);
            if (!laneSpawners[lane].isTouchingVehicle()){
                int vehicleType = Greenfoot.getRandomNumber(3);
                if (vehicleType == 0){
                    Car car = new Car(laneSpawners[lane]);
                    addObject(car,0,0);
                    double speed = car.getSpeed();
                    if(speed > 4){
                        crime = true;
                        car.setCrime(true);
                        speedingLane.offer(lane);
                    }
                } else if (vehicleType == 1){
                    addObject(new Bus(laneSpawners[lane]), 0, 0);
                } else if (vehicleType == 2){
                    addObject(new Ambulance(laneSpawners[lane]), 0, 0);
                } 
                if (crime == true){
                    if(timer >= DELAY_SPAWN_DURATION){
                        while(!speedingLane.isEmpty()){
                            int chase = speedingLane.poll();
                            addObject(new Police(laneSpawners[chase]), -1000,0);
                        }
                        crime = false;
                        timer = 0;
                    }
                } 
            }
        }

        // Chance to spawn a Pedestrian
        if (Greenfoot.getRandomNumber (60) == 0){
            int xSpawnLocation = Greenfoot.getRandomNumber (150) + 430; // random between 99 and 699, so not near edges
            boolean spawnAtTop = Greenfoot.getRandomNumber(2) == 0 ? true : false;
            int pedestrianType = Greenfoot.getRandomNumber(2);
            int spawnChance = Greenfoot.getRandomNumber(1000);
            boolean spawn = true;
            if(checkLight().equals("red")){
                if(spawnChance <= 500){
                    spawn = true;
                } else{
                    spawn = false;
                } 
            } else if (checkLight().equals("yellow")){
               if(spawnChance <= 100){
                    spawn = true;
                } else{
                    spawn = false;
                }  
            } else if (checkLight().equals("green")){
                if(spawnChance <= 50){
                    spawn = true;
                } else{
                    spawn = false;
                }
            }
            if(spawn){
                if (spawnAtTop){
                    if(pedestrianType == 0){
                        addObject(new Walker(1), xSpawnLocation, TOP_SPAWN);
                    } else if (pedestrianType == 1) {
                        addObject (new Biker (1), xSpawnLocation, TOP_SPAWN);
                    }
                } else {
                    if (pedestrianType == 0){
                        addObject(new Walker (-1), xSpawnLocation, BOTTOM_SPAWN);
                    } else if (pedestrianType == 1){
                        addObject(new Biker (-1), xSpawnLocation, BOTTOM_SPAWN);
                    }
                }
            }
        }

        if(actCount == nextRainEffectAct){
            addObject(new RainEffect(), 0, getHeight()/2); // Spawn rain
            rainSound.play();
            
            int actsUntilNextStorm = Greenfoot.getRandomNumber (300) + 600; // Random delay for next spawn + minimum 10 seconds
            
            nextRainEffectAct = actCount + actsUntilNextStorm; // Next spawn interval determined 
        }
    }
    
        
    public void changeTraffic(){
        if(actCount >= nextChangeLightAct){
            String light = checkLight();
            if(light.equals("green")){
                top.changeLight();
                bottom.changeLight();
                nextChangeLightAct += YELLOW_LIGHT_DURATION;
            } else if (light.equals("yellow")){
                top.changeLight();
                bottom.changeLight();
                nextChangeLightAct += RED_LIGHT_DURATION;
            } else if (light.equals("red")){
                top.changeLight();
                bottom.changeLight();
                nextChangeLightAct += GREEN_LIGHT_DURATION;
            }
        }
    }
    
    public String checkLight(){
        String light = top.getColour();
        return light;
    }

    /**
     *  Given a lane number (zero-indexed), return the y position
     *  in the centre of the lane. (doesn't factor offset, so 
     *  watch your offset, i.e. with Bus).
     *  
     *  @param lane the lane number (zero-indexed)
     *  @return int the y position of the lane's center, or -1 if invalid
     */
    public int getLaneY (int lane){
        if (lane < lanePositionsY.length){
            return lanePositionsY[lane];
        } 
        return -1;
    }

    /**
     * Given a y-position, return the lane number (zero-indexed).
     * Note that the y-position must be valid, and you should 
     * include the offset in your calculations before calling this method.
     * For example, if a Bus is in a lane at y=100, but is offset by -20,
     * it is actually in the lane located at y=80, so you should send
     * 80 to this method, not 100.
     * 
     * @param y - the y position of the lane the Vehicle is in
     * @return int the lane number, zero-indexed
     * 
     */
    public int getLane (int y){
        for (int i = 0; i < lanePositionsY.length; i++){
            if (y == lanePositionsY[i]){
                return i;
            }
        }
        return -1;
    }

    public static int[] prepareLanes (World world, GreenfootImage target, VehicleSpawner[] spawners, int startY, int heightPerLane, int lanes, int spacing, boolean twoWay, boolean centreSplit, int centreSpacing)
    {
        // Declare an array to store the y values as I calculate them
        int[] lanePositions = new int[lanes];
        // Pre-calculate half of the lane height, as this will frequently be used for drawing.
        // To help make it clear, the heightOffset is the distance from the centre of the lane (it's y position)
        // to the outer edge of the lane.
        int heightOffset = heightPerLane / 2;

        // draw top border
        target.setColor (GREY_BORDER);
        target.fillRect (0, startY, target.getWidth(), spacing);

        // Main Loop to Calculate Positions and draw lanes
        for (int i = 0; i < lanes; i++){
            // calculate the position for the lane
            lanePositions[i] = startY + spacing + (i * (heightPerLane+spacing)) + heightOffset ;

            // draw lane
            target.setColor(GREY_STREET); 
            // the lane body
            target.fillRect (0, lanePositions[i] - heightOffset, target.getWidth(), heightPerLane);
            // the lane spacing - where the white or yellow lines will get drawn
            target.fillRect(0, lanePositions[i] + heightOffset, target.getWidth(), spacing);

            // Place spawners and draw lines depending on whether its 2 way and centre split
            if (twoWay && centreSplit){
                // first half of the lanes go rightward (no option for left-hand drive, sorry UK students .. ?)
                if ( i < lanes / 2){
                    spawners[i] = new VehicleSpawner(false, heightPerLane, i);
                    world.addObject(spawners[i], target.getWidth(), lanePositions[i]);
                } else { // second half of the lanes go leftward
                    spawners[i] = new VehicleSpawner(true, heightPerLane, i);
                    world.addObject(spawners[i], 0, lanePositions[i]);
                }

                // draw yellow lines if middle 
                if (i == lanes / 2){
                    target.setColor(YELLOW_LINE);
                    target.fillRect(0, lanePositions[i] - heightOffset - spacing, target.getWidth(), spacing);

                } else if (i > 0){ // draw white lines if not first lane
                    for (int j = 0; j < target.getWidth(); j += 120){
                        target.setColor (Color.WHITE);
                        target.fillRect (j, lanePositions[i] - heightOffset - spacing, 60, spacing);
                    }
                } 

            } else if (twoWay){ // not center split
                if ( i % 2 == 0){
                    spawners[i] = new VehicleSpawner(false, heightPerLane, i);
                    world.addObject(spawners[i], target.getWidth(), lanePositions[i]);
                } else {
                    spawners[i] = new VehicleSpawner(true, heightPerLane, i);
                    world.addObject(spawners[i], 0, lanePositions[i]);
                }

                // draw Grey Border if between two "Streets"
                if (i > 0){ // but not in first position
                    if (i % 2 == 0){
                        target.setColor(GREY_BORDER);
                        target.fillRect(0, lanePositions[i] - heightOffset - spacing, target.getWidth(), spacing);

                    } else { // draw dotted lines
                        for (int j = 0; j < target.getWidth(); j += 120){
                            target.setColor (YELLOW_LINE);
                            target.fillRect (j, lanePositions[i] - heightOffset - spacing, 60, spacing);
                        }
                    } 
                }
            } else { // One way traffic
                spawners[i] = new VehicleSpawner(true, heightPerLane, i);
                world.addObject(spawners[i], 0, lanePositions[i]);
                if (i > 0){
                    for (int j = 0; j < target.getWidth(); j += 120){
                        target.setColor (Color.WHITE);
                        target.fillRect (j, lanePositions[i] - heightOffset - spacing, 60, spacing);
                    }
                }
            }
        }
        // draws bottom border
        target.setColor (GREY_BORDER);
        target.fillRect (0, lanePositions[lanes-1] + heightOffset, target.getWidth(), spacing);

        return lanePositions;
    }

    /**
     * A z-sort method which will sort Actors so that Actors that are
     * displayed "higher" on the screen (lower y values) will show up underneath
     * Actors that are drawn "lower" on the screen (higher y values), creating a
     * better perspective. 
     */
    public static void zSort (ArrayList<Actor> actorsToSort, World world){
        ArrayList<ActorContent> acList = new ArrayList<ActorContent>();
        // Create a list of ActorContent objects and populate it with all Actors sent to be sorted
        for (Actor a : actorsToSort){
            if(!(a instanceof CrossingPlatform || a instanceof StopLine)){
                acList.add (new ActorContent (a, a.getX(), a.getY()));
            }
        }    
        // Sort the Actor, using the ActorContent comparitor (compares by y coordinate)
        Collections.sort(acList);
        // Replace the Actors from the ActorContent list into the World, inserting them one at a time
        // in the desired paint order (in this case lowest y value first, so objects further down the 
        // screen will appear in "front" of the ones above them).
        for (ActorContent a : acList){
            Actor actor  = a.getActor();
            world.removeObject(actor);
            world.addObject(actor, a.getX(), a.getY());
        }
    }

    /**
     * <p>The prepareLanes method is a static (standalone) method that takes a list of parameters about the desired roadway and then builds it.</p>
     * 
     * <p><b>Note:</b> So far, Centre-split is the only option, regardless of what values you send for that parameters.</p>
     *
     * <p>This method does three things:</p>
     * <ul>
     *  <li> Determines the Y coordinate for each lane (each lane is centered vertically around the position)</li>
     *  <li> Draws lanes onto the GreenfootImage target that is passed in at the specified / calculated positions. 
     *       (Nothing is returned, it just manipulates the object which affects the original).</li>
     *  <li> Places the VehicleSpawners (passed in via the array parameter spawners) into the World (also passed in via parameters).</li>
     * </ul>
     * 
     * <p> After this method is run, there is a visual road as well as the objects needed to spawn Vehicles. Examine the table below for an
     * in-depth description of what the roadway will look like and what each parameter/component represents.</p>
     * 
     * <pre>
     *                  <=== Start Y
     *  ||||||||||||||  <=== Top Border
     *  /------------\
     *  |            |  
     *  |      Y[0]  |  <=== Lane Position (Y) is the middle of the lane
     *  |            |
     *  \------------/
     *  [##] [##] [##| <== spacing ( where the lane lines or borders are )
     *  /------------\
     *  |            |  
     *  |      Y[1]  |
     *  |            |
     *  \------------/
     *  ||||||||||||||  <== Bottom Border
     * </pre>
     * 
     * @param world     The World that the VehicleSpawners will be added to
     * @param target    The GreenfootImage that the lanes will be drawn on, usually but not necessarily the background of the World.
     * @param spawners  An array of VehicleSpawner to be added to the World
     * @param startY    The top Y position where lanes (drawing) should start
     * @param heightPerLane The height of the desired lanes
     * @param lanes     The total number of lanes desired
     * @param spacing   The distance, in pixels, between each lane
     * @param twoWay    Should traffic flow both ways? Leave false for a one-way street (Not Yet Implemented)
     * @param centreSplit   Should the whole road be split in the middle? Or lots of parallel two-way streets? Must also be two-way street (twoWay == true) or else NO EFFECT
     * 
     */
    public static int[] prepareLanes (World world, GreenfootImage target, VehicleSpawner[] spawners, int startY, int heightPerLane, int lanes, int spacing, boolean twoWay, boolean centreSplit){
        return prepareLanes (world, target, spawners, startY, heightPerLane, lanes, spacing, twoWay, centreSplit, spacing);
    }

}

/**
 * Container to hold and Actor and an LOCAL position (so the data isn't lost when the Actor is temporarily
 * removed from the World).
 */
class ActorContent implements Comparable <ActorContent> {
    private Actor actor;
    private int xx, yy;
    public ActorContent(Actor actor, int xx, int yy){
        this.actor = actor;
        this.xx = xx;
        this.yy = yy;
    }

    public void setLocation (int x, int y){
        xx = x;
        yy = y;
    }

    public int getX() {
        return xx;
    }

    public int getY() {
        return yy;
    }

    public Actor getActor(){
        return actor;
    }

    public String toString () {
        return "Actor: " + actor + " at " + xx + ", " + yy;
    }

    public int compareTo (ActorContent a){
        return this.getY() - a.getY();
    }

}
