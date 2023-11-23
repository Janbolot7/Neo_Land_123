package kg.neobis.neo_land.mapper;

import kg.neobis.neo_land.dto.RegistrationUserDto;
import kg.neobis.neo_land.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RegistrationUserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    User toEntity(RegistrationUserDto model);
    RegistrationUserDto toModel(User entity);
    List<RegistrationUserDto> toModelList(List<User> userList);
}

