package br.ufscar.dc.rejasp.model;

import java.util.ArrayList;

import br.ufscar.dc.rejasp.model.ASTNodeInfo.MethodInfo;
import br.ufscar.dc.rejasp.model.ASTNodeInfo.ParameterInfo;

public class Pointcut {
	public static final int PC_NONE = 0;
	// Primitive Pointcuts using MethodPattern 
	public static final int PC_CALL = 1;
	public static final int PC_EXECUTION = 1 << 1;
	public static final int PC_WITHINCODE = 1 << 2;
	// Primitive Pointcuts using Type
	public static final int PC_THIS = 1 << 3;
	public static final int PC_TARGET = 1 << 4;
	public static final int PC_ARGS = 1 << 5;
	// Primitive Pointcuts using BooleanExpression
	public static final int PC_IF = 1 << 6;
	// Primitive Pointcuts using another poitcut
	public static final int PC_CFLOW = 1 << 7;
	
	private String sName;
	private String sTypeName;
	private MethodInfo methodInfo;
	private int nPrimitivePC;
	private String sIdentation;
	/**
	 * If null, default expression will be generated by this class; otherwise,
	 * sExpression content will be used. 
	 */
	private String sExpression;
	/**
	 * It defines a list of arguments that has been customized by user or null
	 * if user has chosen default arguments 
	 */
	private ArrayList lstCustomArguments;
	
	public Pointcut(String sName, String sTypeName, MethodInfo methodInfo, int nPrimitvePC) {
		this.sName = sName;
		this.sTypeName = sTypeName;
		this.methodInfo = methodInfo;
		this.nPrimitivePC = nPrimitvePC;
		this.sExpression = null;
		this.lstCustomArguments = null;
		this.sIdentation = "";
	}
	
	public void setName(String name) {
		this.sName = name;
	}
	
	public void setTypeName(String typeName) {
		this.sTypeName = typeName;
	}
	
	public void setPrimitivePC( int primitivePC) {
		this.nPrimitivePC = primitivePC;
	}
	
	public void setExpression(String expression) {
		this.sExpression = expression;
	}
	
	public void setIdentation( String sIdentation ) {
		this.sIdentation = sIdentation;
	}
	
	/**
	 * @param lstArguments List of ParameterInfo elements.
	 */
	public void customizeArguments(ArrayList lstArguments) {
		this.lstCustomArguments = lstArguments;
	}
	
	/**
	 * @return A list of custom arguments 
	 */
	public ArrayList getCustomArguments() {
		return lstCustomArguments;
	}
	
	public String getName() {
		return sName;
	}
	
	public String getTypeName() {
		return sTypeName;
	}
	
	public int getPrimitivePC() {
		return nPrimitivePC;
	}
	
	public String getIdentation() {
		return sIdentation;
	}

	public MethodInfo getMethodInfo() {
		return methodInfo;
	}
	
	public void addPrimitivePC(int primitivePC) {
		this.nPrimitivePC |= primitivePC;
	}
	
	public void removePrimitivePC(int primitivePC) {
		this.nPrimitivePC &= ~primitivePC;
	}
	
	/**
	 * @param i Index (based zero) of parameter in the list
	 * @return Name of parameter or null if not found
	 */
	public String getParameterNameAt(int i) {
		if(lstCustomArguments == null || lstCustomArguments.size() > i)
			return null;
		return ((ParameterInfo)lstCustomArguments.get(i)).getName();
	}
	
	public String toString() {
		String sPointcut = sIdentation + "pointcut " + sName + "(";
		ArrayList parameters = null;
		if ( (nPrimitivePC & PC_THIS) != 0 )
			sPointcut += methodInfo.getType().getName() + " " + sTypeName;
		if ( (nPrimitivePC & PC_ARGS) != 0 ) {
			if ( lstCustomArguments != null )
				parameters = lstCustomArguments;
			else
				parameters = methodInfo.getParameters();
			
			for( int i = 0; i < parameters.size(); i++ )
				if( i == 0 ) {
					if(sPointcut.charAt(sPointcut.length() - 1) != '(')
						sPointcut += ", ";
					sPointcut += ((ParameterInfo)parameters.get(0)).toString();
				}
				else
					sPointcut += ", " + ((ParameterInfo)parameters.get(i)).toString();
		}
		if(sExpression == null) {
			sPointcut += "):\r\n" + sIdentation + sIdentation;
			if ( (nPrimitivePC & PC_EXECUTION) != 0 )
				sPointcut += "execution(" + methodInfo.getSignatureForPointcutExpression() + 
							")";
			if ( (nPrimitivePC & PC_THIS) != 0 ) {
				if(sPointcut.charAt(sPointcut.length() - 1) != ':')
					sPointcut += " && \r\n" + sIdentation + sIdentation;
				sPointcut += "this(" + sTypeName + ")";
			}
			if ( (nPrimitivePC & PC_ARGS) != 0 ) {
				if(sPointcut.charAt(sPointcut.length() - 1) != ':')
					sPointcut += " && \r\n" + sIdentation + sIdentation;
				sPointcut += "args(";
				for(int i = 0; i < parameters.size(); i++)
					if(i == 0)
						sPointcut += ((ParameterInfo)parameters.get(i)).getName();
					else
						sPointcut += ", " + ((ParameterInfo)parameters.get(i)).getName();
				sPointcut += ")";
			}
		}
		else
			sPointcut += "):\r\n" + sIdentation + sIdentation + sExpression;
		return sPointcut + ";";
	}
}
