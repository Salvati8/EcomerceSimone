package com.betacom.bec;

import org.junit.jupiter.api.Test;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.springframework.boot.test.context.SpringBootTest;

@Suite
@SelectClasses({
	ProdottoControllerTest.class,
	ProdottoTest.class,
	CarrelloControllerTest.class,
	CarrelloTest.class,
	RecensioneControllerTest.class,
	RecensioneTest.class,
	UtenteControllerTest.class,
	UtenteTest.class,
	OrdineControllerTest.class,
	OrdineTest.class,
	PagamentoControllerTest.class,
	PagamentoTest.class
})
//ciao
@SpringBootTest
class BackendEcommerceApplicationTests {

	@Test
	void contextLoads() {
	}

}
