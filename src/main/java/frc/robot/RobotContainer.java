package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

import frc.robot.subsystems.*;      

public class RobotContainer {

    private final Intake I_Intake = new Intake();
    private final indexer I_Indexer = new indexer();

    private final Joystick manipulator = new Joystick(1);
    
    public final JoystickButton intakeIn = new JoystickButton(manipulator, XboxController.Button.kLeftBumper.value);
    public final JoystickButton intakeOut = new JoystickButton(manipulator, XboxController.Button.kRightBumper.value);
    public final JoystickButton intakeSlow = new JoystickButton(manipulator, XboxController.Button.kA.value);
    public final JoystickButton indexerIn = new JoystickButton(manipulator, XboxController.Button.kB.value);
    public final JoystickButton indexerOut = new JoystickButton(manipulator, XboxController.Button.kY.value);

    

    




     private void configureButtonBindings() {
        /* Manipulator Butoons */
        intakeIn.onTrue(new InstantCommand(() -> I_Intake.runIntake()));
        intakeOut.onTrue(new InstantCommand(() -> I_Intake.reverseIntake()));
        intakeIn.onFalse(new InstantCommand(() -> I_Intake.stopIntake()));
        intakeOut.onFalse(new InstantCommand(() -> I_Intake.stopIntake()));
        intakeSlow.onTrue(new InstantCommand(() ->I_Intake.slowReverseIntake()));
        intakeSlow.onFalse(new InstantCommand(() -> I_Intake.stopIntake()));
        indexerIn.onTrue(new InstantCommand(() -> I_Indexer.runIndexer()));
        indexerOut.onTrue(new InstantCommand(() -> I_Indexer.reverseIndexer()));
    
     }   
}
