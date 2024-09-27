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

	@GetMapping
	public Map<?, ?> findAddress() {
		List<Address> addressV1 = addressRepository.findAddressV1(35.8413, 128.7601);
		return addressV1.stream().collect(Collectors.toMap(Address::getId, Address::getName));
	}

	@GetMapping("/test")
	@ResponseStatus(HttpStatus.OK)
	public String test() {
		return "success";
	}
}
