/**
 * 
 */
package manager.states;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.yaml.snakeyaml.Yaml;

/**
 * @author Patrick Blesi
 *
 */
public abstract class AbstractAppState implements AppState {

	public static final String STATE_PATH = "./";
	
	/**
	 * 
	 */
	public AbstractAppState() {
		
	}

	public void commitToDisk() {
		
		try {
		
			Yaml yaml = new Yaml();
			
			String output = yaml.dump(this);
			
			File file = new File(STATE_PATH + this.getStateFileName());
		
			if (!file.exists()) {
				file.createNewFile();
			}
			
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(output);
			bw.close();
			
		} catch (IOException e) {
			// TODO: Log error
			e.printStackTrace();
		}
		
	}
	
	protected static String readFile(String path, Charset encoding) throws IOException {
		
	  byte[] encoded = Files.readAllBytes(Paths.get(path));
	  return encoding.decode(ByteBuffer.wrap(encoded)).toString();
	
	}
	
	
	
}
