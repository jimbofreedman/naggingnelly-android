package uk.co.makosoft.naggingnelly.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Date;

/**
 * Created by jimbo on 20/01/2018.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Action {
    Integer id;

    @JsonProperty("short_description")
    String shortDescription;

    @JsonProperty("folder")
    Integer folder;

    @JsonProperty("context")
    Integer context;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("status")
    Integer status;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("priority")
    Integer priority;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("start_at")
    Date startAt;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("due_at")
    Date dueAt;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("recurrence")
    String recurrence;


    public Action() {

    }

    public Action(String _shortDescription) {
        shortDescription = _shortDescription;
        folder = 1;
        context = 2;
        status = 0;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String _shortDescription) {
        shortDescription = _shortDescription;
    }
}
