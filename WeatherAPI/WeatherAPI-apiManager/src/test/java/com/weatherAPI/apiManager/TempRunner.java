package com.weatherAPI.apiManager;

public class TempRunner {
    public static void main(String gg[]) {
        /* Uncomment below lines to test API call */
        runApixu();
        // runDarkSky();
    }
    
    public static void runDarkSky() {
        DarkSkyAPITest test = new DarkSkyAPITest();
        test.testExecute();
    }
    
    public static void runApixu() {
        ApixuAPITest test = new ApixuAPITest();
        test.testExecute();
    }
}
