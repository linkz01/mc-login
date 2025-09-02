package com.myconstruction.auth;

public class DbTest {
    public static void main(String[] args) {
        try (java.sql.Connection cn = Database.getConnection()) {
            System.out.println("Conexión OK: " + (cn != null));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
