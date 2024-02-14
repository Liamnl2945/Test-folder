// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;


/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();
  private NetworkTable limelightTable;
  private static final double TAG_WIDTH_INCHES = 6.5;
  private static final double HORIZONTAL_FOV = 63.3;
  private static final double FOCAL_LENGTH = 17.5;

  public static CTREConfigs ctreConfigs;


  private double interval = 2.5;

  private RobotContainer m_robotContainer;

  public ShuffleboardTab limelightTab;

  Timer  rumbleTimer = new Timer();


  

  

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);
    limelightTable = NetworkTableInstance.getDefault().getTable("limelight");
    //rumbleTimer.start();
    
    
    ctreConfigs = new CTREConfigs();   

    m_robotContainer = new RobotContainer();

    
    
  

  }
  public void rumbleTask() {
    RobotContainer.manipulator.setRumble(RumbleType.kLeftRumble, 1.0);
    RobotContainer.manipulator.setRumble(RumbleType.kRightRumble, 1.0);
   }

     public void endRumbleTask() {
    RobotContainer.manipulator.setRumble(RumbleType.kLeftRumble, 0.0);
    RobotContainer.manipulator.setRumble(RumbleType.kRightRumble, 0.0);
   }

  /**
   * This function is called every 20 ms, no matter the mode. Use this for items like diagnostics
   * that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    /*if(rumbleTimer.hasElapsed(interval)){
      rumbleTask();
    }
    if(rumbleTimer.hasElapsed(interval*2)){
      endRumbleTask();
      rumbleTimer.reset();
    }*/
    

     CommandScheduler.getInstance().run();
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select between different
   * autonomous modes using the dashboard. The sendable chooser code works with the Java
   * SmartDashboard. If you prefer the LabVIEW Dashboard, remove all of the chooser code and
   * uncomment the getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to the switch structure
   * below with additional strings. If using the SendableChooser make sure to add them to the
   * chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
      case kCustomAuto:
        // Put custom auto code here
        break;
      case kDefaultAuto:
      default:
        // Put default auto code here
        break;
    }
  }

  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {

    //rumbleTimer.start();
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
        double[] targetPose = limelightTable.getEntry("targetpose_cameraspace").getDoubleArray(new double[6]);

        double targetX = limelightTable.getEntry("tx").getDouble(0.0);
        double targetY = limelightTable.getEntry("ty").getDouble(0.0);
        double targetA = limelightTable.getEntry("ta").getDouble(0.0);
        double targetS = limelightTable.getEntry("ts").getDouble(0.0);

        double targetXC = targetPose[0];
        double targetYC = targetPose[1];
        double targetZC = targetPose[2];
        double targetRoll = targetPose[3];
        double targetPitch = targetPose[4];
        double targetYaw = targetPose[5]; 

       System.out.println(Math.sqrt((Math.pow(targetXC, 2)) + Math.pow(targetZC, 2)));
       // System.out.println("Roll: " + targetRoll + "   Pitch: " + targetPitch + "   Yaw: " + targetYaw);
        //System.out.println("X: " + targetXC + "   Y: " + targetYC + "   Z: " + targetZC);
        //System.out.println("Distance to Tag: " + distance);
  }
  private double estimateDistance(double area, double targetRotation){
    double apparentWidthPixels = Math.sqrt(area);
    double adustedWidthPixels = apparentWidthPixels/Math.abs(Math.cos(Math.toRadians(targetRotation)));
    double distance = (TAG_WIDTH_INCHES * FOCAL_LENGTH)/(2*adustedWidthPixels*Math.tan(Math.toRadians(HORIZONTAL_FOV/2)));
    return distance;
  }

  /** This function is called once when the robot is disabled. */
  @Override
  public void disabledInit() {
    endRumbleTask();
  }

  /** This function is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {
    endRumbleTask();
  }

  /** This function is called once when test mode is enabled. */
  @Override
  public void testInit() {}

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}

  /** This function is called once when the robot is first started up. */
  @Override
  public void simulationInit() {}

  /** This function is called periodically whilst in simulation. */
  @Override
  public void simulationPeriodic() {}
}