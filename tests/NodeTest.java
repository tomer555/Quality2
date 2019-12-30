import org.junit.Before;
import org.junit.Test;
import system.*;

import static org.junit.Assert.*;

public class NodeTest {

    private FileSystem system;
    private String[] namef1;
    private String[] named1;

    @Before
    public void setUp(){
        system = new FileSystem(16);
        namef1 = new String[] {"root","dir", "file1"};
        named1 = new String[]  {"root", "dir1"};
    }

    @Test
    public void getPathTestLeaf() {
        try {
            system.file(namef1, 4);
            Leaf file1 = system.FileExists(namef1);
            assertArrayEquals(namef1, file1.getPath());

        } catch (OutOfSpaceException | BadFileNameException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getPathTestTree(){
        try {
            system.dir(named1);
            Tree dir1 = system.DirExists(named1);
            assertArrayEquals(named1, dir1.getPath());
        } catch (BadFileNameException e) {
            e.printStackTrace();
        }

    }

}