val declaredLower by configurations.creating {
    isCanBeConsumed = false
    isCanBeResolved = true
}

val strictLower = providers.gradleProperty("fixture.strictLower")
    .map { it.toBooleanStrict() }
    .orElse(false)

dependencies {
    add(declaredLower.name, "org.apache.httpcomponents:httpclient:4.5.14")
    if (strictLower.get()) {
        add(declaredLower.name, "org.apache.httpcomponents:httpcore") {
            version {
                strictly("4.4.10")
            }
        }
    } else {
        add(declaredLower.name, "org.apache.httpcomponents:httpcore:4.4.16")
    }
}

tasks.register("resolveDeclaredLower") {
    doLast {
        check(declaredLower.files.isNotEmpty())
    }
}
tasks.named("check") {
    dependsOn("resolveDeclaredLower")
}
