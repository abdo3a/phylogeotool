package be.kuleuven.rega.phylogeotool.tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import be.kuleuven.rega.phylogeotool.data.Sequence;
import be.kuleuven.rega.phylogeotool.data.csv.CsvUtils;
import be.kuleuven.rega.phylogeotool.data.fasta.FastaUtils;

public class CsvToFasta {
	
	public static void main(String args[]) throws IOException {
		String fileInputLocation = args[0];
		String fileOutputLocation = args[1];
		
		List<Sequence> sequences = null;
		File inputFile = new File(fileInputLocation);
		File outputFile = new File(fileOutputLocation);
		try {
			sequences = CsvUtils.readCsv(inputFile);
		} catch (IOException e) {
			System.err.println("File with location: " + fileInputLocation + " cannot be found.");
			e.printStackTrace();
		}
		
		FileWriter fw = null;
		try {
			fw = new FileWriter(outputFile);
			
			for (Sequence s : sequences) {
				String entry = FastaUtils.toFastaEntry(s);
				fw.write(entry);
				fw.write('\n');
			}
			fw.close();
		} catch (FileNotFoundException e) {
			System.err.println("File with location: " + fileInputLocation + " cannot be found.");
			e.printStackTrace();
		} finally {
			if (fw != null)
				fw.close();
		}
	}
}