package com.johnakins.bankapp.customerservice.mapper;


import com.johnakins.bankapp.customerservice.dto.CustomerCreatedResponse;
import com.johnakins.bankapp.customerservice.dto.CustomerRequest;
import com.johnakins.bankapp.customerservice.dto.CustomerResponse;
import com.johnakins.bankapp.customerservice.dto.UpdateCustomerRequest;
import com.johnakins.bankapp.customerservice.model.Customer;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    Customer toEntity(CustomerRequest request);

    CustomerResponse toResponse(Customer customer);
    
    CustomerCreatedResponse toCreateResponse(Customer customer);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCustomerFromRequest(UpdateCustomerRequest request, @MappingTarget Customer customer);

}
