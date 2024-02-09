package frc.robot.subsystems.LimeLight;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.HttpCamera;
import edu.wpi.first.cscore.HttpCamera.HttpCameraKind;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

public class LimeLight {




    //TEST 
    
    //limelight
    private HttpCamera limelightCamera;
    private NetworkTable limelightTable;
    private NetworkTableEntry tx, ty;
    private double x, y;

    //adjustment fields
    private double angleError, distanceError;
    private double KpAiming, KpDistance;
    private double minCommand;
    private double steeringAdjust, distanceAdjust;
    private double leftCommand, rightCommand;

    //shuffleboard
    GenericEntry lime_KpAiming, lime_KpDistance, lime_minCommand;

    public LimeLight() {
        //define limelight network table + get offset values
        limelightTable = NetworkTableInstance.getDefault().getTable("limelight");
        tx = limelightTable.getEntry("tx");
        ty = limelightTable.getEntry("ty");

        x = tx.getDouble(0.0);
        y = ty.getDouble(0.0);
    }

    public void configLimelightTab(ShuffleboardTab limelightTab) {
        limelightCamera = new HttpCamera("Limelight Camera", "http://10.29.45.11:5800/stream.mjpg", HttpCameraKind.kMJPGStreamer);
        limelightCamera.setResolution(320, 240);
        limelightCamera.setFPS(30);

        CameraServer.startAutomaticCapture(limelightCamera);

        limelightTab.add("Limelight Cam 1", limelightCamera);
        limelightTab.addCamera("Limelight Cam 2", "limelight", "http://10.29.45.11:5800/stream.mjpg");

        limelightTab.addDouble("Limelight tx", () -> x);
        limelightTab.addDouble("Limelight ty", () -> y);

        lime_KpAiming = limelightTab.add("Kp Aiming", -0.02).getEntry();
        lime_KpDistance = limelightTab.add("Kp Distance", -0.02).getEntry();
        lime_minCommand = limelightTab.add("Min Command (Angle)", 0.2).getEntry();
    }

    public void getShuffleboardValues() {
        KpAiming = lime_KpAiming.getDouble(-0.02);
        KpDistance = lime_KpDistance.getDouble(-0.02);
        minCommand = lime_minCommand.getDouble(0.2);
    }

    public void angleAdjust() {
        angleError = -x;
        steeringAdjust = 0.0;

        leftCommand = 0.0;
        rightCommand = 0.0;

        steeringAdjust = KpAiming * angleError;

        if (x < -0.5) {
            steeringAdjust -= minCommand;
            leftCommand += steeringAdjust;
            rightCommand -= steeringAdjust;
        } else if (x > 0.5) {
            steeringAdjust += minCommand;
            rightCommand += steeringAdjust;
            leftCommand -= steeringAdjust;
        }
    }

    public void distanceAdjust() {
        distanceError = -y;
        distanceAdjust = 0.0;

        leftCommand = 0.0;
        rightCommand = 0.0;

        distanceAdjust = KpDistance * distanceError;

        leftCommand = distanceAdjust;
        rightCommand = -distanceAdjust;
    }

    public double getLeftCommand() {
        return leftCommand;
    }

    public double getRightCommand() {
        return rightCommand;
    }
}
