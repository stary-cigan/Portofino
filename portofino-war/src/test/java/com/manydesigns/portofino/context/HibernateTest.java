/*
 * Copyright (C) 2005-2009 ManyDesigns srl.  All rights reserved.
 * http://www.manydesigns.com/
 *
 * Unless you have purchased a commercial license agreement from ManyDesigns srl,
 * the following license terms apply:
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 3 as published by
 * the Free Software Foundation.
 *
 * There are special exceptions to the terms and conditions of the GPL 
 * as it is applied to this software. View the full text of the 
 * exception in file OPEN-SOURCE-LICENSE.txt in the directory of this
 * software distribution.
 *
 * This program is distributed WITHOUT ANY WARRANTY; and without the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see http://www.gnu.org/licenses/gpl.txt
 * or write to:
 * Free Software Foundation, Inc.,
 * 59 Temple Place - Suite 330,
 * Boston, MA  02111-1307  USA
 *
 */
package com.manydesigns.portofino.context;

import com.manydesigns.elements.fields.search.Criteria;
import com.manydesigns.portofino.AbstractPortofinoTest;
import com.manydesigns.portofino.model.datamodel.Table;
import com.manydesigns.portofino.reflection.TableAccessor;
import com.manydesigns.portofino.system.model.users.User;
import com.manydesigns.portofino.system.model.users.UsersGroups;
import org.hibernate.proxy.map.MapProxy;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Giampiero Granatella - giampiero.granatella@manydesigns.com
 * @author Angelo    Lupo       - angelo.lupo@manydesigns.com
 * @author Paolo     Predonzani - paolo.predonzani@manydesigns.com
 */
public class HibernateTest extends AbstractPortofinoTest {
    private static final String PORTOFINO_PUBLIC_USER = "portofino.public.users";

    @Override
    public void setUp() throws Exception {
        super.setUp();
        context.openSession();
    }

    public void tearDown() {
        context.closeSession();
    }

    public void testReadProdotti() {
        List<Object> resultProd =
                context.getAllObjects("jpetstore.PUBLIC.product");
        int sizePrd = resultProd.size();
        assertEquals("prodotti", 16, sizePrd);
    }

    public void testUsers() {
        List<Object> groupList =
                context.getAllObjects("portofino.public.groups");
        assertEquals( 2, groupList.size());

        List<Object> usergroups =
                context.getAllObjects("portofino.public.users_groups");
        assertEquals( 3, usergroups.size());

        List<Object> users =
                context.getAllObjects(PORTOFINO_PUBLIC_USER);
        assertEquals("numero utenti", 2, users.size());
        User admin = (User) users.get(0);
        List<UsersGroups> groups = admin.getGroups();
        assertEquals("numero gruppi per admin",  2, groups.size());
        

    }
    public void testSearchAndReadCategorieProdotti() {
        List<Object> resultCat =
                context.getAllObjects("jpetstore.PUBLIC.category");

        int sizeCat = resultCat.size();
        assertEquals("categorie", 5, sizeCat);


        Map categoria0 = (Map<String, Object>) resultCat.get(0);
        assertEquals("jpetstore_public_category", categoria0.get("$type$"));
        assertNotNull(categoria0.get("name"));
        Map categoria1 = (Map<String, Object>)resultCat.get(1);
        assertNotNull(categoria0.get("name"));
        Map categoria2 = (Map<String, Object>)resultCat.get(2);
        assertNotNull(categoria0.get("name"));
        Map categoria3 = (Map<String, Object>)resultCat.get(3);
        assertNotNull(categoria0.get("name"));
        Map categoria4 = (Map<String, Object>)resultCat.get(4);
        assertNotNull(categoria0.get("name"));

        List<Object> resultProd =
                context.getAllObjects("jpetstore.PUBLIC.product");

        Table table = context.getModel()
                .findTableByQualifiedName("jpetstore.PUBLIC.category");
        TableAccessor tableAccessor = new TableAccessor(table);
        Criteria criteria = new Criteria(tableAccessor);
        HashMap<String, String> category= findCategory(tableAccessor, criteria);

        int sizePrd = resultProd.size();
        assertEquals("prodotti", 16, sizePrd);
        Map prd0 = (Map<String, Object>)resultProd.get(0);
        assertEquals("FI-SW-01", prd0.get("productid") );
        assertEquals("Angelfish", prd0.get("name"));
    }

    private HashMap<String, String> findCategory(TableAccessor tableAccessor, Criteria criteria) {
        HashMap<String, String> category=null;
        try {
            criteria.eq(tableAccessor.getProperty("catid"), "FISH");
            List<Object> listObjs = context.getObjects(criteria);
            assertEquals(1, listObjs.size());
            category = (HashMap<String, String>) listObjs.get(0);
            String catid = category.get("catid");
            assertEquals("FISH", catid);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            fail();
        }
        return category;
    }

    public void testSearchAndUpdateCategorie() {
        context.openSession();
        Table table = context.getModel()
                .findTableByQualifiedName("jpetstore.PUBLIC.category");
        TableAccessor tableAccessor = new TableAccessor(table);
        Criteria criteria = new Criteria(tableAccessor);

        List<Object> resultCat =
                context.getAllObjects("jpetstore.PUBLIC.category");
        int sizeCat = resultCat.size();
        assertEquals("categorie", 5, sizeCat);
        Map<String, String> categoria0 =  findCategory(tableAccessor, criteria);
        assertEquals("jpetstore_public_category", categoria0.get("$type$"));
        assertEquals("Fish", categoria0.get("name"));
        categoria0.put("name", "Pesciu");
        context.updateObject("jpetstore.PUBLIC.category", categoria0);
        context.commit("jpetstore");
        context.closeSession();

        //Controllo l'aggiornamento e riporto le cose come stavano
        context.openSession();
        criteria = new Criteria(tableAccessor);
        categoria0 =  findCategory(tableAccessor, criteria);
        assertEquals("jpetstore_public_category", categoria0.get("$type$"));
        assertEquals("Pesciu", categoria0.get("name"));
        categoria0.put("name", "Fish");
        context.updateObject("jpetstore.PUBLIC.category", categoria0);
        context.commit("jpetstore");
        context.closeSession();
    }

    public void testSaveCategoria() {
        context.openSession();
        Map<String, Object> worms = new HashMap<String, Object>();
        worms.put("$type$", "jpetstore.PUBLIC.category");
        worms.put("catid", "VERMI");
        worms.put("name", "worms");
        worms.put("descn",
          "<image src=\"../images/worms_icon.gif\"><font size=\"5\" color=\"blue\">" +
                  "Worms</font>");
        context.saveObject("jpetstore.PUBLIC.category",worms);
        context.commit("jpetstore");
    }

    public void testSaveLineItem() {
        context.openSession();
        Map<String, Object> lineItem = new HashMap<String, Object>();
        lineItem.put("$type$", "jpetstore.PUBLIC.lineitem");
        lineItem.put("orderid", 2);
        lineItem.put("linenum", 2);
        lineItem.put("itemid",
          "test");
        lineItem.put("quantity", 20);
        lineItem.put("unitprice", new BigDecimal(10.80));

        context.saveObject("jpetstore.PUBLIC.lineitem", lineItem);
        context.commit("jpetstore");
        context.closeSession();

        //e ora cancello
        context.openSession();
        try {
            context.deleteObject("jpetstore.PUBLIC.lineitem", lineItem);
            context.commit("jpetstore");
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            fail();
        }
        context.closeSession();
    }


    public void testSaveTestElement() {
        try {
            context.openSession();
            Map<String, Object> testItem = new HashMap<String, Object>();
            testItem.put("$type$", "hibernatetest.PUBLIC.table1");
            testItem.put("testo", "esempio");
            //salvo
            context.saveObject("hibernatetest.PUBLIC.table1", testItem);
            context.commit("hibernatetest");

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    public void testDeleteCategoria() {
        try {
            context.openSession();
            Map<String, Object> worms = new HashMap<String, Object>();
            worms.put("$type$", "jpetstore.PUBLIC.category");
            worms.put("catid", "VERMI");
            worms.put("name", "worms");
            worms.put("descn",
          "<image src=\"../images/worms_icon.gif\"><font size=\"5\" color=\"blue\">" +
                      "Worms</font>");
            context.saveObject("jpetstore.PUBLIC.category", worms);
            context.commit("jpetstore");
            context.deleteObject("jpetstore.PUBLIC.category", worms);
            //test commit globale
            context.commit();
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

    }

    public void testDeleteCategoriaAndCascadedProducys() {
        try {
            context.openSession();
            HashMap birdsPk = new HashMap();
            birdsPk.put("catid", "BIRDS");
            Map<String, Object> birdCat = (Map<String, Object>) context.getObjectByPk("jpetstore.PUBLIC.category", birdsPk);
            assertEquals(16,context.getAllObjects("jpetstore.PUBLIC.product").size());

            context.deleteObject("jpetstore.PUBLIC.category", birdCat);

            //Perdo i due prodotti associati alla categoria Birds
            assertEquals(14,context.getAllObjects("jpetstore.PUBLIC.product").size());


            //test commit globale
            context.commit();
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

    }

        public void testSpaccaSession(){
        context.openSession();

        try {
            //Inserisco un valore troppo grande per email
            // e pwd in modo tale da rompere la insert
            User user = new User();
            user.setEmail("123456789012345678901234567890123456789012345678901234567890" +
            "123456789012345678901234567890123456789012345678901234567890" +
            "123456789012345678901234567890123456789012345678901234567890" +
            "123456789012345678901234567890123456789012345678901234567890" +
            "123456789012345678901234567890123456789012345678901234567890" +
            "123456789012345678901234567890123456789012345678901234567890" +
            "123456789012345678901234567890123456789012345678901234567890" +
            "123456789012345678901234567890123456789012345678901234567890" +
            "123456789012345678901234567890123456789012345678901234567890" +
            "123456789012345678901234567890123456789012345678901234567890" +
            "123456789012345678901234567890123456789012345678901234567890");
            user.setPwd("123456789012345678901234567890123456789012345678901234567890" +
            "123456789012345678901234567890123456789012345678901234567890" +
            "123456789012345678901234567890123456789012345678901234567890" +
            "123456789012345678901234567890123456789012345678901234567890" +
            "123456789012345678901234567890123456789012345678901234567890" +
            "123456789012345678901234567890123456789012345678901234567890" +
            "123456789012345678901234567890123456789012345678901234567890" +
            "123456789012345678901234567890123456789012345678901234567890" +
            "123456789012345678901234567890123456789012345678901234567890" +
            "123456789012345678901234567890123456789012345678901234567890" +
            "123456789012345678901234567890123456789012345678901234567890");

            context.saveObject(PORTOFINO_PUBLIC_USER, user);
            context.commit("portofino");

            fail();
        } catch (Exception e) {
            //corretto
        }
        context.closeSession();
        context.openSession();

        //Faccio una seconda operazione
        try{
            List<Object> users= context.getAllObjects(PORTOFINO_PUBLIC_USER);
            assertNotNull(users);
        }catch (Exception e){
            e.printStackTrace();
            fail("La sessione è spaccata");
        }
    }

    public void testGetObjByPk(){
        //Test Chiave singola
        HashMap<String, Object> pk = new HashMap<String, Object>();
        pk.put("catid", "BIRDS");
        Object bird =  context.getObjectByPk
                ("jpetstore.PUBLIC.category", pk);
        assertEquals("Birds", ((MapProxy) bird).get("name"));

        //Test Chiave composta
        pk = new HashMap<String, Object>();
        pk.put("orderid", 1);
        pk.put("linenum", 1);
        Map lineItem = (Map) context.getObjectByPk
                ("jpetstore.PUBLIC.lineitem", pk);
        assertEquals("EST-1", lineItem.get("itemid"));
    }

    public void testGetRelatedObjects(){
        HashMap<String, Object> pk = new HashMap<String, Object>();
        pk.put("catid", "BIRDS");
        Object bird = context.getObjectByPk
                ("jpetstore.PUBLIC.category", pk);
        assertEquals("Birds", ((MapProxy) bird).get("name"));

        List objs = context.getRelatedObjects("jpetstore.PUBLIC.category",
                bird, "fk_product_1");
        assertTrue(objs.size()>0);
    }

    public void testFkComposite(){
        List<Object> list1 =
                context.getAllObjects("hibernatetest.PUBLIC.table1");
        List<Object> list2 =
                context.getAllObjects("hibernatetest.PUBLIC.table2");
        HashMap map = (HashMap)list2.get(0);
        List obj =  (List) map.get("fk_tb_2");
        assertNotNull(obj);
        assertTrue(obj.size()>0);
        Map obj2 = (Map) ((Map)obj.get(0)).get("fk_tb_2");
        assertNotNull(obj2);
        assertEquals(5, obj2.keySet().size());
        List<Object> list3 =
                context.getAllObjects("hibernatetest.PUBLIC.table3");


        List<Object> listu =
                context.getAllObjects(PORTOFINO_PUBLIC_USER);
        List<Object> listg =
                context.getAllObjects("portofino.public.groups");
        List<Object> listug =
                context.getAllObjects("portofino.public.users_groups");
    }
}



