package me.ederign;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PomUpdater {
    private final List<String> clientDirectories;
    private final String webappName;

    public PomUpdater(List<String> clientDirectories, String webappName) {
        this.clientDirectories = clientDirectories;
        this.webappName = webappName;
    }

    public void update(String pluginContent) throws IOException {
        Optional<String> webAppDir = clientDirectories.stream().filter(d -> d.contains(webappName)).findFirst();

        Path path = Paths.get(webAppDir.get() + "/pom.xml");
        try (Stream<String> lines = Files.lines(path)) {
            List<String> replaced = lines
                    .map(line -> line.replaceAll("<!-- Include additional sources path for hot reload -->", pluginContent))
                    .collect(Collectors.toList());
            Files.write(path, replaced);
        }
    }
}
