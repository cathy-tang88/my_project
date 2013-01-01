package tjx.trs.pojo;

public class PersonString {
	private static final String TAB = "\t";
	private StringBuilder sb = null;

	public PersonString() {
		sb = new StringBuilder();
	}

	public void addTags(String tag) {
		sb.append(TAB);
		sb.append(tag);
	}

	public String toString() {
		return sb.toString();
	}
}
