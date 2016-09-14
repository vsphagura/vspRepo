-compile ObjectSizeFetcher in .class file
-jar -cmf manifest.txt agent.jar com/vsp/binarysize/ObjectSizeFetcher.class
-copy this last line in MANIFEST>MF file in the agent.jar
	Manifest-Version: 1.0
	Created-By: 1.6.0_45 (Sun Microsystems Inc.)
	Premain-Class: com.vsp.binarysize.ObjectSizeFetcher
-Run by executing:
	C:\Users\vphagura\workspace\BinarySize\bin>java -javaagent:agent.jar -cp . com.vsp.binarysize.Tester