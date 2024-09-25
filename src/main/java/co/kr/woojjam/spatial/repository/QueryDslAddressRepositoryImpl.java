package co.kr.woojjam.spatial.repository;

import static co.kr.woojjam.spatial.entity.QAddress.*;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.BooleanTemplate;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import co.kr.woojjam.spatial.entity.Address;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class QueryDslAddressRepositoryImpl implements QueryDslAddressRepository {

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public List<Address> findAddress(Double latitude, Double longitude) {
		return jpaQueryFactory
			.selectFrom(address)
			.where(Expressions.booleanTemplate(
				"function('ST_Distance_Sphere', {0}, function('ST_GEOMFROMTEXT', concat('POINT(', {1}, ' ', {2}, ')'), 4326)) <= {3}",
				address.location,
				latitude,
				longitude,
				5000 // 5km in meters
			))
			.fetch();
	}
}
