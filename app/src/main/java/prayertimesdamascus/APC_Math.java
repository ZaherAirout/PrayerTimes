package prayertimesdamascus;

public class APC_Math {
    static double Frac(double x) {
        return x - ((double) ((long) x));
    }

    static double Ddd(int D, int M, double S) {
        double sign;
        if (D < 0 || M < 0 || S < 0.0d) {
            sign = -1.0d;
        } else {
            sign = 1.0d;
        }
        return ((((double) Math.abs(D)) + (((double) Math.abs(M)) / 60.0d)) + (Math.abs(S) / 3600.0d)) * sign;
    }

    public static double Pegasus(MoonPhases moonPhase, double LowerBound, double UpperBound, double Accuracy, boolean[] Success) {
        double x1 = LowerBound;
        double x2 = UpperBound;
        double f1 = moonPhase.calculatePhase(x1);
        double f2 = moonPhase.calculatePhase(x2);
        int Iterat = 0;
        Success[0] = false;
        double Root = x1;
        if (f1 * f2 < 0.0d) {
            do {
                double x3 = x2 - (f2 / ((f2 - f1) / (x2 - x1)));
                double f3 = moonPhase.calculatePhase(x3);
                if (f3 * f2 <= 0.0d) {
                    x1 = x2;
                    f1 = f2;
                    x2 = x3;
                    f2 = f3;
                } else {
                    f1 = (f1 * f2) / (f2 + f3);
                    x2 = x3;
                    f2 = f3;
                }
                if (Math.abs(f1) < Math.abs(f2)) {
                    Root = x1;
                } else {
                    Root = x2;
                }
                Success[0] = Math.abs(x2 - x1) <= Accuracy;
                Iterat++;
                if (Success[0]) {
                    break;
                }
            } while (Iterat < 30);
        }
        return Root;
    }
}
