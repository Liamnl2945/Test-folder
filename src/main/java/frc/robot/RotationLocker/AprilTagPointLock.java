package frc.robot.RotationLocker;

import edu.wpi.first.math.controller.PIDController;
import frc.robot.limelightData;

public class AprilTagPointLock implements RotationSource {

    public static PIDController rotationPID = createPIDController();
    private static PIDController createPIDController() {
        PIDController pid = new PIDController(.01, .02, .001);
        pid.setTolerance(.25); // allowable angle error
        pid.enableContinuousInput(0, 360); // it is faster to go 1 degree from 359 to 0 instead of 359 degrees
        pid.setSetpoint(0); // 0 = apriltag angle/offset
        return pid;
    }
    @Override
    public double getR() {
        double calculatedValue = rotationPID.calculate(limelightData.targetXOffset);
        return calculatedValue;
    }

    
    @Override
    public double getRd(double degrees) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getRd'");
    }
}
