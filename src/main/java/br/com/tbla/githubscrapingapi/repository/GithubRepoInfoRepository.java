package br.com.tbla.githubscrapingapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.tbla.githubscrapingapi.controller.dto.GithubRepoInfoDTO;
import br.com.tbla.githubscrapingapi.model.GithubRepoInfo;


public interface GithubRepoInfoRepository extends JpaRepository<GithubRepoInfo, Long> {
	
	public GithubRepoInfo findByUrl(String url);
	
	@Query(value = "SELECT new br.com.tbla.githubscrapingapi.controller.dto.GithubRepoInfoDTO (file.extension, "
			+ "COUNT(file.id) , "
			+ "SUM (file.linesOfCode) , "
			+ "SUM (file.bytes)) "
			+ "FROM FileInformation file "
			+ "WHERE file.repositorio.id = :id  "
			+ "GROUP BY file.extension")
	public List<GithubRepoInfoDTO> findFilesInRepositoryGroupedByExtension(@Param ("id") Long gitRepoId);

//	@Query(value = "SELECT a from GithubRepoInfo a")
//	public List<GithubRepoInfo> findQualquerCoisa();


}
