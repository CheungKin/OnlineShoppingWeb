package com.cheung.mybatis.repository;

import java.util.List;

import com.cheung.mybatis.model.Address;

public interface AddressRepository {
	public List<Address> findByuserId(Integer userId);

	public Address findByAddressId(Integer addressId);

	public int save(Address address);

	public int update(Address address);

	public int delete(Integer addressId);

	public int changeToFalse(Integer userId);

	public int changeToTrue(Integer addressId);
}
