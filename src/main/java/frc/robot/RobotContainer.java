package frc.robot;

import java.util.List;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.commands.PathPlannerAuto;
import com.pathplanner.lib.path.GoalEndState;
import com.pathplanner.lib.path.PathConstraints;
import com.pathplanner.lib.path.PathPlannerPath;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.Intake_Indexer;
import frc.robot.commands.RunIndexerCommand;
import frc.robot.commands.RunShooter;
import frc.robot.commands.SetShooterSpeedByAprilTag;
import frc.robot.commands.TeleopSwerve;
import frc.robot.commands.climberCom;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Swerve;
import frc.robot.subsystems.indexer;      

public class RobotContainer {
   
   //intake
   private final Intake I_Intake = new Intake();
   //indexer
   private final indexer I_Indexer = new indexer();
   //shooter
   private final Shooter S_Shooter = new Shooter();
   //climber
   private final Climber C_Climber = new Climber();
   //Swerve 
   private final Swerve s_Swerve = new Swerve();
   //limelight
  
  //AUTOS 
    private final SendableChooser<Command> autoChooser;

  //ShuffleboardTab limelightTab; 

  //joysticks
  public final static Joystick driver = new Joystick(0);

  public final static Joystick manipulator = new Joystick(1);


    // Controller Buttons Used

        // **********************************************************
        // Xbox Controller - 1/Driver - Port 0
        // ----------------------------------------------------------
        // Left Stick (Translate along X and Y plane) - "Swerve"
        // Right Stick (Rotate about Z-Axis) - Drive Right/Left
        // Button Y - Zero the gyro 
        // Left Bumper - Robot Centric drive mode
        // Right Bumper - Speed throttle 
        //
        // **********************************************************

        // **********************************************************
        // Xbox Controller - 2/Operator - Port 1
        // ----------------------------------------------------------
        // Left Trigger -
        // Right Trigger -
        // Left Bumper
        // Button A - Normal Shoot
        // Button B - Slow Shoot
        // Button X - Reverse Shooter
        // Button Y - Aim limelight 
        // D-Pad up - intake & indexer in 
        // D-Pad Down - intake & indexer out
        // **********************************************************


   //Timer  rumbleTimer = new Timer();
   
  //Driver Buttons 
   //Driver Buttons 
  /* Drive Controls */
  private final int translationAxis = XboxController.Axis.kLeftY.value;
  private final int strafeAxis = XboxController.Axis.kLeftX.value;
  private final int rotationAxis = XboxController.Axis.kRightX.value;

  /* Driver Buttons */
  private final JoystickButton zeroGyro = new JoystickButton(driver, XboxController.Button.kY.value);
  private final JoystickButton robotCentric = new JoystickButton(driver, XboxController.Button.kX.value);
  public final static JoystickButton aim = new JoystickButton(driver, XboxController.Button.kRightBumper.value);
  public final static JoystickButton slowMove = new JoystickButton(driver, XboxController.Button.kLeftBumper.value);

 

  //intake
  //public final JoystickButton intakeIn = new JoystickButton(manipulator, XboxController.Button.kLeftBumper.value);
  // public final JoystickButton intakeOut = new JoystickButton(manipulator, XboxController.Button.kRightBumper.value);
  //public final JoystickButton intakeSlow = new JoystickButton(manipulator, XboxController.Button.kA.value);

  //indexer
  //public final JoystickButton indexerIn = new JoystickButton(manipulator, XboxController.Button.kB.value);
  //public final JoystickButton indexerOut = new JoystickButton(manipulator, XboxController.Button.kY.value);

   //shooter

  //public final JoystickButton shooterRun = new JoystickButton(manipulator, XboxController.Button.kA.value);
  public final JoystickButton shooterReverse = new JoystickButton(manipulator, XboxController.Button.kB.value);
  public final JoystickButton slowShoot = new JoystickButton(manipulator, XboxController.Button.kX.value);
  public final JoystickButton ShootLimeLight = new JoystickButton(manipulator, XboxController.Button.kY.value);
  //private RotationSource hijackableRotation = new JoystickLock(); // get rotation from driver input;

  

    
  //one button code
  private final Intake_Indexer intake_Indexer = new Intake_Indexer(I_Intake,I_Indexer,driver);
  private final climberCom climberCommand = new climberCom(C_Climber, manipulator);
  private final SetShooterSpeedByAprilTag ShooterAprilTags = new SetShooterSpeedByAprilTag(S_Shooter);
  private final RunIndexerCommand RunIndexerCommand = new RunIndexerCommand(I_Indexer);
  private final RunShooter RunShooter = new RunShooter(S_Shooter);


    
    public RobotContainer(){

      // Build an auto chooser. This will use Commands.none() as the default option.
      //autoChooser = AutoBuilder.buildAutoChooser();
      
      
      NamedCommands.registerCommand("SHOOTER RUN", RunShooter);
      NamedCommands.registerCommand("Intake & Indexer", intake_Indexer);
      NamedCommands.registerCommand("indexer ONLY", RunIndexerCommand);
      NamedCommands.registerCommand("Shooter Lock", ShooterAprilTags);
      NamedCommands.registerCommand("Lock PID",  new TeleopSwerve(
              s_Swerve, 
              () -> -driver.getRawAxis(translationAxis), 
              () -> -driver.getRawAxis(strafeAxis), 
              () -> -driver.getRawAxis(rotationAxis), 
              () -> robotCentric.getAsBoolean(),
              () -> aim.getAsBoolean()
              ) );


      
      configureBindings();

      autoChooser = AutoBuilder.buildAutoChooser(); // Default auto will be `Commands.none()`
      SmartDashboard.putData("Auto Mode", autoChooser);

      // Another option that allows you to specify the default auto by its name
      // autoChooser = AutoBuilder.buildAutoChooser("My Default Auto");

    
   
      //JoystickButton shooterButton = new JoystickButton(manipulator, XboxController.Button.kA.value);
      //shooterButton.whileTrue(new SetShooterSpeedByAprilTag(S_Shooter));
      //shooterButton.whileFalse(new InstantCommand(()-> S_Shooter.stopShooter()));


      
      // Register Named Commands
        //NamedCommands.registerCommand("autoBalance", s_Swerve.ExamplePath());
        


        s_Swerve.setDefaultCommand(
          new TeleopSwerve(
              s_Swerve, 
              () -> -driver.getRawAxis(translationAxis), 
              () -> -driver.getRawAxis(strafeAxis), 
              () -> -driver.getRawAxis(rotationAxis), 
              () -> robotCentric.getAsBoolean(),
              () -> aim.getAsBoolean()
          ) 
      );

        

     
      configureButtonBindings();
      configureDefaultCommands();

      
    //limelightTab = Shuffleboard.getTab("Limelight Tab");
    //L_Limelight.configLimelightTab(limelightTab);
    //L_Limelight.getShuffleboardValues();
    
      

      
    }
    private void configureBindings() {

       /* AUTO STUFF  */
      SmartDashboard.putData("Blue Auto Score Twice", new PathPlannerAuto("Blue 2 Auto"));
      SmartDashboard.putData("Test", new PathPlannerAuto("Test"));
      SmartDashboard.putData("Example", new PathPlannerAuto("New Auto"));

      // Add a button to run the example auto to SmartDashboard, this will also be in the auto chooser built above
    SmartDashboard.putData("Example Auto", new PathPlannerAuto("New Auto"));

    // Add a button to run pathfinding commands to SmartDashboard
    SmartDashboard.putData("Pathfind to Pickup Pos", AutoBuilder.pathfindToPose(
      new Pose2d(14.0, 6.5, Rotation2d.fromDegrees(0)), 
      new PathConstraints(
        4.0, 4.0, 
        Units.degreesToRadians(360), Units.degreesToRadians(540)
      ), 
      0, 
      2.0
    ));
    SmartDashboard.putData("Pathfind to Scoring Pos", AutoBuilder.pathfindToPose(
      new Pose2d(2.15, 3.0, Rotation2d.fromDegrees(180)), 
      new PathConstraints(
        4.0, 4.0, 
        Units.degreesToRadians(360), Units.degreesToRadians(540)
      ), 
      0, 
      0
    ));

    // Add a button to SmartDashboard that will create and follow an on-the-fly path
    // This example will simply move the robot 2m in the +X field direction
    SmartDashboard.putData("On-the-fly path", Commands.runOnce(() -> {
      Pose2d currentPose = s_Swerve.getPose();
      
      // The rotation component in these poses represents the direction of travel
      Pose2d startPos = new Pose2d(currentPose.getTranslation(), new Rotation2d());
      Pose2d endPos = new Pose2d(currentPose.getTranslation().plus(new Translation2d(2.0, 0.0)), new Rotation2d());

      List<Translation2d> bezierPoints = PathPlannerPath.bezierFromPoses(startPos, endPos);
      PathPlannerPath path = new PathPlannerPath(
        bezierPoints, 
        new PathConstraints(
          4.0, 4.0, 
          Units.degreesToRadians(360), Units.degreesToRadians(540)
        ),  
        new GoalEndState(0.0, currentPose.getRotation())
      );

      // Prevent this path from being flipped on the red alliance, since the given positions are already correct
      path.preventFlipping = true;

      AutoBuilder.followPath(path).schedule();
    }));
  }



      // Add a button to run pathfinding commands to SmartDashboard


    
    



     /**
     * Use this method to define your button->command mappings. Buttons can be created by
     * instantiating a {@link GenericHID} or one of its subclasses ({@link
     * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
     * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
     private void configureButtonBindings() {
     
        /* Driver BUTTons */

        zeroGyro.onTrue(new InstantCommand(() -> s_Swerve.zeroHeading()));
         /* Manipulator Butoons */


         /* intake */
          //intakeIn.onTrue(new InstantCommand(() -> I_Intake.runIntake()));
          //intakeOut.onTrue(new InstantCommand(() -> I_Intake.reverseIntake()));
          //intakeIn.onFalse(new InstantCommand(() -> I_Intake.stopIntake()));
          //intakeOut.onFalse(new InstantCommand(() -> I_Intake.stopIntake())); 
          //intakeSlow.onTrue(new InstantCommand(() ->I_Intake.slowReverseIntake()));
          //intakeSlow.onFalse(new InstantCommand(() -> I_Intake.stopIntake()));

         /* indexer */
          //indexerIn.onTrue(new InstantCommand(() -> I_Indexer.runIndexer()));
          //indexerIn.onFalse(new InstantCommand(()-> I_Indexer.stopIndexer()));
          //indexerOut.onTrue(new InstantCommand(() -> I_Indexer.reverseIndexer()));
          //indexerOut.onFalse(new InstantCommand(()-> I_Indexer.stopIndexer())); 

           /* shooter */
          //shooterRun.onTrue(new InstantCommand(() -> S_Shooter.runShooter()));
          //shooterRun.onFalse(new InstantCommand(()-> S_Shooter.stopShooter()));
          shooterReverse.onTrue(new InstantCommand(() -> S_Shooter.reverseShooter()));
          shooterReverse.onFalse(new InstantCommand(()-> S_Shooter.stopShooter()));
          slowShoot.onTrue(new InstantCommand(()-> S_Shooter.slowShoot()));
          slowShoot.onFalse(new InstantCommand(()-> S_Shooter.stopShooter()));


          
          //new JoystickButton(driver, XboxController.Button.kY.value)
          //.onTrue(new InstantCommand(() -> hijackableRotation =  new AprilTagLock()))
          //.onFalse(new InstantCommand(() -> hijackableRotation = new JoystickLock()));

     }  
     
     public void configureDefaultCommands(){
      I_Indexer.setDefaultCommand(intake_Indexer);
      I_Intake.setDefaultCommand(intake_Indexer);
      C_Climber.setDefaultCommand(climberCommand);
      S_Shooter.setDefaultCommand(ShooterAprilTags);
     }
     public Command getAutonomousCommand() {
      return autoChooser.getSelected();

    }
}