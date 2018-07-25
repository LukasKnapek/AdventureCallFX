package org.mabufudyne.designer.core;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AdventureSavingLoadingTest {

    private Adventure defaultAdventure;
    private StoryPiece defaultStoryPiece;
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
        defaultStoryPiece = defaultAdventure.getStoryPieces().get(0);
    }

    @Test
    public void Save_ShouldSaveTheAdventureToAFile_GivenTheSaveLocation() {

        String fileName = "TestAdventure.adv";
        defaultAdventure.save(tempDirectory.toString(), fileName);

        File savedFile = new File(tempDirectory.toString() + File.separator + fileName);
        assertTrue(savedFile.exists(),
                "The saved Adventure file was not found for the given location and file name: " + savedFile.toString());
    }

    @Test
    public void Save_ShouldSaveTheAdventureToAFileWithADVSuffix_GivenAFileNameWithNoSuffix() {
        String fileName = "TestAdventure";
        defaultAdventure.save(tempDirectory.toString(), fileName);

        File savedFile = new File(tempDirectory.toString() + File.separator + fileName + ".adv");
        assertTrue(savedFile.exists(),
                "The saved Adventure file is missing the adv suffix that should have been automatically appended.");
    }

    @Test
    public void Load_ShouldLoadTheAdventureAndReturnIt_GivenAPathToValidSavedAdventureFile() {
        String saveName = "ToBeLoaded.adv";
        defaultAdventure.save(tempDirectory.toString(), saveName);

        Object loadedAdv = Adventure.load(tempDirectory.toString() + File.separator + saveName);
        assertTrue(loadedAdv instanceof Adventure,
                "The saved Adventure was not loaded as an Adventure object.");
    }

    @Test
    public void Load_ShouldReturnNull_GivenANonExistentFilePath() {
        assertEquals(null, Adventure.load(tempDirectory + File.separator + "NonsenseBla.adv"),
                "Load did not return null given a nonexistent file path.");
    }

    @Test
    public void Load_ShouldReturnNull_GivenAnInvalidAdventureFile() throws IOException {
        String fileName = "NotActuallyAnAdventure.adv";
        FileOutputStream fileOut = new FileOutputStream(tempDirectory + File.separator + fileName);
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject("I am just a plain string!");
        out.close();
        fileOut.close();
        assertEquals(null, Adventure.load(tempDirectory + File.separator + fileName));
    }

    @Test
    public void Adventure_ShouldNotChangeWhenSavedAndLoaded() {
        String saveName = "Adventure.adv";
        defaultAdventure.save(tempDirectory.toString(), saveName);
        Adventure loadedAdventure = Adventure.load(tempDirectory.toString() + File.separator + saveName);
        assertTrue(loadedAdventure.equals(defaultAdventure));
    }

}
