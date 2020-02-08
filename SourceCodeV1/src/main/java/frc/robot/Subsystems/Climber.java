package frc.robot.Subsystems;
import edu.wpi.first.wpilibj.*;
/**
 * @author John C. Pace
 * @since 01/19/2020
 * @version 01/19/2020
 * @apiNote This will be the class where anything related to the climbing mechanism will probably be. As of this point (01/18/20), the climber is going to be a linear slide like device,
    Which will probably involve multiple motors and motor groups working simultaneously. Still in the process of being determined.
 */

public class Climber{
    private String mode = "";
    private VictorSP climbMotor; // Representing 2 Motors that will spin in tandem to each other. 
    public Climber(){
        mode = "Climbing Class";
        climbMotor = new VictorSP(Constants.CLIMBMOTOR_PWM);
    }
    public String getMode(){
        return mode;
    }
    public void killAllMotors(){
        climbMotor.disable();
    }
    public void climb(double powerInput){
        climbMotor.set(powerInput);
    }
}
