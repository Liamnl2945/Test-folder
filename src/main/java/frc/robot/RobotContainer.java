package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

import frc.robot.subsystems.*;      

public class RobotContainer {
   
   //intake
   private final Intake I_Intake = new Intake();
   //indexer
   private final indexer I_Indexer = new indexer();
   //shooter
   private final Shooter S_Shooter = new Shooter();

   private final Joystick manipulator = new Joystick(1);
   //intake
   public final JoystickButton intakeIn = new JoystickButton(manipulator, XboxController.Button.kLeftBumper.value);
   public final JoystickButton intakeOut = new JoystickButton(manipulator, XboxController.Button.kRightBumper.value);
   public final JoystickButton intakeSlow = new JoystickButton(manipulator, XboxController.Button.kA.value);
   //indexer
   public final JoystickButton indexerIn = new JoystickButton(manipulator, XboxController.Button.kB.value);
   public final JoystickButton indexerOut = new JoystickButton(manipulator, XboxController.Button.kY.value);
   //shooter
   public final JoystickButton shooterRun = new JoystickButton(manipulator, XboxController.Button.kStart.value);
   public final JoystickButton shooterReverse = new JoystickButton(manipulator, XboxController.Button.kBack.value);
    

    
    public RobotContainer(){
      configureButtonBindings();
      
    }
    




     private void configureButtonBindings() {
         /* Manipulator Butoons */
         //intake
         intakeIn.onTrue(new InstantCommand(() -> I_Intake.runIntake()));
         intakeOut.onTrue(new InstantCommand(() -> I_Intake.reverseIntake()));
         intakeIn.onFalse(new InstantCommand(() -> I_Intake.stopIntake()));
         intakeOut.onFalse(new InstantCommand(() -> I_Intake.stopIntake()));
         intakeSlow.onTrue(new InstantCommand(() ->I_Intake.slowReverseIntake()));
         intakeSlow.onFalse(new InstantCommand(() -> I_Intake.stopIntake()));
         //indexer
         indexerIn.onTrue(new InstantCommand(() -> I_Indexer.runIndexer()));
         indexerOut.onTrue(new InstantCommand(() -> I_Indexer.reverseIndexer()));
         //shooter
         shooterRun.onTrue(new InstantCommand(() -> S_Shooter.runShooter()));
         shooterReverse.onTrue(new InstantCommand(() -> S_Shooter.reverseShooter()));
     }   
}