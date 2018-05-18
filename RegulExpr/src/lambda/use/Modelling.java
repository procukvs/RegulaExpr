package lambda.use;

import java.util.*;
import java.util.stream.*;

public class Modelling {
	public Map<Integer, Double> parallelDiceRolls(int N){
		double fraction = 1.0 / N;
		return IntStream.range(0,N)
				        .parallel()
				        .mapToObj(i-> twoDiceThrows(i))
				        .collect(Collectors.groupingBy(side -> side,
				        		Collectors.summingDouble(n -> fraction))); 
	}
	
	private int twoDiceThrows(int i){
		Random rand = new Random(i);
		int first = rand.nextInt(6)+1;
		int second = rand.nextInt(6)+1;
		return first+second;
	}
	
	public Map<Integer, Double> sequentialDiceRolls(int N){
		double fraction = 1.0 / N;
		return IntStream.range(0,N)
				        .sequential()
				        .mapToObj(i-> twoDiceThrows(i))
				        .collect(Collectors.groupingBy(side -> side,
				        		Collectors.summingDouble(n -> fraction))); 
	}	
	
	public Map<Integer, Double> fastDiceRolls(int N){
		Map <Integer, Double> res = new TreeMap<>();
		int[] cnt = new int[13]; 
		double fraction = 1.0 / N;
		for(int i = 1; i < N; i++){
			cnt[twoDiceThrows(i)]++;
		}
		for(int j = 2;j<13;j++ )
			res.put(j, cnt[j]*fraction);
		return res; 				        
	}
}
