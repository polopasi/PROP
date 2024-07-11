package domain;
import java.util.Objects;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * User of the application. User has a name that identifies it.
 * @author Pol Garcia Vernet
 */
public class User implements Comparable<User> {

    private String name;

    /** Creates an instance of User.
     * @param name is the name of the user.
     */
    public User(String name) {
		this.name = name;
	}

    /** Creates an instance of User given a clone.
     * @param clone is the clone.
     */
    public User(User clone) {
        this.name = clone.name;
    }

    //getter

    /**
     * Gets the name of the user.
     * @return returns the name of the user.
     */
	public String getName () {
        return name;
    }

    //setter

    /**
     * Sets the name of the user.
     * @param name is the new name of user.
     */
    public void setName(String name) {
        this.name = name;
    }
 
    /**
     * Clones this instance.
     * @return clone of the Completion.
     */
    @Override
    protected Object clone() {
        return new User(this.name);
    }

    /**
     * Equals of this class.
     * @return returns true if this instance is equal to obj, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (obj.getClass() != this.getClass()) {
            return false;
        }

        final User other = (User) obj;
        return 
            this.name == other.name;
    }
    
    /**
     * Returns the hashCode of User.
     * @return hashcode of this instance.
     */
    @Override
    public int hashCode() {
        return Objects.hash (
            this.name
        );
    }

    /**
     * Returns the class in Json.
     * @return string with Json of this instance.
     */
    @Override
    public String toString() {
        // This will print the class in pretty Json
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }

    @Override
    public int compareTo(User o) {
        return this.name.compareTo(o.getName());
    }
}
