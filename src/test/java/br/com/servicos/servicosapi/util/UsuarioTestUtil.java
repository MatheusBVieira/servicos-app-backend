package br.com.servicos.servicosapi.util;

import static io.restassured.RestAssured.given;

import io.restassured.http.ContentType;

public class UsuarioTestUtil {

	public static String pegaTokenAdmin() {
		return given()
			.basePath("/auth")
			.body(ResourceUtils.getContentFromResource("/json/correto/login-admin.json"))
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
			.jsonPath().get("token");
	}
	
}
