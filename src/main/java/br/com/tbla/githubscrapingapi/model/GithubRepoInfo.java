package br.com.tbla.githubscrapingapi.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="GITHUB_REPOSITORY")
public class GithubRepoInfo {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String url;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "repositorio")
	private List<FileInformation> files;
	
	public GithubRepoInfo (String url) {
		this.url = url;
	}
	
}
