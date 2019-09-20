package ch01.ex16;

import java.io.FileInputStream;
import java.io.IOException;

class MyUtilities {
	public double[] getDataSet(String setName)
		throws BadDataSetException
	{
		String file = setName + ".dset";
		FileInputStream in = null;
		try {
			in = new FileInputStream(file);
			return readDataSet(in);
		} catch (IOException e) {
			throw new BadDataSetException(setName,e);
		} finally {
			try {
				if (in != null)
					in.close();
			} catch (IOException e) {
				; // ignore : reading is successful,
				  // or try to throw BadDataSetException
			}
		}
	}
	// define readDataSet
	double[] readDataSet(FileInputStream fi) {
		double[] resultSample = {-1,-1,-1};
		return resultSample;
	}
}