package horovtom.graphics;

import javax.swing.filechooser.FileFilter;
import java.io.File;

/**
 * Created by lactosis on 14.10.16.
 */
public class FileTypeFilter extends FileFilter {

    private final String description;
    private final String extension;

    public FileTypeFilter(String description, String extension) {
        this.description = description;
        this.extension = extension;
    }

    public boolean accept(File f) {
        if (f.isDirectory()){
            return true;
        }
        return f.getName().endsWith(extension);
    }

    public String getDescription() {
        return description + String.format(" (*%s)", extension);
    }
}
