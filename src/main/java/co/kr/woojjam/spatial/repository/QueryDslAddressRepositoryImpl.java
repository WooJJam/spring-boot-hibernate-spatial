package co.kr.woojjam.spatial.repository;

import static co.kr.woojjam.spatial.entity.QAddress.*;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import co.kr.woojjam.spatial.entity.Address;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class QueryDslAddressRepositoryImpl implements QueryDslAddressRepository {

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public List<Address> findAddressV1(Double latitude, Double longitude) {
		return jpaQueryFactory
			.selectFrom(address)
			.where(Expressions.numberTemplate(Double.class,
					"ST_Distance_Sphere({0}, ST_GeomFromText({1}, 4326))",
					address.location,
					Expressions.stringTemplate("CONCAT('POINT(', {0}, ' ', {1}, ')')", latitude, longitude))
				.loe(20000)) // 3km = 3000M
			.fetch();
	}

	public List<Address> findAddressV2(Double latitude, Double longitude) {
		return null;
	}
}
