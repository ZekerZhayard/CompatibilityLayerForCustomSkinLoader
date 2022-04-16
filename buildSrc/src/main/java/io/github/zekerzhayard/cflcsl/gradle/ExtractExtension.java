package io.github.zekerzhayard.cflcsl.gradle;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.gradle.api.Project;
import org.gradle.api.artifacts.Dependency;

public class ExtractExtension {
    private final Project project;
    private final Map<Dependency, String> dependencies = new HashMap<>();

    public ExtractExtension(Project project) {
        this.project = project;
    }

    public Dependency extract(Object dependency, String fileName) {
        Dependency result = this.project.getDependencies().create(dependency);
        this.dependencies.put(result, fileName);
        return result;
    }

    public String match(String group, String name, String version) {
        for (Dependency d : this.dependencies.keySet()) {
            if (Objects.equals(d.getGroup(), group) && Objects.equals(d.getName(), name) && Objects.equals(d.getVersion(), version)) {
                return this.dependencies.get(d);
            }
        }
        return null;
    }
}
