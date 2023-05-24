package spring.axteam.lib.rest.rest.restcomponent;

import lombok.Data;

import java.util.Map;

/**

 Class that defines query string parameters configuration.
 * @author  Alexei Vezzola
 */
@Data
public class QueryParamsConfig {

    private Map<String, String> params;

}
