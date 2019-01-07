package ma.forix.citybuilder;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControlPanel extends JPanel {
    JButton construction, finances, sauvegarder, charger, newParty, quitter;
    JButton route, immeuble, retour;

    Map map;
    Save save;
    Load load;

    public ControlPanel(Map map){
        this.map = map;
        save = new Save();
        load = new Load();

        construction = new JButton("Construction");
        construction.setVisible(true);
        construction.setBounds(20, 5, 100, 40);
        construction.addActionListener(new ConstructionL());

        finances = new JButton("Finances");
        finances.setVisible(true);
        finances.setBounds(150, 5, 100, 40);

        sauvegarder = new JButton("Sauvegarder");
        sauvegarder.setVisible(true);
        sauvegarder.setBounds(280, 5, 100, 40);
        sauvegarder.addActionListener(new SauvegarderL());

        charger = new JButton("Charger");
        charger.setVisible(true);
        charger.setBounds(410, 5, 100, 40);
        charger.addActionListener(new ChargerL());

        newParty = new JButton("Nouvelle Partie");
        newParty.setVisible(true);
        newParty.setBounds(540, 5, 100, 40);
        newParty.addActionListener(new newPartyL());

        quitter = new JButton("Quitter");
        quitter.setBounds(670, 5, 100 ,40);
        quitter.addActionListener(new QuitterL());

        route = new JButton("Route");
        route.setVisible(false);
        route.setBounds(20, 5, 100, 40);
        route.addActionListener(new RouteL());

        immeuble = new JButton("Immeuble");
        immeuble.setVisible(false);
        immeuble.setBounds(150, 5, 100, 40);
        immeuble.addActionListener(new ImmeubleL());

        retour = new JButton("Retour");
        retour.setVisible(false);
        retour.setBounds(280, 5, 100, 40);
        retour.addActionListener(new RetourL());

        add(construction);
        add(finances);
        add(sauvegarder);
        add(charger);
        add(newParty);
        add(quitter);
        add(route);
        add(immeuble);
        add(retour);
    }

    private class QuitterL implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

    private class ConstructionL implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            construction.setVisible(false);
            finances.setVisible(false);
            sauvegarder.setVisible(false);
            charger.setVisible(false);
            newParty.setVisible(false);
            quitter.setVisible(false);

            route.setVisible(true);
            immeuble.setVisible(true);
            retour.setVisible(true);
        }
    }

    private class RouteL implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            map.setCurrentTool("Route");
        }
    }

    private class ImmeubleL implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            map.setCurrentTool("Immeuble");
        }
    }

    private class RetourL implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            route.setVisible(false);
            immeuble.setVisible(false);
            retour.setVisible(false);

            construction.setVisible(true);
            finances.setVisible(true);
            sauvegarder.setVisible(true);
            charger.setVisible(true);
            newParty.setVisible(true);
            quitter.setVisible(true);
        }
    }

    private class SauvegarderL implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            save.save(map);
        }
    }

    private class ChargerL implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            load.load(map);
        }
    }

    private class newPartyL implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            map.newMap();
        }
    }
}
