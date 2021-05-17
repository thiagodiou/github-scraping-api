package br.com.tbla.githubscrapingapi.controller;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.tbla.githubscrapingapi.controller.dto.GithubRepoInfoDTO;
import br.com.tbla.githubscrapingapi.model.FileInformation;
import br.com.tbla.githubscrapingapi.model.GithubRepoInfo;
import br.com.tbla.githubscrapingapi.repository.GithubRepoInfoRepository;
import br.com.tbla.githubscrapingapi.util.ScrapingUtil;

@RestController
@RequestMapping("/githubRepoInfo")
public class GithubRepoInfoController {

	@Autowired
	private GithubRepoInfoRepository githubRepoInfoRepository;

	@GetMapping
	@Transactional
	public List<GithubRepoInfoDTO> listar(@RequestParam String url) {
		List<GithubRepoInfoDTO> result = new ArrayList<GithubRepoInfoDTO>();
		if (url == null || url.trim().isEmpty()) {
			return result;
		}
		GithubRepoInfo gitRepo = githubRepoInfoRepository.findByUrl(url);
		if (gitRepo == null) {
			try {
				gitRepo = new GithubRepoInfo(url);
				List<FileInformation> files = ScrapingUtil.getFilesInformation(url, gitRepo);
				gitRepo.setFiles(files);
				githubRepoInfoRepository.save(gitRepo);
			} catch (Exception e) {
				return result;
			}
		}
		 result = githubRepoInfoRepository.findFilesInRepositoryGroupedByExtension(gitRepo.getId());
		return result;
	}

}
