package com.evo.order.application.dto.mapper;

import com.evo.common.dto.response.OrderDTO;
import com.evo.common.dto.response.OrderItemDTO;
import com.evo.order.domain.Order;
import com.evo.order.domain.OrderItem;
import com.evo.order.infrastructure.persistence.entity.OrderEntity;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-30T15:09:07+0700",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.5 (Oracle Corporation)"
)
@Component
public class OrderDTOMapperImpl implements OrderDTOMapper {

    @Autowired
    private OrderItemDTOMapper orderItemDTOMapper;

    @Override
    public OrderDTO domainModelToDTO(Order model) {
        if ( model == null ) {
            return null;
        }

        OrderDTO orderDTO = new OrderDTO();

        orderDTO.setId( model.getId() );
        orderDTO.setUserId( model.getUserId() );
        orderDTO.setOrderCode( model.getOrderCode() );
        orderDTO.setOrderStatus( model.getOrderStatus() );
        orderDTO.setPaymentMethod( model.getPaymentMethod() );
        orderDTO.setPaymentStatus( model.getPaymentStatus() );
        orderDTO.setFromName( model.getFromName() );
        orderDTO.setFromPhoneNumber( model.getFromPhoneNumber() );
        orderDTO.setFromAddressLine1( model.getFromAddressLine1() );
        orderDTO.setFromAddressLine2( model.getFromAddressLine2() );
        orderDTO.setFromWard( model.getFromWard() );
        orderDTO.setFromWardCode( model.getFromWardCode() );
        orderDTO.setFromDistrict( model.getFromDistrict() );
        orderDTO.setFromDistrictId( model.getFromDistrictId() );
        orderDTO.setFromCity( model.getFromCity() );
        orderDTO.setToName( model.getToName() );
        orderDTO.setToPhoneNumber( model.getToPhoneNumber() );
        orderDTO.setToAddressLine1( model.getToAddressLine1() );
        orderDTO.setToAddressLine2( model.getToAddressLine2() );
        orderDTO.setToWard( model.getToWard() );
        orderDTO.setCashbackUsed( model.getCashbackUsed() );
        orderDTO.setToWardCode( model.getToWardCode() );
        orderDTO.setToDistrict( model.getToDistrict() );
        orderDTO.setToDistrictId( model.getToDistrictId() );
        orderDTO.setToCity( model.getToCity() );
        orderDTO.setReturnName( model.getReturnName() );
        orderDTO.setReturnPhoneNumber( model.getReturnPhoneNumber() );
        orderDTO.setReturnAddressLine1( model.getReturnAddressLine1() );
        orderDTO.setReturnAddressLine2( model.getReturnAddressLine2() );
        orderDTO.setReturnWard( model.getReturnWard() );
        orderDTO.setReturnWardCode( model.getReturnWardCode() );
        orderDTO.setReturnDistrict( model.getReturnDistrict() );
        orderDTO.setReturnDistrictId( model.getReturnDistrictId() );
        orderDTO.setReturnCity( model.getReturnCity() );
        orderDTO.setTotalProductVariant( model.getTotalProductVariant() );
        orderDTO.setShipmentFee( model.getShipmentFee() );
        orderDTO.setTotalPrice( model.getTotalPrice() );
        orderDTO.setRejectReason( model.getRejectReason() );
        orderDTO.setNote( model.getNote() );
        orderDTO.setReferencesId( model.getReferencesId() );
        orderDTO.setTotalWeight( model.getTotalWeight() );
        orderDTO.setTotalHeight( model.getTotalHeight() );
        orderDTO.setTotalWidth( model.getTotalWidth() );
        orderDTO.setTotalLength( model.getTotalLength() );
        orderDTO.setPrinted( model.getPrinted() );
        orderDTO.setPaymentUrl( model.getPaymentUrl() );
        orderDTO.setGHNOrderCode( model.getGHNOrderCode() );
        orderDTO.setCreatedBy( model.getCreatedBy() );
        orderDTO.setLastModifiedBy( model.getLastModifiedBy() );
        orderDTO.setCreatedAt( model.getCreatedAt() );
        orderDTO.setOrderItems( orderItemDTOMapper.domainModelsToDTOs( model.getOrderItems() ) );

        return orderDTO;
    }

    @Override
    public OrderDTO entityToDTO(OrderEntity entity) {
        if ( entity == null ) {
            return null;
        }

        OrderDTO orderDTO = new OrderDTO();

        orderDTO.setId( entity.getId() );
        orderDTO.setUserId( entity.getUserId() );
        orderDTO.setOrderCode( entity.getOrderCode() );
        orderDTO.setOrderStatus( entity.getOrderStatus() );
        orderDTO.setPaymentMethod( entity.getPaymentMethod() );
        orderDTO.setPaymentStatus( entity.getPaymentStatus() );
        orderDTO.setFromName( entity.getFromName() );
        orderDTO.setFromPhoneNumber( entity.getFromPhoneNumber() );
        orderDTO.setFromAddressLine1( entity.getFromAddressLine1() );
        orderDTO.setFromAddressLine2( entity.getFromAddressLine2() );
        orderDTO.setFromWard( entity.getFromWard() );
        orderDTO.setFromWardCode( entity.getFromWardCode() );
        orderDTO.setFromDistrict( entity.getFromDistrict() );
        orderDTO.setFromDistrictId( entity.getFromDistrictId() );
        orderDTO.setFromCity( entity.getFromCity() );
        orderDTO.setToName( entity.getToName() );
        orderDTO.setToPhoneNumber( entity.getToPhoneNumber() );
        orderDTO.setToAddressLine1( entity.getToAddressLine1() );
        orderDTO.setToAddressLine2( entity.getToAddressLine2() );
        orderDTO.setToWard( entity.getToWard() );
        orderDTO.setCashbackUsed( entity.getCashbackUsed() );
        orderDTO.setToWardCode( entity.getToWardCode() );
        orderDTO.setToDistrict( entity.getToDistrict() );
        orderDTO.setToDistrictId( entity.getToDistrictId() );
        orderDTO.setToCity( entity.getToCity() );
        orderDTO.setReturnName( entity.getReturnName() );
        orderDTO.setReturnPhoneNumber( entity.getReturnPhoneNumber() );
        orderDTO.setReturnAddressLine1( entity.getReturnAddressLine1() );
        orderDTO.setReturnAddressLine2( entity.getReturnAddressLine2() );
        orderDTO.setReturnWard( entity.getReturnWard() );
        orderDTO.setReturnWardCode( entity.getReturnWardCode() );
        orderDTO.setReturnDistrict( entity.getReturnDistrict() );
        orderDTO.setReturnDistrictId( entity.getReturnDistrictId() );
        orderDTO.setReturnCity( entity.getReturnCity() );
        if ( entity.getTotalProductVariant() != null ) {
            orderDTO.setTotalProductVariant( entity.getTotalProductVariant() );
        }
        if ( entity.getShipmentFee() != null ) {
            orderDTO.setShipmentFee( entity.getShipmentFee() );
        }
        orderDTO.setTotalPrice( entity.getTotalPrice() );
        orderDTO.setRejectReason( entity.getRejectReason() );
        orderDTO.setNote( entity.getNote() );
        orderDTO.setReferencesId( entity.getReferencesId() );
        if ( entity.getTotalWeight() != null ) {
            orderDTO.setTotalWeight( entity.getTotalWeight() );
        }
        if ( entity.getTotalHeight() != null ) {
            orderDTO.setTotalHeight( entity.getTotalHeight() );
        }
        if ( entity.getTotalWidth() != null ) {
            orderDTO.setTotalWidth( entity.getTotalWidth() );
        }
        if ( entity.getTotalLength() != null ) {
            orderDTO.setTotalLength( entity.getTotalLength() );
        }
        orderDTO.setPrinted( entity.getPrinted() );
        orderDTO.setPaymentUrl( entity.getPaymentUrl() );
        orderDTO.setGHNOrderCode( entity.getGHNOrderCode() );
        if ( entity.getCreatedBy() != null ) {
            orderDTO.setCreatedBy( entity.getCreatedBy().toString() );
        }
        orderDTO.setLastModifiedBy( entity.getLastModifiedBy() );
        orderDTO.setCreatedAt( entity.getCreatedAt() );

        return orderDTO;
    }

    @Override
    public List<OrderDTO> entitiesToDTOs(List<OrderEntity> entities) {
        if ( entities == null ) {
            return null;
        }

        List<OrderDTO> list = new ArrayList<OrderDTO>( entities.size() );
        for ( OrderEntity orderEntity : entities ) {
            list.add( entityToDTO( orderEntity ) );
        }

        return list;
    }

    @Override
    public Order dtoToDomainModel(OrderDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Order.OrderBuilder<?, ?> order = Order.builder();

        order.createdBy( dto.getCreatedBy() );
        order.lastModifiedBy( dto.getLastModifiedBy() );
        order.createdAt( dto.getCreatedAt() );
        order.id( dto.getId() );
        order.orderCode( dto.getOrderCode() );
        order.userId( dto.getUserId() );
        order.orderStatus( dto.getOrderStatus() );
        order.paymentMethod( dto.getPaymentMethod() );
        order.paymentStatus( dto.getPaymentStatus() );
        order.fromName( dto.getFromName() );
        order.fromPhoneNumber( dto.getFromPhoneNumber() );
        order.fromAddressLine1( dto.getFromAddressLine1() );
        order.fromAddressLine2( dto.getFromAddressLine2() );
        order.fromWard( dto.getFromWard() );
        order.fromWardCode( dto.getFromWardCode() );
        order.fromDistrict( dto.getFromDistrict() );
        order.fromDistrictId( dto.getFromDistrictId() );
        order.fromCity( dto.getFromCity() );
        order.toName( dto.getToName() );
        order.toPhoneNumber( dto.getToPhoneNumber() );
        order.toAddressLine1( dto.getToAddressLine1() );
        order.toAddressLine2( dto.getToAddressLine2() );
        order.toWard( dto.getToWard() );
        order.toWardCode( dto.getToWardCode() );
        order.toDistrict( dto.getToDistrict() );
        order.toDistrictId( dto.getToDistrictId() );
        order.toCity( dto.getToCity() );
        order.returnName( dto.getReturnName() );
        order.returnPhoneNumber( dto.getReturnPhoneNumber() );
        order.returnAddressLine1( dto.getReturnAddressLine1() );
        order.returnAddressLine2( dto.getReturnAddressLine2() );
        order.returnWard( dto.getReturnWard() );
        order.returnWardCode( dto.getReturnWardCode() );
        order.returnDistrict( dto.getReturnDistrict() );
        order.returnDistrictId( dto.getReturnDistrictId() );
        order.returnCity( dto.getReturnCity() );
        order.totalProductVariant( dto.getTotalProductVariant() );
        order.shipmentFee( dto.getShipmentFee() );
        order.totalPrice( dto.getTotalPrice() );
        order.cashbackUsed( dto.getCashbackUsed() );
        order.rejectReason( dto.getRejectReason() );
        order.note( dto.getNote() );
        order.referencesId( dto.getReferencesId() );
        order.totalWeight( dto.getTotalWeight() );
        order.totalHeight( dto.getTotalHeight() );
        order.totalWidth( dto.getTotalWidth() );
        order.totalLength( dto.getTotalLength() );
        order.paymentUrl( dto.getPaymentUrl() );
        order.printed( dto.getPrinted() );
        order.GHNOrderCode( dto.getGHNOrderCode() );
        order.orderItems( orderItemDTOListToOrderItemList( dto.getOrderItems() ) );

        return order.build();
    }

    @Override
    public List<OrderDTO> domainModelsToDTOs(List<Order> models) {
        if ( models == null ) {
            return null;
        }

        List<OrderDTO> list = new ArrayList<OrderDTO>( models.size() );
        for ( Order order : models ) {
            list.add( domainModelToDTO( order ) );
        }

        return list;
    }

    protected List<OrderItem> orderItemDTOListToOrderItemList(List<OrderItemDTO> list) {
        if ( list == null ) {
            return null;
        }

        List<OrderItem> list1 = new ArrayList<OrderItem>( list.size() );
        for ( OrderItemDTO orderItemDTO : list ) {
            list1.add( orderItemDTOMapper.dtoToDomainModel( orderItemDTO ) );
        }

        return list1;
    }
}
