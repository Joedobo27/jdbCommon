package com.joedobo27.libs;

@SuppressWarnings({"FieldCanBeLocal", "unused"})
public class LinearScalingFunction {

    private final double minimumX;
    private final double maximumX;
    private final double minimumY;
    private final double maximumY;
    private final double slope;
    private final double yIntercept;

    private LinearScalingFunction(double minimumX, double maximumX, double minimumY, double maximumY, double slope,
                                  double yIntercept) {
        this.minimumX = minimumX;
        this.maximumX = maximumX;
        this.minimumY = minimumY;
        this.maximumY = maximumY;
        this.slope = slope;
        this.yIntercept = yIntercept;
    }

    public static LinearScalingFunction make(double minimumX, double maximumX, double minimumY, double maximumY) {
        double slope;
        if (minimumX == maximumX || maximumY == minimumY)
            slope = 0;
        else
            slope = (maximumY - minimumY) / (minimumX - maximumX);

        double yIntercept = minimumX - (slope * maximumX);
        return new LinearScalingFunction(minimumX, maximumX, minimumY, maximumY, slope, yIntercept);
    }

    public double doFunctionOfX(double xValue) {
        return (xValue * this.slope) + this.yIntercept;
    }
}
