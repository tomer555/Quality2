import org.junit.Before;
import org.junit.Test;
import system.*;

import static org.junit.Assert.*;

public class SpaceTest {
    private FileSystem system;
    private Space filestorage;
    private String[] namef1;

    @Before
    public void setUp(){
        system = new FileSystem(16);
        this.filestorage = FileSystem.fileStorage;
        namef1 = new String[] {"root", "file1"};
    }


    @Test
    public void alloc() {
        try {
            new Leaf("leaf",10);
            Leaf [] allocations = filestorage.getAlloc();
            for(int i = 0 ; i<10;i++){
                assertNotNull(allocations[i]);
            }
        } catch (OutOfSpaceException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void dealloc() {
        String[] namef1 = {"root", "file1"};
        try {
            system.file(namef1,4);
            assertEquals(12,filestorage.countFreeSpace());

            Leaf [] allocations = filestorage.getAlloc();
            for(int i = 0 ; i<4;i++){
                assertNotNull(allocations[i]);
            }

            Leaf file1 = system.FileExists(namef1);
            filestorage.Dealloc(file1);
            assertEquals(16,filestorage.countFreeSpace());

            for(int i = 0 ; i<4;i++){
                assertNull(allocations[i]);
            }
        } catch (BadFileNameException | OutOfSpaceException e) {
            e.printStackTrace();
        }
    }
    //test fail - unhandled null pointer exception when deallocate memory of leaf without parent
    @Test
    public void deallocLeafWithoutParent() {
        try {
            Leaf leaf = new Leaf("leaf",10);
            filestorage.Dealloc(leaf);
        } catch (OutOfSpaceException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void countFreeSpace() {
        assertEquals(16,filestorage.countFreeSpace());
        try {
            system.file(namef1,4);
            assertEquals(12,filestorage.countFreeSpace());
            Leaf file1 = system.FileExists(namef1);
            filestorage.Dealloc(file1);
            assertEquals(16,filestorage.countFreeSpace());
        } catch (OutOfSpaceException | BadFileNameException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getAlloc() {
        try {
            new Leaf("leaf",10);
        } catch (OutOfSpaceException e) {
            e.printStackTrace();
        }
        Leaf [] allocations = filestorage.getAlloc();
        assertNotNull(allocations);
        assertEquals(16,allocations.length);
    }
    //
    // Test fails - bug in the system in class Space line 43
    @Test(expected = OutOfSpaceException.class)
    public void outOfSpaceTest() throws OutOfSpaceException {
        new Leaf("leaf",17);
    }
}