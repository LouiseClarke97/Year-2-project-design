package models;

import java.util.*;
import javax.persistence.*;

import com.avaje.ebean.Model;
import play.data.format.*;
import play.data.validation.*;

// Product Entity managed by the ORM
@Entity
public class Chart extends Model {

    // Properties
    // Annotate id as the primary key
    @Id
    private Long id;

    // Other fields marked as being required (for validation purposes)
    @Constraints.Required
    private String song;

    @Constraints.Required
    private String artist;

    @Constraints.Required
    private String release;

    // Default constructor
    public  Chart() {
    }

    // Constructor to initialise object
    public  Chart(Long id, String song, String artist, String release) {
        this.id = id;
        this.song = song;
        this.artist = artist;
        this.release = release;
    }

    //Generic query helper for entity Computer with id Long
    public static Finder<Long,Chart> find = new Finder<Long,Chart>(Chart.class);

    // Find all Products in the database
    // Filter product name 
    public static List<Chart> findAll() {
        return Chart.find.all();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSong() {
        return song;
    }

    public void setSong(String song) {
        this.song = song;
    }
    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }
}
