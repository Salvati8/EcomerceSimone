package com.betacom.bec;

import org.junit.jupiter.api.Test;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.springframework.boot.test.context.SpringBootTest;

@Suite
@SelectClasses({
	ProdottoTest.class,
	CarrelloTest.class,
	RecensioneTest.class,
	UtenteTest.class,
	OrdineTest.class,
	PagamentoTest.class,
	
	ProdottoControllerTest.class,
	CarrelloControllerTest.class,
	RecensioneControllerTest.class,
	UtenteControllerTest.class,
	OrdineControllerTest.class,
	PagamentoControllerTest.class
	
})


@SpringBootTest
class BackendEcommerceApplicationTests {

	@Test
	void contextLoads() {
	}

}
