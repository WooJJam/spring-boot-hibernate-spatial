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
		return jpaQueryFactory.select(address)
			.from(address)
            .where(getContainsBooleanExpression(latitude, longitude))
			.fetch();
	}

	private BooleanTemplate getContainsBooleanExpression(Double latitude, Double longitude) {
		int radius = 5000;
		String target = "Point(%f %f)".formatted(latitude, longitude);
		String geoFunction = "ST_CONTAINS(ST_BUFFER(ST_GeomFromText('%s', 4326), {0}), point)";
		String expression = String.format(geoFunction, target);

		return Expressions.booleanTemplate(expression, radius);
	}
}
