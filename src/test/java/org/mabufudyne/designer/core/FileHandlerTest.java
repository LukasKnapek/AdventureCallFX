package org.mabufudyne.designer.core;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;


class FileHandlerTest {

    private Application app = new Application(new Properties());
    private Adventure defaultAdventure;

    private static File tempDirectory;
    private static FileHandler fileHandler;

    @BeforeAll
    static void createTempFolderFilesHandler() {
        String tempSaveFolderPath = System.getProperty("user.dir") + File.separator + "testSavingLoading";
        tempDirectory = new File(tempSaveFolderPath);
        tempDirectory.mkdir();

        fileHandler = new FileHandler();
    }

    @AfterAll
    static void deleteTempFolder() {
        File[] files = tempDirectory.listFiles();
        if (files != null) {
            for (File f : files) {
                f.delete();
            }
        }
        tempDirectory.delete();
    }

    @BeforeEach
    void createDefaultObjects() {
        StoryPiece sp = new StoryPiece();
        defaultAdventure = new Adventure(app, sp);

        StoryPiece choiceSP = new StoryPiece();
        Choice ch = new Choice(choiceSP);
        sp.addChoice(ch);
    }

    @Test
    void SaveAdventure_ShouldReturnNull_GivenAnExceptionOccursWhileSaving() {
        assertFalse(fileHandler.saveAdventure(defaultAdventure,"nonexistent-file-path/blah.adv"),
                "SaveAdventure was given non-existent path, should have returned false.");
    }

    @Test
    void SaveAdventure_ShouldSaveTheAdventureToAFile_GivenTheSaveLocation() {
        String fileName = "TestAdventure.adv";
        fileHandler.saveAdventure(defaultAdventure, tempDirectory.toString() + File.separator + fileName);

        File savedFile = new File(tempDirectory.toString() + File.separator + fileName);
        assertTrue(savedFile.exists(),
                "The saved Adventure file was not found for the given location and file name: " + savedFile.toString());
    }

    @Test
    void SaveAdventure_ShouldSaveTheAdventureToAFileWithADVSuffix_GivenAFileNameWithNoSuffix() {
        String fileName = "TestAdventure";
        fileHandler.saveAdventure(defaultAdventure, tempDirectory.toString() + File.separator + fileName);

        File savedFile = new File(tempDirectory.toString() + File.separator + fileName + ".adv");
        assertTrue(savedFile.exists(),
                "The saved Adventure file is missing the adv suffix that should have been automatically appended.");
    }

    @Test
    void LoadAdventure_ShouldLoadTheAdventureAndReturnIt_GivenAPathToValidSavedAdventureFile() {
        String saveName = "ToBeLoaded.adv";
        fileHandler.saveAdventure(defaultAdventure, tempDirectory.toString() + File.separator + saveName);

        Object loadedAdv = fileHandler.loadAdventure(tempDirectory.toString() + File.separator + saveName);
        assertTrue(loadedAdv instanceof Adventure,
                "The saved Adventure was not loaded as an Adventure object.");
    }

    @Test
    void LoadAdventure_ShouldReturnNull_GivenANonExistentFilePath() {
        assertNull(fileHandler.loadAdventure(tempDirectory + File.separator + "NonsenseBla.adv"),
                "Load did not return null given a nonexistent file path.");
    }

    @Test
    void LoadAdventure_ShouldReturnNull_GivenAnInvalidAdventureFile() throws IOException {
        String fileName = "NotActuallyAnAdventure.adv";
        FileOutputStream fileOut = new FileOutputStream(tempDirectory + File.separator + fileName);
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject("I am just a plain string!");
        out.close();
        fileOut.close();

        assertNull(fileHandler.loadAdventure(tempDirectory + File.separator + fileName),
                "LoadAdventure did not return null when given an invalid Adventure file");
    }

    @Test
    void LoadAdventure_ShouldLoadTheSameAdventureObjectThatWasSaved() {
        String saveName = "Adventure.adv";
        fileHandler.saveAdventure(defaultAdventure, tempDirectory.toString() + File.separator + saveName);
        Adventure loadedAdventure = fileHandler.loadAdventure(tempDirectory.toString() + File.separator + saveName);
        assertEquals(loadedAdventure, defaultAdventure,
                "The loaded Adventure is not equal to the Adventure that was saved");
    }
}
