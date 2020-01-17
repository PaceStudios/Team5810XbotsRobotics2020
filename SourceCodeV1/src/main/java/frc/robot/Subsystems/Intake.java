package frc.robot.Subsystems;
import edu.wpi.first.wpilibj.Spark;
import frc.robot.Subsystems.Constants;
public class Intake  

{
    // Motors can be changed according to what names they are.
    private boolean intakeEngaged = false;
    private Spark intake;
    public Intake(){
        
        intake = new Spark(Constants.intakeMotor1);
    }
    
    
    public void intakeBalls(double speed){
        intakeEngaged = true;
        intake.set(-speed);
        intakeEngaged = false;
    }
        

    public void outtakeBalls(double speed){
        intakeEngaged = true;
        intake.set(speed);
        intakeEngaged = false;
    }
    public boolean getIntakeStatus(){
        return intakeEngaged;
    }
    
}