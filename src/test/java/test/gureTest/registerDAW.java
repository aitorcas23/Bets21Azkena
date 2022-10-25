package test.gureTest;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import configuration.UtilDate;
import dataAccess.DataAccess;
import domain.Admin;
import domain.Bezeroa;
import domain.Langilea;
import domain.Pertsona;
import exceptions.UserAlreadyExist;

public class registerDAW {

     private static DataAccess db;


     Langilea langile1;
     Admin admin1;
     Bezeroa bezero1;
     Pertsona bezero2;



    @BeforeClass
    public static void setDataAccess() {
        db = new DataAccess(true);
        db.initializeDB();
		db.open(false);

    }
    
    @After
    public void removeUser() {
    	db.removePertsona("Ulabak");
    }

    @Test
    public void test1() {
        try {
        bezero1 = new Bezeroa("Unax", "Labaka", "Zubimendi", "Ulabak", "Unax1234", "123456789", "unaxlabak@gmail.com", UtilDate.newDate(2002, 9, 11));
        bezero2 = (Bezeroa)db.register(bezero1, "bezeroa" );
        assertEquals(bezero1,bezero2);
        }catch(Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void test2() {
        try {
        bezero1 = new Bezeroa("Unax", "Labaka", "Zubimendi", "Ulabak", null, "123456789", "unaxlabak@gmail.com", UtilDate.newDate(2002, 9, 11));
        bezero2 = (Bezeroa)db.register(bezero1, "bezeroa" );
        }catch(Exception e) {
            assertTrue(true);
        }
    }
@Test
    public void test3() {
        try {
        bezero1 = new Bezeroa("Unax", "Labaka", "Zubimendi", "Ulabak", "Unax1234", "123456789", "unaxlabak@gmail.com", UtilDate.newDate(2002, 9, 11));
        db.register(bezero1, "bezeroa");
        bezero2 = (Bezeroa)db.register(bezero1, "bezeroa" );
        fail();

        }catch(UserAlreadyExist e) {
            assertTrue(true);


        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test4() {
        try {
        admin1 = new Admin("Unax", "Labaka", "Zubimendi", "Ulabak", "Unax1234", "123456789", "unaxlabak@gmail.com", UtilDate.newDate(2002, 9, 11));
        bezero2 = (Pertsona)db.register(admin1, "admin" );

        assertEquals(admin1,bezero2);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test5() {
        try {
        langile1 = new Langilea("Unax", "Labaka", "Zubimendi", "Ulabak", "Unax1234", "123456789", "unaxlabak@gmail.com", UtilDate.newDate(2002, 9, 11));
        bezero2 = (Pertsona)db.register(langile1, "langilea" );
        assertEquals(langile1, bezero2);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
}
