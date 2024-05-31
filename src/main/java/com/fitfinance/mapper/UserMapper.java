package com.fitfinance.mapper;

import com.fitfinance.annotation.EncodedMapping;
import com.fitfinance.domain.User;
import com.fitfinance.request.UserPostRoleSpecificRequest;
import com.fitfinance.request.UserPostRequest;
import com.fitfinance.request.UserPutRequest;
import com.fitfinance.response.UserGetResponse;
import com.fitfinance.response.UserPostResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE,
uses = PasswordEncoderMapper.class)
public interface UserMapper {
    @Mapping(target = "roles", constant = "USER")
    @Mapping(target = "password", qualifiedBy = EncodedMapping.class)
    @Mapping(source = "birthdate", target = "birthdate", dateFormat = "yyyy-MM-dd")
    User toUser(UserPostRequest request);

    @Mapping(target = "password", qualifiedBy = EncodedMapping.class)
    @Mapping(source = "birthdate", target = "birthdate", dateFormat = "yyyy-MM-dd")
    User toUser(UserPutRequest request);

    @Mapping(target = "password", qualifiedBy = EncodedMapping.class)
    @Mapping(target = "roles", constant = "ADMIN")
    @Mapping(source = "birthdate", target = "birthdate", dateFormat = "yyyy-MM-dd")
    User toUser(UserPostRoleSpecificRequest userPostRoleSpecificRequest);

    UserPostResponse toUserPostResponse(User user);

    UserGetResponse toUserGetResponse(User user);

    List<UserGetResponse> toUserGetResponses(List<User> users);
}
