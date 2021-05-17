package br.com.tbla.githubscrapingapi.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class FileInformation {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	private GithubRepoInfo repositorio;
	private String name;
	private String extension;
	private Integer linesOfCode;
	private Double bytes;
	
	public FileInformation(GithubRepoInfo repo, String name, String extension, Integer linesOfCode, Double bytes) {
		this.repositorio = repo;
		this.name = name;
		this.extension = extension;
		this.linesOfCode = linesOfCode;
		this.bytes = bytes;
	}
	
	public FileInformation(String name, String extension, Integer linesOfCode, Double bytes) {
		this.name = name;
		this.extension = extension;
		this.linesOfCode = linesOfCode;
		this.bytes = bytes;
	}
}
