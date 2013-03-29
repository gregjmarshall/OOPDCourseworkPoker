
package oodpcourseworkpoker;

/**
 *@author Anna Taylor
 * @author Greg Marshall
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
