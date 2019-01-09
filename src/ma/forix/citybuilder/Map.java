package ma.forix.citybuilder;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Iterator;

public class Map extends JPanel implements MouseListener, MouseMotionListener {
    private int scale = 50;
    private int MouseCoord[] = {0, 0};
    private ArrayList<Integer> mouseX;
    private ArrayList<Integer> mouseY;
    private int canBuild = -1, route = 1, immeuble = 2;
    private String currentTool = "Null";
    private boolean clicked = false;
    private JSONObject map;
    private JSONArray blocks;

    public Map(){
        setSize(1000, 550);
        addMouseMotionListener(this);
        addMouseListener(this);
        generateMap();
        System.out.println(map.toString());
    }

    public void generateMap(){
        int loop = 0;
        map = new JSONObject();
        mouseX = new ArrayList<>();
        mouseY = new ArrayList<>();
        for(int y = 0; y < getHeight() / scale; y++){
            mouseY.add(0);
            for (int x = 0; x < getWidth() / scale; x++){
                blocks = new JSONArray();
                blocks.add(0);
                blocks.add(x);
                blocks.add(y);

                map.put("block"+loop, blocks);
                loop++;
            }
        }
        for (int x = 0; x < this.getWidth() / scale; x++) {
            mouseX.add(0);
        }
        System.out.println(map.toString());
    }

    public void newMap(){
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
        int type, Bx, By;
        int loop = 0;
        for (int y = 0; y < getHeight() / scale; y++){
            for (int x = 0; x < getWidth() / scale; x++){
                infos = (JSONArray) map.get("block" + loop);
                try {
                    iterator = infos.iterator();
                    type = iterator.next();
                    Bx = iterator.next();
                    By = iterator.next();
                } catch (ClassCastException cce){
                    iteratorL = infos.iterator();
                    type = Math.toIntExact(iteratorL.next());
                    Bx = Math.toIntExact(iteratorL.next());
                    By = Math.toIntExact(iteratorL.next());
                }
                if (type == route) {
                    g.setColor(Color.BLACK);
                    g.fillRect(Bx * scale + Bx+1, By * scale + By+1, scale-1, scale-1);
                }
                if (type == immeuble) {
                    g.setColor(Color.BLUE);
                    g.fillRect(Bx * scale + Bx+1, By * scale + By+1, scale-1, scale-1);
                }
                if (type == canBuild) {
                    if(mouseX.get(x)==-1 && mouseY.get(y) == -1){
                        if (clicked)
                            g.setColor(Color.YELLOW);
                        if (!clicked)
                            g.setColor(Color.GREEN);
                        g.drawRect(x * scale+x, y * scale+y, scale, scale);
                    } else {
                        g.setColor(Color.lightGray);
                        g.drawRect(Bx * scale + Bx, By * scale + By, scale, scale);
                    }
                }
                loop++;
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

    private void mouseUpdate(MouseEvent e){
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

    private void setStructure(){
        int nbrBlock = MouseCoord[1]*(mouseX.size()+1)+MouseCoord[0];
        blocks = new JSONArray();
        if (currentTool.equals("Route")){
            blocks.add(route);
            blocks.add(MouseCoord[0]);
            blocks.add(MouseCoord[1]);
            map.replace("block"+nbrBlock, blocks);
            JSONArray temp;
            temp = (JSONArray) map.get("block"+(nbrBlock+1));
            if (!temp.get(0).toString().equals(Integer.toString(route)) /*|| !map.get("block"+(nbrBlock+1)).equals(immeuble)*/) {
                System.out.println(temp.get(0).toString());
                System.out.println(temp.get(1).toString());
                System.out.println(temp.get(2).toString());
                blocks = new JSONArray();
                blocks.add(canBuild);
                blocks.add(MouseCoord[0] + 1);
                blocks.add(MouseCoord[1]);
                map.replace("block" + (nbrBlock + 1), blocks);
                System.out.println(map.get("block" + nbrBlock));
                System.out.println(map.get("block" + (nbrBlock + 1)));
            }
            temp = new JSONArray();
            temp = (JSONArray) map.get("block"+(nbrBlock-1));
            if (!temp.get(0).toString().equals(Integer.toString(route))/* || !map.get("block"+(nbrBlock-1)).equals(immeuble)*/) {
                blocks = new JSONArray();
                blocks.add(canBuild);
                blocks.add(MouseCoord[0] - 1);
                blocks.add(MouseCoord[1]);
                map.replace("block" + (nbrBlock - 1), blocks);
            }
            temp = new JSONArray();
            temp = (JSONArray) map.get("block"+((MouseCoord[1] - 1) * (mouseX.size() + 1) + MouseCoord[0]));
            if (!temp.get(0).toString().equals(Integer.toString(route))/* || !map.get("block"+((MouseCoord[1] - 1) * (mouseX.size() + 1) + MouseCoord[0])).equals(immeuble)*/) {
                blocks = new JSONArray();
                blocks.add(canBuild);
                blocks.add(MouseCoord[0]);
                blocks.add(MouseCoord[1] - 1);
                map.replace("block" + ((MouseCoord[1] - 1) * (mouseX.size() + 1) + MouseCoord[0]), blocks);
            }
            temp = new JSONArray();
            temp = (JSONArray) map.get("block"+((MouseCoord[1] + 1) * (mouseX.size() + 1) + MouseCoord[0]));
            if (!temp.get(0).toString().equals(Integer.toString(route))/* || !map.get("block"+((MouseCoord[1] + 1) * (mouseX.size() + 1) + MouseCoord[0])).equals(immeuble)*/) {
                blocks = new JSONArray();
                blocks.add(canBuild);
                blocks.add(MouseCoord[0]);
                blocks.add(MouseCoord[1] + 1);
                map.replace("block" + ((MouseCoord[1] + 1) * (mouseX.size() + 1) + MouseCoord[0]), blocks);
            }
            temp = new JSONArray();
            temp = (JSONArray) map.get("block"+((MouseCoord[1] + 1) * (mouseX.size() + 1) + MouseCoord[0] + 1));
            if (!temp.get(0).toString().equals(Integer.toString(route))/* || !map.get("block"+((MouseCoord[1] + 1) * (mouseX.size() + 1) + MouseCoord[0] + 1)).equals(immeuble)*/) {
                blocks = new JSONArray();
                blocks.add(canBuild);
                blocks.add(MouseCoord[0] + 1);
                blocks.add(MouseCoord[1] + 1);
                map.replace("block" + ((MouseCoord[1] + 1) * (mouseX.size() + 1) + MouseCoord[0] + 1), blocks);
            }
            temp = new JSONArray();
            temp = (JSONArray) map.get("block"+((MouseCoord[1] - 1) * (mouseX.size() + 1) + MouseCoord[0] - 1));
            if (!temp.get(0).toString().equals(Integer.toString(route))/* || !map.get("block"+((MouseCoord[1] - 1) * (mouseX.size() + 1) + MouseCoord[0] - 1)).equals(immeuble)*/) {
                blocks = new JSONArray();
                blocks.add(canBuild);
                blocks.add(MouseCoord[0] - 1);
                blocks.add(MouseCoord[1] - 1);
                map.replace("block" + ((MouseCoord[1] - 1) * (mouseX.size() + 1) + MouseCoord[0] - 1), blocks);
            }
            temp = new JSONArray();
            temp = (JSONArray) map.get("block"+((MouseCoord[1] - 1) * (mouseX.size() + 1) + MouseCoord[0] + 1));
            if (!temp.get(0).toString().equals(Integer.toString(route))/* || !map.get("block"+((MouseCoord[1] - 1) * (mouseX.size() + 1) + MouseCoord[0] + 1)).equals(immeuble)*/) {
                blocks = new JSONArray();
                blocks.add(canBuild);
                blocks.add(MouseCoord[0] + 1);
                blocks.add(MouseCoord[1] - 1);
                map.replace("block" + ((MouseCoord[1] - 1) * (mouseX.size() + 1) + MouseCoord[0] + 1), blocks);
            }
            temp = new JSONArray();
            temp = (JSONArray) map.get("block"+((MouseCoord[1] + 1) * (mouseX.size() + 1) + MouseCoord[0] - 1));
            if (!temp.get(0).toString().equals(Integer.toString(route))/* || !map.get("block"+((MouseCoord[1] + 1) * (mouseX.size() + 1) + MouseCoord[0] - 1)).equals(immeuble)*/) {
                blocks = new JSONArray();
                blocks.add(canBuild);
                blocks.add(MouseCoord[0] - 1);
                blocks.add(MouseCoord[1] + 1);
                map.replace("block" + ((MouseCoord[1] + 1) * (mouseX.size() + 1) + MouseCoord[0] - 1), blocks);
            }
        } else if (currentTool.equals("Immeuble")){
            JSONArray temp = (JSONArray) map.get("block"+nbrBlock);
            if (temp.get(0).toString().equals(Integer.toString(-1))) {
                blocks.add(immeuble);
                blocks.add(MouseCoord[0]);
                blocks.add(MouseCoord[1]);
                map.replace("block" + nbrBlock, blocks);
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if(!clicked)
            clicked = true;
        mouseUpdate(e);
        setStructure();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (clicked)
            clicked = false;
        mouseUpdate(e);
    }

    @Override
    public void mouseClicked(MouseEvent e) { }

    @Override
    public void mousePressed(MouseEvent e) {
        if(!clicked)
            clicked = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (clicked)
            clicked = false;
        setStructure();
    }
    @Override
    public void mouseEntered(MouseEvent e) { }
    @Override
    public void mouseExited(MouseEvent e) { }
}