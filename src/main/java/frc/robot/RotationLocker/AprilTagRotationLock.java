package frc.robot.RotationLocker;

import com.ctre.phoenix6.configs.Pigeon2Configuration;
import com.ctre.phoenix6.hardware.Pigeon2;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Rotation2d;
import frc.robot.constants;
import frc.robot.limelightData;
import frc.robot.subsystems.Swerve;



public class AprilTagRotationLock implements RotationSource {

    
    

    public PIDController rotationPID = createPIDController();
    private  PIDController createPIDController() {

        

        PIDController pid = new PIDController(.01, .02, .001);
        pid.setTolerance(.25); // allowable angle error
        pid.enableContinuousInput(0, 360); // it is faster to go 1 degree from 359 to 0 instead of 359 degrees
        pid.setSetpoint(0); // 0 = apriltag angle/offset
        return pid;
    }

    @Override
    public double getRd(double degreesOffset) {
        double calculatedValue = rotationPID.calculate(limelightData.targetYaw - degreesOffset);
        return calculatedValue;
    }

    @Override
    public double getR() {
        throw new UnsupportedOperationException("Unimplemented method 'getR'");
    }


}
