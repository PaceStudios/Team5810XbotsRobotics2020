package frc.robot;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj2.*;
import edu.wpi.first.wpilibj2.command.*;
import frc.robot.Commands.DriveWithXboxControllers;




public class RobotContainer{
    public RobotContainer(){
        init();
    }
    public void init(){
        configureCommands();
    }
    public void configureCommands(){

    }
    public Command getAutonomousCommand(){
        return new DriveWithXboxControllers();
    }
    

}
