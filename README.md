# Gradle declarations and security fixture

Synthetic fixture covering cases 2, 3, 4, 5, 6, 8, 9, 10, 11, 12 and 17: literals, a version
catalog, a buildSrc convention, Kotlin DSL, independent security configurations,
a wrapper, pinned Actions, ordinary/strict declared-lower dependencies, and majors
(bump-it and secure-it) whose upgrade needs source adaptation, at apply time or
during review.

Current main still holds the seeds of cases 2, 3, 5, 6, 8, 9, 10, 11, 12 and 17, plus
the security floors that secure-it's #12 added. The security seeds of case 4, C
(OkHttp parent search) and the grgit plugin move were consumed when #12 merged on
2026-10-11. EXPECTED.md lists which commit seeded each one and how to re-seed it.

```sh
./gradlew --no-daemon check
```

Use a Gradle daemon on JDK 21 with JDK 25 installed for the main toolchain.
buildSrc uses the daemon JVM. Security inputs are isolated in directSecurity,
transitiveSecurity, okhttpSecurity and floorSecurity; case 10 uses declaredLower.
The two OkHttp baselines were separate seed commits (3078549 and ab614a7), as
documented in EXPECTED.md. Tests exercise benign APIs;
no vulnerability is exploited and HttpClient does not perform network I/O.

For the independent case-10 probes, run the commands in
[EXPECTED.md](EXPECTED.md#case-10-reproducible-probes). They prepare local copies
and collect real inventories, plans, checks and verifier/gate reports.

[EXPECTED.md](EXPECTED.md) is the single source of case expectations, runnable
acceptance steps, timing caveats and VERIFY items. [VALIDATION.md](VALIDATION.md)
records checks and tool runs already performed. These private repositories are test inputs;
do not publish packages. Claude manages GitHub adoption and the run configuration.
This fixture isn't part of regular tool passes: it runs only in release-candidate
passes that opt fixtures in (`SUPPLY_CHAIN_FIXTURES=1`).
The gate is pinned to supply-chain v0.6.0 and uses the same commit for inventory
and comparison; the tools are at v0.4.2. EXPECTED.md has the pin history.
