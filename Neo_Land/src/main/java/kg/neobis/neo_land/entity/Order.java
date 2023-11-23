package kg.neobis.neo_land.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "orders")
public class Order {
    @Id
    @Column(name = "user_id")
    private Long id;
    @OneToOne(optional = false, cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    User user;
    @OneToMany(fetch = FetchType.EAGER)
    List<Product> productList;


    public Map<Long, Product> getProductMap() {
        Map<Long, Product> productMap = productList
                .stream()
                .collect(Collectors.toMap(Product::getId, p -> p));
        return productMap;
    }
}