package uk.co.makosoft.naggingnelly.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by jimbo on 20/01/2018.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Context {
    Integer id;

    @JsonProperty("name")
    String name;

   public Context() {

    }

    public Context(String _name) {
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
