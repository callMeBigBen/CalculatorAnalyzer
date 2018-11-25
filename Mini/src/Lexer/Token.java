package Lexer;

import java.util.regex.Pattern;

public class Token {
	String content ;
	int code;
	int line;
	Token(){
		
	}
	
	public void put(String _token,int _line){
		this.content = _token;
		this.code = 0;
		this.line = _line;
	}
	
	public void confirmCode(){
		if(content.equals("boolean")){
			this.code=1;
		}
		else if (content.equals("char")) {
			this.code=2;
		}
		else if (content.equals("int")) {
			this.code=3;
		}
		else if (content.equals("double")) {
			this.code=4;
		}
		else if (content.equals("string")){
			this.code=5;
		}
		else if (content.equals("true")) {
			this.code=6;
		}
		else if (content.equals("false")) {
			this.code=7;
		}
		else if (content.equals("if")) {
			this.code=8;
		}
		else if (content.equals("else")) {
			this.code=9;
		}
		else if (content.equals("while")) {
			this.code=10;
		}
		else if (content.equals("for")) {
			this.code=11;
		}
		else if (content.equals("break")) {
			this.code=12;
		}
		else if (content.equals("continue")) {
			this.code=13;
		}
		else if (content.equals("void")) {
			this.code=14;
		}
		else if (content.equals("return")) {
			this.code=15;
		}
		else if (content.equals("(")) {
			this.code=16;
		}
		else if (content.equals(")")) {
			this.code=17;
		}
		else if (content.equals("{")) {
			this.code=18;
		}
		else if (content.equals("}")) {
			this.code=19;
		}
		else if (content.equals("[")) {
			this.code=20;
		}
		else if (content.equals("]")) {
			this.code=21;
		}
		else if (content.equals(";")) {
			this.code=22;
		}
		else if (content.equals(",")) {
			this.code=23;
		}
		else if (content.equals("=")) {
			this.code=24;
		}
		else if (content.equals(">")) {
			this.code=25;
		}
		else if (content.equals("<")) {
			this.code=26;
		}
		else if (content.equals("==")) {
			this.code=27;
		}
		else if (content.equals("<=")) {
			this.code=28;
		}
		else if (content.equals(">=")) {
			this.code=29;
		}
		else if (content.equals("!=")) {
			this.code=30;
		}
		else if (content.equals("&&")) {
			this.code=31;
		}
		else if (content.equals("||")) {
			this.code=32;
		}
		else if (content.equals("!")) {
			this.code=33;
		}
		else if (content.equals("+")) {
			this.code=34;
		}
		else if (content.equals("*")) {
			this.code=35;
		}
		else if (content.equals("/")) {
			this.code=36;
		}
		else if (content.equals("%")) {
			this.code=37;
		}
		else if (content.equals("\"")) {
			this.code=38;
		}
		else if (content.equals("\'")) {
			this.code=39;
		}
		else if (content.equals("-")) {//此处识别为减号
			this.code=40;
		}
		else if (content.equals("-_")) {//此处识别为负号
			this.content="-";
			this.code=41;
		}
		else if(Pattern.matches("[A-Za-z][A-Za-z|1-9|_]*", content)){//识别标识符
			this.code=-1;
		}
		else if(Pattern.matches("(-)?[0-9]+(.[0-9]+)", content)){//识别浮点数
			this.code=-2;
		}
		else if(Pattern.matches("(-)?[0-9]+?", content)){//识别整数
			this.code=-3;
		}
		else if(Pattern.matches("\'(\\S|\\s)\'", content)){//识别字符
			this.code=-4;
		}
		else if(Pattern.matches("\"(\\S|\\s)*\"", content)){//识别字符串
			this.code=-5;
		}
		else{
			this.code=0;
		}
	}
}
