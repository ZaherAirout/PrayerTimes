package prayertimesdamascus;

public class NewMoon extends MoonPhases {
    EclipticPosition eclipPos = new EclipticPosition();

    public double calculatePhase(double T) {
        return Modulo(3.141592653589793d + (EclipticPosition.getMiniMoon(T)[0] - EclipticPosition.getMiniSunLongitude(T - 1.5818693436763253E-7d)), 6.283185307179586d) - 3.141592653589793d;
    }

    private double Modulo(double x, double y) {
        return Frac(x / y) * y;
    }

    private double Frac(double x) {
        return x - Math.floor(x);
    }
}
