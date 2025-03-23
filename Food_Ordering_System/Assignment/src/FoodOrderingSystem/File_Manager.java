package FoodOrderingSystem;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Teoh Peng Kee
 */
public class File_Manager {
    public static class DirInfo {
        public final String fileDir;
        public final String fileAbsdir;
        public final boolean isFileExist;

        public DirInfo(String fileDir, String fileAbsdir, boolean isFileExist) {
            this.fileDir = fileDir;
            this.fileAbsdir = fileAbsdir;
            this.isFileExist = isFileExist;
        }
    }

    public static DirInfo fileHandlerGetdirs(String filePathExtension, String inputFilename) {
        Path currentPath = Paths.get("").toAbsolutePath();
        Path filePath = Paths.get(currentPath.toString(), filePathExtension, inputFilename);
        String fileDir = filePath.getParent().toString();
        String fileAbsdir = filePath.toAbsolutePath().toString();
        boolean isFileExist = Files.isRegularFile(filePath);
        
        return new DirInfo(fileDir, fileAbsdir, isFileExist);
    }

    public static class CreateFileInfo {
        public final String fileAbsPath;
        public final boolean isFilepathCreated;

        public CreateFileInfo(String fileAbsPath, boolean isFilepathCreated) {
            this.fileAbsPath = fileAbsPath;
            this.isFilepathCreated = isFilepathCreated;
        }
    }

    public static CreateFileInfo fileHandlerCreate(String folderPathExtension, String fileName, String header) 
            throws IOException {
        Path currentPath = Paths.get("").toAbsolutePath();
        Path fileAbsPath = Paths.get(currentPath.toString(), folderPathExtension, fileName);
        boolean isFilepathCreated = true;

        // Create directories if they don't exist
        Files.createDirectories(fileAbsPath.getParent());

        if (!Files.exists(fileAbsPath)) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileAbsPath.toString()))) {
                writer.write(header);
            }
            isFilepathCreated = false;
        }

        return new CreateFileInfo(fileAbsPath.toString(), isFilepathCreated);
    }             
}
