package com.joedobo27.libs.action;

@SuppressWarnings("unused")
enum DurationModifiers {
    MAX_WOA_EFFECT(0.20),
    TOOL_RARITY_EFFECT(0.1),
    ACTION_RARITY_EFFECT(0.33);

    final double reductionMultiple;

    DurationModifiers(double reductionMultiple) {
        this.reductionMultiple = reductionMultiple;
    }

    public double getReductionMultiple() {
        return this.reductionMultiple;
    }
}
