package com.joedobo27.libs.action;

class TimeScalingLinearFunction {
    private final int longestTime;
    private final double slope;
    private final double yIntercept;

    private TimeScalingLinearFunction(int longestTime, double slope, double yIntercept) {
        this.longestTime = longestTime;
        this.slope = slope;
        this.yIntercept = yIntercept;
    }

    public int getTensOfSecondTime(double modifiedKnowledge) {
        int time = (int)((modifiedKnowledge * this.slope) + this.yIntercept);
        time = Math.min(this.longestTime, time);
        return time;
    }

    static private double getSlopeOfFunction(double minSkill, double maxSkill, double longestTime, double
            shortestTime) {
        if (minSkill == maxSkill || longestTime == shortestTime)
            return 0;
        return (longestTime - shortestTime) / (minSkill - maxSkill);
    }

    static private double getYInterceptOfFunction(double minSkill, double maxSkill, double slope) {
        return minSkill - (slope * maxSkill);
    }

    static public TimeScalingLinearFunction makeGetLinearFunction(int minSkill, int maxSkill, int longestTime, int shortestTime) {
        double slope = getSlopeOfFunction(minSkill, maxSkill, longestTime, shortestTime);
        double yIntercept = getYInterceptOfFunction(minSkill, maxSkill, slope);
        return new TimeScalingLinearFunction(longestTime, slope, yIntercept);
    }

}
