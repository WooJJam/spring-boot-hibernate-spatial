package co.kr.woojjam.spatial.repository;

import static org.assertj.core.api.Assertions.*;

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
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
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
		Point point1 = geometryFactory.createPoint(new Coordinate(128.7601, 35.8413)); // company 위치
		Point point2 = geometryFactory.createPoint(new Coordinate(128.7541,35.8264)); // 영남대학교
		Point point3 = geometryFactory.createPoint(new Coordinate(128.7286, 35.8340)); // 정평역
		Point point4 = geometryFactory.createPoint(new Coordinate(128.6815, 35.8411)); // 삼성라이온즈 파크
		Point point5 = geometryFactory.createPoint(new Coordinate(128.6141, 35.8597)); // Daegu Bank Station

		Address address1 = new Address(point1, "Runner co.");
		Address address2 = new Address(point2, "YU");
		Address address3 = new Address(point3, "Jeongpyeong station");
		Address address4 = new Address(point4, "Samsung Lions Park");
		Address address5 = new Address(point5, "Daegu Bank Station");
		Address address6 = new Address(point1, "address6");
		Address address7 = new Address(point2, "address7");
		Address address8 = new Address(point3, "address8");
		Address address9 = new Address(point4, "address9");
		Address address10 = new Address(point5, "address10");


		addressRepository.saveAll(List.of(address1,address2,address3,address4,address5,address6,address7,address8,address9,address10));
    }

	@Test
	@DisplayName("1. 3km 내의 좌표를 조회할 수 있다.")
	public void 범위_3KM_내의_장소_조회() {

		// when
		List<Address> addresses = addressRepository.findAddressV1(35.8413, 128.7601); // 서울 시청 좌표

		for (Address address : addresses) {
			System.out.println("address = " + address);
		}
		// then
		assertThat(addresses).isNotEmpty();
		assertThat(addresses.size()).isEqualTo(6);
		assertThat(addresses.get(1).getName()).isEqualTo("YU");
		assertThat(addresses.get(2).getName()).isEqualTo("Jeongpyeong station");
	}
}
