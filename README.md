![Project icon](https://raw.githubusercontent.com/karahanbuhan/bloodparticles-fabric/master/src/main/resources/assets/bloodparticles/icon.png)

# Blood Particles for Fabric

![GitHub license](https://img.shields.io/github/license/karahanbuhan/bloodparticles-fabric.svg)
![GitHub issues](https://img.shields.io/github/issues/karahanbuhan/bloodparticles-fabric.svg)
![GitHub tag](https://img.shields.io/github/tag/karahanbuhan/bloodparticles-fabric.svg)

Blood Particles is a free and open source mod that adds blood particles for the Minecraft Client. Blood particles occur
when entities are damaged from various sources which are all configurable.

## Downloads

You can find downloads for Blood Particles through
the [GitHub releases page](https://github.com/karahanbuhan/bloodparticles-fabric/releases).

## Building

If you would like to compile a custom build of Blood Particles from the latest sources, you will want to follow this
section.

### Prerequisites

You will need to install JDK 8 in order to build Sodium. You can either install this through a
package manager such as Chocolatey on Windows or SDKMAN! on other platforms. If you'd prefer to not use a package
manager, you can always grab the installers or packages directly from AdoptOpenJDK.

On Windows, the Oracle JDK/JRE builds should be avoided where possible due to their poor quality. Always prefer using
the open-source builds from AdoptOpenJDK when possible.

### Compiling

Navigate to the directory you've cloned this repository and launch a build with Gradle using gradlew build (Windows) or
./gradlew build (macOS/Linux). If you are not using the Gradle wrapper, simply replace gradlew with gradle or the path
to it.

The initial setup may take a few minutes. After Gradle has finished building everything, you can find the resulting
artifacts in build/libs.

## License

Blood Particles is licensed under GNU LGPLv3, a free and open-source license. For more information, please see
the [license file](https://github.com/karahanbuhan/bloodparticles-fabric/blob/master/LICENSE).