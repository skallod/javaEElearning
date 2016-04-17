-------------------------------
JPF demo application
-------------------------------

Welcome to JPF-demo!

JPF-demo is a sample Swing based GUI application that demonstrates how
Framework can be used to build extendable application. It demonstrates
main principles in building plug-ins and shows how to run framework.

To run application from command line, change current directory to the folder
where you unzipped distribution package and type the following command:

java -jar lib\jpf-boot.jar        [windows]
./java -jar lib/jpf-boot.jar      [unix/linux]

To get understanding how application works, start looking at
"org.jpf.demo.toolbox.core.CorePlugin" class in "org.jpf.demo.toolbox.core"
plug-in.

For more information visit JPF website at http://jpf.sourceforge.net

NOTE:
If program failed during start-up process, look for "jpf-boot-error.txt"
file in current folder or check log files in "logs" folder.