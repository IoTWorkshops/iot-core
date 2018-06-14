package gr.iot.iot.web.rest;

import gr.iot.iot.domain.user.User;
import gr.iot.iot.exception.ServiceException;
import gr.iot.iot.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class UserResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserResource.class);

    private UserService userService;

    public UserResource(@Lazy UserService userService) {
        this.userService = userService;
    }


    /**
     * @return ResponseEntity (spring wrapper class)
     */
    @RequestMapping(value = "/user/{uuid}",
        method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getUser(@PathVariable String uuid) {
        try {
            return new ResponseEntity<>(userService.getUser(uuid), HttpStatus.OK);
        } catch (ServiceException e) {
            LOGGER.debug(e.getMessage(), e);
            return new ResponseEntity<>(e.getHttpStatus());
        }
    }

}
