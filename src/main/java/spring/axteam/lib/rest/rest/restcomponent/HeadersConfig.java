package spring.axteam.lib.rest.rest.restcomponent;

import lombok.Data;

import java.util.Map;

/**

 Class that defines the headers. It is a pojo
 @param <S>
 @author  Alexei Vezzola
 */
@Data
public class HeadersConfig<S> {

    Map<String, S > headers;


}
