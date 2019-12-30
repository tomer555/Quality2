import org.junit.Before;
import org.junit.Test;
import system.*;

import java.util.HashMap;

import static org.junit.Assert.*;

public class TreeTest {
    private Tree testTree;
    private Tree toCheck;
    @Before
    public void setUp() throws OutOfSpaceException {
        FileSystem system = new FileSystem(16);
        HashMap<String, Node> children = new HashMap<>();
        children.put("dir2",new Tree("dir2"));
        children.put("dir3",new Tree("dir3"));
        toCheck = new Tree("dir4");
        children.put("dir4",toCheck);
        children.put("file0",new Leaf("file0",5));
        testTree = new Tree("dir1");
        testTree.children = children;
    }


    @Test
    public void getChildByNameOfLeafTest() {
        testTree.GetChildByName("file0");
    }

    @Test
    public void getChildByNameOfTreeTest() {
        Tree t = testTree.GetChildByName("dir4");
        assertEquals(t,toCheck);
    }
}