package kg.neobis.neo_land.service;

import kg.neobis.neo_land.dto.OrderDto;
import kg.neobis.neo_land.entity.Order;
import kg.neobis.neo_land.entity.Product;
import kg.neobis.neo_land.entity.User;
import kg.neobis.neo_land.mapper.OrderMapper;
import kg.neobis.neo_land.repository.OrderRepository;
import kg.neobis.neo_land.repository.ProductRepository;
import kg.neobis.neo_land.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final OrderMapper orderMapper;


    public OrderDto addProduct(Long productId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = (String) authentication.getPrincipal();

        User user = userRepository.findByUsername(login)
                .orElseThrow(() -> new RuntimeException("Попробуйте перезайти в ваш аккаунт"));

        Order order = orderRepository.findById(user.getId())
                .orElse(new Order(user.getId(), user, null));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Not found"));

        List<Product> productList = order.getProductList();
        if (productList != null) {
            boolean productExists = false;
            for (Product searchProduct : productList) {
                if (searchProduct.getId().equals(productId)) {
                    productExists = true;
                    break;
                }
            }
            if (!productExists)
                productList.add(product);
        } else
            productList = Arrays.asList(product);
        order.setProductList(productList);

        Order savedOrder = orderRepository.save(order);

        return orderMapper.toModel(savedOrder);
    }

    public OrderDto deleteProduct(Long productId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = (String) authentication.getPrincipal();

        User user = userRepository.findByUsername(login)
                .orElseThrow(()-> new RuntimeException("Попробуйте перезайти в Ваш аккаунт"));

        Optional<Order> oOrder = orderRepository.findById(user.getId());
        if (oOrder.isEmpty())
            return orderMapper.toModel(new Order(user.getId(), user, null));
        Order order = oOrder.get();

        Map<Long, Product> productMap = order.getProductMap();

        if (productMap == null)
            return  orderMapper.toModel(order);

        if (productMap.containsKey(productId))
            productMap.remove(productId);

        else
            return orderMapper.toModel(order);

        order.setProductList(productMap.values().stream().collect(Collectors.toList()));
        Order savedOrder = orderRepository.save(order);

        return orderMapper.toModel(savedOrder);
    }

    public OrderDto getOrder(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = (String) authentication.getPrincipal();

        User user = userRepository.findByUsername(login)
                .orElseThrow(()-> new RuntimeException("Попробуйте перезайти в ваш аккаунт"));

        Order order = this.orderRepository.findById(user.getId())
                .orElse(new Order(user.getId(), user, null));

        return orderMapper.toModel(order);
    }

    public  OrderDto clearOrder(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = (String) authentication.getPrincipal();

        User user = userRepository.findByUsername(login)
                .orElseThrow(()-> new RuntimeException("Попробуйте перезайти в ваш аккаунт"));

        Order order = this.orderRepository.findById(user.getId())
                .orElse(new Order(user.getId(), user, null));

        order.setProductList(null);
        return orderMapper.toModel(order);

    }
}
