package ma.forix.citybuilder;

import javax.swing.*;
import java.awt.*;
import java.awt.Container;
import java.awt.event.*;

public class Fenetre extends JFrame implements ActionListener, MouseListener, MouseMotionListener {

    private ContentPane contentPane;
    private Map map;
    private ControlPanel controlPanel;
    private PanelTest panelTest;

    public Fenetre(){
        contentPane = new ContentPane();
        setContentPane(contentPane);
        contentPane.setBackground(Color.gray);
        contentPane.setLayout(null);

        map = new Map();
        map.setSize(1000, 550);
        map.setVisible(true);
        map.setLayout(null);

        panelTest = new PanelTest(map);
        panelTest.setSize(1000, 550);
        panelTest.setVisible(true);
        panelTest.setLayout(null);
        panelTest.setOpaque(false);

        controlPanel = new ControlPanel(map);
        controlPanel.setSize(1000, 50);
        controlPanel.setBackground(Color.lightGray);
        controlPanel.setVisible(true);
        controlPanel.setLayout(null);

        addComponent(map, panelTest, 0, 0, 1000, 550);
        addComponent(contentPane, map, 0, 0, 1000, 550);
        addComponent(contentPane, controlPanel, 0, 550, 1000, 50);

        setTitle("City builder test");
        setSize(1005, 629);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        Trame();
    }

    public void addComponent(Container container, Component c, int x, int y, int w, int h){
        c.setBounds(x,y,w,h);
        container.add(c);
    }

    public void Trame(){
        while (true){
            map.repaint();
            panelTest.repaint();
            try {
                Thread.sleep(1000/60);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}