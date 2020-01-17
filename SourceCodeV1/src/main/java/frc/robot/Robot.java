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
import frc.robot.Subsystems.*;


public class Robot extends TimedRobot {
  private final XboxController m_controller = new XboxController(0);
  private final DriveTrain m_mecanum = new DriveTrain();
  private final Timer timer01 = new Timer();
  private final Intake intake1 = new Intake();
 
  
  
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
    timer01.reset();
    timer01.start();
    driveWithJoystick(true);
    if(m_controller.getRawButton(1)){
      intake1.intakeBalls(0.6);
    }
    if(m_controller.getRawButton(2)){
      intake1.outtakeBalls(0.6);
    }
    
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