package frc.robot.subsystems;

import com.ctre.phoenix.led.CANdle;
import com.ctre.phoenix.led.RainbowAnimation;
import com.ctre.phoenix.led.LarsonAnimation.BounceMode;
import com.ctre.phoenix.led.LarsonAnimation;
import com.ctre.phoenix.led.FireAnimation;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LEDSubsystem extends SubsystemBase{
    // Define CANdle device
    private CANdle candle = new CANdle(20);

    // Create a rainbow animation
    //rAiNbOwS

    private static final RainbowAnimation rainbowAnimation = new RainbowAnimation(1, 1, 0);
    private static final FireAnimation fireAnimation = new FireAnimation(1, 1, 0, 0.5, 0.8);
    private static final LarsonAnimation larsonAnimation = new LarsonAnimation(255, 255, 255, 3, 1, /*TODO SET*/3, BounceMode.Front, 3, 1);

    
    public LEDSubsystem() {
        // Set the animation for the CANdle device
        candle.animate(fireAnimation);
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
    }
}