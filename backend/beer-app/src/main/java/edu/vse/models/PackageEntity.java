package edu.vse.models;

import edu.vse.dtos.Package;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static javax.persistence.GenerationType.AUTO;

@Entity
@Table(name = "`package`")
public class PackageEntity {

    @Id
    @GeneratedValue(strategy = AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "`order`")
    private OrderEntity order;

    public PackageEntity() {
    }

    public PackageEntity(OrderEntity order) {
        this.order = order;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public OrderEntity getOrder() {
        return order;
    }

    public void setOrder(OrderEntity order) {
        this.order = order;
    }

    public Package toDto(List<ProductPackageEntity> productPackageEntityList) {
        return new Package(id, order.getId(), productPackageEntityList.stream().map(ProductPackageEntity::toDto).collect(toList()));
    }
}
