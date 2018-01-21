package uk.co.makosoft.naggingnelly.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * Created by jimbo on 20/01/2018.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Folder {
    Integer id;

    @JsonProperty("name")
    String name;

   public Folder() {

    }

    public Folder(String _name) {
        name = _name;
    }

    public Integer getId() { return id; }

    public String getName() {
        return name;
    }

    public void setName(String _name) {
        name = _name;
    }
}
