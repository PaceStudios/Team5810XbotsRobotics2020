/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;


import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.*;
import frc.robot.Subsystems.*;


public class Robot extends TimedRobot {
  private final XboxController m_controller = new XboxController(Constants.XBOXCONTROL_PORT);
  private final Joystick m_joystick = new Joystick(Constants.JOYSTICK_PORT);
  private final DriveTrain m_mecanum = new DriveTrain();
  private final Timer timer01 = new Timer();
  public Robot(){

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
    /*
    timer01.reset();              // Resets the timer for the round on the RoboRio
    timer01.start();              // Starts the timer on the RoboRio
    driveWithXboxControl(true);      // Sets the method for driving in TeleOp, was modified to work with an Xbox Controller 
    if (m_joystick.getRawButton(1)){  // Method working with the Intake and Outtake for the Robot (Will be Eventually modified)
      intake1.setEngaged(true);
      intake1.intakeBalls(0.8, true);
    }
    else
    {
      intake1.setEngaged(false);
    }
    if (m_joystick.getRawButton(2)){
      intake1.outtakeBalls(0.8, true);
    }
    if(m_controller.getRawButton(1)){ // Red Button 'A'. Toggles itself on or off based on button press. 
      isAimOn = !isAimOn;
      while(isAimOn){
        aimWithVision(limelight.getTable());
      }
    }
    
    if(m_joystick.getRawButton(7)){
      align.moveUp(2);
    }
    if(m_joystick.getRawButton(10)){
      align.moveDown(2);
    }
    if(m_joystick.getRawButton(8)){
      align.moveLeft(2);
    }
    if(m_joystick.getRawButton(11)){
      align.moveRight(2);
    }
    if(m_joystick.getRawButton(9)){
      align.moveUp(0.5);
    }
    if(m_joystick.getRawButton(12)){
      align.moveDown(0.5);
    }
    if(m_joystick.getRawButton(5)){
      align.moveLeft(0.5);
    }
    if(m_joystick.getRawButton(6)){
      align.moveRight(0.5);
    }
    */
  }
  @Override
  public void disabledInit(){
    super.disabledInit();
  }
  @Override
  public void testInit(){
    super.testInit();
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
}