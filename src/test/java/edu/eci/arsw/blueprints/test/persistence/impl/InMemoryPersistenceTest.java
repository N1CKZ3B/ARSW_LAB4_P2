/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.test.persistence.impl;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.impl.InMemoryBlueprintPersistence;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author hcadavid
 */
public class InMemoryPersistenceTest {
    
    @Test
    public void saveNewAndLoadTest() throws BlueprintPersistenceException, BlueprintNotFoundException{
        InMemoryBlueprintPersistence ibpp=new InMemoryBlueprintPersistence();

        Point[] pts0=new Point[]{new Point(40, 40),new Point(15, 15)};
        Blueprint bp0=new Blueprint("mack", "mypaint",pts0);
        
        ibpp.saveBlueprint(bp0);
        
        Point[] pts=new Point[]{new Point(0, 0),new Point(10, 10)};
        Blueprint bp=new Blueprint("john", "thepaint",pts);
        
        ibpp.saveBlueprint(bp);
        
        assertNotNull("Loading a previously stored blueprint returned null.",ibpp.getBlueprint(bp.getAuthor(), bp.getName()));
        
        assertEquals("Loading a previously stored blueprint returned a different blueprint.",ibpp.getBlueprint(bp.getAuthor(), bp.getName()), bp);
        
    }


    @Test
    public void saveExistingBpTest() {
        InMemoryBlueprintPersistence ibpp=new InMemoryBlueprintPersistence();
        
        Point[] pts=new Point[]{new Point(0, 0),new Point(10, 10)};
        Blueprint bp=new Blueprint("john", "thepaint",pts);
        
        try {
            ibpp.saveBlueprint(bp);
        } catch (BlueprintPersistenceException ex) {
            fail("Blueprint persistence failed inserting the first blueprint.");
        }
        
        Point[] pts2=new Point[]{new Point(10, 10),new Point(20, 20)};
        Blueprint bp2=new Blueprint("john", "thepaint",pts2);

        try{
            ibpp.saveBlueprint(bp2);
            fail("An exception was expected after saving a second blueprint with the same name and autor");
        }
        catch (BlueprintPersistenceException ex){
            
        }
                
        
    }

    @Test
    public void getBlueprintTest() throws BlueprintPersistenceException, BlueprintNotFoundException {
        InMemoryBlueprintPersistence ibpp = new InMemoryBlueprintPersistence();

        Point[] pts = new Point[]{new Point(0, 0), new Point(10, 10)};
        Blueprint bp = new Blueprint("john", "thepaint", pts);
        ibpp.saveBlueprint(bp);

        Blueprint retrievedBp = ibpp.getBlueprint("john", "thepaint");

        assertNotNull("Loading a previously stored blueprint returned null.", retrievedBp);
        assertEquals("Loading a previously stored blueprint returned a different blueprint.", bp, retrievedBp);
    }

    @Test
    public void getBlueprintsByAuthorTest() throws BlueprintPersistenceException, BlueprintNotFoundException {
        InMemoryBlueprintPersistence ibpp = new InMemoryBlueprintPersistence();

        Point[] pts1 = new Point[]{new Point(0, 0), new Point(10, 10)};
        Blueprint bp1 = new Blueprint("john", "thepaint1", pts1);
        ibpp.saveBlueprint(bp1);

        Point[] pts2 = new Point[]{new Point(20, 20), new Point(30, 30)};
        Blueprint bp2 = new Blueprint("john", "thepaint2", pts2);
        ibpp.saveBlueprint(bp2);

        Point[] pts3 = new Point[]{new Point(40, 40), new Point(50, 50)};
        Blueprint bp3 = new Blueprint("jane", "thepaint3", pts3);
        ibpp.saveBlueprint(bp3);

        assertEquals("The number of blueprints by author is incorrect.", 2, ibpp.getBlueprintsByAuthor("john").size());
        assertTrue("The list of blueprints by author does not contain the expected blueprint.", ibpp.getBlueprintsByAuthor("john").contains(bp1));
        assertTrue("The list of blueprints by author does not contain the expected blueprint.", ibpp.getBlueprintsByAuthor("john").contains(bp2));
        assertFalse("The list of blueprints by author contains an unexpected blueprint.", ibpp.getBlueprintsByAuthor("john").contains(bp3));
    }



    
}
