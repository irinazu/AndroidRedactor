package com.example.redactor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import yuku.ambilwarna.AmbilWarnaDialog;

public class MainActivity extends AppCompatActivity {
    CustomCanvas customCanvas;
    Button segment,rectangle,circle,delete,edge,fill,save;
    int edgeColor,fillColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        customCanvas=(CustomCanvas)findViewById(R.id.redactor);
        segment=findViewById(R.id.segmentCanvas);
        circle=findViewById(R.id.circleCanvas);
        rectangle=findViewById(R.id.rectangleCanvas);
        delete=findViewById(R.id.delete);
        edge = findViewById(R.id.edge);
        fill = findViewById(R.id.fill);
        save = findViewById(R.id.save);

        customCanvas.setButton(segment,circle,rectangle,delete,edge,fill,save);

        View segmentLayout=findViewById(R.id.segmentLayout);
        View circleLayout=findViewById(R.id.circleLayout);
        View rectangleLayout=findViewById(R.id.rectangleLayout);

        EditText beginSegmentX=findViewById(R.id.XBeginPoint);
        EditText beginSegmentY=findViewById(R.id.YBeginPoint);
        EditText endSegmentX=findViewById(R.id.XEndPoint);
        EditText endSegmentY=findViewById(R.id.YEndPoint);
        EditText centerX=findViewById(R.id.XCenterPoint);
        EditText centerY=findViewById(R.id.YCenterPoint);
        EditText radius=findViewById(R.id.Radius);
        EditText cornerX=findViewById(R.id.LeftCornerXPoint);
        EditText cornerY=findViewById(R.id.LeftCornerYPoint);
        EditText height=findViewById(R.id.Height);
        EditText width=findViewById(R.id.Width);
        EditText size=findViewById(R.id.inputEdge);

        customCanvas.setSegmentInfo(beginSegmentX,beginSegmentY,endSegmentX,endSegmentY);
        customCanvas.setCircleInfo(centerX,centerY,radius);
        customCanvas.setRectangleInfo(cornerX,cornerY,height,width);
        customCanvas.setSize(size);
        customCanvas.setLayout(segmentLayout,circleLayout,rectangleLayout);

        segment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                segmentLayout.setVisibility(View.VISIBLE);
                circleLayout.setVisibility(View.GONE);
                rectangleLayout.setVisibility(View.GONE);
                segment.setEnabled(false);
                circle.setEnabled(true);
                rectangle.setEnabled(true);
            }
        });

        circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                segmentLayout.setVisibility(View.GONE);
                circleLayout.setVisibility(View.VISIBLE);
                rectangleLayout.setVisibility(View.GONE);
                segment.setEnabled(true);
                circle.setEnabled(false);
                rectangle.setEnabled(true);
            }
        });

        rectangle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                segmentLayout.setVisibility(View.GONE);
                circleLayout.setVisibility(View.GONE);
                rectangleLayout.setVisibility(View.VISIBLE);
                segment.setEnabled(true);
                circle.setEnabled(true);
                rectangle.setEnabled(false);
            }
        });

        edge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openColorPicker();
            }
        });
        fill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openColorPickerFill();
            }
        });
        //customCanvas.setContext(getApplicationContext());
        RelativeLayout layout=findViewById(R.id.redactorLayout);
        customCanvas.setL(layout);
    }
    public void openColorPicker() {
        AmbilWarnaDialog colorPicker = new AmbilWarnaDialog(this, edgeColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                edgeColor = color;
                edge.setBackgroundColor(edgeColor);
                customCanvas.setColourEdge(edgeColor);
            }
        });
        colorPicker.show();
    }
    public void openColorPickerFill() {
        AmbilWarnaDialog colorPicker = new AmbilWarnaDialog(this, fillColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                fillColor = color;
                fill.setBackgroundColor(fillColor);
                customCanvas.setColourFill(fillColor);
            }
        });
        colorPicker.show();
    }

}