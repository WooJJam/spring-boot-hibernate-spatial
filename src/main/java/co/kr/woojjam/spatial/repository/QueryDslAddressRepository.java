package co.kr.woojjam.spatial.repository;

import java.util.List;

import co.kr.woojjam.spatial.entity.Address;

public interface QueryDslAddressRepository {
	List<Address> findAddressV1(Double latitude, Double longitude);
}
