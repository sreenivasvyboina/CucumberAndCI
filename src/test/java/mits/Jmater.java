package mits;

import java.io.File;

public class Jmater {
	public static String splitFile(String s){
		File f=new File(s);
		String s1=f.getName();
		String[] words=s1.split("/.");
String r = null;
		for(String w:words){  
		System.out.println(w);  
		r=w;
		}
		return r;
	}
	public static String splitFile2(String s1){
		File f=new File(s1);
		String s2=f.getName();
		String[] words=s2.split("\\.");
String r = null;
		for(String w:words){  
		System.out.println(w);  
		r=w;
		}
		return r;
	}
	
	public static void main(String args[]){  
                  
		String ret=Jmater.splitFile("D:/1_63pages1.7mb.json");
		System.out.println(ret); 
		String ret2=Jmater.splitFile(ret);
		System.out.println(ret2);
	}

}

//File f=new File("D:/FileName/test.pdf");
//String s1=f.getName();
/*String[] words=s1.split("\\.");

for(String w:words){  
System.out.println(w);  
}
	}
*/ 