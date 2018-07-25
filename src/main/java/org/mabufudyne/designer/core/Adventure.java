package org.mabufudyne.designer.core;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;

public class Adventure implements Serializable {

    private static String DEFAULT_NAME = "New Adventure";
    private static String DEFAULT_STORYPIECE_TITLE = "Untitled";
    private static ArrayList<Integer> availableOrders = new ArrayList<>();

    private ArrayList<StoryPiece> storyPieces;
    private String name;

    /** Constructors **/

    public Adventure() {
        this(DEFAULT_NAME);
    }

    public Adventure(String name) {
        this.name = name;
        this.storyPieces = new ArrayList<>();

        this.createNewStoryPiece(DEFAULT_STORYPIECE_TITLE);
    }

    /** Overriden methods **/

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof Adventure)) return false;

        Adventure other = (Adventure) obj;
        ArrayList<StoryPiece> otherStoryPieces = other.getStoryPieces();

        if (!name.equals(other.getName())) return false;
        if (storyPieces.size() != otherStoryPieces.size()) return false;

        for (int i = 0; i < storyPieces.size(); i++) {
            StoryPiece thisSP = storyPieces.get(i);
            StoryPiece otherSP = storyPieces.get(i);
            if (!thisSP.equals(otherSP)) return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(storyPieces, name);
    }

    /** Getters and Setters **/

    public String getName() {
        return this.name;
    }

    public  ArrayList<StoryPiece> getStoryPieces() {
        return this.storyPieces;
    }

    public static String getDefaultName() {
        return DEFAULT_NAME;
    }

    public static String getDefaultStoryPieceTitle() { return DEFAULT_STORYPIECE_TITLE; }

    /** Private helper methods **/

    private void sortStoryPiecesByOrder() {
        Collections.sort(storyPieces, Comparator.comparing(StoryPiece::getOrder));
    }

    private int obtainNextStoryPieceOrder() {
        return availableOrders.isEmpty() ? storyPieces.size() + 1 : availableOrders.remove(0);
    }

    private void freeUpOrder(int order) {
        availableOrders.add(order);
        Collections.sort(availableOrders);
    }

    private ArrayList<Integer> obtainShuffableOrders() {
        ArrayList<Integer> shuffableOrders = new ArrayList<>();

        for (StoryPiece sp : storyPieces) {
            if (!sp.isFixed()) shuffableOrders.add(sp.getOrder());
        }

        return shuffableOrders;
    }

    private void reassignStoryPieceOrders(ArrayList<Integer> newOrders) {
        for (StoryPiece sp : storyPieces) {
            if (!sp.isFixed()) sp.setOrder(newOrders.remove(0));
        }
    }

    /** Public methods **/

    public StoryPiece createNewStoryPiece(String title) {
        StoryPiece newSP = new StoryPiece(title, obtainNextStoryPieceOrder());
        storyPieces.add(newSP);
        sortStoryPiecesByOrder();
        return newSP;
    }

    public StoryPiece createNewStoryPiece() {
        return createNewStoryPiece(DEFAULT_STORYPIECE_TITLE);
    }

    public void removeStoryPiece(StoryPiece sp) {
        if (storyPieces.size() > 1) {
            freeUpOrder(sp.getOrder());
            this.storyPieces.remove(sp);
        }
    }

    public void switchStoryPieceOrder(StoryPiece firstSP, int newOrder) {
        if (newOrder > storyPieces.size() || newOrder <= 0) {
            throw new IllegalArgumentException("The requested new order is out of range (1-Number of existing StoryPieces).");
        }

        for (StoryPiece sp : storyPieces) {
            if (sp.getOrder() == newOrder) {
                int temp = firstSP.getOrder();
                firstSP.setOrder(sp.getOrder());
                sp.setOrder(temp);
                return;
            }
        }
    }

    public void shuffleStoryPieces() {
        ArrayList<StoryPiece> storyPiecesOriginalOrder = new ArrayList<>(storyPieces);
        ArrayList<Integer> shuffableOrders;
        boolean sameResultOrder = true;

        while (sameResultOrder) {
            shuffableOrders = obtainShuffableOrders();
            // If there is less than two shuffable orders, there is nothing to be done
            if (shuffableOrders.size() <= 1) return;

            Collections.shuffle(shuffableOrders);
            reassignStoryPieceOrders(shuffableOrders);
            sortStoryPiecesByOrder();

            // If the result of the shuffle is the same StoryPiece order as before, we will repeat the process
            sameResultOrder = storyPiecesOriginalOrder.equals(storyPieces);
        }


    }

    public void save(String location, String fileName) {
        try {
            if (!fileName.endsWith(".adv")) {
                fileName = fileName + ".adv";
            }

            FileOutputStream fileOut = new FileOutputStream(location + File.separator + fileName);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this);
            out.close();
            fileOut.close();
        }
        catch (IOException e) {
            System.out.println("Encountered an error while saving the Adventure");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public static Adventure load(String filePath) {
        Adventure loadedAdv = null;

        try {
            FileInputStream fileIn = new FileInputStream(filePath);
            ObjectInput in = new ObjectInputStream(fileIn);
            loadedAdv = (Adventure) in.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("File at the given path does not exist: " + filePath);
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Error while trying to load the file");
            System.out.println(e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("The given file cannot be loaded as an Adventure object");
            e.printStackTrace();
        } finally {
            return loadedAdv;
        }
    }


}
