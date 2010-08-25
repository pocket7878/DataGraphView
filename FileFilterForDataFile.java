import java.io.File;
import javax.swing.filechooser.FileFilter;

public class FileFilterForDataFile extends FileFilter {
	@Override
	public boolean accept(File f) {
		if(f.isDirectory()) {
			return true;
		}
		String s = f.getName();
		int x = s.lastIndexOf('.');
		if(x < 0) {
			return false;
		}
		String extention = s.substring(x + 1).toLowerCase();
		if(extention.equals("data")) {
			return true;
		}
		return false;
	}

	@Override
	public String getDescription() {
		return "SunSpotDataFile";
	}
}
