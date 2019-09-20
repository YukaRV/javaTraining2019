package ch01.ex16;

import java.io.IOException;

 class BadDataSetException extends Exception {
	// 1.16 add field dataSetName and I/O error to BadDataSetException
	// then we can get cause of error
	public BadDataSetException(String setName, IOException e) {
		super("set name : "+setName,e);
	}
}