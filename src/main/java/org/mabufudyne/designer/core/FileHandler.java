package org.mabufudyne.designer.core;

import java.io.*;

class FileHandler {

    /** Saves the given Adventure to the given location
     * @param adv An Adventure instance to be saved
     * @param path The location where the file should be saved
     * @return true if the save was successful, false otherwise
     */
    boolean saveAdventure(Adventure adv, String path) {
        path = path.endsWith(".adv") ? path : path + ".adv";

        try (FileOutputStream fileOut = new FileOutputStream(path);
            ObjectOutputStream out = new ObjectOutputStream(fileOut))
        {
            out.writeObject(adv);
            return true;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Loads Adventure from a file found at the given path
     * @param filePath The location of the Adventure (*.adv) file
     * @return Adventure instance if successful, null if not
     */
    Adventure loadAdventure(String filePath) {
        Adventure loadedAdv = null;

        try (FileInputStream fileIn = new FileInputStream(filePath);
             ObjectInputStream in = new ObjectInputStream(fileIn))
        {
            loadedAdv = (Adventure) in.readObject();
            return loadedAdv;
        }
        catch (IOException | ClassNotFoundException | ClassCastException e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
