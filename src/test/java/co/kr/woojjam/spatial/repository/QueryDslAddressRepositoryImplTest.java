package co.kr.woojjam.spatial.repository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.jpa.impl.JPAQueryFactory;

import co.kr.woojjam.spatial.config.QuerydslConfig;
import co.kr.woojjam.spatial.entity.Address;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(QuerydslConfig.class)
@Transactional
class QueryDslAddressRepositoryImplTest {

	@Autowired
	private AddressRepository addressRepository;

	@Autowired
	private TestEntityManager testEntityManager; // 테스트 데이터 삽입용 EntityManager

	@Autowired
	private JPAQueryFactory jpaQueryFactory;

	private final GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);

	@BeforeEach
	void setUp() {
		Point point1 = geometryFactory.createPoint(new Coordinate(126.9780, 37.5665));
		Point point2 = geometryFactory.createPoint(new Coordinate(126.9785,37.5672));
		Point point3 = geometryFactory.createPoint(new Coordinate(126.9149, 37.5687));
		Address address1 = new Address(point1, "Seoul City Hall"); // 서울 시청
		Address address2 = new Address(point2, "5KM 이내"); // 5Km 이내
		Address address3 = new Address(point3, "5KM 초과"); // 5KM 초과

		addressRepository.save(address1);
		addressRepository.save(address2);
		addressRepository.save(address3);
    }

	@Test
	@DisplayName("특정 좌표 근처의 주소를 조회한다.")
	void findAddressesNearLocation() {

		// when
		List<Address> addresses = addressRepository.findAddress(37.5665, 126.9780); // 서울 시청 좌표

		// then
		assertThat(addresses).isNotEmpty();
		assertThat(addresses.size()).isEqualTo(2);
		assertThat(addresses.get(0).getName()).isEqualTo("Seoul City Hall");
	}
}
