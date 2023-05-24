package spring.axteam.lib.rest.examples;

import lombok.Data;

@Data
public class ResponseExample {

    String message;
    String section;
    Object data;
    String auth;
}
