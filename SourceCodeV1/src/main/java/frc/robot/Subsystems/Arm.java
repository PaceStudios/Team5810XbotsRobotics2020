package frc.robot.Subsystems;
import edu.wpi.first.wpilibj.*;
public class Arm{
    /**
     * @apiNote This class is meant to deal with both the Arm and Wrist of the Old Robot
     */
    private Spark armMotor;
    private Spark wristMotor;
    public Arm(){
        armMotor = new Spark(Constants.DUMBBOT_ARCADEBOT_LIFT);
        wristMotor = new Spark(Constants.DUMBBOT_ARCADEBOT_WRIST);
    }

    public void moveArm(Joystick joy, double power){
        if(joy.getRawButton(12) == true){
            armMotor.set(power);
        }
        else if(joy.getRawButton(11) == true){
            armMotor.set(power);
        }
        else{
            armMotor.set(0);
        }
    }
    public void moveWrist(Joystick joy, double power){
        if(joy.getRawButton(5) == true){
            wristMotor.set(power);
        }
        else if(joy.getRawButton(3) == true){
            wristMotor.set(power);
        }
        else{
            wristMotor.set(0);
        } 
    }
}