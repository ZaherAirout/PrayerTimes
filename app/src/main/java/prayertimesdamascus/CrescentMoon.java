package prayertimesdamascus;

public class CrescentMoon extends MoonPhases {
    EclipticPosition eclipPos;

    public double calculatePhase(double T) {
        double l_Sun = EclipticPosition.getMiniSunLongitude(T - 1.5818693436763253E-7d);
        double[] moonLongLat = EclipticPosition.getMiniMoon(T);
        double l_Moon = moonLongLat[0];
        double beta = moonLongLat[1];
        double LongDiff = l_Moon - l_Sun;
        return ((3.141592653589793d * 8.0d) / 180.0d) - Math.sqrt((LongDiff * LongDiff) + (beta * beta));
    }
}
