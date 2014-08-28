package CellHashLife;

import com.bahus.ConwayLife.Core.CellHashLife.CellHashLife;
import com.bahus.ConwayLife.Core.GenericLife;
import com.bahus.ConwayLife.Core.Storage.Bounds;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.fail;

/**
* CellHashLife Tester.
*
* @author <Authors name>
* @since <pre>Aug 27, 2014</pre>
* @version 1.0
*/
public class CellHashLifeTest {

    GenericLife nLife = new CellHashLife();

@Before
public void before() throws Exception {
    nLife.toggleCell(0, 0);
    nLife.toggleCell(1, 0);
    nLife.toggleCell(1, 2);
    nLife.toggleCell(3, 1);
    nLife.toggleCell(4, 0);
    nLife.toggleCell(5, 0);
    nLife.toggleCell(6, 0);
}

@After
public void after() throws Exception {
    nLife = null;
}

/**
*
* Method: getBounds()
*
*/
@Test
public void testGetBounds() throws Exception {
    Bounds b = nLife.getBounds();
    System.out.println(b);
    if(b.lx != 0 || b.hx != 6 || b.ly != 0 || b.hy != 2)
        fail("Something is wrong with the boundaries");
}

/**
*
* Method: reset()
*
*/
@Test
public void testReset() throws Exception {
//TODO: Test goes here...

}

/**
*
* Method: toggleCell(int x, int y)
*
*/
@Test
public void testToggleCell() throws Exception {
//TODO: Test goes here...
}

/**
*
* Method: nextGen()
*
*/
@Test
public void testNextGen() throws Exception {
//TODO: Test goes here...
}

/**
*
* Method: getCells()
*
*/
@Test
public void testGetCells() throws Exception {
//TODO: Test goes here...

}

/**
*
* Method: cleanup()
*
*/
@Test
public void testCleanup() throws Exception {
//TODO: Test goes here...
}


/**
*
* Method: computeXY(int x, int y)
*
*/
@Test
public void testComputeXY() throws Exception {
//TODO: Test goes here...
/*
try {
   Method method = CellHashLife.getClass().getMethod("computeXY", int.class, int.class);
   method.setAccessible(true);
   method.invoke(<Object>, <Parameters>);
} catch(NoSuchMethodException e) {
} catch(IllegalAccessException e) {
} catch(InvocationTargetException e) {
}
*/
}

}
