package com.example.detectarobjetos;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;
import java.util.ArrayList;
import java.util.List;

public class DetectionOverlay extends View {
    private List<DetectionResult> detections = new ArrayList<>();
    private Paint boxPaint;
    private Paint textPaint;

    public DetectionOverlay(Context context) {
        super(context);
        init();
    }

    private void init() {
        boxPaint = new Paint();
        boxPaint.setColor(Color.RED);
        boxPaint.setStyle(Paint.Style.STROKE);
        boxPaint.setStrokeWidth(5.0f);

        textPaint = new Paint();
        textPaint.setColor(Color.RED);
        textPaint.setTextSize(36.0f);
    }

    public void setDetections(List<DetectionResult> detections) {
        this.detections = detections;
        invalidate(); // Solicita un redibujado de la vista
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (DetectionResult detection : detections) {
            RectF scaledRect = scaleRect(detection.getBoundingBox());

            // Dibuja el rectángulo
            canvas.drawRect(scaledRect, boxPaint);

            // Dibuja el texto (etiqueta y confianza)
            String label = String.format("%s: %.2f", detection.getLabel(), detection.getConfidence());
            canvas.drawText(label, scaledRect.left, scaledRect.top - 10, textPaint);
        }
    }

    private RectF scaleRect(RectF rect) {
        float scaleX = getWidth() / (float) 640;
        float scaleY = getHeight() / (float) 640;
        return new RectF(
                rect.left * scaleX,
                rect.top * scaleY,
                rect.right * scaleX,
                rect.bottom * scaleY
        );
    }

    public static class DetectionResult {
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
}