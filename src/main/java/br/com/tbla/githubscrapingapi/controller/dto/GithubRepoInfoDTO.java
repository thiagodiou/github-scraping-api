package br.com.tbla.githubscrapingapi.controller.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GithubRepoInfoDTO {
	private String extension;
	private Long count;
	private Long lines;
	private double bytes;
	
	public GithubRepoInfoDTO() {
		
	}
	
	public GithubRepoInfoDTO(String extension, long count, long lines, double bytes) {
		this.extension = extension;
		this.count = count;
		this.lines = lines;
		this.bytes = bytes;
	}

	@Override
	public String toString() {
		return "GithubRepoInfoDTO [extension=" + extension + ", count=" + count + ", lines=" + lines + ", bytes="
				+ bytes + "]";
	}
}

