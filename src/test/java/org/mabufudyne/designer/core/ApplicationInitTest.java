package org.mabufudyne.designer.core;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

class ApplicationInitTest {

    @Tag("slow")
    @Test
    void Main_ShouldInitializeTheApplicationGUISuccessfully() {
        /*
         * Run main with test argument, this will cause the main window to be hidden immediately, exiting the application
         * If there are no exceptions during this process, the application was launched successfully
         */
        String[] args = {"--testRun=true"};
        ApplicationInit.main(args);
    }

    @Test
    void Init_ShouldInitializeTheApplicationState() throws Exception {
        ApplicationInit appInit = new ApplicationInit();
        appInit.init();

        assertNotNull(appInit.getInitializedApplication(),
                "ApplicationInit did not initialize an Application instance");
        assertNotNull(appInit.getInitializedApplication().getActiveAdventure(),
                "The initialized Application does not have an active Adventure");
        assertNotNull(appInit.getInitializedApplication().getActiveAdventure().getStoryPieces(),
                "The Adventure in the initialized Application does not have a StoryPiece");
        assertNotNull(appInit.getInitializedApplication().getProperties(),
                "ApplicationInit did not initialise (default) properties");
    }
}
