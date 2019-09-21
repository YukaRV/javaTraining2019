package ch01.ex16;

import java.io.IOException;

 class BadDataSetException extends Exception {
	 // 1.16 add field dataSetName and I/O error to BadDataSetException
	 // then we can get cause of error
	 // 2019/09/22 modify
	 private String datasetName;
	 public BadDataSetException(String setName, IOException e) {
		 super(e);
		 datasetName = setName;
	}

	public String getDatasetName() {
		return datasetName;
	}
}