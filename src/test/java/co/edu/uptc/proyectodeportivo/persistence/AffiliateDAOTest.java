package co.edu.uptc.proyectodeportivo.persistence;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AffiliateDAOTest {

    @Test
    void getAll() {
        AffiliateDAO adao = new AffiliateDAO();
        assertNotNull(adao.getAll());
    }
}