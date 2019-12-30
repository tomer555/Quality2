import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import system.FileSystem;
import system.Leaf;
import system.OutOfSpaceException;

import static org.junit.Assert.*;

public class LeafTest {
    private FileSystem fileSystem;
    private Leaf file1;
    private Leaf file2;
    @Before
    public void setUp()  {
        try {
            fileSystem = new FileSystem(7);
            file1 = new Leaf("file1",5);
            file2 = new Leaf("file2",2);
        } catch (OutOfSpaceException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void AllocateTest(){
        int[] expected1={0,1,2,3,4};
        assertArrayEquals(expected1,file1.allocations);

        int[] expected2={5,6};
        assertArrayEquals(expected2,file2.allocations);

    }

}