package co.kr.woojjam.spatial.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import co.kr.woojjam.spatial.entity.Address;

public interface AddressRepository extends JpaRepository<Address, Long>, QueryDslAddressRepository {


	@Query(value = "SELECT * FROM address FORCE INDEX (coordinate) " +
		"WHERE ST_Contains(ST_Buffer(ST_GeomFromText(CONCAT('POINT(', ?1, ' ', ?2, ')'), 4326), 1000), coordinate)",
		nativeQuery = true)
	List<Address> findAddressV2(Double latitude, Double longitude);

	@Query(value = "SELECT * FROM address " +
		"WHERE MBRContains(" +
		"    ST_Buffer(ST_GeomFromText(CONCAT('POINT(', ?1, ' ', ?2, ')'), 4326), 1000), " +
		"    coordinate" +
		") AND ST_Distance_Sphere(coordinate, ST_GeomFromText(CONCAT('POINT(', ?1, ' ', ?2, ')'), 4326)) <= 1000",
		nativeQuery = true)
	List<Address> findAddressV3(Double latitude, Double longitude);


}
