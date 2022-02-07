package io.muzoo.ssc.p2;

import org.apache.commons.cli.*;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/*
-a, --total-num-files               The total number of files
-b, --total-num-dirs                Total number of directory
-c, --total-unique-exts Total number of unique file extensions.
-d, --list-exts List all unique file extensions. Do not list duplicates.
--num-ext=EXT List total number of file for specified extension EXT.
-f=path-to-folder Path to the documentation folder. This is a required argument.
 */

public class Problem2 extends Problem1 {

    public static void main(String[] args) {

        //define options
        Options options = new Options();

        Option a = new Option("a", "total-num-files", false, "The total number of files" );
        Option b = new Option("b", "total-num-dirs", false, "Total number of directory");
        Option c = new Option("c", "total-unique-exts", false,"Total number of unique file extensions" );
        Option d = new Option("d", "list-exts", false, "List all unique file extensions");

        Option ext = new Option(null, "num-ext=", true, "List total number of file for specified extension EXT.");
        Option f = new Option("f", null, true, "Set path to the documentation folder. This is a required argument.");

        options.addOption(a);
        options.addOption(b);
        options.addOption(c);
        options.addOption(d);
        options.addOption(ext);
        options.addOption(f);

        //define parser
        CommandLine cmd;
        CommandLineParser parser = new BasicParser();
        HelpFormatter helper = new HelpFormatter();

        //store path
        File dir;

        try {
            cmd = parser.parse(options, args);

            if (cmd.hasOption("f")) {
                //this one is required
                String path = cmd.getOptionValue("f");
                dir = new File(path);
            } else {
                System.out.println("\"-f=path-to-folder\". This is a required argument");
                return;
            }

            if (cmd.hasOption("a") || cmd.hasOption("total-num-files")) {
                System.out.println(searchFile(dir).size());
            }

            if (cmd.hasOption("b") || cmd.hasOption("total-num-dirs")) {
                System.out.println(searchDir(dir).size());
            }

            if (cmd.hasOption("c") || cmd.hasOption("total-unique-exts")) {
                System.out.println(uniqueExtension(dir).size());
            }

            if (cmd.hasOption("d") || cmd.hasOption("list-exts") ) {
                System.out.println(uniqueExtension(dir));
            }

            if (cmd.hasOption("num-ext=")) {
                String extension = cmd.getOptionValue("num-ext=");
                HashMap map = numExtension(dir);
                System.out.println(map.get(extension));
            }

        } catch (ParseException | IOException e) {
            System.out.println(e.getMessage());
            helper.printHelp("Usage:", options);
            System.exit(0);
        }

    }



}
/*
Collaborator:
    Worawit Penglam (Poon)
    Lorenzo
    Supashok (Jay)

Reference:
    https://opensource.com/article/21/8/java-commons-cli
    https://javadoc.io/doc/commons-cli/commons-cli/latest/index.html
    https://stackoverflow.com/questions/6534072/how-can-i-break-from-a-try-catch-block-without-throwing-an-exception-in-java

 */