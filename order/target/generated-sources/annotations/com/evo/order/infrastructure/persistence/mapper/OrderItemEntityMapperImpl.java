package com.evo.order.infrastructure.persistence.mapper;

import com.evo.order.domain.OrderItem;
import com.evo.order.infrastructure.persistence.entity.OrderItemEntity;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-30T15:09:07+0700",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.5 (Oracle Corporation)"
)
@Component
public class OrderItemEntityMapperImpl implements OrderItemEntityMapper {

    @Override
    public OrderItem toDomainModel(OrderItemEntity entity) {
        if ( entity == null ) {
            return null;
        }

        OrderItem.OrderItemBuilder<?, ?> orderItem = OrderItem.builder();

        if ( entity.getCreatedBy() != null ) {
            orderItem.createdBy( entity.getCreatedBy().toString() );
        }
        orderItem.lastModifiedBy( entity.getLastModifiedBy() );
        orderItem.createdAt( entity.getCreatedAt() );
        orderItem.id( entity.getId() );
        orderItem.orderId( entity.getOrderId() );
        orderItem.productId( entity.getProductId() );
        orderItem.productVariantId( entity.getProductVariantId() );
        orderItem.quantity( entity.getQuantity() );
        orderItem.price( entity.getPrice() );
        orderItem.deleted( entity.getDeleted() );

        return orderItem.build();
    }

    @Override
    public OrderItemEntity toEntity(OrderItem domain) {
        if ( domain == null ) {
            return null;
        }

        OrderItemEntity.OrderItemEntityBuilder orderItemEntity = OrderItemEntity.builder();

        orderItemEntity.id( domain.getId() );
        orderItemEntity.orderId( domain.getOrderId() );
        orderItemEntity.productId( domain.getProductId() );
        orderItemEntity.productVariantId( domain.getProductVariantId() );
        orderItemEntity.quantity( domain.getQuantity() );
        orderItemEntity.price( domain.getPrice() );
        orderItemEntity.deleted( domain.getDeleted() );

        return orderItemEntity.build();
    }

    @Override
    public List<OrderItem> toDomainModelList(List<OrderItemEntity> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<OrderItem> list = new ArrayList<OrderItem>( entityList.size() );
        for ( OrderItemEntity orderItemEntity : entityList ) {
            list.add( toDomainModel( orderItemEntity ) );
        }

        return list;
    }

    @Override
    public List<OrderItemEntity> toEntityList(List<OrderItem> domainList) {
        if ( domainList == null ) {
            return null;
        }

        List<OrderItemEntity> list = new ArrayList<OrderItemEntity>( domainList.size() );
        for ( OrderItem orderItem : domainList ) {
            list.add( toEntity( orderItem ) );
        }

        return list;
    }
}
