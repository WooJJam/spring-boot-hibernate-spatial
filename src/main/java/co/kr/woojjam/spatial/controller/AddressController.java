package co.kr.woojjam.spatial.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import co.kr.woojjam.spatial.entity.Address;
import co.kr.woojjam.spatial.repository.AddressRepository;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/address")
public class AddressController {

	public final AddressRepository addressRepository;

	@GetMapping("/v1")
	public Map<?, ?> findAddressV1() {
		List<Address> addressV1 = addressRepository.findAddressV1(35.8601, 128.6200);
		return addressV1.stream().collect(Collectors.toMap(Address::getId, Address::getName));
	}

	@GetMapping("/v2")
	public Map<?, ?> findAddressV2() {
		List<Address> addressV1 = addressRepository.findAddressV2(35.8601, 128.6200);
		return addressV1.stream().collect(Collectors.toMap(Address::getId, Address::getName));
	}

	@GetMapping("/v3")
	public Map<?, ?> findAddressV3() {
		List<Address> addressV1 = addressRepository.findAddressV3(35.8601, 128.6200);
		return addressV1.stream().collect(Collectors.toMap(Address::getId, Address::getName));
	}

	@GetMapping("/test")
	@ResponseStatus(HttpStatus.OK)
	public String test() {
		return "success";
	}
}
