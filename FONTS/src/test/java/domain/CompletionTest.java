package domain;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class CompletionTest {

    @Test
    public void getTime () {
        User user = new User("Mark");
        Completion completion = new Completion(12, user.getName());
        assertEquals(12, completion.getTime());
    }


    @Test
    public void getUser () {
        User user = new User("Mark");
        Completion completion = new Completion(12, user.getName());
        assertEquals(user.getName(), completion.getUser());
    }

    @Test 
    public void setTime () {
        User user = new User("Mark");
        Completion completion = new Completion(12, user.getName());
        completion.setTime(99999999);
        assertEquals(99999999, completion.getTime());
    }


    @Test 
    public void setUser () {
        User user = new User("Dipper");
        Completion completion = new Completion(12, user.getName());
        User user2 = new User("Mabel");
        completion.setUser(user2.getName());
        assertEquals(user2.getName(), completion.getUser());
    }

    @Test
    public void getUserName () {
        User user = new User("Mark");
        Completion completion = new Completion(12, user.getName());
        assertEquals("Mark", completion.getUserName());

        user = new User("");
        completion = new Completion(12, user.getName());
        assertEquals("", completion.getUserName());
    }


    @Test
    public void compareTo () {
        //completion == completion2
        User user = new User("Mark");
        Completion completion = new Completion(12, user.getName());
        User user2 = new User ("Abril");
        Completion completion2 = new Completion(12, user2.getName());
        assertEquals(0, completion.compareTo(completion2));
        
        //completion > completion2  
        completion2 = new Completion(11, user.getName());
        assertEquals(1, completion.compareTo(completion2));

        //completion < completion2
        completion2 = new Completion(13, user.getName());
        assertEquals(-1, completion.compareTo(completion2));
    }


    @Test
    public void equals() {
        //Compares user to user2
        User user = new User("Amalia");
        Completion completion = new Completion(1, user.getName());
        User user2 = new User("Amalia");
        Completion completion2 = new Completion(1, user2.getName());
        assertEquals(true, completion.equals(completion2));

        //If user == user2 ==> clone of user (user3) == user2
        Completion completion3 = (Completion) completion.clone();
        assertEquals(true, completion2.equals(completion3));
    }


    @Test
    public void userJson() {
        User user = new User("Rick");
        Completion completion = new Completion(5, user.getName());
        String expected = "{\n" + //
        "  \"time\": 5,\n" +
        "  \"user\": \"Rick\"\n" + 
        "}";
        assertEquals(expected, completion.toString());

        /*
         * Checks if toString of user.clone is equal to user
         */
        Completion completion2 = (Completion) completion.clone();
        assertEquals(expected, completion2.toString());
    }

    @Test
    public void Completion() {
        //Completion constructor Completion(Completion clone)
        User user = new User("Rick");
        Completion completion = new Completion(314, user.getName());
        Completion completion2 = new Completion(completion);
        String expected = "{\n" + //
        "  \"time\": 314,\n" +
        "  \"user\": \"Rick\"\n" +
        "}";
        assertEquals(expected, completion2.toString());

        //setTime in completion2
        completion2.setTime(99999);
        expected = "{\n" + //
        "  \"time\": 99999,\n" +
        "  \"user\": \"Rick\"\n" +
        "}";
        assertEquals(expected, completion2.toString());

        //setUser to completion that should not change in completion2
        User user2 = new User("NewUser in completion");
        completion.setUser(user2.getName());
        expected = "{\n" + //
        "  \"time\": 99999,\n" +
        "  \"user\": \"Rick\"\n" +
        "}";
        assertEquals(expected, completion2.toString());

        //setUser in completion2
        User user3 = new User("NewUser in completion2");
        completion2.setUser(user3.getName());
        expected = "{\n" + //
        "  \"time\": 99999,\n" +
        "  \"user\": \"NewUser in completion2\"\n" +
        "}";
        assertEquals(expected, completion2.toString());
    }

}
