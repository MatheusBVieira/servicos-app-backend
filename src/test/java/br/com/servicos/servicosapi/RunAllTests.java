package br.com.servicos.servicosapi;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        CategoriaIT.class,
        CidadeIT.class,
        EstadoIT.class,
        ServicoIT.class
})
public class RunAllTests {
	
	@Test
    public void contextLoads() {
    }
}
