package common;

import java.util.HashMap;
import java.util.Map;

public abstract class APIRequest {
	
	private Map<String, String> fields;
	
	
	@Override
	public String toString() {
		StringBuilder string = new StringBuilder();
		for (String key : fields().keySet()) {
			string.append(key + ": " 
								+ fields().get(key) 
								+ "\n");
		}
		return string.toString();
	}
	
	protected Map<String, String> fields() {
		if (this.fields == null) {
			this.fields = new HashMap<>();
		}
		return this.fields;
	}
	
	public Integer getId() {
		String temp_id = fields().getOrDefault("id", null);
		if (temp_id == null) {
			return null;
		}
		return Integer.parseInt(temp_id);
		
	}

	public void setId(int id) {
		fields().put("id", Integer.toString(id));
	}
	
	public Map<String, String> getNotNullFields() {
		return this.fields();
	}

}
