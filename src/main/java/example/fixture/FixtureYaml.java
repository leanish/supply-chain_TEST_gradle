package example.fixture;

import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

/**
 * Case 12: SnakeYAML 2.0 removed the single-argument {@code Constructor(Class)} that 1.x had, so this passes
 * {@code LoaderOptions}. The only fix for CVE-2022-1471 is 2.0, which is why the upgrade had to adapt this code.
 */
public final class FixtureYaml {
    private FixtureYaml() {}

    public static FixtureConfig load(String yaml) {
        return new Yaml(new Constructor(FixtureConfig.class, new LoaderOptions())).load(yaml);
    }
}
