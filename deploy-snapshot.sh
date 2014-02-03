#!/bin/sh

VERSION=`grep version pom.xml | head -2 |tail -1 | cut -f 2 -d '>' | cut -f 1 -d '<'`
echo Deploying ddns-dnsjava-$VERSION
mvn gpg:sign-and-deploy-file -Durl=https://oss.sonatype.org/content/repositories/snapshots/ -DrepositoryId=sonatype-nexus-snapshots -DpomFile=target/ddns-dnsjava-$VERSION.pom -Dfile=target/ddns-dnsjava-$VERSION.jar
mvn gpg:sign-and-deploy-file -Durl=https://oss.sonatype.org/content/repositories/snapshots/ -DrepositoryId=sonatype-nexus-snapshots -DpomFile=target/ddns-dnsjava-$VERSION.pom -Dfile=target/ddns-dnsjava-$VERSION-sources.jar -Dclassifier=sources
mvn gpg:sign-and-deploy-file -Durl=https://oss.sonatype.org/content/repositories/snapshots/ -DrepositoryId=sonatype-nexus-snapshots -DpomFile=target/ddns-dnsjava-$VERSION.pom -Dfile=target/ddns-dnsjava-$VERSION-javadoc.jar -Dclassifier=javadoc
