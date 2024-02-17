package frc.robot.subsystems;

import frc.robot.SwerveModule;
import frc.robot.constants;


import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModulePosition;

import com.ctre.phoenix6.configs.Pigeon2Configuration;
import com.ctre.phoenix6.hardware.Pigeon2;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.commands.FollowPathHolonomic;
import com.pathplanner.lib.path.PathPlannerPath;
import com.pathplanner.lib.util.HolonomicPathFollowerConfig;
import com.pathplanner.lib.util.PIDConstants;
import com.pathplanner.lib.util.ReplanningConfig;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Swerve extends SubsystemBase {
    public SwerveDriveOdometry swerveOdometry;
    public SwerveModule[] mSwerveMods;
    public Pigeon2 gyro;

    public Swerve() {
        gyro = new Pigeon2(constants.Swerve.pigeonID);
        gyro.getConfigurator().apply(new Pigeon2Configuration());
        gyro.setYaw(0);


        mSwerveMods = new SwerveModule[] {
            new SwerveModule(0, constants.Swerve.Mod0.constants),
            new SwerveModule(1, constants.Swerve.Mod1.constants),
            new SwerveModule(2, constants.Swerve.Mod2.constants),
            new SwerveModule(3, constants.Swerve.Mod3.constants)
        };
        swerveOdometry = new SwerveDriveOdometry(constants.Swerve.swerveKinematics, getGyroYaw(), getModulePositions());


         AutoBuilder.configureHolonomic(
            this::getPose,
            this::resetPose,
            this::getSpeeds,
            this::driveRobotRelative,
            new HolonomicPathFollowerConfig(                     
                    new PIDConstants(0.05, 0.0, 0.0), // Translation PID constants
                    new PIDConstants(0.05, 0.0, 0.0), // Rotation PID constants
                    4.5, // Max module speed, in m/s
                    0.4, // Drive base radius in meters. Distance from robot center to furthest module.
                    new ReplanningConfig() // Default path replanning config. See the API for the options here
             ), // Adjust config values as needed
            () -> { // Boolean supplier that controls when the path will be mirrored for the red alliance
              // This will flip the path being followed to the red side of the field.
              // THE ORIGIN WILL REMAIN ON THE BLUE SIDE

              var alliance = DriverStation.getAlliance();
              if (alliance.isPresent()) {
                return alliance.get() == DriverStation.Alliance.Red;
              }
              return false;
             }, // Mirroring logic for red alliance
            this
        );
    }

   public void drive(Translation2d translation, double rotation, boolean fieldRelative, boolean isOpenLoop) {
    SwerveModuleState[] swerveModuleStates = 
        constants.Swerve.swerveKinematics.toSwerveModuleStates(
            fieldRelative ? ChassisSpeeds.fromFieldRelativeSpeeds(
                    translation.getX(),
                    translation.getY(),
                    rotation,
                    getHeading()
                )
                : new ChassisSpeeds(
                        translation.getX(),
                        translation.getY(),
                        rotation)
                );
    SwerveDriveKinematics.desaturateWheelSpeeds(swerveModuleStates, constants.Swerve.maxSpeed);

    for (SwerveModule mod : mSwerveMods) {
        mod.setDesiredState(swerveModuleStates[mod.moduleNumber], isOpenLoop);
    }
} 

 /* Used by SwerveControllerCommand in Auto */
 public void setModuleStates(SwerveModuleState[] desiredStates) {
    SwerveDriveKinematics.desaturateWheelSpeeds(desiredStates, constants.Swerve.maxSpeed);
    
    for(SwerveModule mod : mSwerveMods){
        mod.setDesiredState(desiredStates[mod.moduleNumber], false);
    }
}

public SwerveModuleState[] getModuleStates(){
    SwerveModuleState[] states = new SwerveModuleState[4];
    for(SwerveModule mod : mSwerveMods){
        states[mod.moduleNumber] = mod.getState();
    }
    return states;
}

public SwerveModulePosition[] getModulePositions(){
    SwerveModulePosition[] positions = new SwerveModulePosition[4];
    for(SwerveModule mod : mSwerveMods){
        positions[mod.moduleNumber] = mod.getPosition();
    }
    return positions;
}

public Pose2d getPose() {
    return swerveOdometry.getPoseMeters();
}

public void setPose(Pose2d pose) {
    swerveOdometry.resetPosition(getGyroYaw(), getModulePositions(), pose);
}

public Rotation2d getHeading(){
    return getPose().getRotation();
}

public void setHeading(Rotation2d heading){
    swerveOdometry.resetPosition(getGyroYaw(), getModulePositions(), new Pose2d(getPose().getTranslation(), heading));
}

public void zeroHeading(){
    swerveOdometry.resetPosition(getGyroYaw(), getModulePositions(), new Pose2d(getPose().getTranslation(), new Rotation2d()));
}

public Rotation2d getGyroYaw() {
    return Rotation2d.fromDegrees(gyro.getYaw().getValue());
}

public void resetModulesToAbsolute(){
    for(SwerveModule mod : mSwerveMods){
        mod.resetToAbsolute();
    }
}

     public ChassisSpeeds getButton(Translation2d translation, double rotation, boolean fieldRelative,boolean halfSpeed){
        if(fieldRelative && halfSpeed){
           return ChassisSpeeds.fromFieldRelativeSpeeds(
                (translation.getX()/3), 
                (translation.getY()/3),
                (rotation/3), 
                getGyroYaw()
                );
        }
    else if(!fieldRelative && halfSpeed){
            return new ChassisSpeeds(
                (translation.getX()/3), 
                (translation.getY()/3),
                (rotation/3)
            );
        }
    else if(fieldRelative && !halfSpeed){
        return ChassisSpeeds.fromFieldRelativeSpeeds(
                        translation.getX(), 
                        translation.getY(), 
                        rotation, 
                        getGyroYaw()
                    );
        }
    else{
        return new ChassisSpeeds(
                        translation.getX(), 
                        translation.getY(), 
                        rotation);
        }
    }
    public void resetPose(Pose2d pose) {
        swerveOdometry.resetPosition(getGyroYaw(), getModulePositions(), pose);
    }

    public ChassisSpeeds getRobotRelativeSpeeds(ChassisSpeeds fieldRelativeSpeeds) {
        return ChassisSpeeds.fromFieldRelativeSpeeds(
            fieldRelativeSpeeds.vxMetersPerSecond,
            fieldRelativeSpeeds.vyMetersPerSecond,
            fieldRelativeSpeeds.omegaRadiansPerSecond,
            getHeading()
        );
    }
    public ChassisSpeeds getSpeeds() {
        return constants.Swerve.swerveKinematics.toChassisSpeeds(getModuleStates());
      }
    

    public void driveRobotRelative(ChassisSpeeds robotRelativeSpeeds) {
        SwerveModuleState[] moduleStates = 
            constants.Swerve.swerveKinematics.toSwerveModuleStates(robotRelativeSpeeds);
        // ... rest of your drive logic ...
    }
    
    

    

    

    @Override
    public void periodic(){
        swerveOdometry.update(getGyroYaw(), getModulePositions());  

        for(SwerveModule mod : mSwerveMods){
            SmartDashboard.putNumber("Mod " + mod.moduleNumber + " Cancoder", mod.getCANcoder().getDegrees());
            SmartDashboard.putNumber("Mod " + mod.moduleNumber + " Integrated", mod.getPosition().angle.getDegrees());
            SmartDashboard.putNumber("Mod " + mod.moduleNumber + " Velocity", mod.getState().speedMetersPerSecond);    
        }
    }

    public Command ExamplePath() {

        PathPlannerPath path = PathPlannerPath.fromPathFile("Example Path");

        return new FollowPathHolonomic(
                path,
                this::getPose,
                this::getSpeeds,
                this::driveRobotRelative,
                new HolonomicPathFollowerConfig(                     
                    new PIDConstants(0.05, 0.0, 0.0), // Translation PID constants
                    new PIDConstants(0.05, 0.0, 0.0), // Rotation PID constants
                    4.5, // Max module speed, in m/s
                    0.4, // Drive base radius in meters. Distance from robot center to furthest module.
                    new ReplanningConfig() // Default path replanning config. See the API for the options here
                ),
            () -> {
                    // Boolean supplier that controls when the path will be mirrored for the red alliance
                    // This will flip the path being followed to the red side of the field.
                    // THE ORIGIN WILL REMAIN ON THE BLUE SIDE

                    var alliance = DriverStation.getAlliance();
                    if (alliance.isPresent()) {
                        return alliance.get() == DriverStation.Alliance.Red;
                    }
                    return false;
                },
                this // Reference to this subsystem to set requirements
        );

    }
}