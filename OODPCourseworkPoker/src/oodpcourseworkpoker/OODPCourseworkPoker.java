/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package oodpcourseworkpoker;

/**
 *
 * @author marshall_gj
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
