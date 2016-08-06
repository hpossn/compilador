package lexicalAnalyzer;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileParser {

	private List<String> lineList;
	private String fileName;
	private int lineCounter;
	private int lineChar;
	
	public static final int END_OF_LINE = '\n';
	public static final int END_OF_FILE = 0;
	
	public FileParser(String fileName) throws FileNotFoundException {
		lineList = new ArrayList<>();
		
		this.fileName = fileName;
		this.lineCounter = 0;
		this.lineChar = 0;
		
		loadLines();
	}

	private void loadLines() throws FileNotFoundException {
		BufferedReader bufferedReader =
				new BufferedReader(new FileReader(fileName));
		
		String line;
		
		try {
			while((line = bufferedReader.readLine()) != null)
				lineList.add(line + "\n");
			
			removeLastBlankLine();
			
			bufferedReader.close();
			
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	private void removeLastBlankLine() {
		if(lineList.isEmpty())
			return;
		
		String line = lineList.remove(lineList.size() - 1);
		
		lineList.add(line.replace('\n', (char) 0));
	}

	public char getNextChar() {
		String line = "";
		char ch = END_OF_FILE;
		
		if(lineCounter < lineList.size()) {
			line = lineList.get(lineCounter);
			ch = line.charAt(lineChar);
			lineChar++;
		}
		
		if(ch == END_OF_LINE) {
			lineChar = 0;
			lineCounter++;
		}
		
		return ch;
	}

	public int getCurrentLineNumber() {
		return lineCounter + 1;
	}

}
