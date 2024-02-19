package frc.robot;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

public class limelightData {

    NetworkTable limelightTable = NetworkTableInstance.getDefault().getTable("limelight");

        public static double needRotate;
        public static double needStrafe;
        public static double needTranslate;

        public static double XC;
        public static double YC;
        public static double ZC;
        public static double targetRoll;
        public static double targetPitch;
        public static double targetYaw;
        public static double tagID;
        public static boolean targetValid;
        public static double targetXOffset;
        public static double targetYOffset;
        public static double targetArea;


        private boolean calculating = true;

    public void calculate() {
        if (!calculating) {
            return;
        }

        needStrafe = 0;
        needTranslate = 0;

        double[] targetPose = limelightTable.getEntry("targetpose_cameraspace").getDoubleArray(new double[6]);

        targetXOffset = limelightTable.getEntry("tx").getDouble(0.0);
        targetYOffset = limelightTable.getEntry("ty").getDouble(0.0);
        targetArea = limelightTable.getEntry("ta").getDouble(0.0);
        tagID = limelightTable.getEntry("tid").getDouble(0.0);
        XC = targetPose[0];
        YC = targetPose[1];
        ZC = targetPose[2];
        //double targetRoll = targetPose[3];
        //double targetPitch = targetPose[4];


       System.out.println(Math.sqrt((XC*XC)+(YC*YC)+(ZC*ZC)));
       // System.out.println("Roll: " + targetRoll + "   Pitch: " + targetPitch + "   Yaw: " + targetYaw);
        //System.out.println("X: " + targetXC + "   Y: " + targetYC + "   Z: " + targetZC);
        //System.out.println("Distance to Tag: " + distance);
    }
    public void stopCalculating() {
        this.calculating = false;
    }
}
