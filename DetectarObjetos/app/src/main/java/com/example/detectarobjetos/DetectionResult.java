package com.example.detectarobjetos;

import android.graphics.RectF;

public class DetectionResult {
    private RectF boundingBox;
    private String label;
    private float confidence;

    public DetectionResult(RectF boundingBox, String label, float confidence) {
        this.boundingBox = boundingBox;
        this.label = label;
        this.confidence = confidence;
    }

    public RectF getBoundingBox() { return boundingBox; }
    public String getLabel() { return label; }
    public float getConfidence() { return confidence; }
}