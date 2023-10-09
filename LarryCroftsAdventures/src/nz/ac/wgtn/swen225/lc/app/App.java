package nz.ac.wgtn.swen225.lc.app;

import nz.ac.wgtn.swen225.lc.domain.Board;
import nz.ac.wgtn.swen225.lc.domain.items.Item;
import nz.ac.wgtn.swen225.lc.domain.items.Key;
import nz.ac.wgtn.swen225.lc.domain.tiles.KeyTile;
import nz.ac.wgtn.swen225.lc.renderer.AudioUnit;
import nz.ac.wgtn.swen225.lc.renderer.Renderer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

/**
 * This class represents the main application panel for a game.
 * It includes various panels for displaying game information and inventory.
 */

public class App extends JPanel implements ActionListener {

    private JLabel timeLabel;
    public JLabel treasureLabel = new JLabel();
    JLabel[][] inventorySlots = new JLabel[2][4];

    private int time;
    private static Renderer renderer;
    JPanel leftPanel;
    JPanel rightPanel;
    static Renderer centrePanel;
    static Board board;
    private AudioUnit audioUnit;

    private int treasureLeft;

    /**
     * Constructor for the App class.
     * Initializes the game board and sets up the graphical user interface.
     */
    public App() {
        Timer timer = new Timer(1000, this);
        timer.setInitialDelay(650);
        timer.start();
        setup(new File("LarryCroftsAdventures/levels/level1.json"));
    }

    /**
     * Performs the initial setup of the game interface and components.
     */
    public void setup(File file) {
        if (centrePanel != null) {
            this.remove(centrePanel);
        } // if there has previously been a Renderer created, remove its corresponding panel from the App JPanel
        if (audioUnit != null) {
            audioUnit.stopAll();
        } // if there has previously been an AudioUnit created, stop all the clips in it from playing before creating a new one

        audioUnit = new AudioUnit();
        audioUnit.startBackgroundMusic();
        audioUnit.startAmbience();
        board = new Board(file, audioUnit);

        try {
            centrePanel = new Renderer(board, 9, audioUnit, this);
        } catch (IOException e) {
            System.err.println("Error: An image has not read properly");
            e.printStackTrace();
        }

        leftPanel = new JPanel(new GridLayout(2, 0, 0, 10));
        rightPanel = new JPanel(new GridLayout(9, 0, 0, 10));
        centrePanel.setBorder(BorderFactory.createLineBorder(Color.black));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));


        createTimer();

        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(1400, 800));
        leftPanel.setPreferredSize(new Dimension(300, 700));
        leftPanel.setBackground(Color.lightGray);
        leftPanel.add(new RecorderPanel(this));

        rightPanel.setPreferredSize(new Dimension(300, 700));
        rightPanel.setBackground(Color.lightGray);
        Font font = new Font("Sans-Serif", Font.BOLD, 40);


        rightPanel.add(createLabel("LEVEL", font, Color.lightGray, Color.red));
        rightPanel.add(createLabel("" + board.getLevel(), font, Color.black, Color.green)); // get the level to display eventually
        rightPanel.add(createLabel("TIME", font, Color.lightGray, Color.red));
        rightPanel.add(timeLabel);
        rightPanel.add(createLabel("TREASURE", font, Color.lightGray, Color.red));
        rightPanel.add(treasureLabel = createLabel("" + (treasureLeft - board.getChap().getCurrentTreasure()), font, Color.black, Color.green)); //get the chips left

        rightPanel.add(createInventory(0));
        rightPanel.add(createInventory(1));


        this.add(centrePanel, BorderLayout.CENTER);
        this.add(leftPanel, BorderLayout.WEST);
        this.add(rightPanel, BorderLayout.EAST);


    }

    /**
     * Creates and returns a panel for displaying the player's inventory.
     *
     * @param index The index of the inventory to create (0 or 1).
     * @return A JPanel containing slots for the player's inventory items.
     */

    public JPanel createInventory(int index) {
        JPanel grid = new JPanel(new GridLayout(1, 4, 5, 5));
        grid.setPreferredSize(new Dimension(200, 100)); // Increase the width to accommodate square slots
        grid.setBackground(Color.lightGray);


        for (int j = 0; j < 4; j++) {
            inventorySlots[index][j] = new JLabel();
            inventorySlots[index][j].setPreferredSize(new Dimension(100, 100)); // Make each slot a square
            inventorySlots[index][j].setBackground(Color.white);
            inventorySlots[index][j].setOpaque(true);
            inventorySlots[index][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));

            grid.add(inventorySlots[index][j]);
        }

        return grid;
    }

    /**
     * Updates the inventory slots with items and their respective icons.
     *
     * @param items A 2D boolean array representing the inventory items.
     */



    public void updateInventory(Item[][] items) throws IOException {
        String path = "LarryCroftsAdventures/assets/";

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 4; j++) {
                if (items[i][j] != null) {
                    Key.Colour keyColour = ((Key) items[i][j]).getColour();
                    String keyImagePath = path + "Key_Blue.png"; // Default image path

                    // Determine the image path based on the keyColour
                    switch (keyColour) {
                        case RED:
                            keyImagePath = path + "Key_Red.png";
                            break;
                        case BLUE:
                            keyImagePath = path + "Key_Blue.png";
                            break;
                        case GREEN:
                            keyImagePath = path + "Key_Green.png";
                            break;
                        case YELLOW:
                            keyImagePath = path + "Key_Yellow.png";
                            break;
                    }


                    // Check if inventorySlots[i][j] is not null
                    if (items[i][j] != null) {
                        //System.out.println(keyImagePath);

                        // Load the image and convert it to an ImageIcon
                        ImageIcon keyIcon = new ImageIcon(keyImagePath);
                        inventorySlots[i][j].setIcon(keyIcon);
                    }
                }
            }
        }
    }





    /**
     * Creates and returns a JLabel with specified properties.
     *
     * @param title      The text to display on the label.
     * @param font       The font for the label's text.
     * @param bg         The background color of the label.
     * @param fontColour The text color of the label.
     * @return A JLabel with the specified properties.
     */
    public JLabel createLabel(String title, Font font, Color bg, Color fontColour) {
        JLabel label = new JLabel(title);
        label.setFont(font);
        label.setForeground(fontColour);
        label.setBackground(bg);
        label.setBorder(BorderFactory.createLineBorder(Color.black));
        label.setPreferredSize(new Dimension(200, 100));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setOpaque(true);
        return label;
    }

    /**
     * Creates and initializes a timer for tracking game time.
     */

    private void createTimer() {
        time = board.getTime();
        Font font = new Font("Sans-Serif", Font.BOLD, 50);
        timeLabel = createLabel("0", font, Color.black, Color.green);
        add(timeLabel);

    }

    /**
     * Handles action events triggered by the timer.
     *
     * @param e The ActionEvent object representing the timer event.
     */

    @Override
    public void actionPerformed(ActionEvent e) {
        if (time > 0) {
            time--;
        } else {
            //end the game

            JOptionPane.showMessageDialog(null,
                    "Time's up! Do you want to replay the current level?",
                    "Game Over",
                    JOptionPane.PLAIN_MESSAGE);
            time = board.getTime();

            //replay level
        }

        timeLabel.setText("" + time);
    }

    /**
     * Gets the renderer used for drawing the game board.
     *
     * @return The Renderer object responsible for rendering the game board.
     */
    public static Renderer getRenderer() {
        return centrePanel;
    }

    /**
     * Gets the game board instance.
     *
     * @return The Board object representing the game board.
     */
    public static Board getBoard() {
        return board;
    }


}
