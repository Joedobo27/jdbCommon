package com.joedobo27.libs;

@SuppressWarnings({"FieldCanBeLocal", "unused"})
public class LinearScalingFunction {

    private final double x1;
    private final double y1;
    private final double x2;
    private final double y2;
    private final double slope;
    private final double yIntercept;

    private LinearScalingFunction(double x1, double y1, double x2, double y2, double slope,
                                  double yIntercept) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.slope = slope;
        this.yIntercept = yIntercept;
    }

    public static LinearScalingFunction make(double x1, double x2, double y1, double y2) {
        double slope;
        double rise;
        double run;
        if (x1 == x2 || y1 == y2)
            slope = 0;
        else {
            rise = y2 - y1;
            run = x2 - x1;
            slope =  rise / run;
        }
        double yIntercept = y1 - (slope * x1);
        return new LinearScalingFunction(x1, y1, x2, y2, slope, yIntercept);
    }

    public double doFunctionOfX(double xValue) {
        return (xValue * this.slope) + this.yIntercept;
    }
}
