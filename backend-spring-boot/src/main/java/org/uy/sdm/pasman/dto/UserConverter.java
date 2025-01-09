package org.uy.sdm.pasman.dto;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.uy.sdm.pasman.model.SecurityUser;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserConverter {

	private static final ModelMapper SECURITY_USER_TO_USER_DTO_MODEL_MAPPER;
	private static final ModelMapper USER_DTO_TO_SECURITY_USER_MODEL_MAPPER;

	static {
		SECURITY_USER_TO_USER_DTO_MODEL_MAPPER = new ModelMapper();
		SECURITY_USER_TO_USER_DTO_MODEL_MAPPER
			.getConfiguration()
			.setMatchingStrategy(MatchingStrategies.STRICT);

		USER_DTO_TO_SECURITY_USER_MODEL_MAPPER = new ModelMapper();
		USER_DTO_TO_SECURITY_USER_MODEL_MAPPER
			.getConfiguration()
			.setMatchingStrategy(MatchingStrategies.STRICT);
	}

	public static UserDto convert(final SecurityUser securityUser) {
		return SECURITY_USER_TO_USER_DTO_MODEL_MAPPER.map(securityUser,UserDto.class);
	}

	public static SecurityUser convert(final UserDto userDto) {
		return USER_DTO_TO_SECURITY_USER_MODEL_MAPPER.map(userDto,SecurityUser.class);
	}

}
