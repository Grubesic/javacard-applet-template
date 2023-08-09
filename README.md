# JavaCard Applet Template

An easy-to-use JavaCard project template including everything needed to quickly get started with developing a JavaCard applet.


## Features
* Applet code template;
* Applet unit tests template;
* Build and test the applet with one command;
* All dependencies downloaded and included automatically;


## Set-up
All paths are given relative to the repository root.

1. Install JDK, Ant, and Maven:
   1. You could use, for example, [SDKMAN](https://sdkman.io/) to manage your JVM-related SDKs;
   2. Supported JDK versions are `1.8`, `11`, and `17` for JavaCard `3.1.0`; `1.8` and `11` for earlier versions;
2. Fetch JavaCard SDKs: `git submodule update --init`;
3. Set required JavaCard SDK version (the options are the sub-folders of `sdk/javacard_sdks` without the trailing `_kit`):
   1. `sdk.version` property in `src/build.xml`;
   2. `sdk.version` property in `src/pom.xml`;
4. Fetch other dependencies: `cd src`, `ant fetch`;
5. Build the applet: `ant build`;
6. The build outputs are in `src/target`:
   1. Applet compiled as a library for testing: `<applet_name>.jar`;
   2. Packaged applet for uploading to a card: `<applet_name>.cap`;
7. Test the applet: `cd test`, `mvn test`;
8. Upload the packaged applet to your smartcard:
   1. Recommended tool: [GlobalPlatformPro](https://github.com/martinpaljak/GlobalPlatformPro).


## Dependencies
* [JavaCard SDKs](https://github.com/martinpaljak/oracle_javacard_sdks) - downloaded automatically as a git submodule;
* [Ant-JavaCard](https://github.com/martinpaljak/ant-javacard) - JavaCard build tool - downloaded automatically by Ant;
* [JCardSim](https://github.com/licel/jcardsim) - JavaCard simulator used for testing - downloaded automatically by Ant;

## Project structure
```
.  
├── sdk/ (external dependencies)
│   ├── javacard_sdks/ (JavaCard SDKs) 
│   └── ...  
├── src/ (applet module)
│   ├── src/ (source code)
│   ├── target/ (build outputs)
│   │   ├── template-applet.jar (applet as a library for testing)
│   │   ├── template-applet.cap (packaged applet for uploading to a card)
│   │   └── ...
│   ├── build.xml (Ant build script)
│   └── pom.xml (Maven configuration - needed for some IDEs to locate dependencies)
├── test/ (applet tests module)
│   ├── src/ (code)
│   │   ├── test/ (tests)
│   │   └── main/ (helper classes)
│   └── pom.xml (Maven configuration)
└── ...  
```

## Configuration guide
The two modules the project consists of are tightly interconnected, so changes in one need to be reflected in another for everything to work. 
This section describes (hopefully) all the steps you need to take to change some parameters.

* Change the applet class name:
  * Rename the class (initially `com.vadimtch.applet_template.src.MyApplet`) and the file it is in;
  * Update the `applet.class` property in `src/build.xml`;
  * Update the applet class name in `appletClass` in `com.vadimtch.applet_template.test.SimulatorWrapper`;
* Change the Java package name:
  * Change the package name in the source files;
  * Rename the package directories in:
    * `src/src/main/java`;
    * `test/src/main/java`;
    * `test/src/test/java`;
  * Change the `applet.class` property in `src/build.xml`;
  * Change the `groupId` property in `src/pom.xml`;
  * Change the `groupId` property in `test/pom.xml`;
  * Update the applet class name in `appletClass` in `com.vadimtch.applet_template.test.SimulatorWrapper`;
* Change the applet AID:
  * Change the `package.aid` property in `src/build.xml` - this must be a colon-separated list of 5-16 hex bytes;
  * Change the `applet.aid` property in `src/build.xml` - this must start with the package AID and also be no longer than 16 bytes;
  * Update the applet AID in `appletAID` in `com.vadimtch.applet_template.test.SimulatorWrapper`;
* Rename the applet `.jar` and `.cap` files:
  * Change the `package.filename` property in `src/build.xml` - this is the filename used for the two files when creating them during a build;
  * Update the `package.filename` property in `test/pom.xml`;
* Change the JavaCard SDK version used:
  * Ensure that the JDK you are using is supported with the required SDK version (see the [Set-up](#set-up) section for details)
  * Choose the new SDK version from the list in `sdk/javacard_sdks` and note its name;
  * Set the `sdk.version` property in `src/build.xml` to the directory name of the chosen SDK version, without the trailing `_kit`:
    * For example, to use JavaCard `3.0.5u3`, set it to `jc305u3`;
  * Set the `sdk.version` property in `src/pom.xml`.