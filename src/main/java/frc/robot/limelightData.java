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
        public static double targetXOffset;
        public static double targetYOffset;
        public static double targetArea;
        public static boolean targetValid;
        public static double distance2d;

    public void calculate() {


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
        targetYaw = targetPose[5];
        distance2d = Math.sqrt((XC*XC)+(ZC*ZC));

       // System.out.println("X:" + XC + " Y: " + YC + " Z: " + ZC);
        //System.out.println(distance2d);

        //double targetRoll = targetPose[3];
        //double targetPitch = targetPose[4];
        
        if(tagID == 4 || tagID == 5 ||tagID == 6 ||tagID == 7 || tagID == 11 || tagID == 12 || tagID == 13 || tagID == 14 || tagID == 15 || tagID == 16){
            targetValid = true;
        }
        else{
            targetValid = false; ;  
        }
        //☆*: .｡. o(≧▽≦)o .｡.:*☆
      // System.out.println(Math.sqrt((XC*XC)+(YC*YC)+(ZC*ZC)));
       // System.out.println("Roll: " + targetRoll + "   Pitch: " + targetPitch + "   Yaw: " + targetYaw);
        //System.out.println("X: " + targetXC + "   Y: " + targetYC + "   Z: " + targetZC);
        //System.out.println("Distance to Tag: " + distance);
    }
}
//skib