package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.Intake_Indexer;
import frc.robot.subsystems.*;      

public class RobotContainer {
   
   //intake
   private final Intake I_Intake = new Intake();
   //indexer
   private final indexer I_Indexer = new indexer();
   //shooter
   private final Shooter S_Shooter = new Shooter();

   public final Joystick manipulator = new Joystick(1);
   //intake
   //public final JoystickButton intakeIn = new JoystickButton(manipulator, XboxController.Button.kLeftBumper.value);
  // public final JoystickButton intakeOut = new JoystickButton(manipulator, XboxController.Button.kRightBumper.value);
   //public final JoystickButton intakeSlow = new JoystickButton(manipulator, XboxController.Button.kA.value);
   //indexer
   //public final JoystickButton indexerIn = new JoystickButton(manipulator, XboxController.Button.kB.value);
   //public final JoystickButton indexerOut = new JoystickButton(manipulator, XboxController.Button.kY.value);
   //shooter
   public final JoystickButton shooterRun = new JoystickButton(manipulator, XboxController.Button.kA.value);
   public final JoystickButton shooterReverse = new JoystickButton(manipulator, XboxController.Button.kX.value);
    
    //one button code
    private final Intake_Indexer intake_Indexer = new Intake_Indexer(I_Intake,I_Indexer,manipulator);
    
    public RobotContainer(){
      configureButtonBindings();
      configureDefaultCommands();
      
    }
    




     private void configureButtonBindings() {
         /* Manipulator Butoons */
         //intake
         //intakeIn.onTrue(new InstantCommand(() -> I_Intake.runIntake()));
        // intakeOut.onTrue(new InstantCommand(() -> I_Intake.reverseIntake()));
         //intakeIn.onFalse(new InstantCommand(() -> I_Intake.stopIntake()));
         //intakeOut.onFalse(new InstantCommand(() -> I_Intake.stopIntake())); 
         //intakeSlow.onTrue(new InstantCommand(() ->I_Intake.slowReverseIntake()));
        // intakeSlow.onFalse(new InstantCommand(() -> I_Intake.stopIntake()));
         //indexer
         //indexerIn.onTrue(new InstantCommand(() -> I_Indexer.runIndexer()));
         //indexerIn.onFalse(new InstantCommand(()-> I_Indexer.stopIndexer()));
         //indexerOut.onTrue(new InstantCommand(() -> I_Indexer.reverseIndexer()));
         //indexerOut.onFalse(new InstantCommand(()-> I_Indexer.stopIndexer())); 
         //shooter
         shooterRun.onTrue(new InstantCommand(() -> S_Shooter.runShooter()));
         shooterRun.onFalse(new InstantCommand(()-> S_Shooter.stopShooter()));
         shooterReverse.onTrue(new InstantCommand(() -> S_Shooter.reverseShooter()));
         shooterReverse.onFalse(new InstantCommand(()-> S_Shooter.stopShooter()));

     }  
     
     public void configureDefaultCommands(){
      I_Indexer.setDefaultCommand(intake_Indexer);
      I_Intake.setDefaultCommand(intake_Indexer);
     }
}