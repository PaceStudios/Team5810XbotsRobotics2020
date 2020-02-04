package frc.robot.Subsystems;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.drive.MecanumDrive;

import java.util.*;
import frc.robot.Subsystems.Constants;
public class SimplifiedMecanum{
    VictorSP frontLeft;
    VictorSP frontRight;
    VictorSP backLeft;
    VictorSP backRight;
    double turn = 0;
    double horiztonalSpeed = 0;
    double verticalSpeed = 0;
    MecanumDrive drive;

    public SimplifiedMecanum(){
        frontLeft = new VictorSP(Constants.DRIVEBASEMOTOR1_PWM);
        frontRight = new VictorSP(Constants.DRIVEBASEMOTOR2_PWM);
        backLeft = new VictorSP(Constants.DRIVEBASEMOTOR3_PWM);
        backRight = new VictorSP(Constants.DRIVEBASEMOTOR4_PWM);
        drive = new MecanumDrive(frontLeft, backLeft, frontRight, backRight);
        
    }
    public void simplifiedDrive(boolean fieldRelative, double xSpeed, double ySpeed, double rot){
        turn = rot;
        horiztonalSpeed = xSpeed;
        verticalSpeed = ySpeed;
        drive.driveCartesian(xSpeed, ySpeed, rot);


    }

}