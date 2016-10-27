/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import facades.UserFacade;
import java.util.List;
import security.UserFacadeFactory;

/**
 *
 * @author edipetres
 */
public class createDB {

    public static void main(String[] args) {
        //EntityManager em = Persistence.createEntityManagerFactory("pu", null).createEntityManager();

        UserFacade facade = (UserFacade) UserFacadeFactory.getInstance();
//        IUser user = facade.getUserByUserId("user");
//        System.out.println("The returned user is: " + user.getUserName());
//        System.out.println("Password: " + user.getPassword());
//        if (!user.getRolesAsStrings().isEmpty()) {
//            for (String role : user.getRolesAsStrings()) {
//                System.out.println("Role " + role);
//            }
//        } else {
//            System.out.println("Roles empty");
//        }
        
        List<String> result = facade.authenticateUser("admin", "test");
        System.out.println("RESULTS");
        for (String res : result) {
            System.out.println(res);
        }
        
    }
}
