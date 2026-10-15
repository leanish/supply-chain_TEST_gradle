import org.gradle.api.attributes.Category
import org.gradle.api.attributes.LibraryElements
import org.gradle.api.attributes.Usage

plugins {
    `java-library`
    id("fixture.conventions")
    id("org.ajoberstar.grgit") version "5.3.2" apply false
}

repositories {
    mavenCentral()
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(25))
    }
}

val directSecurity by configurations.creating {
    isCanBeConsumed = false
    isCanBeResolved = true
}
val transitiveSecurity by configurations.creating {
    isCanBeConsumed = false
    isCanBeResolved = true
}
val okhttpSecurity by configurations.creating {
    isCanBeConsumed = false
    isCanBeResolved = true
    attributes {
        attribute(Usage.USAGE_ATTRIBUTE, objects.named(Usage.JAVA_RUNTIME))
        attribute(Category.CATEGORY_ATTRIBUTE, objects.named(Category.LIBRARY))
        attribute(LibraryElements.LIBRARY_ELEMENTS_ATTRIBUTE, objects.named(LibraryElements.JAR))
    }
}
val floorSecurity by configurations.creating {
    isCanBeConsumed = false
    isCanBeResolved = true
}

dependencies {
    implementation("commons-codec:commons-codec:1.16.0")
    implementation(libs.slf4j.api)
    implementation("org.yaml:snakeyaml:1.33")
    compileOnly(libs.jetbrains.annotations)
    testImplementation("org.junit.jupiter:junit-jupiter:5.11.0")
    testImplementation("org.assertj:assertj-core:3.27.7")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher:1.11.0")

    directSecurity("org.apache.commons:commons-text:1.14.0")
    transitiveSecurity("org.apache.commons:commons-configuration2:2.15.0")
    okhttpSecurity("com.squareup.okhttp3:okhttp:5.5.0")
    floorSecurity("commons-beanutils:commons-beanutils:1.11.0")
    floorSecurity(libs.commons.beanutils) {
        because("GHSA-6phf-73q6-gh87, GHSA-p66x-2cv9-qq3v, GHSA-wxr5-93ph-8wr9: commons-digester3 brings vulnerable BeanUtils")
    }
}

tasks.test {
    useJUnitPlatform()
}

// Resolve the isolated security graphs without evaluating untrusted input.
tasks.register("resolveSecurityFixtures") {
    doLast {
        check(directSecurity.files.isNotEmpty())
        check(transitiveSecurity.files.isNotEmpty())
        check(okhttpSecurity.files.isNotEmpty())
        check(floorSecurity.files.isNotEmpty())
    }
}
tasks.named("check") {
    dependsOn("resolveSecurityFixtures")
}

apply(from = "declared-lower.gradle.kts")
