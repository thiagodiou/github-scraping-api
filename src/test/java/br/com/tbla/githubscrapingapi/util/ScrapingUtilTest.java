package br.com.tbla.githubscrapingapi.util;

import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


import br.com.tbla.githubscrapingapi.model.FileInformation;

class ScrapingUtilTest {

	@Test
	public void mustLoadValidRepositoryUrl() {
		String validUrl = "https://github.com/thiagodiou/brasileirao-api";
		try {
		List<FileInformation> lista = ScrapingUtil.getFilesInformation(validUrl, null);
		Assertions.assertNotNull(lista);
		}catch(Exception e) {
			fail("Excecao nao deveria ser lancada");
		}
	}

}
