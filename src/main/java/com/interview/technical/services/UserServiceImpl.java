package com.interview.technical.services;

import com.interview.technical.dtos.api.request.UserRequest;
import com.interview.technical.dtos.api.request.UserUpdateRequest;
import com.interview.technical.dtos.api.response.UserResponse;
import com.interview.technical.exceptions.GeneralException;
import com.interview.technical.mappers.PhoneMapper;
import com.interview.technical.mappers.UserMapper;
import com.interview.technical.models.Phone;
import com.interview.technical.models.User;
import com.interview.technical.repositories.UserJpaRepository;
import com.interview.technical.validators.UserValidator;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import com.interview.technical.enums.EMessages;

import java.util.List;


@Service
public class UserServiceImpl implements  UserService{

    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserValidator validator;
    private final UserJpaRepository userJpaRepository;
    public UserServiceImpl(UserValidator validator,
                           UserJpaRepository userJpaRepository) {
        this.validator = validator;
        this.userJpaRepository = userJpaRepository;

    }

    @Override
    public UserResponse findById(String id) {
        logger.info(EMessages.MSG_START_METHOD.getValue(),"findAll");

        try {
           UserResponse user;
           logger.info(EMessages.MSG_ARGUMENTS.getValue(),id);
           user= userJpaRepository.findById(id)
                   .map(UserMapper::map)
                   .orElseThrow(() ->
                           new GeneralException(EMessages.MSG_ERROR_USER_NOT_FOUND.getValue()));

           logger.info(EMessages.MSG_RESPONSE.getValue(),user);

           return user;
       } catch (DataAccessException | NullPointerException | IllegalArgumentException   e) {
           logger.info(e.getMessage());
           throw new GeneralException(EMessages.MSG_GENERAL_ERROR.getValue());
       }

    }

    @Override
    public List<UserResponse> findAll(String name, String email) {
        List<UserResponse>usersResponse;
        List<User>users;
        logger.info(EMessages.MSG_START_METHOD.getValue(),"findAll");

        logger.info(EMessages.MSG_ARGUMENTS.getValue(),name +" / "+ email);
        try {
            if (Strings.isNotEmpty(name) && Strings.isNotEmpty(email)) {
                users = userJpaRepository.findByNameAndEmail(name, email);
            } else if (Strings.isNotEmpty(name)) {
                users = userJpaRepository.findByName(name);
            } else if (Strings.isNotEmpty(email)) {
                users = userJpaRepository.findByEmail(email);
            } else {
                users = userJpaRepository.findAll();
            }
            usersResponse=users.stream()
                    .map(UserMapper::map)
                    .toList();
            logger.info(EMessages.MSG_RESPONSE.getValue(),usersResponse);

            return usersResponse;

        }catch (DataIntegrityViolationException e) {
            logger.error("Violación de integridad de datos al crear usuario: {}", e.getMessage(), e);
            throw new GeneralException(EMessages.MSG_ERROR_DATA_INTEGRITY.getValue());

        } catch (IllegalArgumentException e) {
            logger.error("Argumento inválido al crear usuario: {}", e.getMessage(), e);
            throw new GeneralException(EMessages.MSG_ERROR_INVALID_DATA.getValue());

        }
    }

    @Override
    public UserResponse update(UserUpdateRequest userRequest) {

        logger.info(EMessages.MSG_START_METHOD.getValue(), "update");

        try {
            User user = userJpaRepository.findById(userRequest.id())
                    .orElseThrow(() -> new GeneralException(EMessages.MSG_ERROR_USER_NOT_FOUND.getValue()));

            if (!user.getEmail().equalsIgnoreCase(userRequest.email())) {
                throw new GeneralException(EMessages.MSG_ERROR_EMAIL_NOT_ALLOWS_TO_MODIFY.getValue());
            }

            UserMapper.map(user, userRequest);

            User updated = userJpaRepository.save(user);

            return UserMapper.map(updated);

        } catch (DataIntegrityViolationException e) {
            logger.error("Violación de integridad de datos al actualizar usuario: {}", e.getMessage(), e);
            throw new GeneralException(EMessages.MSG_ERROR_DATA_INTEGRITY.getValue());

        }catch ( Exception e) {
            logger.error("Argumento inválido al actualizar usuario: {}", e.getMessage(), e);
            throw new GeneralException(EMessages.MSG_ERROR_INVALID_DATA.getValue());

        }

    }

    @Override
    public UserResponse create(UserRequest userRequest) {
        logger.info(EMessages.MSG_START_METHOD.getValue(),"create");
        try {
            logger.info(EMessages.MSG_ARGUMENTS.getValue(),userRequest);

            validator.verificarDuplicidad(userRequest.name(),userRequest.email());
            validator.validatePassword(userRequest.password());

            User user = UserMapper.map(userRequest);


            List<Phone>phones = userRequest
                    .phones().stream().map(p ->
                            PhoneMapper.map(p,user)).toList();

            user.setPhones(phones);

            User userSaved = userJpaRepository.save(user);

            logger.info(EMessages.MSG_RESPONSE.getValue(),userSaved);

            return UserMapper.map(userSaved);

        }catch (DataAccessException | NullPointerException | IllegalArgumentException e){
            logger.info(e.getMessage());
            throw new GeneralException(EMessages.MSG_GENERAL_ERROR.getValue());
        }
    }




    @Override
    public void delete(String id) {
        logger.info(EMessages.MSG_START_METHOD.getValue(), "delete");

        try {
            if (!userJpaRepository.existsById(id)) {
                throw new GeneralException(EMessages.MSG_ERROR_USER_NOT_FOUND.getValue());
            }

            userJpaRepository.deleteById(id);
        } catch (Exception e) {
            logger.error("Error al eliminar usuario: {}", e.getMessage(), e);
            throw new GeneralException(EMessages.MSG_GENERAL_ERROR.getValue());
        }



    }

}
