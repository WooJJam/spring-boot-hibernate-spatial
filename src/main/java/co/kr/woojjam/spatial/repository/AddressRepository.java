package co.kr.woojjam.spatial.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.kr.woojjam.spatial.entity.Address;

public interface AddressRepository extends JpaRepository<Address, Long>, QueryDslAddressRepository {
}
