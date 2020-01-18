package frc.robot.Subsystems;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class Limelight{
    double validTargets_tv; //Whether the limelight has any valid targets (0 to 1)
    double horitzontaloffset_tx; //Horiztonal Offset From the Crosshair to Target (-27 to 27 Degrees)
    double verticaloffset_ty; // Vertical offset from Crosshair to Target (-20.5 degrees to 20.5)
    double targetArea_ta; // (0% to 100% of an image)
    NetworkTable limelighttable = NetworkTableInstance.getDefault().getTable("limelight");
    NetworkTableEntry tv; //Whether the limelight has any valid targets (0 to 1)
    NetworkTableEntry tx; //Horiztonal Offset From the Crosshair to Target (-27 to 27 Degrees)
    NetworkTableEntry ty; // Vertical offset from Crosshair to Target (-20.5 degrees to 20.5)
    NetworkTableEntry ta; // (0% to 100% of an image)
  
    public Limelight(){
        tv = limelighttable.getEntry("tv");
        tx = limelighttable.getEntry("tx");
        ty = limelighttable.getEntry("ty");
        ta = limelighttable.getEntry("ta");
        // Sets the Double values to the values given from the getDouble method in NetworkTable entry objects
        validTargets_tv = tv.getDouble(0.0);
        horitzontaloffset_tx= tx.getDouble(0.0);
        verticaloffset_ty= ty.getDouble(0.0);
        targetArea_ta= ta.getDouble(0.0);

        // Declares the smart dashboard periodically
        SmartDashboard.putNumber("Limelight X Value", horitzontaloffset_tx);
        SmartDashboard.putNumber("Limelight Y Value", verticaloffset_ty);
        SmartDashboard.putNumber("Limelight Area", targetArea_ta);
        SmartDashboard.putNumber("Limelight Target Boolean", validTargets_tv);
        updateLimeLight();
        
        
    }
    public void updateLimeLight(){
        /* Creates the Camera mode and sets it to specific values

        0 - Vision Processor
        1 - Driver Camera (increases exposure, disables vision processing)
        */
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode").forceSetNumber(1); 
        /*
        Sets the Stream mode to Standard, useful for the Smart Dashboard
        */
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("stream").forceSetNumber(0);
        /*
        Sets the LED state on the physical Limelight
        */
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").forceSetNumber(0);

    }
    public NetworkTable getTable(){
        return limelighttable;
    }
}