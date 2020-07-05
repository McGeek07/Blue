package blue.util;

public class Version implements Comparable<Version> {
	public final String
		version_name;
	public final int
		version_id,
		release_id,
		patch_id;
	
	public Version(
		String version_name,
		int version_id,
		int release_id,
		int patch_id
	) {
		this.version_name = version_name;
		this.version_id = version_id;
		this.release_id = release_id;
		this.patch_id = patch_id;
	}
	
	@Override
	public String toString() {
		return 
			version_name + " " +
			version_id + "." +
			release_id + "." +
			patch_id;
	}

	@Override
	public int compareTo(Version v) {
		int t;
		if((t = this.version_id - v.version_id) != 0) return t > 0 ? 1 : -1;
		if((t = this.release_id - v.release_id) != 0) return t > 0 ? 1 : -1;
		if((t = this.patch_id - v.patch_id) != 0) return t > 0 ? 1 : -1;
		return 0;
	}
}
