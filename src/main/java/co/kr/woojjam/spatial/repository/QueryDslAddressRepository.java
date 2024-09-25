package co.kr.woojjam.spatial.repository;

import java.util.List;

import co.kr.woojjam.spatial.entity.Address;

public interface QueryDslAddressRepository {
	List<Address> findAddress(Double latitude, Double longitude);
}
