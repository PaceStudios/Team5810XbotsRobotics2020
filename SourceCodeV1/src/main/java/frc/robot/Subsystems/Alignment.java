package frc.robot.Subsystems;
import java.awt.*;
/**
 * @author John C. Pace
 * @since 01/19/2020
 * @version 01/19/2020
 * @apiNote Class meant to deal with alignment, will eventually be implemented once we understand how to use encoders better 
 */
public class Alignment{
    private boolean isAligned = false; 
    private Color myColor;
    public Alignment(){
        myColor = Color.BLACK; 
    }
    public void moveUp(double distance){}
    public void moveLeft(double distance){}
    public void moveRight(double distance){}
    public void moveDown(double distance){}
    public boolean isRobotAligned(){
        return isAligned;
    }
}

