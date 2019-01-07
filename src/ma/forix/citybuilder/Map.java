package ma.forix.citybuilder;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

public class Map extends JPanel implements MouseListener, MouseMotionListener {
    private int scale = 50;
    private int MouseCoord[] = {0, 0};
    private ArrayList<Integer> mouseX = new ArrayList();
    private ArrayList<Integer> mouseY = new ArrayList();
    private int route = 1, immeuble = 2;
    private String currentTool = "Null";
    private boolean clicked = false;
    private JSONObject map = new JSONObject();
    private JSONArray blocks;

    public Map(){
        setSize(1000, 550);

        addMouseMotionListener(this);
        addMouseListener(this);

        int loop = 0;
        for(int y = 0; y < getHeight() / scale; y++){
            for (int x = 0; x < getWidth() / scale; x++){
                blocks = new JSONArray();
                blocks.add(0);
                blocks.add(x);
                blocks.add(y);

                map.put("block"+loop, blocks);
                loop++;
            }
        }
        System.out.println(map.toString());

        for (int y = 0; y < this.getHeight() / scale; y++) {
            mouseY.add(0);
        }
        for (int x = 0; x < this.getWidth() / scale; x++) {
            mouseX.add(0);
        }

    }

    public void generateMap(){
        int loop = 0;
        for(int y = 0; y < getHeight() / scale; y++){
            for (int x = 0; x < getWidth() / scale; x++){
                blocks = new JSONArray();
                blocks.add(0);
                blocks.add(x);
                blocks.add(y);

                map.put("block"+loop, blocks);
                loop++;
            }
        }
        System.out.println(map.toString());

        for (int y = 0; y < this.getHeight() / scale; y++) {
            mouseY.add(0);
        }
        for (int x = 0; x < this.getWidth() / scale; x++) {
            mouseX.add(0);
        }
    }

    public void newMap(){
        map = new JSONObject();
        mouseY.clear();
        mouseX.clear();
        generateMap();
    }

    public JSONObject getMap(){
        return map;
    }

    public void setMap(JSONObject jo){
        this.map = jo;
    }

    public void setCurrentTool(String currentTool){
        this.currentTool = currentTool;
    }

    public String getCurrentTool(){
        return currentTool;
    }

    public void paintComponent(Graphics g) {
        g.setColor(Color.gray);
        g.fillRect(0, 0 , getWidth(), getHeight());
        JSONArray infos;
        Iterator<Integer> iterator;
        Iterator<Long> iteratorL;
        int loop = 0;
        for (int y = 0; y < getHeight() / scale; y++){
            for (int x = 0; x < getWidth() / scale; x++){
                try {
                    infos = (JSONArray) map.get("block" + loop);
                    iterator = infos.iterator();
                    int type = iterator.next();
                    int Bx = iterator.next();
                    int By = iterator.next();
                    if (type == route) {
                        g.setColor(Color.BLACK);
                        g.fillRect(Bx * scale + Bx, By * scale + By, scale, scale);
                    }

                    if (type == immeuble) {
                        g.setColor(Color.BLUE);
                        g.fillRect(Bx * scale + Bx, By * scale + By, scale, scale);
                    }

                    loop++;
                } catch (ClassCastException cce){
                    infos = (JSONArray) map.get("block" + loop);
                    iteratorL = infos.iterator();
                    long type = iteratorL.next();
                    long Bx = iteratorL.next();
                    long By = iteratorL.next();
                    if (type == route) {
                        g.setColor(Color.BLACK);
                        g.fillRect((int) (Bx * scale + Bx), (int) (By * scale + By), scale, scale);
                    }

                    if (type == immeuble) {
                        g.setColor(Color.BLUE);
                        g.fillRect((int) (Bx * scale + Bx), (int) (By * scale + By), scale, scale);
                    }

                    loop++;
                }
            }
        }

        for (int y = 0; y < getHeight() / scale; y++){
            for (int x = 0; x < getWidth() / scale; x++){
                if(mouseX.get(x)==-1 && mouseY.get(y) == -1){
                    if (clicked)
                        g.setColor(Color.YELLOW);
                    if (!clicked)
                        g.setColor(Color.GREEN);
                    g.drawRect(x * scale+x, y * scale+y, scale, scale);
                }
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if(!clicked)
            clicked = true;
        if (MouseCoord[0] >= 0 && MouseCoord[0] <= getWidth()/scale-1 && MouseCoord[1] >= 0 && MouseCoord[1] <= getHeight()/scale-1) {
            mouseX.set(MouseCoord[0], 0);
            mouseY.set(MouseCoord[1], 0);
        }
        MouseCoord[0] = e.getX() / (scale+1);
        MouseCoord[1] = e.getY() / (scale+1);
        if (MouseCoord[0] >= 0 && MouseCoord[0] <= getWidth()/scale-1 && MouseCoord[1] >= 0 && MouseCoord[1] <= getHeight()/scale-1) {
            mouseX.set(MouseCoord[0], -1);
            mouseY.set(MouseCoord[1], -1);
        }
        if (currentTool.equals("Route")){
            int nbrBlock = MouseCoord[1]*(mouseX.size()+1)+MouseCoord[0];
            blocks = new JSONArray();
            blocks.add(route);
            blocks.add(MouseCoord[0]);
            blocks.add(MouseCoord[1]);
            map.replace("block"+nbrBlock, blocks);
        } else if (currentTool.equals("Immeuble")){
            int nbrBlock = MouseCoord[1]*(mouseX.size()+1)+MouseCoord[0];
            blocks = new JSONArray();
            blocks.add(immeuble);
            blocks.add(MouseCoord[0]);
            blocks.add(MouseCoord[1]);
            map.replace("block"+nbrBlock, blocks);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (clicked)
            clicked = false;
        if (MouseCoord[0] >= 0 && MouseCoord[0] <= getWidth()/scale-1 && MouseCoord[1] >= 0 && MouseCoord[1] <= getHeight()/scale-1) {
            mouseX.set(MouseCoord[0], 0);
            mouseY.set(MouseCoord[1], 0);
        }
            MouseCoord[0] = e.getX() / (scale+1);
            MouseCoord[1] = e.getY() / (scale+1);
        if (MouseCoord[0] >= 0 && MouseCoord[0] <= getWidth()/scale-1 && MouseCoord[1] >= 0 && MouseCoord[1] <= getHeight()/scale-1) {
            mouseX.set(MouseCoord[0], -1);
            mouseY.set(MouseCoord[1], -1);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(!clicked)
            clicked = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (clicked)
            clicked = false;
        if (currentTool.equals("Route")){
            int nbrBlock = MouseCoord[1]*(mouseX.size()+1)+MouseCoord[0];
            blocks = new JSONArray();
            blocks.add(route);
            blocks.add(MouseCoord[0]);
            blocks.add(MouseCoord[1]);
            map.replace("block"+nbrBlock, blocks);
        } else if (currentTool.equals("Immeuble")){
            int nbrBlock = MouseCoord[1]*(mouseX.size()+1)+MouseCoord[0];
            blocks = new JSONArray();
            blocks.add(immeuble);
            blocks.add(MouseCoord[0]);
            blocks.add(MouseCoord[1]);
            map.replace("block"+nbrBlock, blocks);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
