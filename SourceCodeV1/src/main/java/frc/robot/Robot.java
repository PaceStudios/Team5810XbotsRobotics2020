/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;


import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.Subsystems.*;
import frc.robot.Subsystems.Constants;


public class Robot extends TimedRobot {
  private XboxController m_controller;
  private Joystick m_joystick;
  private DriveTrain m_mecanum = new DriveTrain();
  private Timer timer01 = new Timer();
  private Intake intake1 = new Intake();
  private Limelight limelight = new Limelight();
  private boolean isAimOn;

  @Override
  public void robotInit(){
    super.robotInit();
    // Declares and Creates Controllers and Joystick
    m_controller = new XboxController(Constants.XBOXCONTROL);
    m_joystick = new Joystick(Constants.JOYSTICKCONTROL);
    // Creates Subsystems and Activates Them in Teleop Periodic 
    m_mecanum = new DriveTrain();
    timer01 = new Timer();
    intake1 = new Intake();
    limelight = new Limelight();
    isAimOn = false;
    }
  @Override
  public void autonomousInit() {
    
    super.autonomousInit();
    driveWithJoystick(false);
    timer01.reset();
  }
  

  @Override
  public void autonomousPeriodic() {
    timer01.reset();
    timer01.start();
    driveWithJoystick(false);
    m_mecanum.updateOdometry();
    
  }
  @Override
  public void teleopInit() {
    
    super.teleopInit();

  }
  @Override
  public void teleopPeriodic() {
    timer01.reset();
    timer01.start();
    driveWithJoystick(true);
    if (m_joystick.getRawButton(1)){
      intake1.intakeBalls(0.8);
    }
    if(m_joystick.getRawButton(2)){
      intake1.outtakeBalls(0.8);
    }
    if(m_controller.getRawButton(1)){ // Red Button 'A'
      isAimOn = !isAimOn;
      while(isAimOn){
        aimWithVision(limelight.getTable());
      }
    }
  }
  @Override
  public void disabledInit(){
    super.disabledInit();
  }
  @Override
  public void disabledPeriodic(){
    super.disabledPeriodic();
  }
  @Override
  public void testInit(){
    super.testInit();
  }
  @Override
  public void testPeriodic(){
    super.testPeriodic();
  }

  private void driveWithJoystick(boolean fieldRelative) {
    // Get the x speed. We are inverting this because Xbox controllers return
    // negative values when we push forward.
    final var xSpeed = -m_controller.getY(GenericHID.Hand.kLeft) * DriveTrain.kMaxSpeed;

    // Get the y speed or sideways/strafe speed. We are inverting this because
    // we want a positive value when we pull to the left. Xbox controllers
    // return positive values when you pull to the right by default.
    final var ySpeed = -m_controller.getX(GenericHID.Hand.kLeft) * DriveTrain.kMaxSpeed;

    // Get the rate of angular rotation. We are inverting this because we want a
    // positive value when we pull to the left (remember, CCW is positive in
    // mathematics). Xbox controllers return positive values when you pull to
    // the right by default.
    final var rot = -m_controller.getX(GenericHID.Hand.kRight) * DriveTrain.kMaxAngularSpeed;

    m_mecanum.drive(xSpeed, ySpeed, rot, fieldRelative);
  }
  private void aimWithVision(NetworkTable a){
    /*
    Used to configure the Joystick to respond to moving towards the specified tx target
    */
    float Kp = -0.1f;
    float min_command = 0.05f;
    //NetworkTable table = a.getTable("limelight");
    float tx = (float)a.getEntry("tx").getDouble(0.0);
    float left_command = 0;
    float right_command = 0;
    float steering_command = 0;
    float heading_error = -tx;
    if(tx > 1.0){
      steering_command = (Kp*heading_error) - min_command;
    }
    else if (tx < 1.0){
      steering_command = (Kp*heading_error) + min_command;
    }
    left_command+= steering_command;
    right_command-= steering_command;

  }
}