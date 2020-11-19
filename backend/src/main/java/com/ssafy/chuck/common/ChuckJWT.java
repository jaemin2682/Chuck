package com.ssafy.chuck.common;

import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;

public class ChuckJWT {
	public static String getToken(long userId, String name) throws JWTCreationException {
		Algorithm algorithm = Algorithm.HMAC256("chuck_project");
		String token = com.auth0.jwt.JWT.create().withClaim("ID", userId).withClaim("NAME",name)
			.sign(algorithm);
		return token;
	}
}
