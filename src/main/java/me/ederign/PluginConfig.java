package me.ederign;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.StringReader;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

public class PluginConfig {

    String plugin = "<plugin>\n" +
            "        <groupId>org.codehaus.mojo</groupId>\n" +
            "        <artifactId>build-helper-maven-plugin</artifactId>\n" +
            "        <version>3.0.0</version>\n" +
            "        <executions>\n" +
            "          <execution>\n" +
            "            <id>add-source</id>\n" +
            "            <phase>generate-sources</phase>\n" +
            "            <goals>\n" +
            "              <goal>add-source</goal>\n" +
            "            </goals>\n" +
            "            <configuration>\n" +
            "              <sources>\n" +
            "\n" +
            "              </sources>\n" +
            "            </configuration>\n" +
            "          </execution>\n" +
            "        </executions>\n" +

            "</plugin>";
    public String generate(List<String> clientDirectories) throws IOException, URISyntaxException {
        String[] lines = plugin.split(System.getProperty("line.separator"));

        StringBuilder data = new StringBuilder();
        Stream.of(lines).forEach(line -> {
            data.append(line).append("\n");
            if (line.contains("<sources>")) {
                clientDirectories.forEach(c ->
                        data.append(createSource(c)));
            }
        });
        return data.toString();
    }

    private String createSource(String line) {

        String java = "<source>" + line + "/src/main/java</source>\n";
        String resource = "<source>" + line + "/src/main/resources</source>\n";
        return java + resource;
    }
}
