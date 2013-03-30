
package oodpcourseworkpoker;

/**
 *@author Anna Taylor
 * @author Greg Marshall
 * MSc Computer Science 
 * Submission date: 31/03/2013
 */
public class OODPCourseworkPoker {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        TableTop instance = new TableTopImpl();
        instance.prepareTable();
    }
}
