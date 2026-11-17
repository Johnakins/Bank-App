package com.johnakins.bankapp.customerservice.service;

import java.util.Optional;

import com.johnakins.bankapp.authuser.service.Auth0UserService;
import com.johnakins.bankapp.commonsdto.commons.exception.*;
import com.johnakins.bankapp.customerservice.dto.*;
import com.johnakins.bankapp.customerservice.mapper.CustomerMapper;
import com.johnakins.bankapp.customerservice.model.Customer;
import com.johnakins.bankapp.customerservice.model.KycStatus;
import com.johnakins.bankapp.customerservice.repository.CustomerRepository;
import com.johnakins.bankapp.customerservice.util.Fingerprints;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerService {

	private final Auth0UserService auth0UserService;
	private final CustomerRepository repository;
	private final CustomerMapper mapper;

	public CustomerCreatedResponse create(CustomerRequest request) {

		String externalId = request.getExternalId();
		String email = request.getEmail();

		String fp = Fingerprints.customerCreate(request.getFirstName(), request.getLastName(), request.getEmail(),
				request.getPhone(), request.getAddress());

		// Fast path: same externalId already present?
		Optional<Customer> byExt = repository.findByExternalId(externalId);
		if (byExt.isPresent()) {
			Customer ex = byExt.get();
			if (fp.equals(ex.getRequestFingerprint())) {
				return mapper.toCreateResponse(ex); // idempotent replay
			}
			throw new ConflictException("Same externalId used with different data");
		}
		
		
		// Fast path: same Email already present?
				Optional<Customer> byEmail = repository.findByEmail(email);
				if (byEmail.isPresent()) {
					Customer ex = byEmail.get();
					if (fp.equals(ex.getRequestFingerprint())) {
						return mapper.toCreateResponse(ex); // idempotent replay
					}
					throw new ConflictException("Same email used with different data");
				}
				
		

		// New record attempt
		Customer entity = mapper.toEntity(request);
		entity.setExternalId(externalId);
		entity.setActive(false);
		entity.setKycStatus(KycStatus.PENDING);
		entity.setRequestFingerprint(fp);
		Customer saved = repository.saveAndFlush(entity);
		return mapper.toCreateResponse(saved);
		
	}

	public CustomerResponse getByExternalId(String externalId) {
		Customer customer = repository.findByExternalId(externalId)
				.orElseThrow(() -> new ResourceNotFoundException("Customer not found with externalId: " + externalId));
		;
		return mapper.toResponse(customer);
	}

	public boolean exists(String externalId) {
		return repository.findByExternalId(externalId).isPresent();

	}

	public boolean existsByEmail(String email) {
		return repository.existsByEmail(email);
	}

	public Integer updateCustomer(String id, UpdateCustomerRequest request, Integer expected) {
		Customer c = repository.findByExternalId(id)
				.orElseThrow(() -> new CustomerNotFoundException("Customer not found: " + id));

		if (expected == null)
			throw new PreconditionRequiredException("If-Match header required");
		if (!expected.equals(c.getVersion())) {
			throw new VersionMismatchException("Stale version. Current=" + c.getVersion() + ", If-Match=" + expected);
		}

		mapper.updateCustomerFromRequest(request, c);
		Customer saved = repository.save(c); // Hibernate increments version
		return mapper.toResponse(saved).getVersion();
	}

	public Integer updateKycStatus(String id, String kycStatus) {
		Customer c = repository.findByExternalId(id)
				.orElseThrow(() -> new ResourceNotFoundException("Customer not found with externalId: " + id));


		if ("VERIFIED".equalsIgnoreCase(kycStatus)) {
            auth0UserService.createDbUser(c.getEmail(), "default-password", c.getExternalId());
		    c.setKycStatus(KycStatus.VERIFIED);
		    c.setActive(true);
		    repository.save(c);
		}
		return c.getVersion();
	}

	public CustomerResponse getById(Long id) {
		return repository.findById(id)
		        .map(mapper::toResponse)
		        .orElseThrow(() -> new ResourceNotFoundException("Customer not found with externalId: " + id));	}
}