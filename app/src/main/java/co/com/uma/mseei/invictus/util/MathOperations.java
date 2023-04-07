package co.com.uma.mseei.invictus.util;

public class MathOperations {

    public static final float _1KG_LBS = 2.20462262185f;
    public static final float _1IN_M = 0.0254f;

    public static float kg2lbs(float kg){
        return kg * _1KG_LBS;
    }

    public static float lbs2kg(float lbs){
        return lbs * (1/_1KG_LBS);
    }

    public static float m2in(float m){
        return m * (1/_1IN_M);
    }

    public static float in2m(float in){
        return in * _1IN_M;
    }
}
