package kg.neobis.neo_land.mapper;

import kg.neobis.neo_land.dto.OrderDto;
import kg.neobis.neo_land.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);
    Order toEntity(OrderDto model);
    OrderDto toModel(Order entity);
    List<OrderDto> toModelList(List<Order> orderList);
}
