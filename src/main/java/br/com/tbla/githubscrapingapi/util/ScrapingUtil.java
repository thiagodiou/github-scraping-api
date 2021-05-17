package br.com.tbla.githubscrapingapi.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.tbla.githubscrapingapi.model.FileInformation;
import br.com.tbla.githubscrapingapi.model.GithubRepoInfo;

public class ScrapingUtil {
	private static final String GITHUB = "https://github.com/";
	private static final String REGEX_FIND_FILES = "<div\\s+role=\"rowheader\"\\s+class=\"flex-auto min-width-0 col-md-2 mr-3\">.*?href=\"((?:[\\w=\"\"-\\.\\#/]+))\">";
	private static final String INFO_ARQUIVO = "text-mono f6 flex-auto pr-3 flex-order-2 flex-md-order-1";
	private static final String REGEX_INFO_FILE = "<div\\s+class=\"text-mono f6 flex-auto pr-3 flex-order-2 flex-md-order-1\">\\s+([\\w.,]+)\\s+.*?</span>\\s+([\\w.\\s]+)";
	private static final String REGEX_INFO_FILE_ONLY_SIZE = "<div\\s+class=\"text-mono f6 flex-auto pr-3 flex-order-2 flex-md-order-1\">\\s+([\\w.\\s]+)";
	private static final String REGEX_FILE_EXTENSION = "[\\w-]*.?([\\w\\s-]+)";
	private static final String REGEX_FILE_SIZE = "([\\d.]+)\\s+([\\w]+)";

	
	public static List<FileInformation> getFilesInformation(String url, GithubRepoInfo repositorio) throws Exception{

		List<FileInformation> files = new ArrayList<FileInformation>();
		try {
			String htmlVerificado = getHtmlPage(url);
			List<String> listOfFiles = new ArrayList<String>();

			Matcher matcher = findLinks(htmlVerificado);
			while (matcher.find()) {
				listOfFiles.add(matcher.group(1));
			}

			for (String file : listOfFiles) {
				String htmlFile = getHtmlPage(GITHUB + file);
				if (htmlFile.contains(INFO_ARQUIVO)) {
					FileInformation arquivoEncontrado = findInfoFile(file, htmlFile);
					arquivoEncontrado.setRepositorio(repositorio);
					files.add(arquivoEncontrado);

				} else {
					files.addAll(getFilesInformation(GITHUB + file, repositorio));
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return files;
	}

	private static FileInformation findInfoFile(String file, String htmlFile) {

		String[] split = file.split("/");
		String fileName = split[split.length - 1].trim();
		String extension = "";
		Matcher matcher = Pattern.compile(REGEX_FILE_EXTENSION).matcher(fileName);
		if (matcher.find()) {
			extension = matcher.group(1);
		}
		Integer linesOfCode = 0;
		String size = null;
		matcher = Pattern.compile(REGEX_INFO_FILE).matcher(htmlFile);
		if (matcher.find()) {
			try {
				linesOfCode = Integer.valueOf(matcher.group(1));
				size = matcher.group(2);
			} catch (Exception e) {
				linesOfCode = 0;
				matcher = Pattern.compile(REGEX_INFO_FILE_ONLY_SIZE).matcher(htmlFile);
				if (matcher.find()) {
					size = matcher.group(1);
				}
			}
		}

		Double fileSize = recuperaTamanhoArquivo(Pattern.compile(REGEX_FILE_SIZE).matcher(size));

		return new FileInformation(fileName, extension, linesOfCode, fileSize);
	}

	private static Double recuperaTamanhoArquivo(Matcher m) {
		Double tamanho = 0.00;
		String tipoTamanho = "";
		if (m.find()) {
			tamanho = Double.valueOf(m.group(1));
			tipoTamanho = m.group(2);
		}

		if (tipoTamanho.equals("KB")) {
			return tamanho * 1024;
		}
		if(tipoTamanho.equals("MB")) {
			return tamanho*1024*1024;
		}
		return tamanho;
	}

	private static String getHtmlPage(String link) throws IOException {
		URL url = new URL(link);
		BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
		StringBuilder sb = new StringBuilder("");
		String inputLine;
		while ((inputLine = br.readLine()) != null) {
			sb.append(inputLine);
		}
		br.close();
		return sb.toString();
	}

	private static Matcher findLinks(String hmtlContent) {
		Matcher m = Pattern.compile(REGEX_FIND_FILES).matcher(hmtlContent);
		return m;
	}
	
}

