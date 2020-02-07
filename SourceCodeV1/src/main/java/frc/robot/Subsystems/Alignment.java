package frc.robot.Subsystems;
import java.awt.*;
import edu.wpi.first.wpilibj2.*;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


/**
 * @author John C. Pace
 * @since 01/19/2020
 * @version 01/20/2020
 * @apiNote Class meant to deal with alignment, will eventually be implemented once we understand how to use encoders better 
 */
public class Alignment extends SubsystemBase {
    private boolean isAligned = false; 
    private DriveTrain mydrive = new DriveTrain();
    public Alignment(){
    }
    public void moveUp(double distance){
        mydrive.moveExactDistance(distance, Constants.MODE_UP);
    }
    public void moveLeft(double distance){
        mydrive.moveExactDistance(distance, Constants.MODE_DOWN);
    }
    public void moveRight(double distance){
        mydrive.moveExactDistance(distance, Constants.MODE_RIGHT);
    }
    public void moveDown(double distance){
        mydrive.moveExactDistance(distance, Constants.MODE_LEFT);
    }
    public boolean isRobotAligned(){
        return isAligned;
    }
    public void setAlignment(boolean flag){

    }
}

