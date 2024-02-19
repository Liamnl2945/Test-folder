package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;

import com.pathplanner.lib.auto.AutoBuilder;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.Intake_Indexer;
import frc.robot.commands.SetShooterSpeedByAprilTag;
import frc.robot.commands.TeleopSwerve;
import frc.robot.commands.climberCom;
import frc.robot.subsystems.*;      

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
  /* Drive Controls */
  private final int translationAxis = XboxController.Axis.kLeftY.value;
  private final int strafeAxis = XboxController.Axis.kLeftX.value;
  private final int rotationAxis = XboxController.Axis.kRightX.value;

  /* Driver Buttons */
  private final JoystickButton zeroGyro = new JoystickButton(driver, XboxController.Button.kY.value);
  private final JoystickButton robotCentric = new JoystickButton(driver, XboxController.Button.kLeftBumper.value);
  private final JoystickButton speedThrottle = new JoystickButton(driver, XboxController.Button.kRightBumper.value);
 

  //intake
  //public final JoystickButton intakeIn = new JoystickButton(manipulator, XboxController.Button.kLeftBumper.value);
  // public final JoystickButton intakeOut = new JoystickButton(manipulator, XboxController.Button.kRightBumper.value);
  //public final JoystickButton intakeSlow = new JoystickButton(manipulator, XboxController.Button.kA.value);

  //indexer
  //public final JoystickButton indexerIn = new JoystickButton(manipulator, XboxController.Button.kB.value);
  //public final JoystickButton indexerOut = new JoystickButton(manipulator, XboxController.Button.kY.value);

  //shooter

  public final JoystickButton shooterRun = new JoystickButton(manipulator, XboxController.Button.kLeftBumper.value);
  public final JoystickButton shooterReverse = new JoystickButton(manipulator, XboxController.Button.kX.value);
  public final JoystickButton slowShoot = new JoystickButton(manipulator, XboxController.Button.kB.value);
  public final JoystickButton aim = new JoystickButton(manipulator, XboxController.Button.kRightBumper.value);
  public final JoystickButton ShootLimeLight = new JoystickButton(manipulator, XboxController.Button.kA.value);
  //private RotationSource hijackableRotation = new JoystickLock(); // get rotation from driver input;

  

    
  //one button code
  private final Intake_Indexer intake_Indexer = new Intake_Indexer(I_Intake,I_Indexer,manipulator);
  private final climberCom climberCommand = new climberCom(C_Climber, manipulator);
  private final SetShooterSpeedByAprilTag ShooterAprilTags = new SetShooterSpeedByAprilTag(S_Shooter);

    
    public RobotContainer(){
      System.out.println("Container Created");
      
      // Register Named Commands
       // NamedCommands.registerCommand("autoBalance", s_Swerve.ExamplePath());
        


      s_Swerve.setDefaultCommand(
            new TeleopSwerve(
                s_Swerve, 
                () -> -driver.getRawAxis(translationAxis), 
                () -> -driver.getRawAxis(strafeAxis), 
                () -> -driver.getRawAxis(rotationAxis), 
                () -> robotCentric.getAsBoolean(),
                () -> speedThrottle.getAsBoolean(),
                () -> aim.getAsBoolean()
            ) 
        );
              System.out.println("swerve initialized");

        

      // Build an auto chooser. This will use Commands.none() as the default option.
      autoChooser = AutoBuilder.buildAutoChooser();

      SmartDashboard.putData("Auto Chooser", autoChooser);
      configureButtonBindings();
      configureDefaultCommands();
            System.out.println("Buttons configured");


    //limelightTab = Shuffleboard.getTab("Limelight Tab");
    //L_Limelight.configLimelightTab(limelightTab);
    //L_Limelight.getShuffleboardValues();
    }
    



     /**
     * Use this method to define your button->command mappings. Buttons can be created by
     * instantiating a {@link GenericHID} or one of its subclasses ({@link
     * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
     * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
     private void configureButtonBindings() {
            System.out.println("Configuring Buttons");

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
          shooterRun.onTrue(new InstantCommand(() -> S_Shooter.runShooter()));
          shooterRun.onFalse(new InstantCommand(()-> S_Shooter.stopShooter()));
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

      // Load the path you want to follow using its name in the GUI
        //PathPlannerPath path = PathPlannerPath.fromPathFile("Example Path");

      // Create a path following command using AutoBuilder. This will also trigger event markers.

        return autoChooser.getSelected();
    }
}