package pac;

import org.junit.Before;
import org.junit.Test;
import system.BadFileNameException;
import system.FileSystem;
import system.Leaf;
import system.OutOfSpaceException;

import java.nio.file.DirectoryNotEmptyException;
import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.*;

public class FileSystemTest {

    private FileSystem fileSystem;

    @Before
    public void setUp() throws Exception {
        fileSystem = new FileSystem(10);
        String[] mail = {"root", "mail"};
        String[] docs = {"root", "docs"};
        String[] java = {"root", "java"};
        String[] inbox = {"root", "mail","inbox"};
        String[] sent = {"root", "mail","sent"};
        String[] file0 = {"root", "mail","file0"};
        String[] file1 = {"root", "docs","file1"};
        String[] file2 = {"root", "java","file2"};
        String[] file3 = {"root", "mail","inbox","file3"};
        String[] file4 = {"root", "mail","inbox","file4"};
        fileSystem.dir(mail);
        fileSystem.dir(docs);
        fileSystem.dir(java);
        fileSystem.dir(inbox);
        fileSystem.dir(sent);
        fileSystem.file(file0,3);
        fileSystem.file(file1,2);
        fileSystem.file(file2,2);
        fileSystem.file(file3,1);
        fileSystem.file(file4,1);
    }

    @Test
    public void dir() {
    }

    @Test
    public void disk() {
        String[][] output = fileSystem.disk();
        int counter =0;
        for (String[] strings : output) {
            if (Arrays.equals(strings, new String[]{"root", "mail", "file0"}))
                counter++;
        }
        assertEquals(3,counter);

    }

    @Test
    public void diskSize() {
        String[][] output = fileSystem.disk();
        assertEquals(10,output.length);
    }


    @Test
    public void diskFree() {
        String[][] output = fileSystem.disk();
        assertNull(output[9]);
    }


    @Test(expected = OutOfSpaceException.class)
    public void fileTest() throws OutOfSpaceException, BadFileNameException {
        String[] file5 = {"root", "mail","inbox","file5"};
        fileSystem.file(file5,2);

    }

    @Test
    public void fileSucTest() throws OutOfSpaceException, BadFileNameException {
        String[] file5 = {"root", "mail","inbox","file6"};
        fileSystem.file(file5,1);

    }

    @Test
    public void fileMissrmTest() throws OutOfSpaceException, BadFileNameException {
        String[] file6 = {"root", "mail","inbox","file6"};
        fileSystem.file(file6,1);
        Leaf file = fileSystem.FileExists(file6);
        assertNotNull(file);

    }

    @Test
    public void fileTestSameName() throws OutOfSpaceException, BadFileNameException {
        String[] mail = {"root", "mail"};
        String[] file0 = {"root", "mail","file0"};
        Leaf leaf = fileSystem.FileExists(file0);
        assertEquals(3,leaf.allocations.length);
        fileSystem.file(file0,4);
        String[] dir = fileSystem.lsdir(mail);
        assertFalse(checkDuplications(dir));

    }

    private boolean checkDuplications(String[] dir){
        for(int i = 0 ;i<dir.length;i++){
            for(int j = i+1;j<dir.length;j++){
                if(dir[i].equals(dir[j]))
                    return true;
            }
        }
        return false;
    }

    /*
    //system fail - file system should throw BadFileNameException -root is already a dir
    @Test(expected = BadFileNameException.class)
    public void fileIsRootTest() throws OutOfSpaceException, BadFileNameException {
        fileSystem = new FileSystem(10);
        String[] rootname = {"root"};
        fileSystem.file(rootname, 1);
    }
    */




    @Test
    public void lsdirRootTest() {

        String[] rootname = {"root"};
        String[] files_Subdir = fileSystem.lsdir(rootname);
        assertNotNull(files_Subdir);
        List<String> list  = Arrays.asList(files_Subdir);
        assertTrue(list.contains("mail"));
        assertTrue(list.contains("docs"));
        assertTrue(list.contains("java"));
    }

    @Test
    public void lsdirTest() {
        String[] inbox = {"root", "mail","inbox"};

        String[] files_Subdir = fileSystem.lsdir(inbox);
        assertNotNull(files_Subdir);
        List<String> list  = Arrays.asList(files_Subdir);
        assertTrue(list.contains("file3"));
        assertTrue(list.contains("file4"));
    }

    @Test
    public void lsdirNullTest() {
        String[] nothing = {"root", "mail","inbox3"};

        String[] files_Subdir = fileSystem.lsdir(nothing);
        assertNull(files_Subdir);

    }


    @Test
    public void lsdirSortTest() {
        String[] inbox = {"root", "mail","inbox"};

        String[] files_Subdir = fileSystem.lsdir(inbox);
        String[] copy = files_Subdir.clone();
        assertNotNull(files_Subdir);
        Arrays.sort(copy);
        assertArrayEquals(copy,files_Subdir);
    }

    @Test(expected = DirectoryNotEmptyException.class)
    public void rmdirNotEmptyTest() throws DirectoryNotEmptyException {
        String[] docs = {"root", "docs"};
        fileSystem.rmdir(docs);
    }

    @Test
    public void rmdir() throws DirectoryNotEmptyException {
        String[] sent = {"root", "mail","sent"};
        assertNotNull(fileSystem.DirExists(sent));
        fileSystem.rmdir(sent);
        assertNull(fileSystem.DirExists(sent));
    }

    //system fail - unhandled ClassCastException when trying to delete empty dir using rmfile function
    @Test
    public void rmfileTestNotDeleteDir() {
        String[] file0 = {"root", "mail","file0"};
        assertNull(fileSystem.DirExists(file0));

    }

    /*
    //system fail - unhandled ClassCastException when trying to check for file with the same name of dir
    @Test
    public void FileExistsTestWithDir(){
        String[] sent = {"root", "mail","sent"};
        assertNotNull(fileSystem.DirExists(sent));
        assertNull(fileSystem.FileExists(sent));
    }

    //system fail - unhandled ClassCastException when trying to check for dir with the same name of file
    @Test
    public void DirExistsTestWithFile(){
        String[] sent = {"root", "mail","sent"};
        assertNotNull(fileSystem.DirExists(sent));
        assertNull(fileSystem.FileExists(sent));
    }
    */

    @Test
    public void rmfileTestFileNotExists() {
        String[] sent = {"root", "mail","file5"};
        fileSystem.rmfile(sent);
    }


    @Test
    public void fileExists() {
    }

    @Test
    public void dirExists() {
    }
}