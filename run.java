package syntax;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

public class run {
	
	//���屣����
	static ArrayList<String> reserved_word = new ArrayList<String>();
	//���������
	static ArrayList<String> operator = new ArrayList<String>();
	//�����ʶ����
	static ArrayList<String> identifier = new ArrayList<String>();
	//����tokenӳ���
	static HashMap<String,Integer> tokens = new HashMap<String,Integer>();
	//���ֵ
	public static void fill_list(){
		reserved_word.add("boolean");  
		reserved_word.add("char");	   
		reserved_word.add("int");
		reserved_word.add("double");
		reserved_word.add("string");
		reserved_word.add("true");
		reserved_word.add("false");
		reserved_word.add("if");
		reserved_word.add("else");
		reserved_word.add("while");
		reserved_word.add("for");
		reserved_word.add("break");
		reserved_word.add("continue");
		reserved_word.add("void");
		reserved_word.add("return");
		
		operator.add("(");
		operator.add(")");
		operator.add("{");
		operator.add("}");
		operator.add("[");
		operator.add("]");
		operator.add(";");
		operator.add(",");
		operator.add("=");
		operator.add(">");
		operator.add("<");
		operator.add("==");
		operator.add("<=");
		operator.add(">=");
		operator.add("!=");
		operator.add("&&");
		operator.add("||");
		operator.add("!");
		operator.add("+");
		operator.add("*");
		operator.add("/");
		operator.add("%");
	}
	
	//�����Ƿ�Ϊ������,���ҷ��ض�Ӧ�������
	public static boolean isReserved(String str){
		for(String rsvdwd:reserved_word){
			if(rsvdwd.equals(str)){
				return true;	//�Ǳ������򷵻�T
			}	
		}
		return false;//���Ǳ����֣�����F
	}
	
	//�ж��Ƿ�Ϊ��ĸ
	public static boolean isLetter(char letter){
		if(letter>='a'&&letter<='z' ||letter>='A'&&letter<='Z' )
			return true;
		else
			return false;
	}
	
	//�ж��Ƿ�Ϊ����
	public static boolean isNumber(char letter){
		if(letter>='0'&&letter<='9')
			return true;
		else
			return false;
	}
	
	//Ԥ����
	public static String preProcess(String sourceCode){
		int len = sourceCode.length();
		int count = 0;
		StringBuffer newCode = new StringBuffer();
		
		for(int i=0;i<len;i++){
			//��������ע��
			if(sourceCode.charAt(i)=='/'&&sourceCode.charAt(i+1)=='/'){
				i+=2;
				while(sourceCode.charAt(i)!='\n'){
					i++;
				}
			}
			//��������ע��
			if(sourceCode.charAt(i)=='/'&&sourceCode.charAt(i+1)=='*'){
				i+=2;
				while(sourceCode.charAt(i)=='*'&&sourceCode.charAt(i+1)=='/'){
					i++;//����ɨ��
					if (sourceCode.charAt(i) == '$')
					{
					System.out.println("ע�ͳ���û���ҵ� */���������������\n");
					System.exit(0);
					}
				}
				i+=2;
			}
			//�����ռ�
			if(sourceCode.charAt(i)!='\n' 
			   &&sourceCode.charAt(i)!='\t' 
			   &&sourceCode.charAt(i)!='\r'){
				newCode.append(sourceCode.charAt(i));
			}
		}
		
		//Դ����ת��
		return newCode.toString();	
	}
	
	
	/*
	 * �ʷ����ຯ�����ֳ�����token���õ���Ӧ�����Ͳ��ұ���
	 * ���ͣ�1.������; 2.��ʶ��; 3.����;4.����
	 */
	public static int scanner(String sourceCode,int currPosition){
		int tag = -1;
		int movement = 0;//���ڷ��ص��ƶ���������ȡtoken֮��Ҫ�ı��ȡλ�ã���������C��ָ��
		int len = sourceCode.length();
		StringBuffer token = new StringBuffer();
		char ch = sourceCode.charAt(currPosition);
		
		//���˿ո�
		while(ch==' '){
			currPosition++;
			ch = sourceCode.charAt(currPosition);
			movement++;
		}
		
		//��ĸ��ͷ����ʼ��¼token
		if(isLetter(ch)){
			token.append(sourceCode.charAt(currPosition));
			currPosition++;
			//���������ֻ�����ĸ��������¼token
			while(isLetter(sourceCode.charAt(currPosition))
					||isNumber(sourceCode.charAt(currPosition))){
				token.append(sourceCode.charAt(currPosition));
				currPosition++;
			}
			//token.append('\0');
			String tmpstr=token.toString();
			movement+=tmpstr.length();
			
			//�鱣���ֱ����ǣ��޸����ͱ�ǩΪ1,����Ϊ2
			if(isReserved(tmpstr)){
				tag = 1;
				tokens.put(tmpstr, tag);
			}
			else{
				tag = 2;
				tokens.put(tmpstr, tag);
			}
		}
		//���ֿ�ͷ
		else if(isNumber(ch)){
			token.append(sourceCode.charAt(currPosition));
			while(isNumber(sourceCode.charAt(currPosition+1))){
				token.append(sourceCode.charAt(currPosition));
			}
			//token.append('\0');
			String tmpstr = token.toString();
			movement+=tmpstr.length();
			tag = 3;
			tokens.put(tmpstr, tag);
		}
		//�����ſ�ͷ
		else if(ch=='+'||ch=='-'||ch=='*'||ch=='/'
				||ch=='('||ch==')'||ch=='['||ch==']'||ch=='{'
				||ch=='}'||ch=='%'||ch=='='||ch==','||ch==';'){
			tag = 4;
			token.append(ch);		
			String tmpstr = token.toString();
			movement++;
			tokens.put(tmpstr,tag);
		}
		//���ܵĸ��Ϸ��ſ�ͷ
		else if(ch=='!'||ch=='>'||ch=='<'||ch=='&'||ch=='|'){
			tag=4;
			if(ch=='!'&&sourceCode.charAt(currPosition+1)=='='){
				token.append("!=");
				String tmpstr = token.toString();
				movement +=2;
				tokens.put(tmpstr, tag);
			}
			else if(ch=='!'&&sourceCode.charAt(currPosition+1)!='='){
				token.append("!");
				String tmpstr = token.toString();
				movement++;
				tokens.put(tmpstr, tag);
			}
			else if(ch=='>'&&sourceCode.charAt(currPosition+1)=='='){
				token.append(">=");
				String tmpstr = token.toString();
				movement+=2;
				tokens.put(tmpstr, tag);
			}else if(ch=='>'&&sourceCode.charAt(currPosition+1)!='='){
				token.append(">");
				String tmpstr = token.toString();
				movement++;
				tokens.put(tmpstr, tag);
			}else if(ch=='<'&&sourceCode.charAt(currPosition+1)=='='){
				token.append("<=");
				String tmpstr = token.toString();
				movement+=2;
				tokens.put(tmpstr, tag);
			}else if(ch=='<'&&sourceCode.charAt(currPosition+1)!='='){
				token.append("<");
				String tmpstr = token.toString();
				movement+=2;
				tokens.put(tmpstr, tag);
			}else if(ch=='&'&&sourceCode.charAt(currPosition+1)=='&'){
				token.append("&&");
				String tmpstr = token.toString();
				movement+=2;
				tokens.put(tmpstr, tag);
			}else if(ch=='|'&&sourceCode.charAt(currPosition+1)=='|'){
				token.append("||");
				String tmpstr = token.toString();
				movement+=2;
				tokens.put(tmpstr, tag);
			}
		}
		else if(ch=='$'){
			tag=0;//������
		}
		else{
			System.out.println("Error letter,can't distinguish.");
			System.exit(0);
		}
		
		return movement;
	}
	
	//��ȡ�ļ�����
	public static String readToString(String fileName) {  
        String encoding = "UTF-8";  
        File file = new File(fileName);  
        Long filelength = file.length();  
        byte[] filecontent = new byte[filelength.intValue()];  
        try {  
            FileInputStream in = new FileInputStream(file);  
            in.read(filecontent);  
            in.close();  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        try {  
            return new String(filecontent, encoding);  
        } catch (UnsupportedEncodingException e) {  
            System.err.println("The OS does not support " + encoding);  
            e.printStackTrace();  
            return null;  
        }  
    }
	

	public static void main(String[] args) {
		fill_list();
		//System.out.println(operator.get(0));
		//��ȡ����
		String src = readToString("D:/code.txt");
		//Ԥ����
		String newSrc = preProcess(src);
		int i = 0;
		int len = newSrc.length();
		while(i<len){
			i+=scanner(newSrc,i);
		}
		
		for(String key:tokens.keySet()){
			System.out.println(key+",Type:"+tokens.get(key));
		}
		
	}

}
