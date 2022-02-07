package io.muzoo.ssc.p1;

import org.apache.commons.io.DirectoryWalker;


// PROBLEM-1
// -Total number of files.
// -Total number of directory
// -Total number of unique file extensions.
// -List all unique file extensions. Do not list duplicates.
// -Total number of files for each extension.

import java.io.File;
import java.io.IOException;
import java.util.*;

import static org.apache.commons.io.FilenameUtils.getExtension;

public class Problem1 extends DirectoryWalker{

    public static List<File> searchFile(File dir) throws IOException {
        List<File> result = new DirectoryWalker() {
            //if file, then add to result
            protected void handleFile(File file, int depth, Collection fileResults) throws IOException {
                if (file.isFile())
                    fileResults.add(file);
            }

            // search for all files
            public List<File> search() throws IOException {
                List<File> found = new ArrayList();
                this.walk(dir, found);
                return found;
            }
        }.search();
        return result;
    }

    //just copy allFile and change to Dir
    public static List<File> searchDir(File dir) throws IOException {
        List<File> result = new DirectoryWalker() {
            protected boolean handleDirectory(File directory, int depth, Collection fileResults) throws IOException {
                if (directory.isDirectory()){
                    fileResults.add(directory);
                    return true;
                } else {
                    return false;
                }
            }

            public List<File> search() throws IOException {
                List<File> found = new ArrayList();
                this.walk(dir, found);
                return found;
            }
        }.search();
        return result;
    }

    public static Set<String> uniqueExtension(File dir) throws IOException {
        List<File> allFiles = searchFile(dir);
        Set<String> hash_Set = new HashSet<String>();
        String ext = new String();
        for (File f : allFiles){
            ext = getExtension(new String(String.valueOf(f)));
            if (!ext.equals("")) {
                hash_Set.add(ext);
            }
        }
        return hash_Set;
    }

    public static int numUniqueExtension(File dir) throws IOException {
        Set<String> hash_Set = uniqueExtension(dir);
        return hash_Set.size();
    }

    public static HashMap<String, Integer> numExtension(File dir) throws IOException {
        List<File> allFile = searchFile(dir);
        HashMap<String, Integer> map = new HashMap<>();
        String ext = new String();
        for (File f : allFile){
            ext = getExtension(new String(String.valueOf(f)));
            if (!map.containsKey(ext)){
                map.put(ext,1);
            } else {
                map.put(ext, map.get(ext)+1);
            }
        }
        map.remove("");
        return map;
    }

    public static void main(String[] args) throws IOException {
        File dir = new File("/Users/thanthongchim-ong/Desktop/HW1/docs/");
        System.out.println(searchFile(dir).size());
        System.out.println(searchDir(dir).size());
        System.out.println(uniqueExtension(dir).toString());
        System.out.println(numUniqueExtension(dir));
        System.out.println((numExtension(dir).toString()));
    }
}

// collab
// Worawit Penglam (Poon)
// Lorenzo
// Supashok (Jay)

// Ref
// https://stackoverflow.com/questions/9834467/how-can-i-find-all-unique-file-extensions-fin-a-folder-hierarchy-in-java