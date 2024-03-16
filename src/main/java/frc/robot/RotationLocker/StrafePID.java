package frc.robot.RotationLocker;

import edu.wpi.first.math.controller.PIDController;
import frc.robot.limelightData;

public class StrafePID {
    
    public static PIDController strafePID = createPIDController();
    private static PIDController createPIDController() {
        PIDController pid = new PIDController(0.008, 0.0004, 0.0001);
        pid.setTolerance(8); // allowable angle error
        pid.enableContinuousInput(0, 360); // it is faster to go 1 degree from 359 to 0 instead of 359 degrees
        pid.setSetpoint(0); // 0 = apriltag angle/offset
        return pid;
    }
  
    public double getS() {
        double calculatedValue = strafePID.calculate(-limelightData.targetYaw);
        System.out.println(calculatedValue);
        return calculatedValue;
    }

}
