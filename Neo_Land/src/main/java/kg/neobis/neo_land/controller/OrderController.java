package kg.neobis.neo_land.controller;

import kg.neobis.neo_land.dto.OrderDto;
import kg.neobis.neo_land.service.OrderService;
import io.swagger.annotations.Api;
import kg.neobis.neo_land.config.SwaggerConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Api(tags = SwaggerConfig.ORDER)
@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/get")
    public OrderDto getOrder(){
        return orderService.getOrder();
    }

    @PostMapping("/addProductToOrder")
    public OrderDto addProduct(@RequestParam Long productId){
        return orderService.addProduct(productId);
    }

    @PostMapping("/deleteProductInOrder")
    public OrderDto deleteProduct(@RequestParam Long productId){
        return orderService.deleteProduct(productId);
    }

    @DeleteMapping("/clear")
    public OrderDto deleteBasket(){
        return orderService.clearOrder();
    }

}
