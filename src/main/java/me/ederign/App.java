package me.ederign;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class App {

    public static void main(String... args) throws IOException, URISyntaxException {
        String root = System.getProperty("user.dir");

//        String root = "/Users/ederign/labs/rh/";
//        String repo = "appformer";
        String repo = args[0];
//        String webapp = "uberfire-webapp";
        String webapp = args[1];

        System.out.println(root);
        System.out.println(repo);
        System.out.println(webapp);

        List<String> allClientDirectories = ClientDirProcessor.process(root);

        String pluginData = new PluginConfig().generate(extractClientDirectories(repo, allClientDirectories));


        new PomUpdater(allClientDirectories, webapp).update(pluginData);

    }

    private static List<String> extractClientDirectories(String pattern, List<String> clientDirectories) {
        return clientDirectories.stream()
                //showcase
                .filter( s -> !s.contains("webapp"))
                .filter( s -> !s.contains("dashbuilder"))
                .filter( s -> !s.contains("backend"))
                .filter( s -> !s.contains("extensions"))
                .filter( s -> !s.contains("structure"))
                .filter( s -> !s.contains("workingset"))
                .filter( s -> !s.contains("m2"))
                .filter( s -> !s.contains("console"))
                .filter( s -> !s.contains("project"))
                //drools-wb
                .filter( s -> !s.contains("kie-wb-common-datasource-mgmt"))
                .filter( s -> !s.contains("stunner"))
                .filter( s -> !s.contains("ala"))
                .filter( s -> !s.contains("forms"))
                .filter(s -> s.contains(pattern)).collect(Collectors.toList());
    }

}
