package org.mabufudyne.designer.core;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;



public class ApplicationInitTest {

    @Tag("slow")
    @Test
    public void Main_ShouldInitializeTheApplicationGUISuccessfully() {
        /*
         * Run main with test argument, this will cause the main window to be hidden immediately, exiting the application
         * If there are no exceptions during this process, the application was launched successfully
         */
        String[] args = {"--testRun=true"};
        ApplicationInit.main(args);
    }

    @Test
    public void Init_ShouldInitializeTheApplicationState() {
        ApplicationInit appInit = new ApplicationInit();
        appInit.init();

        Adventure adv = Adventure.getActiveAdventure();
        assertNotNull(adv,
                "After Application initialization, there should be an active Adventure");
        assertEquals(1, adv.getStoryPieces().size(),
                "After Application initialization, the active Adventure should have a default StoryPiece");
    }
}
