package de.collectioncompanion.ComposerMS.adapter.outbound;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class OPExecCmd {

    public static String execCMD(String cmd) {
        boolean isWindows = System.getProperty("os.name")
                .toLowerCase().startsWith("windows");
        StringBuilder output = new StringBuilder();
        ProcessBuilder processBuilder = new ProcessBuilder();
        String shellType;

        ProcessBuilder builder = new ProcessBuilder();
        if (isWindows)
            shellType = "cmd.exe";
        else
            shellType = "bash";

        // Run a command on Windows or Linux
        System.out.println("Shell type: " + shellType);
        processBuilder.command(shellType, "-c", cmd);

        try {
            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            int exitVal;

            while ((line = reader.readLine()) != null)
                output.append(line).append("\n");

            exitVal = process.waitFor();

            if (exitVal == 0)
                System.out.println("Success!");
            else
                System.out.println("Failed executing the command: " + cmd);

            System.out.println("Output: " + output);
            reader.close();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return output.toString();
    }

}
