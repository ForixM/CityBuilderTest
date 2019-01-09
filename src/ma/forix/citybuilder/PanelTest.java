package ma.forix.citybuilder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

public class PanelTest extends JPanel implements MouseListener, MouseMotionListener {
    Map map;
    private ArrayList<Integer> mouseX;
    private ArrayList<Integer> mouseY;
    private int MouseCoord[] = {0, 0};
    private boolean clicked = false;
    private int scale;
    public PanelTest(Map map){
        this.map = map;
        scale = map.getScale();
        System.out.println(scale);
        System.out.println(getWidth());
        System.out.println(getHeight());
        addMouseListener(this);
        addMouseMotionListener(this);
        mouseX = new ArrayList<>();
        mouseY = new ArrayList<>();
        for (int y = 0; y < 550/scale; y++){
            mouseY.add(0);
        }

        for (int x = 0; x < 1000/scale; x++){
            mouseX.add(0);
        }
        System.out.println(mouseX);
        System.out.println(mouseY);
    }

    public void paintComponent(Graphics g){
        for (int y = 0; y < getHeight()/scale; y++) {
            for (int x = 0; x < getWidth()/scale; x++) {
                if (mouseX.get(x) == -1 && mouseY.get(y) == -1) {
                    if (clicked)
                        g.setColor(Color.YELLOW);
                    if (!clicked)
                        g.setColor(Color.GREEN);
                    g.drawRect(x * scale + x, y * scale + y, scale, scale);
                }
            }
        }
    }

    private void mouseUpdate(MouseEvent e){
        if (MouseCoord[0] >= 0 && MouseCoord[0] <= getWidth()/scale-1 && MouseCoord[1] >= 0 && MouseCoord[1] <= getHeight()/scale-1) {
            mouseX.set(MouseCoord[0], 0);
            mouseY.set(MouseCoord[1], 0);
            map.setMouseCoord(MouseCoord);
        }
        MouseCoord[0] = e.getX() / (scale+1);
        MouseCoord[1] = e.getY() / (scale+1);
        map.setMouseCoord(MouseCoord);
        if (MouseCoord[0] >= 0 && MouseCoord[0] <= getWidth()/scale-1 && MouseCoord[1] >= 0 && MouseCoord[1] <= getHeight()/scale-1) {
            mouseX.set(MouseCoord[0], -1);
            mouseY.set(MouseCoord[1], -1);
            map.setMouseCoord(MouseCoord);
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
        map.setStructure();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if(!clicked)
            clicked = true;
        map.setStructure();
        mouseUpdate(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (clicked)
            clicked = false;
        mouseUpdate(e);
    }
}
