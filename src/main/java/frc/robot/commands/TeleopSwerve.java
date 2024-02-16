package frc.robot.commands;

import frc.robot.constants;
import frc.robot.limelightData;
import frc.robot.subsystems.Swerve;


import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Translation2d;

import edu.wpi.first.wpilibj2.command.Command;


public class TeleopSwerve extends Command {    
    private Swerve s_Swerve;    
    private DoubleSupplier translationSup;
    private DoubleSupplier strafeSup;
    private DoubleSupplier rotationSup;
    private BooleanSupplier robotCentricSup;
    private static boolean isAiming;


   

    public TeleopSwerve(Swerve s_Swerve, DoubleSupplier translationSup, DoubleSupplier strafeSup, DoubleSupplier rotationSup, BooleanSupplier robotCentricSup, BooleanSupplier speedSup, BooleanSupplier aiming) {
        this.s_Swerve = s_Swerve;
        addRequirements(s_Swerve);

        this.translationSup = translationSup;
        this.strafeSup = strafeSup;
        this.rotationSup = rotationSup;
        this.robotCentricSup = robotCentricSup;
        isAiming = aiming.getAsBoolean();
        
    }
    
    

    @Override
    public void execute() {
        double translationVal;
        double strafeVal;
        double rotationVal;
        

        if (isAiming) {
            // Use determined values
            translationVal = limelightData.XC;
            strafeVal = limelightData.YC;
            rotationVal = limelightData.ZC;
        } else {
            // Use joystick inputs with deadband applied
            translationVal = MathUtil.applyDeadband(translationSup.getAsDouble(), constants.stickDeadband);
            strafeVal = MathUtil.applyDeadband(strafeSup.getAsDouble(), constants.stickDeadband);
            rotationVal = MathUtil.applyDeadband(rotationSup.getAsDouble(), constants.stickDeadband);
        }

        // Drive
        s_Swerve.drive(
            new Translation2d(translationVal, strafeVal).times(constants.Swerve.maxSpeed), 
            rotationVal * constants.Swerve.maxAngularVelocity, 
            !robotCentricSup.getAsBoolean(), 
            true
        );
    }
}