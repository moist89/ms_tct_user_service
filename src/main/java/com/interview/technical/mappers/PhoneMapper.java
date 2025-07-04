package com.interview.technical.mappers;

import com.interview.technical.dtos.api.request.PhoneRequest;
import com.interview.technical.enums.EMessages;
import com.interview.technical.models.Phone;
import com.interview.technical.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PhoneMapper {
    private static final Logger log = LoggerFactory.getLogger(PhoneMapper.class);

    public static Phone map (PhoneRequest phoneRequest, User user){
        log.info(EMessages.MSG_ARGUMENTS.getValue(),phoneRequest.toString());
        log.info(EMessages.MSG_ARGUMENTS.getValue(),user.toString());

        Phone phone = new Phone() ;
        phone.setCityCode(phoneRequest.cityCode());
        phone.setCountryCode(phoneRequest.countryCode());
        phone.setNumber(phoneRequest.number());
        phone.setUser(user);
        log.info(EMessages.MSG_RESPONSE.getValue(),phone);

        return phone;
    }
}
