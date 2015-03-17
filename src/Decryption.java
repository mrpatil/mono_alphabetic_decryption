
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.omg.CORBA.FREE_MEM;


public class Decryption {

	static int diagramMatrix[][]=new int[26][26];
	HashMap<String,Double > letterFrequency=new HashMap<String, Double>();
	static String[] descFreqLetter=new String[26];
	HashMap<String, Integer>frequecyCount=new HashMap<String, Integer>();
	static String testCipher="RIHILKHIERSKILEKKLKTRSKHRIHILKHLREIRTRSKLKTRSKHHLEKRS";
	public Decryption() throws IOException{
		
		BufferedReader file=new BufferedReader(new FileReader("letterFrequency.txt"));
		//Adding all standard english letter frequency in hashmap
		String tempLine[]=new String[2];
		int i=0;
		for (String line = file.readLine(); line != null; line = file.readLine()) 
		{	
			tempLine=line.split("\t");
			//System.out.println(tempLine[0]+" "+tempLine[1]);
			letterFrequency.put(tempLine[0], Double.parseDouble(tempLine[1]));
			descFreqLetter[i]=tempLine[0];
			//System.out.print(descFreqLetter[i]);
			i++;
		}
		
	}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Decryption d=new Decryption();
		for (int i = 0; i < testCipher.length(); i++) {
			String key=testCipher.charAt(i)+"";
			if(d.frequecyCount.containsKey(key)){
				int tempCount=d.frequecyCount.get(key)+1;
				d.frequecyCount.put(key, tempCount);
			}
			else
				d.frequecyCount.put(key, 1);
		}
		System.out.println(d.frequecyCount);
		Map<String, Integer> map = sortByValuesDesc(d.frequecyCount);
		System.out.println(map);
		String sortedCipher="";
		Set tempset2 = map.entrySet();
	    Iterator iterator2 = tempset2.iterator();
	    while(iterator2.hasNext()) {
	           Map.Entry me2 = (Map.Entry)iterator2.next();
	           sortedCipher+=me2.getKey();
	    }
	   
	    String newPlainTxt="";
	    for (int i = 0; i < testCipher.length(); i++) {
	    	newPlainTxt+=descFreqLetter[sortedCipher.indexOf(testCipher.charAt(i))];
		}
	    System.out.println(newPlainTxt);
	    
	    for (int i = 0; i < descFreqLetter.length; i++) {
			for (int j = 0; j < descFreqLetter.length; j++) {
				String subString=descFreqLetter[i]+descFreqLetter[j];
				diagramMatrix[i][j]=d.getSubstringCount(newPlainTxt,subString);
				System.out.print(subString+"="+diagramMatrix[i][j]+"\t");
			}
			System.out.println();
		}
	    
	    
	    
	   
	    
		
	}
	public int getSubstringCount(String str,String findStr){
		int lastIndex = 0;
		int count =0;
		while(lastIndex != -1){

		       lastIndex = str.indexOf(findStr,lastIndex);

		       if( lastIndex != -1){
		             count ++;
		             lastIndex+=findStr.length();
		      }
		}
		return count;
	}
	private static HashMap sortByValuesDesc(HashMap map) { 
	       List list = new LinkedList(map.entrySet());
	       // Defined Custom Comparator here
	       Collections.sort(list, new Comparator() {
	            public int compare(Object o1, Object o2) {
	               return ((Comparable) ((Map.Entry) (o2)).getValue())
	                  .compareTo(((Map.Entry) (o1)).getValue());
	            }
	       });

	       // Here I am copying the sorted list in HashMap
	       // using LinkedHashMap to preserve the insertion order
	       HashMap sortedHashMap = new LinkedHashMap();
	       for (Iterator it = list.iterator(); it.hasNext();) {
	              Map.Entry entry = (Map.Entry) it.next();
	              sortedHashMap.put(entry.getKey(), entry.getValue());
	       } 
	       return sortedHashMap;
	  }
	

}
