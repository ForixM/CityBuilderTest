package ma.forix.citybuilder;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Iterator;

public class Map extends JPanel implements MouseListener, MouseMotionListener {
    private int scale = 50;
    private int MouseCoord[] = {0, 0};
    private int mapSize[] = {0, 0};
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
        for(int y = 0; y < getHeight() / scale; y++){
            mapSize[1]++;
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
            mapSize[0]++;
        }
        System.out.println(map.toString());
    }

    public void newMap(){
        generateMap();
    }

    public JSONObject getMap(){
        return map;
    }

    public int getScale(){
        return scale;
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
                    g.setColor(Color.lightGray);
                    g.drawRect(Bx * scale + Bx, By * scale + By, scale, scale);
                }
                loop++;
            }
        }
    }

    public void setMouseCoord(int mouseC[]){
        this.MouseCoord = mouseC;
    }

    public void setStructure(){
        int nbrBlock = MouseCoord[1]*(mapSize[0]+1)+MouseCoord[0];
        blocks = new JSONArray();
        if (currentTool.equals("Route")){
            blocks.add(route);
            blocks.add(MouseCoord[0]);
            blocks.add(MouseCoord[1]);
            map.replace("block"+nbrBlock, blocks);
            JSONArray temp;
            temp = (JSONArray) map.get("block"+(nbrBlock+1));
            if (!temp.get(0).toString().equals(Integer.toString(route))) {
                blocks = new JSONArray();
                blocks.add(canBuild);
                blocks.add(MouseCoord[0] + 1);
                blocks.add(MouseCoord[1]);
                map.replace("block" + (nbrBlock + 1), blocks);
            }
            temp = (JSONArray) map.get("block"+(nbrBlock-1));
            if (!temp.get(0).toString().equals(Integer.toString(route))) {
                blocks = new JSONArray();
                blocks.add(canBuild);
                blocks.add(MouseCoord[0] - 1);
                blocks.add(MouseCoord[1]);
                map.replace("block" + (nbrBlock - 1), blocks);
            }
            temp = (JSONArray) map.get("block"+((MouseCoord[1] - 1) * (mapSize[0] + 1) + MouseCoord[0]));
            if (!temp.get(0).toString().equals(Integer.toString(route))) {
                blocks = new JSONArray();
                blocks.add(canBuild);
                blocks.add(MouseCoord[0]);
                blocks.add(MouseCoord[1] - 1);
                map.replace("block" + ((MouseCoord[1] - 1) * (mapSize[0] + 1) + MouseCoord[0]), blocks);
            }
            temp = (JSONArray) map.get("block"+((MouseCoord[1] + 1) * (mapSize[0] + 1) + MouseCoord[0]));
            if (!temp.get(0).toString().equals(Integer.toString(route))) {
                blocks = new JSONArray();
                blocks.add(canBuild);
                blocks.add(MouseCoord[0]);
                blocks.add(MouseCoord[1] + 1);
                map.replace("block" + ((MouseCoord[1] + 1) * (mapSize[0] + 1) + MouseCoord[0]), blocks);
            }
            temp = (JSONArray) map.get("block"+((MouseCoord[1] + 1) * (mapSize[0] + 1) + MouseCoord[0] + 1));
            if (!temp.get(0).toString().equals(Integer.toString(route))) {
                blocks = new JSONArray();
                blocks.add(canBuild);
                blocks.add(MouseCoord[0] + 1);
                blocks.add(MouseCoord[1] + 1);
                map.replace("block" + ((MouseCoord[1] + 1) * (mapSize[0] + 1) + MouseCoord[0] + 1), blocks);
            }
            temp = (JSONArray) map.get("block"+((MouseCoord[1] - 1) * (mapSize[0] + 1) + MouseCoord[0] - 1));
            if (!temp.get(0).toString().equals(Integer.toString(route))) {
                blocks = new JSONArray();
                blocks.add(canBuild);
                blocks.add(MouseCoord[0] - 1);
                blocks.add(MouseCoord[1] - 1);
                map.replace("block" + ((MouseCoord[1] - 1) * (mapSize[0] + 1) + MouseCoord[0] - 1), blocks);
            }
            temp = (JSONArray) map.get("block"+((MouseCoord[1] - 1) * (mapSize[0] + 1) + MouseCoord[0] + 1));
            if (!temp.get(0).toString().equals(Integer.toString(route))) {
                blocks = new JSONArray();
                blocks.add(canBuild);
                blocks.add(MouseCoord[0] + 1);
                blocks.add(MouseCoord[1] - 1);
                map.replace("block" + ((MouseCoord[1] - 1) * (mapSize[0] + 1) + MouseCoord[0] + 1), blocks);
            }
            temp = (JSONArray) map.get("block"+((MouseCoord[1] + 1) * (mapSize[0] + 1) + MouseCoord[0] - 1));
            if (!temp.get(0).toString().equals(Integer.toString(route))) {
                blocks = new JSONArray();
                blocks.add(canBuild);
                blocks.add(MouseCoord[0] - 1);
                blocks.add(MouseCoord[1] + 1);
                map.replace("block" + ((MouseCoord[1] + 1) * (mapSize[0] + 1) + MouseCoord[0] - 1), blocks);
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
        setStructure();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (clicked)
            clicked = false;
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