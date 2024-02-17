package frc.robot.RotationLocker;

import java.util.function.DoubleSupplier;

import edu.wpi.first.math.MathUtil;
import frc.robot.RobotContainer;
import frc.robot.constants;

public class JoystickLock implements RotationSource {

    private DoubleSupplier translationSup;
    
    @Override
    public double getR() {
        
        return -MathUtil.applyDeadband(translationSup.getAsDouble(), constants.stickDeadband);
    }
}