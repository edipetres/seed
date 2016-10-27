package facades;

import security.IUserFacade;
import entity.User;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import security.IUser;
import security.PasswordStorage;

public class UserFacade implements IUserFacade {

    /*When implementing your own database for this seed, you should NOT touch any of the classes in the security folder
    Make sure your new facade implements IUserFacade and keeps the name UserFacade, and that your Entity User class implements 
    IUser interface, then security should work "out of the box" with users and roles stored in your database */
    //private final Map<String, IUser> users = new HashMap<>();
    public UserFacade() {

        //Add some dummy users
        try {
            EntityManager em = Persistence.createEntityManagerFactory("pu", null).createEntityManager();

            User user;
            user = new User("user", PasswordStorage.createHash("test"));
            user.addRole("User");

            User admin;
            admin = new User("admin", PasswordStorage.createHash("test"));
            admin.addRole("Admin");
            admin.addRole("User");

            try {
                em.getTransaction().begin();
                em.persist(user);
                em.persist(admin);
                em.getTransaction().commit();
            } finally {
                if (em != null) {
                    em.close();
                }
            }

        } catch (PasswordStorage.CannotPerformOperationException ex) {
            Logger.getLogger(UserFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public IUser getUserByUserId(String id) {
        EntityManager em = Persistence.createEntityManagerFactory("pu", null).createEntityManager();
        System.out.println("!!! ID = " + id);
        Query query = em.createQuery("SELECT u FROM User u WHERE u.userName = :username", User.class);
        query.setParameter("username", id);
        List<User> result = query.getResultList();
        User user = result.get(0);
        return user;
//        
//        TypedQuery<User> query = em.createQuery("SELECT c FROM User c", User.class);
//        List<User> results = query.getResultList();
//        return results.get(0);
    }

    /*
  Return the Roles if users could be authenticated, otherwise null
     */
    @Override
    public List<String> authenticateUser(String userName, String password) {
        EntityManager em = Persistence.createEntityManagerFactory("pu", null).createEntityManager();
        Query query = em.createQuery("SELECT u FROM User u WHERE u.userName = :username", User.class);
        query.setParameter("username", userName);
        List<User> result = query.getResultList();
        User user = result.get(0);
        System.out.println("USER OBJECT: null: " + user == null);

        try {
            if (user != null && PasswordStorage.verifyPassword(password, user.getPassword())) {
                return user.getRolesAsStrings();
            } else {
                return null;
            }
        } catch (PasswordStorage.CannotPerformOperationException ex) {
            System.out.println("CannotPerformOperationException");
            System.out.println("in UserFacade.authenticateUser() " + ex.toString());
            return null;
        } catch (PasswordStorage.InvalidHashException ex) {
            System.out.println("InvalidHashException");
            System.out.println("in UserFacade.authenticateUser() " + ex.toString());
            return null;
            
        }

    }

}
