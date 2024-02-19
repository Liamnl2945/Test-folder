package frc.robot.commands;

import frc.robot.constants;
import frc.robot.limelightData;
import frc.robot.RotationLocker.AprilTagPointLock;
import frc.robot.RotationLocker.AprilTagRotationLock;
import frc.robot.subsystems.Swerve;


import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Translation2d;

import edu.wpi.first.wpilibj2.command.Command;


public class TeleopSwerve extends Command {  
    
    AprilTagPointLock rotationPIDAprilTagPointLock;
    AprilTagRotationLock rotationPIDAprilTagRotationLock;

    private Swerve s_Swerve;    
    private DoubleSupplier translationSup;
    private DoubleSupplier strafeSup;
    private DoubleSupplier rotationSup;
    private BooleanSupplier robotCentricSup;
    private boolean isAiming;
    private double translationVal;
    private double strafeVal;
    private double rotationVal;


   

    public TeleopSwerve(Swerve s_Swerve, DoubleSupplier translationSup, DoubleSupplier strafeSup, 
    DoubleSupplier rotationSup, BooleanSupplier robotCentricSup, BooleanSupplier speedSup, BooleanSupplier aiming) {
        this.s_Swerve = s_Swerve;
        addRequirements(s_Swerve);

        this.translationSup = translationSup;
        this.strafeSup = strafeSup;
        this.rotationSup = rotationSup;
        this.robotCentricSup = robotCentricSup;
        this.isAiming = aiming.getAsBoolean();
        
    }
    
    

    @Override
    public void execute() {

        
/*
        if (isAiming = true) {
            // Use determined values
            translationVal = limelightData.needTranslate;
            strafeVal = limelightData.needStrafe;
            rotationVal = limelightData.needRotate;
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
        */




        if (limelightData.targetValid && isAiming) {
            if (limelightData.tagID == 4 || limelightData.tagID == 7) {//speaker tags, logic to point at tags
                     translationVal = MathUtil.applyDeadband(translationSup.getAsDouble(), constants.stickDeadband);//maintain strafe & translate driver control
                     strafeVal = MathUtil.applyDeadband(strafeSup.getAsDouble(), constants.stickDeadband);
                     rotationVal = rotationPIDAprilTagPointLock.getR();

            } else if (limelightData.tagID == 5 || limelightData.tagID == 6) {//amp tags, logic to lock roation to tags
                     translationVal = MathUtil.applyDeadband(translationSup.getAsDouble(), constants.stickDeadband);//maintain strafe & translate driver control
                     strafeVal = MathUtil.applyDeadband(strafeSup.getAsDouble(), constants.stickDeadband);                    
                     rotationVal = rotationPIDAprilTagRotationLock.getR();
            }
        } else {//standard swerve teleop
            // Standard teleop control logic (joystick inputs)
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