package prayertimesdamascus;

public class EclipticPosition {
    static double getMiniSunLongitude(double T) {
        double M = 6.283185307179586d * APC_Math.Frac(0.993133d + (99.997361d * T));
        return APC_Math.Frac((0.7859453d + (M / 6.283185307179586d)) + ((((6893.0d * Math.sin(M)) + (72.0d * Math.sin(2.0d * M))) + (6191.2d * T)) / 1296000.0d)) * 6.283185307179586d;
    }

    static double[] getMiniMoon(double T) {
        double[] moonLongLat = new double[2];
        double l = 6.283185307179586d * APC_Math.Frac(0.374897d + (1325.55241d * T));
        double ls = 6.283185307179586d * APC_Math.Frac(0.993133d + (99.997361d * T));
        double D = 6.283185307179586d * APC_Math.Frac(0.827361d + (1236.853086d * T));
        double F = 6.283185307179586d * APC_Math.Frac(0.259086d + (1342.227825d * T));
        double dL = (((((((((((((22640.0d * Math.sin(l)) - (4586.0d * Math.sin(l - (2.0d * D)))) + (2370.0d * Math.sin(2.0d * D))) + (769.0d * Math.sin(2.0d * l))) - (668.0d * Math.sin(ls))) - (412.0d * Math.sin(2.0d * F))) - (212.0d * Math.sin((2.0d * l) - (2.0d * D)))) - (206.0d * Math.sin((l + ls) - (2.0d * D)))) + (192.0d * Math.sin((2.0d * D) + l))) - (165.0d * Math.sin(ls - (2.0d * D)))) - (125.0d * Math.sin(D))) - (110.0d * Math.sin(l + ls))) + (148.0d * Math.sin(l - ls))) - (55.0d * Math.sin((2.0d * F) - (2.0d * D)));
        double S = F + ((((412.0d * Math.sin(2.0d * F)) + dL) + (541.0d * Math.sin(ls))) / 206264.80624709636d);
        double h = F - (2.0d * D);
        double N = ((((((-526.0d * Math.sin(h)) + (44.0d * Math.sin(l + h))) - (31.0d * Math.sin((-l) + h))) - (23.0d * Math.sin(ls + h))) + (11.0d * Math.sin((-ls) + h))) - (25.0d * Math.sin((-2.0d * l) + F))) + (21.0d * Math.sin((-l) + F));
        moonLongLat[0] = APC_Math.Frac((dL / 1296000.0d) + APC_Math.Frac(0.606433d + (1336.855225d * T))) * 6.283185307179586d;
        moonLongLat[1] = ((18520.0d * Math.sin(S)) + N) / 206264.80624709636d;
        return moonLongLat;
    }
}
