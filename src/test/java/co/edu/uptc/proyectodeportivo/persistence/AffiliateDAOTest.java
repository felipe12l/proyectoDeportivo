package co.edu.uptc.proyectodeportivo.persistence;

import co.edu.uptc.proyectodeportivo.model.Affiliate;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AffiliateDAOTest {
    AffiliateDAO adao ;
    void setup(){
        adao= new AffiliateDAO();
    }
    @Test
    void getAll() {
       setup();
       List<Affiliate> affiliates = adao.getAll();
       for(int i=0; i<affiliates.size(); i++){
           assertEquals(i+1, Integer.parseInt(affiliates.get(i).getId()));
       }

    }



    @Test
    void findById() {
        setup();
        assertNotNull(adao.findById("4"));
    }

    @Test
    void delete() {
        setup();
        assertNotNull(adao.delete("4"));
    }

    @Test
    void update() {
    }
}