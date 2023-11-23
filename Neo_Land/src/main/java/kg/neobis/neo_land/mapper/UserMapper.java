package kg.neobis.neo_land.mapper;

import kg.neobis.neo_land.dto.UserDto;
import kg.neobis.neo_land.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    User toEntity(UserDto model);
    UserDto toModel(User entity);
    List<UserDto> toModelList(List<User> userList);
}
