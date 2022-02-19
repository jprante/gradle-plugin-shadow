Gradle plugin for creating fat/uber JARs with support for package relocation

This work is a fork of

Gradle-Shadow-Plugin
https://github.com/johnrengelman/shadow
Copyright (c) 2013 John Engelman All Rights Reserved.

When forked, this projects represented the state of 22-Sep-2018 (Version 2.0.4-git, License: Apache 2.0)
with the following changes:

- removed dependencies jdom, commons-io, apache-ant, plexus-utils, log4j2, xmlunit, commons-lang3, guava
- integration of a trimmed down version of jdependency https://github.com/tcurdt/jdependency version 2.0, 22-Sep-2018, Apache 2.0
- integration of a trimmed down commons-compress zip (Apache 2.0)
- only dependency is asm (asm-commons, asm-util) (3-Clause BSD License)
- updates to gradle 7.3.2, JDK 17
- removed maven plugin dependency
- all tests removed which are XML related
- some tests were removed due to internal gradle changes
- removed original copyright headers and @author tags to avoid confusion, this is not the original code,  
  do not report errors in org.xbib packages to the original authors

This product is licensed to you under the Apache License, Version 2.0 (the "License").  
You may not use this product except in compliance with the License.
