package ui;

import model.EventLog;
import model.Media;
import model.MediaSortExe;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.table.DefaultTableModel;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;

//GUI for Media Sorting app
public class MediaSortMenu extends JFrame {
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 600;

    private final MediaSortExe exe;
    private final JDesktopPane desktop;
    private JPanel mainPanel;
    private JInternalFrame displayList;
    private JInternalFrame options;
    private final BufferedImage backgroundImg = ImageIO.read(new File("./data/background.jpg"));

    private JTable listTable;
    private DefaultTableModel allTableModel;
    private JScrollPane allTablesScrPane;
    private final String[] allTablesColumnNames = {"Index","Name"};
    private String[][] allTablesData;
    private int allTablesCurrentRow = 1;

    private JInternalFrame mediaDisplay;
    private JTable mediaTable;
    private DefaultTableModel mediaDTM;
    private JScrollPane mediaScrPane;
    private final String[] mediaColumnNames = {"Index","Name", "Media Type", "Genre", "Length"};
    private final String[][] currentListData = new String[20][5];
    private int mediaCurrentRow = 0;

    private JPanel displayPanel;

    private boolean loaded;

    /**
     * Implementation of background from https://stackoverflow.com/questions/13814704/how-to-change-jdesktoppane-default-background-image
     */
    /*
    EFFECTS: creates GUI for MediaSort
     */
    public MediaSortMenu() throws IOException {
        exe = new MediaSortExe();
        desktop = new JDesktopPane() {

            @Override
            protected void paintComponent(Graphics img) {
                super.paintComponent(img);
                img.drawImage(backgroundImg, 0, 0, null);
            }

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(backgroundImg.getWidth(), backgroundImg.getHeight());
            }
        };
        begin();
    }



    /*
    EFFECTS: creates GUI of app including options tab, desktop, and displayList
     */
    public void begin() {
        desktop.addMouseListener(new DesktopFocusAction());

        options = new JInternalFrame("Options", false, false, false, false);
        options.setLayout(new BorderLayout());
        desktop.add(options);
        options.setBounds(0,HEIGHT / 2, WIDTH, HEIGHT);
        options.setSize(WIDTH, 200);
        options.setVisible(true);

        setContentPane(desktop);
        setTitle("Media Sort Menu");
        setSize(WIDTH, HEIGHT);

        addButtons();

        displayList = new JInternalFrame("Media Lists", false, false, false, false);
        displayList.setLayout(new BorderLayout());
        displayList.setSize(WIDTH, 300);
        displayList.setVisible(true);
        desktop.add(displayList);

        addDisplayLists();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }


    /**
     * Helper to add control buttons.
     */
    /*
    EFFECTS: creates JPanel with buttons for new list, select, display list, load, save, event log and exit
     */
    private void addButtons() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(3, 3));
        mainPanel.setSize(WIDTH, 200);
        addNewList();
        addSelect();
        addDisplay();
        addLoad();
        addSave();
        addEventLog();
        addExit();

        options.add(mainPanel, BorderLayout.CENTER);

    }

    /**
     * Helper to add control buttons.
     */
    /*
    EFFECTS: creates JPanel for with buttons for new list, select, display list, load, save, event log and exit
     */
    public void addDisplayLists() {
        allTablesData = new String[14][2];
        for (int n = 1; n < allTablesData.length; ++n) {
            allTablesData[n][0] = String.valueOf(n - 1);
        }
        allTablesData[0][0] = "Index";
        allTablesData[0][1] = "Table Name";
        allTableModel = new DefaultTableModel(allTablesData, allTablesColumnNames);
        listTable = new JTable(allTableModel);
        allTablesScrPane = new JScrollPane(listTable);
        displayList.add(allTablesScrPane, BorderLayout.CENTER);
    }

    /*
    REQUIRES: name is a valid string
    MODIFIES: exe
    EFFECTS: creates new list in exe and makes internal frame window for list appear
     */
    public void setList(String name) throws PropertyVetoException {
        exe.newList(name);
        mediaListTable(name);
    }

    /*
    MODIFIES: exe, mainPanel
    EFFECTS: adds new list button that creates a new list
     */
    public void addNewList() {
        JButton newListBtn = new JButton("New List");
        mainPanel.add(newListBtn);
        newListBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String listName = JOptionPane.showInputDialog(null,
                        "Input List Name",
                        "Add New List",
                        JOptionPane.QUESTION_MESSAGE);
                try {
                    setList(listName);
                } catch (PropertyVetoException ex) {
                    System.out.println("Whoops");
                }
                updateDisplayList(listName);
            }
        });
    }

    /*
    MODIFIES: exe, mainPanel
    EFFECTS: adds select list button that selects list from index
     */
    public void addSelect() {
        JButton selectBtn = new JButton("Select List");
        mainPanel.add(selectBtn);
        selectBtn.addActionListener(e -> {
            String listIndex = JOptionPane.showInputDialog(null,
                    "Input List Index",
                    "Select New List",
                    JOptionPane.QUESTION_MESSAGE);
            exe.selectList(Integer.parseInt(listIndex));
            try {
                mediaListTable(exe.getCurrentList().getName());
                for (Media m : exe.getCurrentList()) {
                    if (m.getMediaType().equals("movie")) {
                        syncAddMedia(m.getName(), m.getGenre(), m.getMediaType(), 0);
                    } else {
                        syncAddMedia(m.getName(), m.getGenre(), m.getMediaType(), m.getLength());
                    }
                }
            } catch (PropertyVetoException ex) {
                System.out.println("Could not create new JInternal Frame");
            }
        });
    }

    /*
    EFFECTS: shows internal frame containing all media in current list
     */
    public void addDisplay() {
        JButton displayBtn = new JButton("Display Current List");
        mainPanel.add(displayBtn);
        displayBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    mediaListTable(exe.getCurrentList().getName());
                } catch (PropertyVetoException ex) {

                    System.out.println("Couldn't display current List");
                }
            }
        });
    }

    /*
    EFFECTS: adds button that loads all media from saved lists
     */
    public void addLoad() {
        JButton loadBtn = new JButton("Load Saved Lists");
        mainPanel.add(loadBtn);
        loadBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exe.loadLists();
                ArrayList<String> listNames = new ArrayList<>();
                for (String s : exe.getListNames()) {
                    updateDisplayList(s);
                }
                loaded = true;
            }
        });
    }

    /*
    MODIFIES: exe
    EFFECTS: adds button that saves all lists
     */
    public void addSave() {
        JButton saveBtn = new JButton("Save Current List");
        mainPanel.add(saveBtn);
        saveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exe.saveCurrentList();
            }
        });

    }

    /*
    EFFECTS: adds button that prints log to command line and exits app
     */
    public void addExit() {
        JButton exitBtn = new JButton("Exit App");
        mainPanel.add(exitBtn);
        exitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exe.printLog();
                System.exit(0);
            }
        });
    }

    /*
    EFFECTS: adds event log button to JPanel that creates pop up window with log
    */
    public void addEventLog() {
        JButton eventLogBtn = new JButton("Pop up Event Log");
        mainPanel.add(eventLogBtn);
        eventLogBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LogPrinter printer;
                printer = new ScreenPrinter(MediaSortMenu.this);
                JFrame eventPanel = new JFrame();
                eventPanel.addMouseListener(new DesktopFocusAction());
                eventPanel.setSize(500, 500);
                eventPanel.setVisible(true);
                try {
                    printer.printLog(EventLog.getInstance());
                    eventPanel.add(((ScreenPrinter) printer));
                } catch (Exception l) {
                    System.out.println("Unable to print EventLog");
                }
            }
        });
    }

    /*
    REQUIRES: addedList is a valid string
    MODIFIES: allTableModel, allTablesData
    EFFECTS: updates table with new lists
     */
    public void updateDisplayList(String addedList) {
        allTablesData[allTablesCurrentRow][1] = addedList;
        allTableModel.setValueAt(addedList, allTablesCurrentRow,1);
        allTablesCurrentRow++;
    }

    /*
    REQUIRES: name is a valid string
    MODIFIES: desktop
    EFFECTS: creates new internal frame containing current list
     */
    public void mediaListTable(String name) throws PropertyVetoException {
        mediaDisplay = new JInternalFrame(name, false, true, false, false);
        mediaDisplay.setLayout(new BorderLayout());
        mediaDisplay.setSize(WIDTH, HEIGHT);
        desktop.add(mediaDisplay, CENTER_ALIGNMENT);
        mediaDisplay.setVisible(true);
        mediaDisplay.setClosed(false);

        for (int n = 0; n < 20; ++n) {
            currentListData[n][0] = String.valueOf(n);
        }

        mediaDTM = new DefaultTableModel(currentListData, mediaColumnNames);
        mediaTable = new JTable(mediaDTM);
        mediaScrPane = new JScrollPane(mediaTable);
        mediaDisplay.add(mediaScrPane, BorderLayout.NORTH);

        addDisplayButtons();

    }

    /*
    EFFECTS: creates buttonPanel with buttons for load, save, end, sync and eventLog and adds it to the options panel
     */
    public void addDisplayButtons() {
        displayPanel = new JPanel();
        displayPanel.setLayout(new GridLayout(1, 3));
        displayPanel.setSize(WIDTH,20);
        displayPanel.setVisible(true);
        mediaDisplay.add(displayPanel, BorderLayout.CENTER);
        addMediaButton();
        removeMediaButton();
        exitFrameButton();
    }

    /*
    MODIFIES: display panel
    EFFECTS: adds button to JPanel that takes user input and adds media to list
     */
    public void addMediaButton() {
        JButton addBtn = new JButton("Add Media");
        displayPanel.add(addBtn);
        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String mediaName = JOptionPane.showInputDialog(null,
                        "Media Name", "Add Media", JOptionPane.QUESTION_MESSAGE);
                String mediaGenre = JOptionPane.showInputDialog(null,
                        "Genre", "Add Media", JOptionPane.QUESTION_MESSAGE);
                String mediaType = JOptionPane.showInputDialog(null,
                        "Media Type", "Add Media", JOptionPane.QUESTION_MESSAGE);
                String mediaLength = "";
                if (mediaType.equals("series")) {
                    mediaLength = JOptionPane.showInputDialog(null,
                            "Media Length", "Add Media", JOptionPane.QUESTION_MESSAGE);
                } else {
                    mediaLength = "0";
                }
                addBtn.setVisible(true);
                syncAddMedia(mediaName,mediaGenre,mediaType,Integer.parseInt(mediaLength));
            }
        });
    }

    /*
    MODIFIES: exe
    EFFECTS: creates button that removes media from data
     */
    public void removeMediaButton() {
        JButton removeBtn = new JButton("Remove Media");
        displayPanel.add(removeBtn);
        removeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String index = JOptionPane.showInputDialog(null,
                        "Index of Media to Remove",
                        "Remove Media",
                        JOptionPane.QUESTION_MESSAGE);
                exe.removeMedia(Integer.parseInt(index));

                removeBtn.setVisible(true);
                for (int n = 1; n <= 4; n++) {
                    mediaTable.getModel().setValueAt("",Integer.parseInt(index), n);
                }
                mediaCurrentRow--;
            }
        });
    }

    /*
    MODIFIES: mediaDisplay
    EFFECTS: closes mediaDisplay
     */
    public void exitFrameButton() {
        JButton exitBtn = new JButton("Exit Current List");
        displayPanel.add(exitBtn);
        exitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    exitBtn.setVisible(true);
                    for (int m = 1; m < 20; m++) {
                        for (int n = 1; n <= 4; n++) {
                            mediaTable.getModel().setValueAt("", m, n);
                        }
                    }
                    mediaCurrentRow = 0;
                    mediaDisplay.setClosed(true);
                } catch (PropertyVetoException ex) {
                    System.out.println("Couldn't close display");
                }
            }
        });

    }

    /*
    REQUIRES: name, genre, mediaType, and length are valid inputs
    MODIFIES: exe, mediaTable
    EFFECTS: adds media to table and exe
     */
    public void syncAddMedia(String name, String genre, String mediaType, int length) {
        if (!loaded) {
            if (mediaType.equals("series")) {
                exe.addSeries(name, genre, length);
            } else {
                exe.addMovie(name, genre);
            }
        }
        currentListData[mediaCurrentRow][1] = name;
        currentListData[mediaCurrentRow][2] = genre;
        currentListData[mediaCurrentRow][3] = mediaType;
        currentListData[mediaCurrentRow][4] = String.valueOf(length);

        mediaDTM.setValueAt(name, mediaCurrentRow, 1);
        mediaDTM.setValueAt(genre, mediaCurrentRow, 2);
        mediaDTM.setValueAt(mediaType, mediaCurrentRow, 3);
        mediaDTM.setValueAt(length, mediaCurrentRow, 4);

        mediaDTM.fireTableDataChanged();
        mediaTable.setModel(mediaDTM);
        mediaCurrentRow++;
    }

    /**
     * Represents action to be taken when user clicks desktop
     * to switch focus. (Needed for key handling.)
     */
    /*
    EFFECTS: monitors system for mouse clicks
     */
    public class DesktopFocusAction extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            MediaSortMenu.this.requestFocusInWindow();
        }
    }
}
