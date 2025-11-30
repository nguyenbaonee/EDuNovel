package com.evo.order.application.dto.mapper;

import com.evo.common.dto.response.OrderItemDTO;
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
public class OrderItemDTOMapperImpl implements OrderItemDTOMapper {

    @Override
    public OrderItemDTO domainModelToDTO(OrderItem model) {
        if ( model == null ) {
            return null;
        }

        OrderItemDTO orderItemDTO = new OrderItemDTO();

        orderItemDTO.setId( model.getId() );
        orderItemDTO.setOrderId( model.getOrderId() );
        orderItemDTO.setProductId( model.getProductId() );
        orderItemDTO.setProductVariantId( model.getProductVariantId() );
        orderItemDTO.setQuantity( model.getQuantity() );
        orderItemDTO.setPrice( model.getPrice() );
        orderItemDTO.setWeight( model.getWeight() );
        orderItemDTO.setHeight( model.getHeight() );
        orderItemDTO.setWidth( model.getWidth() );
        orderItemDTO.setLength( model.getLength() );
        orderItemDTO.setDeleted( model.getDeleted() );

        return orderItemDTO;
    }

    @Override
    public OrderItemDTO entityToDTO(OrderItemEntity entity) {
        if ( entity == null ) {
            return null;
        }

        OrderItemDTO orderItemDTO = new OrderItemDTO();

        orderItemDTO.setId( entity.getId() );
        orderItemDTO.setOrderId( entity.getOrderId() );
        orderItemDTO.setProductId( entity.getProductId() );
        orderItemDTO.setProductVariantId( entity.getProductVariantId() );
        orderItemDTO.setQuantity( entity.getQuantity() );
        orderItemDTO.setPrice( entity.getPrice() );
        orderItemDTO.setDeleted( entity.getDeleted() );

        return orderItemDTO;
    }

    @Override
    public List<OrderItemDTO> entitiesToDTOs(List<OrderItemEntity> entities) {
        if ( entities == null ) {
            return null;
        }

        List<OrderItemDTO> list = new ArrayList<OrderItemDTO>( entities.size() );
        for ( OrderItemEntity orderItemEntity : entities ) {
            list.add( entityToDTO( orderItemEntity ) );
        }

        return list;
    }

    @Override
    public OrderItem dtoToDomainModel(OrderItemDTO dto) {
        if ( dto == null ) {
            return null;
        }

        OrderItem.OrderItemBuilder<?, ?> orderItem = OrderItem.builder();

        orderItem.id( dto.getId() );
        orderItem.orderId( dto.getOrderId() );
        orderItem.productId( dto.getProductId() );
        orderItem.productVariantId( dto.getProductVariantId() );
        orderItem.quantity( dto.getQuantity() );
        orderItem.price( dto.getPrice() );
        orderItem.weight( dto.getWeight() );
        orderItem.height( dto.getHeight() );
        orderItem.width( dto.getWidth() );
        orderItem.length( dto.getLength() );
        orderItem.deleted( dto.getDeleted() );

        return orderItem.build();
    }

    @Override
    public List<OrderItemDTO> domainModelsToDTOs(List<OrderItem> models) {
        if ( models == null ) {
            return null;
        }

        List<OrderItemDTO> list = new ArrayList<OrderItemDTO>( models.size() );
        for ( OrderItem orderItem : models ) {
            list.add( domainModelToDTO( orderItem ) );
        }

        return list;
    }
}
