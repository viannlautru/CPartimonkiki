package com.example.myapplication.modele;

import junit.framework.TestCase;

public class PhotoTest extends TestCase {
    //creation photo
    private Photo photo = new Photo("image","256 chemin du printemp","12400");
    //resultat codepostale
    //message
    private Integer codevraix = 12;
    private Integer codefaux = 124;

    public void testDepartement() {
        assertEquals(codevraix, photo.Departement());
        assertNotSame(codefaux, photo.Departement());
    }

}