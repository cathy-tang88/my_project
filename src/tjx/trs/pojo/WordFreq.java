package tjx.trs.pojo;

import org.ansj.util.newWordFind.NewTerm;

public class WordFreq {
	private static  int allComputer =1;
  
	private static  int allOther =1  ;
	
	private int computer = 1 ;
	private int other = 1;
	
	public double getBayesValue(){
//		P(A|W) = P(W|A)P(A)/[(P(W|A))*P(A)+P(W|B)*P(B)]
		return (computer/(double)allComputer)/((computer/(double)allComputer)+(other/(double)allOther)) ;
	}
	
	public static void main(String[] args) {
		System.out.println(new WordFreq().getBayesValue());
	}

	public int getComputer() {
		return computer;
	}

	public void setComputerValue(int value) {
		allComputer+=value ;
		this.computer += value;
	}

	public int getOther() {
		return other;
	}

	public void setOtherValue(int value) {
		allOther+=value ;
		this.other += value;
	}
	
	
}
