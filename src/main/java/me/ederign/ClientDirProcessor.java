package me.ederign;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ClientDirProcessor extends SimpleFileVisitor<Path> {

    private List<String> clientDir = new ArrayList<>();

    public static List<String> process(String directory) throws IOException {
        ClientDirProcessor fileProcessor = new ClientDirProcessor();
        Path currentDir = Paths.get(directory);
//        Path currentDir = Paths.get("");
        Files.walkFileTree(currentDir, fileProcessor);
        return fileProcessor.getClientDir();
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
        if (findRootProjectDir(file)) {
            if (findClientProjects(file)) {
                clientDir.add(file.getParent().toString());
            }
        }
        return FileVisitResult.CONTINUE;
    }

    private boolean findClientProjects(Path file) {
        String dir = file.getParent().toString();
        return (dir.contains("webapp") || dir.contains("client")) &&
                !dir.contains("target") &&
                !dir.contains(".") &&
                !dir.contains("archetype") &&
                !dir.contains("test");
    }

    private boolean findRootProjectDir(Path file) {
        return file.getFileName().startsWith("pom.xml");
    }

    public List<String> getClientDir() {
        return clientDir;
    }
}
