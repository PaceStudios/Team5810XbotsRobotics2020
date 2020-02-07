package frc.robot.Subsystems;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj2.*;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import java.util.*;
import frc.robot.Subsystems.Constants;
public class SimplifiedMecanum extends SubsystemBase{
    VictorSP frontLeft;
    VictorSP frontRight;
    VictorSP backLeft;
    VictorSP backRight;
    double turn = 0;
    double horiztonalSpeed = 0;
    double verticalSpeed = 0;
    MecanumDrive drive;

    public SimplifiedMecanum(){
        frontLeft = new VictorSP(Constants.FRONTLEFTMOTOR_PWM);
        frontRight = new VictorSP(Constants.FRONTRIGHTMOTOR_PWM);
        backLeft = new VictorSP(Constants.REARLEFTMOTOR_PWM);
        backRight = new VictorSP(Constants.REARRIGHTMOTOR_PWM);
        drive = new MecanumDrive(frontLeft, backLeft, frontRight, backRight);
        
    }
    public void simplifiedDrive(boolean fieldRelative, double xSpeed, double ySpeed, double rot){
        turn = rot;
        horiztonalSpeed = xSpeed;
        verticalSpeed = ySpeed;
        drive.driveCartesian(xSpeed, ySpeed, rot);


    }

}