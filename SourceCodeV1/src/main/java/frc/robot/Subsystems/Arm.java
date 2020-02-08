package frc.robot.Subsystems;
import java.util.*;
import frc.robot.Subsystems.Constants;
import edu.wpi.first.wpilibj.*;

/**
 * @apiNote Class dedicated to potential Arm Movement for the DumbBot Robot
 * @author John C. Pace
 * @since 01/03/2020
 * @version 02/08/2020
 */
public class Arm{
    private Victor motor1;
    private Victor motor2;
    private List<Victor> armMotors;
    public Arm(){
        motor1 = new Victor(Constants.DUMBBOT_ARCADEBOT_LIFT);
        motor2 = new Victor(Constants.DUMBBOT_ARCADEBOT_WRIST);
        armMotors = new ArrayList<Victor>();
        armMotors.add(motor1);
        armMotors.add(motor2);
    }
    public void killAllMotors(){
        for(int i = 0; i < armMotors.size(); i ++){
            armMotors.get(i).set(0);
        }
    }
    public void liftArm(Joystick joy, double power_total){
        if(joy.getRawButton(8)){
            motor1.set(power_total);
        }
        else if(joy.getRawButton(7)){
            motor1.set(-power_total);
        }
        else
        {
            motor1.set(Constants.DEAD_SPEED);
        }
    }
    public void liftWrist(Joystick joy, double power_total){
        if(joy.getRawButton(5)){
            motor2.set(power_total);
        }
        else if(joy.getRawButton(3)){
            motor2.set(-power_total);
        }
        else{
            motor2.set(0);
        }
    }
}