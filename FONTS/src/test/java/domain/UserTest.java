package domain;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class UserTest {
    
	@Test
    public void getName() {
        User user = new User("Mark");
        String name = user.getName();
        assertEquals("Mark", name);

        user = new User("");
        name = user.getName();
        assertEquals("", name);

        user = new User(" ");
        name = user.getName();
        assertEquals(" ", name);

        user = new User("\n");
        name = user.getName();
        assertEquals("\n", name);
    }

	@Test
    public void setName () {
        User user = new User("Ken");
        user.setName("Barbie");
        assertEquals("Barbie", user.getName());

        user.setName("");
        assertEquals("", user.getName());

        user.setName(" ");
        assertEquals(" ", user.getName());

        user.setName("\n");
        assertEquals("\n", user.getName());

        user.setName("Paul");
        user.setName(user.getName());
        assertEquals("Paul", user.getName());
    }

    @Test
    public void equals() {
        //Compares user to user2
        User user = new User("Amalia");
        User user2 = new User("Amalia");
        assertEquals(true, user.equals(user2));

        //If user == user2 ==> clone of user (user3) == user2
        User user3 = (User) user.clone();
        assertEquals(true, user2.equals(user3));
    }

    @Test
    public void userJson() {
        User user = new User("Rick");
        String expected = "{\n" + //
        "  \"name\": \"Rick\"\n" +
        "}";
        assertEquals(expected, user.toString());

        /*
         * Checks if toString of user.clone is equal to user
         */
        user.setName("Judith");
        User user2 = (User) user.clone();
        expected = "{\n" + //
        "  \"name\": \"Judith\"\n" +
        "}";
        assertEquals(expected, user2.toString());
    }
}

