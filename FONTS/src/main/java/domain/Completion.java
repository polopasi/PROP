package domain;
import java.util.Objects;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Completion of a Level by a User in a specific time in seconds.
 * @author Pol Garcia Vernet
 */
public class Completion implements Comparable<Completion> {
    
    private int time;
    private String user;

    /** Creates an instance of Completion.
     * @param time is the Completion's time in seconds.
     * @param user is the Completion's user username.
     */
    public Completion(int time, String user) {
        this.time = time;
        this.user = user;
    }

    /** Creates an instance of Completion given a clone.
     * @param clone is the clone.
     */
    public Completion(Completion clone) {
        this.time = clone.time;
        this.user = clone.getUserName();
    }

    //getters

    /**
     * Gets the time that the user needed to finish the level.
     * @return time that user needed to finish.
     */
    public int getTime() {
        return time;
    }

    /**
     * Gets the name of the user.
     * @return the name of the user.
     */
    public String getUser() {
        return user;
    }

    //setters

    /**
     * Sets the time of completion.
     * @param time time must be greater than or equal to 0.
     * @return returns true if time is greater than or equal to 0, false otherwise.
     */
    public boolean setTime(int time) {
        if (time >= 0) this.time = time;
        else return false;
        return true;
    }

    /**
     * Sets the name of the user.
     * @param user name of the user.
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * Gets the name of the user associated to this Completion.
     * @return name of the User.
     */
    public String getUserName() {
        return user;
    }


    /**
     * Compares if this instance is equal, greater or smaller to other.
     * @param other Completion to compare.
     * @return returns 1 if greater, -1 if smaller and 0 if equal.
     */
    @Override public int compareTo(Completion other) {
        if (this.time > other.time) {
            // if current object is greater,then return 1
            return 1;
        }
        else if (this.time < other.time) {
            // if current object is smaller,then return -1
            return -1;
        }
        else {
            // if current object is equal to other,then return 0
            return 0;
        }
    }
    
    /**
     * Clones this instance.
     * @return clone of the Completion.
     */
    @Override
    protected Object clone() {
        return new Completion (this);
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

        final Completion other = (Completion) obj;
        return 
            this.time == other.time &&
            this.user.equals(other.user);
    }

    /**
     * Returns the hashCode of Completion.
     * @return hashcode of this instance.
     */
    @Override
    public int hashCode() {
        return Objects.hash (
            this.time,
            this.user
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
}
