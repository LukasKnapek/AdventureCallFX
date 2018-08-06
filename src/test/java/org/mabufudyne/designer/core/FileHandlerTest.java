package org.mabufudyne.designer.core;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class FileHandlerTest {

    private Adventure defaultAdventure;
    private static File tempDirectory;

    @BeforeAll
    protected static void createTempFolderAndTestFiles() {
        String tempSaveFolderPath = System.getProperty("user.dir") + File.separator + "testSavingLoading";
        tempDirectory = new File(tempSaveFolderPath);
        tempDirectory.mkdir();
    }

    @AfterAll
    protected static void deleteTempFolder() {
        File[] files = tempDirectory.listFiles();
        if (files != null) {
            for (File f : files) {
                f.delete();
            }
        }
        tempDirectory.delete();
    }

    @BeforeEach
    protected void createDefaultObjects() {
        defaultAdventure = new Adventure();
    }

    @Test
    public void SaveAdventure_ShouldReturnNull_GivenAnExceptionOccursWhileSaving() {

        String savedFilePath = FileHandler.saveAdventure(defaultAdventure,"nonexistent-file-path", "blah.adv");

        assertEquals(null, savedFilePath,
                "The savedFilePath should be null in case save is not successful.");
    }

    @Test
    public void SaveAdventure_ShouldSaveTheAdventureToAFile_GivenTheSaveLocation() {

        String fileName = "TestAdventure.adv";
        FileHandler.saveAdventure(defaultAdventure, tempDirectory.toString(), fileName);

        File savedFile = new File(tempDirectory.toString() + File.separator + fileName);
        assertTrue(savedFile.exists(),
                "The saved Adventure file was not found for the given location and file name: " + savedFile.toString());
    }

    @Test
    public void SaveAdventure_ShouldSaveTheAdventureToAFileWithADVSuffix_GivenAFileNameWithNoSuffix() {
        String fileName = "TestAdventure";
        FileHandler.saveAdventure(defaultAdventure, tempDirectory.toString(), fileName);

        File savedFile = new File(tempDirectory.toString() + File.separator + fileName + ".adv");
        assertTrue(savedFile.exists(),
                "The saved Adventure file is missing the adv suffix that should have been automatically appended.");
    }

    @Test
    public void LoadAdventure_ShouldLoadTheAdventureAndReturnIt_GivenAPathToValidSavedAdventureFile() {
        String saveName = "ToBeLoaded.adv";
        FileHandler.saveAdventure(defaultAdventure, tempDirectory.toString(), saveName);

        Object loadedAdv = FileHandler.loadAdventure(tempDirectory.toString() + File.separator + saveName);
        assertTrue(loadedAdv instanceof Adventure,
                "The saved Adventure was not loaded as an Adventure object.");
    }

    @Test
    public void LoadAdventure_ShouldReturnNull_GivenANonExistentFilePath() {
        assertEquals(null, FileHandler.loadAdventure(tempDirectory + File.separator + "NonsenseBla.adv"),
                "Load did not return null given a nonexistent file path.");
    }

    @Test
    public void LoadAdventure_ShouldReturnNull_GivenAnInvalidAdventureFile() throws IOException {
        String fileName = "NotActuallyAnAdventure.adv";
        FileOutputStream fileOut = new FileOutputStream(tempDirectory + File.separator + fileName);
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject("I am just a plain string!");
        out.close();
        fileOut.close();
        assertEquals(null, FileHandler.loadAdventure(tempDirectory + File.separator + fileName));
    }

    @Test
    public void LoadAdventure_ShouldSetTheLoadedAdventureAsActive() {
        String saveName = "ToBeLoaded.adv";
        FileHandler.saveAdventure(defaultAdventure, tempDirectory.toString(), saveName);

        Adventure loadedAdv = FileHandler.loadAdventure(tempDirectory.toString() + File.separator + saveName);
        assertTrue(loadedAdv == Adventure.getActiveAdventure(),
                "Loaded Adventure was not set as the active Adventure.");
    }

    @Test
    public void LoadAdventure_ShouldLoadTheSameAdventureObjectThatWasSaved() {
        String saveName = "Adventure.adv";
        FileHandler.saveAdventure(defaultAdventure, tempDirectory.toString(), saveName);
        Adventure loadedAdventure = FileHandler.loadAdventure(tempDirectory.toString() + File.separator + saveName);
        assertTrue(loadedAdventure.equals(defaultAdventure));
    }

}
