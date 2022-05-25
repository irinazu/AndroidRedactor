package com.example.redactor;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import com.snatik.polygon.Point;
import com.snatik.polygon.Polygon;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.AttributedCharacterIterator;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CustomCanvas extends View {
    List<List<Integer>> figures=new ArrayList<>();
    Paint paint;
    Integer indexForDelete=-1;
    Integer deleteForever=-1;
    float x,y;
    int colorEdge,colorFill;
    Context context;
    RelativeLayout layout;
    View segment, circle, rect;
    Button segmentForCanvas,circleForCanvas,rectangleForCanvas,delete,edgeButton,fillButton,save;
    EditText size,segmentBeginX,segmentBeginY,segmentEndX,segmentEndY,centerX,centerY,radius,cornerX,cornerY,height,width;

    void setButton(Button segmentForCanvas, Button circleForCanvas, Button rectangleForCanvas,Button delete,
                   Button edgeButton,Button fillButton,Button save){
        this.segmentForCanvas=segmentForCanvas;
        this.circleForCanvas=circleForCanvas;
        this.rectangleForCanvas=rectangleForCanvas;
        this.delete=delete;
        this.edgeButton=edgeButton;
        this.fillButton=fillButton;
        this.save=save;
    }
    void setSegmentInfo(EditText segmentBeginX,EditText segmentBeginY,EditText segmentEndX,EditText segmentEndY){
        this.segmentBeginX=segmentBeginX;
        this.segmentBeginY=segmentBeginY;
        this.segmentEndX=segmentEndX;
        this.segmentEndY=segmentEndY;
    }
    void setCircleInfo(EditText centerX,EditText centerY,EditText radius){
        this.centerX=centerX;
        this.centerY=centerY;
        this.radius=radius;
    }
    void setRectangleInfo(EditText cornerX,EditText cornerY,EditText height,EditText width){
        this.cornerX=cornerX;
        this.cornerY=cornerY;
        this.height=height;
        this.width=width;
    }
    void setSize(EditText size){
        this.size=size;
    }
    void setLayout(View segment,View circle,View rect){
        this.segment=segment;
        this.circle=circle;
        this.rect=rect;
    }

    public CustomCanvas(Context context) {
        super(context);
        init();
    }
    public CustomCanvas(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    public void init(){
        paint=new Paint();
        paint.setColor(Color.BLUE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);
        boolean switchMode=false;
        if(figures!=null){
            for(List<Integer> list:figures){
                Integer flag=list.get(0);
                if(flag==0){
                    Rect rect=new Rect();
                    Paint paint=new Paint();
                    Rect rect1=new Rect();
                    Paint paint1 = new Paint();
                    paint.setColor(list.get(7));
                    paint.setStyle(Paint.Style.FILL);
                    paint1.setStrokeWidth(list.get(5));
                    paint1.setStyle(Paint.Style.STROKE);
                    paint1.setColor(list.get(6));

                    rect.set(list.get(1),list.get(2),list.get(3),list.get(4));
                    rect1.set(list.get(1),list.get(2),list.get(3),list.get(4));
                    canvas.drawRect(rect,paint);
                    canvas.drawRect(rect1,paint1);

                }
                else if(flag==1){
                    Paint paint=new Paint();
                    Paint paint1 = new Paint();
                    paint.setColor(list.get(6));
                    paint.setStyle(Paint.Style.FILL);
                    paint1.setStrokeWidth(list.get(4));
                    paint1.setStyle(Paint.Style.STROKE);
                    paint1.setColor(list.get(5));

                    canvas.drawCircle(list.get(1),list.get(2),list.get(3),paint);
                    canvas.drawCircle(list.get(1),list.get(2),list.get(3),paint1);
                }
                else{
                    Paint paint=new Paint();
                    Paint paint1 = new Paint();
                    paint.setColor(list.get(7));
                    paint.setStyle(Paint.Style.FILL);
                    paint1.setStrokeWidth(list.get(5));
                    paint1.setStyle(Paint.Style.STROKE);
                    paint1.setColor(list.get(6));

                    canvas.drawLine(list.get(1),list.get(2),list.get(3),list.get(4),paint);
                    canvas.drawLine(list.get(1),list.get(2),list.get(3),list.get(4),paint1);
                }
            }
        }

        if(!rectangleForCanvas.isEnabled()) {
            String x = cornerX.getText().toString();
            String y = cornerY.getText().toString();
            String heightInfo = height.getText().toString();
            String widthInfo = width.getText().toString();
            if (x.length() != 0 && y.length() != 0 && heightInfo.length() != 0 && widthInfo.length() != 0) {
                switchMode=true;
                Rect rect1=new Rect();
                Paint paint1 = new Paint();
                Rect rect2 = new Rect();
                Paint paint2 = new Paint();

                int xInt = Integer.parseInt(x);
                int yInt = Integer.parseInt(y);
                int widthInfoInteger = xInt + Integer.parseInt(widthInfo);
                int heightInfoInteger = yInt + Integer.parseInt(heightInfo);
                rect1.set(xInt,yInt,widthInfoInteger,heightInfoInteger);
                paint1.setStyle(Paint.Style.STROKE);
                paint1.setColor(colorEdge);
                paint1.setStrokeWidth(Integer.parseInt(size.getText().toString()));

                rect2.set(xInt, yInt, widthInfoInteger, heightInfoInteger);
                paint2.setStyle(Paint.Style.FILL);
                paint2.setColor(colorFill);
                canvas.drawRect(rect2, paint2);
                canvas.drawRect(rect1, paint1);

                List<Integer> integers = new ArrayList<>();
                integers.add(0);
                integers.add(xInt);
                integers.add(yInt);
                integers.add(widthInfoInteger);
                integers.add(heightInfoInteger);
                integers.add(Integer.parseInt(size.getText().toString()));
                integers.add(colorEdge);
                integers.add(colorFill);
                figures.add(integers);

                cornerX.setText("");
                cornerY.setText("");
                height.setText("");
                width.setText("");
            }
        }
        else if(!circleForCanvas.isEnabled()){
            String x = centerX.getText().toString();
            String y = centerY.getText().toString();
            String radiusInfo = radius.getText().toString();

            if (x.length() != 0 && y.length() != 0 && radiusInfo.length() != 0) {
                switchMode=true;
                Paint paint2 = new Paint();
                Paint paint1=new Paint();

                int xInt = Integer.parseInt(x);
                int yInt = Integer.parseInt(y);
                int radiusInfoInteger =Integer.parseInt(radiusInfo);

                paint1.setColor(colorEdge);
                paint1.setStrokeWidth(Integer.parseInt(size.getText().toString()));
                paint1.setStyle(Paint.Style.STROKE);
                paint2.setStyle(Paint.Style.FILL);
                paint2.setColor(colorFill);
                canvas.drawCircle(xInt,yInt,radiusInfoInteger,paint2);
                canvas.drawCircle(xInt,yInt,radiusInfoInteger,paint1);

                List<Integer> integers = new ArrayList<>();
                integers.add(1);
                integers.add(xInt);
                integers.add(yInt);
                integers.add(radiusInfoInteger);
                integers.add(Integer.parseInt(size.getText().toString()));
                integers.add(colorEdge);
                integers.add(colorFill);
                figures.add(integers);

                centerX.setText("");
                centerY.setText("");
                radius.setText("");
            }
        }
        else if(!segmentForCanvas.isEnabled()){
            String xB = segmentBeginX.getText().toString();
            String yB = segmentBeginY.getText().toString();
            String xE = segmentEndX.getText().toString();
            String yE = segmentEndY.getText().toString();

            if (xB.length() != 0 && yB.length() != 0 && xE.length() != 0&& yE.length() != 0) {
                switchMode=true;
                Paint paint2 = new Paint();
                Paint paint1 = new Paint();

                int xBInt = Integer.parseInt(xB);
                int yBInt = Integer.parseInt(yB);
                int xEInt = Integer.parseInt(xE);
                int yEInt = Integer.parseInt(yE);

                paint1.setColor(colorEdge);
                paint1.setStrokeWidth(Integer.parseInt(size.getText().toString()));
                paint1.setStyle(Paint.Style.STROKE);

                paint2.setStyle(Paint.Style.FILL);
                paint2.setColor(colorFill);

                canvas.drawLine(xBInt,yBInt,xEInt,yEInt,paint2);
                canvas.drawLine(xBInt,yBInt,xEInt,yEInt,paint1);

                List<Integer> integers = new ArrayList<>();
                integers.add(2);
                integers.add(xBInt);
                integers.add(yBInt);
                integers.add(xEInt);
                integers.add(yEInt);
                integers.add(Integer.parseInt(size.getText().toString()));
                integers.add(colorEdge);
                integers.add(colorFill);
                figures.add(integers);

                segmentBeginX.setText("");
                segmentBeginY.setText("");
                segmentEndX.setText("");
                segmentEndY.setText("");

            }
        }

        if(!switchMode){
            for (List<Integer> list:figures){
                int flag=list.get(0);
                if(flag==0){
                    Polygon polygon=new Polygon.Builder()
                            .addVertex(new Point(list.get(1),list.get(2)))
                            .addVertex(new Point(list.get(3),list.get(2)))
                            .addVertex(new Point(list.get(3),list.get(4)))
                            .addVertex(new Point(list.get(1),list.get(4)))
                            .build();

                    boolean contain=polygon.contains(new Point(x,y));
                    if(contain){
                        Integer w=list.get(3)-list.get(1);
                        Integer h=list.get(4)-list.get(2);
                        cornerX.setText(list.get(1).toString());
                        cornerY.setText(list.get(2).toString());
                        width.setText(w.toString());
                        height.setText(h.toString());
                        size.setText(list.get(5).toString());
                        edgeButton.setBackgroundColor(list.get(6));
                        fillButton.setBackgroundColor(list.get(7));
                        deleteForever=figures.indexOf(list);
                        System.out.println(deleteForever+"deleteForever in !switchMode");
                        indexForDelete=figures.indexOf(list);
                        System.out.println(indexForDelete+"indexForDelete in !switchMode");

                        segmentForCanvas.setEnabled(true);
                        circleForCanvas.setEnabled(true);
                        rectangleForCanvas.setEnabled(false);
                        segment.setVisibility(View.GONE);
                        circle.setVisibility(View.GONE);
                        rect.setVisibility(View.VISIBLE);

                    }
                }
                else if(flag==1){
                    boolean contain=false;

                    if((list.get(1)-x)*(list.get(1)-x)+(list.get(2)-y)*(list.get(2)-y)<=list.get(3)*list.get(3)){
                        contain=true;
                    }

                    if(contain){
                        centerX.setText(list.get(1).toString());
                        centerY.setText(list.get(2).toString());
                        radius.setText(list.get(3).toString());
                        size.setText(list.get(4).toString());
                        edgeButton.setBackgroundColor(list.get(5));
                        fillButton.setBackgroundColor(list.get(6));
                        deleteForever=figures.indexOf(list);
                        indexForDelete=figures.indexOf(list);
                        segmentForCanvas.setEnabled(true);
                        circleForCanvas.setEnabled(false);
                        rectangleForCanvas.setEnabled(true);
                        segment.setVisibility(View.GONE);
                        circle.setVisibility(View.VISIBLE);
                        rect.setVisibility(View.GONE);
                    }
                }
                else if(flag==2){
                    Polygon polygon=new Polygon.Builder()
                            .addVertex(new Point(list.get(1)-40,list.get(2)))
                            .addVertex(new Point(list.get(3)-40,list.get(4)))
                            .addVertex(new Point(list.get(3)+40,list.get(4)))
                            .addVertex(new Point(list.get(1)+40,list.get(2)))
                            .build();
                    boolean contain=polygon.contains(new Point(x,y));
                    if(contain){
                        segmentBeginX.setText(list.get(1).toString());
                        segmentBeginY.setText(list.get(2).toString());
                        segmentEndX.setText(list.get(3).toString());
                        segmentEndY.setText(list.get(4).toString());
                        size.setText(list.get(5).toString());
                        edgeButton.setBackgroundColor(list.get(6));
                        fillButton.setBackgroundColor(list.get(7));
                        deleteForever=figures.indexOf(list);
                        System.out.println(deleteForever+"deleteForever in !switchMode");
                        indexForDelete=figures.indexOf(list);
                        System.out.println(indexForDelete+"indexForDelete in !switchMode");
                        segmentForCanvas.setEnabled(false);
                        circleForCanvas.setEnabled(true);
                        rectangleForCanvas.setEnabled(true);
                        segment.setVisibility(View.VISIBLE);
                        circle.setVisibility(View.GONE);
                        rect.setVisibility(View.GONE);
                    }
                }
            }
        }

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cornerX.setText("");
                cornerY.setText("");
                height.setText("");
                width.setText("");
                segmentBeginX.setText("");
                segmentBeginY.setText("");
                segmentEndX.setText("");
                segmentEndY.setText("");
                centerX.setText("");
                centerY.setText("");
                radius.setText("");
                int z=deleteForever;
                if(z>-1&&z<figures.size()){
                    figures.remove(z);
                }
                deleteForever=-1;
                indexForDelete=-1;
                invalidate();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*saveImageToMediaStore();*/
                save2();
            }
        });

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        x = event.getX();
        y = event.getY();

        if(indexForDelete.intValue()>-1){
            figures.remove((int)indexForDelete);
            //deleteForever=indexForDelete.intValue();
        }
        indexForDelete=-1;
        invalidate();
        return super.onTouchEvent(event);

    }
    void setColourEdge(int colour){
        this.colorEdge=colour;
    }
    void setColourFill(int colour){
        this.colorFill=colour;
    }
    void setL(RelativeLayout layout){
        this.layout=layout;
    }

    /*void saveImageToMediaStore(){
        Bitmap bMap = Bitmap.createBitmap(this.getWidth(), this.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bMap);
        this.draw(canvas);
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
                + "/Android/data/"
                + context.getPackageName()
                + "/Files");
        mediaStorageDir.mkdirs();
        String fname = "ImageName" + ".png";
        File file = new File(mediaStorageDir, fname);
        if (file.exists()) file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            bMap.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    void setContext(Context context){
        this.context=context;
    }*/

    void save2(){
        layout.setDrawingCacheEnabled(true);
        layout.buildDrawingCache();
        layout.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        Bitmap bMap=layout.getDrawingCache();

        //Bitmap bMap = Bitmap.createBitmap(this.getWidth(), this.getHeight(), Bitmap.Config.ARGB_8888);
        //Canvas canvas = new Canvas(bMap);
        //this.draw(canvas);
        String root=Environment.getExternalStorageDirectory().getAbsolutePath();
        File file=new File(root+"/Download");
        UUID uniqueKey = UUID.randomUUID();
        String filwName=uniqueKey+".jpg";
        File fileMain=new File(file,filwName);
        try (FileOutputStream fileOutputStream = new FileOutputStream(fileMain)) {
            bMap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            layout.setDrawingCacheEnabled(false);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
