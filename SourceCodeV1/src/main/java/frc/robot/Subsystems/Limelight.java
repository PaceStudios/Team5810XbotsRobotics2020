package frc.robot.Subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//import edu.wpi.first.networktables.NetworkTable;
//import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class Limelight {

    double tv = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0);
    double tx = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
    double ty = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0);
    double ta = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ta").getDouble(0);

    public void SmartDashBoardExecute ()
        {
            SmartDashboard.putNumber("LimelightV", tv);
            SmartDashboard.putNumber("LimelightX", tx);
            SmartDashboard.putNumber("LimelightY", ty);
            SmartDashboard.putNumber("LimelightArea", ta);
        }


}

//Limelight sample code given by Limelight (that doesn't work lol)
//NetworkTable table = NetworkTable.getTable("limelight");
//double targetOffsetAngle_Horizontal = table.getNumber("tx", 0);
//double targetOffsetAngle_Vertical = table.getNumber("ty", 0);
//double targetArea = table.getNumber("ta", 0);
//double targetSkew = table.getNumber("ts", 0);

