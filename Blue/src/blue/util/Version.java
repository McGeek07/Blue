package blue.util;

public class Version implements Comparable<Version> {
	public final String
		VERSION_NAME;
	public final int
		VERSION_ID,
		RELEASE_ID,
		PATCH_ID;
	
	public Version(
			String version_name,
			int version_id,
			int release_id,
			int patch_id
			) {
		this.VERSION_NAME = version_name;
		this.VERSION_ID = version_id;
		this.RELEASE_ID = release_id;
		this.PATCH_ID = patch_id;
	}
	
	@Override
	public String toString() {
		return 
				VERSION_NAME + " " +
				VERSION_ID + "." +
				RELEASE_ID + "." +
				PATCH_ID;				
	}

	@Override
	public int compareTo(Version v) {
		int tmp;
		if((tmp = this.VERSION_ID - v.VERSION_ID) != 0) return tmp > 0 ? 1 : -1;
		if((tmp = this.RELEASE_ID - v.RELEASE_ID) != 0) return tmp > 0 ? 1 : -1;
		if((tmp = this.PATCH_ID - v.PATCH_ID) != 0) return tmp > 0 ? 1 : -1;
		return 0;
	}
}
