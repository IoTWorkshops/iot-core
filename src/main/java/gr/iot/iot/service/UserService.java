package gr.iot.iot.service;

import gr.iot.iot.domain.enums.ErrorCode;
import gr.iot.iot.domain.user.User;
import gr.iot.iot.exception.ServiceException;
import gr.iot.iot.repository.UserRepository;
import gr.iot.iot.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.Optional;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);


    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    private void init() {
    }

    public User getUser(String uuid) {
        Optional<User> user = userRepository.findOneByUuid(uuid);
        if (!user.isPresent()) {
            throw new ServiceException(HttpStatus.BAD_REQUEST, ErrorCode.REQUESTED_DATA_NOT_PRESENT);
        }
        return user.get();
    }

    public User saveUser(String username, String email) {
        User user = new User();

        user.setUuid(StringUtils.generateUuid());
        user.setDateCreated(new Date());

        user.setUsername(username);
        user.setEmail(email);

        return userRepository.save(user);
    }

    public void deleteUser(String uuid) {
        User user = getUser(uuid);
        userRepository.delete(user);
    }
}
