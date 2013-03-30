
package oodpcourseworkpoker;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/*
 * *@author Anna Taylor
 * @author Greg Marshall
 */
public class TableTopTest { 
	private TableTop testTable;

	@Before
	public void setUp() {
		testTable = new TableTopImpl();
	}

	@After
	public void cleanUp() {
		testTable = null;
	}	

	@Test(timeout = 1000) //Expected to fail; shows that pause() doesn't finish prematurely
	public void testPauseLengthFail() {
		testTable.pause();	
		System.out.println("Pause complete.");
	}

	@Test(timeout = 1850)
	public void testPauseLength() {
		testTable.pause();
		System.out.println("Pause complete.");
	}    


}