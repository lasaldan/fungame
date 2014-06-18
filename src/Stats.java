import java.util.Iterator;
import java.util.Map;

/**
 * Created by daniel on 6/17/14.
 */
public class Stats {

	private Map<String,Object> data;

	public void addMessage(String s, Object v) {
		data.put(s,v);
	}

	public String getOutput() {

		String output = "";

		for(String s: data.keySet()) {
			output += s + ": " + data.get(s) + "\n";
		}

		return output;
	}
}
