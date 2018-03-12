package io.redspark.wikireddev.GitHub;

import org.kohsuke.github.GHRateLimit;
import org.kohsuke.github.GitHub;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@Service
public class GithubProvider {

	private GitHub connection;
	
	private GithubToken actual;
	
	private LocalDateTime checkedIn;
	
	private List<GithubToken> tokens = Arrays.asList(new GithubToken("brunoqueiroz","c7be93d54187f1111f89f870542ca49587d64f3f"));
	
	private Queue<GithubToken> queue = new LinkedList<GithubToken>(tokens);
	
	private int remaining;
	
	public GitHub getConnection() throws IOException{
		
		if(connection == null){
			actual = queue.poll();
			connection = GitHub.connect(actual.getUsername(),actual.getToken());
		}
		
		if(checkedIn == null || checkedIn.until(LocalDateTime.now(), ChronoUnit.SECONDS) > 60){
			remaining = getRateLimit().remaining;
			checkedIn = LocalDateTime.now();
		}
		
		if(remaining <= 1000 ){
			if(queue.isEmpty()){
				queue = new LinkedList<GithubToken>(tokens);
			}
			actual = queue.poll();
			connection = GitHub.connect(actual.getUsername(),actual.getToken());
		}	
		return connection;
		
	}
	
	public GHRateLimit getRateLimit() throws IOException{
		return connection.getRateLimit();
	}
	

}