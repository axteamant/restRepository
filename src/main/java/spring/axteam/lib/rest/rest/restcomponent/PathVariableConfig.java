package spring.axteam.lib.rest.rest.restcomponent;

import lombok.Data;

import java.util.List;

/**

 Class that defines path variables. The order is important.
 * @author  Alexei Vezzola
 */
@Data
public class PathVariableConfig {
    List<String> variables;
}
