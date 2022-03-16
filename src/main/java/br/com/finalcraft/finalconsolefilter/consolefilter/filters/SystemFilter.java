package br.com.finalcraft.finalconsolefilter.consolefilter.filters;

import java.io.OutputStream;
import java.io.PrintStream;

public class SystemFilter extends PrintStream {
	
	public SystemFilter(OutputStream out) {
		super(out, true);
	}
	
	@Override
	public void println(String s) {
		super.println(s);
	}
	
	private boolean shouldFilter(String s) {
		return false;
	}
	
	public static void applyFilter() {
		System.setOut(new SystemFilter(System.out));
	}

}
