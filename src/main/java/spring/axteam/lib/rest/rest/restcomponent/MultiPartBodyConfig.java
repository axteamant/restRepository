package spring.axteam.lib.rest.rest.restcomponent;

import lombok.Data;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**

 Class that defines a multipart request.
 It is a pojo
 * @author  Alexei Vezzola
 */
@Data
public class MultiPartBodyConfig {

    private MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
}
